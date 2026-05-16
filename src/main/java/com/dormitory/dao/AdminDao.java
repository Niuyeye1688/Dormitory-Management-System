package com.dormitory.dao;

import com.dormitory.model.Admin;

public interface AdminDao {

    Admin findByUsername(String username);

    Admin findById(Integer id);

    int update(Admin admin);

    int updateLastLoginTime(Integer id);

    int updatePassword(Integer id, String password);
}
