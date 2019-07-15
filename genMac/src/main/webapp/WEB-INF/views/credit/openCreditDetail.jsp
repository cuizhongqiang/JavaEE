<%@page import="com.cbmie.genMac.credit.entity.OpenCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<table id="detailTab" width="98%" class="tableClass">
	<tr>
		<td colspan="6" align="center" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					信用证审批表
				</c:when>
				<c:otherwise>
					TT审批表
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr id="agentImportTR">
		<th>外合同号</th>
		<td colspan="2">
			${openCredit.foreignContractNo }
		</td>
		<th>供应外商</th>
		<td colspan="2">
			${empty openCredit.foreignId ? "" : my:getAffiliatesById(openCredit.foreignId).customerName }
		</td>
	</tr>
	<tr id="purchaseTR">
		<th></th>
		<td colspan="2">
			${openCredit.contractNo }
		</td>
		<th></th>
		<td colspan="2">
			${my:getAffiliatesById(openCredit.customerId).customerName }
		</td>
	</tr>
	<tr>
		<th>合同原币金额</th>
		<td>
			${openCredit.currency }<fmt:formatNumber type="number" value="${openCredit.originalCurrency }" />
		</td>
		<th>折美元</th>
		<td>
			<fmt:formatNumber type="number" value="${openCredit.dollar }" />
		</td>
		<th>折人民币</th>
		<td>
			<fmt:formatNumber type="number" value="${openCredit.rmb }" />
		</td>
	</tr>
	<tr>
		<th>价格条款</th>
		<td>
			${openCredit.priceTerms }
		</td>
		<th>即期/远期</th>
		<td>
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					${openCredit.lcType eq 0 ? '即期' : '远期' }
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</td>
		<th>远期天数</th>
		<td>
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					${openCredit.days }
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th height="48px" rowspan="2">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					本次LC应收
				</c:when>
				<c:otherwise>
					本次TT应收
				</c:otherwise>
			</c:choose>
		</th>
		<td height="24px">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${openCredit.receivable / openCredit.rmb * 100 }" />%
		</td>
		<th rowspan="2">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					本次LC实收
				</c:when>
				<c:otherwise>
					本次TT实收
				</c:otherwise>
			</c:choose>
		</th>
		<td height="24px">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${openCredit.receipts / openCredit.rmb * 100 }" />%
		</td>
		<th rowspan="2">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					信用证金额占合同比例
				</c:when>
				<c:otherwise>
					本次TT金额占合同比例
				</c:otherwise>
			</c:choose>
		</th>
		<td rowspan="2">
			${openCredit.percent }%
		</td>
	</tr>
	<tr>
		<td>
			<fmt:formatNumber type="number" value="${openCredit.receivable }" />
		</td>
		<td>
			<fmt:formatNumber type="number" value="${openCredit.receipts }" />
		</td>
	</tr>
	<tr>
		<th>开证行</th>
		<td colspan="2">
			${openCredit.bank }
		</td>
		<th>
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					本次开证金额
				</c:when>
				<c:otherwise>
					本次TT金额
				</c:otherwise>
			</c:choose>
		</th>
		<td colspan="2">
			<fmt:formatNumber type="number" value="${openCredit.theMoney }" />
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="openCreditGoodsDetail"></table>
			<%
				OpenCredit openCredit = (OpenCredit)request.getAttribute("openCredit");
				List<OpenCreditGoods> ocgList = openCredit.getOpenCreditGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String ocgJson = objectMapper.writeValueAsString(ocgList);
				request.setAttribute("ocgJson", ocgJson);
			%>
		</td>
	</tr>
	<tr>
		<td colspan="6" height="48px">
			备注<br/>
			${openCredit.remark }
		</td>
	</tr>
	<c:if test="${!workflow }">
	<tr>
		<th>执行文员</th>
		<td>
		</td>
		<th>主管业务员</th>
		<td colspan="3">
		</td>
	</tr>
	<tr>
		<th>财务</th>
		<td>
		</td>
		<th>总经理</th>
		<td colspan="3">
		</td>
	</tr>
	</c:if>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td id="createDate" width="15%">
		<fmt:formatDate value="${openCredit.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td id="createrName" width="20%">${openCredit.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td id="updateDate" width="20%"><fmt:formatDate value="${openCredit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#openCreditGoodsDetail').datagrid({
		data : JSON.parse('${ocgJson}'),
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
	
	var purchaseTRArray = [];
	$("#purchaseTR th").each(function(index, dom){
		purchaseTRArray.push(dom);
	});
	if("${openCredit.contractType }" == 0){
		$('#agentImportTR').show();
		$(purchaseTRArray[0]).text("内合同号");
		$(purchaseTRArray[1]).text("国内客户");
	}else{
		$('#agentImportTR').hide();
		$(purchaseTRArray[0]).text("合同号");
		$(purchaseTRArray[1]).text("供应商");
	}
});
</script>
</body>
</html>