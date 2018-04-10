<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div style="width: 50%;float: left;">
	<fieldset class="fieldsetClass">
	<legend>修改后</legend>
		<div id="after" style="width: 100%"></div>
	</fieldset>
</div>
<div style="float: left;width: 50%;">
	<fieldset class="fieldsetClass">
	<legend>修改前</legend>
		<div id="before" style="width: 100%"></div>
	</fieldset>
</div>
<div style="width: 100%;">
<fieldset class="fieldsetClass">
<legend>系统信息</legend>
<table width="100%" style="margin: 0px;" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${changeCredit.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${changeCredit.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${changeCredit.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
</fieldset>
</div>
<script type="text/javascript">
$(function(){
	$.ajax({
		type:'get',
		url:'${ctx}/credit/changeCredit/configDetail/${changeCredit.id}',
		success:function(data){
			$("#after").html(data);
		}
	});
	if('${empty changeCredit.historyId}' == 'true'){
		$.ajax({
			type:'get',
			url:'${ctx}/credit/changeCredit/config/${changeCredit.changeId}',
			success:function(data){
				$("#before").html(data);
			}
		});
	}else{
		$.ajax({
			type:'get',
			url:'${ctx}/credit/changeCredit/config/history/${changeCredit.historyId}',
			success:function(data){
				$("#before").html(data);
			}
		});
	}
	
	window.setTimeout(function(){
		//填充子表商品空缺
		var afterObj = $('#changeCreditGoodsDetail');
		var beforeObj;
		if('${empty changeCredit.historyId}' == 'true'){
			beforeObj = $('#openCreditGoodsDetail');
		}else{
			beforeObj = $('#openCreditHistoryGoodsDetail');
		}
		var afterGoods = afterObj.datagrid('getRows').length;
		var beforeGoods = beforeObj.datagrid('getRows').length;
		if(afterGoods > beforeGoods){
			for(var i = 0; i < afterGoods - beforeGoods; i++){
				beforeObj.datagrid('appendRow',{});
			}
		}else if(beforeGoods > afterGoods){
			for(var i = 0; i < beforeGoods - afterGoods; i++){
				afterObj.datagrid('appendRow',{});
			}
		}
		//获取所有的td对象，after before分别放置
		var afterArray = [];
		var beforeArray = [];
		$("#after table tr td").each(function(index, dom){
			afterArray.push(dom);
		});
		$("#before table tr td").each(function(index, dom){
			beforeArray.push(dom);
		});
		//比较不同之处标红
		for(var i = 0; i < afterArray.length; i++){
			if (i == 17) {
				continue;
			}
			if (beforeArray[i] == null || afterArray[i].innerText != beforeArray[i].innerText) {
				$(afterArray[i]).attr("style", "color: red;");
			}
		}
	}, 1000); //需要页面元素进入一定的状态才能使用，所以延迟1秒执行
});
</script>
</body>
</html>