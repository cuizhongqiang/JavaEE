<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<table width="98%" class="tableClass" >
	<c:if test="${!empty serial.contractNo }">
	<tr>
		<th>合同号</th>
		<td>${serial.contractNo }</td>
		<th>水单类型</th>
		<td>${serial.serialCategory }</td>
	</tr>
	</c:if>
	<tr>
		<th>流水号</th>
		<td>${serial.serialNumber }</td>
		<th>水单抬头</th>
		<td id="customerName"></td>
	</tr>
	<tr>
		<th>银行</th>
		<td>${serial.bank }</td>
		<th>银行账号</th>
		<td>${serial.bankNO }</td>
	</tr> 
	<tr>
		<th>资金类别</th>
		<td>${serial.fundCategory }</td>
		<th>金额</th>
		<td><fmt:formatNumber type="number" value="${serial.money }"/></td>
	</tr>
	<tr>
		<th width="25%">日期</th>
		<td>
			<fmt:formatDate value="${serial.serialDate }" />
		</td>
		<th>银承到期日期</th>
		<td><fmt:formatDate value="${serial.bankDeadline }" /></td>
	</tr>
	<tr>
		<th>资金来源</th>
		<td colspan="3">${serial.fundSource }</td>
	</tr> 
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${serial.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${serial.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${serial.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=${serial.customerId }',
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