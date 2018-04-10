<%@page import="com.cbmie.genMac.logistics.entity.SendGoods"%>
<%@page import="com.cbmie.genMac.logistics.entity.SendGoodsGoods"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
</head>
<body>
<div id="mainDiv" class="easyui-tabs" data-options="border: false">
<div data-options="title: '发货通知', iconCls: false, refreshable: false">
<table class="tableClass" width="98%">
	<tr>
		<td align="center" colspan="7" style="font-size: 16px;font-weight: bold;border: none;padding-bottom: 10px;letter-spacing: 4px;">发货通知</td>
	</tr>
	<tr>
		<td align="right" colspan="7" style="font-weight: bold;border: none;">
			编号:&nbsp;&nbsp;${sendGoods.sendGoodsNo }
		</td>
	</tr>
	<c:if test="${!empty sendGoods.inStockId}">
	<tr>
		<td rowspan="2" style="font-weight: bold;" width="10%">兹通知在右表格中的仓储单位</td>
		<th>仓库名称</th>
		<td>${sendGoods.warehouse }</td>
		<th>地址</th>
		<td>${sendGoods.warehouseAddress }</td>
		<th>邮编</th>
		<td>${sendGoods.warehouseZipcode }</td>
	</tr>
	<tr>
		<th>联系人</th>
		<td>${sendGoods.warehouseContacts }</td>
		<th>电话</th>
		<td>${sendGoods.warehouseContactsPhone }</td>
		<th>传真</th>
		<td>${sendGoods.warehouseContactsFax }</td>
	</tr>
	</c:if>
	<tr>
		<td rowspan="3" style="font-weight: bold;">将右表中存储的货物</td>
		<th>合同号</th>
		<td>${sendGoods.contractNo }</td>
		<th>提单号</th>
		<td>${sendGoods.invoiceNo }</td>
		<th>入库单号</th>
		<td>${sendGoods.inStockId }</td>
	</tr>
	<tr>
		<td colspan="6" style="padding: 0px;">
			<table id="sendGoodsGoodsInfo"></table>
			<%
				SendGoods sendGoods = (SendGoods)request.getAttribute("sendGoods");
				List<SendGoodsGoods> sggList = sendGoods.getSendGoodsGoods();
				ObjectMapper objectMapper = new ObjectMapper();
				String sggJson = objectMapper.writeValueAsString(sggList);
				request.setAttribute("sggJson", sggJson);
			%>
		</td>
	</tr>
	<tr>
		<th>放货备注</th>
		<td colspan="5">${sendGoods.remark }</td>
	</tr>
	<tr>
		<td rowspan="2" style="font-weight: bold;">权益转移给表格中提货人</td>
		<th>提货单位</th>
		<td>${sendGoods.getGoodsUnit }</td>
		<th>地址</th>
		<td>${sendGoods.getGoodsUnitAddress }</td>
		<th>邮编</th>
		<td>${sendGoods.getGoodsUnitZipcode }</td>
	</tr>
	<tr>
		<th>联系人</th>
		<td>${sendGoods.getGoodsContacts }</td>
		<th>电话</th>
		<td>${sendGoods.getGoodsContactsPhone }</td>
		<th>传真</th>
		<td>${sendGoods.getGoodsContactsFax }</td>
	</tr>
	<c:if test="${!workflow }">
	<tr>
		<td colspan="2" height="48px" style="border: none;">
			经办:&nbsp;&nbsp;<span></span>
		</td>
		<td style="border: none;">
			经理:&nbsp;&nbsp;<span id="leaderuser"></span>
		</td>
		<td colspan="2" style="border: none;">
			财务:&nbsp;&nbsp;<span id="glaccountant"></span>
		</td>
		<td colspan="2" style="border: none;">
			签发日期:&nbsp;&nbsp;<span><fmt:formatDate value="${sendGoods.sendDate }" /></span>
		</td>
	</tr>
	<tr>
		<td colspan="7" style="border: none;">
			&nbsp;&nbsp;申明：<br />
			&nbsp;&nbsp;&nbsp;&nbsp;此发货通知自签发日期五个工作日内生效，作发货使用，不可转让，收到此发货单单位须联系我司核实真伪，请在发货通知有效期内为提货人办理完成提货或货权变更转存手续或者按约将货物交付给提货人，仓储单位承担失察过失和逾期不办理全责。
		</td>
	</tr>
	</c:if>
</table>
</div>
<div data-options="title: '送货提示', iconCls: false, refreshable: false">
<table id="tab2" width="100%">
<tr>
<td>
	<center style="font-size: 18px;font-weight: bold;letter-spacing: 4px;">送货提示</center>
	<br/>
	<p style="font-size: 16px;letter-spacing: 2px;padding: 5px;line-height: 2;">
		<font style="font-weight: bold;">致尊敬的${sendGoods.getGoodsUnit }:</font>
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;贵公司委托我司代理进口的<c:forEach items="${sendGoods.sendGoodsGoods }" var="item" varStatus="status"><c:if test="${status.index > 0 }">、</c:if>${item.goodsCategory }</c:forEach>（合同编号：${sendGoods.contractNo }），现已经清关完毕，货物将于近日到达公司。为保障贵司权益，特提示如下：
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;1、该货物将由物流公司运至贵公司指定场地，贵司如安排跟车押运，请提前告知，以便我司安排物流公司办理。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;2、为确保货物符合贵司购进之要求和进口合同之约定，请贵司<font style="font-weight: bold;">及时联系外商确定安装时间，早日进行生产调试，</font>避免因超期无法主张质保责任。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;3、根据国家商检法有关规定：<font style="font-weight: bold;">法检商品未经检验，不得擅自开箱和使用，</font>否则将给予行政处罚；构成犯罪的，将追究刑事责任。为此，特提醒贵司请主动与所属地商检局联系检验事宜，未经商检局同意或检验千万不要擅自开箱和使用。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;4、根据国家海关法有关规定：监管期限内的减免税设备和物品属于<font style="font-weight: bold;">海关监管货物，未经海关许可，不得挪用他用火办理抵押</font>。请贵司依据海关法及有关政策规定合法使用、保管和处置上述货物。
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;感谢贵司选择和我司合作，在此特致谢意并恭祝贵司生意兴隆！
		<br/>
		&nbsp;&nbsp;&nbsp;&nbsp;此致
		<br/>
		敬礼！
	</p>
</td>
</tr>
<tr>
<td>
	<p align="right" style="font-size: 16px;letter-spacing: 2px;padding: 10px;line-height: 2;">
		中建材通用机械有限公司
		<br/>
		<fmt:formatDate value="${sendGoods.sendDate }" pattern="yyyy年MM月dd日" />&nbsp;&nbsp;&nbsp;&nbsp;
	</p>
</td>
</tr>
</table>
</div>
</div>
<table id="sysTable" width="98%" class="tableClass">
	<tr>
		<th width="15%">制单日期</th>
		<td width="15%">
		<fmt:formatDate value="${sendGoods.createDate}" pattern="yyyy-MM-dd" />
		</td>
		<th width="15%">制单人</th>
		<td width="20%">${sendGoods.createrName }</td>
		<th width="15%">最近修改时间</th>
		<td width="20%"><fmt:formatDate value="${sendGoods.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</table>
<script type="text/javascript">
$(function(){
	$('#sendGoodsGoodsInfo').datagrid({
		data : JSON.parse('${sggJson}'),
	    fit : false,
		fitColumns : true,
		border : false,
		striped:true,
		idField : 'id',
		rownumbers:true,
		singleSelect:true,
		extEditing:false,
		showFooter:true,
		columns:[[
			{field:'goodsCategory',title:'货物名称',width:270},
			{field:'specification',title:'规格型号',width:170},
			{field:'frameNo',title:'车架号',width:160},
			{field:'unit',title:'单位',width:80},
			{field:'amount',title:'数量',width:140}
		]],
	    enableHeaderClickMenu: false,
	    enableHeaderContextMenu: false,
	    enableRowContextMenu: false,
	    onLoadSuccess:function(data){
	    	var salesAmountTotal = 0;//销售金额合计
			var amountTotal = 0;//数量合计
			for(var i = 0; i < data.rows.length; i++){
				salesAmountTotal += Number(data.rows[i].salesAmount);
				amountTotal += Number(data.rows[i].amount);
			}
	    	// 更新页脚行
	    	$(this).datagrid('reloadFooter', [{'salesAmount':salesAmountTotal, 'amount':amountTotal}]);
	    }
	});
	
	//商务经理
	$.ajax({
		url : '${ctx}/workflow/getNewComments',
		data : {processInstanceId:'${importBilling.processInstanceId}', taskAssignee:'leaderuser'},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#leaderuser').text(data);
		}
	});
	
	//总账会计
	$.ajax({
		url : '${ctx}/workflow/getNewComments',
		data : {processInstanceId:'${importBilling.processInstanceId}', taskAssignee:'glaccountant'},
		type : 'get',
		cache : false,
		success : function(data) {
			$('#glaccountant').text(data);
		}
	});
});
</script>
</body>
</html>