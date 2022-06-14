<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>금융 Open API</title>
</head>
<body>
	<%-- JSTL 을 사용하여 accessToken 객체가 없을 경우 "토큰발급" 버튼 표시 --%>
	<c:if test="${accessToken eq null }">
		<h3>토큰 미발급 상태이므로 토큰 발급 필수!</h3>
		<%-- /authorize URL 요청을 통해 OAuth 인증 요청 작업 수행 --%>
		<form method="get" action="https://testapi.openbanking.or.kr/oauth/2.0/authorize">
			<%-- 필요 파라미터는 입력데이터 없이 hidden 속성으로 전달 --%>
			<input type="hidden" name="response_type" value="code">
			<input type="hidden" name="client_id" value="4066d795-aa6e-4720-9383-931d1f60d1a9">
			<input type="hidden" name="redirect_uri" value="http://localhost:8080/fintech/callback">
			<input type="hidden" name="scope" value="login inquiry transfer">
			<input type="hidden" name="state" value="12345678123456781234567812345678">
			<input type="hidden" name="auth_type" value="0">
			<input type="submit" value="토큰발급">
		</form>
	</c:if>
	
	<%-- JSTL 을 사용하여 accessToken 객체가 있을 경우 "계좌조회" 버튼 표시 --%>
	<c:if test="${accessToken ne null }">
		<h3>토큰 발급 상태이므로 계좌 접근 가능!</h3>
		<form action="">
			<input type="submit" value="계좌조회">
		</form>
	</c:if>
</body>
</html>







