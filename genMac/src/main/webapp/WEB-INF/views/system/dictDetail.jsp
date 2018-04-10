<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.system.entity.DictChild"%>
<%@page import="java.util.List"%>
<%@page import="com.cbmie.system.entity.DictMain"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table id="dictChild"></table>
<%
	DictMain dictMain = (DictMain)request.getAttribute("dictMain");
	List<DictChild> dcList = dictMain.getDictChild();
	ObjectMapper objectMapper = new ObjectMapper();
	String dcJson = objectMapper.writeValueAsString(dcList);
	request.setAttribute("dcJson", dcJson);
%>
<script type="text/javascript">
var dictChild;
$(function(){
	dictChild=$('#dictChild').datagrid({
    data:JSON.parse('${dcJson}'),
    fit : true,
	fitColumns : true,
	border : false,
	striped:true,
	idField : 'id',
	rownumbers:true,
	singleSelect:true,
    columns:[[    
		{field:'id',title:'id',hidden:true},
		{field:'name',title:'名称'},
		{field:'value',title:'值'},
		{field:'code',title:'编码'},
		{field:'scode',title:'简码'},
		{field:'orderNum',title:'顺序',sortable:true},
		{field:'remark',title:'备注'}
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    remoteSort:false
});
});

</script>