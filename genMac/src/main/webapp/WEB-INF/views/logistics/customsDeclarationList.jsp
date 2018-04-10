<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<link rel="stylesheet" href="${ctx}/static/plugins/orgChart/css/font-awesome.min.css">
<link href="${ctx}/static/plugins/easyui/jquery-easyui-theme/<c:out value="${cookie.themeName.value}" default="default"/>/jquery.orgchart.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/plugins/orgChart/css/style.css">
<script type="text/javascript" src="${ctx}/static/plugins/orgChart/js/jquery.orgchart.js"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
	    <input type="text" name="filter_LIKES_invoiceNo_OR_contractNo" class="easyui-validatebox" data-options="prompt: '提单号或合同号'"/>
	    <input type="text" name="filter_LIKES_customsDeclarationNo" class="easyui-validatebox" data-options="prompt: '报关单号'"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:customsDeclaration:registration">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="registration()">登记</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:customsDeclaration:trace">
		<span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace()">跟踪</a>
	</shiro:hasPermission>
</div>
<table id="dg" data-options="rowStyler: function(index,row){
								if (row.customsDeclarationNo != null && row.customsDeclarationNo.length > 0) {
									return 'color:red;font-style:italic;';
								}
							}"></table>
<div id="dlg"></div>
<div id="traceDlg"></div>
<div id="detailDlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/logistics/invoiceReg/findHaveFreight/all',
		fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'contractNo',title:'合同号',sortable:true,width:20},
			{field:'invoiceNo',title:'提单号',sortable:true,width:20},
			{field:'customsDeclarationNo',title:'报关单号',width:20},
			{field:'customsDeclarationUnit',title:'报关公司',width:20},
			{field:'arrivalPortDate',title:'到港日期',width:20},
		    {field:'letDate',title:'放行日期',width:20},
			{field:'inventoryWay',title:'库存方式',width:20},
		    {field:'customsDeclarationState',title:'清关状态',width:20}
		]],
		sortName:'id',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			trace();
		}
	});
});

function registration() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		width:500,
		height:300,
	    title: '登记',
	    href:'${ctx}/logistics/invoiceReg/customsDeclaration/' +  + row.id,
	    modal:true,
	    closable:false,
	    buttons:[{
			text:'保存',
			handler:function(){
				$("#mainform").submit();
			}
		},{
			text:'关闭',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

function trace(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#traceDlg").dialog({
		fit:true,
		style:{borderWidth:0},
	    title: '跟踪清关进展',    
	    href:'${ctx}/logistics/invoiceReg/customsDeclarationTrace/'+row.id,
	    modal:true,
	    closable:false,
	    buttons:[{
			text:'关闭',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//创建查询对象并查询
function cx() {
	var obj = $("#searchFrom").serializeObject();
	dg.datagrid('reload', obj);
}

function reset() {
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj = $("#searchFrom").serializeObject();
	dg.datagrid('reload', obj);
}
</script>
</body>
</html>