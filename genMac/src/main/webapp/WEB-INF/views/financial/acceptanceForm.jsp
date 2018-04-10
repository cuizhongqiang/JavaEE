<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/financial/acceptance/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="10%">提单号</th>
		<td width="40%">
			<input type="hidden" name="id" value="${acceptance.id }"/>
			<input type="hidden" name="type" value="${type eq 1 || acceptance.type == 1 ? 1 : 0 }"/>
			<input type="text" id="invoiceNo" name="invoiceNo" value="${acceptance.invoiceNo }"/>
		</td>
		<th width="10%">合同号</th>
		<td width="40%">
			<input type="text" id="contractNo" name="contractNo" value="${acceptance.contractNo }" class="easyui-validatebox" data-options="prompt:'选择提单号加载'" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			<input type="text" id="currency" name="currency" value="${acceptance.currency }" class="easyui-validatebox" data-options="prompt:'选择提单号加载'" readonly="true" style="background: #eee"/>
		</td>
		<th>原币金额</th>
		<td>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${acceptance.originalCurrency }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:','" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<c:if test="${type eq 1 || acceptance.type == 1 }">
	<tr>
		<th>押汇天数</th>
		<td>
			<input type="text" id="days" name="days" value="${acceptance.days }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',
			onChange:function(newValue, oldValue){
				$('#interest').numberbox('setValue', newValue * 0.01 * $('#documentaryBillsRate').numberbox('getValue') * $('#originalCurrency').numberbox('getValue') / 360);
			}" readonly="true" style="background: #eee"/>
		</td>
		<th>押汇到期日</th>
		<td>
			<input type="text" id="documentaryBillsDate" name="documentaryBillsDate" value="<fmt:formatDate value="${acceptance.documentaryBillsDate }" />" class="easyui-my97" data-options="prompt:'选择提单号加载',disabled:true" datefmt="yyyy-MM-dd"/>
		</td>
	</tr>
	</c:if>
	<tr>
		<th>${type eq 1 || acceptance.type == 1 ? '押汇' : '付汇' }金额</th>
		<td>
			<input type="text" id="acceptanceMoney" name="acceptanceMoney" value="${acceptance.acceptanceMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
		<th>备注</th>
		<td>
			<input type="text" id="remark" name="remark" value="${acceptance.remark }" class="easyui-validatebox" data-options=""/>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>回填信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="10%">实际付汇日期</th>
		<td width="40%">
			<input type="text" id="actualDate" name="actualDate" value="<fmt:formatDate value="${acceptance.actualDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
		<th width="10%">银行</th>
		<td width="40%">
			<input type="text" id="bank" name="bank" value="${acceptance.bank }" class="easyui-combobox" data-options="prompt:'选择提单号加载'"/>
		</td>
	</tr>
	<tr>
		<th>实际汇率</th>
		<td>
			<input type="text" id="actualRate" name="actualRate" value="${acceptance.actualRate }" class="easyui-numberbox" data-options="min:0,precision:4,
			onChange:function(newValue, oldValue){
				$('#rmb').numberbox('setValue', newValue * $('#originalCurrency').numberbox('getValue'));
			}"/>
		</td>
		<th>客户汇率</th>
		<td>
			<input type="text" id="customerRate" name="customerRate" value="${acceptance.customerRate }" class="easyui-numberbox" data-options="min:0,precision:4"/>
		</td>
	</tr>
	<tr>
		<th>折算成人民币</th>
		<td colspan="3">
			<input type="text" id="rmb" name="rmb" value="${acceptance.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th>实际手续费</th>
		<td>
			<input type="text" id="actualPoundage" name="actualPoundage" value="${acceptance.actualPoundage }" class="easyui-numberbox" data-options="min:0,precision:2"/>
		</td>
		<th>客户手续费</th>
		<td>
			<input type="text" id="customerPoundage" name="customerPoundage" value="${acceptance.customerPoundage }" class="easyui-numberbox" data-options="min:0,precision:2"/>
		</td>
	</tr>
	<c:if test="${type eq 1 || acceptance.type == 1 }">
	<tr>
		<th>押汇利率</th>
		<td>
			<input type="text" id="documentaryBillsRate" name="documentaryBillsRate" value="${acceptance.documentaryBillsRate }" class="easyui-numberbox" data-options="min:0,max:100,precision:4,suffix:'%',
			onChange:function(newValue, oldValue){
				$('#interest').numberbox('setValue', newValue * 0.01 * $('#acceptanceMoney').numberbox('getValue') * $('#days').numberbox('getValue') / 360);
			}"/>
		</td>
		<th>利息<!-- 融资金额X利率X计息天数/360 --></th>
		<td>
			<input type="text" id="interest" name="interest" value="${acceptance.interest }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
		</td>
	</tr>
	</c:if>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty acceptance.createDate ? now : acceptance.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty acceptance.createrName ? sessionScope.user.name : acceptance.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${acceptance.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
			parent.$.messager.progress({  
		        title : '提示',  
		        text : '数据处理中，请稍后....'  
		    });
	    	var isValid = $(this).form('validate');
	    	if(!isValid){parent.$.messager.progress('close');};
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
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
	
	$('#invoiceNo').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/logistics/invoiceReg/filter?${type eq 1 ? "filter_EQI_ifDocumentaryBills=1" : "" }',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		onSelect:function(record){
			$('#contractNo').val(record.contractNo);//合同号
			$('#currency').val(record.currency);//币种
			$('#originalCurrency').numberbox('setValue', record.originalCurrency);//原币金额
			$('#days').numberbox('setValue', record.days);//押汇天数
			$('#documentaryBillsDate').my97('setValue', record.documentaryBillsDate);//押汇到期日
			$('#acceptanceMoney').numberbox('setValue', record.documentaryBillsMoney);//付汇/押汇金额
			$('#bank').combobox({
				method:'GET',
				url:'${ctx}/credit/openCredit/filter?filter_EQS_contractNo=' + record.contractNo + '&filter_EQI_lcType=${type eq 1 || acceptance.type == 1 ? 1 : 0 }',
				valueField : 'bank',
				textField : 'bank'
			});
		}
	});
	
	if('${action}' == 'update'){
		$('#bank').combobox({
			method:'GET',
			url:'${ctx}/credit/openCredit/filter?filter_EQS_contractNo=${acceptance.contractNo }&filter_EQI_lcType=${type eq 1 || acceptance.type == 1 ? 1 : 0 }',
			valueField : 'bank',
			textField : 'bank'
		});
	}
});
</script>
</body>
</html>