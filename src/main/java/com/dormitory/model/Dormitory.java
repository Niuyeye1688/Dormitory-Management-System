package com.dormitory.model;

import java.sql.Timestamp;

public class Dormitory {

    private Integer id;
    private Integer buildingId;
    private String roomNo;
    private Integer floor;
    private Integer capacity;
    private Integer currentCount;
    private Integer roomType;
    private Integer leaderStudentId;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 扩展字段
    private String buildingNo;
    private String buildingName;
    private String leaderName;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getBuildingId() { return buildingId; }
    public void setBuildingId(Integer buildingId) { this.buildingId = buildingId; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getCurrentCount() { return currentCount; }
    public void setCurrentCount(Integer currentCount) { this.currentCount = currentCount; }

    public Integer getRoomType() { return roomType; }
    public void setRoomType(Integer roomType) { this.roomType = roomType; }

    public Integer getLeaderStudentId() { return leaderStudentId; }
    public void setLeaderStudentId(Integer leaderStudentId) { this.leaderStudentId = leaderStudentId; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public Timestamp getUpdateTime() { return updateTime; }
    public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }

    public String getRoomTypeStr() {
        if (roomType == null) return "四人间";
        switch (roomType) {
            case 1: return "六人间";
            case 2: return "双人间";
            default: return "四人间";
        }
    }

    public String getStatusStr() {
        return status != null && status == 1 ? "可用" : "维修中";
    }

    public int getEmptyBeds() {
        int cap = capacity != null ? capacity : 0;
        int cur = currentCount != null ? currentCount : 0;
        return cap - cur;
    }
}
