<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="config_searchFrom" action="">
    <input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
    <c:choose>
    	<c:when test="${contractType eq 0 }">
    		<input type="text" name="filter_EQL_foreignId" class="easyui-combobox" data-options="
				prompt: '供应外商',
				url : '${ctx}/baseinfo/affiliates/getCompany/4',
				valueField : 'id',
				textField : 'customerName'
			"/>
    	</c:when>
    	<c:when test="${contractType eq 1 }">
    		<input type="text" name="filter_EQL_supplier" class="easyui-combobox" data-options="
				prompt: '供应商',
				url : '${ctx}/baseinfo/affiliates/getCompany/3,4',
				valueField : 'id',
				textField : 'customerName'
			"/>
    	</c:when>
    	<c:otherwise>
    		
    	</c:otherwise>
    </c:choose>
    <span class="toolbar-item dialog-tool-separator"></span>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx_config()">查询</a>
    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset_config()">重置</a>
</form>
<table id="config_dg"></table>
<script type="text/javascript">
var config_dg;
$(function(){
	var url;
	if('${contractType }' == 0){
		url = '${ctx}/agent/agentImport/findOpenCredit/${type}';
	}else if('${contractType }' == 1){
		url = '${ctx}/selfRun/purchase/findOpenCredit/${type}';
	}
	config_dg=$('#config_dg').datagrid({
		method: "get",
		url:url,
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
		    		if('${contractType }' == 0){
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