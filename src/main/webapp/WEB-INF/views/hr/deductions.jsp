<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Salary Deductions - HR</title>
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
    </style>
</head>
<body>
    <div class="navbar">
        <h1>💰 Salary Deductions</h1>
        <div>
            <a href="/hr/dashboard">Dashboard</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <a href="/hr/deductions/new" class="btn">+ Add Deduction</a>
        
        <table>
            <thead>
                <tr>
                    <th>Employee</th>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Amount</th>
                    <th>Reason</th>
                    <th>Approved By</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${deductions}" var="deduction">
                    <tr>
                        <td>${deduction.worker.user.fullName}</td>
                        <td>${deduction.deductionDate}</td>
                        <td>${deduction.type}</td>
                        <td>${deduction.amount} RWF</td>
                        <td>${deduction.reason}</td>
                        <td>${deduction.approvedBy.fullName}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
