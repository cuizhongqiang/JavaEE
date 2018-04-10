<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">自营(代理)进口结算联系单</font><hr/>
	<font class="label-left" >购物单位名称:</font>
	<font class="label-right">
		${obj.unitName }
	</font>
	<hr/>
	
	<font class="label-left" >地址:</font>
	<font class="label-right">
		${obj.address }
	</font>
	<hr/>
	
	<font class="label-left" >电话:</font>
	<font class="label-right">
		${obj.phone }
	</font>
	<hr/>
	
	<font class="label-left" >税务登记号:</font>
	<font class="label-right">
		${obj.taxNo }
	</font>
	<hr/>
	
	<font class="label-left" >开户行:</font>
	<font class="label-right">
		${obj.bankName }
	</font>
	<hr/>
	
	<font class="label-left" >账号:</font>
	<font class="label-right">
		${obj.bankNo }
	</font>
	<hr/>
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.importBillingGoods }">
			<listitem type="twoline" 
				caption="货物(应税劳务)名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|单位:${goods.unit }|单价:${goods.price }|销售金额:${goods.salesAmount }|税率:${goods.rateMain }|税额:${goods.taxMain }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	
	<font color="#0080FF" style="align:center">结算</font><hr/>
	<font class="label-left" >支付货款:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.payMoney }" />-<fmt:formatNumber type="number" value="${obj.payMoneySys }" />=<fmt:formatNumber type="number" value="${obj.payMoney - obj.payMoneySys }" />
	</font>
	<hr/>
	
	<font class="label-left" >到账货款:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.accountMoney }" />
	</font>
	<hr/>
	
	<font class="label-left" >银行手续费:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.bankPoundage }" />-<fmt:formatNumber type="number" value="${obj.bankPoundageSys }" />=<fmt:formatNumber type="number" value="${obj.bankPoundage - obj.bankPoundageSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >关税(监管费):</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.custom }" />-<fmt:formatNumber type="number" value="${obj.customSys }" />=<fmt:formatNumber type="number" value="${obj.custom - obj.customSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >增值税:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.vat }" />-<fmt:formatNumber type="number" value="${obj.vatSys }" />=<fmt:formatNumber type="number" value="${obj.vat - obj.vatSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >消费税:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.saleTax }" />-<fmt:formatNumber type="number" value="${obj.saleTaxSys }" />=<fmt:formatNumber type="number" value="${obj.saleTax - obj.saleTaxSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >报关:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.customsDeclaration }" />-<fmt:formatNumber type="number" value="${obj.customsDeclarationSys }" />=<fmt:formatNumber type="number" value="${obj.customsDeclaration - obj.customsDeclarationSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >运杂:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.shipMix }" />-<fmt:formatNumber type="number" value="${obj.shipMixSys }" />=<fmt:formatNumber type="number" value="${obj.shipMix - obj.shipMixSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >保险:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.insurance }" />-<fmt:formatNumber type="number" value="${obj.insuranceSys }" />=<fmt:formatNumber type="number" value="${obj.insurance - obj.insuranceSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >代理费(利润):</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.agencyFee }" />
	</font>
	<hr/>
	
	<font class="label-left" >其他(免表.录入等):</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.others }" />-<fmt:formatNumber type="number" value="${obj.othersSys }" />=<fmt:formatNumber type="number" value="${obj.others - obj.othersSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >利息:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.interest }" />
	</font>
	<hr/>
	
	<font class="label-left" >支付合计:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.payTotal }" />-<fmt:formatNumber type="number" value="${obj.payTotalSys }" />=<fmt:formatNumber type="number" value="${obj.payTotal - obj.payTotalSys }" />
	</font>
	<hr/>
	
	<font class="label-left" >本合同余额:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.balance }" />
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">代理</font><hr/>
	<font class="label-left" >客户名称:</font>
	<font class="label-right">
		${obj.agencyName }
	</font>
	<hr/>
	
	<font class="label-left" >备注:</font>
	<font class="label-right">
		${obj.remark }
	</font>
	<hr/>
