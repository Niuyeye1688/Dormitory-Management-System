package com.dormitory.servlet.admin;

import com.dormitory.dao.RepairDao;
import com.dormitory.dao.impl.RepairDaoImpl;
import com.dormitory.model.Repair;
import com.dormitory.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class RepairManageServlet extends HttpServlet {

    private final RepairDao repairDao = new RepairDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("detail".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Repair repair = repairDao.findById(Integer.parseInt(id));
                req.setAttribute("repair", repair);
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/repair_detail.jsp").forward(req, resp);
            return;
        }

        Integer status = null;
        String statusStr = req.getParameter("status");
        if (statusStr != null && !statusStr.isEmpty()) {
            status = Integer.parseInt(statusStr);
        }
        String keyword = req.getParameter("keyword");
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }

        int totalCount = repairDao.count(status, keyword);
        List<Repair> list = repairDao.findPage(status, keyword, (page - 1) * pageSize, pageSize);
        Pagination<Repair> pagination = new Pagination<>(page, pageSize, totalCount, list);

        req.setAttribute("pagination", pagination);
        req.setAttribute("keyword", keyword);
        req.setAttribute("status", statusStr);
        req.getRequestDispatcher("/WEB-INF/views/admin/repair_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("handle".equals(action)) {
            String id = req.getParameter("id");

            Integer status = parseIntParam(req, "status", "处理状态");
            if (status == null) { doGet(req, resp); return; }

            String handleResult = req.getParameter("handleResult");
            HttpSession session = req.getSession();
            com.dormitory.model.Admin admin = (com.dormitory.model.Admin) session.getAttribute("admin");
            String handlerName = admin != null ? admin.getRealName() : "Admin";

            if (id != null) {
                Integer idVal = parseIntParam(req, "id", "ID");
                if (idVal == null) { doGet(req, resp); return; }
                repairDao.updateStatus(idVal, status, handlerName, handleResult);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/repair");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/repair");
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
