<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.genMac.baseinfo.entity.WarehouseGoods"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/warehouse/${action}" method="post">
		<table width="98%" class="tableClass">
			<tr>
				<th width="25%">仓库编码</th>
				<td>
				<input type="hidden" name="id" value="${warehouse.id }" />
				<input id="warehouseCode" name="warehouseCode" type="text" value="${warehouse.warehouseCode }" class="easyui-validatebox"  data-options="required:true,prompt:'选择仓储企业自动生成'" />
				</td>
				<th>仓库名称</th>
				<td><input name="warehouseName" type="text" value="${warehouse.warehouseName }" class="easyui-validatebox" data-options="required:true"/></td>
			</tr>
			<tr>
				<th>仓储企业</th>
				<td>
					<input id="enterpriseStock" name="enterpriseStock" type="text" value="${warehouse.enterpriseStock }"/>
				</td>
				<th>地址</th>
				<td><input id="address" name="address" type="text" value="${warehouse.address }" class="easyui-validatebox"  data-options="required:true"/></td>
			</tr>
			<tr>
				<th>联系人</th>
				<td><input id="contactPerson" name="contactPerson" type="text" value="${warehouse.contactPerson }" class="easyui-validatebox" data-options="required:true"/></td>
					<th>联系电话</th>
				<td><input id="phoneContact" name="phoneContact" type="text" value="${warehouse.phoneContact }" class="easyui-validatebox" data-options="required:true" validtype="telOrMobile"/></td>
			</tr>
			<tr>
				<th>传真</th>
				<td><input id="fax" name="fax" type="text" value="${warehouse.fax }" class="easyui-validatebox"  validtype="fax"/></td>
				<th>邮编</th>
				<td><input name="zipCode" type="text" value="${warehouse.zipCode }" class="easyui-validatebox"  validtype="zipCode"/></td>
			</tr>
			<tr>
				<th>是否实库</th>
				<td>
					<input id="isRealWarehouse" name="isRealWarehouse" type="text"  value="${warehouse.isRealWarehouse }" class="easyui-validatebox"/>
				</td>
				<th>状态</th>
				<td>
					<input id="status" name="status" type="text"  value="${warehouse.status }" class="easyui-validatebox"/>
				</td>
			</tr>
			<tr>
				<th>登记日期</th>
				<td>
				<jsp:useBean id="now" class="java.util.Date" scope="page"/>
				<fmt:formatDate value="${empty warehouse.createDate ? now : warehouse.createDate}" pattern="yyyy-MM-dd" />
				</td>
				<th>登记人</th>
				<td>${empty warehouse.createrName ? sessionScope.user.name : warehouse.createrName }</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
$(function(){
	$('#mainform').form({    
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
	    		$.ajax({
		    		url : '${ctx}/baseinfo/warehouse/uniqueNo/${empty warehouse.id ? 0 : warehouse.id}/' + $('#warehouseCode').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '仓库编码重复！', 'info');
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
	
	//仓储企业
	$('#enterpriseStock').combobox({
		required : true,
		method: "get",
		url : '${ctx}/stock/enterpriseStockCheck/filter?filter_EQS_state=生效',
		valueField : 'enterpriseName',
		textField : 'enterpriseName',
		onSelect:function (data) {
			$('#contactPerson').val(data.contactPerson);
			$('#phoneContact').val(data.phoneContact);
			$('#address').val(data.warehouseAddress);
			$('#fax').val(data.fax);
			//构造编码
			if(!$('#warehouseCode').validatebox('isValid')){
				$.ajax({
					url : '${ctx}/baseinfo/warehouse/generateCode',
					data : {enterpriseName:data.enterpriseName},
					type : 'get',
					async : false,
					cache : false,
					success : function(data) {
						$('#warehouseCode').val(data);
					}
				});
			}
		}
	});
	
	//是否实库
	$('#isRealWarehouse').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dict/getDictByCode/YESNO',
		valueField : 'name',
		textField : 'name'
	});
	
	//状态
	$('#status').combobox({
		panelHeight : 'auto',
		required : true,
		url : '${ctx}/system/dict/getDictByCode/STATUS',
		valueField : 'name',
		textField : 'name'
	});
	
});
</script>
</body>
</html>