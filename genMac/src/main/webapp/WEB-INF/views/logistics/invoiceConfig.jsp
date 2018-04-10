<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="invoice_searchFrom" action="">
    <input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="prompt: '提单号'"/>
	<input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '进口合同号'"/>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a>
</form>
<table id="invoice_dg"></table>
<script type="text/javascript">
var invoice_dg;
$(function(){
	invoice_dg=$('#invoice_dg').datagrid({
		method: "get",
		url:'${ctx}/logistics/invoiceReg/findHaveFreight/直运业务',
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 5,
		pageList : [ 5 ],
		singleSelect:true,
		columns:[[
		    {field:'id',title: '选择',
				formatter:function(value, rowData, rowIndex){
					return '<input type="radio" name="selectRadio" id="selectRadio" value="' + rowData.id + '" />';
	        	}
		    },
			{field:'contractNo',title:'合同号',sortable:true},
			{field:'invoiceNo',title:'提单号',sortable:true},
			{field:'supplier',title:'进口供应商',sortable:true},
		    {field:'salesman',title:'业务员',sortable:true},
		    {field:'createDate',title:'创建时间',sortable:true}
		]],
		sortName:'createDate',
		onClickRow : function(rowIndex, rowData) {
			$("input[name='selectRadio']")[rowIndex].checked = true;
		}
	});
});

//创建查询对象并查询
function cx_config() {
	var obj = $("#invoice_searchFrom").serializeObject();
	invoice_dg.datagrid('reload', obj);
}

function reset_config() {
	$("#invoice_searchFrom")[0].reset();
	var obj = $("#invoice_searchFrom").serializeObject();
	invoice_dg.datagrid('reload', obj);
}
</script>
</body>
</html>