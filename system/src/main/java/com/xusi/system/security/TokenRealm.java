package com.xusi.system.security;

import com.xusi.system.entity.User;
import com.xusi.system.service.imp.UserServiceImp;
import com.xusi.system.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-25 15:51
 **/
public class TokenRealm extends AuthorizingRealm {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserServiceImp userService;

    // 处理哪种token
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        System.out.println(principals);
        // 获取 user 对应的 role
        User user = userService.getOneByName(principals.toString());
        String role = user.getRole();

        // 设置权限
        info.addRole(role);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String upToken = (String)token.getCredentials();
        String username = jwtUtil.getSubject(upToken);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        if(JwtUtil.isExpiration(upToken)){
            throw new AuthenticationException("token过期");
        }
        User user = userService.getOneByName(username);
        if(user == null){
            throw new AuthenticationException("用户不存在");
        }
        // 创建 token 的时候没有存储密码
//        Claims claims = JwtUtil.getTokenBody(upToken);
//        String pw = claims.get("password").toString();
//        if(!pw.equals(user.getUserPw())){
//            throw new AuthenticationException("密码不匹配");
//        }

        return new SimpleAuthenticationInfo(upToken,token.getCredentials(),getName());
    }
}
