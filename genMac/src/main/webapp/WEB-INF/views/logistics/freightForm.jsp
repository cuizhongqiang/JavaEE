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
<form id="mainform" action="${ctx}/logistics/freight/${action}" method="post">
<input type="hidden" name="id" value="${freight.id }"/>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<td width="10%" style="font-weight: bold;">货权单位</td>
		<td colspan="6"><input type="text" id="goodsRightUnit" name="goodsRightUnit" value="${freight.goodsRightUnit }"/></td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">兹委托右表格中的单位</td>
		<th>单位名称</th>
		<td>
			<span id="logisticsUnitNameSpan"><input type="text" id="logisticsUnitName" name="logisticsUnitName" value="${freight.logisticsUnitName }" class="easyui-validatebox"/></span>
			<input type="checkbox" id="specified" style="margin-top:1px;margin-left:20px;" <c:if test="${freight.specified eq 1}">checked</c:if> onclick="specifiedClick(this)"/>指定货代
			<input type="hidden" name="specified" value="${freight.specified }"/>
		</td>
		<th>地址</th>
		<td><input type="text" id="logisticsUnitAddr" name="logisticsUnitAddr" value="${freight.logisticsUnitAddr }" class="easyui-validatebox"/></td>
		<th>邮编</th>
		<td><input type="text" id="logisticsUnitZipcode" name="logisticsUnitZipcode" value="${freight.logisticsUnitZipcode }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>联系人</th>
		<td><input type="text" id="logisticsUnitContacts" name="logisticsUnitContacts" value="${freight.logisticsUnitContacts }" class="easyui-validatebox"/></td>
		<th>电话</th>
		<td><input type="text" id="logisticsUnitContactsPhone" name="logisticsUnitContactsPhone" value="${freight.logisticsUnitContactsPhone }" class="easyui-validatebox"/></td>
		<th>传真</th>
		<td><input type="text" id="logisticsUnitContactsFax" name="logisticsUnitContactsFax" value="${freight.logisticsUnitContactsFax }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">将右表格中的货物</td>
		<th>提单号</th>
		<td><input type="text" id="invoiceNo"  name="invoiceNo" value="${freight.invoiceNo }"/></td>
		<th>合同号</th>
		<td colspan="3"><input type="text" id="contractNo" name="contractNo" value="${freight.contractNo }" class="easyui-validatebox" readonly="true" data-options="prompt:'选择提单号自动加载'"/></td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
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
			<table id="freightGoods" data-options="onClickRow: onClickRow"></table>
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
			<span id="goalUnitNameSpan"><input type="text" id="goalUnitName" name="goalUnitName" value="${freight.goalUnitName }" class="easyui-validatebox"/></span>
			<input type="checkbox" id="storage" style="margin-top:1px;margin-left:20px;" <c:if test="${freight.storage eq 1}">checked</c:if> onclick="storageClick(this)"/>仓储单位
			<input type="hidden" name="storage" value="${freight.storage }"/>
		</td>
		<th>地址</th>
		<td><input type="text" id="goalUnitAddr" name="goalUnitAddr" value="${freight.goalUnitAddr }" class="easyui-validatebox"/></td>
		<th>邮编</th>
		<td><input type="text" id="goalUnitZipcode" name="goalUnitZipcode" value="${freight.goalUnitZipcode }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>联系人</th>
		<td><input type="text" id="goalUnitContacts" name="goalUnitContacts" value="${freight.goalUnitContacts }" class="easyui-validatebox"/></td>
		<th>电话</th>
		<td><input type="text" id="goalUnitContactsPhone" name="goalUnitContactsPhone" value="${freight.goalUnitContactsPhone }" class="easyui-validatebox"/></td>
		<th>传真</th>
		<td><input type="text" id="goalUnitContactsFax" name="goalUnitContactsFax" value="${freight.goalUnitContactsFax }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<td style="font-weight: bold;">备注</td>
		<th>送库要求</th>
		<td colspan="2" id="requirement"></td>
		<th>堆存条件</th>
		<td colspan="2" id="conditions"></td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty freight.createDate ? now : freight.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty freight.createrName ? sessionScope.user.name : freight.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${freight.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="freightGoodsJson" id="freightGoodsJson"/>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
	    	var isValid = $(this).form('validate');
	    	if(!isValid){parent.$.messager.progress('close');};
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}"){
	    		successTip("success",dg);
	    		if($("#apply").val() == "true"){
	    			d.panel('close');
	    		}
	    	}else{
	    		successTip("success",dg,d);
	    	}
	    	parent.$.messager.progress('close');
	    	//提交流程
	    	if($("#apply").val() == "true"){
	    		apply(data);
	    	}
	    }
	});
	
	$('#goodsRightUnit').combobox({
		width : 410,
		required : true,
		url : '${ctx}/baseinfo/affiliates/getCompany/5',
		valueField : 'customerName',
		textField : 'customerName',
		onLoadSuccess:function(data){
			if($(this).combobox('getValue') == ""){
				$(this).combobox('setValue', "中建材通用机械有限公司");
			}
		}
	});
	
	$('#invoiceNo').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/logistics/invoiceReg/filter',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		onSelect:function(record){
			$('#contractNo').val(record.contractNo); //合同号
			var url;
			if(value == 0){
				url = '${ctx}/agent/agentImport/';
			}else if(value == 1){
				url = '${ctx}/selfRun/purchase/';
			}
			$.ajax({
				url : url + 'filter?filter_EQS_contractNo=' + record.contractNo,
				type : 'get',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(data) {
					var specified = data[0].freight;
					var specifiedFlag = $('#specified')[0].checked;
					if(specified == "指定货代"){
						if(!specifiedFlag){
							$('#specified').click();
						}
					}else if(specified == "我司货代"){
						if(specifiedFlag){
							$('#specified').click();
						}
					}
					var storageFlag = $('#storage')[0].checked;
					if(!storageFlag){
						$.ajax({
							url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + data[0].customerId,
							type : 'get',
							async : false,
							cache : false,
							dataType : 'json',
							success : function(data) {
								$('#goalUnitName').val(data[0].customerName);
								$('#goalUnitAddr').val(data[0].address); //地址
								$('#goalUnitContacts').val(data[0].contactPerson); //联系人
								$('#goalUnitContactsPhone').val(data[0].phoneContact); //电话
								$('#goalUnitContactsFax').val(data[0].fax); //传真
							}
						});
					}
				}
			});
			var goodsNew = [];
			var amountTotal = 0;//总数量
			for(var i = 0; i < record.invoiceGoods.length; i++){
				goodsNew.push({'goodsCategory':record.invoiceGoods[i].goodsCategory,
					'specification':record.invoiceGoods[i].specification,
					'amount':record.invoiceGoods[i].amount});
				amountTotal += Number(record.invoiceGoods[i].amount);
			}
			var footer = [{'amount':amountTotal}]; //放入footer页脚
			var datax = { "rows": goodsNew, "footer": footer };
			initFreightGoods(datax);
			editIndex = undefined;
		}
	}); //提单号
	
	if('${freight.specified}' == 0){
		logisticsCombobox();
	}
	if('${freight.storage}' == 1){
		storageCombobox();
	}
	
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/deliveryRequirement',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${freight.requirement }'.split(",");
			$.each(data, function(index, value){
				$("#requirement").append("<input type='checkbox' name='requirement'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	}); //送货要求
	
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/storageConditions',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${freight.conditions }'.split(",");
			$.each(data, function(index, value){
				$("#conditions").append("<input type='checkbox' name='conditions'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	}); //堆存条件
	
});

var freightGoods;
initFreightGoods(); //初始化
function initFreightGoods(value){
	freightGoods=$('#freightGoods').datagrid({
		data : value != null ? value : JSON.parse('${fgJson}'),
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
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'货物名称',width:50,
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
			{field:'specification',title:'规格型号',width:40,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:40,
		    	editor:{
		    		type:'numberbox',
		    		options:{
		    			precision:2,
		    			required:true
		    		}
		    	}
			},
			{field:'chest',title:'箱量',width:40,
		    	editor:{
		    		type:'numberbox',
		    		options:{
		    			precision:2
		    		}
		    	}
			},
			{field:'chestType',title:'箱型',width:40,editor:{type:'validatebox'}},
			{field:'stray',title:'散杂货',width:40,editor:{type:'validatebox'}},
			{field:'remark',title:'备注',width:80,editor:{type:'validatebox'}}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	    onEndEdit : function(index, row, changes){
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
	if ($('#freightGoods').datagrid('validateRow', editIndex)){
		$('#freightGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#freightGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#freightGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#freightGoods').datagrid('appendRow', {});
		editIndex = $('#freightGoods').datagrid('getRows').length-1;
		$('#freightGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#freightGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	changeChildTB();
}
function accept(){
	if (endEditing()){
		var rows = $('#freightGoods').datagrid('getRows');
		$('#freightGoods').datagrid('acceptChanges');
		$('#freightGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#freightGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#freightGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#freightGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(){
	var rows = $('#freightGoods').datagrid('getData').rows;
	var amountTotal = 0;//数量合计
	for(var i = 0; i < rows.length; i++){
		amountTotal += Number(rows[i].amount);
	}
	// 更新页脚行
	$('#freightGoods').datagrid('reloadFooter', [{'amount':amountTotal}]);
}

function specifiedClick(obj){
	if(obj.checked){
		$("input[name='specified']").val(1);
		$('#logisticsUnitNameSpan').html('<input type="text" id="logisticsUnitName" name="logisticsUnitName" value="${freight.logisticsUnitName }" class="easyui-validatebox"/>');
		$.parser.parse('#logisticsUnitNameSpan');
	}else{
		$("input[name='specified']").val(0);
		logisticsCombobox();
	}
}

function logisticsCombobox(){
	$('#logisticsUnitName').combobox({
		method:'GET',
		url:'${ctx}/logistics/logisticsCheck/filter?filter_EQS_state=生效',
		valueField : 'companyName',
		textField : 'companyName',
		onSelect:function(record){
			$('#logisticsUnitAddr').val(record.companyAddress); //地址
			$('#logisticsUnitContacts').val(record.contactPerson); //联系人
			$('#logisticsUnitContactsPhone').val(record.phoneContact); //电话
			$('#logisticsUnitContactsFax').val(record.fax); //传真
		}
	}); //物流公司
}

function storageClick(obj){
	if(obj.checked){
		$("input[name='storage']").val(1);
		storageCombobox();
	}else{
		$("input[name='storage']").val(0);
		$('#goalUnitNameSpan').html('<input type="text" id="goalUnitName" name="goalUnitName" value="${freight.goalUnitName }" class="easyui-validatebox"/>');
		$.parser.parse('#goalUnitNameSpan');
	}
}

function storageCombobox(){
	$('#goalUnitName').combobox({
		method : 'get',
		url : '${ctx}/baseinfo/warehouse/filter?filter_EQS_status=正常',
		valueField : 'warehouseName',
		textField : 'warehouseName',
		onSelect:function (data) {
			$('#goalUnitAddr').val(data.address); //地址
			$('#goalUnitContacts').val(data.contactPerson); //联系人
			$('#goalUnitContactsPhone').val(data.phoneContact); //电话
			$('#goalUnitContactsFax').val(data.fax); //传真
		}	
	}); //仓储单位
}
</script>
</body>
</html>