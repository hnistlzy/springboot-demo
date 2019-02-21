package com.keton.server.enums;
/**
 * @author KentonLee
 * @date 2019/2/21
 */
public enum StatusCode {
    /**
     * Success(200,"成功"),
     */
   Success(200,"成功"),
    /**
     * Fail(-1,"失败")
     */
   Fail(-1,"失败"),
    /**
     * Invalid_Pram(10021,"参数不合法")
     */
   Invalid_Pram(10021,"参数不合法"),
    /**
     * FileUploadFail(10022,"文件上传失败")
     */
    FileUploadFail(10022,"文件上传失败");

    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
