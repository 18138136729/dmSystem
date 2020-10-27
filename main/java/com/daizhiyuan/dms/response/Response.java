package com.daizhiyuan.dms.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int code;
    private boolean success;
    private String message;
    private Object data;

    public Response(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }
    public static Response succ(){
        return new Response(ResponseState.SUCCESS);
    }
    public static Response ACCOUNT_NOT_LOGIN(){

        return new Response(ResponseState.ACCOUNT_NOT_LOGIN);
    }
    public static Response PERMISSION_DENIED(){

        return new Response(ResponseState.PERMISSION_DENIED);
    }
    public static Response ACCOUNT_DENIED(){

        return new Response(ResponseState.ACCOUNT_DENIED);
    }
    public static Response ERROR_404(){

        return new Response(ResponseState.ERROR_404);
    }
    public static Response ERROR_403(){

        return new Response(ResponseState.ERROR_403);
    }
    public static Response ERROR_504(){

        return new Response(ResponseState.ERROR_504);
    }
    public static Response ERROR_505(){

        return new Response(ResponseState.ERROR_505);
    }


    public static Response succ(String message){
        Response responseResult = new Response(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static Response fail(){
        return new Response(ResponseState.FAILED);
    }

    public static Response fail(String message){
        Response responseResult = new Response(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public Object getData() {
        return data;
    }

    public Response setData(Object data) {
        this.data = data;
        return this;
    }
}
