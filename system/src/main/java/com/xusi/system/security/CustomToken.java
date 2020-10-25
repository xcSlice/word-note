package com.xusi.system.security;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: IntelliJ IDEA
 * @description:
 * @author: xusi
 * @create:2020-10-25 16:15
 **/
public class CustomToken implements AuthenticationToken {
    private String token;

    public CustomToken(String token){
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
