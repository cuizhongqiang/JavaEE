<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.cbmie.genMac.shop.entity.Goods"%>
<%@page import="com.cbmie.genMac.shop.entity.GoodsChild"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="childToolbar" style="padding:5px;height:auto">
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">新增</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">重置</a>
	<span class="toolbar-item dialog-tool-separator"></span>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="getChanges()">变化条数</a>
</div>
<table id="childTB" data-options="onClickRow: onClickRow">
</table>
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
		{field:'name',title:'名称',width:20,editor:{type:'validatebox',options:{required:true}}},
		{field:'type',title:'类型',width:20,
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
			},
			editor:{
				type:'combobox',
				options:{
					panelHeight:'auto',
					valueField:'type',
					textField:'typeText',
					method:'get',
					url:'${ctx}/shop/goodsChild/getType',
					required:true
				}
			}
		},
        {field:'count',title:'数量',width:20,editor:{type:'numberbox',options:{precision:0}}},
        {field:'createDate',title:'创建时间',width:30,
        	formatter: function(value,row,index){
        		if(value == null){
        			return "";
        		} else {
	        		var date = new Date(value);
	                var year = date.getFullYear().toString();
	                var month = (date.getMonth() + 1);
	                var day = date.getDate().toString();
	                var hour = date.getHours().toString();
	                var minutes = date.getMinutes().toString();
	                var seconds = date.getSeconds().toString();
	                if (month < 10) {
	                    month = "0" + month;
	                }
	                if (day < 10) {
	                    day = "0" + day;
	                }
	                if (hour < 10) {
	                    hour = "0" + hour;
	                }
	                if (minutes < 10) {
	                    minutes = "0" + minutes;
	                }
	                if (seconds < 10) {
	                    seconds = "0" + seconds;
	                }
	                return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
        		}
			}
        },
        {field:'updateDate',title:'修改时间',width:30,
        	formatter: function(value,row,index){
        		if(value == null){
        			return "";
        		} else {
	        		var date = new Date(value);
	                var year = date.getFullYear().toString();
	                var month = (date.getMonth() + 1);
	                var day = date.getDate().toString();
	                var hour = date.getHours().toString();
	                var minutes = date.getMinutes().toString();
	                var seconds = date.getSeconds().toString();
	                if (month < 10) {
	                    month = "0" + month;
	                }
	                if (day < 10) {
	                    day = "0" + day;
	                }
	                if (hour < 10) {
	                    hour = "0" + hour;
	                }
	                if (minutes < 10) {
	                    minutes = "0" + minutes;
	                }
	                if (seconds < 10) {
	                    seconds = "0" + seconds;
	                }
	                return year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
        		}
			}
        },
        {field:'state',title:'是否生效',width:20,
			editor:{
				type:'checkbox',
				options:{
					on:'生效',
					off:'无效'
				}
			}
        }
    ]],
    enableHeaderClickMenu: false,
    enableHeaderContextMenu: false,
    enableRowContextMenu: false,
    toolbar:'#childToolbar'
	});
});

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#childTB').datagrid('validateRow', editIndex)){
		var ed = $('#childTB').datagrid('getEditor', {index:editIndex,field:'type'});
		var typeText = $(ed.target).combobox('getText');
		$('#childTB').datagrid('getRows')[editIndex]['type'] = typeText;
		$('#childTB').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#childTB').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#childTB').datagrid('selectRow', editIndex);
		}
	}
}
function append(){
	if (endEditing()){
		$('#childTB').datagrid('appendRow', {state:'生效'});
		editIndex = $('#childTB').datagrid('getRows').length-1;
		$('#childTB').datagrid('selectRow', editIndex)
				.datagrid('beginEdit', editIndex);
	}
}
function removeit(){
	if (editIndex == undefined){return}
	$('#childTB').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}
function accept(){
	if (endEditing()){
		var rows = $('#childTB').datagrid('getRows');
		$('#childTB').datagrid('acceptChanges');
		$('#goodsChildJson').val(JSON.stringify(rows));
	}
}
function reject(){
	$('#childTB').datagrid('rejectChanges');
	editIndex = undefined;
}
function getChanges(){
	var rows = $('#childTB').datagrid('getChanges');
	$.messager.alert('提示', rows.length+' 行被修改！', 'info');
}
</script>