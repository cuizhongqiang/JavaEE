<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">
				分配角色
			</h4>
		</div>
		<div class="modal-body">
			<table id="roleDataTable"></table>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-primary" id="submitButton"
				data-loading-text="Loading..."
				data-container="body" data-toggle="popover" data-placement="right"
				data-content="保存成功">提交保存</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	var roleDataTable;
	$(function() {
		var queryUrl = "${ctx}/role/jsonDate";
		roleDataTable = $('#roleDataTable').bootstrapTable({
			url : queryUrl,//请求后台的URL
			method : "GET",
			striped : true,//是否显示行间隔色
			singleSelect : false,//设置True 将禁止多选
			cache : false,//是否使用缓存
			pagination : true,//是否显示分页
			sortable : true,
			sortName : "id",
			sortOrder : "desc",//排序方式
			silentSort : false,//设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
			sidePagination : "server",//分页方式：client客户端分页，server服务端分页
			pageNumber : 1,//初始化加载第一页，默认第一页,并记录
			pageSize : 10,//每页的记录行数
			pageList : [ 10, 20 ],//可供选择的每页的行数
			minimumCountColumns : 2,//当列数小于此值时将隐藏内容列下拉框
			clickToSelect : true,//是否启用点击选中行
			checkboxHeader : false,//设置false 将在列头隐藏check-all checkbox
			height : 300,//行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
			idField : "id",
			uniqueId : "id",//每一行的唯一标识，一般为主键列
			queryParamsType : '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort // 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
			columns : [ {
				title : '选择',
				checkbox : true
			}, {
				field : 'name',
				title : '名称'
			}, {
				field : 'code',
				title : '编码',
				sortable : true
			}, {
				field : 'description',
				title : '描述'
			} ],
			onLoadSuccess : function() {
				var roleIds = '${roleIds}';
				var roleIdArray = roleIds.split(',');
				var tableDate = roleDataTable.bootstrapTable('getData');
				for (var i = 0; i < tableDate.length; i++) {
					if (roleIdArray.contains(tableDate[i].id)) {
						roleDataTable.bootstrapTable('check', i);
					}
				}
			},
			onLoadError : function() {
				alert("数据加载失败！");
			}
		});
	});

	$("#submitButton").click(function() {
		var jsonArray = roleDataTable.bootstrapTable('getSelections');
		var strArray = [];
		for (var i = 0; i < jsonArray.length; i++) {
			strArray.push(jsonArray[i].id);
		}
		$(this).button('loading');
		$.post("${ctx }/user/distributionRoleSave", {
			roleIds : strArray.join(","),
			userId : '${id}'
		}, function(data) {
			if (data == "success") {
				$("#submitButton").popover('show');
				setTimeout(function() {
					$("#submitButton").popover('hide');
					$('#myModal').modal('hide');
					$("#submitButton").button('reset');
				}, 1000);
			}
		});
	});
</script>