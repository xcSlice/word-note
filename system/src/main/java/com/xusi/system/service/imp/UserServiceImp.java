package com.xusi.system.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xusi.system.entity.User;
import com.xusi.system.mapper.UserMapper;
import com.xusi.system.service.IUserService;
import com.xusi.system.utils.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-25 16:53
 **/
@Service
public class UserServiceImp extends ServiceImpl<UserMapper,User> implements IUserService{
    @Resource
    private JwtUtil jwtUtil;


    public User login(String username, String password){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = getOne(wrapper);
        String passwordEncode = jwtUtil.passwordEncode(password);
        if(user == null || !user.getPassword().equals(passwordEncode)){
            return null;
        }
        return user;
    }

    public boolean isExistByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = getOne(wrapper);
        System.out.println("user = " + user);
        if(user == null) return false;
        else return true;
    }

    public User getOneByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = getOne(wrapper);
        return user;
    }
}
