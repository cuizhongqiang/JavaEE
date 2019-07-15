<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<input id="accParentId" type="hidden"  value="${goods.id}" />
<div id="dgAcc"  class="tableClass"></div>

<script type="text/javascript">
	var urlValue = "${ctx}/accessory/json?filter_EQS_accParentId=" + $('#accParentId').attr('value');
	var dgAcc;
	$(function() {
		dgAcc = $('#dgAcc')
				.datagrid(
						{
							method : "get",
							fit : false,
							fitColumns : true,
							border : false,
							striped : true,
							singleSelect : true,
							scrollbarSize : 0,
							url : urlValue,
							idField : 'accId',
							columns : [ [
									{
										field : 'accRealName',
										title : '附件名称',
										sortable : true,
										width : 40
									},
									{
										field : 'accAuthor',
										title : '上传人',
										sortable : true,
										width : 10
									},
									{
										field : 'accId',
										title : '操作',
										sortable : true,
										width : 20,
										formatter : function(value, row, index) {
											var str = "";
											str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
													+ value + ");'>下载</a>"
											return str;
										}
									} ] ]
						})
	});
	//下载附件
	function downnloadAcc(id) {
		window.open("${ctx}/accessory/download/" + id, '下载');
	}
</script>