<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${user.id }">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					修改密码
				</h4>
			</div>
			<div class="modal-body">
				<div id="pwdAlert" class="alert alert-warning">
					<a href="#" class="close" onclick="javascript:$('#pwdAlert').hide();">&times;</a>
					<strong>警告！</strong>原密码输入有误！
				</div>
				<div class="form-group">
					<label for="plainPassword" class="col-sm-2 control-label">原密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" placeholder="原密码" id="oldPassword" name="oldPassword">
					</div>
				</div>
				<div class="form-group">
					<label for="plainPassword" class="col-sm-2 control-label">新密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" placeholder="密码" name="plainPassword">
					</div>
				</div>
				<div class="form-group">
					<label for="confirmPassword" class="col-sm-2 control-label">确认新密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" placeholder="确认密码" name="confirmPassword">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<c:if test="${action ne 'detail' }">
					<button type="button" class="btn btn-primary" id="submitButton"
						data-loading-text="Loading..."
						data-container="body" data-toggle="popover" data-placement="right"
						data-content="保存成功">提交保存</button>
				</c:if>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	</form>
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
				oldPassword: {
	                validators: {
	                    notEmpty: {
	                        message: '原密码是必需的，不能是空的'
	                    }
	                }
	            },
				plainPassword: {
	                validators: {
	                    notEmpty: {
	                        message: '新密码是必需的，不能为空'
	                    },
	                    identical: {
	                        field: 'confirmPassword',
	                        message: '新密码和确认不相同'
	                    },
                        stringLength: {
                            min: 6,
                            max: 30,
                            message: '密码长度必须大于6，小于30'
                        }
	                }
	            },
	            confirmPassword: {
	                validators: {
	                    notEmpty: {
	                        message: '确认新密码是必需的，不能为空'
	                    },
	                    identical: {
	                        field: 'plainPassword',
	                        message: '新密码和确认不相同'
	                    }
	                }
	            }
			}
		});

		$('#pwdAlert').hide();//隐藏警告框
		
		$("#submitButton").click(function() {
			/*手动验证表单，当是普通按钮时。*/
			var bootstrapValidator = $("#mainForm").data('bootstrapValidator');
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return false;
			}
			$("#submitButton").button('loading');
			$.post("${ctx }/user/updatePwd/" + $("#oldPassword").val(), $("#mainForm").serialize(), function(data) {
				if (data == "success") {
					$("#submitButton").popover('show');
					setTimeout(function() {
						$("#submitButton").popover('hide');
						$('#myModal').modal('hide');
					}, 1000);
				} else if (data == "fail") {
					$("#pwdAlert").show();
				}
			});
			$("#submitButton").button('reset');
		});
	});
</script>