<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/system/user/${action}" method="post">
		<table width="98%" class="tableClass">
			<tr>
				<th><font color="red">*</font>用户名</th>
				<td>
					<input type="hidden" name="id" value="${id }"/>
					<input id="loginName" name="loginName" class="easyui-validatebox" data-options="width: 150,required:true,validType:['engNum','length[6,20]']" value="${user.loginName }"/> 
				</td>
			</tr>
			<c:if test="${action != 'update'}">
			<tr>
				<th><font color="red">*</font>密码</th>
				<td><input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:true,validType:'length[6,20]'"/></td>
			</tr>
			<tr>
				<th><font color="red">*</font>确认密码</th>
				<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:true,validType:'equals[$(\'#plainPassword\').val()]'"/></td>
			</tr>
			</c:if>
			<tr>
				<th><font color="red">*</font>姓名</th>
				<td><input name="name" type="text" value="${user.name }" class="easyui-validatebox" data-options="width: 150,required:true,validType:['chinese','minLength[2]']"/></td>
			</tr>
			<tr>
				<th>部门</th>
				<td><input id="newOrganazationId" name="newOrganazationId" type="text"  data-options="width: 150,required:true" value="${user.organization.id}"/></td>
			</tr>
			<tr>
				<th>出生日期</th>
				<td><input name="birthday" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${user.birthday}"/>"/></td>
			</tr>
			<tr>
				<th>性别</th>
				<td>
				<input type="radio" id="man" name="gender" value="1"/><label for="man">男</label>
				<input type="radio" id="woman" name="gender" value="0"/><label for="woman">女</label>
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>手机可登陆</th>
				<td>
					<input name="phonePermission"  value="${user.phonePermission }" class="easyui-combobox" data-options="width: 150,required:true,valueField: 'label',textField: 'value', panelHeight:'auto',data: [{label: '0',value: '否'}, {label: '1',value: '是'}]" />
				</td>
			</tr>
			<tr>
				<th><font color="red">*</font>Email</th>
				<td><input type="text" name="email" value="${user.email }" class="easyui-validatebox" data-options="width: 150,required:true,validType:'email'"/></td>
			</tr>
			<tr>
				<th><font color="red">*</font>电话</th>
				<td><input type="text" name="phone" value="${user.phone }" class="easyui-numberbox" validtype="telOrMobile" data-options="width: 150,required:true"/></td>
			</tr>
			<tr>
				<th>描述</th>
				<td><textarea name="description" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${user.description}</textarea></td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
var action="${action}";
//用户 新增修改区分
if(action=='create'){
	$("input[name='gender'][value=1]").attr("checked",true); 
	//用户名存在验证
	$('#loginName').validatebox({    
	    required: true,    
	    validType:{
	    	length:[6,20],
	    	remote:["${ctx}/system/user/checkLoginName","loginName"]
	    }
	});
}else if(action=='update'){
	$("input[name='loginName']").attr('readonly','readonly');
	$("input[name='loginName']").css('background','#eee')
	$("input[name='gender'][value=${user.gender}]").attr("checked",true);
}

//提交表单
$('#mainform').form({
    onSubmit: function(){
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },
    success:function(data){
    	if("update"=="${action}")
    		successTip(data,$("#dg"));
    	else
    		successTip(data,$("#dg"),$("#dlg"));
    }    
});
$(function(){
	$('#newOrganazationId').combotree({
		method:'GET',
	    url: '${ctx}/system/organization/json',
	    idField : 'id',
	    textFiled : 'orgName',
		parentField : 'pid',
	    animate:true,
	    required:true,
	    onBeforeSelect:function(node){
            if(!$(this).tree('isLeaf', node.target)){
            	return false;
            }
	    },
	    onHidePanel:function(){}
	});
});
</script>
</body>
</html>