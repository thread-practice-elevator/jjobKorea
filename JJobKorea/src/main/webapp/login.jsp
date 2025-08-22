<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>짭코리아 로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #74ebd5 0%, #ACB6E5 100%);
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        h1 {
            color: white;
            font-size: 42px;
            margin-bottom: 30px;
            text-shadow: 2px 2px 6px rgba(0,0,0,0.2);
        }
        .login-box {
            width: 350px;
            background: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0px 4px 20px rgba(0,0,0,0.2);
            text-align: center;
            animation: fadeIn 0.8s ease-in-out;
        }
        .login-box h2 {
            margin-bottom: 20px;
            color: #333;
        }
        .login-box input[type="text"], 
        .login-box input[type="password"] {
            width: 90%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 8px;
            outline: none;
            transition: border 0.3s;
        }
        .login-box input[type="text"]:focus,
        .login-box input[type="password"]:focus {
            border: 1px solid #74b9ff;
        }
        .login-box button {
            width: 95%;
            padding: 12px;
            background: #74b9ff;
            border: none;
            border-radius: 8px;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .login-box button:hover {
            background: #0984e3;
        }
        .error {
            color: red;
            font-size: 14px;
            margin-bottom: 15px;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
    <!-- 타이틀 -->
    <h1>짭코리아</h1>

    <div class="login-box">
        <h2>로그인</h2>

        <% String error = (String) request.getAttribute("loginError"); %>
        <% if (error != null) { %>
            <p class="error"><%= error %></p>
        <% } %>

        <form action="<%= request.getContextPath() %>/login" method="post">
            <input type="text" id="username" name="username" placeholder="아이디 입력" required><br>
            <input type="password" id="password" name="password" placeholder="비밀번호 입력" required><br>
            <button type="submit">로그인</button>
        </form>
    </div>
</body>
</html>
