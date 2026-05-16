<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Allocation" %>
<%@ page import="com.dormitory.util.Pagination" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    Pagination<Allocation> pagination = (Pagination<Allocation>) request.getAttribute("pagination");
    String keyword = (String) request.getAttribute("keyword");
    List<Allocation> list = pagination != null ? pagination.getList() : null;
%>
<div class="container">
    <div class="page-header">
        <h2>住宿分配</h2>
        <a href="<%= request.getContextPath() %>/admin/allocation?action=add" class="btn btn-primary">+ 分配宿舍</a>
    </div>
    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/admin/allocation" method="get">
            <input type="text" name="keyword" placeholder="搜索学号/姓名" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit" class="btn btn-search">搜索</button>
        </form>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>宿舍楼</th>
                    <th>房间号</th>
                    <th>床位</th>
                    <th>入住日期</th>
                    <th>退宿日期</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Allocation a : list) { %>
                    <tr>
                        <td><%= a.getStudentNo() %></td>
                        <td><%= a.getStudentName() %></td>
                        <td><%= a.getBuildingNo() %></td>
                        <td><%= a.getRoomNo() %></td>
                        <td><%= a.getBedNo() %>号床</td>
                        <td><%= a.getCheckInDate() %></td>
                        <td><%= a.getCheckOutDate() != null ? a.getCheckOutDate() : "-" %></td>
                        <td><span class="status-badge <%= a.getStatus() == 1 ? "status-active" : "status-inactive" %>"><%= a.getStatusStr() %></span></td>
                        <td class="actions">
                            <% if (a.getStatus() == 1) { %>
                                <a href="javascript:void(0)" onclick="checkout(<%= a.getId() %>)" class="btn btn-sm">退宿</a>
                                <a href="javascript:void(0)" onclick="transfer(<%= a.getId() %>)" class="btn btn-sm">调宿</a>
                            <% } %>
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
                <a href="<%= request.getContextPath() %>/admin/allocation?page=<%= pagination.getCurrentPage() - 1 %>&keyword=<%= keyword != null ? keyword : "" %>">上一页</a>
            <% } %>
            <span><%= pagination.getCurrentPage() %> / <%= pagination.getTotalPage() %></span>
            <% if (pagination.hasNext()) { %>
                <a href="<%= request.getContextPath() %>/admin/allocation?page=<%= pagination.getCurrentPage() + 1 %>&keyword=<%= keyword != null ? keyword : "" %>">下一页</a>
            <% } %>
        </div>
        <% } %>
    </div>
</div>
<script>
function checkout(id) {
    var remark = prompt('请输入退宿舍备注（可选）：');
    if (remark !== null) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/admin/allocation';
        form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="checkout"><input type="hidden" name="id" value="' + id + '"\u003e<input type="hidden" name="remark" value="' + (remark || '') + '"\u003e';
        document.body.appendChild(form);
        form.submit();
    }
}

function transfer(id) {
    var newDormitoryId = prompt('请输入新宿舍ID：');
    if (newDormitoryId && newDormitoryId.trim()) {
        var bedNo = prompt('请输入新床位号：');
        if (bedNo && bedNo.trim()) {
            var remark = prompt('请输入调宿舍备注（可选）：');
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '<%= request.getContextPath() %>/admin/allocation';
            form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="transfer"><input type="hidden" name="id" value="' + id + '"\u003e<input type="hidden" name="newDormitoryId" value="' + newDormitoryId + '"\u003e<input type="hidden" name="bedNo" value="' + bedNo + '"\u003e<input type="hidden" name="remark" value="' + (remark || '') + '"\u003e';
            document.body.appendChild(form);
            form.submit();
        }
    }
}
</script>
<%@ include file="../common/footer.jsp" %>
