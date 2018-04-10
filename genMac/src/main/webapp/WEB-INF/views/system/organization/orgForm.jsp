<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/organization/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="20%"><font color="red">*</font>机构名称</th>
			<td>
			<input type="hidden" name="id" value="${id}" data-options="required:false"/>
			<input name="orgName" type="text" value="${organization.orgName }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<th>上级机构</th>
			<td>
				<input id="pid" name="pid" type="text" value="${organization.pid }" class="easyui-validatebox" data-options="validType:['length[0,50]']" />
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>所在区域</th>
			<td>
				<input id="areaId" name="areaId" type="text" value="${organization.areaId }" data-options="required:true"/>
			</td>
		</tr>
		<tr>
			<th><font color="red">*</font>机构代码</th>
			<td>
			<input name="orgCode" type="text" value="${organization.orgCode }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<th>机构分类</th>
			<td>
			<input name="orgType" type="text" value="${organization.orgType }" class="easyui-combobox"  data-options="required:false,valueField: 'label',textField: 'value', panelHeight:'auto',data: [{label: '0',value: '公司'}, {label: '1',value: '部门'}]" />
			</td>
		</tr>
		<tr>
			<th>机构层级</th>
			<td>
			<input name="orgLevel" type="text" value="${organization.orgLevel }" class="easyui-validatebox"  data-options="required:false,validType:['length[0,12]']" />
			</td>
		</tr>
		<tr>
			<th>排序码</th>
			<td>
			<input name="orgSort" type="text" value="${organization.orgSort }" class="easyui-validatebox"  data-options="required:false,validType:['length[0,12]']" />
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">
$(function(){
	//上级菜单
	$('#pid').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/system/organization/json',
	    idField : 'id',
	    textFiled : 'orgName',
		parentField : 'pid',
	    animate:true,
	    onHidePanel:function(){}
	});  
	//区域菜单
	$('#areaId').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true,
	    onHidePanel:function(){}
	});
	$('#mainform').form({    
	    onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	if(successTip(data,dg,d))
	    		dg.treegrid('reload');
	    }    
	}); 
});

</script>
</body>
</html>