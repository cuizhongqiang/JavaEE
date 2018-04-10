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
					<c:choose>
						<c:when test="${action eq 'create' }">
							新增
						</c:when>
						<c:when test="${action eq 'update' }">
							修改
						</c:when>
						<c:when test="${action eq 'detail' }">
							查看
						</c:when>
					</c:choose>
				</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<label for="type" class="col-sm-2 control-label">用户名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="userName" placeholder="用户名" name="userName" value="${user.userName }">
					</div>
				</div>
				<c:if test="${action ne 'detail' }">
				<div class="form-group">
					<label for="plainPassword" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="plainPassword" placeholder="密码" name="plainPassword" <c:if test="${action eq 'update' }">disabled</c:if>>
					</div>
				</div>
				<div class="form-group">
					<label for="confirmPassword" class="col-sm-2 control-label">确认密码</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="confirmPassword" placeholder="确认密码" name="confirmPassword" <c:if test="${action eq 'update' }">disabled</c:if>>
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">真实姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="realName" placeholder="真实姓名" name="realName" value="${user.realName }">
					</div>
				</div>
				<div class="form-group">
					<label for="gender" class="col-sm-2 control-label">性别</label>
					<div class="col-sm-10">
						<select class="form-control" id="gender" name="gender">
							<option value="">请选择</option>
							<option value="男" <c:if test="${user.gender eq '男' }">selected="selected"</c:if>>男</option>
							<option value="女" <c:if test="${user.gender eq '女' }">selected="selected"</c:if>>女</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="birthday" class="col-sm-2 control-label">生日</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="birthday" placeholder="出生日期" name="birthday" value="<fmt:formatDate value='${user.birthday }' pattern='yyyy-MM-dd'/>">
					</div>
				</div>
				<div class="form-group">
					<label for="phone" class="col-sm-2 control-label">手机</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="phone" placeholder="手机" name="phone" value="${user.phone }">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">电子邮箱</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="email" placeholder="电子邮箱" name="email" value="${user.email }">
					</div>
				</div>
				<div class="form-group">
					<label for="userInfo" class="col-sm-2 control-label">个人信息</label>
					<div class="col-sm-10">
						<textarea style="width: 100%;height: 90px;" id="userInfo" name="userInfo" maxlength="500">${user.userInfo }</textarea>
					</div>
				</div>
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${user.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${user.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
				</c:if>
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
		laydate.render({
			elem : '#birthday',
			calendar : true
		});
		
		$('#mainForm').bootstrapValidator({
			message: '这个值无效',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				userName: {
					message: '用户名无效',
	                validators: {
	                    notEmpty: {
	                        message: '用户名是必需的，不能是空的'
	                    },
	                    stringLength: {
	                        min: 5,
	                        max: 30,
	                        message: '用户名必须大于5，长度小于30'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_\.]+$/,
	                        message: '用户名只能由字母、数字、点和下划线组成'
	                    },
	                    remote: {
	                        type: 'POST',
	                        url: '${ctx}/user/uniqueUserName',
	                        message: '用户名已存在',
	                        delay: 1000,
	                        data: function(validator) {
	                        	return {
	                        		userName: $("#userName").val(),
	                        		id: '${empty user.id ? 0 : user.id }'
	                        	};
	                        }
	                    }
	                }
				},
				plainPassword: {
	                validators: {
	                    notEmpty: {
	                        message: '密码是必需的，不能是空的'
	                    },
	                    identical: {
	                        field: 'confirmPassword',
	                        message: '密码和确认不相同'
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
	                        message: '确认密码是必需的，并且不能为空'
	                    },
	                    identical: {
	                        field: 'plainPassword',
	                        message: '密码和确认不相同'
	                    }
	                }
	            },
				realName: {
					message: '姓名无效',
	                validators: {
	                    notEmpty: {
	                        message: '姓名是必需的，不能是空的'
	                    },
	                    stringLength: {
	                        min: 2,
	                        max: 30,
	                        message: '姓名必须大于等于2，长度小于30'
	                    }
	                }
				},
				phone: {
	                validators: {
	                	notEmpty: {
							message: '电话号码不能为空'
						},
	                    phone: {
	                        message: '输入不是有效的电话号码',
	                        country: 'CN'
	                    }
	                }
	            },
	            email: {
	                validators: {
	                    emailAddress: {
	                        message: '输入不是一个有效的电子邮件地址'
	                    }
	                }
	            }
			}
		});

		var bootstrapValidator = $("#mainForm").data('bootstrapValidator');
		setTimeout(function() {//remote请求--"延迟"+"主动"--验证
			if ('${action}' == "update") {
				bootstrapValidator.validate();
			}
		}, 1000);
		$("#submitButton").click(function() {
			/*手动验证表单，当是普通按钮时。*/
			bootstrapValidator.validate();
			if (!bootstrapValidator.isValid()) {
				return false;
			}
			$(this).button('loading');
			$.post("${ctx }/user/save/${action}", $("#mainForm").serialize(), function(data) {
				if (data == "success") {
					if (typeof cx == "function") {
						cx();
					}
					$("#submitButton").popover('show');
					setTimeout(function() {
						$("#submitButton").popover('hide');
						//if ('${action}' == 'create') {
						$('#myModal').modal('hide');
						//}
						$("#submitButton").button('reset');
					}, 1000);
				}
			});
		});
	});
</script>