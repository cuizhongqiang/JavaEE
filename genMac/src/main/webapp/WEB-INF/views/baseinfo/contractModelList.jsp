<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
		<input type="text" name="filter_LIKES_accAuthor" class="easyui-validatebox" data-options="width:150,prompt: '上传人'"/>
		<input type="text" name="filter_LIKES_accRealName" class="easyui-validatebox" data-options="width:150,prompt: '附件原名'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	<shiro:hasPermission name="sys:contracts:up">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-up" plain="true" onclick="toUpload();">上传</a>
	</shiro:hasPermission>
</div>

<table id="dg"></table> 
<div id="dlgUpload"></div> 
<div id="dlg" ></div>  
<script type="text/javascript">
var urlValue = '${ctx}/baseinfo/contracts/json?filter_EQS_accParentId=1&filter_EQS_accParentEntity=com_cbmie_genMac_baseinfo_entity_ContractModel';
var dg;
$(function(){   
	dg=$('#dg').datagrid({
		method: "get",
	    url:urlValue,
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 15,
		pageList : [ 5, 10, 15, 20, 25 ],
		singleSelect:true,
	    columns:[[    
			{field:'id',title:'id',hidden:true},  
			{field:'accAuthor',title:'上传人',sortable:true,width:20},
			{field:'accRealName',title:'附件原名',sortable:true,width:40},
			{
				field : 'accId',
				title : '操作',
				align : 'center',
				width : 10,
				formatter : function(value, row, index) {
					var str = "";
					str += "&nbsp&nbsp&nbsp";
					str += "<a name='down' style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>";
					str += "<span class='toolbar-item dialog-tool-separator'></span>";
					str += "<a name='remove' style='text-decoration:none' href='#' onclick='delAcc(" + value + ");'>删除</a>";
					return str;
				}
			}
	    ]],
	    sortName:'id',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
	 	onLoadSuccess:function(data){
	 		$("a[name='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	    	$("a[name='down']").linkbutton({text:'下载',plain:true,iconCls:'icon-hamburg-down'});
	    	
	    }
	});
});

//跳转上传文件
function toUpload() {
	dUpload = $("#dlgUpload").dialog({
		title : '上传文件',
		height:350,
		width:500,
		href : '${ctx}/baseinfo/contracts/toUpload/1',
		maximizable : false,
		closable:false,
		modal : true,
		buttons : [ {
			text : '关闭',
			handler : function() {
				$('#dg').datagrid('reload');
				dUpload.panel('close');
			}
		} ]
	});
}

//删除附件
function delAcc(idValue) {
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
		if (data) {
			$.ajax({
				type : 'get',
				url : "${ctx}/accessory/delete/" + idValue,
				success : function(data) {
					if (data == "success") {
						successTip(data, dg);
					}
				}
			});
		}
	});
}

//下载附件
function downnloadAcc(id) {
	window.open("${ctx}/accessory/download/" + id, '下载');
}

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj);
}

function reset(){
	$("#searchFrom")[0].reset();
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj);
}

</script>
</body>
</html>