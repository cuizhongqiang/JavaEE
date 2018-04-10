<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<form id="mainForm" class="form-horizontal" role="form">
	<input type="hidden" name="id" value="${staffInfor.id }">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="myModalLabel">
					<c:choose>
						<c:when test="${action eq 'create' }">
							新增
						</c:when>
						<c:when test="${action eq 'update' }">
							修改
						</c:when>
						<c:when test="${action eq 'detail' }">
							查看
						</c:when>
					</c:choose>
					<c:if test="${action != 'create' }">
						<p style="text-align: center;"><img id="imgAra" src="${ctx }/staffInfor/avatarView/${staffInfor.accId }" 
							onerror="this.src='${ctx}/static/images/defaultUser.png'" onclick="toAddImg()" style="width: 150px; height: 150px;"/>
					</c:if>
				</h4>
			</div>
			<div class="modal-body">
				<div id="myModalChild">
				</div>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">姓名</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="realName" placeholder="姓名" name="realName" value="${staffInfor.realName }">
					</div>
				</div>
				<div class="form-group">
					<label for="department" class="col-sm-2 control-label">部门</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="department" placeholder="部门" name="department" value="${staffInfor.department }">
					</div>
				</div>
				<div class="form-group">
					<label for="compName" class="col-sm-2 control-label">公司名称</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="compName" placeholder="公司名称" name="compName" value="${staffInfor.compName }">
					</div>
				</div>
				<div class="form-group">
					<label for="title" class="col-sm-2 control-label">Title（对外）</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="title" placeholder="Title（对外）" name="title" value="${staffInfor.title }">
					</div>
				</div>
				<div class="form-group">
					<label for="telphone" class="col-sm-2 control-label">电话</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="telphone" placeholder="电话" name="telphone" value="${staffInfor.telphone }">
					</div>
				</div>
				<div class="form-group">
					<label for="mobile" class="col-sm-2 control-label">手机</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="mobile" placeholder="手机" name="mobile" value="${staffInfor.mobile }">
					</div>
				</div>
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">电子邮箱</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="email" placeholder="电子邮箱" name="email" value="${staffInfor.email }">
					</div>
				</div>
				<div class="form-group">
					<label for="address" class="col-sm-2 control-label">公司地址</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="address" placeholder="公司地址" name="address" value="${staffInfor.address }">
					</div>
				</div>
				<div class="form-group">
					<label for="website" class="col-sm-2 control-label">网址</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="website" placeholder="网址" name="website" value="${staffInfor.website }">
					</div>
				</div>
				<div class="form-group">
					<label for="fax" class="col-sm-2 control-label">传真</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="fax" placeholder="传真" name="fax" value="${staffInfor.fax }">
					</div>
				</div>
				<c:if test="${action eq 'detail' }">
					<div class="form-group">
						<label for="createDate" class="col-sm-2 control-label">录入时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${staffInfor.createDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
					<div class="form-group">
						<label for="updateDate" class="col-sm-2 control-label">修改时间</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="<fmt:formatDate value='${staffInfor.updateDate }' pattern='yyyy-MM-dd HH:mm:ss'/>">
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label for="comment" class="col-sm-2 control-label">关联简介</label>
					<div class="col-sm-10">
						<select class="form-control" id="relationBrief" name='relationBrief'>
							<c:forEach var="briefInfor"   items="${briefInforList }"  >
								<option value='${briefInfor.id}' <c:if test="${relationBrief.comment eq briefInfor.id }">selected="selected"</c:if>>${briefInfor.title}</option>
                  			</c:forEach>
                        </select>
					</div>
				</div>
				<div class="form-group">
					<label for="realName" class="col-sm-2 control-label">个人简介</label>
					<div class="col-sm-10">
						<textarea style="width: 100%;height: 90px;" name="comment" maxlength="500">${staffInfor.comment }</textarea>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<c:if test="${action ne 'detail' }">
					<button type="button" class="btn btn-primary" id="submitButton"
						data-loading-text="Loading..."
						data-container="body" data-toggle="popover" data-placement="right"
						data-content="保存成功">提交保存</button>
				</c:if>
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
	</form>
<script type="text/javascript">
$(function(){
	$('#mainForm').bootstrapValidator({
		message: '这个值无效',
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			realName: {
				message: '姓名无效',
                validators: {
                    notEmpty: {
                        message: '姓名是必需的，不能是空的'
                    },
                    stringLength: {
                        min: 2,
                        max: 30,
                        message: '姓名必须大于等于2，长度小于30'
                    }
                }
			},
			mobile: {
                validators: {
                	notEmpty: {
						message: '手机号码不能为空'
					},
                    phone: {
                        message: '输入不是有效的手机号码',
                        country: 'CN'
                    }
                }
            },
            email: {
                validators: {
                	notEmpty: {
						message: '电子邮箱不能为空'
					},
                    emailAddress: {
                        message: '输入不是一个有效的电子邮件地址'
                    }
                }
            },
            compName: {
                validators: {
                    notEmpty: {
                        message: '公司名称不能为空'
                    }
                }
            }
		}
	});
	
	$("#submitButton").click(function() {
		/*手动验证表单，当是普通按钮时。*/
		var bootstrapValidator = $("#mainForm").data('bootstrapValidator');
		bootstrapValidator.validate();
		if (!bootstrapValidator.isValid()) {
			return false;
		}
		$(this).button('loading');
		$.post("${ctx }/staffInfor/save/${action}", $("#mainForm").serialize(), function(data) {
			if (data == "success") {
				cx();
				$("#submitButton").popover('show');
				setTimeout(function(){
					$("#submitButton").popover('hide');
// 					if ('${action}' == 'create') {
						$('#myModal').modal('hide');
// 					}
					$("#submitButton").button('reset');
				}, 1000);
			}
		});
	});
	
});

function toAddImg(){
	if('${staffInfor.id}'!=""&&'${action}'=='update'){
		$.get('${ctx}/staffInfor/toImg/${staffInfor.id}', function(data) {
			$("#myModalChild").html(data);
		});
		$('#myModalChild').show();
	}else{
//			$.messager.alert('提示','请保存之后上传头像','info');
	}
	
}
</script>