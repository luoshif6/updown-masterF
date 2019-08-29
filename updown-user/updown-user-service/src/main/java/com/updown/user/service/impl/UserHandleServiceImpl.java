package com.updown.user.service.impl;

import com.updown.common.exceptions.ExceptionEnum;
import com.updown.common.exceptions.UpException;
import com.updown.common.pojo.UpdownResult;
import com.updown.common.pojo.UserBo;
import com.updown.mapper.UserMapper;
import com.updown.pojo.User;
import com.updown.sso.service.UserExitService;
import com.updown.user.service.UserHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserHandleServiceImpl implements UserHandleService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserExitService userExitService;

    @Override
    public UpdownResult updateUserPassword(UserBo user) {

        //1.判断密码格式是否正确
        if (user.getUser_id() == null || user.getUser_password() == null){
            return UpdownResult.build(400,"数据格式有误",null);
        }
        if (user.getUser_password().length() < 6){
            return UpdownResult.build(400,"数据格式有误",null);
        }
        //1.1判断输入的原密码是否正确
        User suser = this.userMapper.selectByPrimaryKey(user.getUser_id());
        //1.2将原密码和数据库中密码进行对比
        //先将原密码进行MD5加密
        String oldPas = user.getOldPas();
        oldPas = DigestUtils.md5DigestAsHex(oldPas.getBytes());
        //进行对比
        if (!StringUtils.equals(oldPas,suser.getUser_password())){
            //不一致,返回403
            return UpdownResult.build(403,"原密码输入有误",null);
        }

        //2.判断密码是否和上次密码一致
        //2.1获取数据库中user
        //2.2将参数user_password进行MD5加密
        String md5_password = DigestUtils.md5DigestAsHex(user.getUser_password().getBytes());
        //2.3比较密码是否一致
        if (StringUtils.equals(md5_password,suser.getUser_password())){
            //3.一致,返回403
            return UpdownResult.build(403,"新旧密码不能保持一致",null);
        }
        //4.不一致,进行密码修改,返回201
        suser.setUser_password(md5_password);
        this.userMapper.updateByPrimaryKeySelective(suser);

        //密码修改完清除用户登录的信息,需要重新登录
        UpdownResult result = this.userExitService.deleteUserStatusByToken(user.getToken());
        if (result.getStatus() == 200){
            return UpdownResult.ok();
        }
        return UpdownResult.build(400,"用户退出失败");
    }


    @Override
    public User findUserByUserId(Long user_id) {
        if (user_id == null){
            throw new UpException(ExceptionEnum.USER_NOT_FOUND);
        }
        User user = this.userMapper.selectByPrimaryKey(user_id);
        return user;
    }
}

