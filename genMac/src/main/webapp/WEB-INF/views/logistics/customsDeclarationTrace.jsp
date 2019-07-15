<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="chart-container"></div>
<script type="text/javascript">
$(function() {
	var datascource = ${datascource};

	$('#chart-container').orgchart({
		'data' : datascource,
		'nodeContent' : 'dataNo',
		'draggable' : true,
		'dropCriteria' : function($draggedNode, $dragZone, $dropZone) {
			if ($draggedNode.find('.content').text().indexOf('manager') > -1 && $dropZone.find('.content').text().indexOf('engineer') > -1) {
				return false;
			}
			return true;
		}
	}).children('.orgchart').on('nodedropped.orgchart',function(event) {
		console.log('draggedNode:'
					+ event.draggedNode.children('.title').text()
					+ ', dragZone:'
					+ event.dragZone.children('.title').text()
					+ ', dropZone:'
					+ event.dropZone.children('.title').text());
	});
});

function myCall(title, id) {
	var url = "";
	if (title.indexOf("到单") == 0) {
		url = '${ctx}/logistics/invoiceReg/detail/' + id;
	} else if (title.indexOf("交税") == 0) {
		url = '${ctx}/financial/payTaxes/detail/' + id;
	} else if (title.indexOf("入库") == 0) {
		url = '${ctx}/stock/inStock/detail/' + id;
	} else if (title.indexOf("送货") == 0 || title.indexOf("直发") == 0) {
		url = '${ctx}/logistics/sendGoods/detail/' + id;
	}
	if (url.length > 0) {
		detailDlg=$("#detailDlg").dialog({
			fit:true,
			cache: false,
			style:{borderWidth:0},
		    title: title + "明细",
		    href:url,
		    modal:true,
		    closable:false,
		    buttons:[{
				text:'关闭',
				handler:function(){
					detailDlg.panel('close');
				}
			}]
		});
	}
}
</script>
</body>
</html>