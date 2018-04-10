<%@page import="com.cbmie.genMac.financial.entity.ExpenseDetail"%>
<%@page import="com.cbmie.genMac.financial.entity.Expense"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
<form id="mainform"  action="${ctx}/financial/expense/${action}" method="post">
<fieldset class="fieldsetClass" >
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass" >
	<tr>
		<th>合同号</th>
		<td>
			<input id="contractNo" name="contractNo" type="text" value="${expense.contractNo }"/>
		</td>
		<th>客户名称</th>
		<td>
			<input type="text" id="customerName" class="easyui-validatebox" data-options="prompt:'选择合同号加载'" readonly="true" style="background: #eee"/>
			<input type="hidden" id="customerId" name="customerId" value="${expense.customerId }"/>
		</td>
		<th>业务员</th>
		<td>
			<input type="text" id="salesman" name="salesman" value="${expense.salesman }" class="easyui-validatebox" data-options="prompt:'选择合同号加载'" readonly="true" style="background: #eee"/>
		</td>
		<input type="hidden" name="id" value="${expense.id }" />
	</tr>
	<tr>
		<th width="10%">币种</th>
		<td>
			<input name="currency" type="text" value="${empty expense.currency ? '人民币' : expense.currency }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th width="10%">申请日期</th>
		<td>
			<input type="text" id="applyDate" name="applyDate" value="<fmt:formatDate value='${expense.applyDate }' />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
		</td>
		<th width="10%">财务实付日期</th>
		<td>
			<input type="text" id="payDate" name="payDate" value="<fmt:formatDate value='${expense.payDate }' />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true" />
		</td>
		
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass" >
<legend>金额信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>应付总金额</th>
		<td>
			<input id="sumMoney" name="sumMoney" type="text" value="${expense.sumMoney }" class="easyui-numberbox"
			data-options="min:0,precision:2,groupSeparator:',',prompt: '填写付款明细后自动加载'" />
		</td>
		<th>实付金额</th>
		<td>
			<input id="payCurrency" name="payCurrency" type="text" value="${expense.payCurrency }" class="easyui-numberbox" 
			data-options="min:0,precision:2,groupSeparator:',',required:true"/>
		</td>
		<th>未付金额</th>
		<td>
			<input id="oweCurrency" name="oweCurrency" type="text" value="${expense.oweCurrency }" class="easyui-numberbox" 
			data-options="min:0,precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<div id="childToolbar" style="padding:5px;height:auto">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">变化条数</a>
			</div>
			<table id="childTB" data-options="onClickRow: onClickRow"></table>
			<%
				Expense expense = (Expense)request.getAttribute("expense");
				List<ExpenseDetail> gcList = expense.getExpenseDetail();
				ObjectMapper objectMapper = new ObjectMapper();
				String gcJson = objectMapper.writeValueAsString(gcList);
				request.setAttribute("gcJson", gcJson);
			%>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass" >
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty expense.createDate ? now : expense.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty expense.createrName ? sessionScope.user.name : expense.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${expense.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="expenseDetailJson" id="expenseDetailJson"/>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<script type="text/javascript">
$(function() {
	$('#mainform').form({
		onSubmit : function() {
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
			var isValid = $(this).form('validate');
			if(!isValid){parent.$.messager.progress('close');};
			return isValid; // 返回false终止表单提交
		} ,
		success : function(data) {
			if("update"=="${action}"){
	    		successTip("success",dg);
	    		if($("#apply").val() == "true"){
	    			d.panel('close');
	    		}
	    	}else{
	    		successTip("success",dg,d);
	    	}
			parent.$.messager.progress('close');
			//提交流程
	    	if($("#apply").val() == "true"){
	    		apply(data);
	    	}
		}
	});
	
	if('${action}' == 'update'){
		$.ajax({
			url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=${expense.customerId}',
			type : 'get',
			async : false,
			cache : false,
			dataType : 'json',
			success : function(data) {
				$('#customerName').val(data[0].customerName);
			}
		});
	}
	
	$('#contractNo').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/agent/agentImport/filter?filter_EQS_state=生效',
		valueField : 'contractNo',
		textField : 'contractNo',
		onSelect:function(record){
			$.ajax({
				url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + record.customerId,
				type : 'get',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(data) {
					$('#customerName').val(data[0].customerName);
					$('#customerId').val(data[0].id);
				}
			});
			$('#salesman').val(record.salesman);
		}
	});
	
	var childTB;
	childTB=$('#childTB').datagrid({
		data : JSON.parse('${gcJson}'),
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'documentNo',title:'单据号',width:15,editor:{type:'validatebox'}},
			{field:'paymentChildType',title:'付款子类型',width:15,
				editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'name',
						textField:'name',
						method:'get',
						url:'${ctx}/system/dict/getDictByCode/paymentChildType',
						required:true
					}
				}
			},
			{field:'settlement',title:'结算客户',width:20,editor:{type:'validatebox',options:{required:true}}},
			{field:'receiptStatus',title:'收款情况',width:15,
				editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'name',
						textField:'name',
						data:[{name:'代垫'},{name:'全款已收'}],
						required:true
					}
				}
			},
			{field:'payModel',title:'支付方式',width:10,
				editor:{
					type:'combobox',
					options:{
						panelHeight : 'auto',
						required : true,
						url : '${ctx}/system/dict/getDictByCode/PAYMODEL',
						valueField : 'name',
						textField : 'name',
						onSelect:function(record){
							var bankNameObj = $('#childTB').datagrid('getEditor', {index:editIndex,field:'bankName'});
							var bankNoObj = $('#childTB').datagrid('getEditor', {index:editIndex,field:'bankNo'});
							if(record.name == "电汇"){
								bankNameObj.target.validatebox({required:true});
								bankNoObj.target.validatebox({required:true});
							}else{
								bankNameObj.target.validatebox({required:false});
								bankNoObj.target.validatebox({required:false});
							}
						}
					}
				}
			},
			{field:'bankName',title:'银行名称',width:15,editor:{type:'validatebox'}},
			{field:'bankNo',title:'银行账号',width:15,editor:{type:'validatebox'}},
			{field:'money',title:'金额',width:15,
				editor:{
					type:'numberbox',
					options:{
						min:0,
						precision:2,
						groupSeparator:',' ,
						required:true
					}
				}
			},
			{field:'remark',title:'备注',width:20,editor:{type:'validatebox'}}
		]],
		enableHeaderClickMenu: false,
		enableHeaderContextMenu: false,
		enableRowContextMenu: false,
		toolbar:'#childToolbar'
	});
});
 
var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow',{});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#expenseDetailJson').val(JSON.stringify(rows));
	}
	calculate();
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function calculate(){
	var rows =  $('#childTB').datagrid('getRows');
	var payCurrency = 0;
	var oweCurrency = 0;
	var sumMoney = 0;
	for(var i = 0; i < rows.length; i++){
		if(rows[i].receiptStatus == '全款已收'){
			payCurrency += Number(rows[i].money);
		}else if(rows[i].receiptStatus == '代垫'){
			oweCurrency += Number(rows[i].money);
		}
		sumMoney += Number(rows[i].money);
	}
	$('#payCurrency').numberbox('setValue', payCurrency);
	$('#oweCurrency').numberbox('setValue', oweCurrency);
	$('#sumMoney').numberbox('setValue', sumMoney);
}
</script>
</body>
</html>