package com.xd.springbootdemo.exception;

import java.text.MessageFormat;

/**
 * 控制流程的异常类，不记录异常的堆栈信息(性能提升极大)，主要用途：
 * 1、抛出自定义错误信息的异常时，如明确异常该如何处理，且不需要异常堆栈信息时，使用该类
 * 2、规范异常的使用，使用自定义的异常错误码和错误信息
 * 3、由于不打印堆栈信息，故打印日志时不要打印该异常对象，例如使用{@code log.error("{}", e.getMessage())}而不是{@code log.error("{}", e.getMessage(), e)}
 */
public class ExpectedException extends Exception {
    private static final long serialVersionUID = 6975574003770730650L;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误描述
     */
    private final String msg;

    public ExpectedException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ExpectedException(ExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }

    public ExpectedException(String msg) {
        super(msg);
        //通用错误码，可以使用常量或枚举
        this.code = -1;
        this.msg = msg;
    }

    /**
     * 重写堆栈填充，不填充错误堆栈信息，提高性能
     * 注意：当重写父类该方法时，不要访问子类也就当前类的私有变量，由于重写了父类的fillInStackTrace方法，父类在其构造函数中会调用子类的
     * fillInStackTrace方法，此时子类的私有变量例如code、msg还未初始化，导致其分别被赋予默认值0和null，父类子类的加载顺序可解释此现象
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}[{1}]", this.code == -1 ? "" : this.code, this.msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
