<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Building" %>
<%@ include file="../common/header.jsp" %>
<%
    Building building = (Building) request.getAttribute("building");
    boolean isEdit = building != null;
%>
<div class="container">
    <div class="page-header">
        <h2><%= isEdit ? "ç¼è¾å®¿èæ¥? : "æ°å¢å®¿èæ¥? %></h2>
    </div>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/building" method="post">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <% if (isEdit) { %><input type="hidden" name="id" value="<%= building.getId() %>"><% } %>
            <div class="form-row">
                <div class="form-group">
                    <label>æ¥¼å· *</label>
                    <input type="text" name="buildingNo" value="<%= isEdit ? building.getBuildingNo() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>åç§°</label>
                    <input type="text" name="buildingName" value="<%= isEdit && building.getBuildingName() != null ? building.getBuildingName() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>æ¥¼å±æ?*</label>
                    <input type="number" name="floors" value="<%= isEdit ? building.getFloors() : "" %>" required min="1">
                </div>
                <div class="form-group">
                    <label>æ»æ¿é´æ° *</label>
                    <input type="number" name="totalRooms" value="<%= isEdit ? building.getTotalRooms() : "" %>" required min="1">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>æ åå¥ä½äººæ° *</label>
                    <input type="number" name="roomCapacity" value="<%= isEdit ? building.getRoomCapacity() : "4" %>" required min="1">
                </div>
                <div class="form-group">
                    <label>æ¥¼ç±»å?*</label>
                    <select name="buildingType" required>
                        <option value="0" <%= isEdit && building.getBuildingType() == 0 ? "selected" : "" %>>ç·å¯</option>
                        <option value="1" <%= isEdit && building.getBuildingType() == 1 ? "selected" : "" %>>å¥³å¯</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>å®¿ç®¡å§å</label>
                    <input type="text" name="managerName" value="<%= isEdit && building.getManagerName() != null ? building.getManagerName() : "" %>">
                </div>
                <div class="form-group">
                    <label>å®¿ç®¡çµè¯</label>
                    <input type="text" name="managerPhone" value="<%= isEdit && building.getManagerPhone() != null ? building.getManagerPhone() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>å°å</label>
                    <input type="text" name="address" value="<%= isEdit && building.getAddress() != null ? building.getAddress() : "" %>">
                </div>
                <% if (isEdit) { %>
                <div class="form-group">
                    <label>ç¶æ?/label>
                    <select name="status">
                        <option value="1" <%= building.getStatus() == 1 ? "selected" : "" %>>å¯ç¨</option>
                        <option value="0" <%= building.getStatus() == 0 ? "selected" : "" %>>åç¨</option>
                    </select>
                </div>
                <% } %>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><%= isEdit ? "ä¿å­" : "æ·»å " %></button>
                <a href="<%= request.getContextPath() %>/admin/building" class="btn btn-secondary">è¿å</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
