package com.daizhiyuan.dms.utils;

public interface Constants {
    interface Page {
        int DEFAULT_PAGE = 1;
    }

    interface Setting{
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }

    interface User{
        String ROLE_ADMIN="role_admin";
        String ROLE_NORMAL="role_normal";
        String DEFAULT_STATE = "1";
        String COOKIE_TOKEN_KEY = "loginToken";
        //redis的key
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_TOKEN = "key_token_";

    }
    interface TimeValueInMillions{
        long MIN = 60 * 1000;
        long HOUR = 60 * MIN;
        long DAY = 24 * HOUR;
        long WEEK = 7 * DAY;
        long MONTH = 30 * DAY;
        long HOUR_2 = 2 * HOUR;
    }
    interface TimeValueInSecond{
        int MIN = 60;
        int HOUR = 60 * MIN;
        int DAY = 24 * HOUR;
        int WEEK = 7 * DAY;
        int MONTH = 30 * DAY;
        int HOUR_2 = 2 * HOUR;
    }
}
