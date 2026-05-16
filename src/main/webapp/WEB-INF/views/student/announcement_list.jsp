<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Announcement" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    List<Announcement> list = (List<Announcement>) request.getAttribute("list");
%>
<div class="container">
    <div class="page-header">
        <h2>公告通知</h2>
    </div>
    <% if (list != null && !list.isEmpty()) {
        for (Announcement a : list) { %>
        <div class="card" style="margin-bottom: 15px;">
            <div style="display: flex; justify-content: space-between; align-items: start;">
                <div>
                    <% if (a.getIsTop() == 1) { %>
                        <span style="background: #e74c3c; color: white; padding: 2px 8px; border-radius: 3px; font-size: 12px; margin-right: 8px;">置顶</span>
                    <% } %>
                    <a href="<%= request.getContextPath() %>/student/announcement?action=detail&id=<%= a.getId() %>" style="font-size: 18px; font-weight: 500; color: #2c3e50; text-decoration: none;"><%= HtmlUtil.escape(a.getTitle()) %></a>
                </div>
                <span style="color: #888; font-size: 13px; white-space: nowrap;"><%= a.getPublishTime() != null ? a.getPublishTime() : "" %></span>
            </div>
            <p style="color: #666; margin-top: 10px; font-size: 14px; line-height: 1.6;">
                <% String content = a.getContent();
                   if (content != null && content.length() > 100) {
                       content = content.substring(0, 100) + "...";
                   }
                %>
                <%= content != null ? HtmlUtil.escape(content) : "" %>
            </p>
            <div style="margin-top: 10px; color: #888; font-size: 12px;">
                发布人: <%= HtmlUtil.escape(a.getPublisherName()) %> | 浏览: <%= a.getViewCount() %>
            </div>
        </div>
    <% } } else { %>
        <div class="card">
            <div class="empty-state">
                <div class="empty-icon">&#128226;</div>
                <p>暂无公告</p>
            </div>
        </div>
    <% } %>
</div>
<%@ include file="../common/footer.jsp" %>
