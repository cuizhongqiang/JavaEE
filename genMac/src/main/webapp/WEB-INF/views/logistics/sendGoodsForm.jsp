<%@page import="com.cbmie.genMac.logistics.entity.SendGoods"%>
<%@page import="com.cbmie.genMac.logistics.entity.SendGoodsGoods"%>
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
<c:if test="${action eq 'create' }">
<center style="margin: 5px 5px 5px 5px;">
<a href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toConfig('toStockConfig')">库存</a>&nbsp;&nbsp;&nbsp;&nbsp;
<a href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toConfig('toInvoiceConfig')">直运</a>
</center>
</c:if>
<form id="mainform" action="${ctx}/logistics/sendGoods/${action}" method="post">
<fieldset class="fieldsetClass">
<legend>放货信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<td colspan="7" align="right" style="font-weight: bold;border: none;">
			编号:&nbsp;&nbsp;
			<input type="hidden" name="id" value="${sendGoods.id }"/>
			<input type="text" id="sendGoodsNo" name="sendGoodsNo" value="${sendGoods.sendGoodsNo }" class="easyui-validatebox" data-options="required:true,prompt:'配置后加载'"/>
		</td>
	</tr>
	<c:if test="${!empty sendGoods.inStockId || action eq 'create'}">
	<tr id="warehouseRowOne">
		<td width="10%" rowspan="2" style="font-weight: bold;">兹通知在右表格中的仓储单位</td>
		<th>仓库名称</th>
		<td><input type="text" id="warehouse" name="warehouse" value="${sendGoods.warehouse }"/></td>
		<th>地址</th>
		<td><input type="text" id="warehouseAddress" name="warehouseAddress" value="${sendGoods.warehouseAddress }" class="easyui-validatebox"/></td>
		<th>邮编</th>
		<td><input type="text" id="warehouseZipcode" name="warehouseZipcode" value="${sendGoods.warehouseZipcode }" class="easyui-validatebox"/></td>
	</tr>
	<tr id="warehouseRowTwo">
		<th>联系人</th>
		<td><input type="text" id="warehouseContacts" name="warehouseContacts" value="${sendGoods.warehouseContacts }" class="easyui-validatebox"/></td>
		<th>电话</th>
		<td><input type="text" id="warehouseContactsPhone" name="warehouseContactsPhone" value="${sendGoods.warehouseContactsPhone }" class="easyui-validatebox"/></td>
		<th>传真</th>
		<td><input type="text" id="warehouseContactsFax" name="warehouseContactsFax" value="${sendGoods.warehouseContactsFax }" class="easyui-validatebox"/></td>
	</tr>
	</c:if>
	<tr>
		<td width="10%" rowspan="3" style="font-weight: bold;">将右表中存储的货物</td>
		<th>合同号</th>
		<td><input type="text" id="contractNo" name="contractNo" value="${sendGoods.contractNo }" class="easyui-validatebox" readonly="true" style="background: #eee" data-options="required:true,prompt:'配置后加载'"/></td>
		<th>提单号</th>
		<td><input type="text" id="invoiceNo"  name="invoiceNo" value="${sendGoods.invoiceNo }" class="easyui-validatebox" readonly="true" style="background: #eee" data-options="required:true,prompt:'配置后加载'"/></td>
		<th>入库单号</th>
		<td><input type="text" id="inStockId"  name="inStockId" value="${sendGoods.inStockId }" class="easyui-validatebox" readonly="true" style="background: #eee"/></td>
	</tr>
	<input type="hidden" id="totalMoney" name="totalMoney" value="${sendGoods.totalMoney }" class="easyui-numberbox" data-options="required:true,precision:2"/>
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
			<table id="sendGoodsGoods" data-options="onClickRow: onClickRow"></table>
			<%
				SendGoods sendGoods = (SendGoods)request.getAttribute("sendGoods");
				List<SendGoodsGoods> sggList = sendGoods.getSendGoodsGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String sggJson = objectMapper.writeValueAsString(sggList);
				request.setAttribute("sggJson", sggJson);
			%>
		</td>
	</tr>
	<tr>
		<th>放货备注</th>
		<td colspan="5"><textarea name="remark" class="easyui-validatebox" style="width: 98%; margin-top: 2px; height: 20px;" data-options="validType:['length[0,255]']">${sendGoods.remark }</textarea></td>
	</tr>
	<tr>
		<td width="10%" rowspan="2" style="font-weight: bold;">权益转移给表格中提货人</td>
		<th>提货单位</th>
		<td><input type="text" id="getGoodsUnit" name="getGoodsUnit" value="${sendGoods.getGoodsUnit }"/></td>
		<th>地址</th>
		<td><input type="text" id="getGoodsUnitAddress" name="getGoodsUnitAddress" value="${sendGoods.getGoodsUnitAddress }" class="easyui-validatebox"/></td>
		<th>邮编</th>
		<td><input type="text" id="getGoodsUnitZipcode" name="getGoodsUnitZipcode" value="${sendGoods.getGoodsUnitZipcode }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<th>联系人</th>
		<td><input type="text" id="getGoodsContacts" name="getGoodsContacts" value="${sendGoods.getGoodsContacts }" class="easyui-validatebox"/></td>
		<th>电话</th>
		<td><input type="text" id="getGoodsContactsPhone" name="getGoodsContactsPhone" value="${sendGoods.getGoodsContactsPhone }" class="easyui-validatebox"/></td>
		<th>传真</th>
		<td><input type="text" id="getGoodsContactsFax" name="getGoodsContactsFax" value="${sendGoods.getGoodsContactsFax }" class="easyui-validatebox"/></td>
	</tr>
	<tr>
		<td colspan="7" align="right" style="font-weight: bold;border: none;">
			签发日期:&nbsp;&nbsp;
			<input type="text" name="sendDate" value="<fmt:formatDate value="${sendGoods.sendDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd" data-options="required:true"/>
		</td>
	</tr>
</table>
</fieldset>
<input type="hidden" name="sendGoodsGoodsJson" id="sendGoodsGoodsJson"/>
<input type="hidden" name="apply" id="apply" value="false"/>
</form>
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty sendGoods.createDate ? now : sendGoods.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty sendGoods.createrName ? sessionScope.user.name : sendGoods.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${sendGoods.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
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
		    		url : '${ctx}/logistics/sendGoods/uniqueNo/${empty sendGoods.id ? 0 : sendGoods.id}/' + $('#sendGoodsNo').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '发货单号重复！', 'info');
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
	
	$('#warehouse').combobox({
		method:'get',
		required:true,
		url:'${ctx}/baseinfo/warehouse/filter?filter_EQS_status=正常',
		valueField : 'warehouseName',
		textField : 'warehouseName',
		onSelect:function(record){
			$('#warehouseAddress').val(record.address); //地址
			$('#warehouseZipcode').val(record.zipCode); //邮编
			$('#warehouseContacts').val(record.contactPerson); //联系人
			$('#warehouseContactsPhone').val(record.phoneContact); //电话
			$('#warehouseContactsFax').val(record.fax); //传真
		}
	});
	
	$('#getGoodsUnit').combobox({
		method:'get',
		required:true,
		url:'${ctx}/baseinfo/affiliates/getCompany/1',
		valueField : 'customerName',
		textField : 'customerName',
		onSelect:function(record){
			$('#getGoodsUnitAddress').val(record.address); //地址
			$('#getGoodsUnitZipcode').val(record.zipCode); //邮编
			$('#getGoodsContacts').val(record.contactPerson); //联系人
			$('#getGoodsContactsPhone').val(record.phoneContact); //电话
			$('#getGoodsContactsFax').val(record.fax); //传真
		}
	});
});

//配置
function toConfig(requestMapping){
	configDlg = $("#configDlg").dialog({
		title: '选择放货',
		width: 750,
		height: 280,
	    href:'${ctx}/logistics/sendGoods/' + requestMapping,
	    modal:true,
	    buttons:[{
			text:'保存',
			handler:function(){
				var row;
				if(requestMapping == "toStockConfig"){
					row = stock_dg.datagrid('getSelected');
				}else{
					row = invoice_dg.datagrid('getSelected');
				}
				if(rowIsNull(row)) return;
				parent.$.messager.progress({  
			        title : '提示',  
			        text : '数据处理中，请稍后....'  
			    });
				$.ajax({
					url : '${ctx}/logistics/sendGoods/generateCode',
					data : {contractNo:row.contractNo,documentType:'放货'},
					type : 'get',
					async : false,
					cache : false,
					success : function(data) {
						$('#sendGoodsNo').val(data);
					}
				});//构造编号
				var rmbRate = 1;
				$.ajax({
					url : '${ctx}/logistics/invoiceReg/filter?filter_EQS_invoiceNo=' + row.invoiceNo,
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						rmbRate = data[0].rmbRate;
					}
				});//到单人民币汇率
				var goods;
				if(requestMapping == "toStockConfig"){
					$('#warehouse').combobox({required:true});
					$("tr[id=warehouseRowOne]").show();
					$("tr[id=warehouseRowTwo]").show();
					$('#inStockId').val(row.inStockId); //入库单号
					$('#warehouse').combobox('select', row.storageUnit); //仓库名称
					goods = row.inStockGoods;
				}else{
					$('#warehouse').combobox({required:false});
					$("tr[id=warehouseRowOne]").hide();
					$("tr[id=warehouseRowTwo]").hide();
					$('#inStockId').val(null); //入库单号
					goods = row.invoiceGoods;
				}
				$('#contractNo').val(row.contractNo); //合同号
				$('#invoiceNo').val(row.invoiceNo); //提单号
				$.ajax({
					url : '${ctx}/agent/agentImport/filter?filter_EQS_contractNo=' + row.contractNo,
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						$.ajax({
							url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + data[0].customerId,
							type : 'get',
							async : false,
							cache : false,
							dataType : 'json',
							success : function(data) {
								$('#getGoodsUnit').combobox('select', data[0].customerName);
							}
						});
					}
				}); //提货单位（委托方）
				var goodsNew = [];
				var amountTotal = 0;//总数量
				var salesAmountTotal = 0;//总销售金额
				for(var i = 0; i < goods.length; i++){
					var price = (goods[i].price * rmbRate).toFixed(2); //人民币单价
					var salesAmount = (price * goods[i].amount).toFixed(2); //销售金额
					goodsNew.push({'goodsCategory':goods[i].goodsCategory,
						'specification':goods[i].specification,
						'frameNo':goods[i].frameNo,
						'unit':goods[i].unit,
						'amount':goods[i].amount,
						'price':price,
						'salesAmount':salesAmount});
					amountTotal += Number(goods[i].amount);
					salesAmountTotal += Number(salesAmount);
				}
				var footer = [{'salesAmount':salesAmountTotal, 'amount':amountTotal}]; //放入footer页脚
				var datax = { "rows": goodsNew, "footer": footer };
				initSendGoodsGoods(datax); //初始化入库商品
				editIndex = undefined;
				$('#totalMoney').numberbox('setValue', salesAmountTotal); //本单合计
				parent.$.messager.progress('close');
				$('#configDlg').dialog('close');
			}
		},{
			text:'关闭',
			handler:function(){
				$('#configDlg').dialog('close');
			}
		}]
	});
}

var sendGoodsGoods;
initSendGoodsGoods(); //初始化
function initSendGoodsGoods(value){
	sendGoodsGoods=$('#sendGoodsGoods').datagrid({
		data : value != null ? value : JSON.parse('${sggJson}'),
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
			{field:'goodsCategory',title:'货物名称',width:100,
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
			{field:'specification',title:'规格型号',width:80,editor:{type:'validatebox'}},
			{field:'frameNo',title:'车架号',width:80,editor:{type:'validatebox'}},
			{field:'unit',title:'单位',width:40,
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
			{field:'amount',title:'数量',width:80,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			required:true,
	        			onChange:function(newValue, oldValue){
		        			var salesAmountObj = $('#sendGoodsGoods').datagrid('getEditor', {index:editIndex,field:'salesAmount'});
		        			if(salesAmountObj == null){
		        				return;
		        			}
		        			var priceObj = $('#sendGoodsGoods').datagrid('getEditor', {index:editIndex,field:'price'});
		        			price = priceObj.target.numberbox('getValue');
		        			salesAmountObj.target.numberbox('setValue', newValue * price);
	        			}
	        		}
	        	}
	        },
			{field:'price',title:'单价',width:80,
	        	editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			required:true,
	        			onChange:function(newValue, oldValue){
		        			var salesAmountObj = $('#sendGoodsGoods').datagrid('getEditor', {index:editIndex,field:'salesAmount'});
		        			if(salesAmountObj == null){
			        			return;
			        		}
		        			var amountObj = $('#sendGoodsGoods').datagrid('getEditor', {index:editIndex,field:'amount'});
		        			amount = amountObj.target.numberbox('getValue');
		        			salesAmountObj.target.numberbox('setValue', newValue * amount);
	        			}
	        		}
	        	}	
			},
			{field:'salesAmount',title:'销售金额',width:80,
				editor:{
	        		type:'numberbox',
	        		options:{
	        			precision:2,
	        			groupSeparator:',',
	        			required:true
	        		}
	        	}	
			}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#childToolbar',
	    onEndEdit:function(rowIndex, rowData, changes){
	    	changeChildTB();
	    },
	    onLoadSuccess:function(data){
	    	var salesAmountTotal = 0;//销售金额合计
			var amountTotal = 0;//数量合计
			for(var i = 0; i < data.rows.length; i++){
				salesAmountTotal += Number(data.rows[i].salesAmount);
				amountTotal += Number(data.rows[i].amount);
			}
	    	// 更新页脚行
	    	$(this).datagrid('reloadFooter', [{'salesAmount':salesAmountTotal, 'amount':amountTotal}]);
	    }
	});
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#sendGoodsGoods').datagrid('validateRow', editIndex)){
		$('#sendGoodsGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#sendGoodsGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#sendGoodsGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#sendGoodsGoods').datagrid('appendRow', {});
		editIndex = $('#sendGoodsGoods').datagrid('getRows').length-1;
		$('#sendGoodsGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#sendGoodsGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
	changeChildTB();
}
function accept(){
	if (endEditing()){
		var rows = $('#sendGoodsGoods').datagrid('getRows');
		$('#sendGoodsGoods').datagrid('acceptChanges');
		$('#sendGoodsGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#sendGoodsGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#sendGoodsGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#sendGoodsGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(){
	var rows = $('#sendGoodsGoods').datagrid('getData').rows;
	var salesAmountTotal = 0;//销售金额合计
	var amountTotal = 0;//数量合计
	for(var i = 0; i < rows.length; i++){
		salesAmountTotal += Number(rows[i].salesAmount);
		amountTotal += Number(rows[i].amount);
	}
	// 更新页脚行
	$('#sendGoodsGoods').datagrid('reloadFooter', [{'salesAmount':salesAmountTotal, 'amount':amountTotal}]);
	$('#totalMoney').numberbox('setValue', salesAmountTotal);//本单合计
}
</script>
</body>
</html>