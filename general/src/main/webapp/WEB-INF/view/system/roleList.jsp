<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<h4 class="page-header">系统管理>>角色管理</h4>
	<div id="toolbar" class="btn-group">
		<button type="button" class="btn btn-default" onclick="toCreate();">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
		</button>
	</div>
	<div class="row">
		<div class="col-sm-8">
			<table id="dataTable"></table>
		</div>
		<div class="col-sm-4">
			<h3>权限列表</h3>
			<div class="form-group">
				<button type="button" class="btn btn-default" id="treeview-check-all">全选</button>
				<button type="button" class="btn btn-default" id="treeview-uncheck-all">全不选</button>
				<button type="button" class="btn btn-success" id="treeview-save">保存</button>
			</div>
			<div id="permissionTreeview" class=""></div>
		</div>
	</div>
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
	var permissionTreeview;
	$(function() {
		var queryUrl = "${ctx}/role/jsonDate";
		dataTable = $('#dataTable').bootstrapTable({
			url : queryUrl,//请求后台的URL
			method : "GET",
			//classes : "",
			toolbar : '#toolbar', //工具按钮用哪个容器
			striped : true,//是否显示行间隔色
			singleSelect : true,//设置True 将禁止多选
			cache : false,//是否使用缓存
			pagination : true,//是否显示分页
			sortable : true,
			sortName : "id",
			sortOrder : "desc",//排序方式
			silentSort : false,//设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效.
			sidePagination : "server",//分页方式：client客户端分页，server服务端分页
			pageNumber : 1,//初始化加载第一页，默认第一页,并记录
			pageSize : 10,//每页的记录行数
			pageList : [ 10, 20, 50, 100 ],//可供选择的每页的行数
			search : false,//是否显示表格搜索
			showHeader : true,
			showPaginationSwitch : true,//是否显示数据条数选择框
			showColumns : true,//是否显示所有的列（选择显示的列）
			showRefresh : true,//是否显示刷新按钮
			minimumCountColumns : 2,//当列数小于此值时将隐藏内容列下拉框
			clickToSelect : true,//是否启用点击选中行
			checkboxHeader : false,//设置false 将在列头隐藏check-all checkbox
			height : 600,//行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
			idField : "id",
			uniqueId : "id",//每一行的唯一标识，一般为主键列
			showToggle : true,//是否显示 切换试图（table/card）按钮
			cardView : false,//设置为 true将显示card视图，适用于移动设备。否则为table试图，适用于pc
			detailView : false,//显示详细页面模式
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
			onCheck : function(row) {
				getPermissionTreeview(row.id);
			},
			onUncheck : function(row) {
				getPermissionTreeview(0);
			},
			onDblClickRow : function(row, element) {
				toUpdate(row.id, "detail");
			}
		});
		
		getPermissionTreeview(0);
		
		$('#treeview-check-all').on('click', function (e) {
			permissionTreeview.treeview('checkAll', { silent: true });
		});
		$('#treeview-uncheck-all').on('click', function (e) {
			permissionTreeview.treeview('uncheckAll', { silent: true });
		});
		$('#treeview-save').on('click', function (e) {
			var jsonArray = dataTable.bootstrapTable('getSelections');
			var treeViewArray = permissionTreeview.treeview('getChecked');
			var strArray = [];
			for (var i = 0; i < treeViewArray.length; i++) {
				strArray.push(treeViewArray[i].id);
			}
			$.ajax({
				type: 'POST',
				url: "${ctx}/role/permissionSave",
				data: {permissionIds : strArray.join(","), roleId : jsonArray[0].id},
				success: function(data){
					if (data == "success") {
						alert("保存成功");
					}
				},
				error:function(eror){
					alert("系统繁忙，请稍后再操作！");
				}
			});
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
		dataTable.bootstrapTable('refresh', {url : "${ctx}/role/jsonDate"});
	}
	
	function getPermissionTreeview(roleId) {
		if (roleId == 0) {
			$('#treeview-check-all').attr("disabled","disabled");
			$('#treeview-uncheck-all').attr("disabled","disabled");
			$('#treeview-save').attr("disabled","disabled");
		} else {
			$('#treeview-check-all').removeAttr("disabled");
			$('#treeview-uncheck-all').removeAttr("disabled");
			$('#treeview-save').removeAttr("disabled");
		}
		$.get('${ctx}/role/getTreeView/' + roleId, function(data) {
			permissionTreeview = $('#permissionTreeview').treeview({
				data: data,
				showIcon: false,
				showCheckbox: true,
				onNodeChecked: function(event, node) {
					
				},
				onNodeUnchecked: function(event, node) {
					
				}
			});
		});
	}
	
	function toCreate() {
		$.get('${ctx}/role/toCreate', function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
	
	function toUpdate(id, action) {
		$.get('${ctx}/role/toUpdate/' + action + '/' + id, function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
	
	var delId = 0;
	function showConfirm(id) {
		delId = id;
		$('#delModal').modal('show');
	}
	$("#delButton").click(function() {
		$(this).button('loading');
		$.get('${ctx}/role/delete/' + delId, function(data) {
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