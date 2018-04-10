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
	<div>
		<form id="searchFrom" action="">
	        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '商品名称'"/>
	        <input type="text" id="filter_EQI_goodsTypeId" name="filter_EQI_goodsTypeId" class="easyui-combobox" style="height:22px;width:100px;" data-options="panelHeight:'auto',prompt: '商品类型'"/>
	        <input type="text" id="filter_LIKES_isSold" name="filter_LIKES_isSold" class="easyui-combobox" style="height:22px;width:70px;"  data-options="panelHeight:'auto',prompt: '是否上架'"/>
	        <span class="toolbar-item dialog-tool-separator"></span>
	        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	        <a href="javascript(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
		</form>
		
	   	<shiro:hasPermission name="sys:goods:add">
	   		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	   		<span class="toolbar-item dialog-tool-separator"></span>
	   	</shiro:hasPermission>
	   	<shiro:hasPermission name="sys:goods:delete">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false" onclick="del()">删除</a>
	        <span class="toolbar-item dialog-tool-separator"></span>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="sys:goods:update">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	        <span class="toolbar-item dialog-tool-separator"></span>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="sys:goods:apply">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
	    	<span class="toolbar-item dialog-tool-separator"></span>
	    </shiro:hasPermission>
	    <shiro:hasPermission name="sys:goods:callBack">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
	    <shiro:hasPermission name="sys:goods:trace">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace();">流程跟踪</a>
			<span class="toolbar-item dialog-tool-separator"></span>
		</shiro:hasPermission>
		<shiro:hasPermission name="sys:goods:detail">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail();">查看明细</a>
		</shiro:hasPermission>
		<!-- 上传按钮 -->
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-old-versions" plain="true" onclick="toUpload();">上传附件</a> -->
	</div>
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table> 
<div id="dlg"></div>  
<div id="dlg_upd"></div>  
<script type="text/javascript">
var dg;
$(function(){   
	dg=$('#dg').datagrid({    
	method: "get",
    url:'${ctx}/shop/goods/json', 
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
		{field:'name',title:'名称',sortable:true,width:20},
		{field:'goodsTypeId',title:'商品类型',sortable:true,width:20,
			formatter: function(value,row,index){
				var val;
				$.ajax({
					type:'POST',
					async: false,
					url:"${ctx}/shop/goodsType/typeAjax/"+row.goodsTypeId,
					contentType:'application/json;charset=utf-8',
					success: function(data){
						if(data != null){
							val = data;
						} else {
							val = '';
						}
					}
				});
				return val;
			}
		},
        {field:'price',title:'单价（元）',sortable:true,width:20},    
        {field:'sales',title:'销量（个）',sortable:true,width:20},
        {field:'isSold',title:'是否上架',sortable:true,width:20,
        	formatter: function(value,row,index){
				if (row.isSold == 1){
					return '是';
				} else {
					return '否';
				}
			}
        },
        {field:'postage',title:'邮费（元）',sortable:true,width:20},
        {field:'createDate',title:'创建时间',sortable:true,width:25},
        {field:'processInstanceId',title:'申请状态',sortable:true,width:20,
        	formatter: function(value,row,index){
				if (row.processInstanceId != null){
					return '已申请';
				} else {
					return '未申请';
				}
			}
        },
        {field:'state',title:'是否生效',sortable:true,width:20}
    ]],
    sortName:'processInstanceId',
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
	    title: '注带红色*的为必填项',
	    href:'${ctx}/shop/goods/create',
	    fit:true,
	    style:{borderWidth:0},
	    maximizable:false,
	    closable:false,
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

//删除
function del(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/shop/goods/delete/"+row.id,
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg_upd").dialog({   
	    title: '修改商品',    
	    width: 420,    
	    height: 400,    
	    href:'${ctx}/shop/goods/update/'+row.id,
	    maximizable:false,
	    closable:true,
	    modal:true,
	    buttons:[{
			text:'修改',
			handler:function(){
				accept();
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

//弹窗查看明细
function detail(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	d=$("#dlg").dialog({   
	    title: '查看商品明细',    
	    width: 420,    
	    height: 400,    
	    href:'${ctx}/shop/goods/detail/'+row.id,
	    fit:true,
	    style:{borderWidth:0},
	    maximizable:false,
	    closable:false,
	    modal:true,
	    buttons:[{
			text:'打印',
			handler:function(){window.open('${ctx}/shop/goods/print','打印');}
		},{
			text:'关闭',
			handler:function(){d.panel('close');}
		}]
	});
}

//申请
function apply(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能重复提交！','info');
		return;
	}
	parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/shop/goods/apply/"+row.id,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "流程已启动！", position: "bottomRight" });
						//parent.d.panel('close');
					}
			    	if(data=='no deployment'){
			    		parent.$.messager.show({ title : "提示",msg: "没有部署流程！", position: "bottomRight" });
			    	}
			    	if(data=='start fail'){
			    		parent.$.messager.show({ title : "提示",msg: "启动流程失败！", position: "bottomRight" });
			    	}
				}
			});
		}
	});
}

//撤回
function callBack(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在撤回申请！','info');
		return;
	}
	parent.$.messager.confirm('提示', '您确定要撤回申请？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/shop/goods/callBack/"+row.id+"/"+row.processInstanceId,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title : "提示",msg: "已成功撤回申请！", position: "bottomRight" });
					}
				}
			});
		}
	});
}

//流程跟踪
function trace() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！','info');
		return;
	}
	$.ajaxSetup({type : 'GET'});
	d=$("#dlg").dialog({   
	    title: '流程跟踪',
	    width: 680,    
	    height: 300,    
	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
	    maximizable:false,
	    modal:true,
	    //onResize:function(){$(this).dialog('center');},
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
	//alert(JSON.stringify(obj));
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-combobox").combobox('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

$('#filter_EQI_goodsTypeId').combobox({
	url:'${ctx}/shop/goodsType/allTypeAjax',
    valueField:'id',
    textField:'name'
});

$('#filter_LIKES_isSold').combobox({
	url:'${ctx}/shop/goods/yesNoAjax',
    valueField:'id',
    textField:'text'
});

</script>
</body>
</html>