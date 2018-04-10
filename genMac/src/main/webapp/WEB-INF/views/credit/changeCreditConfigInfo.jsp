<%@page import="com.cbmie.genMac.credit.entity.OpenCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditGoods" %>
<%@page import="com.cbmie.genMac.credit.entity.ChangeCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.ChangeCreditGoods" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditHistory" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditHistoryGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<table class="tableClass" width="98%">
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
		<th height="48px" rowspan="2" width="15%">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					本次LC应收
				</c:when>
				<c:otherwise>
					本次TT应收
				</c:otherwise>
			</c:choose>
		</th>
		<td height="24px" width="20%">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${openCredit.receivable / openCredit.rmb * 100 }" />%
		</td>
		<th rowspan="2" width="15%">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					本次LC实收
				</c:when>
				<c:otherwise>
					本次TT实收
				</c:otherwise>
			</c:choose>
		</th>
		<td height="24px" width="20%">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${openCredit.receipts / openCredit.rmb * 100 }" />%
		</td>
		<th rowspan="2" width="15%">
			<c:choose>
				<c:when test="${openCredit.lcType != 3 }">
					信用证金额占合同比例
				</c:when>
				<c:otherwise>
					本次TT金额占合同比例
				</c:otherwise>
			</c:choose>
		</th>
		<td rowspan="2" width="15%">
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
			<table id="${detail }"></table>
			<%
				Object obj = request.getAttribute("openCredit");
				ObjectMapper objectMapper = new ObjectMapper();
				List childList = new ArrayList();
				if (obj instanceof OpenCredit) {
					OpenCredit oc = (OpenCredit)obj;
					childList = oc.getOpenCreditGoods();
				} else if (obj instanceof ChangeCredit) {
					ChangeCredit cc = (ChangeCredit)obj;
					childList = cc.getChangeCreditGoods();
				} else if (obj instanceof OpenCreditHistory) {
					OpenCreditHistory och = (OpenCreditHistory)obj;
					childList = och.getOpenCreditHistoryGoods();
				}
				String childJson = objectMapper.writeValueAsString(childList);
				request.setAttribute("childJson", childJson);
			%>
		</td>
	</tr>
	<tr>
		<td colspan="6" height="48px">
			备注<br/>
			${openCredit.remark }
		</td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#${detail }').datagrid({
		data : JSON.parse('${childJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[
			{field:'goodsCategory',title:'商品名称',width:50},
			{field:'specification',title:'规格型号',width:40},
			{field:'amount',title:'数量',width:20},
			{field:'unit',title:'单位',width:20}
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