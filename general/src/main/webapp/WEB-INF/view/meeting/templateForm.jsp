<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/mytag.tld" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${template.id }">
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
				<c:if test="${action eq 'detail' }">
				<p style="text-align: center;">
					<img src="${ctx }/template/qrCode/${template.id }/${template.channel }.png"/>
				</p>
				</c:if>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">标题</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" placeholder="标题" id="title" name="title" value="${template.title }">
					</div>
				</div>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">划分渠道</label>
					<div class="col-sm-10">
						<input type="radio" name="channel" value="1" <c:if test="${template.channel eq 1 }">checked</c:if>>是
						<input type="radio" name="channel" value="0" <c:if test="${template.channel eq 0 }">checked</c:if>>否
					</div>
				</div>
				<div class="form-group">
					<label for="interest" class="col-sm-2 control-label">收集信息</label>
					<div class="col-sm-10">
					
						<c:forEach items="${my:findMeetingCollect() }" var="collect">
							<c:set var="iscontain" value="false"/>
							<c:forEach items="${collectIds }" var="cid">
								<c:if test="${cid eq collect.id }">
									<c:set var="iscontain" value="true"/>
								</c:if>
							</c:forEach>
							<input type="checkbox" value="${collect.id }" name="collectIds" <c:if test="${iscontain }">checked</c:if>> ${collect.name }
							<br>
						</c:forEach>
						
					</div>
				</div>
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${template.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${template.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
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
				title: {
					validators: {
						notEmpty: {
							message: '标题不能为空'
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
			$.post("${ctx }/template/save/${action}", $("#mainForm").serialize(), function(data) {
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