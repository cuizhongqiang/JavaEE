<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

</head>
<body>
<div id="tb" style="padding:5px;height:auto">
	<form id="searchFrom" action="">
	    <input type="text" name="goodsNameSpecification" class="easyui-validatebox" data-options="width:150,prompt: '商品大类名称或规格型号'"/>
		<input type="text" name="filter_EQS_storageUnit" class="easyui-combobox" data-options="
			width:150,
			prompt: '仓库名称',
			method:'get',
			url:'${ctx}/baseinfo/warehouse/filter?filter_EQS_status=正常',
			valueField : 'warehouseName',
			textField : 'warehouseName'
		"/>
		<input type="text" name="filter_LIKES_invoiceNo" class="easyui-validatebox" data-options="width:150,prompt: '提单号'"/>
		<input type="text" id="startTime" name="filter_BETWEEND_inStockDate_start" class="Wdate" placeholder="入库开始日期"/> - 
		<input type="text" id="endTime" name="filter_BETWEEND_inStockDate_end" class="Wdate" placeholder="入库结束日期"/>
		<span class="toolbar-item dialog-tool-separator"></span>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-search" onclick="cx()">查询</a>
	    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="reset()">重置</a>
	</form>
</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
var dg;
$(function(){
	dg=$('#dg').datagrid({
		method: "get",
	    url:'${ctx}/stock/queryStock/json',
	    fit : true,
		fitColumns : true,
		border : false,
		striped:true,
		scrollbarSize : 0,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		frozenColumns:[[
			{field:'goodsNameSpecification',title:'商品大类/规格型号',width:180},
			{field:'warehouseName',title:'仓库名称',width:180},
			{field:'invoiceNo',title:'提单号',width:150}
		]],
	    columns:[
	    	[
			{"title":"入库","colspan":3},
			{"title":"出库","colspan":3}
	  	    ],
	    	[
			{field:'inStockId',title:'编号',width:10},
			{field:'inStockAmount',title:'数量',width:10},
			{field:'inStockDate',title:'日期',width:10},
			{field:'sendGoodsNo',title:'编号',width:10},
			{field:'sendGoodsAmount',title:'数量',width:10,
				styler:function(value, row, index){
					if(value > row.inStockAmount){
						return 'color:red;';
					}
				}
			},
			{field:'sendDate',title:'日期',width:10}
	    	]
	   	],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    toolbar:'#tb',
	    groupField:'goodsNameSpecification',
	    view:groupview,
	    groupFormatter:function(value, rows){
	    	var inTotal = 0;
	    	var outTotal = 0;
	    	var profit = 0;
	    	var loss = 0;
	    	var warehouseNameArray = [];
	    	for(var i = 0; i < rows.length; i++){
	    		inTotal += rows[i].inStockAmount;
	    		outTotal += rows[i].sendGoodsAmount;
	    		warehouseNameArray.push(rows[i].warehouseName);
	    	}
	    	$.ajax({
				type:'POST',
				url:"${ctx}/stock/queryStock/findPlanSum",
				data:{warehouseName:warehouseNameArray.join(','),goodsNameSpecification:value},
				async:false,
				dataType:'json',
				success: function(data){
					if(data[0] != null){
						profit = data[0];
					}
					if(data[1] != null){
						loss += data[1];
					}
				}
			});
	    	var bookRemain = inTotal - outTotal;
	    	var remain = bookRemain + profit - loss;
	    	return value + "，入库总量：" + inTotal + "，出库总量：" + outTotal + "，账面剩余：" + bookRemain + "，盘盈：" + profit + "，盘亏：" + loss + "，实际剩余：" + remain;
	    }
	});
	
	$('#startTime').bind('click',function(){
	    WdatePicker({doubleCalendar:true,startDate:'%y-{%M-1}-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true,maxDate:'#F{$dp.$D(\'endTime\')||\'%y-%M-%d\'}',onpicked:function(){endTime.click();}});
	});
	$('#endTime').bind('click',function(){
	    WdatePicker({doubleCalendar:true,startDate:'%y-{%M-1}-%d',minDate:'#F{$dp.$D(\'startTime\')}',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',autoPickDate:true});
	});
});

//创建查询对象并查询
function cx(){
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj); 
}

function reset(){
	$("#searchFrom")[0].reset();
	$(".easyui-my97").combo('clear');
	$(".easyui-combobox").combo('clear');
	var obj=$("#searchFrom").serializeObject();
	dg.datagrid('reload',obj);
}
</script>
</body>
</html>