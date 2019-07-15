<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.AffiBaseInfo"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.AffiAssureInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<shiro:hasPermission name="sys:affiliates:review">
<div id="childToolbarAssure" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendAssure()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeitAssure()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptAssure()">保存</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectAssure()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChangesAssure()">变化条数</a>
</div>
</shiro:hasPermission>
<table id="childTBAssure" <shiro:hasPermission name="sys:affiliates:review">data-options="onClickRow: onClickRow"</shiro:hasPermission>></table>
<%
	AffiBaseInfo affiBaseInfoAssure = (AffiBaseInfo)request.getAttribute("affiBaseInfo");
	List<AffiAssureInfo> affiAssureList = affiBaseInfoAssure.getAffiAssureInfo();
	ObjectMapper objectMapperAssure = new ObjectMapper();
	String assureJson = objectMapperAssure.writeValueAsString(affiAssureList);
	request.setAttribute("assureJson", assureJson);
%>
<script type="text/javascript">
var childTBAssure;
$(function(){
	childTBAssure=$('#childTBAssure').datagrid({
	data : JSON.parse('${assureJson}'),
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
		{field:'guarantee',title:'个人保证',width:20,editor:{type:'validatebox',options:{required:true}}},
	    {field:'pledge',title:'质押',width:20, editor:{type:'validatebox',options:{required:true}}},
        {field:'chattel',title:'动产抵押',width:20, editor:{type:'validatebox',options:{required:true}}},
		{field:'realEstate',title:'不动产抵押',width:25,editor:{type:'validatebox',options:{required:true}}}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbarAssure'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTBAssure').datagrid('validateRow', editIndex)){
		$('#childTBAssure').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTBAssure').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTBAssure').datagrid('selectRow', editIndex);
		}
	}
}
function appendAssure(){
	if (endEditing()){
		$('#childTBAssure').datagrid('appendRow',{});
		editIndex = $('#childTBAssure').datagrid('getRows').length-1;
		$('#childTBAssure').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeitAssure(){
	if (editIndex == undefined){return}
	$('#childTBAssure').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function acceptAssure(){
	if (endEditing()){
		var rows = $('#childTBAssure').datagrid('getRows');
		$('#childTBAssure').datagrid('acceptChanges');
		$('#affiAssureJson').val(JSON.stringify(rows));
	}
}
function rejectAssure(){
	$('#childTBAssure').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChangesAssure(){
	var rows = $('#childTBAssure').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>