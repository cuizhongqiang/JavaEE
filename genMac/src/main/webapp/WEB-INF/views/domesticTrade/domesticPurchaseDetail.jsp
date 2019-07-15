<%@page import="com.cbmie.genMac.domesticTrade.entity.DomesticPurchase" %>
<%@page import="com.cbmie.genMac.domesticTrade.entity.DomesticPurchaseGoods" %>
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
<form id="mainform" action="${ctx}/domesticTrade/domesticPurchase/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th>合同编号</th>
		<td>
			${domesticPurchase.contractNo }
		</td>
		<td>供应商名称</td>
		<td>
			${domesticPurchase.supplier }" 
		</td>
	</tr>
	<tr>
		<td colspan="4" style="padding: 0px;">
			<table id="domesticPurchaseGoodsTB" data-options="onClickRow: onClickRow"></table>
			<%
				DomesticPurchase domesticPurchase = (DomesticPurchase)request.getAttribute("domesticPurchase");
				List<DomesticPurchaseGoods> domesticPurchaseGoods = domesticPurchase.getDomesticPurchaseGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String domesticPurchaseGoodsJson = objectMapper.writeValueAsString(domesticPurchaseGoods);
				request.setAttribute("domesticPurchaseGoodsJson", domesticPurchaseGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			${domesticPurchase.paymentMethod }
			${domesticPurchase.paymentMethodRemark }
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			${domesticPurchase.remark }
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty domesticPurchase.createDate ? now : domesticPurchase.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty domesticPurchase.createrName ? sessionScope.user.name : domesticPurchase.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${domesticPurchase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="domesticPurchaseGoodsJson" id="domesticPurchaseGoodsJson"/>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<script type="text/javascript">
$(function(){
	
	
	//供应商
	$('#supplier').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/3,4',
		required : true,
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			//构造合同编号
			$.ajax({
				url : '${ctx}/domesticTrade/domesticPurchase/generateCode',
				data : {customer:record.customerCode,documentType:'合同'},
				type : 'get',
				async : false,
				cache : false,
				success : function(data) {
					$('#contractNo').val(data);
				}
			});
		}
	});
	
	$('#domesticPurchaseGoodsTB').datagrid({
		data : JSON.parse('${domesticPurchaseGoodsJson}'),
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
			{field:'goodsCode',title:'商品大类码',hidden:true},
			{field:'goodsCategory',title:'商品名称',width:200},
			{field:'specification',title:'规格型号',width:100},
			{field:'amount',title:'数量',width:100},
			{field:'unit',title:'单位',width:100},
			{field:'price',title:'单价',width:100}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#domesticPurchaseGoodsTBToolbar'
	});
});



</script>
</body>
</html>