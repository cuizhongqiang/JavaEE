<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<fieldset class="fieldsetClass"   >
	<legend>仓库信息</legend>
		<table width="98%" class="tableClass">
			<tr>
				<th width="25%">仓库编码</th>
				<td>
				${warehouse.warehouseCode }
				</td>
				<th>仓库名称</th>
				<td>
				${warehouse.warehouseName }
				</td>
			</tr>
			<tr>
				<th>仓储企业</th>
				<td>
				${warehouse.enterpriseStock }
				</td>
				<th>地址</th>
				<td>
				${warehouse.address }
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td>
				${warehouse.contactPerson }
				</td>
				<th>联系电话</th>
				<td>
				${warehouse.phoneContact }
				</td>
			</tr>
			<tr>
				<th>传真</th>
				<td>
				${warehouse.fax }
				</td>
				<th>邮编</th>
				<td>
				${warehouse.zipCode }
				</td>
			</tr>
			<tr>
				<th>是否实库</th>
				<td>
				${warehouse.isRealWarehouse }
				</td>
				<th>状态</th>
				<td>
				${warehouse.status }
				</td>
			</tr>
			<tr>
				<th>登记日期</th>
				<td>
				<fmt:formatDate value="${warehouse.createDate }" pattern="yyyy-MM-dd" />
				</td>
				<th>登记人</th>
				<td>${warehouse.createrName }</td>
			</tr>
		</table>
	</fieldset>
	<fieldset class="fieldsetClass"   >
	<legend>仓库货物信息</legend>
		<form id="warehouseGoodsSearchFrom" action="${ctx}/baseinfo/warehouse/listDetail/${warehouse.id }">
			<input type="text" id="filters" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="width:150,prompt: '合同号'"/>
			<input type="text" id="filters" name="filter_LIKES_inStockId_OR_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '入库编号或提单号'"/>
			<span class="toolbar-item dialog-tool-separator"></span>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_warehouseGoods()">查询</a>
			<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_warehouseGoods()">重置</a>
		</form>
		<div id="warehouseGoods"></div>
	</fieldset>
</div>
<script type="text/javascript">
var warehouseGoods;
$(function () { 
	warehouseGoods = $("#warehouseGoods").datagrid({
		method:'get',
		url:'${ctx}/baseinfo/warehouse/warehouseGoodsDetail/${warehouse.warehouseName }',
		fit:false,
		rownumbers:true,
		fitColumns:true,
		pagination:true,
		pageNumber:1,
		pageSize : 5,
		pageList : [ 5, 10, 20 ],
		singleSelect:true,
		striped:true,
		remoteSort:true,
		columns:[[ 
			{field:'inStockId',title:'入库编号',sortable:true,width:20},
			{field:'goodsAffiliates',title:'货权单位',sortable:true,width:20},
			{field:'contacts',title:'联系人',sortable:true,width:20},
			{field:'invoiceNo',title:'提单号',sortable:true,width:25},
			{field:'contractNo',title:'合同号',sortable:true,width:25},
			{field:'note',title:'货物概要(品名/规格型号)',sortable:true,width:25},
			{field:'amount',title:'数量',sortable:true,width:15},
			{field:'unit',title:'单位',sortable:true,width:10},
			{field:'inStockDate',title:'入库日期',sortable:true,width:15}
		]],
		toolbar:'#warehouseGoodsSearchFrom'
	});
});
	
//查询对象
function cx_warehouseGoods(){
	var obj=$("#warehouseGoodsSearchFrom").serializeObject();
	warehouseGoods.datagrid('reload',obj);
}
//重置查询
function reset_warehouseGoods(){
	$("#warehouseGoodsSearchFrom")[0].reset();
	var obj=$("#warehouseGoodsSearchFrom").serializeObject();
	warehouseGoods.datagrid('reload',obj);
}
</script>
</body>
</html>