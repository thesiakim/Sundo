<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ko">
	<head>
		<title>해역이용 영향평가 시스템</title>
		<link rel="icon" href="images/common/favicon.png">
		<link type="text/css" href="style/login/design.css" rel="stylesheet">
		<link type="text/css" href="style/login/style.css" rel="stylesheet">
		
		<!-- jquery -->
		<script src="script/lib/jquery-3.3.1.min.js"></script>
		<script src="script/lib/jquery-ui.min.js"></script>
		<script src="script/lib/bootstrap.bundle.min.js"></script>
		
		<script src="script/main/common-bizUtil.js"></script>
		
		<script src="script/main/login/login.js"></script>
		<script src="script/main/login/sessionCnt.js"></script>
	</head>
	
	<body class="mainWrap">
		<div class="loginPart">
			<div class="inBox">
				<div class="form-login" name="login-infor" method="post" action="" role="form">
					<img src="images/common/logo.png" style="width: 70px;vertical-align: middle;">
					<span style="font-size: 20px;color: white;font-weight: 600;vertical-align: middle;">해역이용 영향평가 시스템</span>
					<div class="formGroup" id="register_form">
						<div class="inputWrap id">
							<label for="user_id">아이디</label>
							<span class="icon-Man"></span>
							<input type="text" maxlength="20" name="user_id" id="user_id" value="" autofocus onKeyPress="if(event.keyCode==13){doLogin(this)}" placeholder="아이디를 입력하세요">
						</div>
						<div class="inputWrap pw">
							<label for="user_pw">비밀번호</label>
							<span class="icon-Lock"></span>
							<input type="password" name="user_pw" id="user_pw" value="" onKeyPress="if(event.keyCode==13){doLogin(this)}" placeholder="비밀번호를 입력하세요">
						</div>
					</div>
					<div class="btn-wrap">
						<a href="#" onclick="javascript:doLogin()"><span class="icon-login"></span><span>로그인</span></a>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>