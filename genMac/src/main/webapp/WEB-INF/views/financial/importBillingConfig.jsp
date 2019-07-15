<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="config_searchFrom" action="">
	<input type="text" name="filter_LIKES_contractNo_OR_foreignContractNo" class="easyui-validatebox" data-options="prompt: '内或外合同号'"/>
	<input type="text" name="filter_EQL_customerId" class="easyui-combobox" data-options="
		prompt: '国内客户名称',
		url : '${ctx}/baseinfo/affiliates/getCompany/1',
		valueField : 'id',
		textField : 'customerName'
	"/>
    <span class="toolbar-item dialog-tool-separator"></span>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a>
</form>
<table id="config_dg"></table>
<script type="text/javascript">
var config_dg;
$(function(){
	config_dg=$('#config_dg').datagrid({
		method: "get",
		url:'${ctx}/agent/agentImport/filter?filter_EQS_state=生效',
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
			{field:'contractNo',title:'内合同号',sortable:true,width:20},
			{field:'customerId',title:'国内客户',sortable:true,width:20,
				formatter: function(value,row,index){
		    		$.ajax({
						url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + value,
						type : 'get',
						async : false,
						cache : false,
						dataType : 'json',
						success : function(data) {
							value = data[0].customerName;
						}
					});
					return value;
				}
			},
			{field:'foreignContractNo',title:'外合同号',sortable:true,width:20},
			{field:'foreignId',title:'供应外商',sortable:true,width:20,
				formatter: function(value,row,index){
		    		$.ajax({
						url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + value,
						type : 'get',
						async : false,
						cache : false,
						dataType : 'json',
						success : function(data) {
							value = data[0].customerName;
						}
					});
					return value;
				}
			},
		    {field:'createDate',title:'创建时间',sortable:true,width:25}
		]],
		sortName:'createDate',
		onClickRow : function(rowIndex, rowData) {
			$("input[name='selectRadio']")[rowIndex].checked = true;
		}
	});
});

//创建查询对象并查询
function cx_config() {
	var obj = $("#config_searchFrom").serializeObject();
	config_dg.datagrid('reload', obj);
}

function reset_config() {
	$("#config_searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj = $("#config_searchFrom").serializeObject();
	config_dg.datagrid('reload', obj);
}
</script>
</body>
</html>