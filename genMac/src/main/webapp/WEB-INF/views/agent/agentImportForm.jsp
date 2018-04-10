<%@page import="com.cbmie.genMac.agent.entity.AgentImport" %>
<%@page import="com.cbmie.genMac.agent.entity.AgentImportGoods" %>
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
<form id="mainform" action="${ctx}/agent/agentImport/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<td width="5%" rowspan="3">基本信息</td>
		<th>商品类别</th>
		<td colspan="5">
			<span id="goodsType"></span>
			<span style="padding-left: 20px;">备注：<input type="text" name="goodsTypeRemark" value="${agentImport.goodsTypeRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>业务员</th>
		<td>
			<input type="hidden" name="id" value="${agentImport.id }"/>
			<input type="text" id="salesman" name="salesman" value="${agentImport.salesman }" />
		</td>
	</tr>
	<tr>
		<th>币种</th>
		<td>
			<input type="text" id="currency" name="currency" value="${agentImport.currency }"/>
		</td>
		<th>对人民币汇率</th>
		<td>
			<input type="text" id="exchangeRateSelf" name="exchangeRateSelf" value="${agentImport.exchangeRateSelf }" class="easyui-numberbox" data-options="min:0,precision:4,required:true,
			onChange:function(newValue, oldValue){
				$('#rmb').numberbox('setValue', $('#originalCurrency').numberbox('getValue') * newValue);
			}"/>
		</td>
		<th>对美元汇率</th>
		<td>
			<input type="text" id="exchangeRateUS" name="exchangeRateUS" value="${agentImport.exchangeRateUS }" class="easyui-numberbox" data-options="min:0,precision:4,required:true,
			onChange:function(newValue, oldValue){
				$('#dollar').numberbox('setValue', $('#originalCurrency').numberbox('getValue') * newValue);
			}"/>
		</td>
		<th>原币金额</th>
		<td>
			<input type="text" id="originalCurrency" name="originalCurrency" value="${agentImport.originalCurrency }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算',
			onChange:function(newValue, oldValue){
				$('#rmb').numberbox('setValue', newValue * $('#exchangeRateSelf').numberbox('getValue'));
				$('#dollar').numberbox('setValue', newValue * $('#exchangeRateUS').numberbox('getValue'));
			}"/>
		</td>
	</tr>
	<tr>
		<td colspan="2"></td>
		<th>人民币金额</th>
		<td>
			<input type="text" id="rmb" name="rmb" value="${agentImport.rmb }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算'"/>
		</td>
		<th>美元金额</th>
		<td>
			<input type="text" id="dollar" name="dollar" value="${agentImport.dollar }" class="easyui-numberbox" data-options="min:0,precision:2,required:true,prompt:'添加商品自动计算'"/>
		</td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td rowspan="5">客户信息</td>
		<td colspan="4" align="center" height="24px">
			<input type="radio" name="customerType" value="0" style="margin-top:-2px" <c:if test="${agentImport.customerType eq 0 }">checked</c:if> onclick="chooseCustomerType(this)"/>新开发客户
		</td>
		<td colspan="4" align="center">
			<input type="radio" name="customerType" value="1" style="margin-top:-2px" <c:if test="${agentImport.customerType eq 1 }">checked</c:if> onclick="chooseCustomerType(this)"/>长单老客户
		</td>
	</tr>
	<tr>
		<th>客户名称</th>
		<td colspan="3">
			<input type="text" id="newCustomer" value="${agentImport.customerType eq 0 ? agentImport.customerName : '' }" class="easyui-validatebox" data-options="validType:['chinese','minLength[4]']" onblur="generateCode('N', $(this).val());"/>
			<input type="hidden" id="customerName" name="customerName"/>
		</td>
		<th>客户名称</th>
		<td colspan="3">
			<input type="text" id="oldCustomer" value="${agentImport.customerType eq 1 ? agentImport.customerId : '' }" class="easyui-combobox"/>
			<input type="hidden" id="customerId" name="customerId" value="${agentImport.customerId }"/>
		</td>
	</tr>
	<tr>
		<th>注册时间</th>
		<td colspan="3">
			<input type="text" id="registerDate" name="registerDate" value="<fmt:formatDate value="${agentImport.registerDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
		<th>授信额度</th>
		<td colspan="3">
			<input type="text" id="lineCredit" name="lineCredit" value="${agentImport.lineCredit }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
			<span id="lineCreditRemind" style="color: red;"></span>
		</td>
	</tr>
	<tr>
		<th>注册资本</th>
		<td colspan="3">
			<input type="text" id="registerCapital" name="registerCapital" value="${agentImport.registerCapital }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
		<th>目前敞口</th>
		<td colspan="3">
			<input type="text" id="currentOpen" name="currentOpen" value="${agentImport.currentOpen }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<td colspan="4">
		</td>
		<th>已超期金额</th>
		<td colspan="3">
			<input type="text" id="hasBeyond" name="hasBeyond" value="${agentImport.hasBeyond }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
		</td>
	</tr>
	<tr>
		<td rowspan="10">合同信息及关键条款</td>
		<td colspan="8" align="center" height="24px" style="font-weight: bold;">外合同或进口合同信息</td>
	</tr>
	<tr>
		<th>合同名称</th>
		<td>
			<input type="text" name="foreignContractName" value="${agentImport.foreignContractName }" class="easyui-combobox" data-options="
				panelHeight:'auto',
				data:[{text:'双方合同',value:'双方合同'},{text:'三方合同',value:'三方合同'}],
				valueField:'value',
				textField:'text'
			"/>
		</td>
		<th>编号</th>
		<td>
			<input type="text" id="foreignContractNo" name="foreignContractNo" value="${agentImport.foreignContractNo }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th>版本来源</th>
		<td>
			<input type="radio" name="foreignVersionSource" value="0" style="margin-top:-2px" <c:if test="${agentImport.foreignVersionSource eq 0 }">checked</c:if>/>外商版本
			<input type="radio" name="foreignVersionSource" value="1" style="margin-top:-2px" <c:if test="${agentImport.foreignVersionSource eq 1 }">checked</c:if>/>我司版本
		</td>
		<th>外商名称</th>
		<td>
			<input type="text" id="foreignId" name="foreignId" value="${agentImport.foreignId }" class="easyui-combobox"/>
		</td>
	</tr>
	<tr>
		<td colspan="8" style="padding: 0px;">
			<div id="agentImportGoodsToolbar" style="padding:5px;height:auto">
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
			<table id="agentImportGoodsTB" data-options="onClickRow: onClickRow"></table>
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
			<span id="paymentMethod"></span>
			<span style="padding-left: 20px;">备注：<input type="text" name="paymentMethodRemark" value="${agentImport.paymentMethodRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>价格条款</th>
		<td colspan="3">
			<input type="text" name="priceTerms" value="${agentImport.priceTerms }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>装期</th>
		<td colspan="3">
			<input type="text" name="shipmentDate" value="<fmt:formatDate value="${agentImport.shipmentDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
		<th>争议管辖</th>
		<td colspan="3">
			<input type="text" name="dispute" value="${agentImport.dispute }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center" height="24px" style="font-weight: bold;">内合同或代理进口合同信息</td>
	</tr>
	<tr>
		<th>合同名称</th>
		<td colspan="3">
			<input type="text" name="inlandContractName" value="代理进口合同" class="easyui-validatebox"/>
		</td>
		<th>编号</th>
		<td colspan="3">
			<input type="text" id="contractNo" name="contractNo" value="${agentImport.contractNo }" class="easyui-validatebox" data-options="required:true,prompt:'输入客户名称自动生成'"/>
		</td>
	</tr>
	<tr>
		<th>版本来源</th>
		<td colspan="3">
			<input type="radio" name="inlandVersionSource" value="0" style="margin-top:-2px" <c:if test="${agentImport.inlandVersionSource eq 0 }">checked</c:if>/>我司版本
			<input type="radio" name="inlandVersionSource" value="1" style="margin-top:-2px" <c:if test="${agentImport.inlandVersionSource eq 1 }">checked</c:if>/>客户版本
		</td>
		<th>项目要求</th>
		<td colspan="3">
			<span id="projectClaim"></span>
			<span style="padding-left: 20px;">备注：<input type="text" name="projectClaimRemark" value="${agentImport.projectClaimRemark }" class="easyui-validatebox"/></span>
		</td>
	</tr>
	<tr>
		<th>代理费</th>
		<td colspan="3">
			<input type="text" id="agencyFee" name="agencyFee" value="${agentImport.agencyFee }" class="easyui-validatebox"/>
			<span style="padding-left: 20px;">备注：<input type="text" name="agencyFeeRemark" value="${agentImport.agencyFeeRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>保证金</th>
		<td colspan="3">
			<input type="text" id="margin" name="margin" value="${agentImport.margin }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>担保措施</th>
		<td colspan="2">
			<input type="radio" name="assurance" value="0" style="margin-top:-2px" <c:if test="${agentImport.assurance eq 0 }">checked</c:if>/>有
			<input type="radio" name="assurance" value="1" style="margin-top:-2px" <c:if test="${agentImport.assurance eq 1 }">checked</c:if>/>无
			<span style="padding-left: 20px;">备注：<input type="text" name="assuranceRemark" value="${agentImport.assuranceRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>融资租赁</th>
		<td colspan="2">
			<input type="radio" name="financeLease" value="0" style="margin-top:-2px" <c:if test="${agentImport.financeLease eq 0 }">checked</c:if>/>有
			<input type="radio" name="financeLease" value="1" style="margin-top:-2px" <c:if test="${agentImport.financeLease eq 1 }">checked</c:if>/>无
			<span style="padding-left: 20px;">备注：<input type="text" name="financeLeaseRemark" value="${agentImport.financeLeaseRemark }" class="easyui-validatebox"/></span>
		</td>
		<th>诉讼管辖</th>
		<td>
			<input type="text" name="litigation" value="${agentImport.litigation }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<td rowspan="2">物流信息</td>
		<td colspan="8" height="24px">
			<input type="radio" name="freight" value="我司货代" style="margin-top:-2px" <c:if test="${agentImport.freight eq '我司货代' }">checked</c:if>/>我司货代
			<input type="radio" name="freight" value="指定货代" style="margin-top:-2px" <c:if test="${agentImport.freight eq '指定货代' }">checked</c:if>/>指定货代
			<span style="padding-left: 20px;">备注：<input type="text" name="freightRemark" value="${agentImport.freightRemark }" class="easyui-validatebox"/></span>
		</td>
	</tr>
	<tr>
		<th>备注</th>
		<td colspan="7">
			<textarea name="logisticsRemark" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${agentImport.logisticsRemark }</textarea>
		</td>
	</tr>
</table>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty agentImport.createDate ? now : agentImport.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty agentImport.createrName ? sessionScope.user.name : agentImport.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${agentImport.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<input type="hidden" name="agentImportGoodsJson" id="agentImportGoodsJson"/>
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
		    		url : '${ctx}/agent/agentImport/uniqueNo/${empty agentImport.id ? 0 : agentImport.id}',
		    		data : {contractNo:$('#contractNo').val(),foreignContractNo:$('#foreignContractNo').val()},
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '内或外合同编号重复！', 'info');
		    			}
		    		}
		    	});
		    	$('#registerDate').combo('enable');//置灰保存传不到后台
	    		$('#registerCapital').numberbox('enable');
	    		$('#lineCredit').numberbox("enable");
	    		$('#currentOpen').numberbox("enable");
	    		$('#hasBeyond').numberbox("enable");
	    		$('#customerName').val($('#newCustomer').val());
	    	}
	    	if(!isValid){parent.$.messager.progress('close');};
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	if("update"=="${action}"){
	    		if($('input:radio[name="customerType"]:checked').val() == 0){//恢复置灰
		    		$('#registerDate').combo('enable');
		    		$('#registerCapital').numberbox('enable');
		    		$('#lineCredit').numberbox("disable");
		    		$('#currentOpen').numberbox("disable");
		    		$('#hasBeyond').numberbox("disable");
	    		}else{
	    			$('#registerDate').combo('disable');
		    		$('#registerCapital').numberbox('disable');
		    		$('#lineCredit').numberbox("enable");
		    		$('#currentOpen').numberbox("enable");
		    		$('#hasBeyond').numberbox("enable");
	    		}
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
	
	//商品类别
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/goodsType',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${agentImport.goodsType }'.split(",");
			$.each(data, function(index, value){
				$("#goodsType").append("<input type='checkbox' name='goodsType'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:30px;'/>" + value.name);
			});
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
	
	window.setTimeout(function(){
		if('${agentImport.customerType }' == 0){
			$('#oldCustomer').combobox('disable');
			$('#lineCredit').numberbox('disable');
			$('#currentOpen').numberbox('disable');
			$('#hasBeyond').numberbox('disable');
			
			$('#newCustomer').validatebox({required: true});
		}else{
			$('#newCustomer').attr('disabled', true);
			$('#registerDate').combo('disable');
			$('#registerCapital').numberbox('disable');
			
			$('#oldCustomer').combobox({required: true});
		}
	}, 1000); //需要页面元素进入一定的状态才能使用，所以延迟1秒执行
	
	//长单老客户
	$('#oldCustomer').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/1',
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			$('#customerId').val(record.id);
			//授信额度
			var lineCredit = 0;
			var nowDate = formatterDate("yyyy-MM-dd");
			var aff = record.affiCustomerInfo;
			for(var i = 0; i < aff.length; i++){
				if (nowDate >= aff[i].checkStartTime && nowDate <= aff[i].checkEndTime) {
					lineCredit += Number(aff[i].creditLine);
				}
			}
			$('#lineCredit').numberbox('setValue', lineCredit);
			//授信额度超过5千万，特别提示
			if(lineCredit > 50000000){
				$('#lineCreditRemind').append("授信额度超过5千万，请谨慎审批！");
			}
			$.ajax({
	    		url : '${ctx}/agent/agentImport/currentOpen/' + record.id,
	    		type : 'get',
	    		async : false,
	    		cache : false,
	    		dataType : 'json',
	    		success : function(data) {
	    			$('#currentOpen').numberbox('setValue', data);
	    		}
	    	});//目前敞口
			$.ajax({
	    		url : '${ctx}/agent/agentImport/hasBeyond/' + record.id,
	    		type : 'get',
	    		async : false,
	    		cache : false,
	    		dataType : 'json',
	    		success : function(data) {
	    			$('#hasBeyond').numberbox('setValue', data);
	    		}
	    	});//已超期金额
	    	generateCode('L', record.customerCode);//编码合同号
		}
	});
	
	//外商
	$('#foreignId').combobox({
		method:'GET',
		url : '${ctx}/baseinfo/affiliates/getCompany/4',
		required : true,
		valueField : 'id',
		textField : 'customerName',
		onSelect:function(record){
			
		}
	});
	
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
							var ed = $('#agentImportGoodsTB').datagrid('getEditor', {index:editIndex,field:'goodsCode'});
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
	    toolbar:'#agentImportGoodsToolbar',
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
			var array = '${agentImport.paymentMethod }'.split(",");
			$.each(data, function(index, value){
				$("#paymentMethod").append("<input type='checkbox' name='paymentMethod'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	});
	
	//项目要求
	$.ajax({
		url : '${ctx}/system/dict/getDictByCode/projectClaim',
		type : 'get',
		cache : false,
		dataType : 'json',
		success : function(data) {
			var array = '${agentImport.projectClaim }'.split(",");
			$.each(data, function(index, value){
				$("#projectClaim").append("<input type='checkbox' name='projectClaim'" + (array.indexOf(value.name) > -1 ? " checked " : ' ') + "value='" + value.name + "' style='margin-top:1px;margin-left:10px;'/>" + value.name);
			});
		}
	});
	
});

function chooseCustomerType(customerType){
	if($(customerType).val() == 0){
		$('#oldCustomer').combobox({
			disabled: true,
			required: false
		});
		$('#oldCustomer').combobox('clear');
		$('#lineCredit').numberbox('disable');
		$('#lineCredit').numberbox('clear');
		$('#currentOpen').numberbox('disable');
		$('#currentOpen').numberbox('clear');
		$('#hasBeyond').numberbox('disable');
		$('#hasBeyond').numberbox('clear');
		
		$('#newCustomer').validatebox({required: true});
		$('#newCustomer').removeAttr("disabled");
		$('#registerDate').combo('enable');
		$('#registerCapital').numberbox('enable');
		$('#customerId').val(null);
	}else{
		$('#newCustomer').attr("disabled", true);
		$('#newCustomer').val(null);
		$('#newCustomer').validatebox({required: false});
		$('#registerDate').combo('clear');
		$('#registerDate').combo('disable');
		$('#registerCapital').numberbox('disable');
		$('#registerCapital').numberbox('clear');
		
		$('#oldCustomer').combobox({
			disabled: false,
			required: true
		});
		$('#oldCustomer').combobox('clear');
		$('#lineCredit').numberbox("enable");
		$('#currentOpen').numberbox("enable");
		$('#hasBeyond').numberbox("enable");
	}
}

var editIndex = undefined;
function endEditing(){
	if(editIndex == undefined){return true}
	if($('#agentImportGoodsTB').datagrid('validateRow', editIndex)){
		$('#agentImportGoodsTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if(editIndex != index){
		if (endEditing()){
			$('#agentImportGoodsTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#agentImportGoodsTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if(endEditing()){
		$('#agentImportGoodsTB').datagrid('appendRow',{});
		editIndex = $('#agentImportGoodsTB').datagrid('getRows').length-1;
		$('#agentImportGoodsTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if(editIndex == undefined){return}
	$('#agentImportGoodsTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if(endEditing()){
		var rows = $('#agentImportGoodsTB').datagrid('getRows');
		if(rows.length == 0){
			parent.$.messager.confirm('提示', '空商品列表，请添加商品！', function(data) {
				if (data) {
					$('#agentImportGoodsTB').datagrid('appendRow',{});
					$('#agentImportGoodsTB').datagrid('selectRow', 0).datagrid('beginEdit',0);
					editIndex = 0;
				}
			});
			return false;
		}
		$('#agentImportGoodsTB').datagrid('acceptChanges');
		$('#agentImportGoodsJson').val(JSON.stringify(rows));
		var array = [];
		for(var i = 0; i < rows.length; i++){
			var goods = rows[i].goodsCategory + "/" + rows[i].specification;
			if(array.indexOf(goods) > -1){
				$.messager.alert('提示', '“' + goods + '”商品重复，请整合！', 'info');
				$('#agentImportGoodsTB').datagrid('selectRow', i).datagrid('beginEdit', i);
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
	$('#agentImportGoodsTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#agentImportGoodsTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}

function changeChildTB(rows){
	var originalCurrency = 0;
	for(var i = 0; i < rows.length; i++){
		originalCurrency += Number(rows[i].amount * rows[i].price);
	}
	$('#originalCurrency').numberbox('setValue', originalCurrency);//原币金额
}

//构造合同编号
function generateCode(customerType, customer) {
	if($('#newCustomer').validatebox('isValid')){
		$.ajax({
			url : '${ctx}/agent/agentImport/generateCode',
			data : {customerType:customerType,customer:customer,documentType:'合同'},
			type : 'get',
			async : false,
			cache : false,
			success : function(data) {
				$('#contractNo').val(data);
			}
		});
	}
}
</script>
</body>
</html>