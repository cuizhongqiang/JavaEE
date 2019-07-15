<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<c:if test="${action eq 'create' }">
<a href="#" class="easyui-linkbutton" iconCls="icon-standard-page-white-edit" plain="true" onclick="toOpenCredit()">配置要修改的开证</a>
</c:if>
<div id="mainDiv"></div>
<table width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<jsp:useBean id="now" class="java.util.Date" scope="page"/>
		<fmt:formatDate value="${empty changeCredit.createDate ? now : changeCredit.createDate }" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${empty changeCredit.createrName ? sessionScope.user.name : changeCredit.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${changeCredit.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	if('${action}' == "update"){
		$('#mainDiv').panel({
			border:false,
			noheader:true,
			closed:false,
			href:'${ctx}/credit/changeCredit/configForm/update/${changeCredit.id}'
		});
	}
});

//配置要修改的开证
function toOpenCredit(){
	changeCreditDlg=$("#changeCreditDlg").dialog({
		title: '配置要修改的开证',
		width: 1000,
		height: 280,
	    href:'${ctx}/credit/changeCredit/config',
	    modal:true,
	    buttons:[{
			text:'保存',
			handler:function(){
				var row = changeCredit_dg.datagrid('getSelected');
				if(rowIsNull(row)) return;
				$('#mainDiv').panel({
					border:false,
					noheader:true,
					closed:false,
					closed:false,
					href:'${ctx}/credit/changeCredit/configForm/create/' + row.id
				});
				changeCreditDlg.panel('close');
			}
		},{
			text:'关闭',
			handler:function(){
				changeCreditDlg.panel('close');
			}
		}]
	});
}
</script>
</body>
</html>