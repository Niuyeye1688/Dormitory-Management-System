package com.dormitory.dao;

import com.dormitory.model.Repair;

import java.util.List;

public interface RepairDao {

    Repair findById(Integer id);

    int insert(Repair repair);

    int update(Repair repair);

    int updateStatus(Integer id, Integer status, String handlerName, String handleResult);

    int delete(Integer id);

    int count(Integer status, String keyword);

    List<Repair> findPage(Integer status, String keyword, int offset, int pageSize);

    List<Repair> findByStudentId(Integer studentId);
}
