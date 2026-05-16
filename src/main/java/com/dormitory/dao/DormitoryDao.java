package com.dormitory.dao;

import com.dormitory.model.Dormitory;

import java.sql.Connection;
import java.util.List;

public interface DormitoryDao {

    Dormitory findById(Integer id);

    Dormitory findById(Integer id, Connection conn);

    int insert(Dormitory dormitory);

    int update(Dormitory dormitory);

    int delete(Integer id);

    int updateCurrentCount(Integer id, int delta);

    int updateCurrentCount(Integer id, int delta, Connection conn);

    List<Dormitory> findAll();

    List<Dormitory> findByBuildingId(Integer buildingId);

    List<Dormitory> findAvailable();

    List<Dormitory> findByCondition(Integer buildingId, String keyword);
}
