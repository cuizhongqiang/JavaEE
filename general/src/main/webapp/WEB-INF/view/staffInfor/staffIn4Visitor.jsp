<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>Home</title>
		<link href="${ctx }/static/others/visitorView/css/bootstrap.css" rel="stylesheet">
		<link href="${ctx }/static/others/visitorView/css/style.css" rel="stylesheet" type="text/css" media="all" />
		
	<!-- for-mobile-apps -->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="keywords" content="Peak Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
		Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
	<!-- //for-mobile-apps -->	
	<!-- js -->
		<script type="text/javascript" src="${ctx}/static/others/visitorView/js/jquery.min.js"></script>
		<script src="${ctx}/static/others/visitorView/js/modernizr.custom.97074.js"></script>
	<!-- js -->
	<!-- start-smoth-scrolling -->
		<script type="text/javascript" src="${ctx}/static/others/visitorView/js/move-top.js"></script>
		<script type="text/javascript" src="${ctx}/static/others/visitorView/js/easing.js"></script>
		<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
				});
			});
		</script>
	<!-- start-smoth-scrolling -->
</head>
<body>
<!-- banner -->
<div id="home" class="banner">
	<div class="container">
		<div class="banner-grids">
			<div class="col-md-4 navigation">
				<div class="nav-left">
					<div class="grid__item color-4">
						<a class="link link--kumya" href="#home"><span data-letters="${staffInfor.realName }">${staffInfor.realName }</span></a>
					</div>
				</div>
				<div class="nav-right">
						<span class="menu"><img src="${ctx}/static/others/visitorView/images/menu.png" alt="" /></span>
							<nav class="cl-effect-1">
								<ul class="nav1">
									<li><a class="scroll" href="#home">Home</a></li>
									<li><a class="scroll" href="#about">ABOUT</a></li>
									<li><a class="scroll" href="#portfolio">ABOUT ME</a></li>
									<li><a class="scroll" href="#services">services</a></li>
									<li><a class="scroll" href="#contact">contact</a></li>
								</ul>
							</nav>
							<!-- script for menu -->
							<script> 
								$( "span.menu" ).click(function() {
								$( "ul.nav1" ).slideToggle( 300, function() {
								 // Animation complete.
								});
								});
							</script>
							<!-- //script for menu -->

				</div>
				<div class="clearfix"></div>
			</div>
			<div class="col-md-8 banner-image text-center">
				<img src="${ctx}/static/others/visitorView/images/banner.png" alt=""/>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- //banner -->
<!-- about -->
<div id="about" class="about">
	<div class="container">
		<div class="about-info">
				<h3>ABOUT</h3>
				<h4>${briefInfor.title }</h4>
				<p>${briefInfor.articleContent }</p>
		</div>
	</div>
</div>
<div id="portfolio" class="gallery">
		<div class="container">
			<div class="about-info">
				<h3>ABOUT ME</h3>
				<h4>${staffInfor.title }</h4>
				<p>${staffInfor.comment }</p>
			</div>
        </div>	
</div>
<!--//gallery-->
<!--services-->
<div id="services" class="services">
	<div class="container">
		<div class="ser-head">
			<h3>FEATURED SERVICES</h3>
		</div>
		<div class="wel-grids">
			<div class="col-md-4 wel-grid text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-star" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="col-md-4 wel-grid btm-gre text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-globe" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="col-md-4 wel-grid text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="col-md-4 wel-grid btm-gre text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="col-md-4 wel-grid text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="col-md-4 wel-grid btm-gre text-center">
				<div class="btm-clr">
					<figure class="icon">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</figure>
					<h3>VOLUPTATEM</h3>
					<p> Nemo enim ipsam voluptatem
					quia voluptas sit aspernatur aut
					odit aut fugi.</p>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!--//services-->
<!--news
<div class="news">
	<div class="container">
		<h3>NEWSLETTER SIGN UP</h3>
		<p> Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing. </p>
		<div class="na-m">
			<div class="name">
				<form>
					<input type="text" placeholder="Enter email id" required="">
				</form>
			</div>
			<div class="button">
				<form>
					<input type="submit" value="Subscribe">
				</form>
			</div>
			<div class="clearfix"> </div>
		</div>
	</div>
</div>
//news-->
<!-- get-in -->
<div id="contact" class="get-in-touch">
	<div class="container">
		<div class="get-info text-center">
			<h3>GET IN TOUCH</h3>
			<h4><i>Feel Free To Contact Us</i></h4>
			<p>If you want to know more information, please contact me through the following ways</p>
		</div>
	</div>
</div>
<!-- //get-in -->
<!-- contact-us -->
<div class="contact-us">
	<div class="container">
		<div class="contact-grids">
			<div class="col-md-4 contact-grid text-center">
				<div class="point-icon"><span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span></div>
				<p>${staffInfor.address }</p>
			</div>
			<div class="col-md-4 contact-grid text-center">
				<div class="point-icon"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></div>
				<p><a href="mailto:${staffInfor.email }">${staffInfor.email }</a></p>
			</div>
			<div class="col-md-4 contact-grid text-center">
				<div class="point-icon"><span class="glyphicon glyphicon-earphone" aria-hidden="true"></span></div>
				<p>${staffInfor.mobile }
				<c:if test="${staffInfor.telphone !='' }">
				/${staffInfor.telphone }
				</c:if>
				</p>
			</div>
			<div class="point-icon"><p style="text-align: center;"><img src="${ctx }/vistor/toUserAdress/${staffInfor.id }"/></p></div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<!-- //contact-us -->
<!-- footer -->
<div class="copy-right">
	<div class="container">
		<p>Copyright &copy; 2017.Company name All rights reserved.</p>
	</div>
</div>
<!-- footer -->
<!-- smooth scrolling -->
	<script type="text/javascript">
		$(document).ready(function() {
		/*
			var defaults = {
			containerID: 'toTop', // fading element id
			containerHoverID: 'toTopHover', // fading element hover id
			scrollSpeed: 1200,
			easingType: 'linear' 
			};
		*/								
		$().UItoTop({ easingType: 'easeOutQuart' });
		});
	</script>
	<a href="#" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
<!-- //smooth scrolling -->
</body>
</html>
