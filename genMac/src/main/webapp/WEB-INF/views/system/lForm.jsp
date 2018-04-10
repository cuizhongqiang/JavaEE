<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
	<div>
		<form id="mainform" action="${ctx}/model/create" method="post">
			<table class="formTable">
				<tr>
					<td>名称</td>
					<td><input name="name" id="name" class="easyui-validatebox" value="${modelBean.name}"
						data-options="required:true,missingMessage:'名称必填'" /></td>
				</tr>
				<tr>
					<td>KEY</td>
					<td><input name="key" id="key" class="easyui-validatebox" value="${modelBean.key}"
						data-options="required:true,missingMessage:'KEY必填'" /></td>
				</tr>
				<tr>
					<td>描述</td>
					<td><textarea id="description" name="description" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${modelBean.description}</textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#mainform').form({
				onSubmit : function() {
					var isValid = $(this).form('validate');
					return isValid; // 返回false终止表单提交
				},
				success : function(data) {
					successTip(data, dg, d);
				}
			});
		});
	</script>
</body>
</html>