<%@page import="com.cbmie.genMac.stock.entity.EnterpriseStockCheck"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 上传按钮 -->
<c:if test="${!empty action }">
<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-hamburg-old-versions" plain="true" onclick="toUpload();">上传附件</a>
</c:if>
<div id="dgAcc"  class="tableClass"></div>
<div id="dlgUpload"  ></div>
<script type="text/javascript">
	var urlValue = '${ctx}/accessory/json?filter_EQS_accParentId=' + $('#accParentId').val()
					+ '&filter_EQS_accParentEntity=' + $('#accParentEntity').val();
	var dgAcc;
	$(function() {
		dgAcc = $('#dgAcc').datagrid({
			method : "get",
			fit : false,
			fitColumns : true,
			border : false,
			striped : true,
			singleSelect : true,
			scrollbarSize : 0,
			url : urlValue,
			idField : 'accId',
			columns : [[
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
						if('${!empty action }' == 'true'){
							str += "<a style='text-decoration:none' href='#' onclick='delAcc(" + value + ");'>删除</a>&nbsp"
						}
						str += "<a style='text-decoration:none' href='#' onclick='downnloadAcc(" + value + ");'>下载</a>"
						return str;
					}
				} 
			]]
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
			href : '${ctx}/accessory/toUpload/' + $('#accParentId').val(),
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