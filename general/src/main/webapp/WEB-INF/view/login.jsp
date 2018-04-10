<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="/static/images/favicon.ico">

<title>中建材信云智联科技有限公司</title>

<!-- Custom styles for this template -->
<link href="${ctx }/static/others/css/signin.css" rel="stylesheet">

</head>

<body>
	<div class="container" style="background-image: url('../../static/images/login_bg.jpg');width: 1344px;height: 756px;background-repeat: no-repeat;background-position: center;">
		<form id="loginForm" class="form-signin">
			
			<div id="uAlert" class="alert alert-warning form-signin-heading form-group">
				<a href="#" class="close" onclick="javascript:$('#uAlert').hide();">&times;</a>
				<strong>警告！</strong>用户名不存在！
			</div>
			
			<div id="pAlert" class="alert alert-warning form-signin-heading form-group">
				<a href="#" class="close" onclick="javascript:$('#pAlert').hide();">&times;</a>
				<strong>警告！</strong>密码输入有误！
			</div>
			
			<div id="dAlert" class="alert alert-warning form-signin-heading form-group">
				<a href="#" class="close" onclick="javascript:$('#dAlert').hide();">&times;</a>
				<strong>警告！</strong>该用户已被禁用！
			</div>
			
			<div class="form-group" style="margin-bottom: 30px;">
				<label for="inputUserName" class="sr-only">用户名</label>
				<input type="text" id="inputUserName" name="userName" class="form-control" placeholder="用户名" required autofocus/>
			</div>
			<div class="form-group" style="margin-bottom: 30px;">
				<label for="inputPassword" class="sr-only">密码</label>
				<input type="password" id="inputPassword" name="plainPassword" class="form-control" placeholder="密码" required/>
			</div>
			<!-- <div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div> -->
			<div class="form-group">
				<button id="submitButton" class="btn btn-lg btn-primary btn-block" type="button">登录</button>
			</div>
		</form>
		<p class="copyright">Copyright　© 2018　中建材信云智联科技有限公司　技术支持：010-68738980</p>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$('#loginForm').bootstrapValidator({
		message: '这个值无效',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			userName: {
				validators: {
					notEmpty: {
						message: '请输入用户名'
					}
				}
			},
			plainPassword: {
				validators: {
					notEmpty: {
						message: '请输入密码'
					}
				}
			}
		}
	});
	
	$('#uAlert').hide();
	$('#pAlert').hide();
	$('#dAlert').hide();//隐藏警告框
	
	$("#submitButton").click(function() {
		/*手动验证表单，当是普通按钮时。*/
		var bootstrapValidator = $("#loginForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		if (!bootstrapValidator.isValid()) {
			return false;
		}
		$.post("${ctx }/a/login", $("#loginForm").serialize(), function(data) {
			if (data == "errorUserName") {
				$("#uAlert").show();
			} else if (data == "errorPassword") {
				$("#pAlert").show();
			} else if (data == "disabled") {
				$("#dAlert").show();
			} else if (data == "success") {
				window.location.href = "${ctx}/a/index";
			}
		});
	});
	
	document.onkeydown = function(event) {
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 27) { // 按 Esc 
			//要做的事情
		}
		if (e && e.keyCode == 113) { // 按 F2 
			//要做的事情
		}
		if (e && e.keyCode == 13) { // enter 键
			$("#submitButton").click();
		}
	};
});
</script>
</html>
