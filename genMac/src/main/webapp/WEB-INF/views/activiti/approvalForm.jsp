<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<fieldset class="fieldsetClass">
	<legend>审批操作</legend>
	<form id="mainform" action="" method="post">
		<table width="100%" class="fstableClass">
			<tr>
				<td width="25%" style="font-weight: bold">同意,选择下一办理节点和办理人</td>
				<td width="25%">
					<a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cologne-sign-in" onclick="agree();">同意</a>
				</td>
				<td width="25%" style="font-weight: bold">不同意,选择驳回</td>
				<td width="25%">
					<a id="back" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-cologne-sign-out" onclick="disagree(0);">驳回</a>
					<shiro:hasPermission name="sys:workflow:return">
					<span class="toolbar-item dialog-tool-separator"></span>
					<a id="return" href="#" class="easyui-linkbutton" plain="true" iconCls="icon-hamburg-left" onclick="disagree(1);">退回</a>
					</shiro:hasPermission>
				</td>
			</tr>
			<tr>
				<td>
					下一办理节点：<c:if test="${empty nextActivity}">无</c:if>
					<c:forEach var="next" items="${nextActivity}">
						<input type="radio" name="goId" value="${next.id}" style="margin-top:-2px" onclick="goActivity('${next.properties.taskDefinition.candidateUserIdExpressions}')"/>${next.properties.name}
					</c:forEach>
					<shiro:hasPermission name="sys:workflow:jump">
					<br />
					可选跳转节点：<c:if test="${empty goActivity}">无</c:if>
					<c:forEach var="go" items="${goActivity}" >
						<input type="radio" name="goId" value="${go.id}" style="margin-top:-2px" onclick="goActivity('${go.properties.taskDefinition.candidateUserIdExpressions}')"/>${go.properties.name}
					</c:forEach>
					</shiro:hasPermission>
				</td>
				<td>
					办理人：
					<span id="candidateUserIds">
						<c:forEach var="candidateUserId" items="${approval.candidateUserIds}">
							<input type="checkbox" name="candidateUserIds" value="${candidateUserId}" style="margin-top:1px;" checked="checked"/>${candidateUserId}
						</c:forEach>
					</span>
					<input type="hidden" name="candidateVariableName" value="${approval.candidateVariableName }"/>
				</td>
				<td colspan="2">
					驳回节点：<c:if test="${empty backActivity}">无</c:if>
					<c:forEach var="back" items="${backActivity}">
						<input type="radio" name="backId" value="${back.id}" style="margin-top:-2px"/>${back.properties.name}
					</c:forEach>
				</td>
			</tr>
			<c:forEach items="${form }" var="data">
				<c:forEach items="${data }" var="item">
					<tr>
						<td style="font-weight: bold">${item.key.name }</td>
						<td colspan="3">
							<c:forEach items="${item.value }" var="map" varStatus="status">
								<input type="radio" name="formMap[${item.key.id }]" value="${map.value }" style="margin-top:-2px" <c:if test="${status.index == 0}">checked</c:if>/>${map.value }
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
			<tr>
				<td colspan="4">
					<font style="font-weight: bold">处理意见</font>
					<br />
					<input type="hidden" name="processInstanceId" value="${approval.processInstanceId }" />
					<input type="hidden" name="taskId" value="${approval.taskId }" />
					<input type="hidden" name="businessKey" value="${approval.businessKey }" />
					<input type="hidden" name="processKey" value="${approval.processKey }" />
					<textarea id="comments" name="comments" class="easyui-validatebox" style="width: 96%;margin-top: 5px;" data-options="required:true,validType:['length[0,500]']" onkeyup="getRemain()"/>
					<br />
					<center>文字最大长度: 500 还剩: <font id="remain" color="red">500</font></center>
					<center style="margin-top: 5px;">
						邮件提醒：
						<input type="radio" name="email" value="1" style="margin-top:-2px"/>是
						<input type="radio" name="email" value="0" checked="checked" style="margin-top:-2px"/>否
						&nbsp;&nbsp;&nbsp;&nbsp;
						短信提醒：
						<input type="radio" name="sms" value="1" style="margin-top:-2px"/>是
						<input type="radio" name="sms" value="0" checked="checked" style="margin-top:-2px"/>否
						&nbsp;&nbsp;&nbsp;&nbsp;
						传阅：
						<input type="text" name="encyclic" class="easyui-combobox" data-options="
							method : 'GET',
							multiple : true,
							url : '${ctx}/system/user/select',
							valueField : 'loginName',
							textField : 'name',
							onHidePanel : function(){}
						"/>
					</center>
				</td>
			</tr>
		</table>
	</form>
	</fieldset>
	<fieldset class="fieldsetClass">
		<legend>审批记录</legend>
		<table id="traceRecord"/>
	</fieldset>
	<script type="text/javascript">
		var traceRecord;
		traceRecord=$('#traceRecord').datagrid({
			method: "get",
		    url:'${ctx}/workflow/trace/list/${approval.processInstanceId}', 
		    fit : false,
			fitColumns : true,
			border : false,
			idField : 'id',
			rownumbers:true,
			singleSelect:true,
			striped:true,
		    columns:[[
				{field:'name',title:'审批节点',sortable:true,width:30},
		        {field:'assignee',title:'处理人 ',sortable:true,width:20},
		        {field:'startTime',title:'任务开始时间',sortable:true,width:20},
		        {field:'endTime',title:'任务结束时间',sortable:true,width:20},
		        {field:'comments',title:'审批意见',sortable:true,width:50}
		    ]],
		    enableHeaderClickMenu: false,
		    enableHeaderContextMenu: false,
		    enableRowContextMenu: false,
		    remoteSort:false
		});
	
		$(function() {
			var goId = $('input[name="goId"]');
			var backId = $('input[name="backId"]');
			if(goId.size() != 0){
				goId.eq(0).click();
			}
			if(backId.size() != 0){
				backId.eq(backId.size()-1).attr("checked", "true");
			}else{
				$('#back').linkbutton({
					disabled: true
				});
				$('#return').linkbutton({
					disabled: true
				});
			}
			
			var candidateUserIds = $('input[name="candidateUserIds"]');
			if(candidateUserIds.size() == 1){
				candidateUserIds[0].style.display = "none";
			}
			candidateUserIds.click(function(){
				if($('input[name="candidateUserIds"]:checked').length == 0){
					$(this).attr("checked", "true");
				}
			});
			
			$('#mainform').form({
			    onSubmit: function(){    
			    	var isValid = $(this).form('validate');
					return isValid;	// 返回false终止表单提交
			    },    
			    success:function(data){   
			    	successTip(data,dgToDo,approvalDlg);
			    	dealDlg.panel('close');
			    	dgHaveDone.datagrid('reload');
					dgRunning.datagrid('reload');
			    }
			});
		});
		
		function goActivity(candidateUserIdExpressions) {
			if(typeof candidateUserIdExpressions == "string"){
				$('#candidateUserIds').html(candidateUserIdExpressions.length == 0 ? "无" : candidateUserIdExpressions.substring(1, candidateUserIdExpressions.length - 1));
			}else{
				var str;
				for(var i = 0; i < candidateUserIdExpressions.length; i++){
					str += candidateUserIdExpressions[i] + ",";
				}
				$('#candidateUserIds').html(str.substring(0, str.length - 1));
			}
		}
		
		function getRemain() {
			$("#remain").text(500 - $("#comments").val().length);
		}
		
		function agree(){
			$("#mainform").attr("action","${ctx}/workflow/agree");
			$("#mainform").submit();
		}
		
		function disagree(flag){
			$("#mainform").attr("action","${ctx}/workflow/back/" + flag);
			$("#mainform").submit();
		}
	</script>
</body>
</html>