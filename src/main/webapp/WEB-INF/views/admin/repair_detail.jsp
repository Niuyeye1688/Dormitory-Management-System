<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Repair" %>
<%@ include file="../common/header.jsp" %>
<%
    Repair repair = (Repair) request.getAttribute("repair");
%>
<div class="container">
    <div class="page-header">
        <h2>报修详情</h2>
        <a href="<%= request.getContextPath() %>/admin/repair" class="btn btn-secondary">返回列表</a>
    </div>
    <% if (repair != null) { %>
    <div class="card">
        <div class="info-grid">
            <div class="info-item">
                <span class="info-label">报修人</span>
                <span class="info-value"><%= repair.getStudentName() %> (<%= repair.getStudentNo() %>)</span>
            </div>
            <div class="info-item">
                <span class="info-label">宿舍</span>
                <span class="info-value"><%= repair.getBuildingNo() %> <%= repair.getRoomNo() %></span>
            </div>
            <div class="info-item">
                <span class="info-label">报修类型</span>
                <span class="info-value"><%= repair.getRepairTypeStr() %></span>
            </div>
            <div class="info-item">
                <span class="info-label">联系电话</span>
                <span class="info-value"><%= repair.getContactPhone() != null ? repair.getContactPhone() : "未填写" %></span>
            </div>
            <div class="info-item">
                <span class="info-label">状态</span>
                <span class="info-value"><span class="status-badge <%= repair.getStatusClass() %>"><%= repair.getStatusStr() %></span></span>
            </div>
            <div class="info-item">
                <span class="info-label">提交时间</span>
                <span class="info-value"><%= repair.getCreateTime() %></span>
            </div>
        </div>
        <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
        <h4><%= repair.getTitle() %></h4>
        <p style="color: #555; margin-top: 10px;"><%= repair.getDescription() != null ? repair.getDescription() : "无详细描述" %></p>

        <% if (repair.getHandlerName() != null) { %>
        <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
        <div class="info-grid">
            <div class="info-item">
                <span class="info-label">处理人</span>
                <span class="info-value"><%= repair.getHandlerName() %></span>
            </div>
            <div class="info-item">
                <span class="info-label">处理时间</span>
                <span class="info-value"><%= repair.getHandleTime() != null ? repair.getHandleTime() : "-" %></span>
            </div>
        </div>
        <div style="margin-top: 15px; padding: 15px; background: #f8f9fa; border-radius: 5px;">
            <strong>处理结果：</strong><br>
            <%= repair.getHandleResult() != null ? repair.getHandleResult() : "无" %>
        </div>
        <% } %>

        <% if (repair.getStatus() != null && repair.getStatus() < 2) { %>
        <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
        <form action="<%= request.getContextPath() %>/admin/repair" method="post">
            <input type="hidden" name="action" value="handle">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <input type="hidden" name="id" value="<%= repair.getId() %>">
            <div class="form-row">
                <div class="form-group">
                    <label>处理状态</label>
                    <select name="status" required>
                        <option value="1">处理中</option>
                        <option value="2">已完成</option>
                        <option value="3">已驳回</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>处理结果</label>
                    <textarea name="handleResult" rows="3" placeholder="请输入处理结果"></textarea>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">提交处理</button>
            </div>
        </form>
        <% } %>
    </div>
    <% } %>
</div>
<%@ include file="../common/footer.jsp" %>
