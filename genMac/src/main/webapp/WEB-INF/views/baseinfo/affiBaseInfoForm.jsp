<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/baseinfo/affiliates/${action}" method="post">
<div id="mainDiv" class="easyui-tabs" data-options="border:false">
	<div data-options="title: '单位基本信息', iconCls: false, refreshable: false">
		<table width="98%" class="tableClass">
			<tr>
				<th>客户编码</th>
				<td>
					<input id="customerCode" name="customerCode" type="text" value="${affiBaseInfo.customerCode }" class="easyui-validatebox" data-options="prompt:'输入客户名称自动生成',required:true,validType:['english','length[2,4]']" />
				</td>
				<th>客户类型</th>
				<td>
					<input id="affiBaseCusType" name="customerType"  value="${affiBaseInfo.customerType }"  />
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="clearSelect()"></a>
				</td>
			</tr>
			<tr>
				<th>客户名称</th>
				<td>
					<input type="hidden" name="id" value="${affiBaseInfo.id }" />
					<input id="customerName" name="customerName" type="text" value="${affiBaseInfo.customerName }" class="easyui-validatebox" data-options="required:true,validType:['chinese','minLength[4]']" onblur="generateCode();"/>
				</td>
				<th>客户英文名</th>
				<td><input name="customerEnName" type="text" value="${affiBaseInfo.customerEnName }" class="easyui-validatebox"/></td>
			</tr>
			<tr>
				<th>注册时间</th>
				<td>
					<input type="text" id="registerDate" name="registerDate" value="<fmt:formatDate value="${affiBaseInfo.registerDate }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
				</td>
				<th>注册资本</th>
				<td>
					<input type="text" id="registerCapital" name="registerCapital" value="${affiBaseInfo.registerCapital }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/>
				</td>
			</tr>
			<tr>
				<th>法定代表人姓名</th>
				<td><input name="legalName" type="text" value="${affiBaseInfo.legalName }" class="easyui-validatebox"  data-options="required:true"/></td>
				<th>法定代表人身份号码</th>
				<td><input name="idCardNO" type="text" value="${affiBaseInfo.idCardNO }" class="easyui-validatebox" validtype="idCard"/></td>
			</tr>
			<tr>
				<th>联系人</th>
				<td><input name="contactPerson" type="text" value="${affiBaseInfo.contactPerson }"  class="easyui-validatebox"  /></td>
				<th>联系电话</th>
				<td><input name="phoneContact" type="text" value="${affiBaseInfo.phoneContact }"  class="easyui-validatebox"  validtype="telOrMobile"/></td>
			</tr>
			<tr>
				<th>传真</th>
				<td><input name="fax" type="text" value="${affiBaseInfo.fax }" class="easyui-validatebox"   validtype="fax"/></td>
				<th>邮编</th>
				<td><input name="zipCode" type="text" value="${affiBaseInfo.zipCode }" class="easyui-validatebox"  validtype="zipCode"/></td>
			</tr>
			<tr>
				<th>国别地区</th>
				<td>
					<input id="affiBaseCountry" name="country" type="text" value="${affiBaseInfo.country }"/>
				</td>
				<th>国内税务编号</th>
				<td><input name="internalTaxNO" type="text" value="${affiBaseInfo.internalTaxNO }" class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<th>业务范围</th>
				<td><input name="businessScope" type="text" value="${affiBaseInfo.businessScope }" class="easyui-validatebox" /></td>
				<th>网址</th>
				<td><input name="internetSite" type="text" value="${affiBaseInfo.internetSite }" class="easyui-validatebox"  validtype="url"/></td>
			</tr>
			<tr>
				<th>状态</th>
				<td>
					<input name="status"  value="${affiBaseInfo.status }"  style="width:100px;"
						class="easyui-combobox" data-options="valueField: 'label',textField: 'value',panelHeight:'auto', data: [{label: '0',value: '正常',selected:'selected'}, {label: '1',value: '停用'}]" />
				</td>
				<th>地址</th>
				<td><input name="address" type="text" value="${affiBaseInfo.address }" class="easyui-validatebox"  data-options="required:true" /></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="3"><textarea name="comments" class="easyui-validatebox" style="width: 98%; margin-top: 2px;" data-options="validType:['length[0,255]']">${affiBaseInfo.comments }</textarea></td>
			</tr>
			<tr>
				<th>登记日期</th>
				<td>
				<jsp:useBean id="now" class="java.util.Date" scope="page"/>
				<fmt:formatDate value="${empty affiBaseInfo.createDate ? now : affiBaseInfo.createDate}" pattern="yyyy-MM-dd" />
				</td>
				<th>登记人</th>
				<td>${empty affiBaseInfo.createrName ? sessionScope.user.name : affiBaseInfo.createrName }</td>
			</tr>
		</table>
	</div>
	<div data-options="title: '银行信息', iconCls: false, refreshable: false">
		<input type="hidden" name="affiBankJson" id="affiBankJson"/>
		<%@ include file="/WEB-INF/views/baseinfo/affiBankForm.jsp"%>
	</div>		
	<div data-options="title: '客户评审', iconCls: false, refreshable: false">
		<input type="hidden" name="affiCustomerJson" id="affiCustomerJson"/>
		<%@ include file="/WEB-INF/views/baseinfo/affiCustomerForm.jsp"%>
	</div>
	<div data-options="title: '担保信息', iconCls: false, refreshable: false">
		<input type="hidden" name="affiAssureJson" id="affiAssureJson"/>
		<%@ include file="/WEB-INF/views/baseinfo/affiAssureForm.jsp"%>
	</div>
	<div data-options="title: '附件', iconCls: false, refreshable: false">
		<input id="accParentEntity" type="hidden" value="<%=AffiBaseInfo.class.getName().replace(".","_") %>" />
		<input id="accParentId" type="hidden" value="${affiBaseInfo.id}" />
		<%@ include file="/WEB-INF/views/accessory/childAccList.jsp"%>
	</div>		
</div>
</form>
<script type="text/javascript">
$(function(){
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
	
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
	    	if(isValid){
		    	$.ajax({
		    		url : '${ctx}/baseinfo/affiliates/uniqueNo/${empty affiBaseInfo.id ? 0 : affiBaseInfo.id}',
		    		data : {customerCode:$('#customerCode').val()},
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '客户编码重复！', 'info');
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
	
	//客户类型
	$('#affiBaseCusType').combobox({
		method:'GET',
		url:'${ctx}/system/dict/getDictByCode/KHLX',
	    textField : 'name',
	    multiple:true,
	    width:200,
	    multiline:true,
		panelHeight:'auto',
		editable:false,
		required:true,
	    prompt: '此选项可以多选，点击X重置选择',
	    onLoadSuccess: function () { //加载完成后,设置选中
	        var val = $(this).combobox("getData"); 
	        $(this).combobox("clear");
	   		var curValue = this.value.split(',');
	   		for(var j = 0; j < curValue.length; j++){
	   		 	for(var i = 0; i < val.length; i++){
					if(val[i].value == curValue[j]){ 
		    			$(this).combobox("select",curValue[j]);
		            } 
		        } 
	   		}
	    },
	    onHidePanel:function(){}
	});
	
	//国别地区
	$('#affiBaseCountry').combotree({
		method:'GET',
	    url: '${ctx}/system/countryArea/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true,
	    required:true
	});
});

//清除选择
function clearSelect() {
	$('#affiBaseCusType').combobox("clear");
}

//构造客户编码
function generateCode() {
	if(!$('#customerCode').validatebox('isValid') && $('#customerName').validatebox('isValid')){
		$.ajax({
			url : '${ctx}/baseinfo/affiliates/generateCode',
			data : {customerName:$('#customerName').val()},
			type : 'get',
			async : false,
			cache : false,
			success : function(data) {
				$('#customerCode').val(data);
			}
		});
	}
}
</script>
</body>
</html>