<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
    <div>
    	<shiro:hasPermission name="sys:perm:add">
    	<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="add();">新增</a>
    	<span class="toolbar-item dialog-tool-separator"></span>
    	</shiro:hasPermission>
        <shiro:hasPermission name="sys:perm:delete">
        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="del()">删除</a>
        <span class="toolbar-item dialog-tool-separator"></span>
        </shiro:hasPermission>
        <shiro:hasPermission name="sys:perm:update">
        <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="upd()">修改</a>
        </shiro:hasPermission>
    </div>
</div>
<table id="dg"></table>
 
<div id="dlg"></div> 
<div id="icon_dlg"></div>  

<script type="text/javascript">
var dg;
var d;
var permissionDg;
var parentPermId;
$(function(){   
	dg=$('#dg').treegrid({  
		method: "get",
	    url:'${ctx}/system/permission/menu/json', 
	    fit : true,
		fitColumns : true,
		border : false,
		idField : 'id',
		treeField:'name',
		parentField : 'pid',
		iconCls: 'icon',
		animate:true, 
		rownumbers:true,
		singleSelect:true,
		striped:true,
	    columns:[[    
	        {field:'id',title:'id',hidden:true,width:100},    
	        {field:'name',title:'名称',width:100},
	        {field:'url',title:'资源路径',width:100},
	        {field:'effective',title:'是否有效',width:100,
	        	formatter: function(value,row,index){
					var val;
					if(value!='' && value!=null){
						if(value=='1'){
							val='是'
						}else if(value=='0'){
							val='否'
						}
						return val;
					}else{
						return '';
					}
				}	},
	        {field:'sort',title:'排序'},
	        {field:'description',title:'描述',width:100}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
	    dataPlain: true
	});
});

//弹窗增加
function add() {
	//父级权限
	var row = dg.treegrid('getSelected');
	if(row){
		parentPermId=row.id;
	}
	
	d=$('#dlg').dialog({    
	    title: '新增菜单',    
	    width: 450,    
	    height: 310,    
	    closed: false,    
	    cache: false,
	    maximizable:false,
	    resizable:true,
	    href:'${ctx}/system/permission/menu/create',
	    modal: true,
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
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			$.ajax({
				type:'get',
				url:"${ctx}/system/permission/delete/"+row.id,
				success: function(data){
					if(successTip(data,dg))
			    		dg.treegrid('reload');
				}
			});
			//dg.datagrid('reload'); //grid移除一行,不需要再刷新
		} 
	});

}

//修改
function upd(){
	var row = dg.treegrid('getSelected');
	if(rowIsNull(row)) return;
	//父级权限
	parentPermId=row.pid;
	d=$("#dlg").dialog({   
	    title: '修改菜单',    
	    width: 450,    
	    height: 310,    
	    href:'${ctx}/system/permission/menu/update/'+row.id,
	    maximizable:false,
	    modal:true,
	    buttons:[{
			text:'修改',iconCls:'icon-edit',
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

var nowIcon;
var icon_dlg;
</script>
</body>
</html>