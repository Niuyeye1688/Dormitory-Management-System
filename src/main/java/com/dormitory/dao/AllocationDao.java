package com.dormitory.dao;

import com.dormitory.model.Allocation;

import java.sql.Connection;
import java.util.List;

public interface AllocationDao {

    Allocation findById(Integer id);

    Allocation findActiveByStudentId(Integer studentId);

    Allocation findActiveByStudentId(Integer studentId, Connection conn);

    int insert(Allocation allocation);

    int insert(Allocation allocation, Connection conn);

    int update(Allocation allocation);

    int checkout(Integer id, String remark);

    int checkout(Integer id, String remark, Connection conn);

    int delete(Integer id);

    int count(String keyword);

    List<Allocation> findPage(String keyword, int offset, int pageSize);

    List<Allocation> findAll();
}
