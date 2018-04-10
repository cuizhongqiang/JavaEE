<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<h4 class="page-header">系统管理>>权限管理</h4>
	<div id="toolbar" class="btn-group">
		<button type="button" class="btn btn-default" onclick="toCreate();">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
	</div>
	<table id="dataTable"></table>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	</div>
	<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="delModalLabel">提示</h4>
				</div>
				<div class="modal-body">
					删除将无法恢复，是否确认删除？
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="delButton"
					data-loading-text="Loading..."
					data-container="body" data-toggle="popover" data-placement="right"
					data-content="删除成功">确认</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
	var dataTable;
	$(function() {
		
		var queryUrl = "${ctx}/permission/jsonDate";
		dataTable = $('#dataTable').bootstrapTable({
			url : queryUrl,//请求后台的URL
			method : "GET",
			toolbar : '#toolbar', //工具按钮用哪个容器
			singleSelect : true,//设置True 将禁止多选
			cache : false,//是否使用缓存
			pagination : false,//是否显示分页
			sidePagination : "server",//分页方式：client客户端分页，server服务端分页
			search : false,//是否显示表格搜索
			showHeader : true,
			showColumns : true,//是否显示所有的列（选择显示的列）
			showRefresh : true,//是否显示刷新按钮
			showToggle : false,//是否显示 切换试图（table/card）按钮
			minimumCountColumns : 2,//当列数小于此值时将隐藏内容列下拉框
			clickToSelect : true,//是否启用点击选中行
			checkboxHeader : false,//设置false 将在列头隐藏check-all checkbox
			height : 600,//行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
			treeView : true,
			treeId : "id",
			treeField : "name",
			treeRootLevel : 1,
			columns : [ {
				title : '选择',
				checkbox : true
			}, {
				field : 'name',
				title : '名称'
			}, {
				field : 'code',
				title : '编码'
			}, {
				field : 'url',
				title : '路径URL'
			}, {
				field : 'icon',
				title : '图标',
				formatter : function(value, row, index) {
					return "<i class='" + value + "'></i>";
				}
			}, {
				field : 'status',
				title : '状态',
				formatter : function(value, row, index) {
					var returnStr = "<div class='btn-group btn-group-sm'>";
					if (value == 1) {
						returnStr += "<button type='button' class='btn btn-info'>有效</button>";
						returnStr += "<button type='button' class='btn btn-default' onclick='updateStatus(" + row.id + ")'>" + cumSpace(6) + "</button>";
					} else {
						returnStr += "<button type='button' class='btn btn-default' onclick='updateStatus(" + row.id + ")'>" + cumSpace(6) + "</button>";
						returnStr += "<button type='button' class='btn btn-warning'>无效</button>";
					}
					returnStr += "</div>";
					return returnStr;
				}
			}, {
				field : 'id',
				title : '操作',
				width : 120,
				align : 'center',
				valign : 'middle',
				formatter : actionFormatter
			} ],
			onLoadSuccess : function() {

			},
			onLoadError : function() {
				alert("数据加载失败！");
			},
			onDblClickRow : function(row, element) {
				toUpdate(row.id, "detail");
			}
		});
	});

	//操作栏的格式化
	function actionFormatter(value, row, index) {
		var id = value;
		var result = "";
		result += "<a href='javascript:;' class='btn-xs btn-success' onclick=\"toUpdate('" + id + "', 'detail')\" title='查看'><span class='glyphicon glyphicon-search'></span></a> ";
		result += "<a href='javascript:;' class='btn-xs btn-primary' onclick=\"toUpdate('" + id + "', 'update')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a> ";
		result += "<a href='javascript:;' class='btn-xs btn-danger' onclick=\"showConfirm('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
		return result;
	}
	
	function cx() {
		// 刷新表格  
		dataTable.bootstrapTable('refresh', {url : "${ctx}/permission/jsonDate"});
	}

	function toCreate() {
		var parentId = 0;
		var jsonArray = dataTable.bootstrapTable('getSelections');
		if (jsonArray.length > 0) {
			parentId = jsonArray[0].id;
		}
		$.get('${ctx}/permission/toCreate/' + parentId, function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
	
	function toUpdate(id, action) {
		$.get('${ctx}/permission/toUpdate/' + action + '/' + id, function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
	
	function updateStatus(id) {
		$.get('${ctx}/permission/updateStatus/' + id, function(data) {
			if (data == "success") {
				cx();
			}
		});
	}
	
	var delId = 0;
	function showConfirm(id) {
		delId = id;
		$('#delModal').modal('show');
	}
	$("#delButton").click(function() {
		$(this).button('loading');
		$.get('${ctx}/permission/delete/' + delId, function(data) {
			if (data == "success") {
				cx();
				$("#delButton").popover('show');
				setTimeout(function(){
					$("#delButton").popover('hide');
					$('#delModal').modal('hide');
					$("#delButton").button('reset');
				}, 1000);
			}
		});
	});
</script>
