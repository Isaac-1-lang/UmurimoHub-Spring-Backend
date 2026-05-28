<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>HR Dashboard - UmurimoHub</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #16a085; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 24px; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .menu { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; }
        .menu-item { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); text-align: center; text-decoration: none; color: #333; transition: transform 0.2s; }
        .menu-item:hover { transform: translateY(-5px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
        .menu-item h3 { margin-top: 10px; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>👔 UmurimoHub - HR Dashboard</h1>
        <div>
            <span>Welcome, ${user.fullName}</span>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="menu">
            <a href="/hr/workers" class="menu-item">
                <div style="font-size: 48px;">👥</div>
                <h3>Manage Workers</h3>
            </a>
            <a href="/hr/attendance" class="menu-item">
                <div style="font-size: 48px;">📅</div>
                <h3>Attendance</h3>
            </a>
            <a href="/hr/disciplinary" class="menu-item">
                <div style="font-size: 48px;">⚠️</div>
                <h3>Disciplinary Actions</h3>
            </a>
            <a href="/hr/deductions" class="menu-item">
                <div style="font-size: 48px;">💰</div>
                <h3>Salary Deductions</h3>
            </a>
        </div>
    </div>
</body>
</html>
