<%@ page contentType="application/uixml+xml;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="my" uri="/WEB-INF/tlds/my.tld" %>
	
	<font color="#0080FF" style="align:center">基本信息</font><hr/>
	<font class="label-left" >评审编号:</font>
	<font class="label-right">
		${obj.checkId }
	</font>
	<hr/>
	
	<font class="label-left" >公司名称:</font>
	<font class="label-right">
		${obj.companyName }
	</font>
	<hr/>
	
	<font class="label-left" >公司地址:</font>
	<font class="label-right">
		${obj.companyAddress }
	</font>
	<hr/>
	
	<font class="label-left" >联系人:</font>
	<font class="label-right">
		${obj.contactPerson }
	</font>
	<hr/>
	
	<font class="label-left" >联系电话:</font>
	<font class="label-right">
		${obj.phoneContact }
	</font>
	<hr/>
	
	<font class="label-left" >传真:</font>
	<font class="label-right">
		${obj.fax }
	</font>
	<hr/>
	
	<font class="label-left" >申请部门:</font>
	<font class="label-right">
		${obj.applyDepart }
	</font>
	<hr/>
	
	<font class="label-left" >申请人:</font>
	<font class="label-right">
		${obj.applyPerson }
	</font>
	<hr/>
	
	<font class="label-left" >申请时间:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.applyDate }" />
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">企业实力</font><hr/>
	<font class="label-left" >公司性质:</font>
	<font class="label-right">
		${obj.companyProperty }
	</font>
	<hr/>
	
	<font class="label-left" >资金币种:</font>
	<font class="label-right">
		${obj.capitalCurrency }
	</font>
	<hr/>
	
	<font class="label-left" >注册资金:</font>
	<font class="label-right">
		${obj.registeredCapital }万元
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">营业年限</font><hr/>
	<font class="label-left" >成立日期:</font>
	<font class="label-right">
		<fmt:formatDate value="${obj.businessLifeFound }" />
	</font>
	<hr/>
	
	<font class="label-left" >营业期限:</font>
	<font class="label-right">
		${obj.businessLifeDealline }年
	</font>
	<hr/>
	
	<font class="label-left" >已营业年限:</font>
	<font class="label-right">
		${obj.businessLife }年
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">公司股东</font><hr/>
	<font class="label-left" >法定代表人:</font>
	<font class="label-right">
		${obj.corporate }
	</font>
	<hr/>
	
	<font class="label-left" >实际控制人:</font>
	<font class="label-right">
		${obj.actualControlPerson }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">运输资质</font><hr/>
	<font class="label-left" >是否有《道路/水路运输资格》:</font>
	<font class="label-right">
		${obj.haveTransportAble }
	</font>
	<hr/>
	
	<font class="label-left" >自有车辆:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${empty obj.haveCar }">
				&nbsp;
			</c:when>
			<c:otherwise>
				${obj.haveCar }辆
			</c:otherwise>
		</c:choose>
	</font>
	<hr/>
	
	<font class="label-left" >自有船舶:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${empty obj.havaShip }">
				&nbsp;
			</c:when>
			<c:otherwise>
				${obj.havaShip }艘
			</c:otherwise>
		</c:choose>	
	</font>
	<hr/>
	
	<font class="label-left" >其中海关监管车:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${empty obj.customsControlVehicles }">
				&nbsp;
			</c:when>
			<c:otherwise>
				${obj.customsControlVehicles }辆
			</c:otherwise>
		</c:choose>	
	</font>
	<hr/>
	
	<font class="label-left" >其他运输方案:</font>
	<font class="label-right">
		${obj.extraTransportProgram }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">在港人数</font><hr/>
	<font class="label-left" >具有报关、报检员资格的人数:</font>
	<font class="label-right">
		${obj.inPostPerson }人
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">专业能力</font><hr/>
	<font color="#0080FF" style="align:center">专长</font><hr/>
	<font class="label-left" >擅长项目、产品、进口出口清关操作:</font>
	<font class="label-right">
		${obj.expertProject }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">业绩</font><hr/>
	<font class="label-left" >上一营业年度报关票数:</font>
	<font class="label-right">
		${obj.lastVotes }票
	</font>
	<hr/>
	
	<font class="label-left" >营业收入:</font>
	<font class="label-right">
		${obj.income }万
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">信用</font><hr/>
	<font class="label-left" >海关、商检监管信息：信用信息:</font>
	<font class="label-right">
		${obj.creditInformation }
	</font>
	<hr/>
	
	<font class="label-left" >海关、商检监管信息：处罚信息:</font>
	<font class="label-right">
		${obj.punishmentInformation }
	</font>
	<hr/>
	
	<font class="label-left" >雇佣总人数:</font>
	<font class="label-right">
		${obj.totalWorker }人
	</font>
	<hr/>
	
	<font class="label-left" >雇佣详情:</font>
	<font class="label-right">
		操作员${obj.workerNumber }人
	</font>
	<hr/>
	
	<font class="label-left" ></font>
	<font class="label-right">
		管理员${obj.adminNumber }人
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">手续效力</font><hr/>
	<font class="label-left" >仓储企业已提供的资料:</font>
	<font class="label-right">
		${my:getDict(obj.enterpriseWarehouseData, 'enterpriseWarehouseData')}
	</font>
	<hr/>
	
	<font class="label-left" >合同是否已签订:</font>
	<font class="label-right">
		${obj.isContract }
	</font>
	<hr/>
	
	<font class="label-left" >哪方面版本:</font>
	<font class="label-right">
		${obj.contractCategory }
	</font>
	<hr/>
	
	<font class="label-left" >是否当面办理:</font>
	<font class="label-right">
		${obj.isFaceToFace }
	</font>
	<hr/>
	
	<font class="label-left" >现场考察及班里人意见:</font>
	<font class="label-right">
		${obj.sitePersonnel }
	</font>
	<hr/>
	
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
