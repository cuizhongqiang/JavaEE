<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
</head>
<body style="padding: 0; margin: 0;">
	<div id="uploader">&nbsp;</div>

	<script type="text/javascript">
		var localUrl = getRootPath();
		var files = [];
		var errors = [];
		var type = 'file';
		var chunk = 0;
		var max_file_size = '100mb';
		var filters = {
			title : "文档",
			extensions : "zip,doc,docx,xls,xlsx,ppt,pptx"
		};
		$("#uploader").pluploadQueue(
				$.extend({
					runtimes : 'html5,html4',
					url : localUrl,
					max_file_size : max_file_size,
					file_data_name : 'file',
					unique_names : true,
					filters : [ filters ],
					flash_swf_url : 'plupload/plupload.flash.swf',
					init : {
						FileUploaded : function(uploader, file, response) {
							if (response.response) {
								var rs = $.parseJSON(response.response);
								if (rs.status) {
									files.push(file.name);
								} else {
									errors.push(file.name);
								}
							}
						},
						UploadComplete : function(uploader, fs) {
							var e = errors.length ? ",失败" + errors.length
									+ "个(" + errors.join("、") + ")。" : "。";
// 							alert("上传完成！共" + fs.length + "个。成功" + files.length
// 									+ e);
							$('#dgAcc').datagrid('reload');    // 重新加载附件页的数据表格
// 							target.window("close");
						}
					}
				}, (chunk ? {
					chunk_size : '1mb'
				} : {})));
		
		function getRootPath() {
			var row = dg.datagrid('getSelected');
			var accParentId = row.id;
			//获取当前网址，如 http://localhost:8080/shop/goods （pathName=window.document.location.pathname;）
			var curWwwPath = window.document.location.href;
			var curWwwPath = window.document.location.href;
			var pathName = window.document.location.pathname;
			var pos = curWwwPath.indexOf(pathName);
			var localhostPaht = curWwwPath.substring(0, pos);
			var projectName = pathName.substring(0, pathName.substr(1).indexOf(
					'/') + 1);
// 			alert(localhostPaht + projectName + "/accessory/upload/"
// 					+ accParentId);
			return (localhostPaht + projectName + "/accessory/upload/" + accParentId);
		};
	</script>
</body>
</html>
