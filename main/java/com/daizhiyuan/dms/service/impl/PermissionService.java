package com.daizhiyuan.dms.service.impl;

import com.daizhiyuan.dms.entity.User;
import com.daizhiyuan.dms.service.UserService;
import com.daizhiyuan.dms.utils.ClaimsUtils;
import com.daizhiyuan.dms.utils.Constants;
import com.daizhiyuan.dms.utils.CookieUtils;
import com.daizhiyuan.dms.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service("permission")
public class PermissionService {
    @Autowired
    private UserService userService;
    public boolean admin(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey==null || tokenKey.length()==0) {
            return false;
        }
        User blogUser = userService.checkUser();
        if (blogUser == null) {
            return false;
        }
        if (Constants.User.ROLE_ADMIN.equals(blogUser.getRoles())) {
            //管理员
            return true;
        }
        return false;
    }

    public boolean adminAction(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey==null || tokenKey.length()==0) {
            return false;
        }
        User blogUser = userService.checkUser2();
        if (blogUser == null) {
            return false;
        }
        if (Constants.User.ROLE_ADMIN.equals(blogUser.getRoles())) {
            //管理员
            return true;
        }
        return false;
    }
    }
