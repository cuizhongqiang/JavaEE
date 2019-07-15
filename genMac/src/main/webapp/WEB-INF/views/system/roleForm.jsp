<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/role/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%"><font color="red">*</font>角色名</th>
			<td>
			<input type="hidden" name="id" value="${id }" />
			<input id="name" name="name" type="text" value="${role.name }" class="easyui-validatebox"  data-options="width: 150,required:true"/>
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>角色编码</th>
			<td><input id="roleCode" name="roleCode" type="text" value="${role.roleCode }" class="easyui-validatebox"  data-options="width: 150,required:true" validType="length[0,30]"/></td>
		</tr>
		<tr>
			<th><font color="red">*</font>数据范围</th>
			<td><input id="dataRange" name="dataRange" type="text" value="${role.dataRange }" class="easyui-combobox" 
			 data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',data: [{label: '0',value: '本公司'}, {label: '3',value: '本部门'}, {label: '5',value: '本人'}]"/>
			 </td>
		</tr>
		<tr>
			<th>排序</th>
			<td><input id="sort" name="sort" type="text" value="${role.sort}" class="easyui-numberbox" data-options="width: 150" /></td>
		</tr>
		<tr>
			<th>描述</th>
			<td><textarea name="description" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${role.description}</textarea></td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
var action="${action}";
//角色 新增修改区分
if(action=='create'){
	//角色存在验证
	$('#roleCode').validatebox({    
	    required: true,
	    validType:{
	    	length:[2,20],
	    	remote:["${ctx}/system/role/checkRoleCode","roleCode"]
	    }
	});
} else if(action=='update'){
	$('#roleCode').attr("readonly",true);
	$("#roleCode").css("background","#eee");
}

$(function(){
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
});

</script>
</body>
</html>