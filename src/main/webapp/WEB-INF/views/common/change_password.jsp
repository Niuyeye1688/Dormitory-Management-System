<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        .change-pwd-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .change-pwd-container h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }
    </style>
</head>
<body style="background: #f5f5f5;">
    <div class="change-pwd-container">
        <h2>修改密码</h2>
        <% if (request.getAttribute("success") != null) { %>
            <div class="alert alert-success"><%= HtmlUtil.escape((String) request.getAttribute("success")) %></div>
            <div style="text-align: center; margin-top: 20px;">
                <a href="<%= request.getContextPath() %>/" class="btn btn-primary">返回登录</a>
            </div>
        <% } else { %>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger"><%= HtmlUtil.escape((String) request.getAttribute("error")) %></div>
            <% } %>
            <form action="<%= request.getContextPath() %>/changePassword" method="post">
                <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
                <div class="form-group">
                    <label>原密码</label>
                    <input type="password" name="oldPassword" required placeholder="请输入原密码">
                </div>
                <div class="form-group">
                    <label>新密码</label>
                    <input type="password" name="newPassword" required placeholder="请输入新密码" minlength="6">
                </div>
                <div class="form-group">
                    <label>确认新密码</label>
                    <input type="password" name="confirmPassword" required placeholder="请再次输入新密码" minlength="6">
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">确认修改</button>
                    <a href="javascript:history.back()" class="btn btn-secondary">取消</a>
                </div>
            </form>
        <% } %>
    </div>
</body>
</html>
