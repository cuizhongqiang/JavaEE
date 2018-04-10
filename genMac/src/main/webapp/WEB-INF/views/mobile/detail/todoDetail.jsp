<c:choose>
	<c:when test="${approval.processKey eq 'wf_agentImport' }">
		<%@ include file="../detail/agentImportDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_openCredit' }">
		<%@ include file="../detail/openCreditDetaill.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_changeCredit' }">
		<%@ include file="../detail/changeCreditDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_expense' }">
		<%@ include file="../detail/expenseDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_acceptance' }">
		<%@ include file="../detail/acceptanceDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_payTaxes' }">
		<%@ include file="../detail/payTaxesDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_logisticsCheck' }">
		<%@ include file="../detail/logisticsCheckDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_freight' }">
		<%@ include file="../detail/freightInfo.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_enterpriseStock' }">
		<%@ include file="../detail/stockCheckDetail.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_sendGoods' }">
		<%@ include file="../detail/sendGoodsInfo.jsp"%>
	</c:when>
	<c:when test="${approval.processKey eq 'wf_importBilling' }">
		<%@ include file="../detail/importBillingDetail.jsp"%>
	</c:when>
</c:choose>