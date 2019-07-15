<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<input id="accParentId" type="hidden"  value="${goods.id}" />
<!-- 上传按钮 -->
<a href="javascript:void(0)" class="easyui-linkbutton"iconCls="icon-hamburg-old-versions" plain="true" onclick="toUpload();">上传附件</a>
<div id="dgAcc"  class="tableClass"></div>
<div id="dlgUpload"  ></div>



<script type="text/javascript">
	var urlValue = "${ctx}/accessory/json?filter_EQS_accParentId="
			+ $('#accParentId').attr('value');
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
											str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='delAcc("
													+ value
													+ ");'>删除</a> &nbsp"
											str += "<a   style='text-decoration:none' href='javascript:void(0)'  onclick='downnloadAcc("
													+ value + ");'>下载</a>"
											return str;
										}
									} ] ]
						})
	});

	//删除附件
	function delAcc(idValue) {
		parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function(data) {
			if (data) {
				$.ajax({
					type : 'get',
					url : "${ctx}/accessory/delete/" + idValue,
					success : function(data) {
						if (data == "success") {
							$('#dgAcc').datagrid('reload');
							$('#dgAcc').datagrid('clearSelections');
						}
						successTip(data, dgAcc);
					}
				});
			}
		});
	}
	//下载附件
	function downnloadAcc(id) {
		window.open("${ctx}/accessory/download/" + id, '下载');
	}
	//跳转上传文件
	function toUpload() {
		dUpload = $("#dlgUpload").dialog({
			title : '上传文件',
			height:350,
			width:500,
			href : '${ctx}/accessory/toUpload',
			maximizable : false,
			closable:false,
			modal : true,
			buttons : [ {
				text : '关闭',
				handler : function() {
					dUpload.panel('close');
				}
			} ]
		});
	}
</script>