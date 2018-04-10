<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
		.label-left{
			color:#0080FF;
			width:40%;
			margin:8;
		}
		.label-right{
			width:60%;
			margin:8 0;
		}
		toggle{
			background-color:#eeeeee;background-click-color:#eeeeee;iconwidth:28;border-size:0;border-color:#eeeeee;
		}
		</style> 
		<script type="text/javascript">
			<![CDATA[
			
			/**
			 * 审批页面跳转
			 */
			function doTask() {
				//设置提交地址
				var url = "http://${ip}:${post}${ctx}/mobile/taskTodo/approval";
				//设置提交方式
				var method = "POST";
				//设置发送数据
				var data = "taskId=${approval.taskId}&businessKey=${approval.businessKey}&processKey=${approval.processKey}&processInstanceId=${approval.processInstanceId}&loginName=${approval.loginName}";
				//设置自定义http头 json格式
				var requestHeader = {};
				//设置进度条显示
				var isShowProgress = false;
				//构建Ajax对象
				var ajax = new Ajax(url, method, data, successFunction, failFunction, requestHeader, isShowProgress);
				//发送请求
				ajax.send();
			}
			
			function failFunction(data){
				alert("fail");
			}
			
			function successFunction(data){
				var content = data.responseText;
				openData(content);
			}
			
			function backto(){
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
			 }	
			
			]]>
		</script>
	</head>
	<header>
		<titlebar title="办理" ricon="res:image/titlebar/ok.png" clickricon="res:image/titlebar/ok_click.png" iconhref="script:close" hidericon="true"/>
	</header>
	<body onl2rside="backto()">
		<br size="5"></br>
		<div style="text-align: left">
			<%@ include file="../detail/todoDetail.jsp"%>
		</div>
	</body>
	<footer>
		<input type="button" style="align: center;width: 50%;" value="审批" onclick="doTask()"/>
		<br/>
	</footer>
</html>