<%@page import="com.cbmie.genMac.logistics.entity.InvoiceReg" %>
<%@page import="com.cbmie.genMac.logistics.entity.InvoiceGoods" %>
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
<form id="mainform" action="${ctx}/logistics/invoiceReg/${action}" method="post">
<c:if test="${action eq 'create' }">
<center style="margin: 10px 0px 5px 0px;font-size: 16px;">
<input type="radio" name="contractType" value="0" style="margin-top:-2px;" <c:if test="${invoiceReg.contractType eq 0 }">checked</c:if> onclick="changeContractType($(this).val())"/>代理进口
<input type="radio" name="contractType" value="1" style="margin-top:-2px;margin-left:20px;" <c:if test="${invoiceReg.contractType eq 1 }">checked</c:if> onclick="changeContractType($(this).val())"/>(自营)采购
<input type="radio" name="contractType" value="2" style="margin-top:-2px;margin-left:20px;" <c:if test="${invoiceReg.contractType eq 2 }">checked</c:if> onclick="changeContractType($(this).val())"/>(内贸)采购
<input type="radio" name="contractType" value="3" style="margin-top:-2px;margin-left:20px;" <c:if test="${invoiceReg.contractType eq 3 }">checked</c:if> onclick="changeContractType($(this).val())"/>(内贸)代理采购
</center>
</c:if>
<fieldset class="fieldsetClass">
<legend>基本信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th>提单号</th>
		<td>
			<input type="hidden" name="id" value="${invoiceReg.id }"/>
			<input type="text" id="invoiceNo" name="invoiceNo" value="${invoiceReg.invoiceNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>合同号</th>
		<td>
			<input type="text" id="contractNo" name="contractNo" value="${invoiceReg.contractNo }" class="easyui-combobox"/>
		</td>
		<th>业务员</th>
		<td>
			<input type="text" id="salesman" name="salesman" value="${invoiceReg.salesman }" class="easyui-validatebox" data-options="prompt:'选择合同号加载'" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th width="10%">供应商</th>
		<td width="24%">
			<input type="text" id="supplier" name="supplier" value="${invoiceReg.supplier }" class="easyui-validatebox" data-options="prompt:'选择合同号加载'" readonly="true" style="background: #eee"/>
		</td>
		<th width="10%">贸易方式</th>
		<td width="23%">
			<input type="text" id="tradeWay" name="tradeWay" value="${invoiceReg.tradeWay }"/>
		</td>
		<th width="10%">是否冲销预付款</th>
		<td width="23%">
			<input type="radio" name="revAdvPayment" value="1" style="margin-top:-2px" <c:if test="${invoiceReg.revAdvPayment eq 1}">checked</c:if>/>是
			<input type="radio" name="revAdvPayment" value="0" style="margin-top:-2px" <c:if test="${invoiceReg.revAdvPayment eq 0}">checked</c:if>/>否
		</td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<div id="childToolbar" style="padding:5px;height:auto">
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
			<table id="invoiceGoods" data-options="onClickRow: onClickRow"></table>
			<%
				InvoiceReg invoiceReg = (InvoiceReg)request.getAttribute("invoiceReg");
				List<InvoiceGoods> igList = invoiceReg.getInvoiceGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String igJson = objectMapper.writeValueAsString(igList);
				request.setAttribute("igJson", igJson);
			%>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>金额信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">价格条款</th>
		<td width="20%">
			<input type="text" id="pricingTerms" name="pricingTerms" value="${invoiceReg.pricingTerms }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
		<th width="10%">币种</th>
		<td width="20%">
			<input type="text" id="currency" name="currency" value="${invoiceReg.currency }" class="easyui-validatebox" readonly="true" style="background: #eee"/>
		</td>
		<th width="15%">对人民币汇率</th>
		<td width="20%">
			<input type="text" id="rmbRate" name="rmbRate" value="${invoiceReg.rmbRate }" class="easyui-numberbox"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<th>原币金额</th>
		<td>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${invoiceReg.originalCurrency }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#invoiceMoney').numberbox('setValue', newValue * $('#rmbRate').numberbox('getValue'));
				}
			" readonly="true" style="background: #eee"/>
		</td>
		<th>提单金额（人民币）</th>
		<td>
			<input type="text" id="invoiceMoney" name="invoiceMoney" value="${invoiceReg.invoiceMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th>装运港</th>
		<td colspan="2">
			<input type="text" id="transportPort" name=transportPort value="${invoiceReg.transportPort }"/>
		</td>
		<th>目的港</th>
		<td colspan="2">
			<input type="text" id="destinationPort" name="destinationPort" value="${invoiceReg.destinationPort }"/>
		</td>
	</tr>
	<tr>
		<th>到单日期</th>
		<td>
			<input type="text" name="arriveDate" value="<fmt:formatDate value="${invoiceReg.arriveDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
		<th>付汇/承兑日期</th>
		<td>
			<input type="text" id="acceptDate" name="acceptDate" value="<fmt:formatDate value="${invoiceReg.acceptDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true,
			onChange:function(newValue, oldValue){
				$('#documentaryBillsDate').my97('setValue', formatterDate('yyyy-MM-dd', Date.parse(newValue) + $('#days').numberbox('getValue') * 86400000));
			}"/>
		</td>
		<th>是否押汇</th>
		<td>
			<input type="radio" name="ifDocumentaryBills" value="1" style="margin-top:-2px" <c:if test="${invoiceReg.ifDocumentaryBills eq 1}">checked</c:if> onclick="ifdb(1);"/>是
			<input type="radio" name="ifDocumentaryBills" value="0" style="margin-top:-2px" <c:if test="${invoiceReg.ifDocumentaryBills eq 0}">checked</c:if> onclick="ifdb(0);"/>否
		</td>
	</tr>
	<tr>
		<th>押汇天数</th>
		<td>
			<input type="text" id="days" name="days" value="${invoiceReg.days }" class="easyui-numberbox" data-options="
			onChange:function(newValue, oldValue){
				$('#documentaryBillsDate').my97('setValue', formatterDate('yyyy-MM-dd', Date.parse($('#acceptDate').my97('getValue')) + newValue * 86400000));
			}"/>
		</td>
		<th>押汇到期日</th>
		<td>
			<input type="text" id="documentaryBillsDate" name="documentaryBillsDate" value="<fmt:formatDate value="${invoiceReg.documentaryBillsDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
			<input type="checkbox" style="margin-top:1px;margin-left:20px;" <c:if test="${invoiceReg.financialConfirm eq 1}">checked</c:if> onclick="financialConfirmClick(this)"/>财务确认
			<input type="hidden" id="financialConfirm" name="financialConfirm" value="${invoiceReg.financialConfirm }"/>
		</td>
		<th>押汇金额</th>
		<td>
			<input type="text" id="documentaryBillsMoney" name="documentaryBillsMoney" value="${invoiceReg.documentaryBillsMoney }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
</table>
</fieldset>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty invoiceReg.createDate ? now : invoiceReg.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty invoiceReg.createrName ? sessionScope.user.name : invoiceReg.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${invoiceReg.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="invoiceGoodsJson" id="invoiceGoodsJson"/>
</form>
<script type="text/javascript">
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if(!$('#mainform').form('validate')){
	    		$("#mainDiv").tabs("select", tabIndex);
	    	}else{
	    		tabIndex = index;
	    	}
	    }
	});
	
	$('#mainform').form({
		onSubmit: function(){
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		$.ajax({
		    		url : '${ctx}/logistics/invoiceReg/uniqueNo/${empty invoiceReg.id ? 0 : invoiceReg.id}/' + $('#invoiceNo').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '提单号重复！', 'info');
		    			}
		    		}
		    	});
	    	}
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }
	});
	
	changeContractType('${invoiceReg.contractType }');
	
	$('#rmbRate').numberbox({
		min:0,
		precision:4,
		required:true,
		onChange:function(newValue, oldValue){
			getRMB(newValue); //提单金额
			var rows = $('#invoiceGoods').datagrid('getRows'); //计算税额
	    	for(var i = 0; i < rows.length; i++){
	    		var rmb = rows[i].original * newValue * 0.01;
	    		rows[i]['tax'] = (rmb * rows[i].taxRate).toFixed(2);
	    		rows[i]['saleTax'] = (rmb * rows[i].saleTaxRate).toFixed(2);
	    		rows[i]['vat'] = (rmb * rows[i].vatRate).toFixed(2);
	    		$('#invoiceGoods').datagrid('refreshRow', i);
	    	}
		}
	});
	
	$('#tradeWay').combobox({
		panelHeight:'auto',
		required:true,
		url:'${ctx}/system/dict/getDictByCode/tradeWay',
		valueField : 'name',
		textField : 'name'
	});
	
	$('#transportPort').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/port/filter?filter_EQI_status=0',
		valueField : 'portName',
		textField : 'portName'
	});
	
	$('#destinationPort').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/port/filter?filter_EQI_status=0',
		valueField : 'portName',
		textField : 'portName'
	});
	
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/inventoryWay',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			$.each(data, function(index, value){
				$("#inventoryWay").append("<input type='radio' name='inventoryWay'" + ((value.name == '${invoiceReg.inventoryWay }' || index == 0) ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:-2px'/>" + value.name);
			});
		}
	});
	
	ifdb('${invoiceReg.ifDocumentaryBills }');
});

function changeContractType(value){
	var url;
	if(value == 0){
		url = '${ctx}/agent/agentImport/findInvoiceReg';
	}else if(value == 1){
		url = '${ctx}/selfRun/purchase/findInvoiceReg';
	}
	$('#contractNo').combobox({
		method:'GET',
		required:true,
		url:url,
		valueField : 'contractNo',
		textField : 'contractNo',
		onSelect:function(record){
			$('#salesman').val(record.salesman); //业务员
			var supplier;
			if(value == 0){
				supplier = record.foreignId;
			}else{
				supplier = record.customerId;
			}
			$.ajax({
				url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + supplier,
				type : 'get',
				async : false,
				cache : false,
				dataType : 'json',
				success : function(data) {
					$('#supplier').val(data[0].customerName);
				}
			}); //供应商
			$('#pricingTerms').val(record.priceTerms); //价格条款
			$('#currency').val(record.currency); //币种
			$('#originalCurrency').numberbox({value:record.originalCurrency}); //原币金额
			$.ajax({
				url : '${ctx}/baseinfo/exchangerate/getThisNewExchangeRate/' + record.currency,
				async : false,
				type : 'get',
				cache : false,
				dataType : 'json',
				success : function(data) {
					$('#rmbRate').numberbox('setValue', data.exchangeRateSelf);
				}
			}); //对人民币汇率(最新)
			getRMB(); //提单金额
			var goodsOld = [];
			if(value == 0){
				goodsOld = record.agentImportGoods;
			}else if(value == 1){
				goodsOld = record.selfPurchaseGoods;
			}else if(value == 2){
				goodsOld = record.domesticPurchaseGoods;
			}else if(value == 3){
				goodsOld = record.agentPurchaseGoods;
			}
			var goodsJson = [];
			for(var i = 0; i < goodsOld.length; i++){
				goodsJson.push({
    				'goodsCategory':goodsOld[i].goodsCategory,
					'specification':goodsOld[i].specification,
					'amount':goodsOld[i].amount,
					'unit':goodsOld[i].unit,
					'price':goodsOld[i].price,
					'original':(goodsOld[i].amount * goodsOld[i].price).toFixed(2)
				});
				$.ajax({
		    		url : '${ctx}/baseinfo/goodsManage/findByCode/' + goodsOld[i].goodsCode,
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			var rmb = goodsJson[i].original * 0.01 * $('#rmbRate').numberbox('getValue');
		    			goodsJson[i]['taxRate'] = data.ordinaryImportTariffs;//普通进口关税率
		    			goodsJson[i]['tax'] = (data.ordinaryImportTariffs * rmb).toFixed(2);
		    			goodsJson[i]['saleTaxRate'] = data.consumptionTax;//消费税率
		    			goodsJson[i]['saleTax'] = (data.consumptionTax * rmb).toFixed(2);
		    			goodsJson[i]['vatRate'] = data.vat;//增值税率
		    			goodsJson[i]['vat'] = (data.vat * rmb).toFixed(2);
		    		}
		    	});
			} //清空进口合同商品id，计算税额
			initInvoiceGoods(goodsJson); //初始进口合同商品
			editIndex = undefined;
		}
	});
}

function ifdb(value){
	if(value == 1){
		$('#days').numberbox({
			disabled:false,
			required:true
		});
		$('#documentaryBillsDate').combo({
			disabled:false,
			required:true
		});
		$('#documentaryBillsMoney').numberbox({
			disabled:false,
			required:true
		});
	}else{
		$('#days').numberbox({
			disabled:true,
			required:false
		});
		$('#documentaryBillsDate').combo({
			disabled:true,
			required:false
		});
		$('#documentaryBillsMoney').numberbox({
			disabled:true,
			required:false
		});
		$('#days').numberbox('clear');
		$('#documentaryBillsMoney').numberbox('clear');
		$('#documentaryBillsDate').my97('clear');
	}
}

var invoiceGoods;
initInvoiceGoods(); //初始化
function initInvoiceGoods(value){
	invoiceGoods=$('#invoiceGoods').datagrid({
		data : value != null ? value : JSON.parse('${igJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		frozenColumns:[[
			{field:'id',title:'id',hidden:true},
			{field:'goodsCategory',title:'商品名称',width:100,
				editor:{
					type:'combobox',
					options:{
						valueField : 'goodsName',
						textField : 'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json/isLeaf',
						required:true,
						onSelect:function(record){
							var taxRate = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'taxRate'});
							var saleTaxRate = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'saleTaxRate'});
							var vatRate = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'vatRate'});
							taxRate.target.numberbox('setValue', record.ordinaryImportTariffs);
							if(record.ordinaryImportTariffs == null || record.ordinaryImportTariffs == ""){
								taxRate.target.numberbox('setValue', record.mostFavoredNationImportTariffs);
							}
							saleTaxRate.target.numberbox('setValue', record.consumptionTax);
							vatRate.target.numberbox('setValue', record.vat);
						}
					}
				}
			},
			{field:'specification',title:'规格型号',width:80,editor:{type:'validatebox'}},
			{field:'frameNo',title:'车架号',width:80,editor:{type:'validatebox'}}
		]],
	    columns:[
	        [
			{"title":"合同","colspan":3},
			{field:'original',title:'原币金额',rowspan:2,align:'center',width:25,
				editor:{
					type:'numberbox',
					options:{
						precision:2,
						groupSeparator:',',
						required:true,
						onChange:function(newValue, oldValue){
							var taxObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'tax'});
							var saleTaxObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'saleTax'});
							var vatObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'vat'});
							
							if(taxObj == null || saleTaxObj == null || vatObj == null){
				        		return;
				        	}
							
							var taxRateObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'taxRate'});
							var saleTaxRateObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'saleTaxRate'});
							var vatRateObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'vatRate'});
							
							if(taxRateObj == null || saleTaxRateObj == null || vatRateObj == null){
				        		return;
				        	}
							
							var rmb = newValue * 0.01 * $('#rmbRate').numberbox('getValue');
							taxObj.target.numberbox('setValue', rmb * taxRateObj.target.numberbox('getValue'));
							saleTaxObj.target.numberbox('setValue', rmb * saleTaxRateObj.target.numberbox('getValue'));
							vatObj.target.numberbox('setValue', rmb * vatRateObj.target.numberbox('getValue'));
						}
					}
				}
			},
			{"title":"关税","colspan":2},
			{"title":"消费税","colspan":2},
			{"title":"增值税","colspan":2}
	        ],
	        [
	        {field:'amount',title:'数量',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			required:true,
	        			onChange:function(newValue, oldValue){
	        				var originalObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'original'});
	        				var priceObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'price'});
	        				if(originalObj == null || priceObj == null){
				        		return;
				        	}
	        				originalObj.target.numberbox('setValue', newValue * priceObj.target.numberbox('getValue'));
	        			}
	        		}
	        	}
	        },
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
			{field:'price',title:'单价',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			groupSeparator:',',
	        			required:true,
	        			onChange:function(newValue, oldValue){
	        				var originalObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'original'});
	        				var amountObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'amount'});
	        				if(originalObj == null || amountObj == null){
				        		return;
				        	}
	        				originalObj.target.numberbox('setValue', newValue * amountObj.target.numberbox('getValue'));
	        			}
	        		}
	        	}	
			},
			{field:'tax',title:'关税税额',width:20,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
			{field:'taxRate',title:'关税税率%',width:20,
				editor:{
					type:'numberbox',
					options:{
						suffix:'%',
						onChange:function(newValue, oldValue){
	        				var originalObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'original'});
	        				var taxObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'tax'});
	        				if(originalObj == null || taxObj == null){
				        		return;
				        	}
	        				taxObj.target.numberbox('setValue', newValue * originalObj.target.numberbox('getValue') * 0.01 * $('#rmbRate').numberbox('getValue'));
	        			}
					}
				}
			},
			{field:'saleTax',title:'消费税额',width:20,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
			{field:'saleTaxRate',title:'消费税率%',width:20,
				editor:{
					type:'numberbox',
					options:{
						suffix:'%',
						onChange:function(newValue, oldValue){
	        				var originalObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'original'});
	        				var saleTaxObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'saleTax'});
	        				if(originalObj == null || saleTaxObj == null){
				        		return;
				        	}
	        				saleTaxObj.target.numberbox('setValue', newValue * originalObj.target.numberbox('getValue') * 0.01 * $('#rmbRate').numberbox('getValue'));
	        			}
					}
				}
			},
			{field:'vat',title:'增值税额',width:20,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}},
			{field:'vatRate',title:'增值税率%',width:25,
				editor:{
					type:'numberbox',
					options:{
						suffix:'%',
						onChange:function(newValue, oldValue){
	        				var originalObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'original'});
	        				var vatObj = $('#invoiceGoods').datagrid('getEditor', {index:editIndex,field:'vat'});
	        				if(originalObj == null || vatObj == null){
				        		return;
				        	}
	        				vatObj.target.numberbox('setValue', newValue * originalObj.target.numberbox('getValue') * 0.01 * $('#rmbRate').numberbox('getValue'));
	        			}
					}
				}
			}
	    	]
	    ],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	    onEndEdit : function(index, row, changes){
	    	changeChildTB($(this).datagrid('getRows'));
	    	getRMB();
	    }
	});
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#invoiceGoods').datagrid('validateRow', editIndex)){
		$('#invoiceGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#invoiceGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#invoiceGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#invoiceGoods').datagrid('appendRow',{});
		editIndex = $('#invoiceGoods').datagrid('getRows').length-1;
		$('#invoiceGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#invoiceGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#invoiceGoods').datagrid('getRows');
		$('#invoiceGoods').datagrid('acceptChanges');
		$('#invoiceGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#invoiceGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
				editIndex = i;
				return false;
			}else{
				array.push(goods);
			}
		}
		changeChildTB(rows);
		return true;
	}
}
function reject(){
	$('#invoiceGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#invoiceGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(rows){
	var original = 0;
	for(var i = 0; i < rows.length; i++){
		original += parseFloat(rows[i].original);
	}
	$('#originalCurrency').numberbox('setValue', original);
}

function getRMB(newRate){
	$('#invoiceMoney').numberbox('setValue', $('#originalCurrency').numberbox('getValue') * (newRate==null?$('#rmbRate').numberbox('getValue'):newRate));
}

function financialConfirmClick(obj){
	if(obj.checked){
		$('#financialConfirm').val(1);
	}else{
		$('#financialConfirm').val(0);
	}
}
</script>
</body>
</html>