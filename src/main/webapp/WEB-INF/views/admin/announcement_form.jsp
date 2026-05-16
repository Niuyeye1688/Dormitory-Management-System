<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Announcement" %>
<%@ include file="../common/header.jsp" %>
<%
    Announcement announcement = (Announcement) request.getAttribute("announcement");
    boolean isEdit = announcement != null;
%>
<div class="container">
    <div class="page-header">
        <h2><%= isEdit ? "ç¼è¾å¬å" : "åå¸å¬å" %></h2>
    </div>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/announcement" method="post">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <% if (isEdit) { %><input type="hidden" name="id" value="<%= announcement.getId() %>"><% } %>
            <div class="form-row">
                <div class="form-group" style="flex: 2;">
                    <label>æ é¢ *</label>
                    <input type="text" name="title" value="<%= isEdit ? announcement.getTitle() : "" %>" required>
                </div>
                <div class="form-group">
                    <label>ç½®é¡¶</label>
                    <select name="isTop">
                        <option value="0" <%= isEdit && announcement.getIsTop() == 0 ? "selected" : "" %>>å?/option>
                        <option value="1" <%= isEdit && announcement.getIsTop() == 1 ? "selected" : "" %>>æ?/option>
                    </select>
                </div>
                <div class="form-group">
                    <label>ç¶æ?/label>
                    <select name="status">
                        <option value="1" <%= isEdit && announcement.getStatus() == 1 ? "selected" : "" %>>å·²åå¸?/option>
                        <option value="0" <%= isEdit && announcement.getStatus() == 0 ? "selected" : "" %>>èç¨¿</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group" style="flex: 1;">
                    <label>åå®¹ *</label>
                    <textarea name="content" rows="10" required><%= isEdit && announcement.getContent() != null ? announcement.getContent() : "" %></textarea>
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><%= isEdit ? "ä¿å­" : "åå¸" %></button>
                <a href="<%= request.getContextPath() %>/admin/announcement" class="btn btn-secondary">è¿å</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
