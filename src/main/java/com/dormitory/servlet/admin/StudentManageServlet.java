package com.dormitory.servlet.admin;

import com.dormitory.dao.StudentDao;
import com.dormitory.dao.impl.StudentDaoImpl;
import com.dormitory.model.Student;
import com.dormitory.util.PasswordUtil;
import com.dormitory.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StudentManageServlet extends HttpServlet {

    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/admin/student_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Student student = studentDao.findById(Integer.parseInt(id));
                req.setAttribute("student", student);
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/student_form.jsp").forward(req, resp);
            return;
        }

        // 列表查询
        String keyword = req.getParameter("keyword");
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }

        int totalCount = studentDao.count(keyword);
        List<Student> list = studentDao.findPage(keyword, (page - 1) * pageSize, pageSize);
        Pagination<Student> pagination = new Pagination<>(page, pageSize, totalCount, list);

        req.setAttribute("pagination", pagination);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/student_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            Student student = new Student();
            String idStr = req.getParameter("id");
            student.setStudentNo(req.getParameter("studentNo"));
            student.setName(req.getParameter("name"));

            Integer gender = parseIntParam(req, "gender", "性别");
            if (gender == null) { doGet(req, resp); return; }
            student.setGender(gender);

            student.setPhone(req.getParameter("phone"));
            student.setEmail(req.getParameter("email"));
            student.setCollege(req.getParameter("college"));
            student.setMajor(req.getParameter("major"));
            student.setClassName(req.getParameter("className"));

            String yearStr = req.getParameter("enrollmentYear");
            if (yearStr != null && !yearStr.isEmpty()) {
                Integer year = parseIntParam(req, "enrollmentYear", "入学年份");
                if (year == null) { doGet(req, resp); return; }
                student.setEnrollmentYear(year);
            }

            if (idStr != null && !idStr.isEmpty()) {
                // 更新
                Integer id = parseIntParam(req, "id", "ID");
                if (id == null) { doGet(req, resp); return; }
                student.setId(id);

                Integer status = parseIntParam(req, "status", "状态");
                if (status == null) { doGet(req, resp); return; }
                student.setStatus(status);
                studentDao.update(student);
            } else {
                // 新增
                student.setPassword(PasswordUtil.encrypt("123456"));
                student.setStatus(1);
                studentDao.insert(student);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/student");
            return;
        }

        if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                studentDao.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/student");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/student");
    }

    private Integer parseIntParam(HttpServletRequest req, String name, String fieldName) {
        String value = req.getParameter(name);
        if (value == null || value.trim().isEmpty()) {
            req.setAttribute("error", fieldName + "不能为空");
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            req.setAttribute("error", fieldName + "必须是数字");
            return null;
        }
    }
}
