<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dormitory.model.Admin" %>
<%@ page import="com.dormitory.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学生宿舍管理系统</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <%
        String userType = (String) session.getAttribute("userType");
        String userName = "";
        if ("admin".equals(userType)) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (admin != null) userName = admin.getRealName() != null ? admin.getRealName() : admin.getUsername();
        } else if ("student".equals(userType)) {
            Student student = (Student) session.getAttribute("student");
            if (student != null) userName = student.getName();
        }
    %>
</head>
<body>
    <div class="wrapper">
        <nav class="sidebar">
            <div class="sidebar-header">
                <h3>宿舍管理系统</h3>
            </div>
            <div class="user-info">
                <span>&#128100; <%= userName %></span>
                <span class="user-type"><%= "admin".equals(userType) ? "管理员" : "学生" %></span>
            </div>
            <ul class="sidebar-menu">
                <% if ("admin".equals(userType)) { %>
                    <li><a href="<%= request.getContextPath() %>/admin/dashboard">&#127968; 控制台</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/student">&#128101; 学生管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/building">&#127969; 宿舍楼管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/dormitory">&#128719; 宿舍管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/allocation">&#128204; 住宿分配</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/repair">&#128295; 报修管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/announcement">&#128226; 公告管理</a></li>
                    <li><a href="<%= request.getContextPath() %>/admin/profile">&#128100; 个人中心</a></li>
                <% } else { %>
                    <li><a href="<%= request.getContextPath() %>/student/dashboard">&#127968; 首页</a></li>
                    <li><a href="<%= request.getContextPath() %>/student/myDormitory">&#128719; 我的宿舍</a></li>
                    <li><a href="<%= request.getContextPath() %>/student/repair">&#128295; 报修服务</a></li>
                    <li><a href="<%= request.getContextPath() %>/student/announcement">&#128226; 公告通知</a></li>
                    <li><a href="<%= request.getContextPath() %>/student/profile">&#128100; 个人信息</a></li>
                <% } %>
                <li class="logout"><a href="<%= request.getContextPath() %>/logout">&#128682; 退出登录</a></li>
            </ul>
        </nav>
        <div class="main-content">
