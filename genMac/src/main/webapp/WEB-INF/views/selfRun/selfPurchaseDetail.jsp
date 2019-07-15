<%@page import="com.cbmie.genMac.selfRun.entity.SelfPurchase" %>
<%@page import="com.cbmie.genMac.selfRun.entity.SelfPurchaseGoods" %>
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
			${selfPurchase.contractNo }
		</td>
		<th>供应商名称</th>
		<td>
			${my:getAffiliatesById(selfPurchase.customerId).customerName }
		</td>
		<th>业务员</th>
		<td>
			${my:getUserByLoginName(selfPurchase.salesman).name }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			${selfPurchase.currency }
		</td>
		<th>对人民币汇率</th>
		<td>
			<fmt:formatNumber type="number" value="${selfPurchase.exchangeRateSelf }" />
		</td>
		<th>对美元汇率</th>
		<td>
			<fmt:formatNumber type="number" value="${selfPurchase.exchangeRateUS }" />
		</td>
	</tr>
	<tr>
		<th>原币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfPurchase.originalCurrency }" />
		</td>
		<th>人民币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfPurchase.rmb }" />
		</td>
		<th>美元金额</th>
		<td>
			<fmt:formatNumber type="number" value="${selfPurchase.dollar }" />
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="selfPurchaseGoodsTB"></table>
			<%
				SelfPurchase selfPurchase = (SelfPurchase)request.getAttribute("selfPurchase");
				List<SelfPurchaseGoods> selfPurchaseGoods = selfPurchase.getSelfPurchaseGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String selfPurchaseGoodsJson = objectMapper.writeValueAsString(selfPurchaseGoods);
				request.setAttribute("selfPurchaseGoodsJson", selfPurchaseGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			${selfPurchase.paymentMethod }
			<span style="padding-left: 20px;">备注：${selfPurchase.paymentMethodRemark }</span>
		</td>
		<th>合同管辖地</th>
		<td>
			${selfPurchase.government }
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="5">
			${selfPurchase.remark }
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${selfPurchase.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${selfPurchase.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${selfPurchase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#selfPurchaseGoodsTB').datagrid({
		data : JSON.parse('${selfPurchaseGoodsJson}'),
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