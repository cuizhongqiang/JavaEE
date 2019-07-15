<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugins/jquery/jquery.form.js"></script>
</head>
<body>
	<div style="padding: 5px">
	<form id="mainform" action="${ctx }/system/user/updatePwd" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th>原密码</th>
			<td>
			<input type="hidden" name="id" value="${user.id }"/>
			<input id="oldPassword" name="oldPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'"/>
			</td>
		</tr>
		<tr>
			<th>密码</th>
			<td>
				<input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[6,20]'"/>
			</td>
		</tr>
		<tr>
			<th>保存密码</th>
			<td>
				<input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" validType="equalTo['#plainPassword']" data-options="width: 150,required:'required'"/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
<script>
$(function(){
	$("#oldPassword").focus();
});

$.extend($.fn.validatebox.defaults.rules, {
	/*必须和某个字段相等*/
	equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '两次输入的密码不一致' }
});

//提交表单
$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },    
    success:function(data){   
    	if(data=='success'){
			parent.$.messager.show({ title : "提示",msg: "修改密码成功！", position: "bottomRight" });
			parent.d.panel('close');
		}
    	if(data=='fail'){
    		parent.$.messager.show({ title : "提示",msg: "原密码输入错误！", position: "bottomRight" });
    	}
    }    
}); 
</script>
</body>
</html>