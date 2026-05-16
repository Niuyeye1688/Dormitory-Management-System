package com.dormitory.servlet.student;

import com.dormitory.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDormitoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        com.dormitory.model.Student student = (com.dormitory.model.Student) session.getAttribute("student");
        Integer studentId = student != null ? student.getId() : null;

        Map<String, Object> dormitoryInfo = new HashMap<>();
        List<Map<String, Object>> roommates = new ArrayList<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            String sql = "SELECT d.*, b.building_no, b.building_name, a.bed_no, a.check_in_date " +
                    "FROM allocation a " +
                    "JOIN dormitory d ON a.dormitory_id = d.id " +
                    "JOIN building b ON d.building_id = b.id " +
                    "WHERE a.student_id = ? AND a.status = 1";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();

            if (rs.next()) {
                dormitoryInfo.put("buildingNo", rs.getString("building_no"));
                dormitoryInfo.put("buildingName", rs.getString("building_name"));
                dormitoryInfo.put("roomNo", rs.getString("room_no"));
                dormitoryInfo.put("floor", rs.getInt("floor"));
                dormitoryInfo.put("bedNo", rs.getInt("bed_no"));
                dormitoryInfo.put("checkInDate", rs.getDate("check_in_date"));
                dormitoryInfo.put("capacity", rs.getInt("capacity"));
                dormitoryInfo.put("currentCount", rs.getInt("current_count"));

                int dormitoryId = rs.getInt("dormitory_id");
                DBUtil.close(null, ps, rs);

                sql = "SELECT s.student_no, s.name, s.gender, s.phone, a.bed_no " +
                        "FROM allocation a " +
                        "JOIN student s ON a.student_id = s.id " +
                        "WHERE a.dormitory_id = ? AND a.status = 1 AND a.student_id != ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, dormitoryId);
                ps.setInt(2, studentId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Map<String, Object> roommate = new HashMap<>();
                    roommate.put("studentNo", rs.getString("student_no"));
                    roommate.put("name", rs.getString("name"));
                    roommate.put("gender", rs.getInt("gender") == 1 ? "男" : "女");
                    roommate.put("phone", rs.getString("phone"));
                    roommate.put("bedNo", rs.getInt("bed_no"));
                    roommates.add(roommate);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        req.setAttribute("dormitoryInfo", dormitoryInfo);
        req.setAttribute("roommates", roommates);
        req.getRequestDispatcher("/WEB-INF/views/student/my_dormitory.jsp").forward(req, resp);
    }
}
