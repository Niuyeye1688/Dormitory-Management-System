package com.dormitory.servlet.common;

import com.dormitory.dao.AdminDao;
import com.dormitory.dao.StudentDao;
import com.dormitory.dao.impl.AdminDaoImpl;
import com.dormitory.dao.impl.StudentDaoImpl;
import com.dormitory.model.Admin;
import com.dormitory.model.Student;
import com.dormitory.util.PasswordUtil;
import com.dormitory.util.CsrfUtil;
import com.dormitory.util.VerifyCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final AdminDao adminDao = new AdminDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/common/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userType = req.getParameter("userType");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String verifyCode = req.getParameter("verifyCode");

        // verify code check
        if (!VerifyCodeUtil.verify(req, verifyCode)) {
            req.setAttribute("error", "Verify code error");
            req.getRequestDispatcher("/WEB-INF/views/common/login.jsp").forward(req, resp);
            return;
        }

        String encryptedPassword = PasswordUtil.encrypt(password);
        HttpSession session = req.getSession();

        if ("admin".equals(userType)) {
            Admin admin = adminDao.findByUsername(username);
            if (admin != null && admin.getStatus() == 1 && PasswordUtil.verify(password, admin.getPassword())) {
                adminDao.updateLastLoginTime(admin.getId());
                session.setAttribute("admin", admin);
                session.setAttribute("userType", "admin");
                CsrfUtil.generateToken(session);
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
                return;
            }
        } else if ("student".equals(userType)) {
            Student student = studentDao.findByStudentNo(username);
            if (student != null && student.getStatus() == 1 && PasswordUtil.verify(password, student.getPassword())) {
                session.setAttribute("student", student);
                session.setAttribute("userType", "student");
                CsrfUtil.generateToken(session);
                resp.sendRedirect(req.getContextPath() + "/student/dashboard");
                return;
            }
        }

        req.setAttribute("error", "Username or password error");
        req.getRequestDispatcher("/WEB-INF/views/common/login.jsp").forward(req, resp);
    }
}
