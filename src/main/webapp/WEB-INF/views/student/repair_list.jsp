<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Repair" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    List<Repair> list = (List<Repair>) request.getAttribute("list");
%>
<div class="container">
    <div class="page-header">
        <h2>我的报修</h2>
        <a href="<%= request.getContextPath() %>/student/repair?action=add" class="btn btn-primary">+ 提交报修</a>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>类型</th>
                    <th>标题</th>
                    <th>状态</th>
                    <th>处理人</th>
                    <th>提交时间</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Repair r : list) { %>
                    <tr>
                        <td><%= HtmlUtil.escape(r.getRepairTypeStr()) %></td>
                        <td><%= HtmlUtil.escape(r.getTitle()) %></td>
                        <td><span class="status-badge <%= r.getStatusClass() %>"><%= HtmlUtil.escape(r.getStatusStr()) %></span></td>
                        <td><%= HtmlUtil.escape(r.getHandlerName()) %></td>
                        <td><%= r.getCreateTime() %></td>
                    </tr>
                    <% if (r.getHandleResult() != null) { %>
                    <tr>
                        <td colspan="5" style="background: #f8f9fa; padding: 10px 15px; color: #666; font-size: 13px;">
                            <strong>处理结果：</strong> <%= HtmlUtil.escape(r.getHandleResult()) %>
                        </td>
                    </tr>
                    <% } %>
                <% } } else { %>
                    <tr><td colspan="5" class="text-center">暂无报修记录</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
