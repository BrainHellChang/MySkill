<%@page import="member.MemberBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/join.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var checkIdResult = false, checkRetypePassResult = false; 
	
	function checkRetypePass(pass2) {
		var pass = document.fr.pass.value;
		var spanElem = document.getElementById("checkRetypePassResult");
		
		if(pass == pass2) {
			spanElem.innerHTML = "패스워드 일치";
			spanElem.style.color = "BLUE";
			checkRetypePassResult = true;
		} else {
			spanElem.innerHTML = "패스워드 불일치";
			spanElem.style.color = "RED";
			checkRetypePassResult = false;
		}
	}k
	
	function openCheckIdWindow() {
		window.open("check_id.jsp","","width=400,height=250");
	}
	
	function checkSubmit() {
		if(checkIdResult == false) { 
			alert("아이디 중복확인 필수!");
			document.fr.id.focus();
			return false;
		} else if(checkRetypePassResult == false) {
			alert("패스워드 확인 필수!");
			document.fr.pass2.focus();
			return false; 
		}
	}
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function getAddressInfo(){
    new daum.Postcode({
        oncomplete: function(data) {
        	      	
            var value = "";
            var jusoSangsae = "";
            var str = data.jibunAddress;   // 풀주소 저장
//             str = str.split(" ");          // 공백으로 짤라준다

            // -> 공백으로 짜르는 이유는 해당 API에서 (서울특별시 강남동 강남구99-9) 이런식으로 띄어쓰기로 시 군 구 를 반환해주기 때문.
            // -> 서울특별시 강남동 강남구99-9를 띄어쓰기로 파싱하면 {"서울특별시","강남동","강남구","99-9"} 이런으로 파싱된다                


            if(data.userSelectedType == "J"){   // 사용자가 지번을 클릭했다면
            	value = data.address;
//                 for(var i =0; i<str.length; i++){
// //                     if(str[i] == data.bname){     //  data.bname 즉 동정보하고 str[i]가 동일 문자일시
// //                         value = i;                // 그위치를 저장한다
// //                     }
// 					value += str[i];
//                 }
            }else{
            	value = data.roadAddress;
//                 str = data.roadAddress;        // 사용자가 도로명을 클릭했다면
//                 str = str.split(" ");         
//                 for(var i=0;i<str.length;i++){
// //                     if(str[i] == data.roadname){ // data.roadname 즉 도로명의 동정보하고 str[i]가 동일 문자일시
// //                         value = i;               // 그위치를 저장한다
// //                     }
// 					value += str[i];
//                 }
            }

			document.getElementById("post_code").value = data.zonecode;
            document.getElementById("add1").value = value;
                
        }
    }).open();
}


</script>
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"></jsp:include>
		<!-- 헤더 들어가는곳 -->
		  
		<!-- 본문들어가는 곳 -->
		  <!-- 본문 메인 이미지 -->
		  <div id="sub_img_member"></div>
		  <!-- 왼쪽 메뉴 -->
		  <nav id="sub_menu">
		  	<ul>
		  		<li><a href="#">Join us</a></li>
		  		<li><a href="#">Privacy policy</a></li>
		  	</ul>
		  </nav>
		  <!-- 본문 내용 -->
		  <article>
		  	<h1>Join Us</h1>
		  	<form action="joinPro.jsp" method="post" id="join" name="fr" onsubmit="return checkSubmit()">
		  		<fieldset>
		  			<legend>Basic Info</legend>
		  			<label>User Id</label>
		  			<input type="text" name="member_id" class="id" id="id" required="required" readonly="readonly">
		  			<input type="button" value="dup. check" class="dup" id="btn" onclick="openCheckIdWindow()"><br>
		  			<!-- 버튼 클릭 시 새 창 띄우기(크기 : 350 * 200, 파일 : check_id.jsp) -->
		  			
		  			<label>Password</label>
		  			<input type="password" name="member_pwd" id="pass" required="required"><br> 			
		  			
		  			<label>Retype Password</label>
		  			<input type="password" name="member_pwd2" required="required" onkeyup="checkRetypePass(this.value)">
		  			<span id="checkRetypePassResult"><!-- 패스워드 일치 여부 표시 영역 --></span><br>
		  			<!-- pass2 항목에 텍스트 입력할 때마다 자바스크립트의 checkRetypePass() 함수 호출 -->
		  			
		  			<label>Name</label>
		  			<input type="text" name="member_name" id="name" required="required"><br>
		  			
		  			<label>Gender</label>
		  			<input type="radio" name="member_gender" value="남">남
		  			<input type="radio" name="member_gender" value="여">여 <br>
		  			
		  			<label>Birth</label>
		  			<input type="text" name="member_birth" required="required"><br>
		  								  			
		  			<label>E-Mail</label>
		  			<input type="email" name="member_email" id="email" required="required"><br>
		  			
		  			<label>Phone Number</label>
		  			<input type="text" name="member_cp"  class="cp" id="cp" required="required"><br>
		  		</fieldset>
		  		
			  	<fieldset>
		  			<legend>Optional</legend>
		  			<label>Post code</label>
		  			<input type="text" name="post_code" id="post_code" readonly="readonly"  placeholder="검색 버튼 클릭">
		  			<input type="button" value="search" class="dup" onclick="getAddressInfo()"><br>
		  			<label>Address</label>
		  			<input type="text" name="member_add1" id="add1" class="add1" ><br>
		  			<label>Detail Address</label>
		  			<input type="text" name="member_add2" placeholder="상세주소 입력"><br>
					
		  		</fieldset>

		  		<div class="clear"></div>
		  		<div id="buttons">
		  			<input type="submit" value="Submit" class="submit">
		  			<input type="reset" value="Cancel" class="cancel">
		  		</div>
		  	</form>
		  </article>
		  
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"></jsp:include>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


