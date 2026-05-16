<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Repair" %>
<%@ page import="com.dormitory.util.Pagination" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    Pagination<Repair> pagination = (Pagination<Repair>) request.getAttribute("pagination");
    String keyword = (String) request.getAttribute("keyword");
    String status = (String) request.getAttribute("status");
    List<Repair> list = pagination != null ? pagination.getList() : null;
%>
<div class="container">
    <div class="page-header">
        <h2>报修管理</h2>
    </div>
    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/admin/repair" method="get">
            <select name="status">
                <option value="">全部状态</option>
                <option value="0" <%= "0".equals(status) ? "selected" : "" %>>待处理</option>
                <option value="1" <%= "1".equals(status) ? "selected" : "" %>>处理中</option>
                <option value="2" <%= "2".equals(status) ? "selected" : "" %>>已完成</option>
                <option value="3" <%= "3".equals(status) ? "selected" : "" %>>已驳回</option>
            </select>
            <input type="text" name="keyword" placeholder="标题/学生姓名" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit" class="btn btn-search">搜索</button>
        </form>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>报修人</th>
                    <th>宿舍</th>
                    <th>类型</th>
                    <th>标题</th>
                    <th>联系电话</th>
                    <th>状态</th>
                    <th>提交时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Repair r : list) { %>
                    <tr>
                        <td><%= r.getId() %></td>
                        <td><%= r.getStudentName() %></td>
                        <td><%= r.getBuildingNo() %> <%= r.getRoomNo() %></td>
                        <td><%= r.getRepairTypeStr() %></td>
                        <td><%= r.getTitle() %></td>
                        <td><%= r.getContactPhone() != null ? r.getContactPhone() : "" %></td>
                        <td><span class="status-badge <%= r.getStatusClass() %>"><%= r.getStatusStr() %></span></td>
                        <td><%= r.getCreateTime() %></td>
                        <td class="actions">
                            <a href="<%= request.getContextPath() %>/admin/repair?action=detail&id=<%= r.getId() %>" class="btn btn-sm">详情</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="9" class="text-center">暂无数据</td></tr>
                <% } %>
            </tbody>
        </table>
        <% if (pagination != null && pagination.getTotalPage() > 1) { %>
        <div class="pagination">
            <% if (pagination.hasPrevious()) { %>
                <a href="<%= request.getContextPath() %>/admin/repair?page=<%= pagination.getCurrentPage() - 1 %>&status=<%= status != null ? status : "" %>&keyword=<%= keyword != null ? keyword : "" %>">上一页</a>
            <% } %>
            <span><%= pagination.getCurrentPage() %> / <%= pagination.getTotalPage() %></span>
            <% if (pagination.hasNext()) { %>
                <a href="<%= request.getContextPath() %>/admin/repair?page=<%= pagination.getCurrentPage() + 1 %>&status=<%= status != null ? status : "" %>&keyword=<%= keyword != null ? keyword : "" %>">下一页</a>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
