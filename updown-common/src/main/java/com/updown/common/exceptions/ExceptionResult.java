package com.updown.common.exceptions;

import lombok.Data;

/**
 * 返回到页面上的结果
 * @auther: 闫昊
 * @date: 2019/8/21
 */
@Data
public class ExceptionResult {
    private int status;

    private String message;

    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getValue();
        this.message = em.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
