<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title show="false"/>
		<link rel="stylesheet" type="text/css" href="res:css/global.css" />
		<style type="text/css">
		</style>
		<script type="text/javascript">
			<![CDATA[

			function backto() {
				window.close();
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="流程跟踪" iconhref="backto()" hidericon="true"/>
	</header>
	<body onl2rside="backto()">
		<div>
			<c:forEach var="item" items="${traceList}">
				<listitem type="twoline" 
			 	keybind="${item.id}" sndcaption="审批意见：${item.comments}" 
			 	caption="审批节点：${item.name}|处理人：${item.assignee}"/>
			</c:forEach>
		</div>
	</body>
	<footer>
		<input type="button" value="关闭" style="align: center;width: 40%;" onclick="backto()"/>
	</footer>
</html>