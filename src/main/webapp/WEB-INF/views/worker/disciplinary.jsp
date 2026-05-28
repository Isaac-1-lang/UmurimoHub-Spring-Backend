<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Disciplinary Actions</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #3498db; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        table { width: 100%; background: white; border-radius: 10px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background: #3498db; color: white; }
        .badge { padding: 5px 10px; border-radius: 5px; font-size: 12px; color: white; }
        .badge-low { background: #4caf50; }
        .badge-medium { background: #ff9800; }
        .badge-high { background: #f44336; }
        .badge-critical { background: #9c27b0; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>⚠️ My Disciplinary Actions</h1>
        <div>
            <a href="/worker/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <table>
            <thead>
                <tr>
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
