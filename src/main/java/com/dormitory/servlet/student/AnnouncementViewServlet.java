package com.dormitory.servlet.student;

import com.dormitory.dao.AnnouncementDao;
import com.dormitory.dao.impl.AnnouncementDaoImpl;
import com.dormitory.model.Announcement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AnnouncementViewServlet extends HttpServlet {

    private final AnnouncementDao announcementDao = new AnnouncementDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("detail".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Announcement announcement = announcementDao.findById(Integer.parseInt(id));
                if (announcement != null) {
                    announcementDao.incrementViewCount(announcement.getId());
                    announcement.setViewCount(announcement.getViewCount() + 1);
                }
                req.setAttribute("announcement", announcement);
            }
            req.getRequestDispatcher("/WEB-INF/views/student/announcement_detail.jsp").forward(req, resp);
            return;
        }

        List<Announcement> list = announcementDao.findAllPublished();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/student/announcement_list.jsp").forward(req, resp);
    }
}
