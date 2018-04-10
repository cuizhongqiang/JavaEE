<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			 
			function del() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/inform/delete";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "id=${inform.id}&loginName=${loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				var ajax = Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				//发送请求
				ajax.send();
			}
			
			function successFunction(data){
				var content = data.responseText;
				openData(content);
			}

			function failFunction(data) {
				alert("fail");
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="通知" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="backto()">
		<div>
			标题：${inform.subject}
		 	<br size="5"></br>
		 	<hr/>
		 	内容：${inform.content}
		 	<hr/>
		</div>
	</body>
	<footer>
		<input type="button" value="删除" style="align: center;width: 50%;" onclick="confirm('确定要删除吗？', del)"/>
	</footer>
</html>