<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Disciplinary Actions - HR</title>
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
        .badge { padding: 5px 10px; border-radius: 5px; font-size: 12px; color: white; }
        .badge-low { background: #4caf50; }
        .badge-medium { background: #ff9800; }
        .badge-high { background: #f44336; }
        .badge-critical { background: #9c27b0; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>⚠️ Disciplinary Actions</h1>
        <div>
            <a href="/hr/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <a href="/hr/disciplinary/new" class="btn">+ Add Disciplinary Action</a>
        
        <table>
            <thead>
                <tr>
                    <th>Employee</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Severity</th>
                    <th>Description</th>
                    <th>Issued By</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${actions}" var="action">
                    <tr>
                        <td>${action.worker.user.fullName}</td>
                        <td>${action.actionDate}</td>
                        <td>${action.type}</td>
                        <td><span class="badge badge-${action.severity.toString().toLowerCase()}">${action.severity}</span></td>
                        <td>${action.description}</td>
                        <td>${action.issuedBy.fullName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
