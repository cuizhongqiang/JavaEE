<%@page import="com.cbmie.genMac.credit.entity.OpenCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.OpenCreditGoods" %>
<%@page import="com.cbmie.genMac.credit.entity.ChangeCredit" %>
<%@page import="com.cbmie.genMac.credit.entity.ChangeCreditGoods" %>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<form id="mainform" action="${ctx}/credit/changeCredit/${action}" method="post">
<table width="98%" class="tableClass">
	<tr id="agentImportTR">
		<th>外合同号</th>
		<td colspan="2">
			<c:if test="${action eq 'update'}"><input type="hidden" name="id" value="${id }"/></c:if>
			<c:if test="${action eq 'create'}"><input type="hidden" name="changeId" value="${openCredit.id }"/></c:if>
			<input type="hidden" name="contractType" value="${openCredit.contractType }"/>
			<input type="text" id="foreignContractNo" name="foreignContractNo" value="${openCredit.foreignContractNo }" class="easyui-validatebox" data-options="" readonly="true" style="background: #eee"/>
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
			<input type="text" id="contractNo" name="contractNo" value="${openCredit.contractNo }" class="easyui-validatebox" data-options="" readonly="true" style="background: #eee"/>
		</td>
		<th></th>
		<td colspan="2">
			<input type="text" id="customerName" class="easyui-validatebox" value="${my:getAffiliatesById(openCredit.customerId).customerName }" data-options="" readonly="true" style="background: #eee"/>
			<input type="hidden" id="customerId" name="customerId" value="${openCredit.customerId }"/>
		</td>
	</tr>
	<tr>
		<th>合同原币金额</th>
		<td>
			<input type="hidden" id="currency" name="currency" value="${openCredit.currency }"/>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${openCredit.originalCurrency }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:',',prefix:'${openCredit.currency }'" readonly="true" style="background: #eee"/>
		</td>
		<th>折美元</th>
		<td>
			<input type="text" id="dollar" name="dollar" value="${openCredit.dollar }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" readonly="true" style="background: #eee"/>
		</td>
		<th>折人民币</th>
		<td>
			<input type="text" id="rmb" name="rmb" value="${openCredit.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" readonly="true" style="background: #eee"/>
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
				<c:when test="${openCredit.lcType != 3 }">
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
				<c:when test="${openCredit.lcType != 3 }">
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
				<c:when test="${openCredit.lcType != 3 }">
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
				<c:when test="${openCredit.lcType != 3 }">
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
				<c:when test="${openCredit.lcType != 3 }">
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
				<c:when test="${openCredit.lcType != 3 }">
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
			<table id="changeCreditGoods" data-options="onClickRow: onClickRow"></table>
			<%
				Object obj = request.getAttribute("openCredit");
				ObjectMapper objectMapper = new ObjectMapper();
				String childJson = "";
				if (obj instanceof OpenCredit) {
					OpenCredit oc = (OpenCredit)obj;
					List<OpenCreditGoods> childList = oc.getOpenCreditGoods();
					for (OpenCreditGoods openCreditGoods : childList) {
						openCreditGoods.setId(null);
					}
					childJson = objectMapper.writeValueAsString(childList);
				} else if (obj instanceof ChangeCredit) {
					ChangeCredit cc = (ChangeCredit)obj;
					List<ChangeCreditGoods> childList = cc.getChangeCreditGoods();
					childJson = objectMapper.writeValueAsString(childList);
				}
				request.setAttribute("childJson", childJson);
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
<input type="hidden" name="apply" id="apply" value="false"/>
<input type="hidden" name="changeCreditGoodsJson" id="changeCreditGoodsJson"/>
</form>
<script type="text/javascript">
var changeCreditGoods;
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
	    		apply(data, '${action eq "create" ? openCredit.id : openCredit.changeId}');
	    	}
	    }
	});
	
	changeCreditGoods=$('#changeCreditGoods').datagrid({
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
	
	changeType('${openCredit.lcType }');
	
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

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#changeCreditGoods').datagrid('validateRow', editIndex)){
		$('#changeCreditGoods').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#changeCreditGoods').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#changeCreditGoods').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#changeCreditGoods').datagrid('appendRow',{});
		editIndex = $('#changeCreditGoods').datagrid('getRows').length-1;
		$('#changeCreditGoods').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#changeCreditGoods').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#changeCreditGoods').datagrid('getRows');
		$('#changeCreditGoods').datagrid('acceptChanges');
		$('#changeCreditGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#changeCreditGoods').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#changeCreditGoods').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#changeCreditGoods').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>