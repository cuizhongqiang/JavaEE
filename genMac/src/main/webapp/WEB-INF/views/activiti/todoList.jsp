<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html>  
<head>  
<title></title>  
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>  
<body>
<div>
<div id="worktab">
	<div style="width:60%;">
		<div id="pToDo" class="easyui-panel" style="padding: 0px;" data-options="title: '待办任务',closable: false,collapsible: true">
			<div id="tbToDo" style="padding: 5px; height: auto">
				<form id="searchFromToDo" action="">
					<input type="text" name="businessInfo" class="easyui-validatebox" data-options="prompt: '业务信息'"/>
					<span class="toolbar-item dialog-tool-separator"></span>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cxToDo()">查询</a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="resetToDo()">重置</a>
				</form>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cologne-pencil" plain="true" onclick="claim();">签收</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cologne-communication" plain="true" onclick="deal();">办理</a>
			</div>
			<table id="dgToDo"></table>
		</div>
		<div id="pHaveDone" class="easyui-panel" style="padding: 0px;" data-options="title: '已办任务',closable: false,collapsible: true">
			<div id="tbHaveDone" style="padding: 5px; height: auto">
				<form id="searchFromHaveDone" action="">
					<input type="text" name="businessInfo" class="easyui-validatebox" data-options="prompt: '业务信息'"/>
					<span class="toolbar-item dialog-tool-separator"></span>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cxHaveDone()">查询</a>
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="resetHaveDone()">重置</a>
				</form>
				<a href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true" onclick="callBack();">撤回</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace('haveDone');">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail('haveDone')">查看详情</a>
			</div>
			<table id="dgHaveDone"></table>
		</div>
	</div>
	<div style="width:40%;">
		<!-- <div class="easyui-panel" data-options=" title: '日历',closable: false,collapsible: true, iconCls: 'icon-standard-date', region: 'north', split: false" style="height: 220px;">
			<div class="easyui-calendar" data-options="fit: true, border: false"></div>
        </div> -->
        <div id="pEncyclic" class="easyui-panel" data-options="title: '传阅',closable: false,collapsible: true">
			<div id="tbEncyclic" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-flag" plain="true" onclick="trace('encyclic');">流程跟踪</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="detail('encyclic')">查看详情</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delEncyclic();">删除</a>
			</div>
			<table id="dgEncyclic"></table>
		</div>
        <div id="pInform" class="easyui-panel" data-options="title: '通知',closable: false,collapsible: true,tools: [{ iconCls: 'icon-hamburg-refresh', handler: function () { window.link.reload(); } }]">
			<div id="tbInform" style="padding: 5px; height: auto">
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delInform();">删除</a>
			</div>
			<table id="dgInform"></table>
		</div>
	</div>
	<!-- <div style="width:25%;">
		
	</div> -->
</div>
<div id="dlg"></div>
<div id="traceDlg"></div>
<div id="dealDlg"></div>
<div id="approvalDlg"></div>
</div>
<script type="text/javascript">
$('#worktab').portal({
	border: false,
	fit: false
});

var dg;
var dgToDo;
var dgHaveDone;
var dgEncyclic;
var dgInform;

dgToDo=$('#dgToDo').datagrid({
	method: "get",
    url:'${ctx}/workflow/task/todo/list',
    fit : false,
	fitColumns : true,
	border : false,
	striped:true,
	rownumbers:true,
	idField : 'id',
	pagination:true,
	pageNumber:1,
	pageSize : 5,
	pageList : [ 5, 10 ],
	singleSelect:true,
    columns:[[    
		{field:'pid',title:'pid',hidden:true},
		{field:'id',title:'id',hidden:true},
		{field:'activityId',title:'activityId',hidden:true},
		{field:'businessKey',title:'businessKey',hidden:true},
        {field:'status',title:'任务状态',sortable:true},
		{field:'businessInfo',title:'业务信息',sortable:true},
        {field:'name',title:'当前节点',sortable:true},
        {field:'processKey',title:'流程KEY',sortable:true},
        {field:'pdversion',title:'流程版本',sortable:true},
        {field:'createTime',title:'创建时间',sortable:true}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbToDo',
    remoteSort:false
});

dgHaveDone=$('#dgHaveDone').datagrid({
	method: "get",
    url:'${ctx}/workflow/task/haveDone/list', 
    fit : false,
	fitColumns : true,
	rownumbers:true,
	border : false,
	striped:true,
	idField : 'processInstanceId',
	pagination:true,
	pageNumber:1,
	pageSize : 5,
	pageList : [ 5, 10 ],
	singleSelect:true,
    columns:[[
		{field:'processInstanceId',title:'processInstanceId',hidden:true},
		{field:'taskId',title:'任务ID',hidden:true},
		{field:'activityId',title:'activityId',hidden:true},
		{field:'currentActivityId',title:'currentActivityId',hidden:true},
		{field:'name',title:'流程名称',sortable:true},
		{field:'businessInfo',title:'业务信息',sortable:true},
		{field:'currentTaskName',title:'当前节点',sortable:true},
        {field:'startTime',title:'开始时间',sortable:true},
        {field:'endTime',title:'结束时间',sortable:true},
        {field:'callBack',title:'可撤回',
        	formatter: function(value, row, index){
        		if(value == true){
        			return "是";
        		}else{
        			return "否";
        		}
        	}	
        }
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbHaveDone',
    remoteSort:false
});

dgEncyclic=$('#dgEncyclic').datagrid({
	method: "get",
    url:'${ctx}/workflow/encyclic/json?filter_EQS_loginName=${sessionScope.user.loginName}',
    fit : false,
	fitColumns : true,
	rownumbers:true,
	border : false,
	striped:true,
	idField : 'id',
	pagination:true,
	pageNumber:1,
	pageSize : 5,
	pageList : [ 5, 10 ],
	singleSelect:true,
    columns:[[
		{field:'id',title:'id',hidden:true},
		{field:'state',title:'',
			formatter : function(value, row, index) {
				var str = "";
				if(value == 0){
					str = "<span name='unRead'></span>";
				}else{
					str = "<span name='haveRead'></span>";
				}
				return str;
			}
		},
		{field:'sendName',title:'发送人',sortable:true},
		{field:'businessInfo',title:'业务信息',sortable:true},
		{field:'createDate',title:'发送时间',sortable:true}
    ]],
    sortName:'createDate',
    sortOrder:'desc',
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbEncyclic',
    remoteSort:false,
    onLoadSuccess:function(data){
    	$("span[name='unRead']").linkbutton({text:'',plain:true,iconCls:'icon-hamburg-email'});
    	$("span[name='haveRead']").linkbutton({text:'',plain:true,iconCls:'icon-hamburg-contact'});
    }
});

dgInform=$('#dgInform').datagrid({
	method: "get",
    url:'${ctx}/system/inform/json?filter_EQS_person=${sessionScope.user.loginName}', 
    fit : false,
    nowrap : true,
	fitColumns : true,
	rownumbers:true,
	border : false,
	striped:true,
	idField : 'id',
	pagination:true,
	pageNumber:1,
	pageSize : 5,
	pageList : [ 5, 10 ],
	singleSelect:true,
    columns:[[
		{field:'id',title:'id',hidden:true},
		{field:'subject',title:'标题',width:15},
		{field:'content',title:'内容',width:20}
    ]],
    sortName:'createDate',
    sortOrder:'desc',
    remoteSort:false,
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tbInform',
    rowTooltip: true
});

//流程跟踪
function trace(str) {
	var row;
	if(str == 'running'){
		row = dgRunning.datagrid('getSelected');
	}else if(str == 'haveDone'){
		row = dgHaveDone.datagrid('getSelected');
	}else if(str == 'encyclic'){
		row = dgEncyclic.datagrid('getSelected');
		$.ajax({
			type:'POST',
			url:"${ctx}/workflow/encyclic/update",
			data:{id : row.id},
			async:false,
			success: function(data){
				if(data == "success"){
					dgEncyclic.datagrid('reload');
				}
			}
		});
	}
	if(rowIsNull(row)) return;
	d=$("#traceDlg").dialog({
	    title: '流程跟踪',
	    width: 800,
	    height: 380,
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

//流程办理
function deal() {
	var row = dgToDo.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(row.status == "待签收"){
		$.messager.alert("任务未签收，不能进行审批！");
		return;
	}
	if("apply" == row.activityId){
		d=$("#dlg").dialog({   
			title: '修改申请',    
		    href:'${ctx}/workflow/reApply/go/' + row.pid,
		    fit:true,
		    style:{borderWidth:0},
		    maximizable:false,
		    closable:false,
		    modal:true,
		    buttons:[{
		    	iconCls:'icon-edit',
				text:'修改',
				handler:function(){
					if(typeof accept == "function"){
						var acc = accept();
						if(typeof acc == "undefined"){
							$("#mainform").submit();
						}else{
							if(acc){
								$("#mainform").submit();
							}
						}
					}else{
						$("#mainform").submit();
					}
				}
			},{
				iconCls:'icon-redo',
				text:'提交申请',
				handler:function(){
					parent.$.messager.confirm('提示', '您确定要提交申请？', function(data){
						if (data){
							if(typeof accept == "function"){
								accept();
							}
							$("#mainform").submit();
							$.ajax({
								type:'get',
								url:"${ctx}/workflow/reApply",
								data:{pid:row.pid,id:row.id},
								success: function(data){
									successTip(data,dgToDo,d);
									dgHaveDone.datagrid('reload');
									dgRunning.datagrid('reload');
								}
							});
						}
					});
				}
			},{
				iconCls:'icon-hamburg-trash',
				text:'废除流程',
				handler:function(){
					parent.$.messager.confirm('提示', '您确定要废除该流程？', function(data){
						if (data){
							$.ajax({
								type:'get',
								url:"${ctx}/workflow/abolishApply/"+row.id,
								success: function(data){
									successTip(data,dgToDo,d);
									dgRunning.datagrid('reload');
								}
							});
						}
					});
				}
			},{
				iconCls:'icon-cancel',
				text:'关闭',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	}else{
		dealDlg=$("#dealDlg").dialog({
			title:row.businessInfo,
		    href:'${ctx}/workflow/deal?businessKey='+row.businessKey+'&processKey='+row.processKey,
		    fit:true,
		    style: {borderWidth:0},
		    closable:true,
		    modal:false,
		    buttons:[{
		    	iconCls:'icon-hamburg-pencil',
				text:'审批',
				handler:function(){
					approvalDlg=$("#approvalDlg").dialog({
						noheader:true,
					    href:'${ctx}/workflow/approval?pid='+row.pid+'&id='+row.id+'&businessKey='+row.businessKey+'&processKey='+row.processKey,
					    width:1000,
					    height:500,
					    closable:true,
					    modal:false,
					    buttons:[{
					    	iconCls:'icon-cancel',
					    	text:'关闭',
							handler:function(){
								approvalDlg.panel('close');
							}
					    }]
					});
				}
			},{
				iconCls:'icon-cancel',
		    	text:'关闭',
				handler:function(){
					dealDlg.panel('close');
				}
		    }]
		});
	}
}

//签收
function claim(){
	var row = dgToDo.datagrid('getSelected');
	if(rowIsNull(row)) return; 
	if(row.status == "待办"){
		$.messager.alert("任务已签收，不能重复签收！");
		return;
	}
	parent.$.messager.confirm('提示', '确定签收当前任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/task/claim/"+row.id,
				success: function(data){
					successTip(data,dgToDo);
				}
			});
		}
	});
}

//撤回
function callBack(){
	var row = dgHaveDone.datagrid('getSelected');
	if(rowIsNull(row)) return;
	if(!row.callBack){
		$.messager.alert("任务已签收，不能撤回！");
		return;
	}
	parent.$.messager.confirm('提示', '确定撤回当前任务？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/callBack",
				data:{currentTaskId:row.currentTaskId,activityId:row.activityId},
				async:false,
				success: function(data){
					successTip(data,dgHaveDone);
					dgToDo.datagrid('reload');
					dgRunning.datagrid('reload');
				}
			});
		}
	});
}

//查看详情
function detail(str){
	var row;
	if(str == 'haveDone'){
		row = dgHaveDone.datagrid('getSelected');
	}else if(str = 'encyclic'){
		row = dgEncyclic.datagrid('getSelected');
	}
	if(rowIsNull(row)) return;
	d=$("#dealDlg").dialog({
		title:row.businessInfo,
	    href:'${ctx}/workflow/deal?businessKey='+row.businessKey+'&processKey='+row.processKey,
	    fit:true,
	    style: {borderWidth:0},
	    closable:true,
	    modal:false,
	    buttons:[{
			iconCls:'icon-cancel',
	    	text:'关闭',
			handler:function(){
				d.panel('close');
			}
	    }]
	});
}

//删除传阅
function delEncyclic(){
	var row = dgEncyclic.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '您确定要删除此传阅？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/encyclic/delete/" + row.id,
				success: function(data){
					successTip(data,dgEncyclic);
				}
			});
		} 
	});
}

//删除通知
function delInform(){
	var row = dgInform.datagrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '您确定要删除此通知？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/system/inform/delete/" + row.id,
				success: function(data){
					successTip(data,dgInform);
				}
			});
		} 
	});
}

//创建查询对象并查询
function cxToDo(){
	var obj=$("#searchFromToDo").serializeObject();
	dgToDo.datagrid('reload',obj);
}

function resetToDo(){
	$("#searchFromToDo")[0].reset();
	var obj=$("#searchFromToDo").serializeObject();
	dgToDo.datagrid('reload',obj);
}

function cxHaveDone(){
	var obj=$("#searchFromHaveDone").serializeObject();
	dgHaveDone.datagrid('reload',obj);
}

function resetHaveDone(){
	$("#searchFromHaveDone")[0].reset();
	var obj=$("#searchFromHaveDone").serializeObject();
	dgHaveDone.datagrid('reload',obj);
}
</script>
</body>
</html>