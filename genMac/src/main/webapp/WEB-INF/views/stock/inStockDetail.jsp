<%@page import="com.cbmie.genMac.stock.entity.InStockGoods"%>
<%@page import="com.cbmie.genMac.stock.entity.InStock"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<table id="detailTab" width="98%" class="tableClass" >
	<tr>
		<td colspan="8" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">入库单</td>
	</tr>
	<tr>
		<td colspan="8" align="right" style="font-weight: bold;border: none;">
			编号:&nbsp;&nbsp;${inStock.inStockId }
		</td>
	</tr>
	<tr>
		<td width="8%" rowspan="4" style="font-weight: bold;">货物来源</td>
		<th width="10%">货权单位</th>
		<td>${inStock.goodsAffiliates }</td>
		<th width="10%">联系人</th>
		<td>${inStock.contacts }</td>
		<td width="8%" rowspan="4" style="font-weight: bold;">存储情况</td>
		<th width="10%">仓储单位</th>
		<td>${inStock.storageUnit }</td>
	</tr>
	<tr>
		<th>地址</th>
		<td>${inStock.address }</td>
		<th>电话</th>
		<td>${inStock.phoneNo }</td>
		<th>仓储地点</th>
		<td>${inStock.storageLocation }</td>
	</tr>
	<tr>
		<th>提单号</th>
		<td colspan="3">${inStock.invoiceNo }</td>
		<th>入库日期</th>
		<td><fmt:formatDate value="${inStock.inStockDate }"/></td>
	</tr>
	<tr>
		<th>合同号</th>
		<td colspan="3">${inStock.contractNo }</td>
		<th>联系方式</th>
		<td>${inStock.contactInformation }</td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<table id="inStockGoods"></table>
			<%
				InStock inStock = (InStock)request.getAttribute("inStock");
				List<InStockGoods> isList = inStock.getInStockGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String isJson = objectMapper.writeValueAsString(isList);
				request.setAttribute("isJson", isJson);
			%>
		</td>
	</tr>
	<tr>
		<td rowspan="4" style="font-weight: bold;">仓储约定</td>
		<th>存储库位</th>
		<td colspan="2">${inStock.storagePosition }</td>
		<th>损耗标准</th>
		<td colspan="3">${inStock.lossStandard }</td>
	</tr>
	<tr>
		<th>仓储期限</th>
		<td colspan="2">${inStock.termStorage }</td>
		<th>仓储费用</th>
		<td colspan="3">${inStock.storageFee }</td>
	</tr>
	<tr>
		<th>起算日期</th>
		<td colspan="2"><fmt:formatDate value="${inStock.startDate }"/></td>
		<th>保险约定</th>
		<td colspan="3">${inStock.insuranceAgreement }</td>
	</tr>
	<tr>
		<th>设备编号</th>
		<td colspan="2">${inStock.deviceID }</td>
		<th>备注</th>
		<td colspan="3">${inStock.remark }</td>
	</tr>
	<tr>
		<td style="font-weight: bold;" height="24px">验收记录</td>
		<td colspan="7">
			${inStock.record }
		</td>
	</tr>
	<tr>
		<td align="right" style="border: none;" height="48px;">仓库填发人:</td>
		<td align="left" style="border: none;">${inStock.warehousePrincipal }</td>
		<td align="right" style="border: none;">填发日期:</td>
		<td align="left" colspan="2" style="border: none;"><fmt:formatDate value="${inStock.signedDate }"/></td>
		<td align="right" style="border: none;">仓库单位盖章:</td>
		<td align="left" style="border: none;"></td>
	</tr>
	<tr>
		<td colspan="8" style="border: none;" height="24px;">
			兹确认上述货物均已收妥入库，签发人作为保管人自入库之日承担保管之责，非经贵方书面放货允许，以上货物均属贵司所有。
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${inStock.createDate }" pattern="yyyy-MM-dd"/>
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${inStock.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${inStock.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
<script type="text/javascript">
$(function() {
	$('#inStockGoods').datagrid({
		data : JSON.parse('${isJson}'),
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
			{field:'goodsCategory',title:'货物名称',width:400},
			{field:'specification',title:'规格型号',width:400},
			{field:'unit',title:'单位',width:200},
			{field:'amount',title:'数量',width:300}
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