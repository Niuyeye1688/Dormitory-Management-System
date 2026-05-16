package com.dormitory.servlet.admin;

import com.dormitory.dao.BuildingDao;
import com.dormitory.dao.impl.BuildingDaoImpl;
import com.dormitory.model.Building;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BuildingServlet extends HttpServlet {

    private final BuildingDao buildingDao = new BuildingDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            req.getRequestDispatcher("/WEB-INF/views/admin/building_form.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                Building building = buildingDao.findById(Integer.parseInt(id));
                req.setAttribute("building", building);
            }
            req.getRequestDispatcher("/WEB-INF/views/admin/building_form.jsp").forward(req, resp);
            return;
        }

        List<Building> list = buildingDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/admin/building_list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            Building building = new Building();
            String idStr = req.getParameter("id");
            building.setBuildingNo(req.getParameter("buildingNo"));
            building.setBuildingName(req.getParameter("buildingName"));

            Integer floors = parseIntParam(req, "floors", "楼层数");
            if (floors == null) { doGet(req, resp); return; }
            building.setFloors(floors);

            Integer totalRooms = parseIntParam(req, "totalRooms", "总房间数");
            if (totalRooms == null) { doGet(req, resp); return; }
            building.setTotalRooms(totalRooms);

            Integer roomCapacity = parseIntParam(req, "roomCapacity", "标准入住人数");
            if (roomCapacity == null) { doGet(req, resp); return; }
            building.setRoomCapacity(roomCapacity);

            Integer buildingType = parseIntParam(req, "buildingType", "宿舍楼类型");
            if (buildingType == null) { doGet(req, resp); return; }
            building.setBuildingType(buildingType);

            building.setManagerName(req.getParameter("managerName"));
            building.setManagerPhone(req.getParameter("managerPhone"));
            building.setAddress(req.getParameter("address"));

            if (idStr != null && !idStr.isEmpty()) {
                Integer id = parseIntParam(req, "id", "ID");
                if (id == null) { doGet(req, resp); return; }
                building.setId(id);

                Integer status = parseIntParam(req, "status", "状态");
                if (status == null) { doGet(req, resp); return; }
                building.setStatus(status);
                buildingDao.update(building);
            } else {
                building.setStatus(1);
                buildingDao.insert(building);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/building");
            return;
        }

        if ("delete".equals(action)) {
            String id = req.getParameter("id");
            if (id != null) {
                buildingDao.delete(Integer.parseInt(id));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/building");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/building");
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
