package com.dormitory.dao.impl;

import com.dormitory.dao.AnnouncementDao;
import com.dormitory.model.Announcement;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDaoImpl implements AnnouncementDao {

    private static final Logger logger = LoggerFactory.getLogger(AnnouncementDaoImpl.class);

    @Override
    public Announcement findById(Integer id) {
        String sql = "SELECT a.*, ad.real_name as publisher_name FROM announcement a LEFT JOIN admin ad ON a.publisher_id = ad.id WHERE a.id = ?";
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
    public int insert(Announcement announcement) {
        String sql = "INSERT INTO announcement (title, content, publisher_id, is_top, status, publish_time) VALUES (?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setInt(3, announcement.getPublisherId());
            ps.setInt(4, announcement.getIsTop());
            ps.setInt(5, announcement.getStatus());
            if (announcement.getPublishTime() != null) {
                ps.setTimestamp(6, announcement.getPublishTime());
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int update(Announcement announcement) {
        String sql = "UPDATE announcement SET title=?, content=?, is_top=?, status=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getContent());
            ps.setInt(3, announcement.getIsTop());
            ps.setInt(4, announcement.getStatus());
            ps.setInt(5, announcement.getId());
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
        String sql = "DELETE FROM announcement WHERE id = ?";
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
    public int incrementViewCount(Integer id) {
        String sql = "UPDATE announcement SET view_count = view_count + 1 WHERE id = ?";
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
    public List<Announcement> findAllPublished() {
        String sql = "SELECT a.*, ad.real_name as publisher_name FROM announcement a LEFT JOIN admin ad ON a.publisher_id = ad.id WHERE a.status = 1 ORDER BY a.is_top DESC, a.publish_time DESC";
        return queryList(sql, null);
    }

    @Override
    public List<Announcement> findAll() {
        String sql = "SELECT a.*, ad.real_name as publisher_name FROM announcement a LEFT JOIN admin ad ON a.publisher_id = ad.id ORDER BY a.create_time DESC";
        return queryList(sql, null);
    }

    @Override
    public int count(String keyword) {
        String sql = "SELECT COUNT(*) FROM announcement WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND title LIKE ?";
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
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
    public List<Announcement> findPage(String keyword, int offset, int pageSize) {
        String sql = "SELECT a.*, ad.real_name as publisher_name FROM announcement a LEFT JOIN admin ad ON a.publisher_id = ad.id WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND a.title LIKE ?";
        }
        sql += " ORDER BY a.create_time DESC LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Announcement> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(idx++, "%" + keyword + "%");
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

    private List<Announcement> queryList(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Announcement> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
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

    private Announcement mapResultSet(ResultSet rs) throws SQLException {
        Announcement a = new Announcement();
        a.setId(rs.getInt("id"));
        a.setTitle(rs.getString("title"));
        a.setContent(rs.getString("content"));
        a.setPublisherId((Integer) rs.getObject("publisher_id"));
        a.setIsTop(rs.getInt("is_top"));
        a.setViewCount(rs.getInt("view_count"));
        a.setStatus(rs.getInt("status"));
        a.setPublishTime(rs.getTimestamp("publish_time"));
        a.setPublisherName(rs.getString("publisher_name"));
        a.setCreateTime(rs.getTimestamp("create_time"));
        a.setUpdateTime(rs.getTimestamp("update_time"));
        return a;
    }
}
