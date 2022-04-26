<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Shopper an E-Commerce online Shopping Category Flat Bootstarp responsive Website Template| Man :: w3layouts</title>
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
    <style>
        .pagination a {
            color: black;
            float: left;
            padding: 8px 16px;
            text-decoration: none;
            transition: background-color .3s;
        }

        .pagination a.active {
            background-color: dodgerblue;
            color: white;
        }

        .pagination a:hover:not(.active) {background-color: #ddd;}
    </style>
</head>
<body>
<!---->
<div class="header catalog">
    <div class="container">
        <div class="header-left">
            <div class="top-menu">
                <ul>
                    <li><a href="index.html">HOME</a></li>
                    <li class="active"><a href="catalog.html">CATALOG</a></li>
                </ul>
            </div>
        </div>
        <div class="logo">
            <a href="index.html"><img src="images/logo.png" alt=""/></a>
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
                    <li><a href="registration.html">REGISTRATION</a> <span>/<span> &nbsp;</li>
                    <li><a href="login.html"> LOGIN</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>
</div>
<!---->
<div class="men-fashions">
    <div class="container">
        <div class="col-md-9 fashions">
            <div class="title">
                <div class="pagination">
                    <a class="active" href='/catalog?page=1'>1</a>
                    <a href='/catalog?page=2'>2</a>
                    <a href='/catalog?page=3'>3</a>
                    <div class="clearfix"></div>
                </div>
            </div>
            <div class="fashion-section">
                <div class="fashion-grid1">
                    <c:forEach var="product" items="${products}">
                    <div class="col-md-4 fashion-grid">
                        <a href="single.html"><img src="<c:out value="${product.photo}"/>" alt=""/>
                            <div class="product">
                                <h3><c:out value="${product.productName}"/></h3>
                                <p><span></span> <c:out value="${product.price}"/> &euro;</p>
                            </div>
                        </a>
                        <form action="/addOrderItem" method="post">
                            <input type="hidden" value="1" name="amount">
                            <input type="hidden" name="product_id" value="${product.id}">
                                <div class="fashion-view"><span></span>
                                <div class="clearfix"></div>
                                <h4><input type="submit" class="btn btn-dark" value="Купить"></h4>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="col-md-3 side-bar">
            <div class="categories">
                <h3>CATEGORIES</h3>
                <ul>
                    <li><a href="#">ВИСКИ</a></li>
                    <li><a href="#">ВИНО</a></li>
                    <li><a href="#">РОМ</a></li>
                    <li><a href="#">ШАМПАНСКОЕ И ИГРИСТОЕ ВИНО</a></li>
                    <li><a href="#">ПИВО</a></li>
                </ul>
            </div>
        </div>
        <div class="clearfix"></div>
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
