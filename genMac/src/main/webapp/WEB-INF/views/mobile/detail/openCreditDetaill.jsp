<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font class="label-left" >外合同号:</font>
	<font class="label-right">
		${obj.foreignContractNo }
	</font>
	<hr/>
	
	<font class="label-left" >内合同号:</font>
	<font class="label-right">
		${obj.contractNo }
	</font>
	<hr/>
	
	<font class="label-left" >国内客户:</font>
	<font class="label-right">
		${my:getAffiliatesById(obj.customerId).customerName }
	</font>
	<hr/>
	
	<font class="label-left" >供应外商:</font>
	<font class="label-right">
		${my:getAffiliatesById(obj.foreignId).customerName }
	</font>
	<hr/>
	
	<font class="label-left" >合同原币金额:</font>
	<font class="label-right">
		${obj.currency }<fmt:formatNumber type="number" value="${obj.originalCurrency }" />
	</font>
	<hr/>
	
	<font class="label-left" >折美元:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.dollar }" />
	</font>
	<hr/>
	
	<font class="label-left" >折人民币:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.rmb }" />
	</font>
	<hr/>
	
	<font class="label-left" >价格条款:</font>
	<font class="label-right">
		${obj.priceTerms }
	</font>
	<hr/>
	
	<font class="label-left" >即期/远期:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${obj.lcType != 3 }">
				${obj.lcType eq 0 ? '即期' : '远期' }
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</font>
	<hr/>
	
	<font class="label-left" >远期天数:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${obj.lcType != 3 }">
				${obj.days }
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</font>
	<hr/>
	<font class="label-left">
		${obj.lcType eq 3 ? '本次TT应收' : '本次LC应收' }
	</font>
	<font class="label-right">
		${obj.receivable }
	</font>
	<font class="label-left"></font>
	<font class="label-right">
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${obj.receivable / obj.rmb * 100 }" />%
	</font>
	<hr/>
	<font class="label-left" >
		${obj.lcType eq 3 ? '本次TT实收:' : '本次LC实收:' }
	</font>
	<font class="label-right">
		${obj.receipts }
	</font>
	<font class="label-left" ></font>
	<font class="label-right">
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${obj.receipts / obj.rmb * 100 }" />%
	</font>
	<hr/>
	
	<font class="label-left" >开证行:</font>
	<font class="label-right">
		${obj.bank }
	</font>
	<hr/>
	
	<font class="label-left" >
		${obj.lcType eq 3 ? '本次TT金额占合同比例:' : '信用证金额占合同比例:' }
	</font>
	<font class="label-right">
		${obj.percent }
	</font>
	<hr/>
	
	<font class="label-left" >
		${obj.lcType eq 3 ? '本次TT金额:' : '本次开证金额:' }
	</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.theMoney }" />
	</font>
	<hr/>
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.openCreditGoods }">
			<listitem type="twoline" 
				caption="名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|单位:${goods.unit }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	
	<font class="label-left" >备注:</font>
	<font class="label-right">
		${obj.remark }
	</font>