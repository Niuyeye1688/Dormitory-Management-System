package com.dormitory.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        String path = uri.substring(contextPath.length());

        // 检查是否已登录
        boolean isLoggedIn = false;
        String userType = null;

        if (session != null) {
            if (session.getAttribute("admin") != null) {
                isLoggedIn = true;
                userType = "admin";
            } else if (session.getAttribute("student") != null) {
                isLoggedIn = true;
                userType = "student";
            }
        }

        if (!isLoggedIn) {
            resp.sendRedirect(contextPath + "/");
            return;
        }

        // 权限检查：管理员只能访问 /admin/*，学生只能访问 /student/*
        if (path.startsWith("/admin/") && !"admin".equals(userType)) {
            resp.sendRedirect(contextPath + "/");
            return;
        }
        if (path.startsWith("/student/") && !"student".equals(userType)) {
            resp.sendRedirect(contextPath + "/");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
