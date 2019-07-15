<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<form id="cliam_form"  action="${ctx}/financial/serial/claim" method="post">
<table width="98%" class="tableClass" >
	<tr>
		<th>合同号</th>
		<td>
			<input type="hidden" name="id" value="${serial.id }" />
			<input  id="contractNo" name="contractNo" type="text" value="${serial.contractNo }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>水单类型</th>
		<td><input id="serialCategory" name="serialCategory" type="text" value="${serial.serialCategory }" class="easyui-validatebox" /></td>
	</tr>
	<tr>
		<th width="25%">认领人备注</th>
		<td>
			<textarea name="comments" class="easyui-validatebox">${serial.comments }</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
	$(function() {
		$('#cliam_form').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
		    	successTip(data,dg,d_claim);
			} 
		});
		
		$('#contractNo').combobox({
			method : 'get',
			required : true,
			url : '${ctx}/agent/agentImport/filter?filter_EQS_state=生效',
			valueField : 'contractNo',
			textField : 'contractNo',
			loadFilter : function(data){//去重
				var array = [];
				for(var i = 0; i < data.length; i++){
					if(array.indexOf(data[i].contractNo) > -1){
						data.splice(i, 1);
					}else{
						array.push(data[i].contractNo);
					}
				}
				return data;
			}
		});
		
		
		//资金类别
		$('#serialCategory').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/serialCategory',
			valueField : 'name',
			textField : 'name'
		});
	});
</script>
</body>
</html>