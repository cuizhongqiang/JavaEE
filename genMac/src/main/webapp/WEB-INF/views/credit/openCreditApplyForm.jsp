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
<form id="mainform" action="${ctx}/credit/openCredit/${action}" method="post">
<c:if test="${action eq 'create' }">
<center style="margin: 10px;font-size: 16px;">
<input type="radio" name="contractType" value="0" style="margin-top:-2px;" <c:if test="${openCredit.contractType eq 0 }">checked</c:if> onclick="changeContractType($(this).val())"/>代理进口
<input type="radio" name="contractType" value="1" style="margin-top:-2px;margin-left:20px;" <c:if test="${openCredit.contractType eq 1 }">checked</c:if> onclick="changeContractType($(this).val())"/>(自营)采购
<input type="radio" name="contractType" value="2" style="margin-top:-2px;margin-left:20px;" <c:if test="${openCredit.contractType eq 2 }">checked</c:if> onclick="changeContractType($(this).val())"/>(内贸)采购
<input type="radio" name="contractType" value="3" style="margin-top:-2px;margin-left:20px;" <c:if test="${openCredit.contractType eq 3 }">checked</c:if> onclick="changeContractType($(this).val())"/>(内贸)代理采购
<span class="toolbar-item dialog-tool-separator" style="margin: 10px;"></span>
<a href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toConfig()">配置合同</a>
</center>
</c:if>
<table width="98%" class="tableClass">
	<tr id="agentImportTR">
		<th>外合同号</th>
		<td colspan="2">
			<input type="hidden" name="id" value="${openCredit.id }"/>
			<input type="text" id="foreignContractNo" name="foreignContractNo" value="${openCredit.foreignContractNo }" class="easyui-validatebox" data-options="prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
		</td>
		<th>供应外商</th>
		<td colspan="2">
			<input type="text" id="foreignName" class="easyui-validatebox" value="${empty openCredit.foreignId ? '' : my:getAffiliatesById(openCredit.foreignId).customerName }" data-options="prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
			<input type="hidden" id="foreignId" name="foreignId" value="${openCredit.foreignId }"/>
		</td>
	</tr>
	<tr id="purchaseTR">
		<th></th>
		<td colspan="2">
			<input type="text" id="contractNo" name="contractNo" value="${openCredit.contractNo }" class="easyui-validatebox" data-options="required:true,prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
		</td>
		<th></th>
		<td colspan="2">
			<input type="text" id="customerName" class="easyui-validatebox" value="${empty openCredit.customerId ? '' : my:getAffiliatesById(openCredit.customerId).customerName }" data-options="prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
			<input type="hidden" id="customerId" name="customerId" value="${openCredit.customerId }"/>
		</td>
	</tr>
	<tr>
		<th>合同原币金额</th>
		<td>
			<input type="hidden" id="currency" name="currency" value="${openCredit.currency }"/>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${openCredit.originalCurrency }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',prefix:'${openCredit.currency }',prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
		</td>
		<th>折美元</th>
		<td>
			<input type="text" id="dollar" name="dollar" value="${openCredit.dollar }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
		</td>
		<th>折人民币</th>
		<td>
			<input type="text" id="rmb" name="rmb" value="${openCredit.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',prompt:'配置合同加载'" readonly="true" style="background: #eee"/>
		</td>
	</tr>
	<tr>
		<th>价格条款</th>
		<td>
			<input type="text" id="priceTerms" name="priceTerms" value="${openCredit.priceTerms }" class="easyui-validatebox" data-options="" readonly="true" style="background: #eee"/>
		</td>
		<th>即期/远期</th>
		<td>
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					<input type="radio" name="lcType" value="0" style="margin-top:-2px" <c:if test="${openCredit.lcType eq 0 }">checked</c:if> onclick="changeType(0);"/>即期			
					<input type="radio" name="lcType" value="1" style="margin-top:-2px" <c:if test="${openCredit.lcType eq 1 }">checked</c:if> onclick="changeType(1);"/>远期
				</c:when>
				<c:otherwise>
					<input type="hidden" name="lcType" value="3"/>
				</c:otherwise>
			</c:choose>
		</td>
		<th>远期天数</th>
		<td>
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					<input type="text" id="days" name="days" value="${openCredit.days }" class="easyui-numberbox" data-options="min:0"/>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<th rowspan="2" height="48px">
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					本次LC应收
				</c:when>
				<c:otherwise>
					本次TT应收
				</c:otherwise>
			</c:choose>
		</th>
		<td>
			<input type="text" value="${openCredit.receivable / openCredit.rmb * 100 }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%',
			onChange:function(newValue, oldValue){
				$('#receivable').numberbox('setValue', newValue * $('#rmb').numberbox('getValue') * 0.01);
			}"/>
		</td>
		<th rowspan="2">
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					本次LC实收
				</c:when>
				<c:otherwise>
					本次TT实收
				</c:otherwise>
			</c:choose>
		</th>
		<td>
			<input type="text" value="${openCredit.receipts / openCredit.rmb * 100 }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%',
			onChange:function(newValue, oldValue){
				$('#receipts').numberbox('setValue', newValue * $('#rmb').numberbox('getValue') * 0.01);
			}"/>
		</td>
		<th rowspan="2">
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					信用证金额占合同比例
				</c:when>
				<c:otherwise>
					本次TT金额占合同比例
				</c:otherwise>
			</c:choose>
		</th>
		<td rowspan="2">
			<input type="text" name="percent" value="${openCredit.percent }" class="easyui-numberbox" data-options="required:true,min:0,precision:2,suffix:'%',
			onChange:function(newValue, oldValue){
				$('#theMoney').numberbox('setValue', newValue * $('#rmb').numberbox('getValue') * 0.01);
			}"/>
		</td>
	</tr>
	<tr>
		<td>
			<input type="text" id="receivable" name="receivable" value="${openCredit.receivable }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
		</td>
		<td>
			<input type="text" id="receipts" name="receipts" value="${openCredit.receipts }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<th>开证行</th>
		<td colspan="2">
			<input type="text" id="bank" name="bank" value="${openCredit.bank }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>
			<c:choose>
				<c:when test="${type eq 'LC' || (action == 'update' && openCredit.lcType != 3) }">
					本次开证金额
				</c:when>
				<c:otherwise>
					本次TT金额
				</c:otherwise>
			</c:choose>
		</th>
		<td colspan="2">
			<input type="text" id="theMoney" name="theMoney" value="${openCredit.theMoney }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"/>
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
			<table id="openCreditGoods" data-options="onClickRow: onClickRow"></table>
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
		<td colspan="6">
			备注<br/>
			<textarea name="remark" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${openCredit.remark }</textarea>
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty openCredit.createDate ? now : openCredit.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty openCredit.createrName ? sessionScope.user.name : openCredit.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${openCredit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="apply" id="apply" value="false"/>
<input type="hidden" name="openCreditGoodsJson" id="openCreditGoodsJson"/>
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
	
	changeType('${openCredit.lcType }');
	changeContractType('${openCredit.contractType }');
	
});

function changeType(value){
	if(value == 1){
		$('#days').numberbox({
			disabled:false,
			required:true
		});
	}else{
		$('#days').numberbox({
			disabled:true,
			required:false
		});
		$('#days').numberbox('clear');
	}
}

function changeContractType(value){
	var purchaseTRArray = [];
	$("#purchaseTR th").each(function(index, dom){
		purchaseTRArray.push(dom);
	});
	if(value == 0){
		$('#agentImportTR').show();
		$(purchaseTRArray[0]).text("内合同号");
		$(purchaseTRArray[1]).text("国内客户");
	}else{
		$('#agentImportTR').hide();
		$(purchaseTRArray[0]).text("合同号");
		$(purchaseTRArray[1]).text("供应商");
	}
}

//配置合同
function toConfig(){
	//合同类型
	var contractType = $("input[name='contractType']:checked").val();
	
	configDlg=$("#configDlg").dialog({
		title: '配置合同',
		width: 600,
		height: 280,
	    href:'${ctx}/credit/openCredit/config/${type}/' + contractType,
	    modal:true,
	    buttons:[{
			text:'保存',
			handler:function(){
				var row = config_dg.datagrid('getSelected');
				if(rowIsNull(row)) return;
				if(contractType == 0){
					$('#foreignContractNo').val(row.foreignContractNo);
					$.ajax({
						url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + row.foreignId,
						type : 'get',
						async : false,
						cache : false,
						dataType : 'json',
						success : function(data) {
							$('#foreignName').val(data[0].customerName);
							$('#foreignId').val(data[0].id);
						}
					});
				}
				$('#contractNo').val(row.contractNo);
				$.ajax({
					url : '${ctx}/baseinfo/affiliates/filter?filter_EQL_id=' + row.customerId,
					type : 'get',
					async : false,
					cache : false,
					dataType : 'json',
					success : function(data) {
						$('#customerName').val(data[0].customerName);
						$('#customerId').val(data[0].id);
					}
				});
				$('#currency').val(row.currency);
				$('#originalCurrency').numberbox({
					value:row.originalCurrency,
					prefix:row.currency
				});
				$('#dollar').numberbox('setValue', row.dollar);
				$('#rmb').numberbox('setValue', row.rmb);
				$('#priceTerms').val(row.priceTerms);
				var goodsOld = [];
				if(contractType == 0){
					goodsOld = row.agentImportGoods;
				}else if(contractType == 1){
					goodsOld = row.selfPurchaseGoods;
				}else if(contractType == 2){
					goodsOld = row.domesticPurchaseGoods;
				}else if(contractType == 3){
					goodsOld = row.agentPurchaseGoods;
				}
				var goodsNew = [];
				for(var i = 0; i < goodsOld.length; i++){
					goodsNew.push({'goodsCategory':goodsOld[i].goodsCategory,
						'specification':goodsOld[i].specification,
						'amount':goodsOld[i].amount,
						'unit':goodsOld[i].unit});
				}
				initOpenCreditGoods(goodsNew);
				editIndex = undefined;
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

var openCreditGoods;
initOpenCreditGoods(); //初始化
function initOpenCreditGoods(value){
	openCreditGoods=$('#openCreditGoods').datagrid({
		data : value != null ? value : JSON.parse('${ocgJson}'),
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
	if ($('#openCreditGoods').datagrid('validateRow', editIndex)){
		$('#openCreditGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#openCreditGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#openCreditGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#openCreditGoods').datagrid('appendRow',{});
		editIndex = $('#openCreditGoods').datagrid('getRows').length-1;
		$('#openCreditGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#openCreditGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#openCreditGoods').datagrid('getRows');
		$('#openCreditGoods').datagrid('acceptChanges');
		$('#openCreditGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#openCreditGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#openCreditGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#openCreditGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>
</body>
</html>