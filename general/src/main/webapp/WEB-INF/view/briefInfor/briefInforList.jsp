<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<h4 class="page-header">数字化名片>>简介信息</h4>
	<div class="panel panel-primary" id="accordion">
		<div class="panel-heading">
			<h3 class="panel-title">
			<a data-toggle="collapse" data-parent="#accordion" href="#searchBody">
				查询条件
				<span id="prompt" class="glyphicon glyphicon-chevron-up"></span>
			</a>
			</h3>
		</div>
		<div id="searchBody" class="panel-body collapse in">
			<form class="form-inline" id="searchForm">
				<div class="form-group">
			    	<label class="control-label">简介类型:</label>
			    	<select class="form-control" id="filter_LIKEI_type" name="filter_LIKEI_type">
			    		<option value="">--选择简介类型--</option>
                        <option value="0">公司</option>
                        <option value="1">项目</option>
                        <option value="2">会议</option>
                    </select>
			  	</div>
			  	<div class="form-group">
			    	<label class="control-label">标题:</label>
			    	<input type="text" class="form-control" id="filter_LIKES_title" name="filter_LIKES_title" placeholder="标题">
			  	</div>
			  	<button type="button" class="btn btn-info" onclick="cx()">查询</button>
			  	<button type="button" class="btn btn-warning" onclick="reset()">重置</button>
			</form>
		</div>
	</div>
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
		
		$('#searchBody').on('show.bs.collapse', function () {
			$('#prompt').attr("class", "glyphicon glyphicon-chevron-up");
		});
		
		$('#searchBody').on('hide.bs.collapse', function () {
			$('#prompt').attr("class", "glyphicon glyphicon-chevron-down");
		});
		
		var queryUrl = "${ctx}/briefInfor/jsonDate?" + $("#searchForm").serialize();
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
			height : 600,//行高，如果没有设置height属性，表格自动根据记录条数设置表格高度
			idField : "id",
			uniqueId : "id",//每一行的唯一标识，一般为主键列
			showToggle : true,//是否显示 切换试图（table/card）按钮
			cardView : false,//设置为 true将显示card视图，适用于移动设备。否则为table试图，适用于pc
			detailView : false,//显示详细页面模式
			queryParamsType : '', //默认值为 'limit' ,在默认情况下 传给服务端的参数为：offset,limit,sort // 设置为 '' 在这种情况下传给服务器的参数为：pageSize,pageNumber
			columns : [ {
				title : '序号',
				formatter : function(value, row, index) {
					return index + 1;
				}
			}, {
				field : 'type',
				title : '类型',
				sortable : true,
				formatter : function(value) {
					if (value == 0) {
						return "公司";
					} else if (value == 1) {
						return "项目";
					} else if (value == 2) {
						return "会议";
					}
				}
			}, {
				field : 'title',
				title : '标题'
			}, {
				field : 'articleContent',
				title : '文章内容',
				formatter : function(value, row, index) {
					value = value ? value : '';
					var length = value.length;
					if (length && length > 20) {
						return value.substring(0, 20) + "...";
					} else {
						return value.substring(0, length);
					}
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
		dataTable.bootstrapTable('refresh', {url : "${ctx}/briefInfor/jsonDate?" + $("#searchForm").serialize()});
	}

	function toCreate() {
		$.get('${ctx}/briefInfor/toCreate', function(data) {
			$("#myModal").html(data);
		});
		$('#myModal').modal('show');
	}
	
	function reset() {
		$("#searchForm")[0].reset();
	}
	
	function toUpdate(id, action) {
		$.get('${ctx}/briefInfor/toUpdate/' + action + '/' + id, function(data) {
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
		$.get('${ctx}/briefInfor/delete/' + delId, function(data) {
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
