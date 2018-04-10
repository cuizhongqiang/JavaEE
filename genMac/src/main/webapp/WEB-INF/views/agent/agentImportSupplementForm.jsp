<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<form id="mainform" action="" method="post">
<table width="99%" class="tableClass">
	<tr>
		<th width="10%">合同名称</th>
		<td colspan="3">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" name="pid" value="${pid }"/>
			<input type="text" id="contractName" name="contractName" value="${agentImportSupplement.contractName }" class="easyui-validatebox" data-options="required:true"/>
		</td>
	</tr>
	<tr>
		<th width="10%">签订主体名称</th>
		<td width="40%">
			<input type="text" id="principalName" name="principalName" value="${agentImportSupplement.principalName }" class="easyui-validatebox" data-options="required:true"/>
		</td>
		<th width="10%">协议编号</th>
		<td>
			<input type="text" id="agreementNo" name="agreementNo" value="${agentImportSupplement.agreementNo }" class="easyui-validatebox" data-options="required:true"/>
			<a href="#" class="easyui-linkbutton" plain="false" onclick="generateCode();">生成编号</a>
		</td>
	</tr>
	<tr>
		<th width="10%">合同内容</th>
		<td colspan="3">
			<textarea id="content" name="content" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="validType:['length[0,255]']">${agentImportSupplement.content }</textarea>
		</td>
	</tr>
</table>
</form>
<center>
	<a href="#" class="easyui-linkbutton" onclick="submitForm()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="#" class="easyui-linkbutton" onclick="resetForm()">重置</a>
</center>
<fieldset class="fieldsetClass">
<legend>补充协议列表</legend>
<table id="agentImportSupplementTB"></table>
</fieldset>
<script type="text/javascript">
var agentImportSupplementTB
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
		    		url : '${ctx}/agent/agentImportSupplement/uniqueNo/' + ($('#id').val() == "" ? 0 : $('#id').val()) + '/' + $('#agreementNo').val(),
		    		type : 'get',
		    		async : false,
		    		cache : false,
		    		dataType : 'json',
		    		success : function(data) {
		    			if (data) {
			    			isValid = false;
			    			$.messager.alert('提示', '协议编号重复！', 'info');
		    			}
		    		}
		    	});
	    	}
	    	if(!isValid){parent.$.messager.progress('close');};
			return isValid;	// 返回false终止表单提交
	    },
	    success:function(data){
	    	successTip(data,agentImportSupplementTB);
	    	parent.$.messager.progress('close');
	    }
	});
	
	agentImportSupplementTB = $('#agentImportSupplementTB').datagrid({
		method : 'get',
		url : '${ctx}/agent/agentImportSupplement/json?filter_EQL_pid=${pid}',
		fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		pagination:true,
		rownumbers:true,
		pageNumber:1,
		pageSize : 5,
		pageList : [ 5, 10 ],
		singleSelect:true,
	    columns:[[
			{field:'contractName',title:'合同名称',width:30},
			{field:'principalName',title:'签订主体名称',width:30},
			{field:'agreementNo',title:'协议编号',width:30},
			{field:'createDate',title:'创建时间',sortable:true,width:30},
			{field:'updateDate',title:'修改时间',width:30},
			{field:'id',title:'操作',align:'center',width:30,
				formatter : function(value, row, index) {
					var str = "";
					str += "<a name='edit' href='#' onclick='updSup(" + index + ");'></a>"
					str += "<span class='toolbar-item dialog-tool-separator'></span>";
					str += "<a name='remove' href='#' onclick='delSup(" + value + ");'></a>"
					return str;
				}
			}
		]],
		sortName:'createDate',
		sortOrder:'desc',
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onLoadSuccess:function(data){
	    	$("a[name='edit']").linkbutton({text:'修改',plain:true,iconCls:'icon-edit'});
	    	$("a[name='remove']").linkbutton({text:'删除',plain:true,iconCls:'icon-remove'});
	    }
	});
	
});

function submitForm(){
	if($('#id').val() == 0){
		$('#mainform').attr('action', "${ctx}/agent/agentImportSupplement/create");
	}else{
		$('#mainform').attr('action', "${ctx}/agent/agentImportSupplement/update");
	}
	$('#mainform').submit();
}

function resetForm(){
	$('#id').val(null);
	$("#mainform")[0].reset();
}

function updSup(index){
	var row = agentImportSupplementTB.datagrid('getRows')[index];
	$('#id').val(row.id);
	$('#contractName').val(row.contractName);
	$('#principalName').val(row.principalName);
	$('#agreementNo').val(row.agreementNo);
	$('#content').val(row.content);
}

function delSup(value){
	parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data){
		if (data){
			if($('#id').val() == value){//如果删除项是当前正在修改的
				$('#id').val(null);
				$("#mainform")[0].reset();
			}
			$.ajax({
				type:'get',
				url:"${ctx}/agent/agentImportSupplement/delete/" + value,
				success: function(data){
					successTip(data,agentImportSupplementTB);
				}
			});
		} 
	});
}

//构造协议编号
function generateCode() {
	$.ajax({
		url : '${ctx}/agent/agentImportSupplement/generateCode/${pid}',
		type : 'get',
		async : false,
		cache : false,
		success : function(data) {
			$('#agreementNo').val(data);
		}
	});
}
</script>
</body>
</html>