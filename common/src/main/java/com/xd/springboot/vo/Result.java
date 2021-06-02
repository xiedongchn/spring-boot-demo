package com.xd.springboot.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xd
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 8824592056392431833L;

    public static final String SUCCESS = "S";
    public static final String FAILED = "F";
    public static final String ERROR = "E";

    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result<Object> success() {
        return new Result<>(Result.SUCCESS, "");
    }

    public static Result<Object> failed() {
        return new Result<>(Result.FAILED, "");
    }

    public static Result<Object> error() {
        return new Result<>(Result.ERROR, "");
    }
}
