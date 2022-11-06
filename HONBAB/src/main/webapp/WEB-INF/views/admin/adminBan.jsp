<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 삭제(정욱)</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	function deleteId() {
		if (!confirm("정말 삭제하시겠습니까?")) {
			alert("취소되었습니다.");
		} else {			
			alert("삭제완료");
			let form = document.getElementById("form");
			form.submit();
			//window.location.href = '${contextPath}/admin/adminBan';
		}
	}
	
	function deletePartnerId() {
		if (!confirm("정말 삭제하시겠습니까?")) {
			alert("취소되었습니다.");
		} else {			
			alert("삭제완료");
			let form = document.getElementById("form");
			form.submit();
			//window.location.href = '${contextPath}/admin/adminBan';
		}
	}
	
	
</script>
</head>
<body>
	<table border="1" width="500px">
		<tr>
			<td><br><span style="color: green; font-weight: bold;">
					회원 강제 탈퇴<%-- (${generalDelete.id }) --%></span>
				<form action="${contextPath}/ban/adminBan" id="form"  method="get">
					<br> -회원 아이디- 
					<input type="text" name="id" placeholder="탈퇴시킬 아이디입력">
					<br>
					<br> 
					<input type="button" value="삭제" onclick="deleteId();"> 
					<br>
					<br>
				</form></td>
		</tr>
	</table>
	<br>
	<br>

	<table border="1" width="500px">
		<tr>
			<td><br> <span style="color: green; font-weight: bold;">회원
					강제 탈퇴(파트너)</span>
				<form action="${contextPath}/ban/adminPartnerBan" id="form"  method="get">
					<br> -회원 아이디- 
					<input type="text" name="id" placeholder="탈퇴시킬 아이디입력">
					<br>
					<br> 
					<input type="button" value="삭제" onclick="deletePartnerId();"> 
					<br>
					<br>
				</form></td>
		</tr>
	</table>
	<br>
	<br>

	<table border="1" width="500px">
		<tr>
			<td><br> <span style="color: green; font-weight: bold;">파트너승인</span>
				<form action="email">
					<br> -파트너 이메일 전송(가입 승인)- <input type="email" name="email"
						placeholder="이메일 주소입력"><br> <br>
					<button type="submit" name="submit">승인 메일 전송</button>
					<br> <br>
				</form></td>
		</tr>
	</table>
</body>

</body>
</html>