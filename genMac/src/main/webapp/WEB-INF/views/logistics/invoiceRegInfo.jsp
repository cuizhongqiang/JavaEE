<%@page import="com.cbmie.genMac.logistics.entity.InvoiceReg" %>
<%@page import="com.cbmie.genMac.logistics.entity.InvoiceGoods" %>
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
<center style="margin: 10px 0px 5px 0px;font-size: 16px;font-weight: bold;">
	<c:choose>
		<c:when test="${invoiceReg.contractType == 0 }">
			代理进口
		</c:when>
		<c:when test="${invoiceReg.contractType == 1 }">
			(自营)采购
		</c:when>
		<c:when test="${invoiceReg.contractType == 2 }">
			(内贸)采购
		</c:when>
		<c:when test="${invoiceReg.contractType == 3 }">
			(内贸)代理采购
		</c:when>
	</c:choose>
</center>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>提单号</th>
		<td>
			${invoiceReg.invoiceNo }
		</td>
		<th>合同号</th>
		<td>
			${invoiceReg.contractNo }
		</td>
		<th>业务员</th>
		<td>
			${invoiceReg.salesman }
		</td>
	</tr>
	<tr>
		<th width="10%">供应商</th>
		<td width="24%">
			${invoiceReg.supplier }
		</td>
		<th width="10%">贸易方式</th>
		<td width="23%">
			${invoiceReg.tradeWay }
		</td>
		<th width="10%">是否冲销预付款</th>
		<td width="23%">
			${invoiceReg.revAdvPayment eq 1 ? '是' : '否'}
		</td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<table id="invoiceGoods"></table>
			<%
				InvoiceReg invoiceReg = (InvoiceReg)request.getAttribute("invoiceReg");
				List<InvoiceGoods> igList = invoiceReg.getInvoiceGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String igJson = objectMapper.writeValueAsString(igList);
				request.setAttribute("igJson", igJson);
			%>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>金额信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">价格条款</th>
		<td width="20%">
			${invoiceReg.pricingTerms }
		</td>
		<th width="10%">币种</th>
		<td width="20%">
			${invoiceReg.currency }
		</td>
		<th width="15%">对人民币汇率</th>
		<td width="20%">
			${invoiceReg.rmbRate }
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<th>原币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${invoiceReg.originalCurrency }" />
		</td>
		<th>提单金额（人民币）</th>
		<td>
			<fmt:formatNumber type="number" value="${invoiceReg.invoiceMoney }" />
		</td>
	</tr>
	<tr>
		<th>装运港</th>
		<td colspan="2">
			${invoiceReg.transportPort }
		</td>
		<th>目的港</th>
		<td colspan="2">
			${invoiceReg.destinationPort }
		</td>
	</tr>
	<tr>
		<th>到单日期</th>
		<td>
			<fmt:formatDate value="${invoiceReg.arriveDate}" pattern="yyyy-MM-dd" />
		</td>
		<th>付汇/承兑日期</th>
		<td>
			<fmt:formatDate value="${invoiceReg.acceptDate}" pattern="yyyy-MM-dd" />
		</td>
		<th>是否押汇</th>
		<td>
			${invoiceReg.ifDocumentaryBills eq 1 ? '是' : '否'}
		</td>
	</tr>
	<tr>
		
		<th>押汇天数</th>
		<td>
			${invoiceReg.days }
		</td>
		<th>押汇到期日</th>
		<td>
			<fmt:formatDate value="${invoiceReg.documentaryBillsDate}" pattern="yyyy-MM-dd" />
			<c:if test="${invoiceReg.financialConfirm eq 1 }">
			<span style="margin-left:20px;">财务已确认</span>
			</c:if>
		</td>
		<th>押汇金额</th>
		<td>
			<fmt:formatNumber type="number" value="${invoiceReg.documentaryBillsMoney }" />
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${invoiceReg.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${invoiceReg.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${invoiceReg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<script type="text/javascript">
$(function(){
	$('#invoiceGoods').datagrid({
		data : JSON.parse('${igJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		frozenColumns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'商品名称',width:100},
			{field:'specification',title:'规格型号',width:80},
			{field:'frameNo',title:'车架号',width:80}
		]],
	    columns:[
	        [
			{"title":"合同","colspan":3},
			{field:'original',title:'原币金额',rowspan:2,align:'center',width:30},
			{"title":"关税","colspan":2},
			{"title":"消费税","colspan":2},
			{"title":"增值税","colspan":2}
	        ],
	        [
	        {field:'amount',title:'数量',width:20},
			{field:'unit',title:'单位',width:20},
			{field:'price',title:'单价',width:20},
			{field:'tax',title:'关税税额',width:20},
			{field:'taxRate',title:'关税税率%',width:20},
			{field:'saleTax',title:'消费税额',width:20},
			{field:'saleTaxRate',title:'消费税率%',width:20},
			{field:'vat',title:'增值税额',width:20},
			{field:'vatRate',title:'增值税率%',width:20}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>
</body>
</html>