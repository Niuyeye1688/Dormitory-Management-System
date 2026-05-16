package com.dormitory.servlet.student;

import com.dormitory.dao.StudentDao;
import com.dormitory.dao.impl.StudentDaoImpl;
import com.dormitory.model.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StudentServlet extends HttpServlet {

    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/student/student_profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Student sessionStudent = (Student) session.getAttribute("student");
        Student student = studentDao.findById(sessionStudent.getId());

        student.setPhone(req.getParameter("phone"));
        student.setEmail(req.getParameter("email"));

        int result = studentDao.update(student);
        if (result > 0) {
            Student updated = studentDao.findById(student.getId());
            session.setAttribute("student", updated);
            req.setAttribute("success", "个人信息更新成功");
        } else {
            req.setAttribute("error", "更新失败");
        }
        req.getRequestDispatcher("/WEB-INF/views/student/student_profile.jsp").forward(req, resp);
    }
}
