package com.daizhiyuan.dms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.daizhiyuan.dms.entity.Dorm;
import com.daizhiyuan.dms.entity.RefreshToken;
import com.daizhiyuan.dms.mapper.RefreshTokenMapper;
import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.entity.Settings;
import com.daizhiyuan.dms.entity.User;
import com.daizhiyuan.dms.mapper.SettingsMapper;
import com.daizhiyuan.dms.mapper.UserMapper;
import com.daizhiyuan.dms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.daizhiyuan.dms.utils.*;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhu
 * @since 2020-10-11
 */
@Service
@Validated
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private SettingsMapper settingsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    @Override
    public Response initAdminAccount(User user) {
        Settings oneByKey = settingsMapper.findOneByKey(Constants.Setting.MANAGER_ACCOUNT_INIT_STATE);
        if (oneByKey!=null) {
            return Response.fail("管理员账号已经初始化了");
        }
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        userMapper.insert(user);

        Settings settings = new Settings();
        settings.setKey(Constants.Setting.MANAGER_ACCOUNT_INIT_STATE);
        settings.setValue("1");
        settingsMapper.insert(settings);
        return Response.succ("管理员初始化成功");
    }
    @Autowired
    private Random random;

    private static final int[] captcha_font_types = {Captcha.FONT_1,Captcha.FONT_2,Captcha.FONT_3,Captcha.FONT_4,Captcha.FONT_5};

    @Override 
    public void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
        if (captchaKey==null || captchaKey.length()==0||captchaKey.length()<13) {
            return;
        }
        long key;
        try {
            key = Long.parseLong(captchaKey);
        }catch (Exception e){
            return;
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        int captchaType = random.nextInt(3);
        Captcha targetCaptcha = null;
        if (captchaType==0){
            targetCaptcha = new SpecCaptcha(200, 60, 5);
        }else if (captchaType ==1){
            targetCaptcha = new GifCaptcha(200,60,5);
        }
        // 三个参数分别为宽、高、位数
        else {
            targetCaptcha = new ArithmeticCaptcha(200,60);
            targetCaptcha.setLen(2);
        }
        targetCaptcha.setFont(captcha_font_types[random.nextInt(captcha_font_types.length)]);
        targetCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = targetCaptcha.text().toLowerCase();

        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT+key,content,60*10);
        targetCaptcha.out(response.getOutputStream());
    }


    @Override
    public Response doLogin(String captcha,
                                  String captchaKey,
                                  User user,
                                  HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) {
        String captchaValue =(String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (!captcha.trim().toLowerCase().equals(captchaValue)) {
            return Response.fail("验证码不正确");
        }
        //验证成功，删除redis里的验证码
        redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT+captchaKey);
        String userName = user.getUserName();
        String password = user.getPassword();
        User userFromDb = userMapper.findOneByUserName(userName);
        if (userFromDb == null){
            return Response.fail("用户名或密码错误");
        }
        boolean matches = passwordEncoder.matches(password, userFromDb.getPassword());
        if (!matches) {
            return Response.fail("用户名或密码错误");
        }
        if (!"1".equals(userFromDb.getState())){
            return Response.ACCOUNT_DENIED();
        }
        createToken(httpServletResponse, userFromDb);
        return Response.succ("登录成功");
    }
    private String createToken(HttpServletResponse httpServletResponse, User userFromDb) {
        Map<String, Object> claims = ClaimsUtils.user2Claims(userFromDb);
        String token = JwtUtil.createToken(claims);
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
        redisUtils.set(Constants.User.KEY_TOKEN+tokenKey,token,Constants.TimeValueInSecond.DAY);
        httpServletResponse.setHeader("Access-control-Expose-Headers",Constants.User.KEY_TOKEN);
        CookieUtils.setUpCookie(httpServletResponse,Constants.User.COOKIE_TOKEN_KEY,tokenKey);
        String refreshTokenValue = JwtUtil.createRefreshToken(userFromDb.getId(), Constants.TimeValueInMillions.MONTH);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUserId(userFromDb.getId());
        refreshToken.setTokenKey(tokenKey);
        refreshTokenMapper.deleteAllByUserId(userFromDb.getId());
        refreshTokenMapper.delete(new QueryWrapper<RefreshToken>().eq("token_key",tokenKey));
        refreshTokenMapper.insert(refreshToken);
        return tokenKey;

    }
    private HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
    private HttpServletResponse getResponse(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }
    @Override
    public User checkUser2() {
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.COOKIE_TOKEN_KEY);
        User user = parseByToken(tokenKey);
//        if (user == null) {
//            RefreshToken refreshToken = refreshTokenMapper.findOneByTokenKey(tokenKey);
//            if (refreshToken == null) {
//                return null;
//            }
//            try {
//                JwtUtil.parseJWT(refreshToken.getRefreshToken());
//                String userId = refreshToken.getUserId();
//                User userFromDb = userMapper.selectById(userId);
//                String newTokenKey = createToken(getResponse(), userFromDb);
//                return parseByToken(newTokenKey);
//            } catch (Exception ex) {
//                return null;
//            }
//        }
        return user;
    }
    @Override
    public User checkUser() {
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.COOKIE_TOKEN_KEY);
        User user = parseByToken(tokenKey);
        if (user == null) {
            RefreshToken refreshToken = refreshTokenMapper.findOneByTokenKey(tokenKey);
            if (refreshToken == null) {
                return null;
            }
            try {
                JwtUtil.parseJWT(refreshToken.getRefreshToken());
                String userId = refreshToken.getUserId();
                User userFromDb = userMapper.selectById(userId);
                String newTokenKey = createToken(getResponse(), userFromDb);
                return parseByToken(newTokenKey);
            } catch (Exception ex) {
                return null;
            }
        }
        return user;
    }
    private User parseByToken(String tokenKey){
        String token = (String) redisUtils.get(Constants.User.KEY_TOKEN + tokenKey);
        if (token != null) {
            try {
                Claims claims = JwtUtil.parseJWT(token);
                return ClaimsUtils.claims2User(claims);
            }catch (Exception e){
                return null;
            }
        }
        return null;
    }

    @Override
    public Response doLogout() {
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey == null || tokenKey=="") {
            return Response.ACCOUNT_NOT_LOGIN();
        }
        redisUtils.del(Constants.User.KEY_TOKEN + tokenKey);
        CookieUtils.deleteCookie(getResponse(),Constants.User.COOKIE_TOKEN_KEY);
        int result = refreshTokenMapper.deleteAllByTokenKey(tokenKey);
        return Response.succ("退出登录成功");
    }

    @Override
    public Response listUsers(int page) {
        page = BaseService.checkPage(page);
        Page<User> pager = new Page<>(page,8);
        Page<User> userPage = userMapper.selectPage(pager,new QueryWrapper<User>());
        return Response.succ("获取用户列表成功").setData(userPage);
    }


}
