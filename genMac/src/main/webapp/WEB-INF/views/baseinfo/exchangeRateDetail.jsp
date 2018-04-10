<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/exchangerate/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="35%">币种</th>
			<td>
			${exchangeRate.currency }
			</td>
		</tr>
		<tr>
			<th>当前汇率时间</th>
			<td>${exchangeRate.showTime }</td>
		</tr>
		<tr>
			<th>对人民币汇率</th>
			<td>${exchangeRate.exchangeRateSelf }</td>
		</tr>
		<tr>
			<th>对美元汇率</th>
			<td>${exchangeRate.exchangeRateUS }</td>
		</tr>
		<tr>
			<th>对人民币汇率买入价</th>
			<td>${exchangeRate.buyingRateSelf }</td>
		</tr>
		<tr>
			<th>对美元汇率买入价</th>
			<td>${exchangeRate.buyingRateUS }</td>
		</tr>
		<tr>
			<th>对人民币汇率卖出价</th>
			<td>${exchangeRate.sellingRateSelf }</td>
		</tr>
		<tr>
			<th>对美元汇率卖出价</th>
			<td>${exchangeRate.sellingRateUS }</td>
		</tr>
		<tr>
			<th>登记日期</th>
			<td>
			<fmt:formatDate value="${exchangeRate.createDate }" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>${exchangeRate.createrName }</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">

</script>
</body>
</html>