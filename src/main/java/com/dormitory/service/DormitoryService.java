package com.dormitory.service;

import com.dormitory.dao.*;
import com.dormitory.dao.impl.*;
import com.dormitory.model.*;

import java.util.List;

public class DormitoryService {

    private final BuildingDao buildingDao = new BuildingDaoImpl();
    private final DormitoryDao dormitoryDao = new DormitoryDaoImpl();
    private final StudentDao studentDao = new StudentDaoImpl();
    private final AllocationDao allocationDao = new AllocationDaoImpl();

    public List<Building> getAllBuildings() {
        return buildingDao.findAll();
    }

    public List<Dormitory> getDormitoriesByBuilding(Integer buildingId) {
        return dormitoryDao.findByBuildingId(buildingId);
    }

    public List<Dormitory> getAvailableDormitories() {
        return dormitoryDao.findAvailable();
    }

    public List<Student> getStudentsWithoutDormitory() {
        List<Student> all = studentDao.findAll();
        all.removeIf(s -> {
            Allocation a = allocationDao.findActiveByStudentId(s.getId());
            return a != null;
        });
        return all;
    }
}
