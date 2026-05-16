package com.dormitory.model;

import java.sql.Timestamp;

public class Building {

    private Integer id;
    private String buildingNo;
    private String buildingName;
    private Integer floors;
    private Integer totalRooms;
    private Integer roomCapacity;
    private Integer buildingType;
    private String managerName;
    private String managerPhone;
    private String address;
    private Integer status;
    private Timestamp createTime;
    private Timestamp updateTime;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getBuildingNo() { return buildingNo; }
    public void setBuildingNo(String buildingNo) { this.buildingNo = buildingNo; }

    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String buildingName) { this.buildingName = buildingName; }

    public Integer getFloors() { return floors; }
    public void setFloors(Integer floors) { this.floors = floors; }

    public Integer getTotalRooms() { return totalRooms; }
    public void setTotalRooms(Integer totalRooms) { this.totalRooms = totalRooms; }

    public Integer getRoomCapacity() { return roomCapacity; }
    public void setRoomCapacity(Integer roomCapacity) { this.roomCapacity = roomCapacity; }

    public Integer getBuildingType() { return buildingType; }
    public void setBuildingType(Integer buildingType) { this.buildingType = buildingType; }

    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

    public String getManagerPhone() { return managerPhone; }
    public void setManagerPhone(String managerPhone) { this.managerPhone = managerPhone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public Timestamp getUpdateTime() { return updateTime; }
    public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

    public String getBuildingTypeStr() {
        return buildingType != null && buildingType == 1 ? "女寝" : "男寝";
    }

    public String getStatusStr() {
        return status != null && status == 1 ? "启用" : "停用";
    }
}
