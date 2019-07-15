<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<shiro:hasPermission name="sys:countryArea:add">
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:countryArea:delete">
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="del()">删除</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:countryArea:update">
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="upd()">修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:countryArea:detail">
		<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search"  onclick="detail();">查看</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
var d;
var pid;
$(function(){
	dg=$('#dg').treegrid({
		method: "get",
	    url:'${ctx}/system/countryArea/json',
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'name',
		parentField : 'pid',
		animate:true,
		rownumbers:true,
		singleSelect:true,
		striped:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true},
	        {field:'name',title:'名称',width:50},
	        {field:'code',title:'编码',width:50},
	        {field:'scode',title:'简码',width:50},
	        {field:'registrant',title:'登记人',width:50},
	        {field:'registrantDept',title:'登记部门',width:50},
	        {field:'registrantDate',title:'登记时间',width:50,
	        	formatter: function(value,row,index){
		    		return formatterDate(value);
				}
	        }
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
	d=$('#dlg').dialog({
	    title: '新增国别区域',
	    width: 450,
	    height: 260,
	    closed: false,
	    cache: false,
	    maximizable:false,
	    resizable:true,
	    href:'${ctx}/system/countryArea/create',
	    modal: true,
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

//删除
function del(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/system/countryArea/delete/"+row.id,
				success: function(data){
					if(successTip(data,dg))
			    		dg.treegrid('reload');
				}
			});
		} 
	});
}

//修改
function upd(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '修改国别区域',
	    width: 450,
	    height: 260,
	    href:'${ctx}/system/countryArea/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'修改',
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

//查看
function detail(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
	    title: '查看国别区域',
	    width: 450,
	    height: 280,
	    href:'${ctx}/system/countryArea/detail/'+row.id,
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
</script>
</body>
</html>