<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">基本信息</font><hr/>
	<font class="label-left" >合同号:</font>
	<font class="label-right">
		${obj.contractNo }
	</font>
	<hr/>
	
	<font class="label-left" >客户名称:</font>
	<font class="label-right">
		${my:getAffiliatesById(obj.customerId).customerName }
	</font>
	<hr/>
	
	<font class="label-left" >业务员:</font>
	<font class="label-right">
		${obj.salesman }
	</font>
	<hr/>
	
	<font class="label-left" >币种:</font>
	<font class="label-right">
		RMB
	</font>
	<hr/>
	
	<font class="label-left" >申请日期:</font>
	<font class="label-right">
		<fmt:formatDate value='${obj.applyDate }' />
	</font>
	<hr/>
	
	<font class="label-left" >财务实付日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.payDate }" />
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">基本信息</font><hr/>
	<font class="label-left" >应付总金额:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.sumMoney }"/>
	</font>
	<hr/>
	
	<font class="label-left" >实付金额:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.payCurrency }"/>
	</font>
	<hr/>
	
	<font class="label-left" >未付金额:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.oweCurrency }"/>
	</font>
	<hr/>
	
		<font color="#0080FF" style="align:center">系统信息</font><hr/>
	<font class="label-left" >制单日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.createDate}" pattern="yyyy-MM-dd" />
	</font>
	<hr/>
	
	<font class="label-left" >制单人:</font>
	<font class="label-right">
		${obj.createrName }
	</font>
	<hr/>
	
	<font class="label-left" >最近修改时间:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
	</font>
	<hr/>
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.expenseDetail }">
			<listitem type="twoline" 
				caption="结算客户:${goods.settlement }|银行名称:${goods.bankName}|银行账号:${goods.bankNo}" 
			 	sndcaption="单据号:${goods.documentNo }|付款子类型:${goods.paymentChildType }|收款情况:${goods.receiptStatus }|金额:${goods.money }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
