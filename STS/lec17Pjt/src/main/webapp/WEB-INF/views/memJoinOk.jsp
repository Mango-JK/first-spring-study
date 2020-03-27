<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<h1>mem Join OK !! </h1>
	ID : ${memId} <br />
	PW : ${memPw} <br />
	Mail : ${memMail} <br />
	Phone : ${memPhone} <br />
	
	<a href="/lec17/resources/memJoin.html"> Go MemberJoin </a>
</body>
</html>