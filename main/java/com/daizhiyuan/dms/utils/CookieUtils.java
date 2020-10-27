package com.daizhiyuan.dms.utils;

import com.sun.deploy.net.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static final int default_age = 60*60*24*365;

    public static final String domain = "localhost";

    public static void setUpCookie(HttpServletResponse response, String key, String value){
        setUpCookie(response,key,value,default_age);
    }
    public static void setUpCookie(HttpServletResponse response,String key,String value,int age){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(age);
        response.addCookie(cookie);

    }
    public static void deleteCookie(HttpServletResponse response,String key){
        setUpCookie(response,key,null,0);
    }
    public static String getCookie(HttpServletRequest request,String key){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
