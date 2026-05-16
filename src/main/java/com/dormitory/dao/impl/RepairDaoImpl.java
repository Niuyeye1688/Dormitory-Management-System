package com.dormitory.dao.impl;

import com.dormitory.dao.RepairDao;
import com.dormitory.model.Repair;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepairDaoImpl implements RepairDao {

    private static final Logger logger = LoggerFactory.getLogger(RepairDaoImpl.class);

    @Override
    public Repair findById(Integer id) {
        String sql = "SELECT r.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM repair r JOIN student s ON r.student_id = s.id JOIN dormitory d ON r.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE r.id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public int insert(Repair repair) {
        String sql = "INSERT INTO repair (student_id, dormitory_id, repair_type, title, description, contact_phone) VALUES (?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, repair.getStudentId());
            ps.setInt(2, repair.getDormitoryId());
            ps.setInt(3, repair.getRepairType());
            ps.setString(4, repair.getTitle());
            ps.setString(5, repair.getDescription());
            ps.setString(6, repair.getContactPhone());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int update(Repair repair) {
        String sql = "UPDATE repair SET repair_type=?, title=?, description=?, contact_phone=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, repair.getRepairType());
            ps.setString(2, repair.getTitle());
            ps.setString(3, repair.getDescription());
            ps.setString(4, repair.getContactPhone());
            ps.setInt(5, repair.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int updateStatus(Integer id, Integer status, String handlerName, String handleResult) {
        String sql = "UPDATE repair SET status=?, handler_name=?, handle_result=?, handle_time=NOW() WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setString(2, handlerName);
            ps.setString(3, handleResult);
            ps.setInt(4, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM repair WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int count(Integer status, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM repair r JOIN student s ON r.student_id = s.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (status != null) {
            sql.append(" AND r.status = ?");
            params.add(status);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (r.title LIKE ? OR s.name LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return 0;
    }

    @Override
    public List<Repair> findPage(Integer status, String keyword, int offset, int pageSize) {
        StringBuilder sql = new StringBuilder("SELECT r.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM repair r JOIN student s ON r.student_id = s.id JOIN dormitory d ON r.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (status != null) {
            sql.append(" AND r.status = ?");
            params.add(status);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (r.title LIKE ? OR s.name LIKE ?)");
            params.add("%" + keyword + "%");
            params.add("%" + keyword + "%");
        }
        sql.append(" ORDER BY r.create_time DESC LIMIT ?, ?");
        params.add(offset);
        params.add(pageSize);
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Repair> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<Repair> findByStudentId(Integer studentId) {
        String sql = "SELECT r.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM repair r JOIN student s ON r.student_id = s.id JOIN dormitory d ON r.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE r.student_id = ? ORDER BY r.create_time DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Repair> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    private Repair mapResultSet(ResultSet rs) throws SQLException {
        Repair r = new Repair();
        r.setId(rs.getInt("id"));
        r.setStudentId(rs.getInt("student_id"));
        r.setDormitoryId(rs.getInt("dormitory_id"));
        r.setRepairType(rs.getInt("repair_type"));
        r.setTitle(rs.getString("title"));
        r.setDescription(rs.getString("description"));
        r.setImageUrls(rs.getString("image_urls"));
        r.setContactPhone(rs.getString("contact_phone"));
        r.setStatus(rs.getInt("status"));
        r.setHandlerName(rs.getString("handler_name"));
        r.setHandleResult(rs.getString("handle_result"));
        r.setHandleTime(rs.getTimestamp("handle_time"));
        r.setStudentNo(rs.getString("student_no"));
        r.setStudentName(rs.getString("student_name"));
        r.setRoomNo(rs.getString("room_no"));
        r.setBuildingNo(rs.getString("building_no"));
        r.setCreateTime(rs.getTimestamp("create_time"));
        r.setUpdateTime(rs.getTimestamp("update_time"));
        return r;
    }
}
