<%@page import="com.cbmie.genMac.credit.entity.OpenCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditGoods" %>
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
<form id="mainform" action="${ctx}/credit/openCredit/registration" method="post">
<fieldset class="fieldsetClass">
<legend>申请信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>外合同号</th>
		<td>
			${openCredit.foreignContractNo }
		</td>
		<th>供应外商</th>
		<td colspan="3" id="foreignName">
			${openCredit.foreignId }
		</td>
	</tr>
	<tr>
		<th>内合同号</th>
		<td>
			${openCredit.contractNo }
		</td>
		<th>国内客户</th>
		<td colspan="3" id="customerName">
			${openCredit.customerId }
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
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>登记信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>开证日</th>
		<td>
			<input type="hidden" name="id" value="${openCredit.id }"/>
			<input type="text" name="openDate" value="<fmt:formatDate value="${openCredit.openDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
		<c:if test="${openCredit.lcType != 3 }">
		<th>信用证号</th>
		<td>
			<input type="text" name="lcNo" value="${openCredit.lcNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		</c:if>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${openCredit.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${openCredit.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${openCredit.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
</form>
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
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=${openCredit.customerId }',
		type : 'get',
		async : false,
		cache : false,
		dataType : 'json',
		success : function(data) {
			$('#customerName').text(data[0].customerName);
		}
	});
	
	$.ajax({
		url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=${openCredit.foreignId }',
		type : 'get',
		async : false,
		cache : false,
		dataType : 'json',
		success : function(data) {
			$('#foreignName').text(data[0].customerName);
		}
	});
});
</script>
</body>
</html>