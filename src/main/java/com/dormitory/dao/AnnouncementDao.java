package com.dormitory.dao;

import com.dormitory.model.Announcement;

import java.util.List;

public interface AnnouncementDao {

    Announcement findById(Integer id);

    int insert(Announcement announcement);

    int update(Announcement announcement);

    int delete(Integer id);

    int incrementViewCount(Integer id);

    List<Announcement> findAllPublished();

    List<Announcement> findAll();

    int count(String keyword);

    List<Announcement> findPage(String keyword, int offset, int pageSize);
}
