package com.xusi.system.config;

import com.xusi.system.security.TokenFilter;
import com.xusi.system.security.TokenRealm;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: IntelliJ IDEA
 * @description: shiro配置类
 * @author: xusi
 * @create:2020-10-25 15:47
 **/
@Configuration
public class ShiroConfig {

    @Bean
    public TokenRealm tokenRealm(){
        return new TokenRealm();
    }

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(){
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");
//        return hashedCredentialsMatcher;
//    }

    @Bean
    public DefaultWebSecurityManager webSecurityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(tokenRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(webSecurityManager());

        Map<String,Filter> filters = new HashedMap();
        filters.put("token",new TokenFilter());
        bean.setFilters(filters);

        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/index","anon");
        filterMap.put("/user/**","anon");
        filterMap.put("/login","anon");
        // swagger 配置过滤
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
//        filterMap.put("/**","authc");
        filterMap.put("/**","token");
        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }
}
