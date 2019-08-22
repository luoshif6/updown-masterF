package com.updown.common.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理器，做一个通知拦截
 * 自定义异常，以后需要抛异常的场景里都用这里的异常
 *
 * @auther: 闫昊
 * @date: 2019/8/21
 */
@ControllerAdvice
public class Advice {
    @ExceptionHandler(UpException.class)
    public ResponseEntity<ExceptionResult> MyException(UpException e) {
//        返回状态码(.status)和内容(.body)
        return ResponseEntity.status(e.getExceptionEnum().getValue()).body(new ExceptionResult(e.getExceptionEnum()));
    }
}
