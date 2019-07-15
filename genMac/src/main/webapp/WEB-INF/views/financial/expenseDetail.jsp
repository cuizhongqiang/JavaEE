<%@page import="com.cbmie.genMac.financial.entity.ExpenseDetail"%>
<%@page import="com.cbmie.genMac.financial.entity.Expense"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<fieldset class="fieldsetClass" >
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="10%">合同号</th>
		<td width="23%">
			${expense.contractNo }
		</td>
		<th width="10%">客户名称</th>
		<td width="24%" id="customerName">
		</td>
		<th width="10%">业务员</th>
		<td width="23%">
			${expense.salesman }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			RMB
		</td>
		<th>申请日期</th>
		<td>
			<fmt:formatDate value='${expense.applyDate }' />
		</td>
		<th>财务实付日期</th>
		<td>
			<fmt:formatDate value="${expense.payDate }" />
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass" >
<legend>金额信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="10%">应付总金额</th>
		<td width="23%">
			<fmt:formatNumber type="number" value="${expense.sumMoney }"/>
		</td>
		<th width="10%">实付金额</th>
		<td width="24%">
			<fmt:formatNumber type="number" value="${expense.payCurrency }"/>
		</td>
		<th width="10%">未付金额</th>
		<td width="23%">
			<fmt:formatNumber type="number" value="${expense.oweCurrency }"/>
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="childTBInfo"></table>
			<%
				Expense expense = (Expense)request.getAttribute("expense");
				List<ExpenseDetail> gcList = expense.getExpenseDetail();
				ObjectMapper objectMapper = new ObjectMapper();
				String gcJson = objectMapper.writeValueAsString(gcList);
				request.setAttribute("gcJson", gcJson);
			%>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass" >
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${expense.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${expense.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${expense.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<script type="text/javascript">
$(function() {
	$('#childTBInfo').datagrid({
		data : JSON.parse('${gcJson}'),
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'documentNo',title:'单据号',width:15},
			{field:'paymentChildType',title:'付款子类型',width:15},
			{field:'settlement',title:'结算客户',width:20},
			{field:'receiptStatus',title:'收款情况',width:15},
			{field:'payModel',title:'支付方式',width:10},
			{field:'bankName',title:'银行名称',width:15},
			{field:'bankNo',title:'银行账号',width:15},
			{field:'money',title:'金额',width:15},
			{field:'remark',title:'备注',width:20}
		]],
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=${expense.customerId }',
		type : 'get',
		async : false,
		cache : false,
		dataType : 'json',
		success : function(data) {
			$('#customerName').text(data[0].customerName);
		}
	});
});
</script>
</body>
</html>