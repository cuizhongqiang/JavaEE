<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<form id="mainform"  action="${ctx}/financial/serial/${action}" method="post">
<table width="98%" class="tableClass" >
	<tr>
		<th  width="25%">流水号</th>
		<td>
			<input type="hidden" name="id" value="${id }" />
			<input id="serialNumber" name="serialNumber" type="text" value="${serial.serialNumber }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>水单抬头</th>
		<td><input id="customerId" name="customerId" type="text" value="${serial.customerId }"/></td>
	</tr>
	<tr>
		<th>银行</th>
		<td><input id="bank" name="bank" type="text" value="${serial.bank }"/></td>
		<th>银行账号</th>
		<td><input id="bankNO" name="bankNO" type="text" value="${serial.bankNO }" readonly="true" class="easyui-validatebox" data-options="prompt:'选择银行后自动加载'"/></td>
	</tr>
	<tr>
		<th>资金类别</th>
		<td><input id="fundCategory" name="fundCategory" type="text" value="${serial.fundCategory }" class="easyui-validatebox" /></td>
		<th>金额</th>
		<td><input name="money" type="text" value="${serial.money }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',required:true"/></td>
	</tr> 
	<tr>
		<th width="25%">日期</th>
		<td>
			<input type="text" id="serialDate" name="serialDate" value="<fmt:formatDate value="${serial.serialDate }" />"
				class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
		</td>
		<th>银承到期日期</th>
		<td><input name="bankDeadline" type="text"  value="<fmt:formatDate value="${serial.bankDeadline }" />"
				class="easyui-my97" datefmt="yyyy-MM-dd"/></td>
	</tr>
	<tr>
		<th>资金来源</th>
		<td colspan="3"><input id="fundSource" name="fundSource" value="${serial.fundSource }" class="easyui-validatebox" /></td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty serial.createDate ? now : serial.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty serial.createrName ? sessionScope.user.name : serial.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${serial.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</form>
<script type="text/javascript">
	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				var isValid = $(this).form('validate');
				if(isValid){
					$.ajax({
			    		url : '${ctx}/financial/serial/uniqueNo/${empty id ? 0 : id}/' + $('#serialNumber').val(),
			    		type : 'get',
			    		async : false,
			    		cache : false,
			    		dataType : 'json',
			    		success : function(data) {
			    			if (data) {
				    			isValid = false;
				    			$.messager.alert('提示', '流水号重复！', 'info');
			    			}
			    		}
			    	});
				}
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
				if("update"=="${action}")
		    		successTip(data,dg);
		    	else
		    		successTip(data,dg,d);
			} 
		});
	});
	
	//水单抬头
	$('#customerId').combobox({
		required : true,
		url : '${ctx}/baseinfo/affiliates/getCompany/1',
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			$('#bankNO').val("");
			$('#bank').combobox('loadData', record.affiBankInfo);
		}
	});
	
	//银行信息初始化 用来回显
	$('#bank').combobox({
		url :'${ctx}/baseinfo/affiliates/selectBank',
		valueField : 'bankName',
		textField : 'bankName',
		prompt:'请先选择抬头信息',
		onSelect:function(record){
			$('#bankNO').val(record.bankNO);
		}
	});
	
	//资金类别
	$('#fundCategory').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dict/getDictByCode/fundCategory',
		valueField : 'name',
		textField : 'name'
	});
	
	//资金来源
	$('#fundSource').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dict/getDictByCode/fundSource',
		valueField : 'name',
		textField : 'name'
	});
	
</script>
</body>
</html>