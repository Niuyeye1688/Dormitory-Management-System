package com.dormitory.filter;

import com.dormitory.util.CsrfUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String method = req.getMethod();
        if (!"POST".equalsIgnoreCase(method)) {
            chain.doFilter(request, response);
            return;
        }

        String path = req.getRequestURI();
        String contextPath = req.getContextPath();
        path = path.substring(contextPath.length());

        if ("/login".equals(path) || "/verifyCode".equals(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (!CsrfUtil.validate(req)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF Token invalid");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
