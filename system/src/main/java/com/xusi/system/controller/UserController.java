package com.xusi.system.controller;

import com.xusi.system.entity.User;
import com.xusi.system.service.imp.UserServiceImp;
import com.xusi.system.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-25 16:51
 **/
@RestController
@Api(tags = "权限管理")
public class UserController {
    @Resource
    private UserServiceImp userService;
    @Resource
    private JwtUtil jwtUtil;

    @ApiOperation("数据库验证成功返回token, 使用token来授权其他操作")
    @PostMapping("/login")
    public ResponseEntity login(String username, String password){
        User user = userService.login(username, password);
        if(user != null){
            String token = jwtUtil.createToken(username, user.getRole(), false);
            return ResponseEntity.ok(JwtUtil.TOKEN_PREFIX + token);
        } else {
            return ResponseEntity.status(500).build();
        }
    }

    @ApiOperation("使用用户名和密码注册一个普通用户")
    @PostMapping("/register")
    public ResponseEntity register(String username, String password){
        if (userService.isExistByName(username)) {
            return ResponseEntity.status(500).build();
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(jwtUtil.passwordEncode(password));
        try {
            userService.save(user);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).build();
        }
    }
}
