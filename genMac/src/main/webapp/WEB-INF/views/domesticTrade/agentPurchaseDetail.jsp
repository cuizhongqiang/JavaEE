<%@page import="com.cbmie.genMac.domesticTrade.entity.AgentPurchase" %>
<%@page import="com.cbmie.genMac.domesticTrade.entity.AgentPurchaseGoods" %>
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
<form id="mainform" action="${ctx}/domesticTrade/agentPurchase/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th>合同编号</th>
		<td>
			${agentPurchase.contractNo }
		</td>
		<th>客户名称</th>
		<td>
			${agentPurchase.customerName }
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			${agentPurchase.paymentMethod }
			${agentPurchase.paymentMethodRemark }
		</td>
	</tr>
	<tr>
		<th>担保</th>
		<td>
			${agentPurchase.guartee }
		</td>
		<th>代理费</th>
		<td>
			${agentPurchase.agencyFee }
		</td>
	</tr>
	<tr>
		<th>合同管理地</th>
		<td>
			${agentPurchase.adress }
		</td>
		<th>备注</th>
		<td>
			${agentPurchase.remark }
		</td>
	</tr>
	<tr>
		<th>物流方案</th>
		<td colspan="3">
			${agentPurchase.logScheme }
		</td>
	</tr>
	<tr>
		<td colspan="4" style="padding: 0px;">
			<table id="agentPurchaseGoodsTB" data-options="onClickRow: onClickRow"></table>
			<%
				AgentPurchase agentPurchase = (AgentPurchase)request.getAttribute("agentPurchase");
				List<AgentPurchaseGoods> agentPurchaseGoods = agentPurchase.getAgentPurchaseGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String agentPurchaseGoodsJson = objectMapper.writeValueAsString(agentPurchaseGoods);
				request.setAttribute("agentPurchaseGoodsJson", agentPurchaseGoodsJson);
			%>
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty agentPurchase.createDate ? now : agentPurchase.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty agentPurchase.createrName ? sessionScope.user.name : agentPurchase.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${agentPurchase.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="agentPurchaseGoodsJson" id="agentPurchaseGoodsJson"/>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<script type="text/javascript">
$(function(){
	
	//客户名称
	$('#customerName').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/3,4',
		required : true,
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			//构造合同编号
			$.ajax({
				url : '${ctx}/domesticTrade/agentPurchase/generateCode',
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
	
	$('#agentPurchaseGoodsTB').datagrid({
		data : JSON.parse('${agentPurchaseGoodsJson}'),
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
	    toolbar:'#agentPurchaseGoodsTBToolbar',
	    onEndEdit : function(index, row, changes){
	    	$(this).datagrid('getData').rows;
	    }
	});
});

</script>
</body>
</html>