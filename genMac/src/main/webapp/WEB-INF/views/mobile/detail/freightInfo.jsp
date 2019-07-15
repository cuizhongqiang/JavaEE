<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">送库委托通知书</font><hr/>
	<font class="label-left" >货权单位:</font>
	<font class="label-right"> 
		${obj.goodsRightUnit }
	</font>
	<hr/>
	<font color="#0080FF" style="align:center">兹委托右表格中的单位</font><hr/>
	<font class="label-left" >单位名称:</font>
	<font class="label-right">
		${obj.logisticsUnitName }
		<c:if test="${obj.specified eq 1 }">
				指定货代
		 </c:if>
	</font>
	<hr/>
	
	<font class="label-left" >地址:</font>
	<font class="label-right"> 
		${obj.logisticsUnitAddr }
	</font>
	<hr/>
	
	<font class="label-left" >邮编:</font>
	<font class="label-right"> 
		${obj.logisticsUnitZipcode }
	</font>
	<hr/>
	
	<font class="label-left" >联系人:</font>
	<font class="label-right"> 
		${obj.logisticsUnitContacts }
	</font>
	<hr/>
	
	<font class="label-left" >电话:</font>
	<font class="label-right"> 
		${obj.logisticsUnitContactsPhone }
	</font>
	<hr/>
	
	<font class="label-left" >传真:</font>
	<font class="label-right"> 
		${obj.logisticsUnitContactsFax }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">将右表格中的货物</font><hr/>
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
	
	<div id="childList" style="display:none;">
		<c:forEach var="goods" items="${obj.freightGoods}">
			<listitem type="twoline" 
				caption="货物名称:${goods.goodsCategory }|规格型号:${goods.specification}" 
			 	sndcaption="数量:${goods.amount }|箱量:${goods.chest }|散杂货:${goods.stray }|箱型:${goods.chestType }|备注:${goods.remark }" />
		</c:forEach>
	</div> 
	<input type="button" id="viewButton" style="align: center" value="显示商品信息（+）"  onclick="viewChildList()"/>
	<hr/>
	
	<font color="#0080FF" style="align:center">送入右表格中的单位</font><hr/>
	<font class="label-left" >单位名称:</font>
	<font class="label-right"> 
		${obj.goalUnitName }
	</font>
	<font class="label-left"></font>
	<font class="label-right"> 
		<c:if test="${obj.storage eq 1 }">
				仓储单位
		</c:if>
	</font>
	<hr/>
	
	<font class="label-left" >地址:</font>
	<font class="label-right"> 
		${obj.goalUnitAddr }
	</font>
	<hr/>
	
	<font class="label-left" >邮编:</font>
	<font class="label-right"> 
		${obj.goalUnitZipcode }
	</font>
	<hr/>
	
	<font class="label-left" >联系人:</font>
	<font class="label-right"> 
		${obj.goalUnitContacts }
	</font>
	<hr/>
	
	<font class="label-left" >电话:</font>
	<font class="label-right"> 
		${obj.goalUnitContactsPhone }
	</font>
	<hr/>
	
	<font class="label-left" >传真:</font>
	<font class="label-right"> 
		${obj.goalUnitContactsFax }
	</font>
	<hr/>
	
	
	<font color="#0080FF" style="align:center">备注</font><hr/>
	<font class="label-left" >送库要求:</font>
	<font class="label-right"> 
		${obj.requirement }
	</font>
	<hr/>
	
	<font class="label-left" >堆存条件:</font>
	<font class="label-right"> 
		${obj.conditions }
	</font>
	<hr/>
	
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
