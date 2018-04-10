<%@page import="com.cbmie.genMac.logistics.entity.LogisticsCheck"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<form id="mainform"  action="${ctx}/logistics/logisticsCheck/${action}" method="post">
	<div id="mainDiv" class="" data-options="border:false">
		<div data-options="title: '物流企业考察信息', iconCls: false, refreshable: false" >
		<c:choose>
			<c:when test="${empty logisticsCheck.processInstanceId }">
			<fieldset class="fieldsetClass" >
			<legend>基本信息</legend>
			<table width="100%" style="margin: 0px;" class="tableClass" >
				<tr>
					<th>评审编号</th>
					<td>
						<input id="checkId" name="checkId" type="text" value="${logisticsCheck.checkId }" class="easyui-validatebox"  data-options="required:true"/>
						<input name="id" type="hidden"  value="${logisticsCheck.id }" />
					</td>
					<th>公司名称</th>
					<td>
						<input id="companyName" name="companyName" type="text" value="${logisticsCheck.companyName }" class="easyui-validatebox"  data-options="required:true"/>
					</td>
					<th>公司地址</th>
					<td>
						<input id="companyAddress" name="companyAddress" type="text" value="${logisticsCheck.companyAddress }" class="easyui-validatebox"  data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>联系人</th>
					<td>
						<input id="contactPerson" name="contactPerson" type="text" value="${logisticsCheck.contactPerson }" class="easyui-validatebox"  data-options="required:true"/>
					</td>
					<th>联系电话</th>
					<td>
						<input id="phoneContact" name="phoneContact" type="text" value="${logisticsCheck.phoneContact }" class="easyui-validatebox"  validtype="telOrMobile" data-options="required:true"/>
					</td>
					<th>传真</th>
					<td>
						<input id="fax" name="fax" type="text" value="${logisticsCheck.fax }" class="easyui-validatebox"  validtype="fax" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>申请部门</th>
					<td>
						<input id="applyDepart" name="applyDepart" type="text" value="${logisticsCheck.applyDepart }" class="easyui-validatebox" data-options="required:true"/>
					</td>
					<th>申请人</th>
					<td>
						<input id="applyPerson" name="applyPerson" type="text" value="${logisticsCheck.applyPerson }" class="easyui-validatebox" data-options="required:true"/>
					</td>
					<th>申请时间</th>
					<td>
						<input id="applyDate" name="applyDate"   type="text" value="<fmt:formatDate value="${logisticsCheck.applyDate }" />" datefmt="yyyy-MM-dd" 
						class="easyui-my97" data-options="required:true"/>
					</td>
				</tr>
			</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
			<legend>企业实力</legend>
			<table width="100%" style="margin: 0px;" class="tableClass" >
				<tr>
					<th>公司性质</th>
					<td>
						<input id="companyProperty" name="companyProperty" type="text" value="${logisticsCheck.companyProperty }" class="easyui-validatebox" />
					</td>
					<th>资金币种</th>
					<td>
						<input id="capitalCurrency" name="capitalCurrency" type="text" value="${logisticsCheck.capitalCurrency }" class="easyui-validatebox" />
					</td>
					<th>注册资金</th>
					<td colspan="2">
						<input id="registeredCapital" name="registeredCapital" type="text" value="${logisticsCheck.registeredCapital }"     class="easyui-numberbox" data-options="required:true,precision:'2'" />万元
					</td>
				</tr>
				<tr>	
					<th>营业年限</th>
					<th>成立日期</th>
					<td>
						<input id="businessLifeFound" name="businessLifeFound"   type="text" value="<fmt:formatDate value="${logisticsCheck.businessLifeFound }" />" datefmt="yyyy-MM-dd" 
						class="easyui-my97" data-options="required:true"/>
					</td>
					<th>营业期限</th>
					<td>
						<input id="businessLifeDealline" name="businessLifeDealline" type="text" value="${logisticsCheck.businessLifeDealline }" class="easyui-numberbox" data-options="required:true"/>年
					</td>
					<th>已营业年限</th>
					<td>
						<input id="businessLife" name="businessLife" type="text" value="${logisticsCheck.businessLife }" class="easyui-numberbox" data-options="required:true"/>年
					</td>
				</tr>
				<tr>
					<th>公司股东</th>
					<th>法定代表人</th>
					<td>
						<input id="corporate" name="corporate" type="text" value="${logisticsCheck.corporate }" class="easyui-validatebox" data-options="required:true"/>
					</td>
					<th>实际控制人</th>
					<td colspan="3">
						<input id="actualControlPerson" name="actualControlPerson" type="text" value="${logisticsCheck.actualControlPerson }" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th rowspan="2">运输资质</th>
					<th>是否有《道路/水路运输资格》</th>
					<td>
						<input id="haveTransportAble" name="haveTransportAble" type="text" value="${logisticsCheck.haveTransportAble }"  class="easyui-validatebox" />
					</td>
					<th>自有车辆</th>
					<td>
						<input id="haveCar" name="haveCar" type="text" value="${logisticsCheck.haveCar }" validtype="integer" class="easyui-numberbox" />辆
					</td>
					<th>自有船舶</th>
					<td>
						<input id="havaShip" name="havaShip" type="text" value="${logisticsCheck.havaShip }"  validtype="integer" class="easyui-numberbox" />艘
					</td>
				</tr>
				<tr>
					<th>其中海关监管车</th>
					<td>
						<input id="customsControlVehicles" name="customsControlVehicles" type="text" value="${logisticsCheck.customsControlVehicles }" validtype="integer" class="easyui-numberbox" />辆
					</td>
					<th>其他运输方案</th>
					<td colspan="3">
						<input id="extraTransportProgram" name="extraTransportProgram" type="text" value="${logisticsCheck.extraTransportProgram }" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th>在港人数</th>
					<th>具有报关、报检员资格的人数</th>
					<td colspan="5">
						<input id="inPostPerson" name="inPostPerson" type="text" value="${logisticsCheck.inPostPerson }"  class="easyui-numberbox"  validtype="integer"   data-options="required:true"/>人
					</td>
				</tr>
			</table>	
			</fieldset>
			<fieldset class="fieldsetClass" >
			<legend>专业能力</legend>
			<table width="100%" style="margin: 0px;" class="tableClass">	
				<tr>
					<th>专长</th>
					<th>擅长项目、产品、进口出口清关操作</th>
					<td colspan="3">
						<input id="expertProject" name="expertProject" type="text" value="${logisticsCheck.expertProject }" class="easyui-validatebox" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>业绩</th>
					<th>上一营业年度报关票数</th>
					<td>
						<input id="lastVotes" name="lastVotes" type="text" value="${logisticsCheck.lastVotes }"  class="easyui-numberbox" data-options="required:true" />票
					</td>
					<th>营业收入</th>
					<td>
						<input id="income" name="income" type="text" value="${logisticsCheck.income }"  class="easyui-numberbox" data-options="required:true,precision:'2'" />万
					</td>
				</tr>
				<tr>
					<th>信用</th>
					<th>海关、商检监管信息：信用信息</th>
					<td>
						<input id="creditInformation" name="creditInformation" type="text" value="${logisticsCheck.creditInformation }" class="easyui-validatebox" />
					</td>
					<th>海关、商检监管信息：处罚信息</th>
					<td>
						<input id="punishmentInformation" name="punishmentInformation" type="text" value="${logisticsCheck.punishmentInformation }" class="easyui-validatebox" />
					</td>
					
				</tr>
				<tr>
					<th>雇佣总人数</th>
					<td>
						<input id="totalWorker" name="totalWorker" type="text" value="${logisticsCheck.totalWorker }"  onchange="calculate()" validtype="integer" data-options="required:true" class="easyui-numberbox"/>人
					</td>
					<th>雇佣详情</th>
					<td>
						操作员<input id="workerNumber" name="workerNumber" type="text" value="${logisticsCheck.workerNumber }"  onchange="calculate()" validtype="integer" data-options="required:true" class="easyui-numberbox"/>人
					</td>
					<td>
						管理员<input id="adminNumber" name="adminNumber" type="text" value="${logisticsCheck.adminNumber }" onchange="calculate()" validtype="integer" data-options="required:true" class="easyui-numberbox"/>人
					</td>
				</tr>
			</table>
			</fieldset>
			<fieldset class="fieldsetClass" >
			<legend>手续效力</legend>
			<table width="100%" style="margin: 0px;" class="tableClass">
				<tr>
					<th>仓储企业已提供的资料</th>
					<td>
						<input id="enterpriseWarehouseData" name="enterpriseWarehouseData" type="text" value="${logisticsCheck.enterpriseWarehouseData }" class="easyui-validatebox"/>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="$('#enterpriseWarehouseData').combobox('clear');"></a>
					</td>
					<th>合同是否已签订</th>
					<td>
						<input id="isContract" name="isContract" type="text" value="${logisticsCheck.isContract }" class="easyui-validatebox" />
					</td>
					<th>哪方面版本</th>
					<td>
						<input id="contractCategory" name="contractCategory" type="text" value="${logisticsCheck.contractCategory }" class="easyui-validatebox"/>
					</td>
				</tr>
				<tr>
					<th>是否当面办理</th>
					<td>
						<input id="isFaceToFace" name="isFaceToFace" type="text" value="${logisticsCheck.isFaceToFace }" class="easyui-validatebox" />
					</td>
					<th>现场考察及班里人意见</th>
					<td colspan="3" >
						<input id="sitePersonnel" name="sitePersonnel" type="text" value="${logisticsCheck.sitePersonnel }" class="easyui-validatebox"/>
					</td>
				</tr>
			</table>
			</fieldset>
			</c:when>
			<c:otherwise>
			<fieldset class="fieldsetClass" >
			<legend>基本信息</legend>
			<table width="100%" style="margin: 0px;" class="tableClass" >
				<tr>
					<th>评审编号</th>
					<td>
						<input id="checkId" name="checkId" type="text" value="${logisticsCheck.checkId }" class="easyui-validatebox"  data-options="required:true" readonly="true"/>
						<input name="id" type="hidden"  value="${logisticsCheck.id }" />
					</td>
					<th>公司名称</th>
					<td>
						<input id="companyName" name="companyName" type="text" value="${logisticsCheck.companyName }" class="easyui-validatebox"  data-options="required:true" readonly="true"/>
					</td>
					<th>公司地址</th>
					<td>
						<input id="companyAddress" name="companyAddress" type="text" value="${logisticsCheck.companyAddress }" class="easyui-validatebox"  data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th>联系人</th>
					<td>
						<input id="contactPerson" name="contactPerson" type="text" value="${logisticsCheck.contactPerson }" class="easyui-validatebox"  data-options="required:true"/>
					</td>
					<th>联系电话</th>
					<td>
						<input id="phoneContact" name="phoneContact" type="text" value="${logisticsCheck.phoneContact }" class="easyui-validatebox"  validtype="telOrMobile" data-options="required:true"/>
					</td>
					<th>传真</th>
					<td>
						<input id="fax" name="fax" type="text" value="${logisticsCheck.fax }" class="easyui-validatebox"  validtype="fax" data-options="required:true"/>
					</td>
				</tr>
			</table>
			</fieldset>
			</c:otherwise>
			</c:choose>
			<fieldset class="fieldsetClass" >
			<legend>系统信息</legend>
			<table width="100%" style="margin: 0px;" class="tableClass">
				<tr>
					<th width="15%">制单日期</th>
					<td width="15%">
					<jsp:useBean id="now" class="java.util.Date" scope="page"/>
					<fmt:formatDate value="${empty logisticsCheck.createDate ? now : logisticsCheck.createDate}" pattern="yyyy-MM-dd" />
					</td>
					<th width="15%">制单人</th>
					<td width="20%">${empty logisticsCheck.createrName ? sessionScope.user.name : logisticsCheck.createrName }</td>
					<th width="15%">最近修改时间</th>
					<td width="20%"><fmt:formatDate value="${logisticsCheck.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</table>
			</fieldset>
		</div>	
		<div data-options="title: '附件信息', iconCls: false, refreshable: false">
			<input id="accParentEntity" type="hidden" value="<%=LogisticsCheck.class.getName().replace(".","_") %>" />
			<input id="accParentId" type="hidden" value="${logisticsCheck.id}" />
			<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
		</div>
	</div>
	<input type="hidden" name="apply" id="apply" value="false"/>
	</form>
<script type="text/javascript">

	$(function() {
		$('#mainform').form({
			onSubmit : function() {
				parent.$.messager.progress({  
			        title : '提示',  
			        text : '数据处理中，请稍后....'  
			    });
				var isValid = $(this).form('validate');
				if(!isValid){parent.$.messager.progress('close');};
				return isValid; // 返回false终止表单提交
			} ,
			success : function(data) {
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

		
		//公司性质
		$('#companyProperty').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/QYXZ',
			valueField : 'name',
			textField : 'name'
		});

		//币种
		$('#capitalCurrency').combobox({
			method:'GET',
			required:true,
			url:'${ctx}/baseinfo/exchangerate/getNewExchangeRate',
			valueField : 'currency',
			textField : 'currency'
		});
		
		//是否有《道路/水路运输资格》
		$('#haveTransportAble').combobox({
			panelHeight : 'auto',
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
		
		//仓储企业已提供的资料 
		$('#enterpriseWarehouseData').combobox({
			method:'GET',
			url : '${ctx}/system/dict/getDictByCode/enterpriseWarehouseData',
		    textField : 'name',
		    editable:false,
		    multiple:true,
		    width:250,
		    multiline:true,
		    required : true,
			panelHeight:'auto',
		    prompt: '此选项可以多选，点击X重置选择',
		    onLoadSuccess: function () { //加载完成后,设置选中第一项 
		        var val = $(this).combobox("getData"); 
		        $(this).combobox("clear");
		   		var curValue = new Array();
		   		curValue = this.value.split(',');
		   		for(var j=0;j<curValue.length;j++){
		   		 	for(var i = 0;i<val.length;i++ ){ 
			            if (val[i].value==curValue[j]) { 
			                $(this).combobox("select",curValue[j]); 
			            } 
			        } 
		   		}
		        
		    } ,
		    onHidePanel:function(){
		    }
		});
		
		//合同是否已签订
		$('#isContract').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
		//哪方面版本
		$('#contractCategory').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/contractCategory',
			valueField : 'name',
			textField : 'name'
		});
		
		//是否当面办理
		$('#isFaceToFace').combobox({
			panelHeight : 'auto',
			required : true,
			url : '${ctx}/system/dict/getDictByCode/YESNO',
			valueField : 'name',
			textField : 'name'
		});
		
	});
	
	function calculate(){
		var totalWorker = $('#totalWorker').val();
		var workerNumber = $('#workerNumber').val();
		var adminNumber = $('#adminNumber').val();
		if(parseInt(workerNumber)+parseInt(adminNumber)>parseInt(totalWorker)){
			$.messager.alert('提示','操作员与管理员人数超过总人数，请核实！','info');
			 $('#workerNumber').val("");
			 $('#adminNumber').val("");
		}
	}
</script>
</body>
</html>