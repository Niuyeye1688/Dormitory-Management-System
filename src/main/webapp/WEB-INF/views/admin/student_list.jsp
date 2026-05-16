<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Student" %>
<%@ page import="com.dormitory.util.Pagination" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    Pagination<Student> pagination = (Pagination<Student>) request.getAttribute("pagination");
    String keyword = (String) request.getAttribute("keyword");
    List<Student> list = pagination != null ? pagination.getList() : null;
%>
<div class="container">
    <div class="page-header">
        <h2>学生管理</h2>
        <a href="<%= request.getContextPath() %>/admin/student?action=add" class="btn btn-primary">+ 新增学生</a>
    </div>
    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/admin/student" method="get">
            <input type="text" name="keyword" placeholder="搜索学号/姓名/学院" value="<%= HtmlUtil.escape(keyword) %>">
            <button type="submit" class="btn btn-search">搜索</button>
        </form>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>学院</th>
                    <th>专业</th>
                    <th>班级</th>
                    <th>联系电话</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Student s : list) { %>
                    <tr>
                        <td><%= HtmlUtil.escape(s.getStudentNo()) %></td>
                        <td><%= HtmlUtil.escape(s.getName()) %></td>
                        <td><%= HtmlUtil.escape(s.getGenderStr()) %></td>
                        <td><%= HtmlUtil.escape(s.getCollege()) %></td>
                        <td><%= HtmlUtil.escape(s.getMajor()) %></td>
                        <td><%= HtmlUtil.escape(s.getClassName()) %></td>
                        <td><%= HtmlUtil.escape(s.getPhone()) %></td>
                        <td><span class="status-badge <%= s.getStatus() == 1 ? "status-active" : "status-inactive" %>"><%= HtmlUtil.escape(s.getStatusStr()) %></span></td>
                        <td class="actions">
                            <a href="<%= request.getContextPath() %>/admin/student?action=edit&id=<%= s.getId() %>" class="btn btn-sm">编辑</a>
                            <a href="javascript:void(0)" onclick="deleteStudent(<%= s.getId() %>)" class="btn btn-sm btn-danger">删除</a>
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
                <a href="<%= request.getContextPath() %>/admin/student?page=<%= pagination.getCurrentPage() - 1 %>&keyword=<%= java.net.URLEncoder.encode(keyword != null ? keyword : "", "UTF-8") %>">上一页</a>
            <% } %>
            <span><%= pagination.getCurrentPage() %> / <%= pagination.getTotalPage() %> 页，共 <%= pagination.getTotalCount() %> 条</span>
            <% if (pagination.hasNext()) { %>
                <a href="<%= request.getContextPath() %>/admin/student?page=<%= pagination.getCurrentPage() + 1 %>&keyword=<%= java.net.URLEncoder.encode(keyword != null ? keyword : "", "UTF-8") %>">下一页</a>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
<script>
function escapeHtml(text) {
    var div = document.createElement('div');
    div.textContent = String(text);
    return div.innerHTML;
}
function deleteStudent(id) {
    if (confirm('确定要删除该学生吗？此操作不可恢复。')) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/admin/student';
        form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="' + id + '"\u003e';
        document.body.appendChild(form);
        form.submit();
    }
}
</script>
<%@ include file="../common/footer.jsp" %>
