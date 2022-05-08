<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| Cart :: w3layouts</title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
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
<!---- tabs---->
<link type="text/css" rel="stylesheet" href="css/easy-responsive-tabs.css" />

</head>
<body>
<!---->
<div class="header">
	 <div class="container">
		 <div class="header-left">
			 <div class="top-menu">
				 <ul>
				 <li class="active"><a href="index.html">HOME</a></li>
				 <li><a class="scroll" href="/catalog">CATALOG</a></li>
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
				  <a href="/cart"><img href="/cart" src="images/cart.png" alt=""/>(0)</a></div>
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
<div class="cart">
	 <div class="container">
		 <div class="cart-info">
			 <div class="row">
				 <c:forEach var="order_item" items="${order_items}">
				 <div class="col-lg-3 col-sm-3 col-xs-12" style="height: 100px; line-height: 100px;">
					 <img src="<c:out value="${order_item.product.photo}"/>" alt="" style="width: 80px; height: 80px;  " />
				 </div>
				 <div  class="col-lg-3 col-sm-3 col-xs-12 mob-fix" style="height: 100px; line-height: 100px;">
					 <c:out value="${order_item.product.productName}"/>
				 </div>
				 <div class="col-lg-2 col-sm-2 col-xs-12 mob-fix" style="height: 100px; line-height: 100px;">
					 <c:out value="${order_item.product.price}"/>
				 </div>
				 <div class="col-lg-1 col-sm-2 col-xs-12 mob-fix" style="height: 100px; line-height: 100px;">
					 <c:out value="${order_item.amount}"/>
				 </div>
				 <div class="col-lg-3 col-sm-2 col-xs-12 mob-fix">
					 <a href="${pageContext.request.contextPath}/deleteOrder?order_item_id=${order_item.id}">Удалить</a>
				 </div>
				 </c:forEach>
			 </div>
		 <h3></h3>
		 <a href="/catalog">CONTINUE SHOPPING</a>
		 </div>
	 <div class="cart-list">
			<h3>Categories</h3>
			<div class="col-md-4 carting">
				 <ul>
					 <li><a href="#">Always free from repetition</a></li>
					 <li><a href="#">Vestibulum rhoncus nibh quis dui fermentum iaculis.</a></li>
					 <li><a href="#">The standard chunk of Lorem Ipsum</a></li>
					 <li><a href="#">In consequat dolor in lorem egestas ultrices.</a></li>
					 <li><a href="#">Aliquam sollicitudin eros id luctus consequat.</a></li>
					 <li><a href="#">Always free from repetition</a></li>
				 </ul>
			</div>
			<div class="col-md-4 carting">
				 <ul>
					 <li><a href="#">Always free from repetition</a></li>
					 <li><a href="#">Vestibulum rhoncus nibh quis dui fermentum iaculis.</a></li>
					 <li><a href="#">The standard chunk of Lorem Ipsum</a></li>
					 <li><a href="#">In consequat dolor in lorem egestas ultrices.</a></li>
					 <li><a href="#">Aliquam sollicitudin eros id luctus consequat.</a></li>
					 <li><a href="#">Always free from repetition</a></li>
				 </ul>
			</div>
			<div class="col-md-4 carting">
				 <ul>
					 <li><a href="#">Always free from repetition</a></li>
					 <li><a href="#">Vestibulum rhoncus nibh quis dui fermentum iaculis.</a></li>
					 <li><a href="#">The standard chunk of Lorem Ipsum</a></li>
					 <li><a href="#">In consequat dolor in lorem egestas ultrices.</a></li>
					 <li><a href="#">Aliquam sollicitudin eros id luctus consequat.</a></li>
					 <li><a href="#">Always free from repetition</a></li>
				 </ul>
			</div>
			<div class="clearfix"></div>
	 </div>
	 </div>
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