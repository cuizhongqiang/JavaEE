/**
 * 
 */
(function($) {
	var d;
	
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
	        {field:'loginName',title:'帐号',sortable:true,width:100},    
	        {field:'name',title:'昵称',sortable:true,width:100},
	        {field:'organization',title:'部门',width:100,
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
	        {field:'gender',title:'性别',sortable:true,
	        	formatter : function(value, row, index) {
	       			return value==1?'男':'女';
	        	}
	        },
	        {field:'phonePermission',title:'手机可登陆',sortable:true,
	        	formatter : function(value, row, index) {
	       			return value==1?'是':'否';
	        	}
	        },
	        {field:'email',title:'email',sortable:true,width:100},
	        {field:'phone',title:'电话',sortable:true,width:100},
	        {field:'loginCount',title:'登录次数',sortable:true},
	        {field:'previousVisit',title:'上一次登录',sortable:true}
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
	function add() {
		d=$("#dlg").dialog({   
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
					url:"${ctx}/system/user/delete/"+row.id,
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
		    title: '修改用户',    
		    width: 400,    
		    height: 360,   
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'修改',
				handler:function(){
					$('#mainform').submit(); 
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	}

	//用户角色弹窗
	function userForRole(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		d=$("#dlg").dialog({   
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
					d.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	}
	//用户机构弹窗
	function userForOrg(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		$.ajaxSetup({type : 'GET'});
		d=$("#dlg").dialog({   
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
					d.panel('close');
				}
			},{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	}

	//查看
	function detail(){
		var row = dg.datagrid('getSelected');
		if(rowIsNull(row)) return;
		d=$("#dlg").dialog({   
		    title: '查看用户',    
		    width: 380,    
		    height: 380,    
		    href:'${ctx}/system/user/update/'+row.id,
		    maximizable:false,
		    modal:true,
		    buttons:[{
				text:'关闭',iconCls:'icon-cancel',
				handler:function(){
					d.panel('close');
				}
			}]
		});
	}

	//初始化用户密码
	function initPwd(){
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
	function cx(){
		var obj=$("#searchFrom").serializeObject();
		dg.datagrid('load',obj);
	}
	
})(jQuery);
