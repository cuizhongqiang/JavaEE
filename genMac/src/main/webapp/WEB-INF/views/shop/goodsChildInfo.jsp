<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.genMac.shop.entity.Goods"%>
<%@page import="com.cbmie.genMac.shop.entity.GoodsChild"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="childTB"></table>
<%
	Goods goods = (Goods)request.getAttribute("goods");
	List<GoodsChild> gcList = goods.getGoodsChild();
	ObjectMapper objectMapper = new ObjectMapper();
	String gcJson = objectMapper.writeValueAsString(gcList);
	request.setAttribute("gcJson", gcJson);
%>
<script type="text/javascript">
var childTB;
$(function(){
	childTB=$('#childTB').datagrid({
		data : JSON.parse('${gcJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
	    columns:[[    
			{field:'id',title:'id',hidden:true},
			{field:'name',title:'名称'},
			{field:'type',title:'类型',
				formatter:function(value,row,index){
					var val;
					if(value==1){
						val='重要';
					}else if(value==2){
						val='次要';
					}else{
						val='一般';
					}
					return val;
				}
			},
	        {field:'count',title:'数量'},
	        {field:'createDate',title:'创建时间'},
	        {field:'updateDate',title:'修改时间'},
	        {field:'state',title:'是否生效'}
	    ]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false
	});
});
</script>