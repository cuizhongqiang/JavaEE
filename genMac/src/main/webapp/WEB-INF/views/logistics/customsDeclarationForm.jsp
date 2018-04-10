<%@page import="com.cbmie.genMac.logistics.entity.Freight" %>
<%@page import="com.cbmie.genMac.logistics.entity.FreightGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/logistics/invoiceReg/${action}" method="post">
<input type="hidden" name="id" value="${invoiceReg.id }"/>
<table width="98%" class="tableClass">
	<tr>
		<th>报关单号</th>
		<td>
			<input type="text" id="customsDeclarationNo" name="customsDeclarationNo" value="${invoiceReg.customsDeclarationNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>报关公司</th>
		<td>
			<input type="text" id="customsDeclarationUnit" name="customsDeclarationUnit" value="${invoiceReg.customsDeclarationUnit }" class="easyui-validatebox" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>到港日期</th>
		<td>
			<input type="text" name="arrivalPortDate" value="<fmt:formatDate value="${invoiceReg.arrivalPortDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>放行日期</th>
		<td>
			<input type="text" name="letDate" value="<fmt:formatDate value="${invoiceReg.letDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>库存方式</th>
		<td id="inventoryWay">
		</td>
	</tr>
	<tr>
		<th>清关状态</th>
		<td>
			<input type="text" id="customsDeclarationState" name="customsDeclarationState" value="${invoiceReg.customsDeclarationState }"/>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}"){
	    		successTip(data,dg);
	    	}else{
	    		successTip(data,dg,d);
	    	}
	    }
	});
	
	$('#customsDeclarationState').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dict/getDictByCode/customsDeclarationState',
		valueField : 'name',
		textField : 'name'
	});
	
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/inventoryWay',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			$.each(data, function(index, value){
				$("#inventoryWay").append("<input type='radio' name='inventoryWay'" + ((value.name == '${invoiceReg.inventoryWay }' || index == 0) ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:-2px'/>" + value.name);
			});
		}
	});
});	
</script>
</body>
</html>