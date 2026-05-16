package com.dormitory.servlet.admin;

import com.dormitory.dao.AnnouncementDao;
import com.dormitory.dao.impl.AnnouncementDaoImpl;
import com.dormitory.model.Admin;
import com.dormitory.model.Announcement;
import com.dormitory.util.Pagination;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class AnnouncementServlet extends HttpServlet {

    private final AnnouncementDao announcementDao = new AnnouncementDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/admin/announcement_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Announcement announcement = announcementDao.findById(Integer.parseInt(id));
                req.setAttribute("announcement", announcement);
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/announcement_form.jsp").forward(req, resp);
            return;
        }

        String keyword = req.getParameter("keyword");
        int page = 1;
        int pageSize = 10;
        try {
            page = Integer.parseInt(req.getParameter("page"));
        } catch (Exception ignored) {
        }

        int totalCount = announcementDao.count(keyword);
        List<Announcement> list = announcementDao.findPage(keyword, (page - 1) * pageSize, pageSize);
        Pagination<Announcement> pagination = new Pagination<>(page, pageSize, totalCount, list);

        req.setAttribute("pagination", pagination);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/announcement_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            HttpSession session = req.getSession();
            Admin admin = (Admin) session.getAttribute("admin");

            Announcement announcement = new Announcement();
            String idStr = req.getParameter("id");
            announcement.setTitle(req.getParameter("title"));
            announcement.setContent(req.getParameter("content"));
            announcement.setIsTop("1".equals(req.getParameter("isTop")) ? 1 : 0);

            Integer status = parseIntParam(req, "status", "状态");
            if (status == null) { doGet(req, resp); return; }
            announcement.setStatus(status);

            if (idStr != null && !idStr.isEmpty()) {
                Integer id = parseIntParam(req, "id", "ID");
                if (id == null) { doGet(req, resp); return; }
                announcement.setId(id);
                announcementDao.update(announcement);
            } else {
                announcement.setPublisherId(admin.getId());
                if (announcement.getStatus() == 1) {
                    announcement.setPublishTime(new Timestamp(System.currentTimeMillis()));
                }
                announcementDao.insert(announcement);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/announcement");
            return;
        }

        if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                announcementDao.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/announcement");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/announcement");
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
