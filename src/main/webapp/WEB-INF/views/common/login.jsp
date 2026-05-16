<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="com.dormitory.util.HtmlUtil" %>
<!DOCTYPE html>
<html>
<head>
    <title>学生宿舍管理系统 - 登录</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            width: 400px;
        }
        .login-title {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
            font-size: 24px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-size: 14px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #667eea;
        }
        .verify-code-group {
            display: flex;
            gap: 10px;
        }
        .verify-code-group input {
            flex: 1;
        }
        .verify-code-group img {
            height: 42px;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn-login {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: transform 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
        }
        .error-msg {
            color: #e74c3c;
            text-align: center;
            margin-bottom: 15px;
            font-size: 14px;
        }
        .tips {
            text-align: center;
            margin-top: 20px;
            color: #888;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2 class="login-title">学生宿舍管理系统</h2>
        <% if (request.getAttribute("error") != null) { %>
            <p class="error-msg"><%= HtmlUtil.escape((String) request.getAttribute("error")) %></p>
        <% } %>
        <form action="<%= request.getContextPath() %>/login" method="post">
            <div class="form-group">
                <label>用户类型</label>
                <select name="userType" required>
                    <option value="admin">管理员</option>
                    <option value="student">学生</option>
                </select>
            </div>
            <div class="form-group">
                <label>用户名 / 学号</label>
                <input type="text" name="username" placeholder="请输入用户名或学号" required>
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" placeholder="请输入密码" required>
            </div>
            <div class="form-group">
                <label>验证码</label>
                <div class="verify-code-group">
                    <input type="text" name="verifyCode" placeholder="请输入验证码" required>
                    <img src="<%= request.getContextPath() %>/verifyCode" onclick="this.src='<%= request.getContextPath() %>/verifyCode?'+Math.random()" title="点击刷新">
                </div>
            </div>
            <button type="submit" class="btn-login">登录</button>
        </form>
    </div>
</body>
</html>
