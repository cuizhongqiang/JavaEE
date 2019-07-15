<%@page import="com.cbmie.genMac.domesticTrade.entity.DomesticPurchase" %>
<%@page import="com.cbmie.genMac.domesticTrade.entity.DomesticPurchaseGoods" %>
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
<form id="mainform" action="${ctx}/domesticTrade/domesticPurchase/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th>合同编号</th>
		<td>
			<input type="hidden" name="id" value="${domesticPurchase.id }"/>
			<input type="text" id="contractNo" name="contractNo" value="${domesticPurchase.contractNo }" class="easyui-validatebox" data-options="required:true,prompt:'选择供应商自动生成'"/>
		</td>
		<td>供应商名称</td>
		<td>
			<input type="text" id="supplier" name="supplier" value="${domesticPurchase.supplier }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<td colspan="4" style="padding: 0px;">
			<div id="domesticPurchaseGoodsToolbar" style="padding:5px;height:auto">
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
			<span id="paymentMethod"></span>
			<span style="padding-left: 20px;">备注：<input type="text" name="paymentMethodRemark" value="${domesticPurchase.paymentMethodRemark }" class="easyui-validatebox"/></span>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="3">
			<input type="text" id="remark" name="remark" value="${domesticPurchase.remark }" class="easyui-validatebox" />
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
	$('#mainform').form({
	    onSubmit: function(){  
	    	parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
	    	var isValid = $(this).form('validate');
	    	if(isValid){
		    	$.ajax({
		    		url : '${ctx}/domesticTrade/domesticPurchase/uniqueNo/${empty domesticPurchase.id ? 0 : domesticPurchase.id}',
		    		data : {contractNo:$('#contractNo').val()},
					type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '客户编码重复！', 'info');
		    			}
		    		}
		    	});
	    	}
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
	
	//支付方式
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/prompt',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${domesticPurchase.paymentMethod }'.split(",");
			$.each(data, function(index, value){
				$("#paymentMethod").append("<input type='checkbox' name='paymentMethod'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	});
	
	//供应商
	$('#supplier').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/3',
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
			{field:'goodsCode',title:'商品大类码',hidden:true,editor:{type:'validatebox'}},
			{field:'goodsCategory',title:'商品名称',width:200,
				editor:{
					type:'combobox',
					options:{
						valueField : 'goodsName',
						textField : 'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json/isLeaf',
						required:true,
						onSelect:function(record){
							var ed = $('#domesticPurchaseGoodsTB').datagrid('getEditor', {index:editIndex,field:'goodsCode'});
							$(ed.target).validatebox('setValue', record.goodsCode);
						}
					}
				}
			},
			{field:'specification',title:'规格型号',width:100,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:100,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}},
			{field:'unit',title:'单位',width:100,
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
			{field:'price',title:'单价',width:100,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#domesticPurchaseGoodsTBToolbar',
	    onEndEdit : function(index, row, changes){
	    	$(this).datagrid('getData').rows;
	    }
	});
});
var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#domesticPurchaseGoodsTB').datagrid('validateRow', editIndex)){
		$('#domesticPurchaseGoodsTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if(editIndex != index){
		if (endEditing()){
			$('#domesticPurchaseGoodsTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#domesticPurchaseGoodsTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if(endEditing()){
		$('#domesticPurchaseGoodsTB').datagrid('appendRow',{});
		editIndex = $('#domesticPurchaseGoodsTB').datagrid('getRows').length-1;
		$('#domesticPurchaseGoodsTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if(editIndex == undefined){return}
	$('#domesticPurchaseGoodsTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if(endEditing()){
		var rows = $('#domesticPurchaseGoodsTB').datagrid('getRows');
		if(rows.length == 0){
			parent.$.messager.confirm('提示', '空商品列表，请添加商品！', function(data) {
				if (data) {
					$('#domesticPurchaseGoodsTB').datagrid('appendRow',{});
					$('#domesticPurchaseGoodsTB').datagrid('selectRow', 0).datagrid('beginEdit',0);
					editIndex = 0;
				}
			});
			return false;
		}
		$('#domesticPurchaseGoodsTB').datagrid('acceptChanges');
		$('#domesticPurchaseGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#domesticPurchaseGoodsTB').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#domesticPurchaseGoodsTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#domesticPurchaseGoodsTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

</script>
</body>
</html>