<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ip" value="${pageContext.request.localAddr}"/>
<c:set var="post" value="${pageContext.request.serverPort}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css" />
		<style type="text/css">
		</style>
		<script type="text/javascript">
			<![CDATA[

			function show(taskId, processInstanceId, businessKey, processKey, status) {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/detail";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "taskId=" + taskId + "&processInstanceId=" + processInstanceId + "&businessKey=" + businessKey + "&processKey=" + processKey + "&status=" + encodeURI(status) + "&loginName=${loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				//发送请求
				ajax.send();
			}

			function successFunction(data) {
				var content = data.responseText;
				openData(content);
			}
			
			function failFunction(data) {
				alert("fail");
			}

			function backmain() {
				open("res:page/main/maingroup.xhtml");
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="待办事项" iconhref="backmain()" hidericon="true"/>
	</header>
	<body backbind="backmain();" onl2rside="backmain();">
		<div>
			<c:forEach var="item" items="${taskList}">
				<listitem type="twoline" 
			 	keybind="${item.id}" sndcaption="${item.name}|${item.pdname}" caption="${item.businessInfo}"  
			 	href="javascript:show('${item.id}', '${item.pid}', '${item.businessKey}', '${item.processKey}', '${item.status}');"/>
			</c:forEach>
		</div>
	</body>
	<footer></footer>
</html>