<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Salary Deduction - HR</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #16a085; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 800px; margin: 30px auto; padding: 0 20px; }
        .form-card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input, select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        button { padding: 12px 30px; background: #16a085; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        button:hover { background: #138f75; }
        h2 { margin-bottom: 20px; color: #333; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>💰 Add Salary Deduction</h1>
        <div>
            <a href="/hr/deductions">Back to Deductions</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="form-card">
            <h2>Salary Deduction</h2>
            <form action="/hr/deductions" method="post">
                <div class="form-group">
                    <label>Worker</label>
                    <select name="workerId" required>
                        <option value="">Select Worker</option>
                        <c:forEach items="${workers}" var="worker">
                            <option value="${worker.id}">${worker.user.fullName} (${worker.employeeId})</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Deduction Date</label>
                    <input type="date" name="deductionDate" required>
                </div>
                <div class="form-group">
                    <label>Amount (RWF)</label>
                    <input type="number" name="amount" step="0.01" required>
                </div>
                <div class="form-group">
                    <label>Deduction Type</label>
                    <select name="type" required>
                        <option value="LATE_ARRIVAL">Late Arrival</option>
                        <option value="ABSENCE">Absence</option>
                        <option value="DISCIPLINARY">Disciplinary</option>
                        <option value="TAX">Tax</option>
                        <option value="INSURANCE">Insurance</option>
                        <option value="OTHER">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Reason</label>
                    <textarea name="reason" rows="3" required></textarea>
                </div>
                
                <button type="submit">Create Deduction</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</body>
</html>
