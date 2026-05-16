package com.dormitory.servlet.admin;

import com.dormitory.dao.*;
import com.dormitory.dao.impl.*;
import com.dormitory.model.*;
import com.dormitory.service.AllocationService;
import com.dormitory.service.DormitoryService;
import com.dormitory.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class AllocationServlet extends HttpServlet {

    private final AllocationDao allocationDao = new AllocationDaoImpl();
    private final AllocationService allocationService = new AllocationService();
    private final DormitoryService dormitoryService = new DormitoryService();
    private final DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            List<Student> students = dormitoryService.getStudentsWithoutDormitory();
            List<Dormitory> dormitories = dormitoryDao.findAvailable();
            req.setAttribute("students", students);
            req.setAttribute("dormitories", dormitories);
            req.getRequestDispatcher("/WEB-INF/views/admin/allocation_form.jsp").forward(req, resp);
            return;
        }

        String keyword = req.getParameter("keyword");
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }

        int totalCount = allocationDao.count(keyword);
        List<Allocation> list = allocationDao.findPage(keyword, (page - 1) * pageSize, pageSize);
        Pagination<Allocation> pagination = new Pagination<>(page, pageSize, totalCount, list);

        req.setAttribute("pagination", pagination);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/allocation_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("allocate".equals(action)) {
            Allocation allocation = new Allocation();

            Integer studentId = parseIntParam(req, "studentId", "学生");
            if (studentId == null) { doGet(req, resp); return; }
            allocation.setStudentId(studentId);

            Integer dormitoryId = parseIntParam(req, "dormitoryId", "宿舍");
            if (dormitoryId == null) { doGet(req, resp); return; }
            allocation.setDormitoryId(dormitoryId);

            Integer bedNo = parseIntParam(req, "bedNo", "床位号");
            if (bedNo == null) { doGet(req, resp); return; }
            allocation.setBedNo(bedNo);

            String checkInDateStr = req.getParameter("checkInDate");
            if (checkInDateStr == null || checkInDateStr.trim().isEmpty()) {
                req.setAttribute("error", "入住日期不能为空");
                doGet(req, resp);
                return;
            }
            try {
                allocation.setCheckInDate(Date.valueOf(checkInDateStr));
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", "入住日期格式错误");
                doGet(req, resp);
                return;
            }
            allocation.setRemark(req.getParameter("remark"));

            boolean success = allocationService.allocate(allocation);
            if (!success) {
                req.setAttribute("error", "分配失败：宿舍已满或学生已有住宿");
                doGet(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/admin/allocation");
            return;
        }

        if ("checkout".equals(action)) {
            String id = req.getParameter("id");
            String remark = req.getParameter("remark");
            if (id != null) {
                Integer idVal = parseIntParam(req, "id", "ID");
                if (idVal == null) { doGet(req, resp); return; }
                allocationService.checkout(idVal, remark);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/allocation");
            return;
        }

        if ("transfer".equals(action)) {
            String id = req.getParameter("id");
            String newDormitoryId = req.getParameter("newDormitoryId");
            String bedNo = req.getParameter("bedNo");
            String remark = req.getParameter("remark");
            if (id != null && newDormitoryId != null) {
                Integer idVal = parseIntParam(req, "id", "ID");
                if (idVal == null) { doGet(req, resp); return; }
                Integer newDormId = parseIntParam(req, "newDormitoryId", "新宿舍");
                if (newDormId == null) { doGet(req, resp); return; }
                Integer bedNoVal = parseIntParam(req, "bedNo", "床位号");
                if (bedNoVal == null) { doGet(req, resp); return; }
                boolean success = allocationService.transfer(idVal, newDormId, bedNoVal, remark);
                if (!success) {
                    req.setAttribute("error", "调宿失败");
                    doGet(req, resp);
                    return;
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/allocation");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/allocation");
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
