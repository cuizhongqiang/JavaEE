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
	       	<shiro:hasPermission name="sys:dict:add">
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">新增</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWf();">配置工作流</a>
	       		<span class="toolbar-item dialog-tool-separator"></span>
	       	</shiro:hasPermission>
	       	<shiro:hasPermission name="sys:dict:delete">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" data-options="disabled:false" onclick="deployModel()">部署</a>
	            <span class="toolbar-item dialog-tool-separator"></span>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="sys:dict:update">
	            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportModel()">导出</a>
	        </shiro:hasPermission>
        </div> 
</div>
<table id="dg"></table>
<div id="dlg"></div> 
<script type="text/javascript">  
var dg;
$(function(){
	dg=$('#dg').datagrid({    
	method: "get",
    url:'${ctx}/model/list', 
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
		{field:'key',title:'KEY',sortable:true,width:20},
		{field:'name',title:'名称',sortable:true,width:20},
        {field:'version',title:'版本',sortable:true,width:20},    
        {field:'createTime',title:'创建时间',sortable:true,width:20,
        	formatter: function(value,row,index){
        		return formatterDate(value);
			}
        },
        {field:'lastUpdateTime',title:'更新时间',sortable:true,width:20,
        	formatter: function(value,row,index){
        		return formatterDate(value);
			}
        },
        {field:'metaInfo',title:'元数据',sortable:true,width:70}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#tb',
    remoteSort:false
});
});

//弹窗增加
function add() {
	d=$("#dlg").dialog({   
	    title: '新增模型',    
	    width: 380,    
	    height: 220,    
	    href:'${ctx}/model/create',
	    maximizable:true,
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
  
function editWf(){  
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)) return;
    window.open("${ctx}/service/editor?id="+row.id);  
}  
function exportModel(){  
    var row = dg.datagrid('getSelected'); 
    if(rowIsNull(row)) return;
    window.open("${ctx}/model/export?modelId="+row.id);  
}  
  
function deployModel(){  
    var row = dg.datagrid('getSelected');
    if(rowIsNull(row)) return;
	
    parent.$.messager.confirm('提示', '您确定要部署选定模型的新流程吗？', function(data){
    	if (data){
    		parent.$.messager.progress({  
	            title : '提示',  
	            text : '流程部署中，请稍后....'  
	        });
		    $.post('${ctx}/model/deploy', {modelId:row.id}, function(j) {  
		        if (j.success) {  
		            dg.datagrid('load');  
		        }
		        dg.datagrid('uncheckAll');  
		        parent.$.messager.progress('close');
		        $.messager.show({  
		            title : '提示',  
		            msg : j.msg, 
		            position: "bottomRight"
		        });  
		    }, 'json');
    	}
    });
}
  
</script>  
</body>  
</html>  