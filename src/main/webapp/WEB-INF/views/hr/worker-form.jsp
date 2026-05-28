<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add New Worker - HR</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; }
        .navbar { background: #16a085; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin-left: 20px; }
        .container { max-width: 800px; margin: 30px auto; padding: 0 20px; }
        .form-card { background: white; padding: 30px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input, select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        button { padding: 12px 30px; background: #16a085; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        button:hover { background: #138f75; }
        h2 { margin-bottom: 20px; color: #333; }
    </style>
</head>
<body>
    <div class="navbar">
        <h1>👥 Add New Worker</h1>
        <div>
            <a href="/hr/workers">Back to Workers</a>
            <a href="/logout">Logout</a>
        </div>
    </div>
    
    <div class="container">
        <div class="form-card">
            <h2>Worker Information</h2>
            <form action="/hr/workers" method="post">
                <h3>User Account</h3>
                <div class="form-group">
                    <label>Username</label>
                    <input type="text" name="username" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" required>
                </div>
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="fullName" required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" required>
                </div>
                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="text" name="phoneNumber" required>
                </div>
                
                <h3 style="margin-top: 30px;">Employment Details</h3>
                <div class="form-group">
                    <label>Employee ID</label>
                    <input type="text" name="employeeId" required>
                </div>
                <div class="form-group">
                    <label>Department</label>
                    <input type="text" name="department" required>
                </div>
                <div class="form-group">
                    <label>Position</label>
                    <input type="text" name="position" required>
                </div>
                <div class="form-group">
                    <label>Hire Date</label>
                    <input type="date" name="hireDate" required>
                </div>
                <div class="form-group">
                    <label>Base Salary (RWF)</label>
                    <input type="number" name="baseSalary" step="0.01" required>
                </div>
                
                <button type="submit">Create Worker</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</body>
</html>
