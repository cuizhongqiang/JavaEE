<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${dictionary.id }">
	<input type="hidden" name="pid" value="${dictionary.pid }">
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
						<input type="text" class="form-control" id="name" placeholder="名称" name="name" value="${dictionary.name }">
					</div>
				</div>
				<c:if test="${action ne 'detail' }">
				<div class="form-group">
					<label for="code" class="col-sm-2 control-label">编码</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="code" placeholder="编码" name="code" value="${dictionary.code }">
					</div>
				</div>
				<div class="form-group">
					<label for="value" class="col-sm-2 control-label">值</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="value" placeholder="值" name="value" value="${dictionary.value }">
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<label for="sort" class="col-sm-2 control-label">排序</label>
					<div class="col-sm-10">
						<input type="number" class="form-control" id="sort" placeholder="排序" name="sort" value="${dictionary.sort }">
					</div>
				</div>
				<div class="form-group">
					<label for="remark" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<textarea style="width: 100%;height: 90px;" id="remark" name="remark" maxlength="500">${dictionary.remark }</textarea>
					</div>
				</div>
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${dictionary.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${dictionary.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
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
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_\.]+$/,
	                        message: '编码只能由字母、数字、点和下划线组成'
	                    },
	                    remote: {
	                        type: 'POST',
	                        url: '${ctx}/dictionary/uniqueCode',
	                        message: '编码已存在',
	                        delay: 1000,
	                        data: function(validator) {
	                        	return {
	                        		code: $("#code").val(),
	                        		id: '${empty dictionary.id ? 0 : dictionary.id }'
	                        	};
	                        }
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
			$.post("${ctx }/dictionary/save/${action}", $("#mainForm").serialize(), function(data) {
				if (data == "success") {
					if (typeof cx == "function") {
						cx('${dictionary.pid }');
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