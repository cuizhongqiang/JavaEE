<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>中建材信云智联科技有限公司</title>

<!-- Custom styles for this template -->
<link href="${ctx }/static/others/css/dashboard.css" rel="stylesheet">
<link href="${ctx }/static/others/css/leftMenu.css" rel="stylesheet">

</head>
<body>

	<nav class="navbar navbar-blue navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" target="_blank" href="http://www.cnbmtech.com">中建材信云智联科技有限公司</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li>
						<a href="#" id="indexDropdownMenu1" data-toggle="dropdown">
							<span class="glyphicon glyphicon-asterisk"></span>
							系统
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu" role="menu"
							aria-labelledby="indexDropdownMenu1">
							<li role="presentation">
								<a role="menuitem" tabindex="-1" href="javascript:about_user('toUpdatePwd');">
									修改密码
								</a>
							</li>
							<li role="presentation">
								<a role="menuitem" tabindex="-1" href="javascript:about_user('toUpdate/update');">
									修改个人资料
								</a>
							</li>
							<li role="presentation" class="divider"></li>
							<li role="presentation">
								<a role="menuitem" tabindex="-1" href="${ctx }/a/logout">
									退出系统
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="${ctx }/a/index">
							<span class="glyphicon glyphicon-menu-left"></span>
							返回首页
						</a>
					</li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>

	<div id="wrapper" class="row">
		<div class="overlay"></div>
	
		<!-- Sidebar -->
		<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation" style="margin-top: 50px;">
		</nav>
	
		<button type="button" class="hamburger" data-toggle="offcanvas">
			<span id="hamburgerSpan" class="glyphicon glyphicon-menu-left"></span>
		</button>
		
		<div id="page-content" class="main">
			<h4 class="page-header">数字化名片与会议信息采集系统</h4>
			<p style="font-size: 24px;">${sessionScope.user.realName}，欢迎您！</p>
			<!-- 模态框（Modal） -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript">
	if ('${empty sessionScope.user }' == "true") {
		window.location.href = "${ctx}/a/login";
	}
	var leftMenu;
	$(function() {
		$.get('${ctx}/permission/getMenu/${sessionScope.user.id}', function(data) {
			leftMenu = $('#sidebar-wrapper').treeview({
				data : data,
				showIcon : true,
				showCheckbox : false,
				onNodeSelected : function(event, node) {
					if (node.href != "#") {
						getContent(node.href);
					}
				}
			});
		});
		
		var trigger = $('.hamburger');
		var triggerSpan = $('#hamburgerSpan');
		overlay = $('.overlay'),
		isClosed = false;
		hamburger_cross();
		$('#wrapper').toggleClass('toggled');

		trigger.click(function() {
			hamburger_cross();
		});

		function hamburger_cross() {

			if (isClosed == true) {
				overlay.hide();
				triggerSpan.removeClass('glyphicon-menu-left');
				triggerSpan.addClass('glyphicon-menu-right');
				isClosed = false;
			} else {
				overlay.show();
				triggerSpan.removeClass('glyphicon-menu-right');
				triggerSpan.addClass('glyphicon-menu-left');
				isClosed = true;
			}
		}

		$('[data-toggle="offcanvas"]').click(function() {
			$('#wrapper').toggleClass('toggled');
		});
	});

	function getContent(url) {
		$.get(url, function(data) {
			$("#page-content").html(data);
		});
	}
	
	function about_user(name) {
		$.get('${ctx}/user/' + name + '/${sessionScope.user.id}', function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
</script>
</html>