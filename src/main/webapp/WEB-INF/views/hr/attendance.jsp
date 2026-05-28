<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Attendance - HR</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #16a085; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .btn { display: inline-block; padding: 10px 20px; background: #16a085; color: white; text-decoration: none; border-radius: 5px; margin-bottom: 20px; }
        table { width: 100%; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #16a085; color: white; }
        .badge { padding: 5px 10px; border-radius: 5px; font-size: 12px; }
        .badge-present { background: #4caf50; color: white; }
        .badge-late { background: #ff9800; color: white; }
        .badge-absent { background: #f44336; color: white; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>📅 Manage Attendance</h1>
        <div>
            <a href="/hr/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <a href="/hr/attendance/new" class="btn">+ Record Attendance</a>
        
        <table>
            <thead>
                <tr>
                    <th>Employee</th>
                    <th>Date</th>
                    <th>Check In</th>
                    <th>Check Out</th>
                    <th>Status</th>
                    <th>Notes</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${attendanceList}" var="att">
                    <tr>
                        <td>${att.worker.user.fullName}</td>
                        <td>${att.date}</td>
                        <td>${att.checkIn}</td>
                        <td>${att.checkOut != null ? att.checkOut : 'Not checked out'}</td>
                        <td><span class="badge badge-${att.status.toString().toLowerCase()}">${att.status}</span></td>
                        <td>${att.notes != null ? att.notes : '-'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
