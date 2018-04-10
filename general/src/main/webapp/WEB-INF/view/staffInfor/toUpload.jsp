<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <meta charset="UTF-8"/>
    <title>Krajee JQuery Plugins - &copy; Kartik</title>
    <link href="${ctx }/static/others/bootstrap-fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${ctx }/static/others/bootstrap-fileinput/themes/explorer-fa/theme.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="${ctx }/static/others/bootstrap-fileinput/js/plugins/sortable.js" type="text/javascript"></script>
    <script src="${ctx }/static/others/bootstrap-fileinput/js/fileinput.js" type="text/javascript"></script>
    <script src="${ctx }/static/others/bootstrap-fileinput/js/locales/fr.js" type="text/javascript"></script>
    <script src="${ctx }/static/others/bootstrap-fileinput/js/locales/es.js" type="text/javascript"></script>
    <script src="${ctx }/static/others/bootstrap-fileinput/themes/explorer-fa/theme.js" type="text/javascript"></script>
    <script src="${ctx }/static/others/bootstrap-fileinput/themes/fa/theme.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" type="text/javascript"></script>
</head>
	<div class="modal-dialog">
		<div class="modal-content">
		<form enctype="multipart/form-data">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
				上传excel
				</h4>
			</div>
			<div class="modal-body">
				<div class="file-loading">
		            <input id="fileExcel" class="file" type="file" multiple data-min-file-count="1">
		        </div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
			</form>
		</div>
	</div>
<script type="text/javascript">
$(function(){
	
	
});
$("#fileExcel").fileinput({
    theme: 'fa',
    uploadUrl: "${ctx}/staffInfor/readExcel/", // you must set a valid URL here else you will get an error
    allowedFileExtensions: ['xls', 'xlsx'],
    overwriteInitial: false,
    showPreview: false,
    maxFileSize: 1000,
    maxFilesNum: 10,
//     slugCallback: function (filename) {
//         return filename.replace('(', '_').replace(']', '_');
//     }
});


</script>