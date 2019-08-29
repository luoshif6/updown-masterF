package com.updown.common.pojo;

import java.io.Serializable;

/**
 * 接受修改密码的实体类
 */
public class UserBo implements Serializable{

    private Long user_id;  //用户id

    private String oldPas; //旧密码

    private String user_password; //新密码

    private String token;  //用户登录令牌

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getOldPas() {
        return oldPas;
    }

    public void setOldPas(String oldPas) {
        this.oldPas = oldPas;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
