<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
<%@ include file="/base.jsp"%>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>渠道登记</title>

<link rel="stylesheet" href="${ctx}/static/others/css/style.css">

</head>

<body>
	<div class="container">
		<form id="mainForm" class="form-horizontal" role="form">
			<div class="form-top">
				<div class="form-top-right">
					<i class="glyphicon glyphicon-user"></i>
				</div>
				<div class="form-top-left">
					<h3>${template.title }</h3>
				</div>
			</div>
			<div class="form-bottom">
				<div class="form-group">
					<label for="company">渠道名称</label>
					<input type="text" class="form-control" id="channel" name="channel" placeholder="渠道名称">
				</div>
				<div class="form-group">
					<button class="btn btn-lg btn-info btn-block" type="button" id="submitButton">生成二维码</button>
		   		</div>
			</div>
		</form>
	</div>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<script type="text/javascript">
	$(function(){
		$('#mainForm').bootstrapValidator({
			message: '这个值无效',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				channel: {
					validators: {
						notEmpty: {
							message: '请输入渠道名称'
						}
					}
				}
			}
		});
		
		$("#submitButton").click(function() {
			/*手动验证表单，当是普通按钮时。*/
			var bootstrapValidator = $("#mainForm").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return false;
			}
			$.get("${ctx }/template/channel/${id}/" + $('#channel').val(), function(data) {
				$("#myModal").html(data);
			});
			$('#myModal').modal('show');
		});
	});
	</script>
</body>
</html>
