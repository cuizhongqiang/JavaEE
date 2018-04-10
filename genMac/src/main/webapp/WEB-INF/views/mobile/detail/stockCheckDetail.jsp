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
	
	<font class="label-left" >企业名称:</font>
	<font class="label-right">
		${obj.enterpriseName }
	</font>
	<hr/>
	
	<font class="label-left" >仓库地址:</font>
	<font class="label-right">
		${obj.warehouseAddress }
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
	<font class="label-left" >企业性质:</font>
	<font class="label-right">
		${obj.enterpriseProperty }
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
	
	<font class="label-left" >是否经营仓储品:</font>
	<font class="label-right">
		${obj.isSealGoods }
	</font>
	<hr/>
	
	<font class="label-left" >经营规模:</font>
	<font class="label-right">
		${obj.sealScale }
	</font>
	<hr/>
	
	<font class="label-left" >可提供的担保:</font>
	<font class="label-right">
		${obj.guarantee }
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
	
	<font color="#0080FF" style="align:center">仓库基本情况</font><hr/>
	<font class="label-left" >总资产:</font>
	<font class="label-right">
		${obj.warehouseTotal }元
	</font>
	<hr/>
	
	<font class="label-left" >面积:</font>
	<font class="label-right">
		${obj.warehouseArea }亩
	</font>
	<hr/>
	
	<font class="label-left" >主要仓储品种:</font>
	<font class="label-right">
		${obj.warehouseInstockCategory }
	</font>
	<hr/>
	
	<font class="label-left" >其他:</font>
	<font class="label-right">
		${obj.warehouseBaseInfo }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">专业能力</font><hr/>
	<font color="#0080FF" style="align:center">设施设备</font><hr/>
	<font class="label-left" >软件:</font>
	<font class="label-right">
		${my:getDict(obj.majorEquipmentSoftware, 'SBSOFT')}
	</font>
	<hr/>
	
	<font class="label-left" >硬件:</font>
	<font class="label-right">
		${enterpriseStockCheck.majorEquipmentHardware }
	</font>
	<hr/>
	
	<font class="label-left" >其他:</font>
	<font class="label-right">
		${obj.majorEquipment }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">可用资源</font><hr/>
	<font class="label-left" >码头:</font>
	<font class="label-right">
		${obj.port }
	</font>
	<hr/>
	
	<font class="label-left" >铁路:</font>
	<font class="label-right">
		${obj.railway }
	</font>
	<hr/>
	
	<font class="label-left" >室内厂房:</font>
	<font class="label-right">
		${obj.indoorPlants }
	</font>
	<hr/>
	
	<font class="label-left" >其他:</font>
	<font class="label-right">
		${obj.warehouseResources }
	</font>
	<hr/>
	
	<font class="label-left" >雇佣人数:</font>
	<font class="label-right">
		<fmt:formatNumber type="number" maxFractionDigits="2" value="${obj.warehouseWorkerNumber + obj.dockerNumber + obj.adminNumber }" />人
	</font>
	<hr/>
	
	<font class="label-left" >雇佣详情:</font>
	<font class="label-right">
		仓管员${obj.warehouseWorkerNumber }人
	</font>
	<hr/>
	
	<font class="label-left" ></font>
	<font class="label-right">
		装卸工${obj.dockerNumber }人
	</font>
	<hr/>
	
	<font class="label-left" ></font>
	<font class="label-right">
		管理员${obj.adminNumber }人
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">经营现状</font><hr/>
	<font color="#0080FF" style="align:center">仓库流动性</font><hr/>
	<font class="label-left" >上年度周转货物总量:</font>
	<font class="label-right">
		${obj.lastYearQuantity }吨
	</font>
	<hr/>
	
	<font class="label-left" >近三个月出库平均量:</font>
	<font class="label-right">
		${obj.recent3MouthPerDayQuantity }吨/天
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">库存现状</font><hr/>
	<font class="label-left" >现有库存:</font>
	<font class="label-right">
		${obj.existingStocks }吨
	</font>
	<hr/>
	
	<font class="label-left" >最大库存:</font>
	<font class="label-right">
		${obj.maxStocks }吨
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">仓库经营稳定性</font><hr/>
	<font class="label-left" >是否领导层变动:</font>
	<font class="label-right">
		${obj.isLeaderChange }
	</font>
	<hr/>
	
	<font class="label-left" >近两年经营情况:</font>
	<font class="label-right">
		${obj.businessConditions }
	</font>
	<hr/>
	
	<font class="label-left" >仓储企业主要合作客户:</font>
	<font class="label-right">
		${obj.mainPartner }
	</font>
	<hr/>
	
	<font class="label-left" >盈利点:</font>
	<font class="label-right">
		${obj.profitPoint }
	</font>
	<hr/>
	
	<font class="label-left" >土地抵押情况:</font>
	<font class="label-right">
		${obj.islandMortgage }
	</font>
	<hr/>
	
	<font class="label-left" >是否有质押仓单业务:</font>
	<font class="label-right">
		${obj.isMortgageBusiness }
	</font>
	<hr/>
	
	<font class="label-left" >合作银行:</font>
	<font class="label-right">
		${obj.cooperationBank }
	</font>
	<hr/>
	
	<font class="label-left" >土地抵押资金用途:</font>
	<font class="label-right">
		${obj.landMortgageUse }
	</font>
	<hr/>
	
	<font class="label-left" >银行其他贷款:</font>
	<font class="label-right">
		${obj.mortgageBank }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">效率信息</font><hr/>
	<font color="#0080FF" style="align:center">工作时间</font><hr/>
	<font class="label-left" >上班时间:</font>
	<font class="label-right">
		${obj.starWorkTime }
	</font>
	<hr/>
	
	<font class="label-left" >下班时间:</font>
	<font class="label-right">
		${obj.endWorkTime }
	</font>
	<hr/>
	
	<font class="label-left" >周六日是否可以提货:</font>
	<font class="label-right">
		${obj.isWeekendDelivery }
	</font>
	<hr/>
	
	<font class="label-left" >最晚提货时间:</font>
	<font class="label-right">
		${obj.latestDeliveryTime }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">收费情况</font><hr/>
	<font class="label-left" >仓储费用:</font>
	<font class="label-right">
		${obj.warehouseFee }元/吨/天
	</font>
	<hr/>
	
	<font class="label-left" >出库费用:</font>
	<font class="label-right">
		${obj.outStockFee }元/吨
	</font>
	<hr/>
	
	<font class="label-left" >其他费用:</font>
	<font class="label-right">
		${obj.extraFee }
	</font>
	<hr/>
	
	<font color="#0080FF" style="align:center">客户情况</font><hr/>
	<font class="label-left" >仓库是否客户推荐:</font>
	<font class="label-right">
		${obj.isRecommend }
	</font>
	<hr/>
	
	<font class="label-left" >准备合作客户:</font>
	<font class="label-right">
		${obj.prepareCooperationCustomer }
	</font>
	<hr/>
	
	<font class="label-left" >业务描述:</font>
	<font class="label-right">
		${obj.businessDescription }
	</font>
	<hr/>
	
	<font class="label-left" >客户与仓储企业的关系:</font>
	<font class="label-right">
		${obj.customer2Enterprise }
	</font>
	<hr/>
	
	<font class="label-left" >客户是否含有库存:</font>
	<font class="label-right">
		${obj.isHaveInstock }
	</font>
	<hr/>
	
	<font class="label-left" >客户在库现有库存:</font>
	<font class="label-right">
		<c:choose>
			<c:when test="${empty obj.haveInstock }">
				&nbsp;
			</c:when>
			<c:otherwise>
				${obj.haveInstock }吨
			</c:otherwise>
		</c:choose>
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