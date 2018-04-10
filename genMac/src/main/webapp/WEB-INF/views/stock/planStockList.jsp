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
	    <input type="text" name="filter_LIKES_createrName" class="easyui-validatebox" data-options="width:150,prompt: '盘库人'"/>
		<input type="text" id="startTime" name="filter_BETWEEND_createDate_start" class="Wdate" placeholder="盘库开始时间"/> - 
		<input type="text" id="endTime" name="filter_BETWEEND_createDate_end" class="Wdate" placeholder="盘库结束时间"/>
		<span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	<shiro:hasPermission name="sys:planStock:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:planStock:delete">
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:planStock:update">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:planStock:detail">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	$('#startTime').bind('click',function(){
	    WdatePicker({doubleCalendar:true,startDate:'%y-{%M-1}-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true,maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d %H:%m:%s\'}',onpicked:function(){endTime.click();}});
	});
	$('#endTime').bind('click',function(){
	    WdatePicker({doubleCalendar:true,startDate:'%y-{%M-1}-%d %H:%m:%s',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',autoPickDate:true});
	});
	
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/stock/planStock/json',
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[
			{field:'createrName',title:'盘库人',sortable:true,width:20},
			{field:'createrNo',title:'编号',sortable:true,width:20},
			{field:'createrDept',title:'部门',sortable:true,width:20},
			{field:'createDate',title:'盘库时间',sortable:true,width:20}
		]],
	    sortName:'createDate',
	    sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
	    onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '添加盘库', 
	    href:'${ctx}/stock/planStock/create',
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'保存',
			handler:function(){
				accept();
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

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/stock/planStock/delete/"+row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		}
	});
}

//弹窗修改
function upd(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '修改盘库',
	    href:'${ctx}/stock/planStock/update/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
	    closable:false,
	    buttons:[{
			text:'修改',
			handler:function(){
				accept();
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

//查看盘库明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '盘库明细',    
	    href:'${ctx}/stock/planStock/detail/'+row.id,
	    modal:true,
	    fit:true,
	    style:{borderWidth:0},
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
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-my97").combo('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}
</script>
</body>
</html>