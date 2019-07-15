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
<form id="mainform"  action="${ctx}/stock/inStock/${action}" method="post">
<table width="98%" class="tableClass" >
	<tr>
		<td colspan="8" align="right" style="font-weight: bold;border: none;">
			编号:&nbsp;&nbsp;
			<input type="hidden" name="id" value="${inStock.id }"/>
			<input id="inStockId" name="inStockId" type="text" value="${inStock.inStockId }" class="easyui-validatebox" data-options="required:true,prompt:'选择提单号后加载'"/>
		</td>
	</tr>
	<tr>
		<td width="8%" rowspan="4" style="font-weight: bold;">货物来源</td>
		<th width="8%">货权单位</th>
		<td><input id="goodsAffiliates" name="goodsAffiliates" type="text" value="${inStock.goodsAffiliates }"/></td>
		<th width="8%">联系人</th>
		<td><input id="contacts" name="contacts" type="text" value="${inStock.contacts }" class="easyui-validatebox" data-options="prompt:'选择货权单位后加载'"/></td>
		<td width="8%" rowspan="4" style="font-weight: bold;">存储情况</td>
		<th width="8%">仓储单位</th>
		<td><input id="storageUnit" name="storageUnit" type="text" value="${inStock.storageUnit }"/></td>
	</tr>
	<tr>
		<th>地址</th>
		<td><input id="address" name="address" type="text" value="${inStock.address }" class="easyui-validatebox" data-options="prompt:'选择货权单位后加载'"/></td>
		<th>电话</th>
		<td><input id="phoneNo" name="phoneNo" type="text" value="${inStock.phoneNo }" class="easyui-validatebox" validtype="telOrMobile" data-options="prompt:'选择货权单位后加载'"/></td>
		<th>仓储地点</th>
		<td><input id="storageLocation" name="storageLocation" type="text" value="${inStock.storageLocation }" class="easyui-validatebox" data-options="required:true,prompt:'选择仓库后加载'"/></td>
	</tr>
	<tr>
		<th>提单号</th>
		<td colspan="3"><input id="invoiceNo" name="invoiceNo" type="text" value="${inStock.invoiceNo }"/></td>
		<th>入库日期</th>
		<td><input id="inStockDate" name="inStockDate" type="text" value="<fmt:formatDate value="${inStock.inStockDate }"/>" datefmt="yyyy-MM-dd" class="easyui-my97" data-options="required:true"/></td>
	</tr>
	<tr>
		<th>合同号</th>
		<td colspan="3"><input id="contractNo" name="contractNo" type="text" value="${inStock.contractNo }" class="easyui-validatebox" data-options="prompt:'选择提单号后加载'"/></td>
		<th>联系方式</th>
		<td><input id="contactInformation" name="contactInformation" type="text" value="${inStock.contactInformation }" class="easyui-validatebox" data-options="required:true,prompt:'选择仓库后加载'"/></td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<div id="childToolbar" style="padding:5px;height:auto">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">变化条数</a>
			</div>
			<table id="inStockGoods" data-options="onClickRow: onClickRow"></table>
			<input type="hidden" name="inStockGoodsJson" id="inStockGoodsJson"/>
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
		<td colspan="2"><input id="storagePosition" name="storagePosition" type="text" value="${inStock.storagePosition }" class="easyui-validatebox"/></td>
		<th>损耗标准</th>
		<td colspan="3"><input id="lossStandard" name="lossStandard" type="text" value="${inStock.lossStandard }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>仓储期限</th>
		<td colspan="2"><input id="termStorage" name="termStorage" type="text" value="${inStock.termStorage }" class="easyui-validatebox"/></td>
		<th>仓储费用</th>
		<td colspan="3"><input id="storageFee" name="storageFee" type="text" value="${inStock.storageFee }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>起算日期</th>
		<td colspan="2"><input id="startDate" name="startDate" type="text" value="<fmt:formatDate value="${inStock.startDate }"/>" datefmt="yyyy-MM-dd" class="easyui-my97" data-options="required:true"/></td>
		<th>保险约定</th>
		<td colspan="3"><input id="insuranceAgreement" name="insuranceAgreement" type="text" value="${inStock.insuranceAgreement }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>设备编号</th>
		<td colspan="2"><input id="deviceID" name="deviceID" type="text" value="${inStock.deviceID }" class="easyui-validatebox"/></td>
		<th>备注</th>
		<td colspan="3"><input id="remark" name="remark" type="text" value="${inStock.remark }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<td style="font-weight: bold;">验收记录</td>
		<td colspan="7">
			<textarea id="record" name="record" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${inStock.record }</textarea>
		</td>
	</tr>
	<tr>
		<th>仓库填发人</th>
		<td colspan="3"><input id="warehousePrincipal" name="warehousePrincipal" type="text" value="${inStock.warehousePrincipal }" class="easyui-validatebox" data-options="required:true"/></td>
		<th>填发日期</th>
		<td colspan="3"><input id="signedDate" name="signedDate" type="text" value="<fmt:formatDate value="${inStock.signedDate }"/>" datefmt="yyyy-MM-dd" class="easyui-my97" data-options="required:true"/></td>
	</tr>
</table>
</form>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty inStock.createDate ? now : inStock.createDate}" pattern="yyyy-MM-dd"/>
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty inStock.createrName ? sessionScope.user.name : inStock.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${inStock.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
</table>
<script type="text/javascript">
$(function() {
	$('#mainform').form({
		onSubmit : function() {
			var isValid = $(this).form('validate');
			if(isValid){
				$.ajax({
		    		url : '${ctx}/stock/inStock/uniqueNo/${empty inStock.id ? 0 : inStock.id}/' + $('#inStockId').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '入库编号重复！', 'info');
		    			}
		    		}
		    	});
			}
			return isValid; // 返回false终止表单提交
		} ,
		success : function(data) {
			if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
		} 
	});
	
	//货权单位
	$('#goodsAffiliates').combobox({
		required : true,
		url : '${ctx}/baseinfo/affiliates/getCompany/5',
		valueField : 'customerName',
		textField : 'customerName',
		onSelect:function (data) {
			$('#address').val(data.address);
			$('#contacts').val(data.contactPerson);
			$('#phoneNo').val(data.phoneContact);
		},
		onLoadSuccess:function(data){
			if($(this).combobox('getValue') == ""){
				$(this).combobox('select', "中建材通用机械有限公司");
			}
		}
	});
	
	//提单号
	$('#invoiceNo').combobox({
		method : 'get',
		required : true,
		url : '${ctx}/logistics/invoiceReg/findHaveFreight/库存业务',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		onSelect:function (data) {
			$('#contractNo').val(data.contractNo);
			$.ajax({
				url : '${ctx}/stock/inStock/generateCode',
				data : {contractNo:data.contractNo,documentType:'入库'},
				type : 'get',
				async : false,
				cache : false,
				success : function(data) {
					$('#inStockId').val(data);
				}
			});//构造编号
			var goodsNew = [];
			var amountTotal = 0;//总数量
			for(var i = 0; i < data.invoiceGoods.length; i++){
				goodsNew.push({'goodsCategory':data.invoiceGoods[i].goodsCategory,
					'specification':data.invoiceGoods[i].specification,
					'frameNo':data.invoiceGoods[i].frameNo,
					'unit':data.invoiceGoods[i].unit,
					'amount':data.invoiceGoods[i].amount,
					'price':data.invoiceGoods[i].price,
					'original':data.invoiceGoods[i].original});
				amountTotal += Number(data.invoiceGoods[i].amount);
			}
			var footer = [{'amount':amountTotal}]; //放入footer页脚
			var datax = { "rows": goodsNew, "footer": footer };
			initInStockGoods(datax); //初始化入库商品
			editIndex = undefined;
		}
	});
	
	//仓储单位
	$('#storageUnit').combobox({
		method : 'get',
		required : true,
		url : '${ctx}/baseinfo/warehouse/filter?filter_EQS_status=正常',
		valueField : 'warehouseName',
		textField : 'warehouseName',
		onSelect:function (data) {
			$('#storageLocation').val(data.address);
			$('#contactInformation').val(data.contactPerson + ":  " + data.phoneContact);
		}	
	});
	
	/* //仓库填发人
	$('#warehousePrincipal').combobox({
		required : true,
		url : '${ctx}/system/user/getRelativeUser/18',//业务员对应角色id
		valueField : 'name',
		textField : 'name'
	}); */
});

var inStockGoods;
initInStockGoods(); //初始化
function initInStockGoods(value){
	inStockGoods=$('#inStockGoods').datagrid({
		data : value != null ? value : JSON.parse('${isJson}'),
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
			{field:'goodsCategory',title:'货物名称',width:100,
				editor:{
					type:'combobox',
					options:{
						valueField : 'goodsName',
						textField : 'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json/isLeaf',
						required:true
					}
				}
			},
			{field:'specification',title:'规格型号',width:80,editor:{type:'validatebox'}},
			{field:'unit',title:'单位',width:40,
		    	editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'name',
						textField:'name',
						method:'get',
						url:'${ctx}/system/dict/getDictByCode/DW',
						required:true
					}
				}
		    },
			{field:'amount',title:'数量',width:80,
		    	editor:{
		    		type:'numberbox',
		    		options:{
		    			precision:2,
		    			required:true
		    		}
		    	}
		    }
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	    onEndEdit : function(index, row, changes){
	    	$(this).datagrid('getRows')[index]['original'] = (row.amount * row.price).toFixed(2);
	    	changeChildTB();
	    },
	    onLoadSuccess:function(data){
			var amountTotal = 0;//数量合计
			for(var i = 0; i < data.rows.length; i++){
				amountTotal += Number(data.rows[i].amount);
			}
	    	// 更新页脚行
	    	$(this).datagrid('reloadFooter', [{'amount':amountTotal}]);
	    }
	});
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#inStockGoods').datagrid('validateRow', editIndex)){
		$('#inStockGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#inStockGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#inStockGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#inStockGoods').datagrid('appendRow',{price:0});
		editIndex = $('#inStockGoods').datagrid('getRows').length-1;
		$('#inStockGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#inStockGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	changeChildTB();
}
function accept(){
	if (endEditing()){
		var rows = $('#inStockGoods').datagrid('getRows');
		$('#inStockGoods').datagrid('acceptChanges');
		$('#inStockGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#inStockGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
				editIndex = i;
				return false;
			}else{
				array.push(goods);
			}
		}
		return true;
	}
}
function reject(){
	$('#inStockGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#inStockGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(){
	var rows = $('#inStockGoods').datagrid('getData').rows;
	var amountTotal = 0;//数量合计
	for(var i = 0; i < rows.length; i++){
		amountTotal += Number(rows[i].amount);
	}
	// 更新页脚行
	$('#inStockGoods').datagrid('reloadFooter', [{'amount':amountTotal}]);
}
</script>
</body>
</html>