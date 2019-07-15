<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div>
	<form id="mainform" action="${ctx}/shop/goods/${action}" method="post">
	
	<div id="mainDiv" class="easyui-tabs" data-options="border:false">
		<div data-options="title: '基本信息', iconCls: false, refreshable: false">
			<table width="98%" class="tableClass">
				<tr>
					<th width="25%">商品名称</th>
					<td>
					<input type="hidden" name="id" value="${id }" />
					<input name="name" type="text" value="${goods.name }" class="easyui-validatebox"  data-options="required:true,validType:['length[0,255]']" />
					</td>
				</tr>
				<tr>
					<th>商品类型</th>
					<td><input id="goodsTypeId" name="goodsTypeId" type="text" value="${goods.goodsTypeId }" class="easyui-combobox" data-options="required:true"/></td>
				</tr>
				<tr>
					<th>封面</th>
					<td><input id="cover" name="cover" type="text" value="${goods.cover}" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']"/></td>
				</tr>
				<tr>
					<th>图片</th>
					<td><input id="img" name="img" type="text" value="${goods.img }" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]']"/></td>
				</tr>
				<tr>
					<th>商品单价</th>
					<td><input name="price" type="text" value="${goods.price}" class="easyui-validatebox" data-options="required:true,validType:['length[0,20]','numeric']"/></td>
				</tr>
				<tr>
					<th>市场价</th>
					<td><input name="marketPrice" type="text" value="${goods.marketPrice}" class="easyui-validatebox" data-options="required:false,validType:['numeric']"/></td>
				</tr>
				<tr>
					<th>邮费</th>
					<td><input name="postage" type="text" value="${goods.postage}" class="easyui-validatebox" data-options="required:false,validType:['numeric']"/></td>
				</tr>
				<tr>
					<th>销量</th>
					<td><input name="sales" type="text" value="${goods.sales}" class="easyui-validatebox" data-options="required:false,validType:['numeric']"/></td>
				</tr>
				<tr>
					<th>是否上架</th>
					<td>
						<input id="isSold" name="isSold" value="${goods.isSold}" style="width:50px;" data-options="panelHeight:'auto'" />
					</td>
				</tr>
				<tr>
					<th>介绍</th>
					<td><input name="introduce" type="text" value="${goods.introduce}" class="easyui-validatebox" data-options="required:false"/></td>
				</tr>
				<tr>
					<th>摘要</th>
					<td><textarea name="brief" rows="3" cols="40" class="easyui-validatebox">${goods.brief}</textarea></td>
				</tr>
			</table>
		</div>
		<div data-options="title: '商品明细', iconCls: false, refreshable: false">
			<input type="hidden" name="goodsChildJson" id="goodsChildJson"/>
			<%@ include file="/WEB-INF/views/shop/goodsChildForm.jsp"%>
		</div>
		<div data-options="title: '附件', iconCls: false, refreshable: false">
			<%@ include file="/WEB-INF/views/shop/goodsChildAccList.jsp"%>
		</div>
	</div>
	
	</form>
</div>
<script type="text/javascript">
$(function(){
	var tabIndex = 0;
	$('#mainDiv').tabs({
	    onSelect:function(title,index){
	    	if(!$('#mainform').form('validate')){
	    		$("#mainDiv").tabs("select", tabIndex);
	    	}else{
	    		tabIndex = index;
	    	}
	    }
	});
	//上级菜单
	$('#goodsTypeId').combotree({
		width:180,
		method:'GET',
	    url: '${ctx}/shop/goodsType/json',
	    idField : 'id',
	    textFiled : 'name',
		parentField : 'pid',
	    animate:true
	});
	
	$('#mainform').form({
	    onSubmit: function(){    
	    	var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
	    },    
	    success:function(data){   
	    	if("update"=="${action}")
	    		successTip(data,dg);
	    	else
	    		successTip(data,dg,d);
	    }
	}); 
});

$('#isSold').combobox({
	data:[{
	    "id":1,
	    "text":"是"
	},{
	    "id":0,
	    "text":"否"
	}],
    valueField:'id',
    textField:'text'
});

</script>
</body>
</html>