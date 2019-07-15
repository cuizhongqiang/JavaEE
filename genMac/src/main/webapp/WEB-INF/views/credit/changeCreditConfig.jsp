<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="changeCredit_searchFrom" action="">
    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	<input type="text" name="filter_LIKES_bank" class="easyui-validatebox" data-options="prompt: '开证行'"/>
    <span class="toolbar-item dialog-tool-separator"></span>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a>
</form>
<table id="changeCredit_dg"></table>
<script type="text/javascript">
var changeCredit_dg;
$(function(){
	changeCredit_dg=$('#changeCredit_dg').datagrid({
		method: "get",
		url:'${ctx}/credit/openCredit/json?filter_EQS_state=生效',
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
				formatter:function(value, row, index){
					return '<input type="radio" name="selectRadio" id="selectRadio" value="' + row.id + '" />';
            	}
			},
			{field:'contractNo',title:'合同号',sortable:true},
			{field:'customerId',title:'客户信息',sortable:true,
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
		    		if(row.contractType == 0){
		    			value = "国内客户 - " + value;
		    			$.ajax({
							url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + row.foreignId,
							type : 'get',
							async : false,
							cache : false,
							dataType : 'json',
							success : function(data) {
								value = value + "，" + "供应外商 - " + data[0].customerName;
							}
						});
		    		}
					return value;
				}
			},
			{field:'bank',title:'开证行',sortable:true},
			{field:'lcType',title:'类型',sortable:true,
				formatter:function(value, row, index){
					if(value == 3){
						return "TT";
					}else{
						return "信用证";
					}
				}	
			},
			{field:'contractType',title:'合同类型',sortable:true,
				formatter: function(value,row,index){
		    		if(value == 0){
		    			return "代理进口";
		    		}else if(value == 1){
		    			return "(自营)采购";
		    		}else if(value == 2){
		    			return "(内贸)采购";
		    		}else if(value == 3){
		    			return "(内贸)代理采购";
		    		}
		    		return "";
				}
			},
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
	var obj = $("#changeCredit_searchFrom").serializeObject();
	changeCredit_dg.datagrid('reload', obj);
}

function reset_config() {
	$("#changeCredit_searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj = $("#changeCredit_searchFrom").serializeObject();
	changeCredit_dg.datagrid('reload', obj);
}
</script>
</body>
</html>