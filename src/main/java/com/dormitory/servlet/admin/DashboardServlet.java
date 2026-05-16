package com.dormitory.servlet.admin;

import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DashboardServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> stats = new HashMap<>();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();

            // 学生总数
            ps = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE status = 1");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("studentCount", rs.getInt(1));
            DBUtil.close(null, ps, rs);

            // 宿舍楼总数
            ps = conn.prepareStatement("SELECT COUNT(*) FROM building WHERE status = 1");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("buildingCount", rs.getInt(1));
            DBUtil.close(null, ps, rs);

            // 宿舍房间总数
            ps = conn.prepareStatement("SELECT COUNT(*) FROM dormitory WHERE status = 1");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("dormitoryCount", rs.getInt(1));
            DBUtil.close(null, ps, rs);

            // 已入住人数
            ps = conn.prepareStatement("SELECT COUNT(*) FROM allocation WHERE status = 1");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("checkedInCount", rs.getInt(1));
            DBUtil.close(null, ps, rs);

            // 待处理报修
            ps = conn.prepareStatement("SELECT COUNT(*) FROM repair WHERE status = 0");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("pendingRepairCount", rs.getInt(1));
            DBUtil.close(null, ps, rs);

            // 空床位数
            ps = conn.prepareStatement("SELECT SUM(capacity - current_count) FROM dormitory WHERE status = 1");
            rs = ps.executeQuery();
            if (rs.next()) stats.put("emptyBeds", rs.getInt(1));

        } catch (SQLException e) {
            logger.error("获取仪表盘统计数据失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        req.setAttribute("studentCount", stats.get("studentCount"));
        req.setAttribute("buildingCount", stats.get("buildingCount"));
        req.setAttribute("dormitoryCount", stats.get("dormitoryCount"));
        req.setAttribute("checkedInCount", stats.get("checkedInCount"));
        req.setAttribute("pendingRepairCount", stats.get("pendingRepairCount"));
        req.setAttribute("emptyBeds", stats.get("emptyBeds"));
        req.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(req, resp);
    }
}
