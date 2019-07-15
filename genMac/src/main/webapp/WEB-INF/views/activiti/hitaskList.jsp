<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<table id="dg_task"></table>
<script type="text/javascript">  
	var dg_task;
	dg_task=$('#dg_task').datagrid({
		nowrap:true,
		method: "get",
	    url:'${ctx}/workflow/trace/list/${pid}', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		pageNumber:1,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		striped:true,
	    columns:[[
			{field:'name',title:'审批节点',sortable:true,width:20},
	        {field:'assignee',title:'处理人 ',sortable:true,width:20},
	        {field:'startTime',title:'任务开始时间',sortable:true,width:25},
	        {field:'endTime',title:'任务结束时间',sortable:true,width:25},
	        {field:'comments',title:'审批意见',sortable:true,width:30}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    remoteSort:false,
		rowTooltip: true
	});
</script>
</body>  
</html>  