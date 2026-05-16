package com.dormitory.dao.impl;

import com.dormitory.dao.AllocationDao;
import com.dormitory.model.Allocation;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AllocationDaoImpl implements AllocationDao {

    private static final Logger logger = LoggerFactory.getLogger(AllocationDaoImpl.class);

    @Override
    public Allocation findById(Integer id) {
        String sql = "SELECT a.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM allocation a JOIN student s ON a.student_id = s.id JOIN dormitory d ON a.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE a.id = ?";
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
    public Allocation findActiveByStudentId(Integer studentId) {
        String sql = "SELECT a.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM allocation a JOIN student s ON a.student_id = s.id JOIN dormitory d ON a.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE a.student_id = ? AND a.status = 1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
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
    public Allocation findActiveByStudentId(Integer studentId, Connection conn) {
        String sql = "SELECT a.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM allocation a JOIN student s ON a.student_id = s.id JOIN dormitory d ON a.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE a.student_id = ? AND a.status = 1 FOR UPDATE";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(null, ps, rs);
        }
        return null;
    }

    @Override
    public int insert(Allocation allocation) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return insert(allocation, conn);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, (PreparedStatement) null);
        }
        return 0;
    }

    @Override
    public int insert(Allocation allocation, Connection conn) {
        String sql = "INSERT INTO allocation (student_id, dormitory_id, bed_no, check_in_date, remark) VALUES (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, allocation.getStudentId());
            ps.setInt(2, allocation.getDormitoryId());
            ps.setInt(3, allocation.getBedNo());
            ps.setDate(4, allocation.getCheckInDate());
            ps.setString(5, allocation.getRemark());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(null, ps);
        }
        return 0;
    }

    @Override
    public int update(Allocation allocation) {
        String sql = "UPDATE allocation SET dormitory_id=?, bed_no=?, remark=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, allocation.getDormitoryId());
            ps.setInt(2, allocation.getBedNo());
            ps.setString(3, allocation.getRemark());
            ps.setInt(4, allocation.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int checkout(Integer id, String remark) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return checkout(id, remark, conn);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, (PreparedStatement) null);
        }
        return 0;
    }

    @Override
    public int checkout(Integer id, String remark, Connection conn) {
        String sql = "UPDATE allocation SET status=0, check_out_date=CURDATE(), remark=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, remark);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(null, ps);
        }
        return 0;
    }

    @Override
    public int delete(Integer id) {
        String sql = "DELETE FROM allocation WHERE id = ?";
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
    public int count(String keyword) {
        String sql = "SELECT COUNT(*) FROM allocation a JOIN student s ON a.student_id = s.id WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (s.student_no LIKE ? OR s.name LIKE ?)";
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                String p = "%" + keyword + "%";
                ps.setString(1, p);
                ps.setString(2, p);
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
    public List<Allocation> findPage(String keyword, int offset, int pageSize) {
        String sql = "SELECT a.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM allocation a JOIN student s ON a.student_id = s.id JOIN dormitory d ON a.dormitory_id = d.id JOIN building b ON d.building_id = b.id WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (s.student_no LIKE ? OR s.name LIKE ?)";
        }
        sql += " ORDER BY a.create_time DESC LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Allocation> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String p = "%" + keyword + "%";
                ps.setString(idx++, p);
                ps.setString(idx++, p);
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, pageSize);
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
    public List<Allocation> findAll() {
        String sql = "SELECT a.*, s.student_no, s.name as student_name, d.room_no, b.building_no FROM allocation a JOIN student s ON a.student_id = s.id JOIN dormitory d ON a.dormitory_id = d.id JOIN building b ON d.building_id = b.id ORDER BY a.create_time DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Allocation> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    private Allocation mapResultSet(ResultSet rs) throws SQLException {
        Allocation a = new Allocation();
        a.setId(rs.getInt("id"));
        a.setStudentId(rs.getInt("student_id"));
        a.setDormitoryId(rs.getInt("dormitory_id"));
        a.setBedNo(rs.getInt("bed_no"));
        a.setCheckInDate(rs.getDate("check_in_date"));
        a.setCheckOutDate(rs.getDate("check_out_date"));
        a.setStatus(rs.getInt("status"));
        a.setRemark(rs.getString("remark"));
        a.setStudentNo(rs.getString("student_no"));
        a.setStudentName(rs.getString("student_name"));
        a.setRoomNo(rs.getString("room_no"));
        a.setBuildingNo(rs.getString("building_no"));
        a.setCreateTime(rs.getTimestamp("create_time"));
        a.setUpdateTime(rs.getTimestamp("update_time"));
        return a;
    }
}
