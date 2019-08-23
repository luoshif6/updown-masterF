package com.updown.common.exceptions;

/**
 * 返回到页面上的结果
 *
 * @auther: 闫昊
 * @date: 2019/8/21
 */

public class ExceptionResult {
    private int status;

    private String message;

    private Long timestamp;

    public ExceptionResult(ExceptionEnum e) {
        this.status = e.getValue();
        this.message = e.getMessage();
        this.timestamp = System.currentTimeMillis();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
