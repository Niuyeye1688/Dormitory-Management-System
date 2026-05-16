<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Admin" %>
<%@ include file="../common/header.jsp" %>
<%
    Admin admin = (Admin) session.getAttribute("admin");
%>
<div class="container">
    <div class="page-header">
        <h2>个人中心</h2>
    </div>
    <% if (request.getAttribute("success") != null) { %>
        <div class="alert alert-success"><%= request.getAttribute("success") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <div class="card">
        <form action="<%= request.getContextPath() %>/admin/profile" method="post">
            <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
            <div class="form-row">
                <div class="form-group">
                    <label>登录账号</label>
                    <input type="text" value="<%= admin.getUsername() %>" disabled>
                </div>
                <div class="form-group">
                    <label>真实姓名</label>
                    <input type="text" name="realName" value="<%= admin.getRealName() != null ? admin.getRealName() : "" %>">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label>联系电话</label>
                    <input type="text" name="phone" value="<%= admin.getPhone() != null ? admin.getPhone() : "" %>">
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input type="email" name="email" value="<%= admin.getEmail() != null ? admin.getEmail() : "" %>">
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
