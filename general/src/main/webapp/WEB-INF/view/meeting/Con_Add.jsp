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

<title>用户注册</title>

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
			
				<input type="hidden" name="type" value="${template.title }">
				<input type="hidden" name="channel" value="${channel }">
				
				<c:forEach items="${template.templateCollect }" var="templateCollect" varStatus="status">
					<c:set value="${templateCollect.collect }" var="collect"/>
					<input type="hidden" name="remarkName" value="${collect.name }">
					<input type="hidden" name="remarkType" value="${collect.type }">
					<input type="hidden" name="remarkDictCode" value="${empty collect.dictCode ? 'none' : collect.dictCode }">
					<div class="form-group">
						<label>${collect.name }</label>
						<c:choose>
							<c:when test="${collect.type eq 'select' }">
								<select class="form-control" name="remarkValue">
									<c:forEach items="${my:findDictByPcode(collect.dictCode) }" var="dict">
										<option value="${dict.name }">${dict.name }</option>
									</c:forEach>
								</select>
							</c:when>
							<c:when test="${collect.type eq 'checkbox' }">
								<input type="hidden" id="remark${status.count }" name="remarkValue">
								<br>
								<c:forEach items="${my:findDictByPcode(collect.dictCode) }" var="dict">
									<input type="checkbox" name="remark${status.count }" value="${dict.name }" onclick="changeCheckboxValue($('#remark${status.count }'), $(this))"> ${dict.name }
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="form-control" name="remarkValue" placeholder="${collect.name }">
							</c:otherwise>
						</c:choose>
					</div>
				</c:forEach>
		
				<div class="form-group">
					<label for="company">公司名称</label>
					<input type="text" class="form-control" id="company" placeholder="公司名称" name="company">
				</div>
				<div class="form-group">
					<label for="realName">姓名</label>
					<input type="text" class="form-control" id="realName" placeholder="姓名" name="realName">
				</div>
				<div class="form-group">
					<label for="phone">手机</label>
					<input type="text" class="form-control" id="phone" placeholder="手机" name="phone">
				</div>
				<div class="form-group">
					<label for="email">电子邮箱</label>
					<input type="text" class="form-control" id="email" placeholder="电子邮箱" name="email">
				</div>
				<div class="form-group">
					<label for="position">职位</label>
					<input type="text" class="form-control" id="position" placeholder="职位" name="position">
				</div>
				<div class="form-group">
					<button class="btn btn-lg btn-info btn-block" type="button" id="submitButton">注册</button>
		   		</div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
	$(function(){
		if ('${template.status}' == 0) {
			$(".container").addClass("sr-only");
			alert("会议已关闭");
		} else if ('${ipFlag}' == 'true') {
			$(".container").addClass("sr-only");
			alert("当前设备已注册");
		}
		$('#mainForm').bootstrapValidator({
			message: '这个值无效',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				company: {
					validators: {
						notEmpty: {
							message: '公司名称不能为空'
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
						notEmpty: {
							message: '邮箱不能为空'
						},
	                    emailAddress: {
	                        message: '输入不是一个有效的电子邮件地址'
	                    }
	                }
	            },
	            position: {
	                validators: {
	                    notEmpty: {
	                        message: '职位不能为空'
	                    }
	                }
	            },
	            remarkValue: {
	                validators: {
	                    notEmpty: {
	                        message: '此项为必填项'
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
			$.post("${ctx }/template/con_add_save", $("#mainForm").serialize(), function(data) {
				if (data == "success") {
					alert('注册成功!');
					//window.location.href = 'http://mp.weixin.qq.com/s/Vn8zWepeWcd5Xz5t66LE9Q';
					if (typeof WeixinJSBridge != "undefined") {
						WeixinJSBridge.call('closeWindow');
					} else {
						window.close();
					}
				}
			});
		});
	});
	
	function changeCheckboxValue(hidden, currentObj) {
		var chk_value = [];
		$('input[name=' + currentObj.attr('name') + ']:checked').each(function() {
			chk_value.push($(this).val());
		});
		hidden.val(chk_value.join('、'));
	}
	</script>
</body>
</html>
