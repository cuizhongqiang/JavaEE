<%@page import="com.cbmie.genMac.stock.entity.PlanStock"%>
<%@page import="com.cbmie.genMac.stock.entity.PlanStockDetail"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="planStockDetail"></table>
<%
	PlanStock planStock = (PlanStock)request.getAttribute("planStock");
	List<PlanStockDetail> psdList = planStock.getPlanStockDetail();
	ObjectMapper objectMapper = new ObjectMapper();
	String psdJson = objectMapper.writeValueAsString(psdList);
	request.setAttribute("psdJson", psdJson);
%>
<script type="text/javascript">
var planStockDetail;
$(function(){
	planStockDetail=$('#planStockDetail').datagrid({
		data : JSON.parse('${psdJson}'),
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
			{field:'warehouseName',title:'仓库名称',width:180},
			{field:'goodsNameSpecification',title:'商品大类及规格型号',width:200},
			{field:'bookNum',title:'账面数',width:150}
		]],
	    columns:[
	        [
			{"title":"盈（+）亏（-）情况","colspan":2},
			{field:'inventoryNum',title:'盘点数',rowspan:2,width:5},
			{field:'diffInstruction',title:'盘盈盘亏差异说明',rowspan:2,width:10},
			{field:'remark',title:'备注',rowspan:2,width:10}
	        ],
	        [
	        {field:'profitNum',title:'盘盈',width:5},
			{field:'lossNum',title:'盘亏',width:5}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	});
});
</script>