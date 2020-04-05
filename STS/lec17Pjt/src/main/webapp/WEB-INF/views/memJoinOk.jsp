<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<h1>mem Join OK !! </h1>
	ID : ${mem.memId} <br />
	PW : ${mem.memPw} <br />
	Mail : ${mem.memMail} <br />
	Phone : ${mem.memPhone1} <br />
	
	<a href="/lec17/resources/memJoin.html"> Go MemberJoin </a>
</body>
</html>