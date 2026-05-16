<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Building" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    List<Building> list = (List<Building>) request.getAttribute("list");
%>
<div class="container">
    <div class="page-header">
        <h2>宿舍楼管理</h2>
        <a href="<%= request.getContextPath() %>/admin/building?action=add" class="btn btn-primary">+ 新增宿舍楼</a>
    </div>
    <div class="card">
        <table class="data-table">
            <thead>
                <tr>
                    <th>楼号</th>
                    <th>名称</th>
                    <th>类型</th>
                    <th>楼层数</th>
                    <th>总房间</th>
                    <th>标准入住</th>
                    <th>宿管</th>
                    <th>电话</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% if (list != null && !list.isEmpty()) {
                    for (Building b : list) { %>
                    <tr>
                        <td><%= b.getBuildingNo() %></td>
                        <td><%= b.getBuildingName() != null ? b.getBuildingName() : "" %></td>
                        <td><%= b.getBuildingTypeStr() %></td>
                        <td><%= b.getFloors() %></td>
                        <td><%= b.getTotalRooms() %></td>
                        <td><%= b.getRoomCapacity() %>人</td>
                        <td><%= b.getManagerName() != null ? b.getManagerName() : "" %></td>
                        <td><%= b.getManagerPhone() != null ? b.getManagerPhone() : "" %></td>
                        <td><span class="status-badge <%= b.getStatus() == 1 ? "status-active" : "status-inactive" %>"><%= b.getStatusStr() %></span></td>
                        <td class="actions">
                            <a href="<%= request.getContextPath() %>/admin/building?action=edit&id=<%= b.getId() %>" class="btn btn-sm">编辑</a>
                            <a href="javascript:void(0)" onclick="deleteBuilding(<%= b.getId() %>)" class="btn btn-sm btn-danger">删除</a>
                        </td>
                    </tr>
                <% } } else { %>
                    <tr><td colspan="10" class="text-center">暂无数据</td></tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
<script>
function deleteBuilding(id) {
    if (confirm('确定要删除该宿舍楼吗？')) {
        var form = document.createElement('form');
        form.method = 'POST';
        form.action = '<%= request.getContextPath() %>/admin/building';
        form.innerHTML = '<input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>"\u003e<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="' + id + '"\u003e';
        document.body.appendChild(form);
        form.submit();
    }
}
</script>
<%@ include file="../common/footer.jsp" %>
