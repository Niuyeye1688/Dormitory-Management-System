package com.dormitory.dao.impl;

import com.dormitory.dao.AdminDao;
import com.dormitory.model.Admin;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {

    private static final Logger logger = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Override
    public Admin findByUsername(String username) {
        String sql = "SELECT * FROM admin WHERE username = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public Admin findById(Integer id) {
        String sql = "SELECT * FROM admin WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public int update(Admin admin) {
        String sql = "UPDATE admin SET real_name = ?, phone = ?, email = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getRealName());
            ps.setString(2, admin.getPhone());
            ps.setString(3, admin.getEmail());
            ps.setInt(4, admin.getId());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int updateLastLoginTime(Integer id) {
        String sql = "UPDATE admin SET last_login_time = NOW() WHERE id = ?";
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
    public int updatePassword(Integer id, String password) {
        String sql = "UPDATE admin SET password = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    private Admin mapResultSet(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setId(rs.getInt("id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setRealName(rs.getString("real_name"));
        admin.setPhone(rs.getString("phone"));
        admin.setEmail(rs.getString("email"));
        admin.setStatus(rs.getInt("status"));
        admin.setLastLoginTime(rs.getTimestamp("last_login_time"));
        admin.setCreateTime(rs.getTimestamp("create_time"));
        admin.setUpdateTime(rs.getTimestamp("update_time"));
        return admin;
    }
}
