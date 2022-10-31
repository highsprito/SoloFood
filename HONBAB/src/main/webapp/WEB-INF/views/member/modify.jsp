<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/login.jsp</title>
<style type="text/css">
.login {
	text-align: center;
	height: 300px;
}
</style>
<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
		$('#business').hide(); 
		$("input[name='chk_join']").change(function(){
			if($("input[name='chk_join']:checked").val() == '개인회원'){
				$('#business').hide();
				$('#personal').show();
			}
			else if($("input[name='chk_join']:checked").val() == '사업자회원'){
				$('#personal').hide();
				$('#business').show();
			}
		});
});
</script>
</head> 
<body>
	<c:import url="../main/header.jsp"/>
	<div class="wrap login">
		<h1>로그인</h1>
		<br>
		<input type="radio" name="chk_join" value="개인회원" checked="checked">개인회원
		<input type="radio" name="chk_join" value="사업자회원">사업자회원
		<br><br>
		<div id="personal">
			<form action="${contextPath }/member/user_check" method="post">
				<input type="text" name="id" placeholder="아이디"><br><br>
				<input type="password" name="pw" placeholder="비밀번호"><br><br>
				<input type="submit" value="login"> &nbsp; 
				<a href="${contextPath }/member/register_form">회원가입</a>
			</form>
		</div>
		<div id="business">
			<form action="${contextPath }/member/bizuser_check" method="post">
				<input type="text" name="id" placeholder="사업자아이디"><br><br>
				<input type="password" name="pw" placeholder="사업자비밀번호"><br><br>
				<input type="submit" value="login"> &nbsp; 
				<a href="${contextPath }/member/register_form">회원가입</a>
			</form>
		</div>
	</div>
	<c:import url="../main/footer.jsp"/>
</body>
</html>