package com.daizhiyuan.dms.response;

public enum ResponseState {
    SUCCESS(true,200,"操作成功"),
    FAILED(false,400,"操作失败"),
    JOIN_IN_SUCCESS(true,202,"注册成功"),
    ACCOUNT_NOT_LOGIN(false,400,"账号未登录"),
    PERMISSION_DENIED(false,400,"无权访问"),
    ACCOUNT_DENIED(false,403,"账号已被禁止"),
    ERROR_404(false,403,"页面丢失"),
    ERROR_403(false,403,"主人，我还没有缓过来，请刷新重试！"),
    ERROR_504(false,504,"系统繁忙，请稍后重试！"),
    ERROR_505(false,505,"请求错误，请检查提交数据");

    ResponseState(boolean success,int code,String message){
        this.code = code;
        this.success = success;
        this.message = message;
    }
    private int code;
    private String message;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
