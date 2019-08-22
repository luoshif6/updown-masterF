package com.updown.sso.service.impl;

import com.updown.common.pojo.UpdownResult;
import com.updown.sso.jedis.JedisClient;
import com.updown.sso.service.UserExitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserExitServiceImpl implements UserExitService {

    @Autowired
    private JedisClient client;

    @Value("${USER_INFO}")
    private String USER_INFO;

    @Override
    public UpdownResult deleteUserStatusByToken(String token) {
        //1.注入jedisclient
        //2.判断redis中是否有当前用户登录的信息
        String s = client.get(USER_INFO + ":" + token);
        //如果为空返回
        if (StringUtils.isBlank(s)){
            return UpdownResult.build(400,"退出失败");
        }
        //3.设置session时间为0
        client.expire(USER_INFO+":"+token,0);
        return UpdownResult.ok();
    }
}
