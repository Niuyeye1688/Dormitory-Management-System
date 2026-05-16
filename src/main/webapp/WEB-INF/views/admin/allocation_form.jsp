<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Student" %>
<%@ page import="com.dormitory.model.Dormitory" %>
<%@ page import="java.util.List" %>
<%@ include file="../common/header.jsp" %>
<%
    List<Student> students = (List<Student>) request.getAttribute("students");
    List<Dormitory> dormitories = (List<Dormitory>) request.getAttribute("dormitories");
%>
<div class="container">
    <div class="page-header">
        <h2>分配宿舍</h2>
    </div>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= HtmlUtil.escape((String) request.getAttribute("error")) %></div>
    <% } %>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/allocation" method="post">
            <input type="hidden" name="action" value="allocate">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <div class="form-row">
                <div class="form-group">
                    <label>选择学生 *</label>
                    <select name="studentId" required>
                        <option value="">请选择</option>
                        <% if (students != null) {
                            for (Student s : students) { %>
                            <option value="<%= s.getId() %>"><%= HtmlUtil.escape(s.getStudentNo()) %> - <%= HtmlUtil.escape(s.getName()) %> (<%= s.getGender() == 1 ? "男" : "女" %>)</option>
                        <% } } %>
                    </select>
                </div>
                <div class="form-group">
                    <label>选择宿舍 *</label>
                    <select name="dormitoryId" required>
                        <option value="">请选择</option>
                        <% if (dormitories != null) {
                            for (Dormitory d : dormitories) { %>
                            <option value="<%= d.getId() %>"><%= HtmlUtil.escape(d.getBuildingNo()) %> <%= HtmlUtil.escape(d.getRoomNo()) %> (空余<%= d.getEmptyBeds() %>床)</option>
                        <% } } %>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>床位号 *</label>
                    <input type="number" name="bedNo" required min="1" placeholder="如: 1">
                </div>
                <div class="form-group">
                    <label>入住日期 *</label>
                    <input type="date" name="checkInDate" required value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>备注</label>
                    <input type="text" name="remark" placeholder="可选">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">确认分配</button>
                <a href="<%= request.getContextPath() %>/admin/allocation" class="btn btn-secondary">返回</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
