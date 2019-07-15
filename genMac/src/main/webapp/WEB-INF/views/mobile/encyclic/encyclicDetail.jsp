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
			/*左右结构*/
			.label-left {
				color: #0080FF;
				width: 40%;
				margin: 8;
			}
			.label-right {
				width: 60%;
				margin: 8 0;
			}
		</style>
		<script type="text/javascript">
			<![CDATA[

			function backto() {
				window.close();
			}

			function viewChildList(){
				 var divObj = document.getElementById("childList");
				 var viewButton = document.getElementById("viewButton");
			 	if(divObj.style.display=='none'){//如果show是隐藏的
					//document.getElementById("childList").css("display","block");//show的display属性设置为block（显示）
			 		viewButton.value="隐藏商品信息（-）";
					divObj.style.display="block";
				}else{//如果show是显示的
			 		//document.getElementById("childList").css("display","none");//show的display属性设置为none（隐藏）
			 		viewButton.value="显示商品信息（+）";
			 		divObj.style.display="none";
			 		}
			 	//document.getElementById("childList").show();
			 }
			 
			 function trace() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/encyclic/trace";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "id=${encyclic.id}&processInstanceId=${encyclic.processInstanceId}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				//发送请求
				ajax.send();
			 }
			 
			function del() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/encyclic/delete";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "id=${encyclic.id}&loginName=${encyclic.loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				//发送请求
				ajax.send();
			}
			
			function successFunction(data){
				var content = data.responseText;
				openData(content);
			}

			function failFunction(data) {
				alert(data.objName + "请求失败,状态码" + data.status);
			}

			]]>
		</script>
	</head>
	<header>
		<titlebar title="查看详情" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="backto()">
		<div style="text-align: left">
			<%@ include file="../detail/todoDetail.jsp"%>
		</div>
	</body>
	<footer>
		<input type="button" value="流程跟踪" style="align: center;width: 40%;" onclick="trace()"/>
		<input type="button" value="删除" style="align: center;width: 40%;" onclick="confirm('确定要删除吗？', del)"/>
	</footer>
</html>