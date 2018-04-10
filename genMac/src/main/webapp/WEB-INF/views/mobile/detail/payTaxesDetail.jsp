<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font class="label-left" >提单号:</font>
	<font class="label-right">
		${obj.invoiceNo }
	</font>
	<hr/>
	
	<font class="label-left" >合同号:</font>
	<font class="label-right">
		${obj.contractNo }
	</font>
	<hr/>
	
	<font class="label-left" >币种:</font>
	<font class="label-right">
		${obj.currency }
	</font>
	<hr/>
	
	<font class="label-left" >汇率:</font>
	<font class="label-right">
		${obj.rate }
	</font>
	<hr/>
	
	<font class="label-left" >原币金额:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.originalCurrency }" />
	</font>
	<hr/>
	
	<font class="label-left" >提单金额(人民币):</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.invoiceMoney }" />
	</font>
	<hr/>
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.payTaxesGoods }">
			<listitem type="twoline" 
				caption="名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|单位:${goods.unit }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	
	<font class="label-left" >税号:</font>
	<font class="label-right">
		${obj.taxNo }
	</font>
	<hr/>
	
	<font class="label-left" >关税:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.tax }" />
	</font>
	<hr/>
	
	<font class="label-left" >增值税:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.vat }" />
	</font>
	<hr/>
	
	<font class="label-left" >消费税:</font>
	<font class="label-right">	
		<fmt:formatNumber type="number" value="${obj.saleTax }" />
	</font>
	<hr/>
	
	<font class="label-left" >其它:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.otherTax }" />
	</font>
	<hr/>
	
	<font class="label-left" >总计税金:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.taxTotal }" />
	</font>
	<hr/>
	
	<font class="label-left" >收款单位:</font>
	<font class="label-right">
		${obj.receivingUnit }
	</font>
	<hr/>
	
	<font class="label-left" >支付方式:</font>
	<font class="label-right">
		${obj.payModel }
	</font>
	<hr/>
	
	<font class="label-left" >交税银行:</font>
	<font class="label-right">
		${obj.bank }
	</font>
	<hr/>
	
	<font class="label-left" >交税账号:</font>
	<font class="label-right">
		${obj.account }
	</font>
	<hr/>
	
	<font class="label-left">是否委托货代:</font>
	<font class="label-right">
		${obj.delegaFreight eq 1 ? '是' : '否' }
	</font>
	
	<font class="label-left" >制单日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.createDate }" pattern="yyyy-MM-dd" />
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