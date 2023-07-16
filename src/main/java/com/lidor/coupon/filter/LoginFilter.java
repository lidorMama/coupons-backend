//package com.lidor.coupon.filter;
//
//import com.lidor.coupon.util.JWTUtils;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//@Component
//public class LoginFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String methodType = request.getMethod().toLowerCase();
//        // Options means it's part of the CORS request
//        // No need to check anything
//        if (methodType.equals("options")) {
//            // filterChain.doFilter = All good, u may go ahead
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        String url = request.getRequestURI().toLowerCase();
//        if (isRequestAWhitelisted(methodType, url)) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//
//        String token = request.getHeader("Authorization");
//
//        // Questions -
//        // 1. Performance ??
//        // 2. Error object ??
//        // 3. Hacking ??
//        try {
//            JWTUtils.validateToken(token);
//            filterChain.doFilter(servletRequest, servletResponse);
//        } catch (Exception e) {
////            e.printStackTrace(); ???
//            response.setStatus(401);
//        }
//    }
//
//    private boolean isRequestAWhitelisted(String methodType, String url) {
//        if (methodType.equals("post") && url.endsWith("user/login")) {
//            return true;
//        }
//
//        if (methodType.equals("post") && url.endsWith("/user")) {
//            return true;
//        }
//
//        if (methodType.equals("get") && url.endsWith("/coupon")) {
//            return true;
//        }
//
//        if (methodType.equals("get") && url.endsWith("/category")) {
//            return true;
//        }
//
//        if (methodType.equals("get") && url.startsWith("/coupon")) {
//            return true;
//        }
//
//        if (methodType.equals("get") && url.endsWith("/company")) {
//            return true;
//        }
//
//        return false;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}