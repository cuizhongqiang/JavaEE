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
<table id="detailTab" width="98%" class="tableClass">
	<tr>
		<td colspan="7" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">送库委托通知书</td>
	</tr>
	<tr>
		<td width="10%" style="font-weight: bold;" height="24">货权单位</td>
		<td colspan="6">${freight.goodsRightUnit }</td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">兹委托右表格中的单位</td>
		<th width="10%">单位名称</th>
		<td width="20%">
			${freight.logisticsUnitName }
			<c:if test="${freight.specified eq 1 }">
			<span style="margin-left:20px;">指定货代</span>
			</c:if>
		</td>
		<th width="10%">地址</th>
		<td width="20%">${freight.logisticsUnitAddr }</td>
		<th width="10%">邮编</th>
		<td width="20%">${freight.logisticsUnitZipcode }</td>
	</tr>
	<tr>
		<th>联系人</th>
		<td>${freight.logisticsUnitContacts }</td>
		<th>电话</th>
		<td>${freight.logisticsUnitContactsPhone }</td>
		<th>传真</th>
		<td>${freight.logisticsUnitContactsFax }</td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">将右表格中的货物</td>
		<th>提单号</th>
		<td>${freight.invoiceNo }</td>
		<th>合同号</th>
		<td colspan="3">${freight.contractNo }</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="freightGoodsInfo"></table>
			<%
				Freight freight = (Freight)request.getAttribute("freight");
				List<FreightGoods> fgList = freight.getFreightGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String fgJson = objectMapper.writeValueAsString(fgList);
				request.setAttribute("fgJson", fgJson);
			%>
		</td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">送入右表格中的单位</td>
		<th>单位名称</th>
		<td>
			${freight.goalUnitName }
			<c:if test="${freight.storage eq 1 }">
			<span style="margin-left:20px;">仓储单位</span>
			</c:if>
		</td>
		<th>地址</th>
		<td>${freight.goalUnitAddr }</td>
		<th>邮编</th>
		<td>${freight.goalUnitZipcode }</td>
	</tr>
	<tr>
		<th>联系人</th>
		<td>${freight.goalUnitContacts }</td>
		<th>电话</th>
		<td>${freight.goalUnitContactsPhone }</td>
		<th>传真</th>
		<td>${freight.goalUnitContactsFax }</td>
	</tr>
	<tr>
		<td style="font-weight: bold;">备注</td>
		<th>送库要求</th>
		<td colspan="2">${freight.requirement }</td>
		<th>堆存条件</th>
		<td colspan="2">${freight.conditions }</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${freight.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${freight.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${freight.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#freightGoodsInfo').datagrid({
		data : JSON.parse('${fgJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		showFooter:true,
		extEditing:false,
		columns:[[
			{field:'goodsCategory',title:'货物名称',width:140},
			{field:'specification',title:'规格型号',width:100},
			{field:'amount',title:'数量',width:100},
			{field:'chest',title:'箱量',width:100},
			{field:'chestType',title:'箱型',width:100},
			{field:'stray',title:'散杂货',width:100},
			{field:'remark',title:'备注',width:150}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onLoadSuccess:function(data){
			var amountTotal = 0;//数量合计
			for(var i = 0; i < data.rows.length; i++){
				amountTotal += Number(data.rows[i].amount);
			}
	    	// 更新页脚行
	    	$(this).datagrid('reloadFooter', [{'amount':amountTotal}]);
	    }
	});
});
</script>
</body>
</html>