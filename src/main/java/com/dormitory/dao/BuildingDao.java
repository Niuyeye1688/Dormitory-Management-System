package com.dormitory.dao;

import com.dormitory.model.Building;

import java.util.List;

public interface BuildingDao {

    Building findById(Integer id);

    int insert(Building building);

    int update(Building building);

    int delete(Integer id);

    List<Building> findAll();

    List<Building> findByType(Integer buildingType);
}
