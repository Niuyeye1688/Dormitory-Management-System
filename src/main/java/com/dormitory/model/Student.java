package com.dormitory.model;

import java.sql.Timestamp;

public class Student {

    private Integer id;
    private String studentNo;
    private String password;
    private String name;
    private Integer gender;
    private String phone;
    private String email;
    private String college;
    private String major;
    private String className;
    private Integer enrollmentYear;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 扩展字段（非数据库字段）
    private String dormitoryInfo; // 当前宿舍信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(Integer enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getDormitoryInfo() {
        return dormitoryInfo;
    }

    public void setDormitoryInfo(String dormitoryInfo) {
        this.dormitoryInfo = dormitoryInfo;
    }

    public String getGenderStr() {
        return gender != null && gender == 1 ? "男" : "女";
    }

    public String getStatusStr() {
        return status != null && status == 1 ? "在校" : "离校";
    }
}
