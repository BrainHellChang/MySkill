<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>인증완료</h3>
	<h3>엑세스 토큰 : ${responseToken.access_token }</h3>
	<h3>사용자 번호 : ${responseToken.user_seq_no }</h3>
	
	<hr>
	
	<form method="get" action="userInfo">
		<%-- 필요 파라미터는 입력데이터 없이 hidden 속성으로 전달 --%>
		<input type="hidden" name="access_token" value="${responseToken.access_token }">
		<input type="hidden" name="user_seq_no" value="${responseToken.user_seq_no }">
		<input type="submit" value="사용자정보조회">
	</form>
	<hr>
	<!-- 2.2.3 등록계좌조회 API -->
	<form method="get" action="accountList">
		<%-- 필요 파라미터는 입력데이터 없이 hidden 속성으로 전달 --%>
		<input type="hidden" name="access_token" value="${responseToken.access_token }">
		<input type="hidden" name="user_seq_no" value="${responseToken.user_seq_no }">
		<input type="hidden" name="include_cancel_yn" value="Y">
		<input type="hidden" name="sort_order" value="D">
		<input type="submit" value="등록계좌조회">
	</form>
</body>
</html>












