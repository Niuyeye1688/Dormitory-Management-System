<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Announcement" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ include file="../common/header.jsp" %>
<%
    Announcement announcement = (Announcement) request.getAttribute("announcement");
%>
<div class="container">
    <div class="page-header">
        <h2>公告详情</h2>
        <a href="<%= request.getContextPath() %>/student/announcement" class="btn btn-secondary">返回列表</a>
    </div>
    <% if (announcement != null) { %>
    <div class="card">
        <% if (announcement.getIsTop() == 1) { %>
            <span style="background: #e74c3c; color: white; padding: 2px 8px; border-radius: 3px; font-size: 12px;">置顶</span>
        <% } %>
        <h2 style="margin-top: 10px; color: #2c3e50;"><%= HtmlUtil.escape(announcement.getTitle()) %></h2>
        <div style="color: #888; font-size: 13px; margin: 15px 0;">
            发布人: <%= HtmlUtil.escape(announcement.getPublisherName()) %> |
            发布时间: <%= HtmlUtil.escape(announcement.getPublishTime()) %> |
            浏览: <%= announcement.getViewCount() %>
        </div>
        <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
        <div style="line-height: 1.8; color: #444; font-size: 15px;">
            <%= announcement.getContent() != null ? HtmlUtil.escape(announcement.getContent()).replace("\n", "<br>") : "" %>
        </div>
    </div>
    <% } %>
</div>
<%@ include file="../common/footer.jsp" %>
