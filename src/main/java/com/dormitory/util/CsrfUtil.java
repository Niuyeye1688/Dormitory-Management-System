package com.dormitory.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CsrfUtil {

    private static final String CSRF_TOKEN_ATTR = "csrfToken";

    public static String generateToken(HttpSession session) {
        String token = UUID.randomUUID().toString();
        session.setAttribute(CSRF_TOKEN_ATTR, token);
        return token;
    }

    public static boolean validate(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        String requestToken = request.getParameter(CSRF_TOKEN_ATTR);
        if (sessionToken == null || requestToken == null) {
            return false;
        }
        return sessionToken.equals(requestToken);
    }

    public static String getToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return "";
        String token = (String) session.getAttribute(CSRF_TOKEN_ATTR);
        return token != null ? token : "";
    }
}
