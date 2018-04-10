<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<div id="mainDiv" class="easyui-tabs" data-options="border: false">
		<div data-options="title: '基本信息', iconCls: false, refreshable: false">
		<!--移动端截取标志1-->
			<table width="98%" class="tableClass">
				<tr>
					<th width="25%">商品名称</th>
					<td>${goods.name }</td>
				</tr>
				<tr>
					<th>商品类型</th>
					<td id="goodsTypeId">${goods.goodsTypeId }</td>
				</tr>
				<tr>
					<th>封面</th>
					<td>${goods.cover}</td>
				</tr>
				<tr>
					<th>图片</th>
					<td>${goods.img }</td>
				</tr>
				<tr>
					<th>商品单价</th>
					<td>${goods.price}</td>
				</tr>
				<tr>
					<th>市场价</th>
					<td>${goods.marketPrice}</td>
				</tr>
				<tr>
					<th>邮费</th>
					<td>${goods.postage}</td>
				</tr>
				<tr>
					<th>销量</th>
					<td>${goods.sales}</td>
				</tr>
				<tr>
					<th>是否上架</th>
					<td id="isSold">${goods.isSold}</td>
				</tr>
				<tr>
					<th>介绍</th>
					<td>${goods.introduce}</td>
				</tr>
				<tr>
					<th>摘要</th>
					<td>${goods.brief}</td>
				</tr> 
			</table>
			<!--移动端截取标志1-->
		</div>
		<div data-options="title: '商品明细', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/shop/goodsChildInfo.jsp"%>
		</div>
		<div data-options="title: '附件', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/shop/goodsChildAccInfo.jsp"%>
		</div>
	</div>
</div>
<script type="text/javascript">

var isSold = $('#isSold').text();
if (isSold == 1) {
	$('#isSold').text("是");
} else {
	$('#isSold').text("否");
}
$.ajax({
	url : "${ctx}/shop/goodsType/typeAjax/${goods.goodsTypeId }",
	success : function(data) {
		$("#goodsTypeId").text(data);
	}
});

</script>
</body>
</html>