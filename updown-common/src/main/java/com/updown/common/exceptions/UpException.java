package com.updown.common.exceptions;

/**
 * 上传服务异常，抛异常需要使用这个类
 * @auther: 闫昊
 * @date: 2019/8/21
 */
public class UpException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }

    public void setExceptionEnum(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public UpException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public UpException(String message, ExceptionEnum exceptionEnum) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }

    public UpException(String message, Throwable cause, ExceptionEnum exceptionEnum) {
        super(message, cause);
        this.exceptionEnum = exceptionEnum;
    }

    public UpException(Throwable cause, ExceptionEnum exceptionEnum) {
        super(cause);
        this.exceptionEnum = exceptionEnum;
    }

    public UpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionEnum exceptionEnum) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.exceptionEnum = exceptionEnum;
    }
}
