package com.dormitory.dao;

import com.dormitory.model.Student;
import com.dormitory.util.Pagination;

import java.util.List;

public interface StudentDao {

    Student findByStudentNo(String studentNo);

    Student findById(Integer id);

    int insert(Student student);

    int update(Student student);

    int delete(Integer id);

    int count(String keyword);

    List<Student> findPage(String keyword, int offset, int pageSize);

    List<Student> findAll();

    int updatePassword(Integer id, String password);
}
