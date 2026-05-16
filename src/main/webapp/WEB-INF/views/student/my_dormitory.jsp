<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ include file="../common/header.jsp" %>
<%
    Map<String, Object> dormitoryInfo = (Map<String, Object>) request.getAttribute("dormitoryInfo");
    List<Map<String, Object>> roommates = (List<Map<String, Object>>) request.getAttribute("roommates");
    boolean hasDormitory = dormitoryInfo != null && !dormitoryInfo.isEmpty();
%>
<div class="container">
    <div class="page-header">
        <h2>我的宿舍</h2>
    </div>
    <% if (!hasDormitory) { %>
        <div class="card">
            <div class="empty-state">
                <div class="empty-icon">&#127968;</div>
                <p>您当前未分配宿舍，请联系管理员进行分配。</p>
            </div>
        </div>
    <% } else { %>
        <div class="card">
            <h3 class="card-title">&#127969; 宿舍信息</h3>
            <div class="info-grid">
                <div class="info-item">
                    <span class="info-label">宿舍楼</span>
                    <span class="info-value"><%= HtmlUtil.escape((String) dormitoryInfo.get("buildingName")) %> (<%= HtmlUtil.escape((String) dormitoryInfo.get("buildingNo")) %>)</span>
                </div>
                <div class="info-item">
                    <span class="info-label">房间号</span>
                    <span class="info-value"><%= HtmlUtil.escape((String) dormitoryInfo.get("roomNo")) %></span>
                </div>
                <div class="info-item">
                    <span class="info-label">楼层</span>
                    <span class="info-value"><%= dormitoryInfo.get("floor") %> 层</span>
                </div>
                <div class="info-item">
                    <span class="info-label">我的床位</span>
                    <span class="info-value"><%= dormitoryInfo.get("bedNo") %> 号床</span>
                </div>
                <div class="info-item">
                    <span class="info-label">入住日期</span>
                    <span class="info-value"><%= dormitoryInfo.get("checkInDate") %></span>
                </div>
                <div class="info-item">
                    <span class="info-label">入住情况</span>
                    <span class="info-value"><%= dormitoryInfo.get("currentCount") %> / <%= dormitoryInfo.get("capacity") %> 人</span>
                </div>
            </div>
        </div>

        <div class="card" style="margin-top: 20px;">
            <h3 class="card-title">&#128101; 室友信息</h3>
            <% if (roommates != null && !roommates.isEmpty()) { %>
                <div class="roommate-list">
                    <% for (Map<String, Object> roommate : roommates) { %>
                        <div class="roommate-card">
                            <%
                                String roommateName = (String) roommate.get("name");
                                char firstChar = (roommateName != null && !roommateName.isEmpty()) ? roommateName.charAt(0) : '?';
                            %>
                            <div class="roommate-avatar"><%= firstChar %></div>
                            <div class="roommate-info">
                                <div class="roommate-name"><%= HtmlUtil.escape(roommateName) %> <span class="roommate-gender"><%= HtmlUtil.escape((String) roommate.get("gender")) %></span></div>
                                <div class="roommate-detail">学号: <%= HtmlUtil.escape((String) roommate.get("studentNo")) %> | 床位: <%= roommate.get("bedNo") %>号床</div>
                                <div class="roommate-detail">电话: <%= HtmlUtil.escape((String) roommate.get("phone")) %></div>
                            </div>
                        </div>
                    <% } %>
                </div>
            <% } else { %>
                <p class="text-center" style="color: #888; padding: 20px;">暂无室友信息</p>
            <% } %>
        </div>
    <% } %>
</div>
<%@ include file="../common/footer.jsp" %>
