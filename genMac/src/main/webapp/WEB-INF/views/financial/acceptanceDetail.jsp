<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="10%">提单号</th>
		<td width="40%">
			${acceptance.invoiceNo }
		</td>
		<th width="10%">合同号</th>
		<td width="40%">
			${acceptance.contractNo }
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			${acceptance.currency }
		</td>
		<th>原币金额</th>
		<td>
			<fmt:formatNumber type="number" value="${acceptance.originalCurrency }" />
		</td>
	</tr>
	<c:if test="${acceptance.type == 1 }">
	<tr>
		<th>押汇天数</th>
		<td>
			${acceptance.days }
		</td>
		<th>押汇到期日</th>
		<td>
			<fmt:formatDate value="${acceptance.documentaryBillsDate }" />
		</td>
	</tr>
	</c:if>
	<tr>
		<th>${acceptance.type == 1 ? '押汇' : '付汇' }金额</th>
		<td>
			<fmt:formatNumber type="number" value="${acceptance.acceptanceMoney }" />
		</td>
		<th>备注</th>
		<td>
			${acceptance.remark }
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
			<fmt:formatDate value="${acceptance.actualDate }" />
		</td>
		<th width="10%">银行</th>
		<td width="40%">
			${acceptance.bank }
		</td>
	</tr>
	<tr>
		<th>实际汇率</th>
		<td>
			${acceptance.actualRate }
		</td>
		<th>客户汇率</th>
		<td>
			${acceptance.customerRate }
		</td>
	</tr>
	<tr>
		<th>折算成人民币</th>
		<td colspan="3">
			<fmt:formatNumber type="number" value="${acceptance.rmb }" />
		</td>
	</tr>
	<tr>
		<th>实际手续费</th>
		<td>
			<fmt:formatNumber type="number" value="${acceptance.actualPoundage }" />
		</td>
		<th>客户手续费</th>
		<td>
			<fmt:formatNumber type="number" value="${acceptance.customerPoundage }" />
		</td>
	</tr>
	<c:if test="${acceptance.type == 1 }">
	<tr>
		<th>押汇利率</th>
		<td>
			${acceptance.documentaryBillsRate }${empty acceptance.documentaryBillsRate ? '' : '%'}
		</td>
		<th>利息<!-- 融资金额X利率X计息天数/360 --></th>
		<td>
			<fmt:formatNumber type="number" value="${acceptance.interest }" />
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
		<fmt:formatDate value="${acceptance.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${acceptance.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${acceptance.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<script type="text/javascript">
$(function(){
});
</script>
</body>
</html>