<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/exchangerate/${action}" method="post">
	<table width="98%" class="tableClass">
		<tr>
			<th width="35%">币种</th>
			<td>
			<input type="hidden" name="id" value="${exchangeRate.id }" />
			<input name="currency" type="text" value="${exchangeRate.currency }" class="easyui-validatebox"  data-options="required:true" />
			</td>
		</tr>
		<tr>
			<th>当前汇率时间</th>
			<td><input name="showTime" type="text" value="${exchangeRate.showTime }" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" data-options="required:true" style="width:170px"/></td>
		</tr>
		<tr>
			<th>对人民币汇率</th>
			<td><input name="exchangeRateSelf" type="text" value="${exchangeRate.exchangeRateSelf }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>对美元汇率</th>
			<td><input name="exchangeRateUS" type="text" value="${exchangeRate.exchangeRateUS }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>对人民币汇率买入价</th>
			<td><input name="buyingRateSelf" type="text" value="${exchangeRate.buyingRateSelf }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>对美元汇率买入价</th>
			<td><input name="buyingRateUS" type="text" value="${exchangeRate.buyingRateUS }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>对人民币汇率卖出价</th>
			<td><input name="sellingRateSelf" type="text" value="${exchangeRate.sellingRateSelf }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>对美元汇率卖出价</th>
			<td><input name="sellingRateUS" type="text" value="${exchangeRate.sellingRateUS }" class="easyui-numberbox" data-options="required:true,precision:4"/></td>
		</tr>
		<tr>
			<th>登记日期</th>
			<td>
			<jsp:useBean id="now" class="java.util.Date" scope="page"/>
			<fmt:formatDate value="${empty exchangeRate.createDate ? now : exchangeRate.createDate}" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>${empty exchangeRate.createrName ? sessionScope.user.name : exchangeRate.createrName }</td>
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