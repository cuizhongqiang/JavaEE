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
		<th width="20%">商品大类码</th>
		<td>
		${goodsManage.goodsCode }
		</td>
		<th width="20%">商品大类名称</th>
		<td>
		${goodsManage.goodsName }
		</td>
	</tr>
	<tr>
		<th>财务商品码</th>
		<td>
		${goodsManage.financialCode }
		</td>
		<th>统计商品码</th>
		<td>
		${goodsManage.statisticalCode }
		</td>
	</tr>
	<tr>
		<th>统计单位</th>
		<td>
		${goodsManage.statisticalUnit }
		</td>
		<th>增值税率</th>
		<td>
		${goodsManage.vat }
		</td>
	</tr>
	<tr>
		<th>退税率</th>
		<td>
		${goodsManage.taxRebate }
		</td>
		<th>出口关税率</th>
		<td>
		${goodsManage.exportTariffs }
		</td>
	</tr>
	<tr>
		<th>出口销项税率</th>
		<td>
		${goodsManage.outputTax }
		</td>
		<th>普通进口关税率</th>
		<td>
		${goodsManage.ordinaryImportTariffs }
		</td>
	</tr>
	<tr>
		<th>最惠国进口关税率</th>
		<td>
		${goodsManage.mostFavoredNationImportTariffs }
		</td>
		<th>消费税率</th>
		<td>
		${goodsManage.consumptionTax }
		</td>
	</tr>
	<tr>
		<th>特别关税</th>
		<td>
		${goodsManage.specialTariffs }
		</td>
		<th>终止年月</th>
		<td>
		<fmt:formatDate value="${goodsManage.endTime }" />
		</td>
	</tr>
	<tr>
		<th>属性一</th>
		<td>
		${goodsManage.attrOne }
		</td>
		<th>属性二</th>
		<td>
		${goodsManage.attrTwo }
		</td>
	</tr>
	<tr>
		<th>登记日期</th>
		<td>
		<fmt:formatDate value="${goodsManage.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th>登记人</th>
		<td>${goodsManage.createrName }</td>
	</tr>
</table>
<script type="text/javascript">
</script>
</body>
</html>