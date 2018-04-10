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
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%"><font color="red">*</font>名称</th>
					<td><input name="name" id="name" class="easyui-validatebox" value="${modelBean.name}"
						data-options="required:true,missingMessage:'名称必填'" /></td>
				</tr>
				<tr>
					<th><font color="red">*</font>KEY</th>
					<td><input name="key" id="key" class="easyui-validatebox" value="${modelBean.key}"
						data-options="required:true,missingMessage:'KEY必填'" /></td>
				</tr>
				<tr>
					<th>描述</th>
					<td><textarea rows="3" cols="35" id="description" name="description" class="easyui-validatebox">${modelBean.description}</textarea></td>
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