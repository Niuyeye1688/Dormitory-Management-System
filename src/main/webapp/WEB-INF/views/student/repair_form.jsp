<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Allocation" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ include file="../common/header.jsp" %>
<%
    Allocation allocation = (Allocation) request.getAttribute("allocation");
%>
<div class="container">
    <div class="page-header">
        <h2>提交报修</h2>
    </div>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= HtmlUtil.escape((String) request.getAttribute("error")) %></div>
    <% } %>
    <% if (allocation == null) { %>
        <div class="card">
            <div class="empty-state">
                <div class="empty-icon">&#128295;</div>
                <p>您当前未分配宿舍，无法提交报修。</p>
            </div>
        </div>
    <% } else { %>
    <div class="card">
        <form action="<%= request.getContextPath() %>/student/repair" method="post">
            <input type="hidden" name="action" value="submit">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <div class="form-row">
                <div class="form-group">
                    <label>当前宿舍</label>
                    <input type="text" value="<%= HtmlUtil.escape(allocation.getBuildingNo()) %> <%= HtmlUtil.escape(allocation.getRoomNo()) %>" disabled>
                </div>
                <div class="form-group">
                    <label>报修类型 *</label>
                    <select name="repairType" required>
                        <option value="0">水电</option>
                        <option value="1">门窗</option>
                        <option value="2">家具</option>
                        <option value="3">网络</option>
                        <option value="4">其他</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group" style="flex: 1;">
                    <label>标题 *</label>
                    <input type="text" name="title" placeholder="简短描述问题" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group" style="flex: 1;">
                    <label>问题描述</label>
                    <textarea name="description" rows="4" placeholder="请详细描述问题情况"></textarea>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" name="contactPhone" placeholder="方便维修人员联系">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">提交报修</button>
                <a href="<%= request.getContextPath() %>/student/repair" class="btn btn-secondary">返回</a>
            </div>
        </form>
    </div>
    <% } %>
</div>
<%@ include file="../common/footer.jsp" %>
