
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html style="font-size: 128.068px;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no,shrink-to-fit=no">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<meta name="format-detection" content="email=no">
<meta name="format-detection" content="telephone=no">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/others/ecard/css/compressed/share_index.css">
<title>电子名片分享</title>
<style type="text/css"></style>
<script type="text/javascript" src="${ctx}/static/others/ecard/js/compressed/static_switch.js"></script>
<script type="text/javascript" src="${ctx }/static/jquery/jquery-3.1.1.min.js" ></script>
<script type="text/javascript">
    $(function () {             
            //绑定滚动条事件 
              //绑定滚动条事件 
            $(window).bind("scroll", function () { 
                var sTop = $(window).scrollTop(); 
                var sTop = parseInt(sTop); 
                if (sTop >= 130) { 
                    if (!$("#anchor").is(":visible")) { 
                        try { 
                            $("#anchor").slideDown(); 
                        } catch (e) { 
                            $("#anchor").show(); 
                        }                       
                    } 
                } 
                else { 
                    if ($("#anchor").is(":visible")) { 
                        try { 
                            $("#anchor").slideUp(); 
                        } catch (e) { 
                            $("#anchor").hide(); 
                        }                        
                    } 
                }  
            });
        }); 
    function upArrow(){
    	$("[name='downItem']").hide();
 	  	$("#downArrow").show();
 	  	$("#upArrow").hide();
    }
 	function downArrow(){
 	   	$("[name='downItem']").show();
 	  	$("#downArrow").hide();
 	  	$("#upArrow").show();
    }
 	function showImage(){
 	  	$("#full_img_wrapper").show();
    }
 	function hiddeImg(){
 	  	$("#full_img_wrapper").hide();
    }
 	
 	function saveCard(){
 		$("#saveCard").show();
 	}
 	
 	function hiddeCard(){
 		$("#saveCard").hide();
 	}
    
</script>
</head>
<body style="overflow-y: auto;">
	<img class="hidden_ele" src="">
	<div class="main" id="main">
		<div class="mask hidden_ele" id="mask"></div>
		<div class="rem"></div>
		<div class="fixed_loading_view" style="display: none;">
			<div class="rotate"></div>
			<div class="logo_blue"></div>
		</div>
		<div class="sg_layout vm_share vm_detail">
			<!--头部信息 + 锚点-->
			<div class="head_wrapper">
				<div class="head_banner" style="background-image: url(https://static.intsig.net/ecard/images/ecard/banner01.jpg)"></div>
				<div class="head_info">
					<div class="avatar_wrapper">
						<img class="avatar" src="${ctx }/vistor/avatarView/${staffInfor.accId }.jpg" onerror="this.src='${ctx}/static/others/ecard/images/ecard/downfile.jpg'">
					</div>

					<div class="username hidelong">${staffInfor.realName }</div>
					<div id="contact" style="padding-top: 40px; "></div>
					<div class="contact_wrapper show_all" style="text-align: left;">
						<a class="contact_item telephone extend " href="tel:${staffInfor.mobile }">
							<div class="contact_icon icon-phone"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.mobile }</div>
								<div class="label" >手机</div>
							</div>
							<div class="extend_icon icon-arrow_right"></div>
						</a>
						<a name="downItem" style="display: none;" class="contact_item telephone extend  contact_sub" href="tel:${staffInfor.telphone }">
							<div class="contact_icon icon-telephone"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.telphone }</div>
								<div class="label">电话</div>
							</div>
							<div class="extend_icon icon-arrow_right"></div>
						</a>
						<a name="downItem" style="display: none;" class="contact_item telephone extend  contact_sub" >
							<div class="contact_icon icon-fax"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.fax }</div>
								<div class="label">传真</div>
							</div>
							<div class="extend_icon icon-arrow_right"></div>
						</a>
						<a class="contact_item" href="mailto:${staffInfor.email }">
							<div class="contact_icon icon-message"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.email }</div>
								<div class="label">邮箱</div>
							</div>
						</a>
						<a class="contact_item" >
							<div class="contact_icon icon-location"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.address }</div>
								<div class="label">地址</div>
							</div>
						</a>
						<a class="contact_item company extend" data-name="中建材信云智联科技有限公司" data-id="b9c255d6-93fb-4d34-ada7-3a0c5ef81256">
							<div class="contact_icon icon-company"></div>
							<div class="contact_info">
								<div class="value">${staffInfor.compName }</div>
								<div class="title">${staffInfor.title }<span class="pipes"></span>${staffInfor.department }</div>
								<div class="label company">公司</div>
							</div>
						</a>
						<a name="downItem" style="display: none;" class="contact_item contact_sub" href="${staffInfor.website }">
							
							<div class="contact_icon icon-website"></div>
							
							<div class="contact_info">
								<div class="value">${staffInfor.website }</div>
								<div class="label">网址</div>
							</div>
						</a>
						
					</div>
					<div id="upArrow" class="fold_tool ib show_all" style="display: none;" onclick="upArrow()">
						<span >收起 ▲</span>
					</div>
					<div id="downArrow" class="fold_tool ib show_all" onclick="downArrow()">
						<span >展开全部▼</span><br>
					</div>
					<div id="anchor">
						<div id="fixed_guide" class="fixed_guide ib" style="display: block;">
						<div id="card_image" class="item card_image" data-id="card_image" onclick="showImage()">名片图像</div>
						<div class="item contacts_info" data-id="contacts_info"><a class="scroll" href="#">名片信息</a></div>
						<div class="item " data-id="company_info"
							style="display: inline-block;"><a class="scroll" href="#company">企业简介</a></div>
						</div>
					</div>
					
				</div>
			</div>
			<!--企业背景 企业产品 企业知识产权 商业需求 工作经历 教育经历 个人专利 个人成就 企业公众号-->
			<div sg-view="" class="vm_extend">
				<div class="card_wrapper company_info">
				<div id="company" style="padding-top: 40px;background-color: rgb(255, 255, 255); "></div>
					<div class="card_title">
					</div>
					<div class="card_wrapper">
						<div sg-view="" class="vm_companybg">

							<div class="wrapper">

								<img class="company_img" src="${ctx}/static/others/ecard/images/ecard/downfile2.jpg">
								<div class="company_info">
									<div class="name hidelong">中建材信云智联科技有限公司</div>
									<div class="info hidelong">
										<div class="extend_icon icon-number"></div>
										<span>301~500人</span>
										<div class="extend_icon icon-coin"></div>
										<span >10000 万人民币</span>
										<br>
										<div class="extend_icon icon-time"></div>
										<span>2017-08-02成立</span>
										<br>
										
									</div>
								</div>
								<div class="tags">
									<div class="tag">
										<span class="corner_tl"></span> <span class="corner_tr"></span>
										<span class="corner_bl"></span> <span class="corner_br"></span>
										${briefInfor.title }
									</div>
								</div>
								<div class="desc_wrap">
									<div class="desc">
										${briefInfor.articleContent }
									</div>
									<div class="btn_desc" >
										<span class="unfold" style="display: block;"style="display: none;"><span
											class="gray3">……</span>全文</span>
										<span class="fold"
											style="display: inline;">收起</span>
									</div>
								</div>

							</div>


						</div>
					</div>

			
				</div>
			</div>
			<div class="card_wrapper card_image">
				<div class="card_title"></div>
				<div class="vm_cardphoto">

					<div class="card_wrap">
						<img src="${ctx }/vistor/toUserCard/${staffInfor.id }.png" style="visibility: visible;width: 100%;">
					</div>

					<div onclick="hiddeImg()" id="full_img_wrapper" class="full_img_wrapper" style="display: none;">
						<div class="imgs_mask "
							style="width: 100%; transform: translateX(0%);">
							<div class="img_wrapper">
								<img class="full_img" src="${ctx }/vistor/toUserCard/${staffInfor.id }.png" style="margin-top: 100px;"/>
							</div>
						</div>
					</div>
					
					<div onclick="hiddeCard()" id="saveCard" class="full_img_wrapper" style="display: none;">
							<div class="img_wrapper" style="width: 100%;">
								<div style="text-align: center;color:#FFF;margin-top: 200px;">
									<span style="font-size:20px;">${staffInfor.realName }的名片</span><br/>
									<img class="full_img" src="${ctx }/vistor/toUserAdress/${staffInfor.id }.png"/>
									<br/>
									<span style="font-size:10px">长按识别二维码</span><br/>
									<span style="font-size:10px">保存到通讯录</span>					
								</div>
							</div>
						</div>
					
					</div>
				</div>
			</div>
			
			

			<div class="btns_wrap not_wx ib" style="background:#FFF">
				<div onclick="saveCard()" class="btn_right">保存到通讯录</div>
			</div>

	</div>

	<span class="notification hidden_ele"> <span class="notice_img"></span>
		<span class="notice_text"></span>
	</span>
</body>
</html>