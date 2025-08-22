<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>짭코리아 - 회원가입</title>
<style>
  body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100vh;
      padding: 20px;
      box-sizing: border-box;
  }
  .site-header { text-align: center; margin-bottom: 20px; }
  .site-header h1 { font-size: 2.5em; color: #007bff; margin: 0; }
  .signup-panel {
      background-color: #fff; padding: 40px; border-radius: 8px;
      box-shadow: 0 0 10px rgba(0,0,0,.1);
      max-width: 450px; width: 100%; box-sizing: border-box;
  }
  h2 { text-align: center; color: #333; margin-top: 0; }
  form { display: flex; flex-direction: column; }
  label { margin-bottom: 8px; font-weight: bold; }
  input[type="text"], input[type="password"], select {
      padding: 10px; margin-bottom: 20px; border: 1px solid #ccc; border-radius: 4px;
      width: 100%; box-sizing: border-box;
      -webkit-appearance: none; -moz-appearance: none; appearance: none;
      background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%22292.4%22%20height%3D%22292.4%22%3E%3Cpath%20fill%3D%22%23007bff%22%20d%3D%22M287%20197.973L146.2%2057.173%205.4%20197.973H287z%22%2F%3E%3C%2Fsvg%3E');
      background-repeat: no-repeat; background-position: right 10px center; background-size: 12px;
  }
  input[type="submit"] {
      padding: 12px; border: none; background-color: #007bff; color: #fff;
      border-radius: 4px; cursor: pointer; font-size: 17px; font-weight: bold;
      transition: background-color .3s ease;
  }
  input[type="submit"]:hover { background-color: #0056b3; }
  @media (max-width: 600px) {
    .signup-panel { padding: 25px; margin: 10px; }
    .site-header h1 { font-size: 2em; }
    h2 { font-size: 1.5em; }
  }
</style>
</head>
<body>
  <header class="site-header">
    <h1>짭코리아</h1>
  </header>

  <div class="signup-panel">
    <h2>회원가입</h2>

    <form action="<c:url value='/signup'/>" method="post" id="signupForm">
      <%-- CSRF 토큰을 쓰는 경우:
      <input type="hidden" name="csrf" value="${csrfToken}" />
      --%>

      <label for="username">아이디</label>
      <input type="text" id="username" name="username" placeholder="아이디를 입력하세요" required />
      
      <label for="name">이름</label>
      <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required />

      <label for="password">비밀번호</label>
      <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required />

      <label for="confirm-password">비밀번호 확인</label>
      <input type="password" id="confirm-password" name="confirm-password" placeholder="비밀번호를 다시 입력하세요" required />

      <label for="interestLocation">관심 지역</label>
		<select id="interestLocation" name="interestLocation" required>
		  <option value="">지역을 선택하세요</option>
		  <c:forEach var="opt" items="${locations}">
		    <option value="${opt.id}" ${param.region == (opt.id) ? 'selected' : ''}>${opt.name}</option>
		  </c:forEach>
		</select>

      <label for="interstJobCategory">관심 직무</label>
		<select id="interstJobCategory" name="interstJobCategory" required>
		  <option value="">직무를 선택하세요</option>
		  <c:forEach var="opt" items="${jobs}">
		    <option value="${opt.id}" ${param['interstJobCategory'] == (opt.id) ? 'selected' : ''}>${opt.name}</option>
		  </c:forEach>
		</select>

      <input type="submit" value="회원가입" />
    </form>
  </div>
</body>
</html>