<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${attendReg.id }">
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
					<label for="type" class="col-sm-2 control-label">信息类别</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" placeholder="信息类别" id="type" name="type" value="${attendReg.type }">
					</div>
				</div>
				<div class="form-group">
					<label for="company" class="col-sm-2 control-label">公司名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="company" placeholder="公司名称" name="company" value="${attendReg.company }">
					</div>
				</div>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="realName" placeholder="姓名" name="realName" value="${attendReg.realName }">
					</div>
				</div>
				<div class="form-group">
					<label for="phone" class="col-sm-2 control-label">手机</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="phone" placeholder="手机" name="phone" value="${attendReg.phone }">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">电子邮箱</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="email" placeholder="电子邮箱" name="email" value="${attendReg.email }">
					</div>
				</div>
				<div class="form-group">
					<label for="position" class="col-sm-2 control-label">职位</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="position" placeholder="职位" name="position" value="${attendReg.position }">
					</div>
				</div>
				
				<c:forEach items="${attendReg.children }" var="child" varStatus="status">
					<input type="hidden" name="remarkName" value="${child.name }">
					<input type="hidden" name="remarkType" value="${child.type }">
					<div class="form-group">
						<label class="col-sm-2 control-label">${child.name }</label>
						<div class="col-sm-10">
							<c:choose>
								<c:when test="${child.type eq 'select' }">
									<select class="form-control" name="remarkValue">
										<c:forEach items="${my:findDictByPcode(child.dictCode) }" var="dict">
											<option value="${dict.name }" <c:if test="${child.value eq dict.name }">selected="selected"</c:if>>${dict.name }</option>
										</c:forEach>
									</select>
								</c:when>
								<c:when test="${child.type eq 'checkbox' }">
									<input type="hidden" id="remark${status.count }" name="remarkValue" value="${child.value }">
									<c:forEach items="${my:findDictByPcode(child.dictCode) }" var="dict">
										<input type="checkbox" name="remark${status.count }" value="${dict.name }" onclick="changeCheckboxValue($('#remark${status.count }'), $(this))" <c:if test="${fn:contains(child.value, dict.name) }">checked="checked"</c:if>> ${dict.name }
									</c:forEach>
								</c:when>
								<c:otherwise>
									<input type="text" class="form-control" name="remarkValue" placeholder="${child.name }" value="${child.value }">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:forEach>
				
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="type" class="col-sm-2 control-label">渠道名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${attendReg.channel }">
						</div>
					</div>
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${attendReg.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${attendReg.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
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
		$('#mainForm').bootstrapValidator({
			message: '这个值无效',
			feedbackIcons: {
				valid: 'glyphicon glyphicon-ok',
				invalid: 'glyphicon glyphicon-remove',
				validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				type: {
					validators: {
						notEmpty: {
							message: '类型不能为空'
						}
					}
				},
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
			$(this).button('loading');
			$.post("${ctx }/attendReg/save/${action}", $("#mainForm").serialize(), function(data) {
				if (data == "success") {
					cx();
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
	
	function changeCheckboxValue(hidden, currentObj) {
		var chk_value = [];
		$('input[name=' + currentObj.attr('name') + ']:checked').each(function() {
			chk_value.push($(this).val());
		});
		hidden.val(chk_value.join('、'));
	}
</script>