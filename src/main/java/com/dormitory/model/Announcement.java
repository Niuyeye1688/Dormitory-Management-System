package com.dormitory.model;

import java.sql.Timestamp;

public class Announcement {

    private Integer id;
    private String title;
    private String content;
    private Integer publisherId;
    private Integer isTop;
    private Integer viewCount;
    private Integer status;
    private Timestamp publishTime;
    private Timestamp createTime;
    private Timestamp updateTime;

    // 扩展字段
    private String publisherName;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getPublisherId() { return publisherId; }
    public void setPublisherId(Integer publisherId) { this.publisherId = publisherId; }

    public Integer getIsTop() { return isTop; }
    public void setIsTop(Integer isTop) { this.isTop = isTop; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public Timestamp getPublishTime() { return publishTime; }
    public void setPublishTime(Timestamp publishTime) { this.publishTime = publishTime; }

    public Timestamp getCreateTime() { return createTime; }
    public void setCreateTime(Timestamp createTime) { this.createTime = createTime; }

    public Timestamp getUpdateTime() { return updateTime; }
    public void setUpdateTime(Timestamp updateTime) { this.updateTime = updateTime; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public String getStatusStr() {
        return status != null && status == 1 ? "已发布" : "草稿";
    }
}
