package com.xusi.system.security;

import com.xusi.system.utils.JwtUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: IntelliJ IDEA
 * @description: 自定义过滤器
 * @author: xusi
 * @create:2020-10-25 16:06
 **/
@Component
public class TokenFilter extends BasicHttpAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
        if(token != null && !token.equals("undefined") && token.startsWith(JwtUtil.TOKEN_PREFIX)){
            try {
                return executeLogin(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }



    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader(JwtUtil.TOKEN_HEADER);
        CustomToken customToken = new CustomToken(token.replace(JwtUtil.TOKEN_PREFIX,""));
        try {
            getSubject(request,response).login(customToken);

            return true;
        } catch (Exception e){
            return false;
        }

    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
