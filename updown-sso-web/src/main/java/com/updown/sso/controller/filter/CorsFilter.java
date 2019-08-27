package com.updown.sso.controller.filter;

import org.apache.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CorsFilter implements Filter {     //filter 接口的自定义实现
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", "*");           
        String token = request.getHeader("token");
        System.out.println("filter origin:"+token);//通过打印，可以看到一次非简单请求，会被过滤两次，即请求两次，第一次请求确认是否符合跨域要求（预检），这一次是不带headers的自定义信息，第二次请求会携带自定义信息。
        if ("OPTIONS".equals(request.getMethod())){//这里通过判断请求的方法，判断此次是否是预检请求，如果是，立即返回一个204状态吗，标示，允许跨域；预检后，正式请求，这个方法参数就是我们设置的post了
          response.setStatus(HttpStatus.SC_NO_CONTENT); //HttpStatus.SC_NO_CONTENT = 204
          response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");//当判定为预检请求后，设定允许请求的方法
          response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token"); //当判定为预检请求后，设定允许请求的头部类型
          response.addHeader("Access-Control-Max-Age", "1");  // 预检有效保持时间                       
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {     
    }
}
