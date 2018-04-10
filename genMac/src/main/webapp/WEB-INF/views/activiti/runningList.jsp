<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div id="tbRunning" style="padding: 5px; height: auto">
	<form id="searchFrom" action="">
		<input type="text" name="applyPerson" class="easyui-validatebox" data-options="prompt: '初始发起者用户名'"/>
		<input type="text" name="businessInfo" class="easyui-validatebox" data-options="prompt: '业务信息'"/>
		<input type="text" name="curTaskName" class="easyui-validatebox" data-options="prompt: '当前流程节点'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	<shiro:hasPermission name="sys:running:effect">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="effect()">生效</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:running:delete">
		<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-busy" plain="true" onclick="del()">删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:running:trace">
		<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace()">跟踪</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<div id="traceDlg"></div>
<script type="text/javascript">
$(function() {
	dg=$('#dg').datagrid({    
		method: "get",
	    url:'${ctx}/workflow/task/running/list',
	    fit : false,
		fitColumns : true,
		border : false,
		rownumbers:true,
		striped:true,
		idField : 'processInstanceId',
		pagination:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20 ],
		singleSelect:true,
	    columns:[[
			{field:'processInstanceId',title:'processInstanceId',hidden:true},
			{field:'name',title:'流程名称',sortable:true},
			{field:'businessInfo',title:'业务信息',sortable:true},
			{field:'applyPerson',title:'初始发起者',sortable:true},
			{field:'curTaskName',title:'当前流程节点',sortable:true},
	        {field:'curTaskCreateTime',title:'当前任务创建时间',sortable:true},
	        {field:'suspended',title:'流程状态',sortable:true,
	        	formatter: function(value,row,index){
					if(value == false){
						return '未挂起';
					} else {
						return '已挂起';
					}
				}	
	        },
	        {field:'curTaskAssignee',title:'当前处理人',sortable:true}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tbRunning',
	    remoteSort:false
	});
});

//流程跟踪
function trace() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#traceDlg").dialog({
	    title: '流程跟踪',
	    width: 680,
	    height: 300,
	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
	    maximizable:true,
	    modal:true,
	    buttons:[{
			text:'关闭',
			handler:function(){
				d.panel('close');
			}
		}]
	});
}

//生效
function effect(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '您确定要强制生效此流程？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/effect/" + row.processInstanceId,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除此流程？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/delete/" + row.processInstanceId,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
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