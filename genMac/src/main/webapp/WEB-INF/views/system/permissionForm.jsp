<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<form id="mainform" action="${ctx}/system/permission/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%"><font color="red">*</font>权限名称</th>
			<td>
				<input type="hidden" name="id" value="${permission.id }"/>
				<input type="hidden" name="type" value="O"/>
				<input id="name" name="name" type="text" value="${permission.name }" class="easyui-validatebox" data-options="width: 180,required:true,validType:'length[2,20]'"/>
			</td>
		</tr>
		<tr>
			<th>权限编码</th>
			<td>
				<input id="permCode" name="permCode" type="text" class="easyui-validatebox"  data-options="width: 180" value="${permission.permCode }" />
			</td>
		</tr>
		<tr>
			<th>访问路径</th>
			<td><input id="url" name="url" type="text" value="${permission.url }" class="easyui-validatebox"  data-options="width: 180" class="easyui-validatebox"/></td>
		</tr>
		<tr>
			<th>上级菜单</th>
			<td><input id="pid" name="pid" value="${permission.pid }"/></td>
		</tr>
		<tr>
			<th>排序</th>
			<td><input id="sort" name="sort" value="${permission.sort }"  class="easyui-validatebox"   data-options="width: 180"/></td>
		</tr>
		<tr>
			<th>描述</th>
			<td><textarea name="description" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${permission.description}</textarea></td>
		</tr>
	</table>
	</form>
<script type="text/javascript">
//父级权限
$('#pid').val(parentPermId);

//菜单类型
$('#type').combobox({  
	width:180,
	panelHeight:50
});  

//上级权限
$('#pid').combotree({
	width:180,
	method:'GET',
	url: '${ctx}/system/permission/menu/json',
    idField : 'id',
    textFiled : 'name',
	parentField : 'pid',
	iconCls: 'icon',
    animate:true
});  

$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if("update"=="${action}")
    		successTip(data,dg);
    	else
    		successTip(data,dg,d);
    }    
});   


</script>
</body>
</html>