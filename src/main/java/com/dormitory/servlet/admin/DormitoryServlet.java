package com.dormitory.servlet.admin;

import com.dormitory.dao.BuildingDao;
import com.dormitory.dao.DormitoryDao;
import com.dormitory.dao.impl.BuildingDaoImpl;
import com.dormitory.dao.impl.DormitoryDaoImpl;
import com.dormitory.model.Building;
import com.dormitory.model.Dormitory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DormitoryServlet extends HttpServlet {

    private final BuildingDao buildingDao = new BuildingDaoImpl();
    private final DormitoryDao dormitoryDao = new DormitoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            List<Building> buildings = buildingDao.findAll();
            req.setAttribute("buildings", buildings);
            req.getRequestDispatcher("/WEB-INF/views/admin/dormitory_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Dormitory dormitory = dormitoryDao.findById(Integer.parseInt(id));
                req.setAttribute("dormitory", dormitory);
            }
            List<Building> buildings = buildingDao.findAll();
            req.setAttribute("buildings", buildings);
            req.getRequestDispatcher("/WEB-INF/views/admin/dormitory_form.jsp").forward(req, resp);
            return;
        }

        String buildingId = req.getParameter("buildingId");
        String keyword = req.getParameter("keyword");
        Integer bid = null;
        if (buildingId != null && !buildingId.isEmpty()) {
            bid = Integer.parseInt(buildingId);
        }

        List<Dormitory> list = dormitoryDao.findByCondition(bid, keyword);
        List<Building> buildings = buildingDao.findAll();

        req.setAttribute("list", list);
        req.setAttribute("buildings", buildings);
        req.setAttribute("selectedBuildingId", buildingId);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/admin/dormitory_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            Dormitory d = new Dormitory();
            String idStr = req.getParameter("id");

            Integer buildingId = parseIntParam(req, "buildingId", "宿舍楼");
            if (buildingId == null) { doGet(req, resp); return; }
            d.setBuildingId(buildingId);

            d.setRoomNo(req.getParameter("roomNo"));

            Integer floor = parseIntParam(req, "floor", "楼层");
            if (floor == null) { doGet(req, resp); return; }
            d.setFloor(floor);

            Integer capacity = parseIntParam(req, "capacity", "床位数");
            if (capacity == null) { doGet(req, resp); return; }
            d.setCapacity(capacity);

            Integer roomType = parseIntParam(req, "roomType", "房型");
            if (roomType == null) { doGet(req, resp); return; }
            d.setRoomType(roomType);

            if (idStr != null && !idStr.isEmpty()) {
                Integer id = parseIntParam(req, "id", "ID");
                if (id == null) { doGet(req, resp); return; }
                d.setId(id);

                Integer status = parseIntParam(req, "status", "状态");
                if (status == null) { doGet(req, resp); return; }
                d.setStatus(status);
                dormitoryDao.update(d);
            } else {
                d.setStatus(1);
                d.setCurrentCount(0);
                dormitoryDao.insert(d);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/dormitory");
            return;
        }

        if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                dormitoryDao.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/dormitory");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/dormitory");
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
