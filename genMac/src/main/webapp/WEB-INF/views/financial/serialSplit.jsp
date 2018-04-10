<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<style>
</style>
</head>

<body>
	<div>
		<form id="split_form"  action="${ctx}/financial/serial/split" method="post">
				<table width="98%" class="tableClass" >
					<tr>
						<th>流水号</th>
						<td>
							<input type="hidden" name="id" value="${serial.id }" />
							${serial.serialNumber }
						</td>
					</tr>
					<tr>
						<th width="25%">拆分表达式</th>
						<td>
							<input id="perAmoutJson" name="perAmoutJson" type="text" value="" class="easyui-validatebox" data-options="required:true"/>
							例如：5可拆写为2,2,1
						</td>
					</tr>
				</table>
		</form>
	</div>

<script type="text/javascript">

	$(function() {
		$('#split_form').form({
			onSubmit : function() {
				var perAmoutJson = $('#perAmoutJson').val();
				if(perAmoutJson.match(/^\d+(,\d+)+$/) == null){
					$.messager.alert('提示', '拆分表达式格式有误！', 'info');
					$('#perAmoutJson').val("");
					return false;
				}else{
					var numArray = perAmoutJson.split(",");
					var numAmount = 0;
					for(var i = 0; i < numArray.length; i++){
						numAmount += Number(numArray[i]);
					}
					if(numAmount != Number('${serial.money }')){
						$.messager.alert('提示', '拆分总金额不等于实际金额！', 'info');
						return false;
					}
				}
				var isValid = $(this).form('validate');
				return isValid; // 返回false终止表单提交
			},
			success : function(data) {
		    	successTip(data,dg,d_split);
			} 
		});
	});
	
	
</script>
</body>
</html>