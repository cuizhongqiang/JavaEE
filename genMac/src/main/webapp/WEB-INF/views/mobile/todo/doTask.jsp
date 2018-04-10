<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css"/>
		<link rel="stylesheet" type="text/css" href="res:css/control.css"/>
		<style type="text/css">
		</style>
		<script type="text/javascript">
			<![CDATA[

			/**
			 * 同意流程
			 */
			function agreeajax() {
				var goId = getchecked('goId');
				var taskId = '${approval.taskId}';
				var businessKey = '${approval.businessKey}';
				var processKey = '${approval.processKey}';
				var comments = document.getElementById("comments").value;
				var processInstanceId = '${approval.processInstanceId}';
				var email = getchecked('email');
				var sms = getchecked('sms');
				var encyclic = getSelected("encyclicSelect");
				if (comments != null && comments != "") {
				    //设置提交地址
				    var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/agree";
				    //设置提交方式
				    var method = "POST";
				    //设置发送数据
				    var data = "email=" + email + "&sms=" + sms + "&encyclic=" + encyclic + "&goId=" + goId + "&taskId=" + taskId + "&comments=" + encodeURI(comments) + "&businessKey=" + businessKey + "&processKey=" + processKey + "&processInstanceId=" + processInstanceId + "&loginName=${approval.loginName}";
				    //设置自定义http头 json格式
				    var requestHeader = {};
				    //设置进度条显示
				    var isShowProgress = false;
				    //构建Ajax对象
				    var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				    //发送请求
				    ajax.send();
				} else {
					alert("请填写处理意见，谢谢！");
				}
			}

			/**
			 * 驳回流程
			 */
			function backajax(flag) {
				var backId = getchecked('backId');
				var taskId = '${approval.taskId}';
				var businessKey = '${approval.businessKey}';
				var processKey = '${approval.processKey}';
				var comments = document.getElementById("comments").value;
				var processInstanceId = '${approval.processInstanceId}';
				var email = getchecked('email');
				var sms = getchecked('sms');
				var encyclic = getSelected("encyclicSelect");
				if (backId != null && backId != "") {
					if (comments != null && comments != "") {
				        //设置提交地址
				        var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/back";
				        //设置提交方式
				        var method = "POST";
				        //设置发送数据
				        var data = "flag=" + flag + "&email=" + email + "&sms=" + sms + "&encyclic=" + encyclic + "&backId=" + backId + "&taskId=" + taskId + "&comments=" + encodeURI(comments) + "&businessKey=" + businessKey + "&processKey=" + processKey + "&processInstanceId=" + processInstanceId + "&loginName=${approval.loginName}";
				        //设置自定义http头 json格式
				        var requestHeader = {};
				        //设置进度条显示
				        var isShowProgress = false;
				        //构建Ajax对象
				        var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				        //发送请求
				        ajax.send();
					} else {
						alert("请填写处理意见，谢谢！");
					}
				} else {
					alert("请选择驳回节点！如果不存在驳回节点当前内容不能驳回。");
				}
			}

			function successFunction(data) {
				var content = data.responseText;
				openData(content);
			}

			function failFunction(data) {
				alert("提交流程失败");
			}

			function getchecked(name) {
				var obj = document.getElementsByName(name);
				for (var i = 0; i < obj.length; i++) {
					if (obj[i].checked == true) {
						return obj[i].value;
					}
				}
				return "";
			}
			
			function getSelected(id) {
				var obj = document.getElementById(id);
				var index = obj.selectedIndex;
				var text = obj.options[index].text;
				var value = obj.options[index].value;
				return value;
			}

			function backto() {
				window.close();
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="办理" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="backto();">
		<br size="5"/>
		<div>
			下一办理节点：<c:if test="${empty nextActivity}">无</c:if>
			<c:forEach var="next" items="${nextActivity}">
				<input type="radio" name="goId" value="${next.id}"/>${next.properties.name}
			</c:forEach>
			<c:if test="${my:shiro(approval.loginName, 'sys:workflow:jump') }">
				<br />
				可选跳转节点：<c:if test="${empty goActivity}">无</c:if>
				<c:forEach var="go" items="${goActivity}" >
					<input type="radio" name="goId" value="${go.id}"/>${go.properties.name}
				</c:forEach>
			</c:if>
			<br />
			驳回节点：<c:if test="${empty backActivity}">无</c:if>
			<c:forEach var="back" items="${backActivity}">
				<input type="radio" name="backId" value="${back.id}"/>${back.properties.name}
			</c:forEach>
		</div>
		<div>
			<textarea id="comments" name="comments" prompt="处理意见" validate="required" validatemsg="非空校验不能为空"></textarea>
		</div>
		<div>
			邮件提醒:
			<input type="radio" name="email" value="1"/>是
			<input type="radio" name="email" value="0" checked="true"/>否
			<br/>
			短信提醒:
			<input type="radio" name="sms" value="1"/>是
			<input type="radio" name="sms" value="0" checked="true"/>否
			<br/>
			传阅:
			<select id="encyclicSelect" multiple="true">
				<option></option>
				<c:forEach var="item" items="${userList}">
					<option value="${item.loginName }">${item.name }</option>
				</c:forEach>
			</select>
		</div>
		<hr/>
		<div>
			<c:forEach var="item" items="${traceList}">
				<listitem type="twoline" keybind="${item.id }" sndcaption="审批意见：${item.comments }" 
			 	caption="审批节点：${item.name }|处理人：${item.assignee }"/>
			</c:forEach>
		</div>
	</body>
	<footer>
		<input type="button" value="同意" style="align: center;width: 20%;" onclick="agreeajax()"/>
		<input type="button" value="驳回" style="align: center;width: 20%;" onclick="backajax(0)"/>
		<c:if test="${my:shiro(approval.loginName, 'sys:workflow:return') }">
			<input type="button" value="退回" style="align: center;width: 20%;" onclick="backajax(1)"/>
		</c:if>
	</footer>
</html>