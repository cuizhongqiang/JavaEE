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
	    <input type="text" name="filter_LIKES_invoiceNo_OR_contractNo" class="easyui-validatebox" data-options="prompt: '提单号或合同号'"/>
	    <input type="text" id="startTime" name="filter_BETWEEND_arriveDate_start" class="Wdate" placeholder="到单开始日期"/> - 
		<input type="text" id="endTime" name="filter_BETWEEND_arriveDate_end" class="Wdate" placeholder="到单结束日期"/>
		<input type="text" name="filter_EQI_contractType" class="easyui-combobox" data-options="
			panelHeight:'auto',
			prompt:'合同类型',
			data:[{id:0,name:'代理进口'},{id:1,name:'(自营)采购'},{id:2,name:'(内贸)采购'},{id:3,name:'(内贸)代理采购'}],
			valueField:'id',
			textField:'name',
			onLoadSuccess:function(){
				$(this).combobox('clear');
			}
		"/>
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	
	<shiro:hasPermission name="sys:invoiceReg:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:invoiceReg:delete">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:invoiceReg:update">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:invoiceReg:detail">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/logistics/invoiceReg/json',
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
			{field:'supplier',title:'供应商',sortable:true,width:20},
		    {field:'salesman',title:'业务员',sortable:true,width:20},
		    {field:'contractType',title:'合同类型',sortable:true,width:20,
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
		    {field:'createDate',title:'创建时间',sortable:true,width:25}
		]],
		sortName:'id',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	$('#startTime').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M-1}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',onpicked:function(){endTime.click();}});
	});
	$('#endTime').bind('click',function(){
	    WdatePicker({doubleCalendar:false,startDate:'%y-{%M-1}-%d',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({
		fit:true,
	    title: '添加到单登记',
	    href:'${ctx}/logistics/invoiceReg/create',
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'保存',
			handler:function(){
				if(accept()){
					$("#mainform").submit();
				}
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
function upd() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '修改到单登记',
	    href:'${ctx}/logistics/invoiceReg/update/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
	    buttons:[{
			text:'修改',
			handler:function(){
				if(accept()){
					$("#mainform").submit();
				}
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
				url:"${ctx}/logistics/invoiceReg/delete/" + row.id,
				success: function(data){
					successTip(data,dg);
				}
			});
		} 
	});
}

//弹窗查看
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({
		fit:true,
	    title: '查看明细',
	    href:'${ctx}/logistics/invoiceReg/detail/' + row.id,
	    modal:true,
	    closable:false,
	    style:{borderWidth:0},
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
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}
</script>
</body>
</html>