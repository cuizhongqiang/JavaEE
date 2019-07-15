<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<c:if test="${fn:length(obj.agentImportSupplement) > 0}">
		<font color="#0080FF" style="align:center">补充协议</font><hr/>
	</c:if>
	<c:forEach var="supplement" items="${obj.agentImportSupplement }">
		<font class="label-left" >合同名称:</font>
		<font class="label-right">
			${supplement.contractName }
		</font>
		<hr/>
		<font class="label-left" >签订主体名称:</font>
		<font class="label-right">
			${supplement.principalName }
		</font>
		<hr/>
		<font class="label-left" >协议编号:</font>
		<font class="label-right">
			${supplement.agreementNo }
		</font>
		<hr/>
		<font class="label-left" >合同内容:</font>
		<font class="label-right">
			${supplement.content }
		</font>
		<hr/>
		<br size="20px"></br>
	</c:forEach>
	
	<font color="#0080FF" style="align:center">合同基本信息</font><hr/>
	<font class="label-left" >商品类别:</font>
	<font class="label-right">
		${obj.goodsType }
	</font>
	<hr/>
	<font class="label-left" >业务员:</font>
	<font class="label-right">
		${my:getUserByLoginName(obj.salesman).name }
	</font>
	<hr/>
	<font class="label-left" >本单交易规模:</font>
	<font class="label-right">
		${obj.currency }<fmt:formatNumber type="number" value="${obj.originalCurrency }" />
	</font>
	<hr/>
	<font class="label-left" >折RMB:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.rmb }" />
	</font>
	<hr/>
	<font color="#0080FF" style="align:center">客户信息</font><hr/>
	<font class="label-left">客户名称:</font>
	<font class="label-right">
		${my:getAffiliatesById(obj.customerId).customerName }
	</font>
	<hr/>
	<font class="label-left" >客户信息:</font>
	<font class="label-right">
		${obj.customerType eq 0 ? '新开发客户' : '长单老客户' }
	</font>
	<hr/>
	<c:choose>
		<c:when test="${obj.customerType eq 0 }">
			<font class="label-left" >注册时间</font>
			<font class="label-right">
				<fmt:formatDate value="${obj.registerDate }" />
			</font>
			<hr/>
			<font class="label-left" >注册资本</font>
			<font class="label-right">
				<fmt:formatNumber type="number" value="${obj.registerCapital }" />
			</font>
			<hr/>
		</c:when>
		<c:otherwise>
			<font class="label-left" >授信额度</font>
			<font class="label-right">
				<fmt:formatNumber type="number" value="${obj.lineCredit }" />
			</font>
		    <c:if test="${obj.lineCredit > 50000000 }">
		    	<font color="red">授信额度超过5千万，请谨慎审批！</font>
		    </c:if>
			<hr/>
			<font class="label-left" >目前敞口</font>
			<font class="label-right">
				<fmt:formatNumber type="number" value="${obj.currentOpen }" />
			</font>
			<hr/>
			<font class="label-left" >已超期金额</font>
			<font class="label-right">
				<fmt:formatNumber type="number" value="${obj.hasBeyond }" />
			</font>
			<hr/>
		</c:otherwise>
	</c:choose>
	<font color="#0080FF" style="align:center">外合同或进口合同信息</font><hr/>
	<font class="label-left" >合同名称:</font>
	<font class="label-right">
		${obj.foreignContractName }
	</font>
	<hr/>
	<font class="label-left" >编号</font>
	<font class="label-right">
		${obj.foreignContractNo }
	</font>
	<hr/>
	<font class="label-left" >版本来源</font>
	<font class="label-right">
		${obj.foreignVersionSource eq 0 ? '外商版本' : '我司版本' }
	</font>
	<hr/>
	<font class="label-left" >外商名称</font>
	<font class="label-right">
		${my:getAffiliatesById(obj.foreignId).customerName }
	</font>
	<hr/>
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.agentImportGoods }">
			<listitem type="twoline" 
				caption="商品名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|单位:${goods.unit }|单价:${goods.price }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	<font class="label-left" >支付方式:</font>
	<font class="label-right">
		${obj.paymentMethod }	备注：${obj.paymentMethodRemark }
	</font>
	<hr/>
	<font class="label-left" >价格条款</font>
	<font class="label-right">
		${obj.priceTerms }
	</font>
	<hr/>
	<font class="label-left" >装期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.shipmentDate }" />
	</font>
	<hr/>
	<font class="label-left" >争议管辖:</font>
	<font class="label-right">
		${obj.dispute }
	</font>
	<hr/>
	<font color="#0080FF" style="align:center">内合同或代理进口合同信息</font><hr/>
	<font class="label-left" >合同名称:</font>
	<font class="label-right">
		${obj.inlandContractName }
	</font>
	<hr/>
	<font class="label-left" >编号</font>
	<font class="label-right">
		${obj.contractNo }
	</font>
	<hr/>
	<font class="label-left" >版本来源:</font>
	<font class="label-right">
		${obj.inlandVersionSource eq 0 ? '我司版本' : '客户版本' }
	</font>
	<hr/>
	<font class="label-left" >项目要求:</font>
	<font class="label-right">
		${obj.projectClaim }	备注：${obj.projectClaimRemark }
	</font>
	<hr/>
	<font class="label-left" >代理费:</font>
	<font class="label-right">
		${obj.agencyFee }	备注:${obj.agencyFeeRemark }
	</font>
	<hr/>
	<font class="label-left" >保证金:</font>
	<font class="label-right">
		${obj.margin }
	</font>
	<hr/>
	<font class="label-left" >担保措施:</font>
	<font class="label-right">
		${obj.assurance eq 0 ? '有' : '无'}	 备注：${obj.assuranceRemark }
	</font>
	<hr/>
	<font class="label-left" >融资租赁:</font>
	<font class="label-right">
		${obj.financeLease eq 0 ? '有' : '无'}	 备注：${obj.financeLeaseRemark }
	</font>
	<hr/>
	<font class="label-left" >诉讼管辖:</font>
	<font class="label-right">
		${obj.litigation }
	</font>
	<hr/>
	<font color="#0080FF" style="align:center">物流信息</font><hr/>
	<font class="label-left" >货代:</font>
	<font class="label-right">
		${obj.freight }	备注:${obj.freightRemark }
	</font>
	<hr/>
	<font class="label-left" >备注:</font>
	<font class="label-right">
		${obj.logisticsRemark }
	</font>
	<hr/>
	<font color="#0080FF" style="align:center">系统信息</font><hr/>
	<font class="label-left" >制单日期:</font>
	<font class="label-right">
		${obj.createDate}
	</font>
	<hr/>
	<font class="label-left" >制单人:</font>
	<font class="label-right">
		${obj.createrName }
	</font>
	<hr/>
	<font class="label-left" >最近修改时间:</font>
	<font class="label-right">
		${obj.updateDate}
	</font>