<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Reports - CEO Dashboard</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .report-section { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 30px; }
        h2 { color: #2c3e50; margin-bottom: 20px; }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; }
        .stat-box { background: #f8f9fa; padding: 20px; border-radius: 5px; text-align: center; }
        .stat-box h3 { color: #666; font-size: 14px; margin-bottom: 10px; }
        .stat-box .value { font-size: 32px; font-weight: bold; color: #667eea; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>📊 Operational Reports</h1>
        <div>
            <a href="/ceo/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="report-section">
            <h2>Workforce Summary</h2>
            <div class="stats-grid">
                <div class="stat-box">
                    <h3>Total Workers</h3>
                    <div class="value">${workers.size()}</div>
                </div>
                <div class="stat-box">
                    <h3>Attendance Records</h3>
                    <div class="value">${attendanceList.size()}</div>
                </div>
                <div class="stat-box">
                    <h3>Disciplinary Actions</h3>
                    <div class="value">${actions.size()}</div>
                </div>
                <div class="stat-box">
                    <h3>Total Deductions</h3>
                    <div class="value">${deductions.size()}</div>
                </div>
            </div>
        </div>
        
        <div class="report-section">
            <h2>System Information</h2>
            <p><strong>Application:</strong> UmurimoHub Workforce Management System</p>
            <p><strong>Version:</strong> 1.0.0</p>
            <p><strong>Actuator Endpoints:</strong> <a href="/actuator" target="_blank">View All</a></p>
            <p><strong>Health Check:</strong> <a href="/actuator/health" target="_blank">System Health</a></p>
        </div>
    </div>
</body>
</html>
