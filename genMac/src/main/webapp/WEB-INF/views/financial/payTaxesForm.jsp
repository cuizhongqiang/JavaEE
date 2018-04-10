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
<form id="mainform" action="${ctx}/financial/payTaxes/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th>提单号</th>
		<td colspan="2">
			<input type="hidden" name="id" value="${payTaxes.id }"/>
			<input type="text" id="invoiceNo" name="invoiceNo" value="${payTaxes.invoiceNo }"/>
		</td>
		<th>合同号</th>
		<td colspan="2">
			<input type="text" id="contractNo" name="contractNo" value="${payTaxes.contractNo }" class="easyui-validatebox" data-options="prompt:'选择提单号加载'" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td colspan="2">
			<input type="text" id="currency" name="currency" value="${payTaxes.currency }" class="easyui-validatebox" data-options="prompt:'选择提单号加载'" readonly="true" style="background: #eee"/>
		</td>
		<th>汇率</th>
		<td colspan="2">
			<input type="text" id="rate" name="rate" value="${payTaxes.rate }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:4" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th>原币金额</th>
		<td colspan="2">
			<input type="text" id="originalCurrency" name="originalCurrency" value="${payTaxes.originalCurrency }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:','," readonly="true" style="background: #eee"/>
		</td>
		<th>提单金额(人民币)</th>
		<td colspan="2">
			<input type="text" id="invoiceMoney" name="invoiceMoney" value="${payTaxes.invoiceMoney }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:','" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
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
			<table id="payTaxesGoods" data-options="onClickRow: onClickRow"></table>
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
			<input type="text" id="taxNo" name="taxNo" value="${payTaxes.taxNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th width="13%">关税</th>
		<td width="13%">
			<input type="text" id="tax" name="tax" value="${payTaxes.tax }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#taxTotal').numberbox('setValue',  Number(newValue) + Number($('#vat').numberbox('getValue')) + Number($('#saleTax').numberbox('getValue')) + Number($('#otherTax').numberbox('getValue')));
				}
			"/>
		</td>
		<td style="border: none"></td>
		<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>增值税</th>
		<td>
			<input type="text" id="vat" name="vat" value="${payTaxes.vat }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#taxTotal').numberbox('setValue',  Number(newValue) + Number($('#tax').numberbox('getValue')) + Number($('#saleTax').numberbox('getValue')) + Number($('#otherTax').numberbox('getValue')));
				}
			"/>
		</td>
		<td style="border: none"></td>
		<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>消费税</th>
		<td>
			<input type="text" id="saleTax" name="saleTax" value="${payTaxes.saleTax }" class="easyui-numberbox" data-options="prompt:'选择提单号加载',precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#taxTotal').numberbox('setValue',  Number(newValue) + Number($('#tax').numberbox('getValue')) + Number($('#vat').numberbox('getValue')) + Number($('#otherTax').numberbox('getValue')));
				}
			"/>
		</td>
		<td style="border: none"></td>
		<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	</tr>
	<tr>
		<td style="border-right: none;border-top: none;border-bottom: none;"></td>
		<td style="border: none"></td>
		<th>其它</th>
		<td>
			<input type="text" id="otherTax" name="otherTax" value="${payTaxes.otherTax }" class="easyui-numberbox" data-options="precision:2,groupSeparator:',',
				onChange:function(newValue, oldValue){
					$('#taxTotal').numberbox('setValue',  Number(newValue) + Number($('#tax').numberbox('getValue')) + Number($('#vat').numberbox('getValue')) + Number($('#saleTax').numberbox('getValue')));
				}
			"/>
		</td>
		<th>总计税金</th>
		<td>
			<input type="text" id="taxTotal" name="taxTotal" value="${payTaxes.taxTotal }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th>收款单位</th>
		<td colspan="2">
			<input type="text" id="receivingUnit" name="receivingUnit" value="${payTaxes.receivingUnit }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>支付方式</th>
		<td colspan="2">
			<input type="text" id="payModel" name="payModel" value="${payTaxes.payModel }"/>
		</td>
	</tr>
	<tr>
		<th>交税银行</th>
		<td colspan="2">
			<input type="text" id="bank" name="bank" value="${payTaxes.bank }" class="easyui-validatebox" data-options=""/>
		</td>
		<th>交税账号</th>
		<td colspan="2">
			<input type="text" id="account" name="account" value="${payTaxes.account }" class="easyui-validatebox" data-options="validType:['num','length[16,19]']"/>
		</td>
	</tr>
	<tr>
		<th>是否委托货代</th>
		<td colspan="2">
			<input type="radio" name="delegaFreight" value="1" style="margin-top:-2px" onclick="" <c:if test="${payTaxes.delegaFreight eq 1}">checked</c:if>/>是
			<input type="radio" name="delegaFreight" value="0" style="margin-top:-2px" onclick="" <c:if test="${payTaxes.delegaFreight eq 0}">checked</c:if>/>否
		</td>
		<th>申请日期</th>
		<td colspan="2">
			<input type="text" id="applyDate" name="applyDate" value="<fmt:formatDate value="${payTaxes.applyDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty payTaxes.createDate ? now : payTaxes.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty payTaxes.createrName ? sessionScope.user.name : payTaxes.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${payTaxes.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="apply" id="apply" value="false"/>
<input type="hidden" name="payTaxesGoodsJson" id="payTaxesGoodsJson"/>
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
	    	if(isValid){
	    		$.ajax({
		    		url : '${ctx}/financial/payTaxes/uniqueNo/${empty payTaxes.id ? 0 : payTaxes.id}/' + $('#taxNo').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '税号重复！', 'info');
		    			}
		    		}
		    	});
	    	}
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
	
	$('#invoiceNo').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/logistics/invoiceReg/filter',
		valueField : 'invoiceNo',
		textField : 'invoiceNo',
		onSelect:function(record){
			$('#contractNo').val(record.contractNo);//合同号
			$('#currency').val(record.currency); //币种
			$('#rate').numberbox('setValue', record.rmbRate);//汇率
			$('#originalCurrency').numberbox('setValue', record.originalCurrency);//原币金额
			$('#invoiceMoney').numberbox('setValue', record.invoiceMoney); //提单金额
			var invoiceGoods = record.invoiceGoods;
			var goodsNew = [];
			for(var i = 0; i < invoiceGoods.length; i++){
				goodsNew.push({'goodsCategory':invoiceGoods[i].goodsCategory,
					'specification':invoiceGoods[i].specification,
					'amount':invoiceGoods[i].amount,
					'unit':invoiceGoods[i].unit});
			}
			initPayTaxesGoods(goodsNew);
			editIndex = undefined;
			var taxTotal = 0 ;
			var saleTaxTotal = 0;
			var vatTotal = 0;
			for (var i = 0; i < invoiceGoods.length; i++) {
				taxTotal += Number(invoiceGoods[i].tax);
				saleTaxTotal += Number(invoiceGoods[i].saleTax);
				vatTotal += Number(invoiceGoods[i].vat);
			}
			$('#tax').numberbox('setValue', taxTotal);//关税
			$('#saleTax').numberbox('setValue', saleTaxTotal);//消费税
			$('#vat').numberbox('setValue', vatTotal);//增值税
			$('#taxTotal').numberbox('setValue', taxTotal + saleTaxTotal + vatTotal + Number($('#otherTax').numberbox('getValue')));//总计
		}
	});
	
	//支付方式
	$('#payModel').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dict/getDictByCode/PAYMODEL',
		valueField : 'name',
		textField : 'name',
		onSelect:function(record){
			choosePayModel(record.name);
		},
		onLoadSuccess:function(data){
			window.setTimeout(function(){
				choosePayModel($('#payModel').combobox('getValue'));
			}, 1000);
		}
	});
});

function choosePayModel(value){
	if(value == "网支"){
		$('#bank').val(null);
		$('#bank').attr('readonly', true);
		$('#bank').attr('style', 'background: #eee');
		$('#bank').validatebox({required: false});
		$('#account').val(null);
		$('#account').attr('readonly', true);
		$('#account').attr('style', 'background: #eee');
		$('#account').validatebox({required: false});
	}else{
		$('#bank').removeAttr('readonly');
		$('#bank').removeAttr('style');
		$('#bank').validatebox({required: true});
		$('#account').removeAttr('readonly');
		$('#account').removeAttr('style');
		$('#account').validatebox({required: true});
	}
}

var payTaxesGoods;
initPayTaxesGoods(); //初始化
function initPayTaxesGoods(value){
	payTaxesGoods=$('#payTaxesGoods').datagrid({
		data : value != null ? value : JSON.parse('${ptgJson}'),
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
			{field:'goodsCategory',title:'商品名称',width:50,
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
			{field:'specification',title:'规格型号',width:40,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:20,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			required:true
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
	        }
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar'
	});
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#payTaxesGoods').datagrid('validateRow', editIndex)){
		$('#payTaxesGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#payTaxesGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#payTaxesGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#payTaxesGoods').datagrid('appendRow',{});
		editIndex = $('#payTaxesGoods').datagrid('getRows').length-1;
		$('#payTaxesGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#payTaxesGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#payTaxesGoods').datagrid('getRows');
		$('#payTaxesGoods').datagrid('acceptChanges');
		$('#payTaxesGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#payTaxesGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#payTaxesGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#payTaxesGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>
</body>
</html>