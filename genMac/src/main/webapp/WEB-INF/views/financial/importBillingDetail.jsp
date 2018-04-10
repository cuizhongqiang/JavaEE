<%@page import="com.cbmie.genMac.financial.entity.ImportBilling" %>
<%@page import="com.cbmie.genMac.financial.entity.ImportBillingGoods" %>
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
<table id="detailTab">
<tr>
	<td align="center" style="font-size: 16px;font-weight: bold;letter-spacing: 4px;">自营(代理)进口结算联系单</td>
</tr>
<tr>
<td>
<table width="100%" class="tableClass">
	<tr>
		<td width="5%" rowspan="3" style="font-weight: bold;">自营</td>
		<th>购物单位名称</th>
		<td>${importBilling.unitName }</td>
		<th>地址</th>
		<td>${importBilling.address }</td>
		<th>电话</th>
		<td>${importBilling.phone }</td>
	</tr>
	<tr>
		<th>税务登记号</th>
		<td>${importBilling.taxNo }</td>
		<th>开户行</th>
		<td>${importBilling.bankName }</td>
		<th>账号</th>
		<td>${importBilling.bankNo }</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="importBillingGoods"></table>
			<%
				ImportBilling importBilling = (ImportBilling)request.getAttribute("importBilling");
				List<ImportBillingGoods> importBillingGoods = importBilling.getImportBillingGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String importBillingGoodsJson = objectMapper.writeValueAsString(importBillingGoods);
				request.setAttribute("importBillingGoodsJson", importBillingGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<td rowspan="11" style="font-weight: bold;">结算</td>
		<th colspan="6" align="center" style="font-weight: bold;font-size: 14px;background: none;">
			进口合同号:&nbsp;&nbsp;<span>${importBilling.contractNo }</span>&nbsp;&nbsp;&nbsp;&nbsp;
			现有库存:&nbsp;&nbsp;<span><fmt:formatNumber type="number" value="${importBilling.nowInventory }" /></span>&nbsp;&nbsp;&nbsp;&nbsp;
			剩余库存:&nbsp;&nbsp;<span><fmt:formatNumber type="number" value="${importBilling.remainInventory }" /></span>&nbsp;&nbsp;&nbsp;&nbsp;
			业务员分成比例：&nbsp;&nbsp;<span>${importBilling.proportion }</span>
		</th>
	</tr>
	<tr>
		<th>支付货款</th>
		<td colspan="3">
			<fmt:formatNumber type="number" value="${importBilling.payMoney }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.payMoneySys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.payMoney - importBilling.payMoneySys }" />
		</td>
		<th>到账货款</th>
		<td><fmt:formatNumber type="number" value="${importBilling.accountMoney }" /></td>
	</tr>
	<tr>
		<th>银行手续费</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.bankPoundage }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.bankPoundageSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.bankPoundage - importBilling.bankPoundageSys }" />
		</td>
	</tr>
	<tr>
		<th>关税(监管费)</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.custom }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.customSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.custom - importBilling.customSys }" />
		</td>
	</tr>
	<tr>
		<th>增值税</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.vat }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.vatSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.vat - importBilling.vatSys }" />
		</td>
	</tr>
	<tr>
		<th>消费税</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.saleTax }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.saleTaxSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.saleTax - importBilling.saleTaxSys }" />
		</td>
	</tr>
	<tr>
		<th>报关</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.customsDeclaration }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.customsDeclarationSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.customsDeclaration - importBilling.customsDeclarationSys }" />
		</td>
	</tr>
	<tr>
		<th>运杂</th>
		<td colspan="5">
			<fmt:formatNumber type="number" value="${importBilling.shipMix }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.shipMixSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.shipMix - importBilling.shipMixSys }" />
		</td>
	</tr>
	<tr>
		<th>保险</th>
		<td colspan="3">
			<fmt:formatNumber type="number" value="${importBilling.insurance }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.insuranceSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.insurance - importBilling.insuranceSys }" />
		</td>
		<th>代理费(利润)</th>
		<td><fmt:formatNumber type="number" value="${importBilling.agencyFee }" /></td>
	</tr>
	<tr>
		<th>其他(免表.录入等)</th>
		<td colspan="3">
			<fmt:formatNumber type="number" value="${importBilling.others }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.othersSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.others - importBilling.othersSys }" />
		</td>
		<th>利息</th>
		<td><fmt:formatNumber type="number" value="${importBilling.interest }" /></td>
	</tr>
	<tr>
		<th>支付合计</th>
		<td colspan="3">
			<fmt:formatNumber type="number" value="${importBilling.payTotal }" />
			-
			<fmt:formatNumber type="number" value="${importBilling.payTotalSys }" />
			=
			<fmt:formatNumber type="number" value="${importBilling.payTotal - importBilling.payTotalSys }" />
		</td>
		<th>本合同余额</th>
		<td><fmt:formatNumber type="number" value="${importBilling.balance }" /></td>
	</tr>
	<tr>
		<td style="font-weight: bold;">代理</td>
		<th>客户名称</th>
		<td colspan="5">${importBilling.agencyName }</td>
	</tr>
	<tr>
		<td width="5%" height="48px" style="font-weight: bold;">备注</td>
		<td colspan="6">
			${importBilling.remark }
		</td>
	</tr>
	<tr>
		<th colspan="2" style="font-weight: bold;">经办人</th>
		<td colspan="5">${importBilling.createrName }</td>
	</tr>
	<tr>
		<th colspan="2" style="font-weight: bold;">部门经理意见</th>
		<td colspan="5" id="mgmdeptuser"></td>
	</tr>
	<tr>
		<th colspan="2" style="font-weight: bold;">财务部门意见</th>
		<td colspan="5" id="financialuser"></td>
	</tr>
	<tr>
		<th colspan="2" style="font-weight: bold;">公司领导意见</th>
		<td colspan="5" id="manageruser"></td>
	</tr>
</table>
</td>
</tr>
</table>
<table width="99%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${importBilling.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${importBilling.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${importBilling.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$("#detailTab").attr("width", $(window).width() * 0.98);
$(function(){
	$('#importBillingGoods').datagrid({
		data : JSON.parse('${importBillingGoodsJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		showFooter:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'货物(应税劳务)名称',width:200},
			{field:'specification',title:'规格型号',width:200},
			{field:'unit',title:'单位',width:50},
			{field:'amount',title:'数量',width:100},
			{field:'price',title:'单价',width:100},
			{field:'salesAmount',title:'销售金额',width:200},
			{field:'rateMain',title:'税率',width:100},
			{field:'taxMain',title:'税额',width:200}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onLoadSuccess:function(data){
	    	var salesAmountTotal = 0;//销售金额合计
			var taxMainTotal = 0;//税额合计
			for(var i = 0; i < data.rows.length; i++){
				salesAmountTotal += Number(data.rows[i].salesAmount);
				taxMainTotal += Number(data.rows[i].taxMain);
			}
	    	// 更新页脚行并载入新数据
	    	$(this).datagrid('reloadFooter', [{'salesAmount':salesAmountTotal, 'taxMain':taxMainTotal}]);
	    }
	});
	
	//部门经理
	$.ajax({
		url : '${ctx}/workflow/getNewComments',
		data : {processInstanceId:'${importBilling.processInstanceId}', taskAssignee:'mgmdeptuser'},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#mgmdeptuser').text(data);
		}
	});

	//财务
	$.ajax({
		url : '${ctx}/workflow/getNewComments',
		data : {processInstanceId:'${importBilling.processInstanceId}', taskAssignee:'financialuser'},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#financialuser').text(data);
		}
	});

	//公司领导
	$.ajax({
		url : '${ctx}/workflow/getNewComments',
		data : {processInstanceId:'${importBilling.processInstanceId}', taskAssignee:'manageruser'},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#manageruser').text(data);
		}
	});

});
</script>
</body>
</html>