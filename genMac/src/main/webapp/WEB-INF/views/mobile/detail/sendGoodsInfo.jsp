<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">兹通知在右表格中的仓储单位</font><hr/>
	<font class="label-left" >仓库名称:</font>
	<font class="label-right">
		${obj.warehouse }
	</font>
	<hr/>
	
	<font class="label-left" >地址:</font>
	<font class="label-right">
		${obj.warehouseAddress }
	</font>
	<hr/>
	
	<font class="label-left" >邮编:</font>
	<font class="label-right">
		${obj.warehouseZipcode }
	</font>
	<hr/>
	
		<font class="label-left" >联系人:</font>
	<font class="label-right">
		${obj.warehouseContacts }
	</font>
	<hr/>
	
	<font class="label-left" >电话:</font>
	<font class="label-right">
		${obj.warehouseContactsPhone }
	</font>
	<hr/>
	
	<font class="label-left" >传真:</font>
	<font class="label-right">
		${obj.warehouseContactsFax }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">将右表中存储的货物</font><hr/>
	<font class="label-left" >合同号:</font>
	<font class="label-right">
		${obj.contractNo }
	</font>
	<hr/>
	
	<font class="label-left" >提单号:</font>
	<font class="label-right">
		${obj.invoiceNo }
	</font>
	<hr/>
	
	<font class="label-left" >入库单号:</font>
	<font class="label-right">
		${obj.inStockId }
	</font>
	<hr/>
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.sendGoodsGoods }">
			<listitem type="twoline" 
				caption="货物名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|单位:${goods.unit }|车架号:${goods.frameNo }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	
	<font class="label-left" >放货备注:</font>
	<font class="label-right">
		${obj.remark }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">权益转移给表格中提货人</font><hr/>
	<font class="label-left" >提货单位:</font>
	<font class="label-right">
		${obj.getGoodsUnit }
	</font>
	<hr/>
	
	<font class="label-left" >地址:</font>
	<font class="label-right">
		${obj.getGoodsUnitAddress }
	</font>
	<hr/>
	
	<font class="label-left" >邮编:</font>
	<font class="label-right">
		${obj.getGoodsUnitZipcode }
	</font>
	<hr/>
	
		<font class="label-left" >联系人:</font>
	<font class="label-right">
		${obj.getGoodsContacts }
	</font>
	<hr/>
	
	<font class="label-left" >电话:</font>
	<font class="label-right">
		${obj.getGoodsContactsPhone }
	</font>
	<hr/>
	
	<font class="label-left" >传真:</font>
	<font class="label-right">
		${obj.getGoodsContactsFax }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">中建材通用机械有限公司</font><hr/>
	<font class="label-left" >制单日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.createDate}" pattern="yyyy-MM-dd" />
	</font>
	<hr/>
	
	<font class="label-left" >最近修改时间:</font>
	<font class="label-right">
		${obj.createrName }
	</font>
	<hr/>
	
	<font class="label-left" >制单人:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
	</font>
	<hr/>
