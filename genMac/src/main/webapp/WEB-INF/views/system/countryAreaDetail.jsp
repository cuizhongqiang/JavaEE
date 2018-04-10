<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<table width="98%" class="tableClass">
		<tr>
			<th width="25%">名称</th>
			<td>
			${countryArea.name }
			</td>
		</tr>
		<tr>
			<th>编码</th>
			<td>
			${countryArea.code }
			</td>
		</tr>
		<tr>
			<th>简码</th>
			<td>
			${countryArea.scode }
			</td>
		</tr>
		<tr>
			<th>上级区域名称</th>
			<td>
			${pName }
			</td>
		</tr>
		<tr>
			<th>登记人</th>
			<td>
			${countryArea.registrant }
			</td>
		</tr>
		<tr>
			<th>登记部门</th>
			<td>
			${countryArea.registrantDept }
			</td>
		</tr>
		<tr>
			<th>登记时间</th>
			<td>
			<fmt:formatDate value="${countryArea.registrantDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
	</table>
</div>
</body>
</html>