<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/countryArea/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%"><font color="red">*</font>名称</th>
			<td>
			<input type="hidden" name="id" value="${id }" data-options="required:false"/>
			<input name="name" type="text" value="${countryArea.name }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>编码</th>
			<td>
			<input name="code" type="text" value="${countryArea.code }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<th>简码</th>
			<td>
			<input name="scode" type="text" value="${countryArea.scode }" class="easyui-validatebox"  data-options="validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<th>上级区域名称</th>
			<td>
			<input id="pid" name="pid" type="text" value="${countryArea.pid }" class="easyui-validatebox"/>
			<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-busy" onclick="$('#pid').combotree('clear')">清除</a>
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>
			${sessionScope.user.name }
			</td>
		</tr>
		<tr>
			<th>登记部门</th>
			<td>
			${sessionScope.user.organization.orgName }
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	//父级
	if (pid != null) {
		$('#pid').val(pid);
		pid = null;
	}
	
	//上级菜单
	$('#pid').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
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
	    	dg.treegrid('reload');
	    }
	});
});

</script>
</body>
</html>