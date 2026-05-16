package com.dormitory.servlet.student;

import com.dormitory.dao.*;
import com.dormitory.dao.impl.*;
import com.dormitory.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RepairServlet extends HttpServlet {

    private final RepairDao repairDao = new RepairDaoImpl();
    private final AllocationDao allocationDao = new AllocationDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            HttpSession session = req.getSession();
            Student student = (Student) session.getAttribute("student");
            Allocation allocation = allocationDao.findActiveByStudentId(student.getId());
            if (allocation != null) {
                req.setAttribute("allocation", allocation);
            }
            req.getRequestDispatcher("/WEB-INF/views/student/repair_form.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Student student = (Student) session.getAttribute("student");
        List<Repair> list = repairDao.findByStudentId(student.getId());
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/student/repair_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("submit".equals(action)) {
            HttpSession session = req.getSession();
            Student student = (Student) session.getAttribute("student");
            Allocation allocation = allocationDao.findActiveByStudentId(student.getId());

            if (allocation == null) {
                req.setAttribute("error", "您当前没有住宿，无法提交报修");
                doGet(req, resp);
                return;
            }

            Repair repair = new Repair();
            repair.setStudentId(student.getId());
            repair.setDormitoryId(allocation.getDormitoryId());

            Integer repairType = parseIntParam(req, "repairType", "报修类型");
            if (repairType == null) { doGet(req, resp); return; }
            repair.setRepairType(repairType);

            repair.setTitle(req.getParameter("title"));
            repair.setDescription(req.getParameter("description"));
            repair.setContactPhone(req.getParameter("contactPhone"));

            repairDao.insert(repair);
            resp.sendRedirect(req.getContextPath() + "/student/repair");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/student/repair");
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
