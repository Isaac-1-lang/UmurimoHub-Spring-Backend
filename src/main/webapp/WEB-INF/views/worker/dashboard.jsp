<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Worker Dashboard - UmurimoHub</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #3498db; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar h1 { font-size: 24px; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .profile { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 30px; }
        .menu { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; }
        .menu-item { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); text-align: center; text-decoration: none; color: #333; transition: transform 0.2s; }
        .menu-item:hover { transform: translateY(-5px); box-shadow: 0 5px 15px rgba(0,0,0,0.2); }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>👤 UmurimoHub - My Dashboard</h1>
        <div>
            <span>Welcome, ${user.fullName}</span>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="profile">
            <h2>My Profile</h2>
            <p><strong>Employee ID:</strong> ${worker.employeeId}</p>
            <p><strong>Department:</strong> ${worker.department}</p>
            <p><strong>Position:</strong> ${worker.position}</p>
            <p><strong>Base Salary:</strong> ${worker.baseSalary} RWF</p>
            <p><strong>Status:</strong> ${worker.status}</p>
        </div>
        
        <div class="menu">
            <a href="/worker/attendance" class="menu-item">
                <div style="font-size: 48px;">📅</div>
                <h3>My Attendance</h3>
                <p>${attendanceList.size()} records</p>
            </a>
            <a href="/worker/deductions" class="menu-item">
                <div style="font-size: 48px;">💰</div>
                <h3>Salary Deductions</h3>
                <p>${deductions.size()} deductions</p>
            </a>
            <a href="/worker/disciplinary" class="menu-item">
                <div style="font-size: 48px;">⚠️</div>
                <h3>Disciplinary Actions</h3>
                <p>${actions.size()} actions</p>
            </a>
        </div>
    </div>
</body>
</html>
