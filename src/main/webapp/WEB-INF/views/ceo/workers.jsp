<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Workers - CEO Dashboard</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        table { width: 100%; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #667eea; color: white; }
        .badge { padding: 5px 10px; border-radius: 5px; font-size: 12px; }
        .badge-active { background: #4caf50; color: white; }
        .badge-suspended { background: #ff9800; color: white; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>🏢 Workers List</h1>
        <div>
            <a href="/ceo/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <table>
            <thead>
                <tr>
                    <th>Employee ID</th>
                    <th>Name</th>
                    <th>Department</th>
                    <th>Position</th>
                    <th>Hire Date</th>
                    <th>Salary</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${workers}" var="worker">
                    <tr>
                        <td>${worker.employeeId}</td>
                        <td>${worker.user.fullName}</td>
                        <td>${worker.department}</td>
                        <td>${worker.position}</td>
                        <td>${worker.hireDate}</td>
                        <td>${worker.baseSalary} RWF</td>
                        <td><span class="badge badge-${worker.status == 'ACTIVE' ? 'active' : 'suspended'}">${worker.status}</span></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
