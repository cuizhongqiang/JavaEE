<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${permission.id }">
	<input type="hidden" name="parentId" value="${permission.parentId }">
	<input type="hidden" name="level" value="${permission.level }">
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
					<label for="name" class="col-sm-2 control-label">名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="name" placeholder="名称" name="name" value="${permission.name }">
					</div>
				</div>
				<div class="form-group">
					<label for="type" class="col-sm-2 control-label">类型</label>
					<div class="col-sm-10">
						<select class="form-control" id="type" name="type">
							<option value="M" <c:if test="${permission.type eq 'M' }">selected="selected"</c:if>>菜单类型</option>
							<option value="F" <c:if test="${permission.type eq 'F' }">selected="selected"</c:if>>功能类型</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="code" class="col-sm-2 control-label">编码</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="code" placeholder="编码" name="code" value="${permission.code }">
					</div>
				</div>
				<div class="form-group">
					<label for="url" class="col-sm-2 control-label">路径</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="url" placeholder="路径" name="url" value="${permission.url }">
					</div>
				</div>
				<div class="form-group">
					<label for="icon" class="col-sm-2 control-label">图标</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="icon" placeholder="图标" name="icon" value="${permission.icon }">
					</div>
				</div>
				<div class="form-group">
					<label for="icon" class="col-sm-2 control-label">排序</label>
					<div class="col-sm-10">
						<input type="number" class="form-control" id="sort" placeholder="排序" name="sort" value="${permission.sort }">
					</div>
				</div>
				<div class="form-group">
					<label for="userInfo" class="col-sm-2 control-label">描述</label>
					<div class="col-sm-10">
						<textarea style="width: 100%;height: 90px;" id="description" name="description" maxlength="500">${permission.description }</textarea>
					</div>
				</div>
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${permission.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${permission.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
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
				name: {
	                validators: {
	                    notEmpty: {
	                        message: '名称是必需的，不能是空的'
	                    }
	                }
				},
				code: {
	                validators: {
	                    notEmpty: {
	                        message: '编码是必需的，不能是空的'
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
			$.post("${ctx }/permission/save/${action}", $("#mainForm").serialize(), function(data) {
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
</script>