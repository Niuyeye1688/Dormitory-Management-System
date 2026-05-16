<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Announcement" %>
<%@ page import="com.dormitory.util.Pagination" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    Pagination<Announcement> pagination = (Pagination<Announcement>) request.getAttribute("pagination");
    String keyword = (String) request.getAttribute("keyword");
    List<Announcement> list = pagination != null ? pagination.getList() : null;
%>
<div class="container">
    <div class="page-header">
        <h2>公告管理</h2>
        <a href="<%= request.getContextPath() %>/admin/announcement?action=add" class="btn btn-primary">+ 发布公告</a>
    </div>
    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/admin/announcement" method="get">
            <input type="text" name="keyword" placeholder="搜索标题" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit" class="btn btn-search">搜索</button>
        </form>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>标题</th>
                    <th>发布人</th>
                    <th>置顶</th>
                    <th>浏览</th>
                    <th>状态</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Announcement a : list) { %>
                    <tr>
                        <td><%= a.getTitle() %></td>
                        <td><%= a.getPublisherName() != null ? a.getPublisherName() : "-" %></td>
                        <td><%= a.getIsTop() == 1 ? "是" : "否" %></td>
                        <td><%= a.getViewCount() %></td>
                        <td><span class="status-badge <%= a.getStatus() == 1 ? "status-active" : "status-inactive" %>"><%= a.getStatusStr() %></span></td>
                        <td><%= a.getPublishTime() != null ? a.getPublishTime() : "-" %></td>
                        <td class="actions">
                            <a href="<%= request.getContextPath() %>/admin/announcement?action=edit&id=<%= a.getId() %>" class="btn btn-sm">编辑</a>
                            <a href="javascript:void(0)" onclick="deleteAnnouncement(<%= a.getId() %>)" class="btn btn-sm btn-danger">删除</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="7" class="text-center">暂无数据</td></tr>
                <% } %>
            </tbody>
        </table>
        <% if (pagination != null && pagination.getTotalPage() > 1) { %>
        <div class="pagination">
            <% if (pagination.hasPrevious()) { %>
                <a href="<%= request.getContextPath() %>/admin/announcement?page=<%= pagination.getCurrentPage() - 1 %>&keyword=<%= keyword != null ? keyword : "" %>">上一页</a>
            <% } %>
            <span><%= pagination.getCurrentPage() %> / <%= pagination.getTotalPage() %></span>
            <% if (pagination.hasNext()) { %>
                <a href="<%= request.getContextPath() %>/admin/announcement?page=<%= pagination.getCurrentPage() + 1 %>&keyword=<%= keyword != null ? keyword : "" %>">下一页</a>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
<script>
function deleteAnnouncement(id) {
    if (confirm('确定要删除该公告吗？')) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/admin/announcement';
        form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="' + id + '"\u003e';
        document.body.appendChild(form);
        form.submit();
    }
}
</script>
<%@ include file="../common/footer.jsp" %>
