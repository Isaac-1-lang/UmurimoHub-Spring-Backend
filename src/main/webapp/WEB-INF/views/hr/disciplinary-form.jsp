<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Disciplinary Action - HR</title>
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
        <h1>⚠️ Add Disciplinary Action</h1>
        <div>
            <a href="/hr/disciplinary">Back to Actions</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="form-card">
            <h2>Disciplinary Action</h2>
            <form action="/hr/disciplinary" method="post">
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
                    <label>Action Type</label>
                    <select name="type" required>
                        <option value="VERBAL_WARNING">Verbal Warning</option>
                        <option value="WRITTEN_WARNING">Written Warning</option>
                        <option value="WARNING">Warning</option>
                        <option value="SUSPENSION">Suspension</option>
                        <option value="TERMINATION">Termination</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Severity</label>
                    <select name="severity" required>
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                        <option value="CRITICAL">Critical</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Description</label>
                    <textarea name="description" rows="5" required></textarea>
                </div>
                
                <button type="submit">Create Action</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</body>
</html>
