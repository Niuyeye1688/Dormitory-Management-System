<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Dormitory" %>
<%@ page import="com.dormitory.model.Building" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    List<Dormitory> list = (List<Dormitory>) request.getAttribute("list");
    List<Building> buildings = (List<Building>) request.getAttribute("buildings");
    String selectedBuildingId = (String) request.getAttribute("selectedBuildingId");
    String keyword = (String) request.getAttribute("keyword");
%>
<div class="container">
    <div class="page-header">
        <h2>宿舍管理</h2>
        <a href="<%= request.getContextPath() %>/admin/dormitory?action=add" class="btn btn-primary">+ 新增宿舍</a>
    </div>
    <div class="search-bar">
        <form action="<%= request.getContextPath() %>/admin/dormitory" method="get">
            <select name="buildingId">
                <option value="">全部宿舍楼</option>
                <% if (buildings != null) {
                    for (Building b : buildings) { %>
                    <option value="<%= b.getId() %>" <%= String.valueOf(b.getId()).equals(selectedBuildingId) ? "selected" : "" %>><%= b.getBuildingNo() %></option>
                <% } } %>
            </select>
            <input type="text" name="keyword" placeholder="房间号" value="<%= keyword != null ? keyword : "" %>">
            <button type="submit" class="btn btn-search">搜索</button>
        </form>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>宿舍楼</th>
                    <th>房间号</th>
                    <th>楼层</th>
                    <th>房型</th>
                    <th>床位数</th>
                    <th>已住</th>
                    <th>空余</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Dormitory d : list) { %>
                    <tr>
                        <td><%= d.getBuildingNo() %></td>
                        <td><%= d.getRoomNo() %></td>
                        <td><%= d.getFloor() %>层</td>
                        <td><%= d.getRoomTypeStr() %></td>
                        <td><%= d.getCapacity() %></td>
                        <td><%= d.getCurrentCount() %></td>
                        <td><span style="color:<%= d.getEmptyBeds() > 0 ? "#27ae60" : "#e74c3c" %>"><%= d.getEmptyBeds() %></span></td>
                        <td><span class="status-badge <%= d.getStatus() == 1 ? "status-active" : "status-inactive" %>"><%= d.getStatusStr() %></span></td>
                        <td class="actions">
                            <a href="<%= request.getContextPath() %>/admin/dormitory?action=edit&id=<%= d.getId() %>" class="btn btn-sm">编辑</a>
                            <a href="javascript:void(0)" onclick="deleteDormitory(<%= d.getId() %>)" class="btn btn-sm btn-danger">删除</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="9" class="text-center">暂无数据</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
<script>
function deleteDormitory(id) {
    if (confirm('确定要删除该宿舍吗？')) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/admin/dormitory';
        form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="' + id + '"\u003e';
        document.body.appendChild(form);
        form.submit();
    }
}
</script>
<%@ include file="../common/footer.jsp" %>
