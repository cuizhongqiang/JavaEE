<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<!-- --------如果浏览器支持多种内核可以用以下部分进行默认设置-------------- -->
<!-- 若页面需默认用极速核，增加标签<meta name="renderer" content="webkit" />  -->
<!-- 若页面需默认用ie兼容内核，增加标签<meta name="renderer" content="ie-comp" />  -->
<!-- 若页面需默认用ie标准内核，增加标签<meta name="renderer" content="ie-stand" /> -->
<!-- <meta name="renderer" content="ie-comp" />  -->
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<!--关键代码部分-->
<style type="text/css" media="print">
	.noprint{display : none }
</style>
</head>
<body >
<!-- -------需要用到ie的activeX时下面部分必须引入--------------- -->
<!-- <object id="wb" height="0" width="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"></object> -->
<div id="header"></div>
<!-- header结束 -->

<!-- 销售结算打印 -->
	<table width="95%">
		<tr>
			<td align="center" style="font-size:24px" colspan="2">开具增值税发票通知单</td>
		</tr>
		
		<tr>
			<td>
				<hr />
			</td>
		</tr>
	</table>
	
	<table width="95%">
		<tr>
			<td colspan="2">
				<table width="100%">
					<tr>
						<td align="left" width="70%">
							财务部
						</td>
						<td align="left" width="30%">
							编号
							
						</td>
					</tr>
				</table>
			</td>
			
		</tr>
		<tr>
			<td align="left" colspan="2">
				请按以下内容开具增值税发票:
			</td>
		</tr>
		<tr>
			<td align="left" width="25%">
				购货单位:
			</td>
			<td align="left" width="75%">
				
			</td>
		</tr>
		<tr>
			<td align="left">
				纳税人识别号:
			</td>
			<td align="left">
				
			</td>
		</tr>
		<tr>
			<td align="left">
				地址、电话:
			</td>
			<td align="left">
				 T:
			</td>
		</tr>
		<tr>
			<td align="left">
				开户行:
			</td>
			<td align="left">
				
			</td>
		</tr>
		<tr>
			<td align="left">
				账号:
			</td>
			<td align="left">
				
			</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td align="left" colspan="2">
				发票内容:
			</td>
		</tr>
	</table>
	
	<table width="95%"  cellpadding="0" cellspacing="0" 
		bordercolor="#8EB4BF" border="1" 
		>
		
		<tr  style="line-height:2">
			<td>货物名称</td>
			<td>规格型号</td>
			<td>计量单位</td>
			<td>数量</td>
			<td>含税单价</td>
			<td>税率%</td>
			<td>税价合计</td>
		</tr>
		<ui:repeat value="" var="sub">
			<tr>
				<td></td>
				<td></td>
				<td>吨</td>
				<td align="right"></td>
				<td></td>
				<td>17%</td>
				<td align="right">
					<h:outputText value="" >
						<f:convertNumber />
					</h:outputText>
				</td>
			</tr>	
		</ui:repeat>
			<tr>
				<td colspan="3">合计</td>
				<td align="right"></td>
				<td align="right" colspan="3">
					<h:outputText value="" >
						<f:convertNumber />
					</h:outputText>	
				</td>
			</tr>
	</table>
	<table width="95%">
		<tr>
			<td></td>
		</tr>
		
		<tr>
			<td align="left" colspan="2">销售合同号</td>
		</tr>
		
		<tr  style="line-height:2">
			<td align="left" colspan="2">
				对应采购合同号:
			</td>
		</tr>
		<tr>
			<td align="left" colspan="2">我司开票单位</td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td align="left" colspan="2">
				<table width="95%"  cellpadding="0" cellspacing="0" border="0" >
					<tr>
						<td width="10%">备注</td>
						<td></td>
					</tr>
					<tr>
						<td></td>	
						<td>
							<h:outputText value="货款利息"/>
						</td>	
					</tr>
					<tr>
						<td></td>	
						<td>
							<h:outputText value="其它"/>
						</td>	
					</tr>
				</table>
			</td>
		</tr>
		
		<tr style="line-height:2">
			<td align="right"  colspan="2">
				经办人赵倍
			</td>
		</tr>
		<tr style="line-height:2">
			<td align="right" colspan="2">
				<h:outputText
				    value="">
				    <s:convertDateTime type="date"/>
				</h:outputText>
			</td>
		</tr>
	</table>	
	<br></br>

<p class="noprint">
<!--class="noprint"的作用是标示不需要打印的地方，任何一个标签的class属性都可以设置，用于去除不需要打印的地方-->
<input type="submit"  value="&#25171;&#21360;" onclick="print()" class="buttom" />
<!-- -------------以下部分为打印设置预留--------------------- -->
<!-- <input type="submit" name="j_id14" value="&#25171;&#21360;" onclick="printit()" class="buttom" /> -->
<!-- <input type="submit" name="j_id15" value="&#25171;&#21360;&#39029;&#38754;&#35774;&#32622;" onclick="printsetup();" class="buttom" /> -->
<!-- <input type="submit" name="j_id16" value="&#25171;&#21360;&#39044;&#35272;" onclick="printpreview();" class="buttom" /> -->
<!-- -------------以上部分为打印设置预留--------------------- -->
</p>

<script type="text/javascript">
// ------------以下部分为打印设置预留------------ 
// function printsetup(){
// 	window.print();
// 	//打印页面设置
//  	wb.execwb(8,1);
// }
// function printpreview(){
// 	// 打印页面预览  
// 	wb.execwb(7,1);
// }

// function printit(){
// 	if(confirm('确定打印吗？')){
// 		wb.execwb(6,6);
// 	}
	
// }
// var HKEY_Root,HKEY_Path,HKEY_Key;       
// HKEY_Root="HKEY_CURRENT_USER";       
// HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";   
// function pagesetup_null(){
//   try{
	  
// 	  var RegWsh = new ActiveXObject("WScript.Shell")
// 	  HKEY_Key="header" 
// 	  RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"")
// 	  HKEY_Key="footer"
// 	  RegWsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"")
//   }catch(e){}
//  }

</script> 
</body>
</html>