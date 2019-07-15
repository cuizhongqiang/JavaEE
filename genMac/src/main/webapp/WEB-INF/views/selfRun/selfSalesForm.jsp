<%@page import="com.cbmie.genMac.selfRun.entity.SelfSales" %>
<%@page import="com.cbmie.genMac.selfRun.entity.SelfSalesGoods" %>
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
<form id="mainform" action="${ctx}/selfRun/sales/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th>合同编号</th>
		<td>
			<input type="hidden" name="id" value="${selfSales.id }"/>
			<input type="text" id="contractNo" name="contractNo" value="${selfSales.contractNo }" class="easyui-validatebox" data-options="required:true,prompt:'选择供应商自动生成'"/>
		</td>
		<th>客户名称</th>
		<td>
			<input type="text" id="customer" name="customer" value="${selfSales.customer }" class="easyui-combobox"/>
		</td>
		<th>业务员</th>
		<td>
			<input type="text" id="salesman" name="salesman" value="${selfSales.salesman }" />
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			<input type="text" id="currency" name="currency" value="${selfSales.currency }"/>
		</td>
		<th>对人民币汇率</th>
		<td>
			<input type="text" id="exchangeRateSelf" name="exchangeRateSelf" value="${selfSales.exchangeRateSelf }" class="easyui-numberbox" data-options="min:0,precision:4,required:true,
			onChange:function(newValue, oldValue){
				$('#rmb').numberbox('setValue', $('#originalCurrency').numberbox('getValue') * newValue);
			}"/>
		</td>
		<th>对美元汇率</th>
		<td>
			<input type="text" id="exchangeRateUS" name="exchangeRateUS" value="${selfSales.exchangeRateUS }" class="easyui-numberbox" data-options="min:0,precision:4,required:true,
			onChange:function(newValue, oldValue){
				$('#dollar').numberbox('setValue', $('#originalCurrency').numberbox('getValue') * newValue);
			}"/>
		</td>
	</tr>
	<tr>
		<th>原币金额</th>
		<td>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${selfSales.originalCurrency }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算',
			onChange:function(newValue, oldValue){
				$('#rmb').numberbox('setValue', newValue * $('#exchangeRateSelf').numberbox('getValue'));
				$('#dollar').numberbox('setValue', newValue * $('#exchangeRateUS').numberbox('getValue'));
			}"/>
		</td>
		<th>人民币金额</th>
		<td>
			<input type="text" id="rmb" name="rmb" value="${selfSales.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算'"/>
		</td>
		<th>美元金额</th>
		<td>
			<input type="text" id="dollar" name="dollar" value="${selfSales.dollar }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算'"/>
		</td>
	</tr>
	<tr>
		<th>保证金</th>
		<td>
			<input type="text" id="margin" name="margin" value="${selfSales.margin }" class="easyui-validatebox"/>
		</td>
		<th>担保措施</th>
		<td colspan="3">
			<input type="radio" name="assurance" value="0" style="margin-top:-2px" <c:if test="${selfSales.assurance eq 0 }">checked</c:if>/>有
			<input type="radio" name="assurance" value="1" style="margin-top:-2px" <c:if test="${selfSales.assurance eq 1 }">checked</c:if>/>无
			<span style="padding-left: 20px;">备注：<input type="text" name="assuranceRemark" value="${selfSales.assuranceRemark }" class="easyui-validatebox"/></span>
		</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<div id="selfSalesGoodsToolbar" style="padding:5px;height:auto">
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
			<table id="selfSalesGoodsTB" data-options="onClickRow: onClickRow"></table>
			<%
				SelfSales selfSales = (SelfSales)request.getAttribute("selfSales");
				List<SelfSalesGoods> selfSalesGoods = selfSales.getSelfSalesGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String selfSalesGoodsJson = objectMapper.writeValueAsString(selfSalesGoods);
				request.setAttribute("selfSalesGoodsJson", selfSalesGoodsJson);
			%>
		</td>
	</tr>
	<tr>
		<th>支付方式</th>
		<td colspan="3">
			<span id="paymentMethod"></span>
			<span style="padding-left: 20px;">备注：<input type="text" name="paymentMethodRemark" value="${selfSales.paymentMethodRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>合同管辖地</th>
		<td>
			<input type="text" id="government" name="government" value="${selfSales.government }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="5">
			<textarea name="remark" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${selfSales.remark }</textarea>
		</td>
	</tr>
	<tr>
		<th>物流方案</th>
		<td colspan="5">
			<textarea name="logistics" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${selfSales.logistics }</textarea>
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty selfSales.createDate ? now : selfSales.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty selfSales.createrName ? sessionScope.user.name : selfSales.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${selfSales.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="selfSalesGoodsJson" id="selfSalesGoodsJson"/>
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
	    	if(isValid){
		    	$.ajax({
		    		url : '${ctx}/selfRun/sales/uniqueNo/${empty selfSales.id ? 0 : selfSales.id}',
		    		data : {contractNo:$('#contractNo').val()},
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '合同编号重复！', 'info');
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
	
	//业务员
	$('#salesman').combobox({
		method:'GET',
		required : true,
		url : '${ctx}/system/user/getRelativeUser/18',//业务员对应角色id
		valueField : 'loginName',
		textField : 'name',
	});
	
	//币种
	$('#currency').combobox({
		method:'GET',
		required:true,
		url:'${ctx}/baseinfo/exchangerate/getNewExchangeRate',
		valueField : 'currency',
		textField : 'currency',
		onSelect:function(record){
			$('#exchangeRateSelf').numberbox('setValue', record.exchangeRateSelf);
			$('#exchangeRateUS').numberbox('setValue', record.exchangeRateUS);
		}
	});

	//客户名称
	$('#customer').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/1,2',
		required : true,
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			//构造合同编号
			$.ajax({
				url : '${ctx}/selfRun/sales/generateCode',
				data : {customer:record.customerCode,documentType:'合同'},
				type : 'get',
				async : false,
				cache : false,
				success : function(data) {
					$('#contractNo').val(data);
				}
			});
		}
	});
	
	$('#selfSalesGoodsTB').datagrid({
		data : JSON.parse('${selfSalesGoodsJson}'),
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
			{field:'goodsCode',title:'商品大类码',hidden:true,editor:{type:'validatebox'}},
			{field:'goodsCategory',title:'商品名称',width:200,
				editor:{
					type:'combobox',
					options:{
						valueField : 'goodsName',
						textField : 'goodsName',
						method:'get',
						url:'${ctx}/baseinfo/goodsManage/json/isLeaf',
						required:true,
						onSelect:function(record){
							var ed = $('#selfSalesGoodsTB').datagrid('getEditor', {index:editIndex,field:'goodsCode'});
							$(ed.target).validatebox('setValue', record.goodsCode);
						}
					}
				}
			},
			{field:'specification',title:'规格型号',width:100,editor:{type:'validatebox'}},
			{field:'amount',title:'数量',width:100,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}},
			{field:'unit',title:'单位',width:100,
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
			{field:'price',title:'单价',width:100,editor:{type:'numberbox',options:{min:0,precision:2,required:true}}}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#selfSalesGoodsToolbar',
	    onEndEdit : function(index, row, changes){
	    	changeChildTB($(this).datagrid('getData').rows);
	    }
	});
	
	//支付方式
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/prompt',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${selfSales.paymentMethod }'.split(",");
			$.each(data, function(index, value){
				$("#paymentMethod").append("<input type='checkbox' name='paymentMethod'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	});
	
});

var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#selfSalesGoodsTB').datagrid('validateRow', editIndex)){
		$('#selfSalesGoodsTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if(editIndex != index){
		if (endEditing()){
			$('#selfSalesGoodsTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#selfSalesGoodsTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if(endEditing()){
		$('#selfSalesGoodsTB').datagrid('appendRow',{});
		editIndex = $('#selfSalesGoodsTB').datagrid('getRows').length-1;
		$('#selfSalesGoodsTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if(editIndex == undefined){return}
	$('#selfSalesGoodsTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if(endEditing()){
		var rows = $('#selfSalesGoodsTB').datagrid('getRows');
		if(rows.length == 0){
			parent.$.messager.confirm('提示', '空商品列表，请添加商品！', function(data) {
				if (data) {
					$('#selfSalesGoodsTB').datagrid('appendRow',{});
					$('#selfSalesGoodsTB').datagrid('selectRow', 0).datagrid('beginEdit',0);
					editIndex = 0;
				}
			});
			return false;
		}
		$('#selfSalesGoodsTB').datagrid('acceptChanges');
		$('#selfSalesGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#selfSalesGoodsTB').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#selfSalesGoodsTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#selfSalesGoodsTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(rows){
	var originalCurrency = 0;
	for(var i = 0; i < rows.length; i++){
		originalCurrency += Number(rows[i].amount * rows[i].price);
	}
	$('#originalCurrency').numberbox('setValue', originalCurrency);//原币金额
}

</script>
</body>
</html>