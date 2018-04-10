<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">基本信息</font><hr/>
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
	
	<font class="label-left" >原币金额:</font>
	<font class="label-right">	
		<fmt:formatNumber type="number" value="${obj.originalCurrency }" />
	</font>
	<hr/>
	
	<c:if test="${obj.type == 1 }">
		<font class="label-left" >押汇天数:</font>
		<font class="label-right">
			${obj.days }
		</font>
		<hr/>
	
		<font class="label-left" >押汇到期日:</font>
		<font class="label-right">
			<fmt:formatDate value="${obj.documentaryBillsDate }" />
		</font>
		<hr/>
	</c:if>
	<font class="label-left" >${obj.type == 1 ? '押汇' : '付汇' }金额:</font>		
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.acceptanceMoney }" />
	</font>
	<hr/>

	<font class="label-left" >备注:</font>
	<font class="label-right">
		${obj.remark }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">回填信息</font><hr/>
	<font class="label-left" >实际付汇日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.actualDate }" />
	</font>
	<hr/>
	
	<font class="label-left" >银行:</font>
	<font class="label-right">
		${obj.bank }
	</font>
	<hr/>
	
	<font class="label-left" >实际汇率:</font>
	<font class="label-right">
		${obj.actualRate }
	</font>
	<hr/>
	
	<font class="label-left" >客户汇率:</font>
	<font class="label-right">
		${obj.customerRate }
	</font>
	<hr/>
	
	<font class="label-left" >折算成人民币:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.rmb }" />
	</font>
	<hr/>
	
	<font class="label-left" >实际手续费:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.actualPoundage }" />
	</font>
	<hr/>
	
	<font class="label-left" >客户手续费:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" value="${obj.customerPoundage }" />
	</font>
	<hr/>
	
	<c:if test="${obj.type == 1 }">
			<font class="label-left" >押汇利率:</font>
			<font class="label-right">
				${obj.documentaryBillsRate }${empty obj.documentaryBillsRate ? '' : '%'}
			</font>
			<hr/>
	
			<font class="label-left" >利息:</font>
			<font class="label-right">
				<fmt:formatNumber type="number" value="${obj.interest }" />
			</font>
			<hr/>
	</c:if>
	
	<font color="#0080FF" style="align:center">系统信息</font><hr/>
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
		<fmt:formatDate value="${obj.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
	</font>
	<hr/>