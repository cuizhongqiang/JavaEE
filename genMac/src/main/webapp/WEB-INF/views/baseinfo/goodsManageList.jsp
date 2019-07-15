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
	    <input type="text" name="filter_LIKES_goodsName" class="easyui-validatebox" data-options="width:150,prompt:'商品大类名称'"/>
	    <input type="text" name="filter_LIKES_goodsCode" class="easyui-validatebox" data-options="width:150,prompt:'商品大类码'"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
		
	<shiro:hasPermission name="sys:goodsManage:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:goodsManage:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:goodsManage:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:goodsManage:detail">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail();">查看明细</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div> 
<script type="text/javascript">
var dg;
var d;
var pid = 0;
$(function(){
	dg=$('#dg').treegrid({
		method: "get",
	    url:'${ctx}/baseinfo/goodsManage/json',
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		treeField:'goodsCode',
		parentField : 'pid',
		rownumbers:true,
		singleSelect:true,
	    columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCode',title:'商品大类码',width:5},
			{field:'goodsName',title:'商品大类名称',width:20}
	    ]],
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
	var row = dg.treegrid('getSelected');
	if (row != null) {
		pid = row.id;
	}
	d=$("#dlg").dialog({
	    title: '新增商品',
	    width: 650,
	    height: 360,
	    href:'${ctx}/baseinfo/goodsManage/create/' + pid,
	    maximizable:false,
	    modal:true,
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

//弹窗修改
function upd(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '修改商品',
	    width: 650,
	    height: 360,
	    href:'${ctx}/baseinfo/goodsManage/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				$('#mainform').submit();
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
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/baseinfo/goodsManage/delete/"+row.id,
				success: function(data){
					if(successTip(data,dg))
						dg.treegrid('reload');
				}
			});
		}
	});
}

//弹窗查看明细
function detail(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '查看商品明细',
	    width: 650,
	    height: 370,
	    href:'${ctx}/baseinfo/goodsManage/detail/'+row.id,
	    maximizable:false,
	    modal:true,
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
	dg.treegrid('reload',obj);
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.treegrid('reload',obj); 
}
</script>
</body>
</html>