package com.dormitory.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Allocation {

    private Integer id;
    private Integer studentId;
    private Integer dormitoryId;
    private Integer bedNo;
    private Date checkInDate;
    private Date checkOutDate;
    private Integer status;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 扩展字段
    private String studentNo;
    private String studentName;
    private String roomNo;
    private String buildingNo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getDormitoryId() { return dormitoryId; }
    public void setDormitoryId(Integer dormitoryId) { this.dormitoryId = dormitoryId; }

    public Integer getBedNo() { return bedNo; }
    public void setBedNo(Integer bedNo) { this.bedNo = bedNo; }

    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }

    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public Timestamp getUpdateTime() { return updateTime; }
    public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getStatusStr() {
        return status != null && status == 1 ? "在住" : "已退宿";
    }
}
