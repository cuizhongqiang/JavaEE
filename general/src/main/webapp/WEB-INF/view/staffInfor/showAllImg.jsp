<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${staffInfor.id }">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
				名片信息
				</h4>
			</div>
			<div class="modal-body">
					<div class="form-group">
						<label  class="col-sm-3 control-label">电子名片二维码</label>
					</div>
					<p style="text-align: center;"><img src="${ctx }/vistor/toUserSummer/${staffInfor.id }.png"/></p>
<!-- 					<div class="form-group"> -->
<!-- 						<label  class="col-sm-2 control-label">名片二维码</label> -->
<!-- 					</div> -->
<%-- 					<p style="text-align: center;"><img src="${ctx }/vistor/toUserAdress/${staffInfor.id }.png"/></p> --%>
					<div class="form-group">
						<label  class="col-sm-2 control-label">个人名片</label>
					</div>
					<p style="text-align: center;"><img src="${ctx }/vistor/toUserCard/${staffInfor.id }.png" style="width: 300px"/></p>
					<p style="text-align: center;"><img src="${ctx }/static/others/ecard/images/ecard/backCard.jpg" style="width: 300px"/></p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	</form>
<script type="text/javascript">
$(function(){
	
	
});

</script>