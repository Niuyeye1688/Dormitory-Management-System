package com.dormitory.model;

import java.sql.Timestamp;

public class Repair {

    private Integer id;
    private Integer studentId;
    private Integer dormitoryId;
    private Integer repairType;
    private String title;
    private String description;
    private String imageUrls;
    private String contactPhone;
    private Integer status;
    private String handlerName;
    private String handleResult;
    private Timestamp handleTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 扩展字段
    private String studentName;
    private String studentNo;
    private String roomNo;
    private String buildingNo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getStudentId() { return studentId; }
    public void setStudentId(Integer studentId) { this.studentId = studentId; }

    public Integer getDormitoryId() { return dormitoryId; }
    public void setDormitoryId(Integer dormitoryId) { this.dormitoryId = dormitoryId; }

    public Integer getRepairType() { return repairType; }
    public void setRepairType(Integer repairType) { this.repairType = repairType; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrls() { return imageUrls; }
    public void setImageUrls(String imageUrls) { this.imageUrls = imageUrls; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getHandlerName() { return handlerName; }
    public void setHandlerName(String handlerName) { this.handlerName = handlerName; }

    public String getHandleResult() { return handleResult; }
    public void setHandleResult(String handleResult) { this.handleResult = handleResult; }

    public Timestamp getHandleTime() { return handleTime; }
    public void setHandleTime(Timestamp handleTime) { this.handleTime = handleTime; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public Timestamp getUpdateTime() { return updateTime; }
    public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getRepairTypeStr() {
        if (repairType == null) return "其他";
        switch (repairType) {
            case 0: return "水电";
            case 1: return "门窗";
            case 2: return "家具";
            case 3: return "网络";
            default: return "其他";
        }
    }

    public String getStatusStr() {
        if (status == null) return "待处理";
        switch (status) {
            case 1: return "处理中";
            case 2: return "已完成";
            case 3: return "已驳回";
            default: return "待处理";
        }
    }

    public String getStatusClass() {
        if (status == null) return "status-pending";
        switch (status) {
            case 1: return "status-processing";
            case 2: return "status-completed";
            case 3: return "status-rejected";
            default: return "status-pending";
        }
    }
}
