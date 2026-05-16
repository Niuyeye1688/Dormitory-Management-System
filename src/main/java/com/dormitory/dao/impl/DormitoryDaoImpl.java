package com.dormitory.dao.impl;

import com.dormitory.dao.DormitoryDao;
import com.dormitory.model.Dormitory;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DormitoryDaoImpl implements DormitoryDao {

    private static final Logger logger = LoggerFactory.getLogger(DormitoryDaoImpl.class);

    @Override
    public Dormitory findById(Integer id) {
        String sql = "SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id WHERE d.id = ?";
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
    public Dormitory findById(Integer id, Connection conn) {
        String sql = "SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id WHERE d.id = ? FOR UPDATE";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
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
    public int insert(Dormitory d) {
        String sql = "INSERT INTO dormitory (building_id, room_no, floor, capacity, room_type) VALUES (?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, d.getBuildingId());
            ps.setString(2, d.getRoomNo());
            ps.setInt(3, d.getFloor());
            ps.setInt(4, d.getCapacity());
            ps.setInt(5, d.getRoomType());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int update(Dormitory d) {
        String sql = "UPDATE dormitory SET building_id=?, room_no=?, floor=?, capacity=?, room_type=?, leader_student_id=?, status=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, d.getBuildingId());
            ps.setString(2, d.getRoomNo());
            ps.setInt(3, d.getFloor());
            ps.setInt(4, d.getCapacity());
            ps.setInt(5, d.getRoomType());
            ps.setObject(6, d.getLeaderStudentId());
            ps.setInt(7, d.getStatus());
            ps.setInt(8, d.getId());
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
        String sql = "DELETE FROM dormitory WHERE id = ?";
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
    public int updateCurrentCount(Integer id, int delta) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            return updateCurrentCount(id, delta, conn);
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, (PreparedStatement) null);
        }
        return 0;
    }

    @Override
    public int updateCurrentCount(Integer id, int delta, Connection conn) {
        String sql = "UPDATE dormitory SET current_count = current_count + ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, delta);
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
    public List<Dormitory> findAll() {
        String sql = "SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id ORDER BY d.id";
        return queryList(sql, null);
    }

    @Override
    public List<Dormitory> findByBuildingId(Integer buildingId) {
        String sql = "SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id WHERE d.building_id = ? ORDER BY d.room_no";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Dormitory> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, buildingId);
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
    public List<Dormitory> findAvailable() {
        String sql = "SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id WHERE d.status = 1 AND d.current_count < d.capacity ORDER BY d.id";
        return queryList(sql, null);
    }

    @Override
    public List<Dormitory> findByCondition(Integer buildingId, String keyword) {
        StringBuilder sql = new StringBuilder("SELECT d.*, b.building_no, b.building_name FROM dormitory d JOIN building b ON d.building_id = b.id WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (buildingId != null) {
            sql.append(" AND d.building_id = ?");
            params.add(buildingId);
        }
        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND d.room_no LIKE ?");
            params.add("%" + keyword + "%");
        }
        sql.append(" ORDER BY d.id");
        return queryList(sql.toString(), params.toArray());
    }

    private List<Dormitory> queryList(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Dormitory> list = new ArrayList<>();
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

    private Dormitory mapResultSet(ResultSet rs) throws SQLException {
        Dormitory d = new Dormitory();
        d.setId(rs.getInt("id"));
        d.setBuildingId(rs.getInt("building_id"));
        d.setRoomNo(rs.getString("room_no"));
        d.setFloor(rs.getInt("floor"));
        d.setCapacity(rs.getInt("capacity"));
        d.setCurrentCount(rs.getInt("current_count"));
        d.setRoomType(rs.getInt("room_type"));
        d.setLeaderStudentId((Integer) rs.getObject("leader_student_id"));
        d.setStatus(rs.getInt("status"));
        d.setBuildingNo(rs.getString("building_no"));
        d.setBuildingName(rs.getString("building_name"));
        d.setCreateTime(rs.getTimestamp("create_time"));
        d.setUpdateTime(rs.getTimestamp("update_time"));
        return d;
    }
}
