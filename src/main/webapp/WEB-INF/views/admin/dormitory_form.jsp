<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Dormitory" %>
<%@ page import="com.dormitory.model.Building" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    Dormitory dormitory = (Dormitory) request.getAttribute("dormitory");
    List<Building> buildings = (List<Building>) request.getAttribute("buildings");
    boolean isEdit = dormitory != null;
%>
<div class="container">
    <div class="page-header">
        <h2><%= isEdit ? "ç¼è¾å®¿è" : "æ°å¢å®¿è" %></h2>
    </div>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/dormitory" method="post">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <% if (isEdit) { %><input type="hidden" name="id" value="<%= dormitory.getId() %>"><% } %>
            <div class="form-row">
                <div class="form-group">
                    <label>æå±æ¥¼ *</label>
                    <select name="buildingId" required>
                        <% if (buildings != null) {
                            for (Building b : buildings) { %>
                            <option value="<%= b.getId() %>" <%= isEdit && dormitory.getBuildingId().equals(b.getId()) ? "selected" : "" %>><%= b.getBuildingNo() %></option>
                        <% } } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>æ¿é´å?*</label>
                    <input type="text" name="roomNo" value="<%= isEdit ? dormitory.getRoomNo() : "" %>" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>æå¨æ¥¼å±?*</label>
                    <input type="number" name="floor" value="<%= isEdit ? dormitory.getFloor() : "" %>" required min="1">
                </div>
                <div class="form-group">
                    <label>åºä½æ?*</label>
                    <input type="number" name="capacity" value="<%= isEdit ? dormitory.getCapacity() : "4" %>" required min="1">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>æ¿å *</label>
                    <select name="roomType" required>
                        <option value="0" <%= isEdit && dormitory.getRoomType() == 0 ? "selected" : "" %>>åäººé?/option>
                        <option value="1" <%= isEdit && dormitory.getRoomType() == 1 ? "selected" : "" %>>å­äººé?/option>
                        <option value="2" <%= isEdit && dormitory.getRoomType() == 2 ? "selected" : "" %>>åäººé?/option>
                    </select>
                </div>
                <% if (isEdit) { %>
                <div class="form-group">
                    <label>ç¶æ?/label>
                    <select name="status">
                        <option value="1" <%= dormitory.getStatus() == 1 ? "selected" : "" %>>å¯ç¨</option>
                        <option value="0" <%= dormitory.getStatus() == 0 ? "selected" : "" %>>ç»´ä¿®ä¸?/option>
                    </select>
                </div>
                <% } %>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><%= isEdit ? "ä¿å­" : "æ·»å " %></button>
                <a href="<%= request.getContextPath() %>/admin/dormitory" class="btn btn-secondary">è¿å</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
