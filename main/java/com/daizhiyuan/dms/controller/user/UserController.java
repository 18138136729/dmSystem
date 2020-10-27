package com.daizhiyuan.dms.controller.user;


import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.entity.User;
import com.daizhiyuan.dms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhu
 * @since 2020-10-11
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/init")
    public Response initAdminAccount(@RequestBody User user){
        return userService.initAdminAccount(user);
    }

    @PostMapping("/login/{captcha}/{captcha_key}")
    public Response login(@PathVariable("captcha_key")String captchaKey,
                                @PathVariable("captcha")String captcha,
                                @Validated @RequestBody User user,
                                HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse){
        return userService.doLogin(captcha,captchaKey,user,httpServletRequest,httpServletResponse);
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response,@RequestParam("captcha_key")String captchaKey){
        try {
            userService.createCaptcha(response,captchaKey);
        }catch (Exception e){
            log.error(e.toString());
        }
    }

    @GetMapping("/info")
    public Response getUserInfo(){
        User user = userService.checkUser();
        return Response.succ("获取成功").setData(user);
    }
    @GetMapping("/logout")
    public Response doLogout(){
        return userService.doLogout();
    }
    @PreAuthorize("@permission.adminAction()")
    @GetMapping("/list")
    public Response listUsers(@RequestParam("page")int page){
        return userService.listUsers(page);
    }
}

