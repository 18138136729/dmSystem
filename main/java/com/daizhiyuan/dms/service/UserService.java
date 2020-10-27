package com.daizhiyuan.dms.service;

import com.daizhiyuan.dms.response.Response;
import com.daizhiyuan.dms.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhu
 * @since 2020-10-11
 */
public interface UserService extends IService<User> {
    Response initAdminAccount(User user);

    Response doLogin(String captcha, String captchaKey, User user, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    User checkUser2();

    User checkUser();

    Response doLogout();

    Response listUsers(int page);
}
