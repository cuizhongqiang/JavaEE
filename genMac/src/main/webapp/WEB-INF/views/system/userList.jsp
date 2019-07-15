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
		        <input type="text" name="filter_LIKES_name" class="easyui-validatebox" data-options="width:150,prompt: '昵称'"/>
		        <input type="text" name="filter_LIKES_phone" class="easyui-validatebox" data-options="width:150,prompt: '电话'"/>
		        <input type="text" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '开始日期'"/>
		        - <input type="text" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width:150,prompt: '结束日期'"/>
		        <span class="toolbar-item dialog-tool-separator"></span>
		        <a id="user_select" href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
			</form>
			
		   	<shiro:hasPermission name="sys:user:add">
		   		<a id="user_add" href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		   		<span class="toolbar-item dialog-tool-separator"></span>
		   	</shiro:hasPermission>
		   	<shiro:hasPermission name="sys:user:delete">
		        <a id="user_del" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false">删除</a>
		    	<span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <shiro:hasPermission name="sys:user:update">
		        <a id="user_upd" href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		        <span class="toolbar-item dialog-tool-separator"></span>
		    </shiro:hasPermission>
		    <a id="user_init" href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true">初始化密码</a>
		    <span class="toolbar-item dialog-tool-separator"></span>
		    <shiro:hasPermission name="sys:user:roleView">
				<a id="user_role" href="#" class="easyui-linkbutton" iconCls="icon-hamburg-suppliers" plain="true">用户角色</a>
				<span class="toolbar-item dialog-tool-separator"></span>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:orgView">
				<a id="user_org" href="#" class="easyui-linkbutton" iconCls="icon-cologne-home" plain="true">用户所属机构</a>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="dg"></table>
	<div id="dlg"></div>
	
<script type="text/javascript">

/**
var sys = window.sys || {};
sys.UserTest = new function() {
  var self_ = this;
  var name = 'world';
  self_.sayHello = function(_name) {
    return 'Hello ' + (_name || name);
  };
  
  var test = function(){alert("test");}
  var abc = function(){
	  //alert(3333);
	  //alert(self_.sayHello("qwe"));
	  $("#user_test").click(test);
  }
  //alert(self_.sayHello("qwe"));
  abc();
};
**/

var sys = window.sys || {};
sys.UserManager = new function() {
	var self_ = this;
	var dlg;
	//用户列表
	var dg = $('#dg').datagrid({
		method: "get",
	    url:'${ctx}/system/user/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		striped:true,
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 20,
		pageList : [ 10, 20, 30, 40, 50 ],
		singleSelect:true,
	    columns:[[
	        {field:'id',title:'id',hidden:true},    
	        {field:'loginName',title:'帐号',sortable:true,width:15},    
	        {field:'name',title:'昵称',sortable:true,width:10},
	        {field:'organization',title:'部门',sortable:true,width:15,
	        	formatter: function(value,row,index){
					var val;
					if(row.id!=''&&row.id!=null){
						$.ajax({
							type:'GET',
							async: false,
							url:"${ctx}/system/user/getDeptName/"+row.id,
							success: function(data){
								if(data != null){
									val = data;
								} else {
									val = '';
								}
							}
						});
						return val;
					}else{
						return '';
					}
				}
	        },
	        {field:'gender',title:'性别',sortable:true,width:5,
	        	formatter : function(value, row, index) {
	       			return value==1?'男':'女';
	        	}
	        },
	        {field:'phonePermission',title:'手机可登陆',sortable:true,width:5,
	        	formatter : function(value, row, index) {
	       			return value==1?'是':'否';
	        	}
	        },
	        {field:'email',title:'email',sortable:true,width:10},
	        {field:'phone',title:'电话',sortable:true,width:10},
	        {field:'loginCount',title:'登录次数',sortable:true,width:10},
	        {field:'lastVisit',title:'最后一次登录',sortable:true,width:10,
	        	formatter : function(value, row, index) {
	        		var res = "";
	        		if(value!=null){
	        			var d = new Date()
	        			d.setTime(value);
	        			res = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
	        		}
	       			return res;
	        	}}
	    ]],
	    headerContextMenu: [
	        {
	            text: "冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", true).contains(field); },
	            handler: function (e, field) { dg.datagrid("freezeColumn", field); }
	        },
	        {
	            text: "关闭冻结该列", disabled: function (e, field) { return dg.datagrid("getColumnFields", false).contains(field); },
	            handler: function (e, field) { dg.datagrid("unfreezeColumn", field); }
	        }
	    ],
	    enableHeaderClickMenu: true,
	    enableHeaderContextMenu: true,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
		onDblClickRow:function(rowIndex, rowData){
			detail();
		}
	});

	//弹窗增加
	self_.add = function() {
		dlg=$("#dlg").dialog({   
		    title: '新增用户',    
		    width: 420,    
		    height: 410,    
		    href:'${ctx}/system/user/create',
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					$("#mainform").submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}

	//弹窗修改
	self_.upd = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		dlg=$("#dlg").dialog({   
		    title: '修改用户',    
		    width: 400,    
		    height: 360,   
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'修改',iconCls:'icon-edit',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}

	//查看
	self_.detail = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		dlg=$("#dlg").dialog({
		    title: '查看用户',    
		    width: 380,    
		    height: 380,    
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	
	//删除
	self_.del = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:"${ctx}/system/user/delete/"+row.id,
					success: function(data){
						successTip(data,dg);
					}
				});
			} 
		});
	}
	
	//用户角色弹窗
	self_.userForRole = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({   
		    title: '用户角色管理',    
		    width: 580,    
		    height: 350,  
		    href:'${ctx}/system/user/'+row.id+'/userRole',
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveUserRole();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}
	//用户机构弹窗
	self_.userForOrg = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		dlg=$("#dlg").dialog({
		    title: '用户机构管理',    
		    width: 400,    
		    height: 350,
		    href:'${ctx}/system/user/'+row.id+'/userOrg',
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'保存',iconCls:'icon-save',
				handler:function(){
					saveUserOrg();
					dlg.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					dlg.panel('close');
				}
			}]
		});
	}

	//初始化用户密码
	self_.initPwd = function(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.messager.confirm('提示', '确定初始化该用户密码吗?', function(data){
			if (data){
				$.ajax({
					type:'get',
					url:"${ctx}/system/user/initPwd/"+row.id,
					success: function(data){
						successTip(data,dg);
					}
				});
			} 
		});
	}

	//创建查询对象并查询
	self_.cx = function(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj);
	}
	
	function init(){
		//绑定表单按钮事件-----------------
		$("#user_select").click(self_.cx);
		$("#user_add").click(self_.add);
		$("#user_del").click(self_.del);
		$("#user_upd").click(self_.upd);
		$("#user_init").click(self_.initPwd);
		$("#user_role").click(self_.userForRole);
		$("#user_org").click(self_.userForOrg);
		//---------------------------------
	}
	
	init();
};


(function($) {})(jQuery);


</script>
</body>
</html>