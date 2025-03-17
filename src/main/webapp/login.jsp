<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="resources/css/style.css">
</head>
<body>
    <div class="login-container">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>Username: <span class="required">*</span></label>
                <input type="text" name="username" required>
            </div>
            <div class="form-group">
                <label>Password: <span class="required">*</span></label>
                <input type="password" name="password" required>
            </div>
            <div class="forgot-password">
                <a href="#">Forgotten your password?</a>
            </div>
            <div class="form-group">
                <button type="submit">Login »</button>
            </div>
        </form>
    </div>
</body>
</html>
