<%@page import="com.cbmie.genMac.financial.entity.PayTaxes" %>
<%@page import="com.cbmie.genMac.financial.entity.PayTaxesGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<table width="98%" class="tableClass">
	<tr>
		<th>提单号</th>
		<td colspan="2">
			${payTaxes.invoiceNo }
		</td>
		<th>合同号</th>
		<td colspan="2">
			${payTaxes.contractNo }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td colspan="2">
			${payTaxes.currency }
		</td>
		<th>汇率</th>
		<td colspan="2">
			${payTaxes.rate }
		</td>
	</tr>
	<tr>
		<th>原币金额</th>
		<td colspan="2">
			<fmt:formatNumber type="number" value="${payTaxes.originalCurrency }" />
		</td>
		<th>提单金额(人民币)</th>
		<td colspan="2">
			<fmt:formatNumber type="number" value="${payTaxes.invoiceMoney }" />
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="payTaxesGoodsDetail"></table>
			<%
				PayTaxes payTaxes = (PayTaxes)request.getAttribute("payTaxes");
				List<PayTaxesGoods> ptgList = payTaxes.getPayTaxesGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String ptgJson = objectMapper.writeValueAsString(ptgList);
				request.setAttribute("ptgJson", ptgJson);
			%>
		</td>
	</tr>
	<tr>
		<th width="13%">税号</th>
		<td width="24%">
			${payTaxes.taxNo }
		</td>
		<th width="13%">关税</th>
		<td width="13%">
			<fmt:formatNumber type="number" value="${payTaxes.tax }" />
		</td>
		<td width="13%" style="border: none"></td>
		<td width="24%" style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>增值税</th>
		<td>
			<fmt:formatNumber type="number" value="${payTaxes.vat }" />
		</td>
		<td style="border: none"></td>
		<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>消费税</th>
		<td>
			<fmt:formatNumber type="number" value="${payTaxes.saleTax }" />
		</td>
		<td style="border: none"></td>
		<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>其它</th>
		<td>
			<fmt:formatNumber type="number" value="${payTaxes.otherTax }" />
		</td>
		<th>总计税金</th>
		<td>
			<fmt:formatNumber type="number" value="${payTaxes.taxTotal }" />
		</td>
	</tr>
	<tr>
		<th>收款单位</th>
		<td colspan="2">
			${payTaxes.receivingUnit }
		</td>
		<th>支付方式</th>
		<td colspan="2">
			${payTaxes.payModel }
		</td>
	</tr>
	<tr>
		<th>交税银行</th>
		<td colspan="2">
			${payTaxes.bank }
		</td>
		<th>交税账号</th>
		<td colspan="2">
			${payTaxes.account }
		</td>
	</tr>
	<tr>
		<th>是否委托货代</th>
		<td colspan="2">
			${payTaxes.delegaFreight eq 1 ? '是' : '否' }
		</td>
		<th>申请日期</th>
		<td colspan="2">
			<fmt:formatDate value="${payTaxes.applyDate }" />
		</td>
	</tr>
</table>
<form id="mainform" action="${ctx}/financial/payTaxes/registration" method="post">
<fieldset class="fieldsetClass">
<legend>登记信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>交税日期</th>
		<td>
			<input type="hidden" name="id" value="${payTaxes.id }"/>
			<input type="text" name="payDate" value="<fmt:formatDate value="${payTaxes.payDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
</table>
</fieldset>
</form>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${payTaxes.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${payTaxes.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${payTaxes.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTip(data, dg);
	    }
	});
	
	$('#payTaxesGoodsDetail').datagrid({
		data : JSON.parse('${ptgJson}'),
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
			{field:'goodsCategory',title:'商品名称',width:270},
			{field:'specification',title:'规格型号',width:250},
			{field:'amount',title:'数量',width:200},
			{field:'unit',title:'单位',width:200}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>
</body>
</html>