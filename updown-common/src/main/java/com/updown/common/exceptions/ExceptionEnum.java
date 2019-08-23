package com.updown.common.exceptions;

/**
 * 上传异常枚举类，如果有异常需要全部定义在这里
 *
 * @auther: 闫昊
 * @date: 2019/8/21
 */

public enum ExceptionEnum {
    /**
     * 枚举：一组相同类型的常量，有固定实例个数，所以要提前设定
     * 下面格式是简写的，实际是用final修饰的
     * 枚举要定义在类的最前面
     */
    INVALID_FILE_FORMAT(400, "密码数据格式错误"),
    PASSWORD_SAME_ERROR(403, "新旧密码相同"),
    USER_DELETE_EXCEPTION(500, "删除用户失败"),
    USER_NOT_FOUND(404, "用户未查询到"),
    USER_INSERT_FAIL(500, "用户添加失败"),

    USER_DATA_NULL(400, "用户数据为空或者不全"),
    TASK_SELECT_FAIL(404, "查询任务失败"),
    FILE_INSERT_ERROR(404,"文件上传失败"),
    ;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ExceptionEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    ExceptionEnum() {

    }

    //    状态码
    private int value;
    //    消息
    private String message;
}
