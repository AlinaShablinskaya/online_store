<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| single :: w3layouts</title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery.min.js"></script>
<!-- Custom Theme files -->
<link href="css/hover.css" rel="stylesheet" media="all">
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<!-- Custom Theme files -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Shopper Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!--webfont-->
<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900' rel='stylesheet' type='text/css'>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

</head>
<body>
<!---->
<div class="header single">
	 <div class="container">
		 <div class="header-left">
			 <div class="top-menu">
				 <ul>
				 <li><a href="/home">HOME</a></li>
				 <li class="active"><a href="/catalog">CATALOG</a></li>
				 </ul>
			 </div>
		 </div>
		 <div class="logo">
			 <a href="/home"><img src="images/logo.png" alt=""/></a>
		 </div>
		 <div class="header-right">
			 <div class="currency">			 
				 <a href="#"><i class="c1"></i></a>
				 <a class="active" href="#"><i class="c2"></i></a>
				 <a href="#"><i class="c3"></i></a>
				 <a href="#"><i class="c4"></i></a>
			 </div>		 
			 <div class="signin">
				  <div class="cart-sec">
				  <a href="cart.html"><img href="cart.html" src="images/cart.png" alt=""/>(0)</a></div>
				  <ul>
					 <li><a href="/registration">REGISTRATION</a> <span>/<span> &nbsp;</li>
					 <li><a href="/login"> LOGIN</a></li>
				 </ul>			 
			 </div>
		 </div>
		 <div class="clearfix"></div>
	 </div>
</div>
<!---->
 <script>
    // You can also use "$(window).load(function() {"
    $(function () {
      // Slideshow 1
      $("#slider2").responsiveSlides({
         auto: true,
		 nav: true,
		 speed: 500,
		 namespace: "callbacks",
      });
    });
  </script>
    <script src="js/responsiveslides.min.js"></script>
<div class="single-section">
	<form action="/detail" method="get">
	 <div class="col-md-8 fashions2">			 
			<div class="slider2">
				<ul class="rslides rslider" id="slider2">				  
				  <li><img src="${product.photo}" alt=""></li>
				</ul>
		   </div>
	  </div> 
	<div class="col-md-4 side-bar2">
		  <div class="product-price">
			   <div class="product-name">
				 <h2> ${product.productName} </h2>
					<span>${product.price} &euro;</span>
					<div class="clearfix"></div>
					<h4>AVAILABLE</h4>
			  </div>
				 <p>Brand : <a href="#">${product.brand}</a></p>
				 <a class="add" href="cart.html">ADD TO BAG</a>
			 </div>
		 </div>
      </div>

	 <div class="clearfix"></div>
</div>
<!---->
<div class="footer">
	 <div class="container">
		 <p>Copyright &copy; 2022 All rights reserved | Template by  <a href="http://w3layouts.com">  W3layouts</a></p>
		 <div class="social">
			 <a href="#"><span class="icon1"></span></a>
			 <a href="#"><span class="icon2"></span></a>
			 <a href="#"><span class="icon3"></span></a>
			 <a href="#"><span class="icon4"></span></a>
		 </div>
		 <div class="clearfix"></div>
	 </div>	 
</div>
<!---->		
</body>
</html>
		