<%-- <%@page import="com.cbmie.common.utils.StringUtils"%> --%>
<%-- <%@page import="com.cbmie.genMac.baseinfo.entity.Port"%> --%>
<%-- <%@page import="java.text.SimpleDateFormat"%> --%>
<%-- <%@page import="java.text.DateFormat"%> --%>
<%-- <%@page import="java.util.Date"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%-- <%@page import="com.cbmie.system.entity.User"%> --%>
<%-- <%@page import="com.cbmie.system.utils.UserUtil"%> --%>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/port/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th>编码</th>
			<td><input name="portId" type="text" value="${port.portId }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th width="25%">港口名称</th>
			<td>
			<input type="hidden" name="id" value="${port.id }" />
			<input name="portName" type="text" value="${port.portName }" class="easyui-validatebox"  data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th>简码</th>
			<td><input name="portCode" type="text" value="${port.portCode }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th>英文名称</th>
			<td><input name="portEnName" type="text" value="${port.portEnName }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th>区域码</th>
			<td><input name="areaCode" type="text" value="${port.areaCode }" class="easyui-validatebox" data-options="required:true"/></td>
		</tr>
		<tr>
			<th>状态</th>
			<td>
				<input name="status"  value="${port.status }" class="easyui-combobox" data-options="required:true,valueField: 'label',textField: 'value', panelHeight:'auto',data: [{label: '0',value: '正常',selected:'selected'}, {label: '1',value: '停用'}]" />
			</td>
		</tr>
		<tr>
			<th>备注</th>
			<td><input name="comments" type="text" value="${port.comments }" class="easyui-validatebox" /></td>
		</tr>
		<tr>
			<th>登记日期</th>
			<td>
			<jsp:useBean id="now" class="java.util.Date" scope="page"/>
			<fmt:formatDate value="${empty port.createDate ? now : port.createDate}" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>${empty port.createrName ? sessionScope.user.name : port.createrName }</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
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