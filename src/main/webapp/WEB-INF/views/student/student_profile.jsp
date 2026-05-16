<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Student" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<%@ include file="../common/header.jsp" %>
<%
    Student student = (Student) session.getAttribute("student");
%>
<div class="container">
    <div class="page-header">
        <h2>个人信息</h2>
    </div>
    <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= HtmlUtil.escape((String) request.getAttribute("success")) %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= HtmlUtil.escape((String) request.getAttribute("error")) %></div>
    <% } %>
    <div class="card">
        <form action="<%= request.getContextPath() %>/student/profile" method="post">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <div class="form-row">
                <div class="form-group">
                    <label>学号</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getStudentNo()) %>" disabled>
                </div>
                <div class="form-group">
                    <label>姓名</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getName()) %>" disabled>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>性别</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getGenderStr()) %>" disabled>
                </div>
                <div class="form-group">
                    <label>学院</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getCollege()) %>" disabled>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>专业</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getMajor()) %>" disabled>
                </div>
                <div class="form-group">
                    <label>班级</label>
                    <input type="text" value="<%= HtmlUtil.escape(student.getClassName()) %>" disabled>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>入学年份</label>
                    <input type="text" value="<%= student.getEnrollmentYear() != null ? student.getEnrollmentYear() : "" %>" disabled>
                </div>
                <div class="form-group">
                    <label>状态</label>
                    <input type="text" value="<%= student.getStatusStr() %>" disabled>
                </div>
            </div>
            <hr style="margin: 20px 0; border: none; border-top: 1px solid #eee;">
            <div class="form-row">
                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" name="phone" value="<%= HtmlUtil.escape(student.getPhone()) %>">
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" name="email" value="<%= HtmlUtil.escape(student.getEmail()) %>">
                </div>
            </div>
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">保存修改</button>
                <a href="<%= request.getContextPath() %>/changePassword" class="btn btn-secondary">修改密码</a>
            </div>
        </form>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
