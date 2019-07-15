<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
	<form id="applyform" action="${ctx}/workflow/agree" method="post">
		<table width="98%" class="tableClass">
			<tr>
				<th width="25%">下一办理节点</th>
				<td>
					<c:if test="${empty nextActivity}">无</c:if>
					<c:forEach var="next" items="${nextActivity}">
						<input type="radio" name="goId" value="${next.id}" style="margin-top:-2px" onclick="goActivity('${next.properties.taskDefinition.candidateUserIdExpressions}')"/>${next.properties.name}
					</c:forEach>
				</td>
			</tr>
			<shiro:hasPermission name="sys:workflow:jump">
			<tr>
				<th>可选跳转节点</th>
				<td>
					<c:if test="${empty goActivity}">无</c:if>
					<c:forEach var="go" items="${goActivity}" >
						<input type="radio" name="goId" value="${go.id}" style="margin-top:-2px" onclick="goActivity('${go.properties.taskDefinition.candidateUserIdExpressions}')"/>${go.properties.name}
					</c:forEach>
				</td>
			</tr>
			</shiro:hasPermission>
			<tr>
				<th>办理人</th>
				<td>
					<span id="candidateUserIds">
						<c:forEach var="candidateUserId" items="${approval.candidateUserIds}">
							<input type="checkbox" name="candidateUserIds" value="${candidateUserId}" style="margin-top:1px;" checked="checked"/>${candidateUserId}
						</c:forEach>
					</span>
					<input type="hidden" name="candidateVariableName" value="${approval.candidateVariableName }"/>
				</td>
			</tr>
			<c:forEach items="${form }" var="data">
				<c:forEach items="${data }" var="item">
					<tr>
						<th style="font-weight: bold">${item.key.name }</th>
						<td>
							<c:forEach items="${item.value }" var="map" varStatus="status">
								<input type="radio" name="formMap[${item.key.id }]" value="${map.value }" style="margin-top:-2px" <c:if test="${status.index == 0}">checked</c:if>/>${map.value }
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</c:forEach>
			<tr>
				<td colspan="2">
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
			<br/>
			<tr>
				<td colspan="2" height="48px">
					<center>
						<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="submitApplication();">提交申请</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeApplication();">关闭</a>
					</center>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(function() {
			var goId = $('input[name="goId"]');
			if(goId.size() != 0){
				goId.eq(0).click();
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
			
			$('#applyform').form({
			    onSubmit: function(){    
			    	var isValid = $(this).form('validate');
					return isValid;	// 返回false终止表单提交
			    },    
			    success:function(data){   
			    	successTip(data, dg, applyDlg);
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
		
		function submitApplication(){
			$("#applyform").submit();
		}
		
		function closeApplication(flag){
			$.ajax({
				type:'get',
				url:"${ctx}/workflow/delete/${approval.processInstanceId }",
				success: function(data){
					if(data == 'success'){
						dg.datagrid('reload');
						applyDlg.panel('close');
					}
				}
			});
		}
	</script>
</body>
</html>