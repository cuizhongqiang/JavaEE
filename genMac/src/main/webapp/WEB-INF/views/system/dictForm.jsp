<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.system.entity.DictChild"%>
<%@page import="java.util.List"%>
<%@page import="com.cbmie.system.entity.DictMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<form id="mainform" action="${ctx}/system/dict/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th><font color="red">*</font>名称</th>
		<td>
		<input type="hidden" name="id" value="${id }" />
		<input name="name" type="text" value="${dictMain.name }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th><font color="red">*</font>编码</th>
		<td>
		<input name="code" type="text" value="${dictMain.code }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>登记人</th>
		<td width="22%">
		${sessionScope.user.name }
		</td>
	</tr>
</table>
<input type="hidden" name="dictChildJson" id="dictChildJson"/>
<table id="childTB" data-options="onClickRow: onClickRow"></table>
</form>
<div id="childToolbar" style="padding:5px;height:auto">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">变化条数</a>
</div>
<%
	DictMain dictMain = (DictMain)request.getAttribute("dictMain");
	List<DictChild> dcList = dictMain.getDictChild();
	ObjectMapper objectMapper = new ObjectMapper();
	String dcJson = objectMapper.writeValueAsString(dcList);
	request.setAttribute("dcJson", dcJson);
%>
<script type="text/javascript">
$(function(){
	$('#childTB').datagrid({
		data : JSON.parse('${dcJson}'),
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
			{field:'name',title:'名称',width:30,editor:{type:'validatebox',options:{required:true}}},
			{field:'value',title:'值',width:30,editor:{type:'validatebox',options:{required:true}}},
			{field:'code',title:'编码',width:20,editor:{type:'validatebox'}},
			{field:'scode',title:'简码',width:20,editor:{type:'validatebox'}},
			{field:'orderNum',title:'顺序',width:10,editor:{type:'numberbox',options:{precision:0,required:true}}},
			{field:'remark',title:'备注',width:20,editor:{type:'validatebox'}}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar'
	});
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }    
	}); 
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow', {});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#dictChildJson').val(JSON.stringify(rows));
	}
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>
</body>
</html>