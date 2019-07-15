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
		<input type="text" name="filter_LIKES_customerCode_OR_customerName" class="easyui-validatebox" data-options="width:150,prompt: '客户编码或客户名称'"/>
		<input type="text" id="customerType" name="customerType"/>
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
		<a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
	<shiro:hasPermission name="sys:affiliates:add">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:affiliates:delete">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	    <span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:affiliates:update">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
		<span class="toolbar-item dialog-tool-separator"></span>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:affiliates:detail">
	    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	</shiro:hasPermission>
</div>
<table id="dg"></table> 
<div id="dlg" ></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({  
		method: "get",
	    url:'${ctx}/baseinfo/affiliates/json', 
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
			{field:'id',title:'id',hidden:true},  
			{field:'customerCode',title:'客户编码',sortable:true,width:15},  
			{field:'customerName',title:'客户名称',sortable:true,width:20},
			{field:'customerType',title:'客户类型',sortable:true,width:25,
				formatter: function(value,row,index){
					$.ajax({
						url : '${ctx}/system/dict/getAllName/' + value + '/KHLX',
						type : 'get',
						cache : false,
						async : false,
						success : function(data) {
							value = data;
						}
					});
					return value;
				}
			},
			{field:'businessScope',title:'业务范围',sortable:true,width:20},
			{field:'createDate',title:'创建时间',sortable:true,width:15},
			{field:'status',title:'状态',sortable:true,width:10,
				formatter: function(value,row,index){
					if (row.status == 1){
						return '停用';
					} if(row.status == 0){
						return '正常';
					}else {
						return '';
					}
				}
			}
	    ]],
	    sortName:'customerCode',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
	
	//客户类型
	$('#customerType').combobox({
		method:'GET',
		url:'${ctx}/system/dict/getDictByCode/KHLX',
	    textField : 'name',
	    multiple:true,
	    width:250,
	    multiline:true,
		panelHeight:'auto',
		editable:false,
	    prompt: '客户类型',
	    onHidePanel:function(){}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增单位信息',    
	    href:'${ctx}/baseinfo/affiliates/create',
	    modal:true,
	    maximizable:false,
	    width: 800,    
	    height: 470,  
	    buttons:[{
			text:'保存',
			handler:function(){
				acceptBank();
				acceptCustomer();
				acceptAssure();
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
				url:"${ctx}/baseinfo/affiliates/delete/"+row.id,
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
	    title: '修改关联单位信息',    
	    href:'${ctx}/baseinfo/affiliates/update/'+row.id,
	    modal:true,
	    maximizable:false,
	    width: 800,    
	    height: 470, 
	    buttons:[{
			text:'修改',
			handler:function(){
				acceptBank();
				acceptCustomer();
				acceptAssure();
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

//查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '关联单位明细',    
	    href:'${ctx}/baseinfo/affiliates/detail/'+row.id,
	    modal:true,
	    maximizable:false,
	    width: 800,    
	    height: 450, 
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
	var array = new Array();
	$("input[name='customerType']").each(function(index, item){
		array.push(item.value);
	});
	var customerType = array.join(",");
	var obj=$("#searchFrom").serializeObject();
	obj.customerType = customerType;
	dg.datagrid('reload',obj);
}

function reset(){
	$("#searchFrom")[0].reset();
	$("#customerType").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	obj.customerType = "";
	dg.datagrid('reload',obj);
}

</script>
</body>
</html>