package com.updown.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 上传服务异常，抛异常需要使用这个类
 * @auther: 闫昊
 * @date: 2019/8/21
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
