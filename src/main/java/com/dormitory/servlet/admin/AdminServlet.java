package com.dormitory.servlet.admin;

import com.dormitory.dao.AdminDao;
import com.dormitory.dao.impl.AdminDaoImpl;
import com.dormitory.model.Admin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AdminServlet extends HttpServlet {

    private final AdminDao adminDao = new AdminDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/admin_profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        admin.setRealName(req.getParameter("realName"));
        admin.setPhone(req.getParameter("phone"));
        admin.setEmail(req.getParameter("email"));

        int result = adminDao.update(admin);
        if (result > 0) {
            // 更新session中的admin信息
            Admin updated = adminDao.findById(admin.getId());
            session.setAttribute("admin", updated);
            req.setAttribute("success", "个人信息更新成功");
        } else {
            req.setAttribute("error", "更新失败");
        }
        req.getRequestDispatcher("/WEB-INF/views/admin/admin_profile.jsp").forward(req, resp);
    }
}
