<%@page import="com.cbmie.genMac.financial.entity.ImportBilling" %>
<%@page import="com.cbmie.genMac.financial.entity.ImportBillingGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/financial/importBilling/${action}" method="post">
<input type="hidden" name="id" value="${importBilling.id }"/>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<c:if test="${action eq 'create' }">
<a href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toConfig()">配置进口合同</a>
</c:if>
<!--移动端截取标志-->
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<td width="5%" rowspan="3" style="font-weight: bold;">自营</td>
		<th>购物单位名称</th>
		<td><input type="text" id="unitName" name="unitName" value="${importBilling.unitName }" class="easyui-validatebox" data-options="required:true"/></td>
		<th>地址</th>
		<td><input type="text" id="address" name="address" value="${importBilling.address }" class="easyui-validatebox"/></td>
		<th>电话</th>
		<td><input type="text" id="phone" name="phone" value="${importBilling.phone }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>税务登记号</th>
		<td><input type="text" id="taxNo" name="taxNo" value="${importBilling.taxNo }" class="easyui-validatebox"/></td>
		<th>开户行</th>
		<td><input type="text" id="bankName" name="bankName" value="${importBilling.bankName }" class="easyui-validatebox"/></td>
		<th>账号</th>
		<td><input type="text" id="bankNo" name="bankNo" value="${importBilling.bankNo }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<div id="importBillingGoodsToolbar" style="padding:5px;height:auto">
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
				<span class="toolbar-item dialog-tool-separator"></span>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">变化条数</a>
			</div>
			<table id="importBillingGoodsTB" data-options="onClickRow: onClickRow"></table>
			<%
				ImportBilling importBilling = (ImportBilling)request.getAttribute("importBilling");
				List<ImportBillingGoods> importBillingGoods = importBilling.getImportBillingGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String importBillingGoodsJson = objectMapper.writeValueAsString(importBillingGoods);
				request.setAttribute("importBillingGoodsJson", importBillingGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<td width="5%" rowspan="11" style="font-weight: bold;">结算</td>
		<th colspan="6" align="center" style="font-weight: bold;font-size: 14px;background: none;">
			进口合同号:&nbsp;&nbsp;<span id="contractNoSpan">${importBilling.contractNo }</span>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="hidden" id="contractNo" name="contractNo" value="${importBilling.contractNo }" />
			现有库存:&nbsp;&nbsp;<span><input type="text" id="nowInventory" name="nowInventory" value="${importBilling.nowInventory }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
			剩余库存:&nbsp;&nbsp;<span><input type="text" id="remainInventory" name="remainInventory" value="${importBilling.remainInventory }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
			币种：&nbsp;&nbsp;<span><input type="text" id="currency" name="currency" value="${empty importBilling.currency ? '人民币' : importBilling.currency }" class="easyui-validatebox" data-options="required:true"/></span>&nbsp;&nbsp;&nbsp;&nbsp;
			业务员分成比例：&nbsp;&nbsp;<span><input type="text" id="proportion" name="proportion" value="${importBilling.proportion }" class="easyui-validatebox" data-options="required:true"/></span>
		</th>
	</tr>
	<tr>
		<th>支付货款</th>
		<td colspan="3">
			<input type="text" id="payMoney" name="payMoney" value="${importBilling.payMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#payMoneyResult').text(newValue - $('#payMoneySys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="payMoneySys" name="payMoneySys" value="${importBilling.payMoneySys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#payMoneyResult').text($('#payMoney').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="payMoneyResult">
				<fmt:formatNumber type="number" value="${importBilling.payMoney - importBilling.payMoneySys }"/>
			</span>
		</td>
		<th>到账货款</th>
		<td>
			<input type="text" id="accountMoney" name="accountMoney" value="${importBilling.accountMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#balance').numberbox('setValue', Number(newValue) - Number($('#payTotal').numberbox('getValue')));
				}
			"/>
		</td>
	</tr>
	<tr>
		<th>银行手续费</th>
		<td colspan="5">
			<input type="text" id="bankPoundage" name="bankPoundage" value="${importBilling.bankPoundage }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#bankPoundageResult').text(newValue - $('#bankPoundageSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="bankPoundageSys" name="bankPoundageSys" value="${importBilling.bankPoundageSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#bankPoundageResult').text($('#bankPoundage').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="bankPoundageResult">
				<fmt:formatNumber type="number" value="${importBilling.bankPoundage - importBilling.bankPoundageSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>关税(监管费)</th>
		<td colspan="5">
			<input type="text" id="custom" name="custom" value="${importBilling.custom }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#customResult').text(newValue - $('#customSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="customSys" name="customSys" value="${importBilling.customSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#customResult').text($('#custom').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="customResult">
				<fmt:formatNumber type="number" value="${importBilling.custom - importBilling.customSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>增值税</th>
		<td colspan="5">
			<input type="text" id="vat" name="vat" value="${importBilling.vat }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#vatResult').text(newValue - $('#vatSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="vatSys" name="vatSys" value="${importBilling.vatSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#vatResult').text($('#vat').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="vatResult">
				<fmt:formatNumber type="number" value="${importBilling.vat - importBilling.vatSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>消费税</th>
		<td colspan="5">
			<input type="text" id="saleTax" name="saleTax" value="${importBilling.saleTax }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#saleTaxResult').text(newValue - $('#saleTaxSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="saleTaxSys" name="saleTaxSys" value="${importBilling.saleTaxSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#saleTaxResult').text($('#saleTax').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="saleTaxResult">
				<fmt:formatNumber type="number" value="${importBilling.saleTax - importBilling.saleTaxSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>报关</th>
		<td colspan="5">
			<input type="text" id="customsDeclaration" name="customsDeclaration" value="${importBilling.customsDeclaration }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#customsDeclarationResult').text(newValue - $('#customsDeclarationSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="customsDeclarationSys" name="customsDeclarationSys" value="${importBilling.customsDeclarationSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#customsDeclarationResult').text($('#customsDeclaration').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="customsDeclarationResult">
				<fmt:formatNumber type="number" value="${importBilling.customsDeclaration - importBilling.customsDeclarationSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>运杂</th>
		<td colspan="5">
			<input type="text" id="shipMix" name="shipMix" value="${importBilling.shipMix }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#shipMixResult').text(newValue - $('#shipMixSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="shipMixSys" name="shipMixSys" value="${importBilling.shipMixSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#shipMixResult').text($('#shipMix').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="shipMixResult">
				<fmt:formatNumber type="number" value="${importBilling.shipMix - importBilling.shipMixSys }"/>
			</span>
		</td>
	</tr>
	<tr>
		<th>保险</th>
		<td colspan="3">
			<input type="text" id="insurance" name="insurance" value="${importBilling.insurance }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#others').numberbox('getValue'))
														);
					$('#insuranceResult').text(newValue - $('#insuranceSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="insuranceSys" name="insuranceSys" value="${importBilling.insuranceSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#othersSys').numberbox('getValue'))
														);
					$('#insuranceResult').text($('#insurance').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="insuranceResult">
				<fmt:formatNumber type="number" value="${importBilling.insurance - importBilling.insuranceSys }"/>
			</span>
		</td>
		<th>代理费(利润)</th>
		<td><input type="text" id="agencyFee" name="agencyFee" value="${importBilling.agencyFee }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>其他(免表、录入等)</th>
		<td colspan="3">
			<input type="text" id="others" name="others" value="${importBilling.others }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotal').numberbox('setValue', Number(newValue) + 
														Number($('#payMoney').numberbox('getValue')) + 
														Number($('#bankPoundage').numberbox('getValue')) + 
														Number($('#custom').numberbox('getValue')) + 
														Number($('#vat').numberbox('getValue')) + 
														Number($('#saleTax').numberbox('getValue')) + 
														Number($('#customsDeclaration').numberbox('getValue')) + 
														Number($('#shipMix').numberbox('getValue')) + 
														Number($('#insurance').numberbox('getValue'))
														);
					$('#othersResult').text(newValue - $('#othersSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="othersSys" name="othersSys" value="${importBilling.othersSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalSys').numberbox('setValue', Number(newValue) + 
														Number($('#payMoneySys').numberbox('getValue')) + 
														Number($('#bankPoundageSys').numberbox('getValue')) + 
														Number($('#customSys').numberbox('getValue')) + 
														Number($('#vatSys').numberbox('getValue')) + 
														Number($('#saleTaxSys').numberbox('getValue')) + 
														Number($('#customsDeclarationSys').numberbox('getValue')) + 
														Number($('#shipMixSys').numberbox('getValue')) + 
														Number($('#insuranceSys').numberbox('getValue'))
														);
					$('#othersResult').text($('#others').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="othersResult">
				<fmt:formatNumber type="number" value="${importBilling.others - importBilling.othersSys }"/>
			</span>
		</td>
		<th>利息</th>
		<td><input type="text" id="interest" name="interest" value="${importBilling.interest }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	</tr>
	<tr>
		<th>支付合计</th>
		<td colspan="3">
			<input type="text" id="payTotal" name="payTotal" value="${importBilling.payTotal }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#balance').numberbox('setValue', Number($('#accountMoney').numberbox('getValue')) - Number(newValue));
					$('#payTotalResult').text(newValue - $('#payTotalSys').numberbox('getValue'));
				}
			"/>
			-
			<input type="text" id="payTotalSys" name="payTotalSys" value="${importBilling.payTotalSys }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#payTotalResult').text($('#payTotal').numberbox('getValue') - newValue);
				}
			"/>
			=
			<span id="payTotalResult">
				<fmt:formatNumber type="number" value="${importBilling.payTotal - importBilling.payTotalSys }"/>
			</span>
		</td>
		<th>本合同余额</th>
		<td><input type="text" id="balance" name="balance" value="${importBilling.balance }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	</tr>
	<tr>
		<td width="5%" style="font-weight: bold;">代理</td>
		<th>客户名称</th>
		<td colspan="5"><input type="text" id="agencyName" name="agencyName" value="${importBilling.agencyName }" class="easyui-combobox"/></td>
	</tr>
	<tr>
		<td width="5%" style="font-weight: bold;">备注</td>
		<td colspan="6">
			<textarea name="remark" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${importBilling.remark }</textarea>
		</td>
	</tr>
</table>
<!--移动端截取标志-->
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty importBilling.createDate ? now : importBilling.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty importBilling.createrName ? sessionScope.user.name : importBilling.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${importBilling.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="importBillingGoodsJson" id="importBillingGoodsJson"/>
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
	
	$('#agencyName').combobox({
		width:410,
		url : '${ctx}/baseinfo/affiliates/getCompany/1',
		valueField : 'customerName',
		textField : 'customerName'
	});

});

initImportBillingGoods();//初始化
function initImportBillingGoods(value){
	$('#importBillingGoodsTB').datagrid({
		data : value != null ? value : JSON.parse('${importBillingGoodsJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		showFooter:true,
		columns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'货物(应税劳务)名称',width:20,
				editor:{
					type:'combobox',
					options:{
						valueField : 'goodsName',
						textField : 'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json/isLeaf',
						required:true
					}
				}
			},
			{field:'specification',title:'规格型号',width:20,editor:{type:'validatebox'}},
			{field:'unit',title:'单位',width:20,
	        	editor:{
					type:'combobox',
					options:{
						panelHeight:'auto',
						valueField:'name',
						textField:'name',
						method:'get',
						url:'${ctx}/system/dict/getDictByCode/DW',
						required:true
					}
				}
	        },
			{field:'amount',title:'数量',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			groupSeparator:',',
	        			required:true,
	        			onChange:function(newValue, oldValue){
		        			var salesAmountObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'salesAmount'});
		        			var priceObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'price'});
		        			if(salesAmountObj == null || priceObj == null){
		        				return;
		        			}
		        			salesAmountObj.target.numberbox('setValue', newValue * priceObj.target.numberbox('getValue'));
	        			}
	        		}
	        	}
	        },
			{field:'price',title:'单价',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			groupSeparator:',',
	        			required:true,
	        			onChange:function(newValue, oldValue){
		        			var salesAmountObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'salesAmount'});
		        			var amountObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'amount'});
		        			if(salesAmountObj == null || amountObj == null){
			        			return;
			        		}
		        			salesAmountObj.target.numberbox('setValue', newValue * amountObj.target.numberbox('getValue'));
	        			}
	        		}
	        	}
	        },
			{field:'salesAmount',title:'销售金额',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			groupSeparator:',',
	        			required:true,
	        			onChange:function(newValue, oldValue){
			        		var taxMainObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'taxMain'});
			        		var rateMainObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'rateMain'});
			        		if(taxMainObj == null || rateMainObj == null){
				        		return;
				        	}
			        		taxMainObj.target.numberbox('setValue', newValue * rateMainObj.target.numberbox('getValue') * 0.01);
	        			}
	        		}
	        	}
	        },
			{field:'rateMain',title:'税率',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			suffix:'%',
	        			onChange:function(newValue, oldValue){
		        			var taxMainObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'taxMain'});
		        			var salesAmountObj = $('#importBillingGoodsTB').datagrid('getEditor', {index:editIndex,field:'salesAmount'});
		        			if(taxMainObj == null || salesAmountObj == null){
			        			return;
			        		}
		        			taxMainObj.target.numberbox('setValue', newValue * salesAmountObj.target.numberbox('getValue') * 0.01);
	        			}
	        		}
	        	}
	        },
			{field:'taxMain',title:'税额',width:20,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#importBillingGoodsToolbar',
	    onEndEdit:function(rowIndex, rowData, changes){
	    	changeChildTB();
	    },
	    onLoadSuccess:function(data){
	    	var salesAmountTotal = 0;//销售金额合计
			for(var i = 0; i < data.rows.length; i++){
				salesAmountTotal += Number(data.rows[i].salesAmount);
			}
	    	// 更新页脚行
	    	$(this).datagrid('reloadFooter', [{'salesAmount':salesAmountTotal}]);
	    }
	});
}

var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#importBillingGoodsTB').datagrid('validateRow', editIndex)){
		$('#importBillingGoodsTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if(editIndex != index){
		if (endEditing()){
			$('#importBillingGoodsTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#importBillingGoodsTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if(endEditing()){
		$('#importBillingGoodsTB').datagrid('appendRow',{});
		editIndex = $('#importBillingGoodsTB').datagrid('getRows').length-1;
		$('#importBillingGoodsTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if(editIndex == undefined){return}
	$('#importBillingGoodsTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	changeChildTB();
}
function accept(){
	if(endEditing()){
		var rows = $('#importBillingGoodsTB').datagrid('getRows');
		$('#importBillingGoodsTB').datagrid('acceptChanges');
		$('#importBillingGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#importBillingGoodsTB').datagrid('selectRow', i).datagrid('beginEdit', i);
				editIndex = i;
				return false;
			}else{
				array.push(goods);
			}
		}
		return true;
	}
}
function reject(){
	$('#importBillingGoodsTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#importBillingGoodsTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(){
	var rows = $('#importBillingGoodsTB').datagrid('getData').rows;
	var salesAmountTotal = 0;//销售金额合计
	for(var i = 0; i < rows.length; i++){
		salesAmountTotal += Number(rows[i].salesAmount);
	}
	// 更新页脚行
	$('#importBillingGoodsTB').datagrid('reloadFooter', [{'salesAmount':salesAmountTotal}]);
}

//配置进口合同
function toConfig(){
	configDlg=$("#configDlg").dialog({
		title: '配置进口合同',
		width: 1000,
		height: 280,
	    href:'${ctx}/financial/importBilling/config',
	    modal:true,
	    buttons:[{
			text:'保存',
			handler:function(){
				var row = config_dg.datagrid('getSelected');
				if(rowIsNull(row)) return;
				parent.$.messager.progress({
			    	title : '提示',
			    	text : '数据处理中，请稍后....'
			    });
				$.ajax({
					url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + row.customerId,
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						$("#unitName").val(data[0].customerName);//购货单位名称(委托方)
						$("#address").val(data[0].address);//地址
						$("#phone").val(data[0].phoneContact);//电话
					}
				});
				$('#payMoneySys').numberbox('setValue', row.rmb);//支付货款
				$('#agencyFee').val(row.agencyFee);//代理费(利润)
				$.ajax({
					url : '${ctx}/financial/acceptance/filter?filter_EQS_contractNo=' + row.contractNo + '&filter_EQS_state=生效',
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						var interest = 0;
						var bankPoundage = 0;
						$.each(data, function(index, element){
							if(element.type == 1){
								interest += Number(element.interest);
							}
							bankPoundage += Number(element.actualPoundage);
						});
						$('#interest').numberbox('setValue', interest);
						$('#bankPoundageSys').numberbox('setValue', bankPoundage);
					}
				});//押汇利息、银行手续费
				$('#taxNo').combobox({
					method : 'get',
					url : '${ctx}/financial/payTaxes/findTaxesByContract/' + row.contractNo,
					required : true,
					valueField : 'taxNo',
					textField : 'taxNo',
					onSelect:function(record){
						$('#customSys').numberbox('setValue', record.tax);//关税（监管费）
						$('#vatSys').numberbox('setValue', record.vat);//增值税
						$('#saleTaxSys').numberbox('setValue', record.saleTax);//消费税
						var importBillingGoodsJson = [];
						var salesAmountTotal = 0;//销售金额合计
						var taxMainTotal = 0;//税额合计
						for(var i = 0; i < row.agentImportGoods.length; i++){
							var price = (row.agentImportGoods[i].price * record.rate).toFixed(2);//人民币单价
							var salesAmount = (price * row.agentImportGoods[i].amount).toFixed(2);//销售金额
							importBillingGoodsJson.push({
								'goodsCategory':row.agentImportGoods[i].goodsCategory,
								'specification':row.agentImportGoods[i].specification,
								'unit':row.agentImportGoods[i].unit,
								'amount':row.agentImportGoods[i].amount,
								'price':price,
								'salesAmount':salesAmount
							});
							salesAmountTotal += Number(salesAmount);
						}
						var footer = [{'salesAmount':salesAmountTotal}];//放入footer页脚
						var datax = { "rows": importBillingGoodsJson, "footer": footer };
						initImportBillingGoods(datax);//初始化进口合同下商品
						editIndex = undefined;
					}
				});//税务登记号
				$.ajax({
					url : '${ctx}/financial/expense/filter?filter_EQS_contractNo=' + row.contractNo + '&filter_EQS_state=生效',
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						var customsDeclaration = 0;
						var shipMix = 0;
						var insurance = 0;
						$.each(data, function(i, elements){
							$.each(elements.expenseDetail, function(j, element){
								if(element != null){
									if(element.paymentChildType == "报关"){
										customsDeclaration += Number(element.money);
									}else if(element.paymentChildType == "运杂"){
										shipMix += Number(element.money);
									}else if(element.paymentChildType == "保险"){
										insurance += Number(element.money);
									}
								}
							});
						});
						$('#customsDeclarationSys').numberbox('setValue', customsDeclaration);
						$('#shipMixSys').numberbox('setValue', shipMix);
						$('#insuranceSys').numberbox('setValue', insurance);
					}
				});//报关、运杂、保险
				$('#contractNoSpan').text(row.contractNo);
				$('#contractNo').val(row.contractNo);//进口合同号
				$.ajax({
					url : '${ctx}/financial/serial/sum/货款/' + row.contractNo,
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						$('#accountMoney').numberbox('setValue', data);//到账货款
					}
				});
				parent.$.messager.progress('close');
				configDlg.panel('close');
			}
		},{
			text:'关闭',
			handler:function(){
				configDlg.panel('close');
			}
		}]
	});
}
</script>
</body>
</html>