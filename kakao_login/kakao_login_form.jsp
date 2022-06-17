<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="https://kauth.kakao.com/oauth/authorize
			?client_id=f9f5f6880f6e92a9e2fabbf34781366d
			&redirect_uri=http://localhost:8080/cono/kakao_callback
			&response_type=code">
<!-- 			client_id -> REST api 키 -->
<!-- 			redirect_uri -> 토큰을 받기 위해 보내고싶은 controller mapping 주소  -->
			<button class="btn btn-warning" name="kakao">카카오톡으로 가입</button>
	</a>
</body>
</html>