package com.dormitory.dao.impl;

import com.dormitory.dao.StudentDao;
import com.dormitory.model.Student;
import com.dormitory.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    @Override
    public Student findByStudentNo(String studentNo) {
        String sql = "SELECT * FROM student WHERE student_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public Student findById(Integer id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public int insert(Student student) {
        String sql = "INSERT INTO student (student_no, password, name, gender, phone, email, college, major, class_name, enrollment_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getStudentNo());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getName());
            ps.setInt(4, student.getGender());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getEmail());
            ps.setString(7, student.getCollege());
            ps.setString(8, student.getMajor());
            ps.setString(9, student.getClassName());
            ps.setObject(10, student.getEnrollmentYear());
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    @Override
    public int update(Student student) {
        String sql = "UPDATE student SET name = ?, gender = ?, phone = ?, email = ?, college = ?, major = ?, class_name = ?, enrollment_year = ?, status = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getGender());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getCollege());
            ps.setString(6, student.getMajor());
            ps.setString(7, student.getClassName());
            ps.setObject(8, student.getEnrollmentYear());
            ps.setInt(9, student.getStatus());
            ps.setInt(10, student.getId());
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
        String sql = "DELETE FROM student WHERE id = ?";
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
    public int count(String keyword) {
        String sql = "SELECT COUNT(*) FROM student WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (student_no LIKE ? OR name LIKE ? OR college LIKE ?)";
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            if (keyword != null && !keyword.isEmpty()) {
                String param = "%" + keyword + "%";
                ps.setString(1, param);
                ps.setString(2, param);
                ps.setString(3, param);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return 0;
    }

    @Override
    public List<Student> findPage(String keyword, int offset, int pageSize) {
        String sql = "SELECT * FROM student WHERE 1=1";
        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND (student_no LIKE ? OR name LIKE ? OR college LIKE ?)";
        }
        sql += " ORDER BY create_time DESC LIMIT ?, ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String param = "%" + keyword + "%";
                ps.setString(idx++, param);
                ps.setString(idx++, param);
                ps.setString(idx++, param);
            }
            ps.setInt(idx++, offset);
            ps.setInt(idx, pageSize);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<Student> findAll() {
        String sql = "SELECT * FROM student ORDER BY create_time DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> list = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public int updatePassword(Integer id, String password) {
        String sql = "UPDATE student SET password = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        } finally {
            DBUtil.close(conn, ps);
        }
        return 0;
    }

    private Student mapResultSet(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setStudentNo(rs.getString("student_no"));
        student.setPassword(rs.getString("password"));
        student.setName(rs.getString("name"));
        student.setGender(rs.getInt("gender"));
        student.setPhone(rs.getString("phone"));
        student.setEmail(rs.getString("email"));
        student.setCollege(rs.getString("college"));
        student.setMajor(rs.getString("major"));
        student.setClassName(rs.getString("class_name"));
        student.setEnrollmentYear((Integer) rs.getObject("enrollment_year"));
        student.setStatus(rs.getInt("status"));
        student.setCreateTime(rs.getTimestamp("create_time"));
        student.setUpdateTime(rs.getTimestamp("update_time"));
        return student;
    }
}
