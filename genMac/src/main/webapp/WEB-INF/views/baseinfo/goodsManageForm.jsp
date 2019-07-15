<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="${ctx}/baseinfo/goodsManage/${action}" method="post">
<table width="98%" class="tableClass">
	<tr>
		<th width="20%"><font color="red">*</font>商品大类码</th>
		<td>
		<input type="text" id="goodsCode" name="goodsCode" value="${goodsManage.goodsCode }" class="easyui-validatebox" data-options="required:true,validType:['num']"/>
		</td>
		<th width="20%"><font color="red">*</font>商品大类名称</th>
		<td>
		<input type="hidden" name="id" value="${id }"/>
		<input type="text" name="goodsName" value="${goodsManage.goodsName }" class="easyui-validatebox" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th>财务商品码</th>
		<td>
		<input type="text" name="financialCode" value="${goodsManage.financialCode }" class="easyui-validatebox"/>
		</td>
		<th>统计商品码</th>
		<td>
		<input type="text" name="statisticalCode" value="${goodsManage.statisticalCode }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>统计单位</th>
		<td>
		<input type="text" id="statisticalUnit" name="statisticalUnit" value="${goodsManage.statisticalUnit }"/>
		</td>
		<th>增值税率</th>
		<td>
		<input type="text" name="vat" value="${goodsManage.vat }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
	</tr>
	<tr>
		<th>退税率</th>
		<td>
		<input type="text" name="taxRebate" value="${goodsManage.taxRebate }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
		<th>出口关税率</th>
		<td>
		<input type="text" name="exportTariffs" value="${goodsManage.exportTariffs }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
	</tr>
	<tr>
		<th>出口销项税率</th>
		<td>
		<input type="text" name="outputTax" value="${goodsManage.outputTax }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
		<th>普通进口关税率</th>
		<td>
		<input type="text" name="ordinaryImportTariffs" value="${goodsManage.ordinaryImportTariffs }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
	</tr>
	<tr>
		<th>最惠国进口关税率</th>
		<td>
		<input type="text" name="mostFavoredNationImportTariffs" value="${goodsManage.mostFavoredNationImportTariffs }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
		<th>消费税率</th>
		<td>
		<input type="text" name="consumptionTax" value="${goodsManage.consumptionTax }" class="easyui-numberbox" data-options="min:0,precision:2,suffix:'%'"/>
		</td>
	</tr>
	<tr>
		<th>特别关税</th>
		<td>
		<input type="text" name="specialTariffs" value="${goodsManage.specialTariffs }" class="easyui-validatebox"/>
		</td>
		<th>终止年月</th>
		<td>
		<input type="text" name="endTime" value="<fmt:formatDate value="${goodsManage.endTime }" />" class="easyui-my97" datefmt="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr>
		<th>上级名称</th>
		<td colspan="3">
		<input id="pid" name="pid" type="text" value="${goodsManage.pid }"  class="easyui-validatebox"/>
		<a href="#" class="easyui-linkbutton" iconCls="icon-hamburg-busy" onclick="$('#pid').combotree('clear')">清除</a>
		</td>
	</tr>
	<tr>
		<th>属性一</th>
		<td>
		<input type="text" name="attrOne" value="${goodsManage.attrOne }" class="easyui-validatebox"/>
		</td>
		<th>属性二</th>
		<td>
		<input type="text" name="attrTwo" value="${goodsManage.attrTwo }" class="easyui-validatebox"/>
		</td>
	</tr>
	<tr>
		<th>登记日期</th>
		<td>
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty goodsManage.createDate ? now : goodsManage.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th>登记人</th>
		<td>${empty goodsManage.createrName ? sessionScope.user.name : goodsManage.createrName }</td>
	</tr>
</table>
</form>
<script type="text/javascript">
$(function(){
	$('#mainform').form({
	    onSubmit: function(){
	    	var isValid = $(this).form('validate');
	    	if(isValid){
		    	$.ajax({
		    		url : '${ctx}/baseinfo/goodsManage/uniqueNo/${empty id ? 0 : id}/' + $('#goodsCode').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '商品大类码重复！', 'info');
		    			}
		    		}
		    	});
	    	}
			return isValid;	// 返回false终止表单
	    },
	    success:function(data){
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    	dg.treegrid('reload');
	    }
	});
	
	$('#statisticalUnit').combobox({
		panelHeight:'auto',
	    url:'${ctx}/system/dict/getDictByCode/DW',
	    valueField:'name',
	    textField:'name'
	});
	
	//父级
	if (pid > 0) {
		$('#pid').val(pid);
		pid = 0;
	}
	
	//上级菜单
	$('#pid').combotree({
		width:400,
		method:'GET',
	    url: '${ctx}/baseinfo/goodsManage/json',
	    idField : 'id',
	    textFiled : 'goodsName',
		parentField : 'pid',
	    animate:true
	});
});
</script>
</body>
</html>