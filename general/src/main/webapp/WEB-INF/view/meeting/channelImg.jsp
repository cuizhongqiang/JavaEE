<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					${name }-渠道二维码
				</h4>
			</div>
			<div class="modal-body">
				<p style="text-align: center;">
					<img src="${ctx }/template/qrCode/channel/${id }/${name }.png"/>
				</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
<script type="text/javascript">
	
</script>