<%@page import="com.cbmie.genMac.agent.entity.AgentImport" %>
<%@page import="com.cbmie.genMac.agent.entity.AgentImportGoods" %>
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
<c:forEach items="${agentImport.agentImportSupplement }" var="agentImportSupplement">
<table width="98%" class="tableClass">
	<tr>
		<th width="10%">合同名称</th>
		<td colspan="3">
			${agentImportSupplement.contractName }
		</td>
	</tr>
	<tr>
		<th width="10%">签订主体名称</th>
		<td width="40%">
			${agentImportSupplement.principalName }
		</td>
		<th width="10%">协议编号</th>
		<td>
			${agentImportSupplement.agreementNo }
		</td>
	</tr>
	<tr>
		<th width="10%">合同内容</th>
		<td colspan="3">
			${agentImportSupplement.content }
		</td>
	</tr>
</table>
</c:forEach>
<table width="98%" class="tableClass">
	<tr>
		<td colspan="3" height="24px" align="center" width="35%">
			我司单位：<font style="font-weight: bold;">中建材通用机械有限公司</font>
		</td>
		<td colspan="3" align="center" width="35%">
			客户名称：<font style="font-weight: bold;">${my:getAffiliatesById(agentImport.customerId).customerName }</font>
		</td>
		<td colspan="3" align="center" width="30%">
			合同编号：<font style="font-weight: bold;">${agentImport.contractNo }</font>
		</td>
	</tr>
	<tr>
		<td rowspan="2" width="10%">基本信息</td>
		<th width="10%">商品类别</th>
		<td colspan="5">
			<span>${agentImport.goodsType }</span>
			<span style="padding-left: 20px;">备注：${agentImport.goodsTypeRemark }</span>
		</td>
		<th width="10%">业务员</th>
		<td>
			${my:getUserByLoginName(agentImport.salesman).name }
		</td>
	</tr>
	<tr>
		<th>本单交易规模</th>
		<td colspan="7">
			${agentImport.currency }<fmt:formatNumber type="number" value="${agentImport.originalCurrency }" />，折<font style="font-weight: bold;">RMB<fmt:formatNumber type="number" value="${agentImport.rmb }" /></font>；
		</td>
	</tr>
	<tr>
		<td rowspan="2">客户信息</td>
		<td colspan="8" align="center" height="24px">
			${agentImport.customerType eq 0 ? '新开发客户' : '长单老客户' }
		</td>
	</tr>
	<c:choose>
		<c:when test="${agentImport.customerType eq 0 }">
			<tr>
				<th width="10%">注册时间</th>
				<td colspan="3">
					<fmt:formatDate value="${agentImport.registerDate }" />
				</td>
				<th width="10%">注册资本</th>
				<td colspan="3">
					<fmt:formatNumber type="number" value="${agentImport.registerCapital }" />
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr>
				<th width="10%">授信额度</th>
				<td colspan="2">
					<fmt:formatNumber type="number" value="${agentImport.lineCredit }" />
					<c:if test="${agentImport.lineCredit > 50000000 }"><span style="color: red;">授信额度超过5千万，请谨慎审批！</span></c:if>
				</td>
				<th width="10%">目前敞口</th>
				<td colspan="2">
					<fmt:formatNumber type="number" value="${agentImport.currentOpen }" />
				</td>
				<th width="10%">已超期金额</th>
				<td>
					<fmt:formatNumber type="number" value="${agentImport.hasBeyond }" />
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	<tr>
		<td rowspan="10">合同信息及关键条款</td>
		<td colspan="8" align="center" height="24px" style="font-weight: bold;">外合同或进口合同信息</td>
	</tr>
	<tr>
		<th width="10%">合同名称</th>
		<td width="15%">
			${agentImport.foreignContractName }
		</td>
		<th width="10%">编号</th>
		<td width="10%">
			${agentImport.foreignContractNo }
		</td>
		<th width="10%">版本来源</th>
		<td width="10%">
			${agentImport.foreignVersionSource eq 0 ? '外商版本' : '我司版本' }
		</td>
		<th width="10%">外商名称</th>
		<td width="15%">
			${my:getAffiliatesById(agentImport.foreignId).customerName }
		</td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<table id="agentImportGoodsTB"></table>
			<%
				AgentImport agentImport = (AgentImport)request.getAttribute("agentImport");
				List<AgentImportGoods> agentImportGoods = agentImport.getAgentImportGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String agentImportGoodsJson = objectMapper.writeValueAsString(agentImportGoods);
				request.setAttribute("agentImportGoodsJson", agentImportGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			<span>${agentImport.paymentMethod }</span>
			<span style="padding-left: 20px;">备注：${agentImport.paymentMethodRemark }</span>
		</t>
		<th>价格条款</th>
		<td colspan="3">
			${agentImport.priceTerms }
		</td>
	</tr>
	<tr>
		<th>装期</th>
		<td colspan="3">
			<fmt:formatDate value="${agentImport.shipmentDate }" />
		</td>
		<th>争议管辖</th>
		<td colspan="3">
			${agentImport.dispute }
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center" height="24px" style="font-weight: bold;">内合同或代理进口合同信息</td>
	</tr>
	<tr>
		<th>合同名称</th>
		<td colspan="3">
			${agentImport.inlandContractName }
		</td>
		<th>编号</th>
		<td colspan="3">
			${agentImport.contractNo }
		</td>
	</tr>
	<tr>
		<th>版本来源</th>
		<td colspan="3">
			${agentImport.inlandVersionSource eq 0 ? '我司版本' : '客户版本' }
		</td>
		<th>项目要求</th>
		<td colspan="3">
			<span>${agentImport.projectClaim }</span>
			<span style="padding-left: 20px;">备注：${agentImport.projectClaimRemark }</span>
		</td>
	</tr>
	<tr>
		<th>代理费</th>
		<td colspan="3">
			${agentImport.agencyFee }
			<span style="padding-left: 20px;">备注：${agentImport.agencyFeeRemark }</span>
		</td>
		<th>保证金</th>
		<td colspan="3">
			${agentImport.margin }
		</td>
	</tr>
	<tr>
		<th>担保措施</th>
		<td colspan="2">
			${agentImport.assurance eq 0 ? '有' : '无'}
			<span style="padding-left: 20px;">备注：${agentImport.assuranceRemark }</span>
		</td>
		<th>融资租赁</th>
		<td colspan="2">
			${agentImport.financeLease eq 0 ? '有' : '无'}
			<span style="padding-left: 20px;">备注：${agentImport.financeLeaseRemark }</span>
		</td>
		<th>诉讼管辖</th>
		<td>
			${agentImport.litigation }
		</td>
	</tr>
	<tr>
		<td rowspan="2">物流信息</td>
		<td colspan="8" height="24px">
			${agentImport.freight }
			<span style="padding-left: 20px;">备注：${agentImport.freightRemark }</span>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="7">
			${agentImport.logisticsRemark }
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${agentImport.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${agentImport.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${agentImport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#agentImportGoodsTB').datagrid({
		data : JSON.parse('${agentImportGoodsJson}'),
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[
			{field:'goodsCategory',title:'商品名称',width:200},
			{field:'specification',title:'规格型号',width:100},
			{field:'amount',title:'数量',width:100},
			{field:'unit',title:'单位',width:100},
			{field:'price',title:'单价',width:100}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>
</body>
</html>