<%@page import="com.cbmie.genMac.selfRun.entity.SelfSales" %>
<%@page import="com.cbmie.genMac.selfRun.entity.SelfSalesGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<table width="98%" class="tableClass">
	<tr>
		<th>合同编号</th>
		<td>
			${selfSales.contractNo }
		</td>
		<th>客户名称</th>
		<td>
			${my:getAffiliatesById(selfSales.customer).customerName }
		</td>
		<th>业务员</th>
		<td>
			${my:getUserByLoginName(selfSales.salesman).name }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			${selfSales.currency }
		</td>
		<th>对人民币汇率</th>
		<td>
			<fmt:formatNumber type="number" value="${selfSales.exchangeRateSelf }" />
		</td>
		<th>对美元汇率</th>
		<td>
			<fmt:formatNumber type="number" value="${selfSales.exchangeRateUS }" />
		</td>
	</tr>
	<tr>
		<th>原币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfSales.originalCurrency }" />
		</td>
		<th>人民币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfSales.rmb }" />
		</td>
		<th>美元金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfSales.dollar }" />
		</td>
	</tr>
	<tr>
		<th>保证金</th>
		<td>
			${selfSales.margin }
		</td>
		<th>担保措施</th>
		<td colspan="3">
			${selfSales.assurance eq 0 ? '有' : '无'}
			<span style="padding-left: 20px;">备注：${selfSales.assuranceRemark }</span>
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="selfSalesGoodsTB"></table>
			<%
				SelfSales selfSales = (SelfSales)request.getAttribute("selfSales");
				List<SelfSalesGoods> selfSalesGoods = selfSales.getSelfSalesGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String selfSalesGoodsJson = objectMapper.writeValueAsString(selfSalesGoods);
				request.setAttribute("selfSalesGoodsJson", selfSalesGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			${selfSales.paymentMethod }
			<span style="padding-left: 20px;">备注：${selfSales.paymentMethodRemark }</span>
		</td>
		<th>合同管辖地</th>
		<td>
			${selfSales.government }
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="5">
			${selfSales.remark }
		</td>
	</tr>
	<tr>
		<th>物流方案</th>
		<td colspan="5">
			${selfSales.logistics }
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${selfSales.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${selfSales.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${selfSales.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#selfSalesGoodsTB').datagrid({
		data : JSON.parse('${selfSalesGoodsJson}'),
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[
			{field:'goodsCategory',title:'商品名称',width:200},
			{field:'specification',title:'规格型号',width:100},
			{field:'amount',title:'数量',width:100},
			{field:'unit',title:'单位',width:100},
			{field:'price',title:'单价',width:100}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>
</body>
</html>