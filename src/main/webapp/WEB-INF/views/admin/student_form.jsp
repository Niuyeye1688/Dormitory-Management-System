<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Student" %>
<%@ include file="../common/header.jsp" %>
<%
    Student student = (Student) request.getAttribute("student");
    boolean isEdit = student != null;
%>
<div class="container">
    <div class="page-header">
        <h2><%= isEdit ? "编辑学生" : "新增学生" %></h2>
    </div>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/student" method="post">
            <input type="hidden" name="action" value="save">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <% if (isEdit) { %>
                <input type="hidden" name="id" value="<%= student.getId() %>">
            <% } %>
            <div class="form-row">
                <div class="form-group">
                    <label>学号 *</label>
                    <input type="text" name="studentNo" value="<%= isEdit ? student.getStudentNo() : "" %>" <%= isEdit ? "disabled" : "required" %>>
                    <% if (isEdit) { %><input type="hidden" name="studentNo" value="<%= student.getStudentNo() %>"><% } %>
                </div>
                <div class="form-group">
                    <label>姓名 *</label>
                    <input type="text" name="name" value="<%= isEdit ? student.getName() : "" %>" required>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>性别 *</label>
                    <select name="gender" required>
                        <option value="1" <%= isEdit && student.getGender() == 1 ? "selected" : "" %>>男</option>
                        <option value="0" <%= isEdit && student.getGender() == 0 ? "selected" : "" %>>女</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" name="phone" value="<%= isEdit && student.getPhone() != null ? student.getPhone() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" name="email" value="<%= isEdit && student.getEmail() != null ? student.getEmail() : "" %>">
                </div>
                <div class="form-group">
                    <label>学院</label>
                    <input type="text" name="college" value="<%= isEdit && student.getCollege() != null ? student.getCollege() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>专业</label>
                    <input type="text" name="major" value="<%= isEdit && student.getMajor() != null ? student.getMajor() : "" %>">
                </div>
                <div class="form-group">
                    <label>班级</label>
                    <input type="text" name="className" value="<%= isEdit && student.getClassName() != null ? student.getClassName() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>入学年份</label>
                    <input type="number" name="enrollmentYear" value="<%= isEdit && student.getEnrollmentYear() != null ? student.getEnrollmentYear() : "" %>">
                </div>
                <% if (isEdit) { %>
                <div class="form-group">
                    <label>状态</label>
                    <select name="status">
                        <option value="1" <%= student.getStatus() == 1 ? "selected" : "" %>>在校</option>
                        <option value="0" <%= student.getStatus() == 0 ? "selected" : "" %>>离校</option>
                    </select>
                </div>
                <% } %>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><%= isEdit ? "保存修改" : "确认添加" %></button>
                <a href="<%= request.getContextPath() %>/admin/student" class="btn btn-secondary">返回列表</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
