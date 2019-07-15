<%@page import="com.cbmie.genMac.stock.entity.PlanStock"%>
<%@page import="com.cbmie.genMac.stock.entity.PlanStockDetail"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<table id="planStockDetail" data-options="onClickRow: onClickRow"></table>
<form id="mainform" action="${ctx}/stock/planStock/${action}" method="post">
	<input type="hidden" name="id" value="${id }" />
	<input type="hidden" name="planStockDetailJson" id="planStockDetailJson"/>
</form>
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
			{field:'warehouseName',title:'仓库名称',width:180,
				editor:{
					type:'combobox',
					options:{
						valueField:'warehouseName',
						textField:'warehouseName',
						method:'GET',
						url:'${ctx}/baseinfo/warehouse/filter',
						required:true,
						onSelect:function(record){
							var obj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'goodsNameSpecification'});
							if(obj != null){
								$.ajax({
					 				url : '${ctx}/stock/planStock/findGoodsForWarehouse/' + record.warehouseName,
					 				type : 'get',
					 				async : false,
					 				cache : false,
					 				dataType : 'json',
					 				success : function(data) {
					 					var jsonObj = [];
					 					$.each(data, function(i, value){
					 						jsonObj.push({value:value});
					 					});
					 					obj.target.combobox({
			                            	width:200,
					 						valueField:'value',
					 						textField:'value',
					 						data:jsonObj
					 					});
					 				}
					 			});
							}
						}
					}
				}
			},
			{field:'goodsNameSpecification',title:'商品大类及规格型号',width:200,
				editor : {
					type : 'combobox',
					options : {
	                    required : true,
	                    prompt : '选择仓库加载',
						onSelect:function(record){
							var obj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'bookNum'});
							var idValue = $('#planStockDetail').datagrid('getSelected').id;
							var warehouseNameObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'warehouseName'});
							$.get('${ctx}/stock/planStock/findGoodsSumForWarehouse', {"warehouseName":warehouseNameObj.target.combobox('getValue'),"goodsNameSpecification":record.value,"id":idValue==null?0:idValue}, function(data){
								obj.target.numberbox('setValue', data);
							}, 'json')
						}
	                }
				}
			},
			{field:'bookNum',title:'账面数',width:150,editor:{type:'numberbox',options:{precision:2,prompt:'选择商品加载'}}}
		]],
	    columns:[
	        [
			{"title":"盈（+）亏（-）情况","colspan":2},
			{field:'inventoryNum',title:'盘点数',rowspan:2,width:5,editor:{type:'numberbox',options:{precision:2}}},
			{field:'diffInstruction',title:'盘盈盘亏差异说明',rowspan:2,width:10,editor:{type:'validatebox'}},
			{field:'remark',title:'备注',rowspan:2,width:10,editor:{type:'validatebox'}}
	        ],
	        [
	        {field:'profitNum',title:'盘盈',width:5,
	        	editor:{
		        	type:'numberbox',
		        	options:{
		        		precision:2,
	        			onChange:function(newValue, oldValue){
		        			var inventoryNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'inventoryNum'});
		        			if(inventoryNumObj == null){
		        				return;
		        			}
		        			var bookNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'bookNum'});
		        			bookNum = bookNumObj.target.numberbox('getValue');
		        			var lossNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'lossNum'});
		        			lossNum = lossNumObj.target.numberbox('getValue');
		        			inventoryNumObj.target.numberbox('setValue', Number(newValue) + Number(bookNum) - Number(lossNum));
	        			}
		        	}
	        	}
	        },
			{field:'lossNum',title:'盘亏',width:5,
	        	editor:{
		        	type:'numberbox',
		        	options:{
		        		precision:2,
	        			onChange:function(newValue, oldValue){
		        			var inventoryNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'inventoryNum'});
		        			if(inventoryNumObj == null){
		        				return;
		        			}
		        			var bookNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'bookNum'});
		        			bookNum = bookNumObj.target.numberbox('getValue');
		        			var profitNumObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'profitNum'});
		        			profitNum = profitNumObj.target.numberbox('getValue');
		        			inventoryNumObj.target.numberbox('setValue', Number(bookNum) + Number(profitNum) - Number(newValue));
	        			}
		        	}
	        	}
	        }
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	    /* onBeforeEdit : function(index, data){
	    	if (data.id != null) {
	    		$('#planStockDetail').datagrid('removeEditor', 'goodsNameSpecification'); //移除editor
	    	} else {
	    		$('#planStockDetail').datagrid('addEditor', [{
		    		field:'goodsNameSpecification',
		    		editor : {
						type : 'combobox',
						options : {
		                    required : true,
		                    prompt : '选择仓库加载',
							onSelect:function(record){
								var obj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'bookNum'});
								var idValue = $('#planStockDetail').datagrid('getSelected').id;
								var warehouseNameObj = $('#planStockDetail').datagrid('getEditor', {index:editIndex,field:'warehouseName'});
								$.get('${ctx}/stock/planStock/findGoodsSumForWarehouse', {"warehouseName":warehouseNameObj.target.combobox('getValue'),"goodsNameSpecification":record.value,"id":idValue}, function(data){
									obj.target.numberbox('setValue', data);
								}, 'json')
							}
		                }
					}
		    	}]); //增加editor
	    	}
	    }, */
	    onEndEdit : function(index, row, changes){
	    	$(this).datagrid('getRows')[index]['inventoryNum'] = Number(row.bookNum) + Number(row.profitNum) - Number(row.lossNum);
	    }
	});
	
	$('#mainform').form({
		onSubmit : function() {
			var isValid = $(this).form('validate');
			var rows = $('#planStockDetail').datagrid('getRows');
			var array = [];
			for (var i = 0; i < rows.length; i++) {
				var str = rows[i].warehouseName + rows[i].goodsNameSpecification;
				if (array.indexOf(str) > -1) {
					isValid = false;
					$.messager.alert('提示', '同一仓库下的商品重复！', 'info');
					$('#planStockDetail').datagrid('beginEdit', i);
					editIndex = i;
					break;
				} else {
					array.push(str);
				}
			}
			return isValid; // 返回false终止表单提交
		} ,
		success : function(data) {
			if("update"=="${action}"){
	    		successTip(data,dg);
	    	}else{
	    		successTip(data,dg,d);
	    	}
		}
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#planStockDetail').datagrid('validateRow', editIndex)){
		$('#planStockDetail').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#planStockDetail').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#planStockDetail').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#planStockDetail').datagrid('appendRow', {});
		editIndex = $('#planStockDetail').datagrid('getRows').length-1;
		$('#planStockDetail').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#planStockDetail').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#planStockDetail').datagrid('getRows');
		$('#planStockDetail').datagrid('acceptChanges');
		$('#planStockDetailJson').val(JSON.stringify(rows));
	}
}
function reject(){
	$('#planStockDetail').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#planStockDetail').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>