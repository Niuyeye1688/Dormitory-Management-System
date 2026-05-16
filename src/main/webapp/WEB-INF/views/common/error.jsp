<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>错误</title>
    <style>
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .error-container {
            text-align: center;
            background: white;
            padding: 60px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .error-code {
            font-size: 100px;
            color: #e74c3c;
            font-weight: bold;
        }
        .error-msg {
            font-size: 20px;
            color: #666;
            margin: 20px 0;
        }
        .back-link {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 30px;
            background: #667eea;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .back-link:hover {
            background: #5a6fd6;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code"><%= response.getStatus() %></div>
        <div class="error-msg">抱歉，页面出现了问题</div>
        <a href="<%= request.getContextPath() %>/" class="back-link">返回首页</a>
    </div>
</body>
</html>
