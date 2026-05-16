<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<div class="container">
    <div class="page-header">
        <h2>控制台</h2>
    </div>
    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon">&#128100;</div>
            <div class="stat-number"><%= request.getAttribute("studentCount") != null ? request.getAttribute("studentCount") : 0 %></div>
            <div class="stat-label">在校学生</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">&#127969;</div>
            <div class="stat-number"><%= request.getAttribute("buildingCount") != null ? request.getAttribute("buildingCount") : 0 %></div>
            <div class="stat-label">宿舍楼</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">&#128719;</div>
            <div class="stat-number"><%= request.getAttribute("dormitoryCount") != null ? request.getAttribute("dormitoryCount") : 0 %></div>
            <div class="stat-label">宿舍房间</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">&#9989;</div>
            <div class="stat-number"><%= request.getAttribute("checkedInCount") != null ? request.getAttribute("checkedInCount") : 0 %></div>
            <div class="stat-label">已入住</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">&#128295;</div>
            <div class="stat-number"><%= request.getAttribute("pendingRepairCount") != null ? request.getAttribute("pendingRepairCount") : 0 %></div>
            <div class="stat-label">待处理报修</div>
        </div>
        <div class="stat-card">
            <div class="stat-icon">&#128716;</div>
            <div class="stat-number"><%= request.getAttribute("emptyBeds") != null ? request.getAttribute("emptyBeds") : 0 %></div>
            <div class="stat-label">空余床位</div>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
