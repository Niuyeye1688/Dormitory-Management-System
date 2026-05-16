package com.dormitory.dao.impl;

import com.dormitory.dao.BuildingDao;
import com.dormitory.model.Building;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuildingDaoImpl implements BuildingDao {

    private static final Logger logger = LoggerFactory.getLogger(BuildingDaoImpl.class);

    @Override
    public Building findById(Integer id) {
        String sql = "SELECT * FROM building WHERE id = ?";
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
    public int insert(Building building) {
        String sql = "INSERT INTO building (building_no, building_name, floors, total_rooms, room_capacity, building_type, manager_name, manager_phone, address) VALUES (?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            setParams(ps, building);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int update(Building building) {
        String sql = "UPDATE building SET building_no=?, building_name=?, floors=?, total_rooms=?, room_capacity=?, building_type=?, manager_name=?, manager_phone=?, address=?, status=? WHERE id=?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            setParams(ps, building);
            ps.setInt(10, building.getStatus());
            ps.setInt(11, building.getId());
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
        String sql = "DELETE FROM building WHERE id = ?";
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
    public List<Building> findAll() {
        String sql = "SELECT * FROM building ORDER BY id";
        return queryList(sql, null);
    }

    @Override
    public List<Building> findByType(Integer buildingType) {
        String sql = "SELECT * FROM building WHERE building_type = ? AND status = 1 ORDER BY id";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Building> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, buildingType);
            rs = ps.executeQuery();
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    private void setParams(PreparedStatement ps, Building b) throws SQLException {
        ps.setString(1, b.getBuildingNo());
        ps.setString(2, b.getBuildingName());
        ps.setInt(3, b.getFloors());
        ps.setInt(4, b.getTotalRooms());
        ps.setInt(5, b.getRoomCapacity());
        ps.setInt(6, b.getBuildingType());
        ps.setString(7, b.getManagerName());
        ps.setString(8, b.getManagerPhone());
        ps.setString(9, b.getAddress());
    }

    private List<Building> queryList(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Building> list = new ArrayList<>();
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

    private Building mapResultSet(ResultSet rs) throws SQLException {
        Building b = new Building();
        b.setId(rs.getInt("id"));
        b.setBuildingNo(rs.getString("building_no"));
        b.setBuildingName(rs.getString("building_name"));
        b.setFloors(rs.getInt("floors"));
        b.setTotalRooms(rs.getInt("total_rooms"));
        b.setRoomCapacity(rs.getInt("room_capacity"));
        b.setBuildingType(rs.getInt("building_type"));
        b.setManagerName(rs.getString("manager_name"));
        b.setManagerPhone(rs.getString("manager_phone"));
        b.setAddress(rs.getString("address"));
        b.setStatus(rs.getInt("status"));
        b.setCreateTime(rs.getTimestamp("create_time"));
        b.setUpdateTime(rs.getTimestamp("update_time"));
        return b;
    }
}
