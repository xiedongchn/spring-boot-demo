package com.xd.springbootdemo.exception;

/**
 * 明确定义的错误码及错误信息的常量类
 */
public enum ExceptionEnum {
    EX_400(400, "通用异常");

    private final int code;
    private final String msg;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ExceptionEnum of(int code) {
        for (ExceptionEnum item : ExceptionEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return EX_400;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
