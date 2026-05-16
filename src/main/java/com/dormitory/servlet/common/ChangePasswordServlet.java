package com.dormitory.servlet.common;

import com.dormitory.dao.AdminDao;
import com.dormitory.dao.StudentDao;
import com.dormitory.dao.impl.AdminDaoImpl;
import com.dormitory.dao.impl.StudentDaoImpl;
import com.dormitory.model.Admin;
import com.dormitory.model.Student;
import com.dormitory.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordServlet extends HttpServlet {

    private final AdminDao adminDao = new AdminDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "New passwords do not match");
            req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
            return;
        }

        String encryptedNew = PasswordUtil.encrypt(newPassword);

        Admin admin = (Admin) session.getAttribute("admin");
        Student student = (Student) session.getAttribute("student");

        if (admin != null) {
            if (!PasswordUtil.verify(oldPassword, admin.getPassword())) {
                req.setAttribute("error", "Old password incorrect");
                req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
                return;
            }
            adminDao.updatePassword(admin.getId(), encryptedNew);
            req.setAttribute("success", "Password changed, please re-login");
            session.invalidate();
            req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
            return;
        }

        if (student != null) {
            if (!PasswordUtil.verify(oldPassword, student.getPassword())) {
                req.setAttribute("error", "Old password incorrect");
                req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
                return;
            }
            studentDao.updatePassword(student.getId(), encryptedNew);
            req.setAttribute("success", "Password changed, please re-login");
            session.invalidate();
            req.getRequestDispatcher("/WEB-INF/views/common/change_password.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
