<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.AffiBaseInfo"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.AffiBankInfo"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.AffiCustomerInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/affiliates/${action}" method="post">
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '单位基本信息', iconCls: false, refreshable: false">
			<table width="98%" class="tableClass">
				<tr>
					<th width="20%">客户编码</th>
					<td width="30%">${affiBaseInfo.customerCode }</td>
					<th width="20%">客户类型</th>
					<td width="30%" id="affiBaseCusType">
					</td>
				</tr>
				<tr>
					<th>客户名称</th>
					<td>${affiBaseInfo.customerName }</td>
					<th>客户英文名</th>
					<td>${affiBaseInfo.customerEnName }</td>
				</tr>
				<tr>
					<th>注册时间</th>
					<td>
						<fmt:formatDate value="${affiBaseInfo.registerDate }" />
					</td>
					<th>注册资本</th>
					<td>
						${affiBaseInfo.registerCapital }
					</td>
				</tr>
				<tr>
					<th>法定代表人姓名</th>
					<td>${affiBaseInfo.legalName }</td>
					<th>法定代表人身份号码</th>
					<td>${affiBaseInfo.idCardNO }</td>
				</tr>
				<tr>
					<th>联系人</th>
					<td>${affiBaseInfo.contactPerson }</td>
					<th>联系电话</th>
					<td>${affiBaseInfo.phoneContact }</td>
				</tr>
				<tr>
					<th>传真</th>
					<td>${affiBaseInfo.fax }</td>
					<th>邮编</th>
					<td>${affiBaseInfo.zipCode }</td>
				</tr>
				<tr>
					<th>国别地区</th>
					<td id="affiBaseCountry" ></td>
					<th>国内税务编号</th>
					<td>${affiBaseInfo.internalTaxNO }</td>
				</tr>
				<tr>
					<th>业务范围</th>
					<td>${affiBaseInfo.businessScope }</td>
					<th>网址</th>
					<td>${affiBaseInfo.internetSite }</td>
				</tr>
				<tr>
					<th>状态</th>
					<td id="status"></td>
					<th>地址</th>
					<td>${affiBaseInfo.address }</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">${affiBaseInfo.comments }</td>
				</tr>
				<tr>
					<th>登记日期</th>
					<td>
					<fmt:formatDate value="${affiBaseInfo.createDate }" pattern="yyyy-MM-dd" />
					</td>
					<th>登记人</th>
					<td>${affiBaseInfo.createrName }</td>
				</tr>
			</table>
		</div>
		<div data-options="title: '银行信息', iconCls: false, refreshable: false">
			<input type="hidden" name="affiBankJson" id="affiBankJson"/>
			<table id="childTB" ></table>
			<%
				AffiBaseInfo affiBaseInfo = (AffiBaseInfo)request.getAttribute("affiBaseInfo");
				List<AffiBankInfo> affiBankList = affiBaseInfo.getAffiBankInfo();
				ObjectMapper objectMapper = new ObjectMapper();
				String abJson = objectMapper.writeValueAsString(affiBankList);
				request.setAttribute("abJson", abJson);
			%>
		</div>		
		
		<div data-options="title: '客户评审', iconCls: false, refreshable: false">
			<input type="hidden" name="affiCustomerJson" id="affiCustomerJson"/>
			<table id="childTBCus" ></table>
			<%
				AffiBaseInfo affiBaseInfoCus = (AffiBaseInfo)request.getAttribute("affiBaseInfo");
				List<AffiCustomerInfo> affiCustomerList = affiBaseInfoCus.getAffiCustomerInfo();
				ObjectMapper objectMapperCus = new ObjectMapper();
				String customerJson = objectMapperCus.writeValueAsString(affiCustomerList);
				request.setAttribute("customerJson", customerJson);
			%>
		</div>
		
		<div data-options="title: '附件', iconCls: false, refreshable: false">
			<input id="accParentEntity" type="hidden" value="<%=AffiBaseInfo.class.getName().replace(".","_") %>" />
			<input id="accParentId" type="hidden" value="${affiBaseInfo.id}" />
			<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
		</div>		
	</div>
	</form>
</div>

<script type="text/javascript">
if('${affiBaseInfo.country }' != ""){
	$.ajax({
		url : '${ctx}/system/countryArea/getName/${affiBaseInfo.country }',
		type : 'get',
		cache : false,
		success : function(data) {
			$('#affiBaseCountry').html(data);
		}
	});
}

$.ajax({
	url : '${ctx}/system/dict/getAllName/${affiBaseInfo.customerType }/KHLX',
	type : 'get',
	cache : false,
	success : function(data) {
		$('#affiBaseCusType').text(data);
	}
});

if ('${affiBaseInfo.status }' == 1) {
	$('#status').text("停用");
} else {
	$('#status').text("正常");
}

var childTB;
var childTBCus;
$(function(){
	childTB=$('#childTB').datagrid({
	data : JSON.parse('${abJson}'),
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
		{field:'bankName',title:'银行名称',width:20},
		{field:'bankNO',title:'帐号',width:20},
		{field:'partitionInfo',title:'省市信息',width:20},
		{field:'contactPerson',title:'联系人',width:20},
		{field:'phoneContact',title:'联系电话',width:20}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});

	childTBCus=$('#childTBCus').datagrid({
	data : JSON.parse('${customerJson}'),
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
		{field:'creditLine',title:'授信业务额度',width:20},
	    {field:'checkStartTime',title:'评审有效开始日期',width:20},
        {field:'checkEndTime',title:'评审有效结束日期',width:20},
		{field:'customerAndConditions',title:'客户简介及生产经营情况等',width:25}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar2'
	});
});
</script>
</body>
</html>