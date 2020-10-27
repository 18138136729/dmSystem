package com.daizhiyuan.dms.service.impl;

import com.daizhiyuan.dms.utils.Constants;

 public class BaseService {
     static int checkPage(int page){
        if (page < Constants.Page.DEFAULT_PAGE){
            page = Constants.Page.DEFAULT_PAGE;
        }
        return page;
    }
}
