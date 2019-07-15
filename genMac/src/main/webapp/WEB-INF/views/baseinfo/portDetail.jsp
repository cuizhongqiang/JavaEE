<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/baseinfo/port/${action}" method="get">
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%">编码</th>
			<td>${port.portId }</td>
		</tr>
		<tr>
			<th width="25%">港口名称</th>
			<td>
			${port.portName }
			</td>
		</tr>
		<tr>
			<th>简码</th>
			<td>${port.portCode }</td>
		</tr>
		<tr>
			<th>英文名称</th>
			<td>${port.portEnName }</td>
		</tr>
		<tr>
			<th>区域码</th>
			<td>${port.areaCode }</td>
		</tr>
		<tr>
			<th>状态</th>
			<td id="status"></td>
		</tr>
		<tr>
			<th>备注</th>
			<td>${port.comments }</td>
		</tr>
		<tr>
			<th>登记日期</th>
			<td>
			<fmt:formatDate value="${port.createDate }" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>${port.createrName }</td>
		</tr>
	</table>
	</form>
</div>

<script type="text/javascript">
if ('${port.status }' == 1) {
	$('#status').text("停用");
} else {
	$('#status').text("正常");
}
</script>
</body>
</html>