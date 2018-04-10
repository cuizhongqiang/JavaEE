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
		<input type="text" name="filter_LIKES_contractNo" class="easyui-validatebox" data-options="prompt: '合同号'"/>
	    <input type="text" name="filter_LIKES_bank" class="easyui-validatebox" data-options="prompt: '开证行'"/>
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
	
	<shiro:hasPermission name="sys:openCreditApply:add">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:delete">
		<span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="del()">删除</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:update">
	    <span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:detail">
	    <span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail()">查看明细</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:apply">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="apply()">提交申请</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:callBack">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回申请</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:trace">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace();">流程跟踪</a>
	</shiro:hasPermission>
	<shiro:hasPermission name="sys:openCreditApply:reg">
		<span class="toolbar-item dialog-tool-separator"></span>
		<a href="#" class="easyui-linkbutton" iconCls="icon-standard-pencil-add" plain="true" onclick="reg();">登记</a>
	</shiro:hasPermission>
</div>
<table id="dg" data-options="
				rowStyler: function(index,row){
					if (row.processInstanceId != null){
						return 'color:red;font-style:italic;';
					}
				}
			"></table>
<div id="dlg"></div>
<div id="traceDlg"></div>
<div id="configDlg"></div>
<div id="applyDlg"></div>
<script type="text/javascript">
var dg;
var lcType;
var typeName;
if('${type}' == 'LC'){
	lcType = "filter_NEI_lcType";
	typeName = "信用证";
}else{
	lcType = "filter_EQI_lcType";
	typeName = "TT";
}
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
		url:'${ctx}/credit/openCredit/json?' + lcType + '=3',
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
			{field:'customerId',title:'客户信息',sortable:true,width:50,
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
		    		if(row.contractType == 0){
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
		    {field:'bank',title:'开证行',sortable:true,width:25},
		    {field:'createDate',title:'创建时间',sortable:true,width:25},
		    {field:'state',title:'状态',sortable:true,width:25,
		    	formatter: function(value,row,index){
		    		var value = getState('${ctx}/workflow/findCurrentTaskList/' + row.processInstanceId, value);
		    		if(row.openDate != null){
		    			return value + "&已登记";
		    		}
		    		return value;
		    	}
		    }
		]],
		sortName:'contractNo',
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#tb',
		onLoadSuccess:function(data){
			if (data.rows.length > 0) {
				mergeCellsByField("dg", "contractNo");
			}
		},
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({
		fit:true,
		style:{borderWidth:0},
	    title: '添加' + typeName + '申请',
	    href:'${ctx}/credit/openCredit/create/${type}',
	    modal:true,
	    closable:false,
	    buttons:[{
			text:'提交申请',
			handler:function(){
				if(accept()){
					$("#apply").val("true");
					$("#mainform").submit();
				}
			}
		},{
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能修改！','info');
		return;
	}
	d=$("#dlg").dialog({
		fit:true,
		style:{borderWidth:0},
	    title: '修改' + typeName + '申请',
	    href:'${ctx}/credit/openCredit/update/${type}/' + row.id,
	    modal:true,
	    closable:false,
	    buttons:[{
			text:'提交申请',
			handler:function(){
				if(accept()){
					$("#apply").val("true");
					$("#mainform").submit();
				}
			}
		},{
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
	if(row.processInstanceId != null){
		$.messager.alert('提示','表单已提交申请，不能删除！','info');
		return;
	}
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if(data){
			$.ajax({
				type:'get',
				url:"${ctx}/credit/openCredit/delete/" + row.id,
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
		width : 1000,
		height : 500,
	    title: '查看明细',
	    href:'${ctx}/credit/openCredit/detail/' + row.id,
	    modal:true,
	    closable:false,
	    buttons:[{
			text:'打印',
			handler:function(){
				$("#detailTab").jqprint();
			}
		},{
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

//申请
function apply(id){
	var row = dg.datagrid('getSelected');
	if(id == null){
		if(rowIsNull(row)) return;
		if(row.processInstanceId != null){
			$.messager.alert('提示','表单已提交申请，不能重复提交！', 'info');
			return;
		}
		id = row.id;
	}
	applyDlg=$("#applyDlg").dialog({
		noheader:true,
		width: 680,
	    height: 300,
	    href:"${ctx}/credit/openCredit/apply/" + id,
	    maximizable:false,
	    modal:true
	});
}

//撤回
function callBack(){
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.processInstanceId == null){
		$.messager.alert('提示','表单未提交申请，不存在撤回申请！', 'info');
		return;
	}
	parent.$.messager.confirm('提示', '您确定要撤回申请？', function(data){
		if (data){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
			$.ajax({
				type:'get',
				url:"${ctx}/credit/openCredit/callBack/" + row.id + "/" + row.processInstanceId,
				success: function(data){
			    	successTip(data,dg);
					if(data=='success'){
						parent.$.messager.show({ title:"提示", msg:"已成功撤回申请！", position:"bottomRight" });
					}
					parent.$.messager.progress('close');
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
		$.messager.alert('提示','表单未提交申请，不存在流程跟踪！', 'info');
		return;
	}
	$.ajaxSetup({type : 'GET'});
	d=$("#traceDlg").dialog({   
	    title: '流程跟踪',
	    width: 680,    
	    height: 300,    
	    href:'${ctx}/workflow/trace/'+row.processInstanceId,
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

//弹窗登记
function reg() {
	var row = dg.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.state != "生效"){
		$.messager.alert('提示', '表单未生效，不允许登记！', 'info');
		return;
	}
	d=$("#dlg").dialog({
		fit:true,
		style:{borderWidth:0},
	    title: typeName + '登记',
	    href:'${ctx}/credit/openCredit/reg/' + row.id,
	    modal:true,
	    closable:false,
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
</script>
</body>
</html>