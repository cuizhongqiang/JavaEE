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
<div class="easyui-tabs" data-options="border:false">
	<div data-options="title: '物流企业考察信息', iconCls: false, refreshable: false" >
		<fieldset class="fieldsetClass" >
		<legend>基本信息</legend>
		<table width="100%" style="margin: 0px;" class="tableClass" >
			<tr>
				<th>评审编号</th>
				<td>
					${logisticsCheck.checkId }
				</td>
				<th>公司名称</th>
				<td>
					${logisticsCheck.companyName }
				</td>
				<th>公司地址</th>
				<td>
					${logisticsCheck.companyAddress }
				</td>
			</tr>
			<tr>
				<th>联系人</th>
				<td>
					${logisticsCheck.contactPerson }
				</td>
				<th>联系电话</th>
				<td>
					${logisticsCheck.phoneContact }
				</td>
				<th>传真</th>
				<td>
				${logisticsCheck.fax }
				</td>
			</tr>
			<tr>
				<th>申请部门</th>
				<td>
					${logisticsCheck.applyDepart }
				</td>
				<th>申请人</th>
				<td>
					${logisticsCheck.applyPerson }
				</td>
				<th>申请时间</th>
				<td>
					<fmt:formatDate value="${logisticsCheck.applyDate }" />
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
					${logisticsCheck.companyProperty }
				</td>
				<th>资金币种</th>
				<td>
					${logisticsCheck.capitalCurrency }
				</td>
				<th>注册资金</th>
				<td colspan="2">
					${logisticsCheck.registeredCapital }万元
				</td>
			</tr>
			<tr>	
				<th>营业年限</th>
				<th>成立日期</th>
				<td>
					<fmt:formatDate value="${logisticsCheck.businessLifeFound }" />
				</td>
				<th>营业期限</th>
				<td>
				${logisticsCheck.businessLifeDealline }年
				</td>
				<th>已营业年限</th>
				<td>
					${logisticsCheck.businessLife }年
				</td>
			</tr>
			<tr>
				<th>公司股东</th>
				<th>法定代表人</th>
				<td>
					${logisticsCheck.corporate }
				</td>
				<th>实际控制人</th>
				<td colspan="3">
					${logisticsCheck.actualControlPerson }
				</td>
			</tr>
			<tr>
				<th rowspan="2">运输资质</th>
				<th>是否有《道路/水路运输资格》</th>
				<td>
					${logisticsCheck.haveTransportAble }
				</td>
				<th>自有车辆</th>
				<td>
					<c:choose>
						<c:when test="${empty logisticsCheck.haveCar }">
							&nbsp;
						</c:when>
						<c:otherwise>
							${logisticsCheck.haveCar }辆
						</c:otherwise>
					</c:choose>
				</td>
				<th>自有船舶</th>
				<td>
					<c:choose>
						<c:when test="${empty logisticsCheck.havaShip }">
							&nbsp;
						</c:when>
						<c:otherwise>
							${logisticsCheck.havaShip }艘
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th>其中海关监管车</th>
				<td>
					<c:choose>
						<c:when test="${empty logisticsCheck.customsControlVehicles }">
							&nbsp;
						</c:when>
						<c:otherwise>
							${logisticsCheck.customsControlVehicles }辆
						</c:otherwise>
					</c:choose>
				</td>
				<th>其他运输方案</th>
				<td colspan="3">
					${logisticsCheck.extraTransportProgram }
				</td>
			</tr>
			<tr>
				<th>在港人数</th>
				<th>具有报关、报检员资格的人数</th>
				<td colspan="5">
					${logisticsCheck.inPostPerson }人
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
					${logisticsCheck.expertProject }
				</td>
			</tr>
			<tr>
				<th>业绩</th>
				<th>上一营业年度报关票数</th>
				<td>
					${logisticsCheck.lastVotes }票
				</td>
				<th>营业收入</th>
				<td>
					${logisticsCheck.income }万
				</td>
			</tr>
			<tr>
				<th>信用</th>
				<th>海关、商检监管信息：信用信息</th>
				<td>
					${logisticsCheck.creditInformation }
				</td>
				<th>海关、商检监管信息：处罚信息</th>
				<td>
					${logisticsCheck.punishmentInformation }
				</td>
				
			</tr>
			<tr>
				<th>雇佣总人数</th>
				<td>
					${logisticsCheck.totalWorker }人
				</td>
				<th>雇佣详情</th>
				<td>
					操作员${logisticsCheck.workerNumber }人
				</td>
				<td>
					管理员${logisticsCheck.adminNumber }人
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
		<legend>手续效力</legend>
		<table width="100%" style="margin: 0px;" class="tableClass">
			<tr>
				<th>仓储企业已提供的资料</th>
				<td id="enterpriseWarehouseData">
				</td>
				<th>合同是否已签订</th>
				<td>
				${logisticsCheck.isContract }
				</td>
				<th>哪方面版本</th>
				<td>
					${logisticsCheck.contractCategory }
				</td>
			</tr>
			<tr>
				<th>是否当面办理</th>
				<td>
					${logisticsCheck.isFaceToFace }
				</td>
				<th>现场考察及班里人意见</th>
				<td colspan="3">
					${logisticsCheck.sitePersonnel }
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldsetClass" >
		<legend>系统信息</legend>
		<table width="100%" style="margin: 0px;" class="tableClass">
			<tr>
				<th width="15%">制单日期</th>
				<td width="15%">
				<fmt:formatDate value="${logisticsCheck.createDate }" pattern="yyyy-MM-dd" />
				</td>
				<th width="15%">制单人</th>
				<td width="20%">${logisticsCheck.createrName }</td>
				<th width="15%">最近修改时间</th>
				<td width="20%"><fmt:formatDate value="${logisticsCheck.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>
		</fieldset>
	</div>	
	<div data-options="title: '附件', iconCls: false, refreshable: false">
		<input id="accParentEntity" type="hidden" value="<%=LogisticsCheck.class.getName().replace(".","_") %>" />
		<input id="accParentId" type="hidden" value="${logisticsCheck.id}" />
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>	
</div>	
<script type="text/javascript">
$(function() {
	$.ajax({
		url : "${ctx}/system/dict/getAllName/${logisticsCheck.enterpriseWarehouseData }/enterpriseWarehouseData",
		type : 'get',
		cache : false,
		success : function(data) {
			$('#enterpriseWarehouseData').text(data);
		}
	});
});
	
</script>
</body>
</html>