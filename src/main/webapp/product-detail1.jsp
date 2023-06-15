<%--
  Created by IntelliJ IDEA.
  User: longv
  Date: 4/15/2023
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="service.ProductSizeService" %>
<%@ page import="service.CategoryService" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Chi tiết sản phẩm || Truemart gạch men cao cấp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicons -->
    <link rel="shortcut icon" href="img\favicon.ico">
    <!-- Fontawesome css -->
    <link rel="stylesheet" href="css\font-awesome.min.css">
    <!-- Ionicons css -->
    <link rel="stylesheet" href="css\ionicons.min.css">
    <!-- linearicons css -->
    <link rel="stylesheet" href="css\linearicons.css">
    <!-- Nice select css -->
    <link rel="stylesheet" href="css\nice-select.css">
    <!-- Jquery fancybox css -->
    <link rel="stylesheet" href="css\jquery.fancybox.css">
    <!-- Jquery ui price slider css -->
    <link rel="stylesheet" href="css\jquery-ui.min.css">
    <!-- Meanmenu css -->
    <link rel="stylesheet" href="css\meanmenu.min.css">
    <!-- Nivo slider css -->
    <link rel="stylesheet" href="css\nivo-slider.css">
    <!-- Owl carousel css -->
    <link rel="stylesheet" href="css\owl.carousel.min.css">
    <!-- Bootstrap css -->
    <link rel="stylesheet" href="css\bootstrap.min.css">
    <!-- Custom css -->
    <link rel="stylesheet" href="css\default.css">
    <!-- Main css -->
    <link rel="stylesheet" href="css\style.css">
    <!-- Responsive css -->
    <link rel="stylesheet" href="css\responsive.css">

    <!-- Modernizer js -->
    <script src="js\vendor\modernizr-3.5.0.min.js"></script>
</head>
<body>
<!-- Main Wrapper Start Here -->
<div class="wrapper">
    <jsp:include page="header.jsp"/>
    <!-- Breadcrumb Start -->
    <div class="breadcrumb-area mt-30">
        <div class="container">
            <div class="breadcrumb">
                <ul class="d-flex align-items-center">
                    <li><a href="Home">Trang chủ</a></li>
                    <li><a href="ProductLists?page=1">Shop</a></li>
                    <li class="active"><a href="#">Sản phẩm</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <c:set var="productImage" value="${product.image}"/>
    <!-- Product Thumbnail Start -->
    <div class="main-product-thumbnail ptb-40 ptb-sm-60">
        <div class="container">
            <div class="thumb-bg">
                <div class="row">
                    <!-- Main Thumbnail Image Start -->
                    <div class="col-lg-5 mb-all-40">
                        <!-- Thumbnail Large Image start -->
                        <div class="tab-content">
                            <c:forEach var="image" items="${productImage}" varStatus="loop">
                                <c:if test="${loop.index == 0}">
                                    <div id="thumb${loop.index+1}" class="tab-pane fade show active">
                                        <a data-fancybox="images" href="${image.image}"><img src="${image.image}"
                                                                                             alt="product-view"></a>
                                    </div>
                                </c:if>
                                <c:if test="${loop.index != 0}">
                                    <div id="thumb${loop.index+1}" class="tab-pane fade show">
                                        <a data-fancybox="images" href="${image.image}"><img src="${image.image}"
                                                                                             alt="product-view"></a>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                        <!-- Thumbnail Large Image End -->
                        <!-- Thumbnail Image End -->
                        <div class="product-thumbnail mt-15">
                            <div class="thumb-menu owl-carousel nav tabs-area" role="tablist">
                                <c:forEach var="image" items="${productImage}" varStatus="loop">
                                    <c:if test="${loop.index == 0}">
                                        <a class="active" data-toggle="tab" href="#thumb1"><img src="${image.image}"
                                                                                                alt="product-thumbnail"></a>
                                    </c:if>
                                    <c:if test="${loop.index != 0}">
                                        <a data-toggle="tab" href="#thumb${loop.index+1}"><img src="${image.image}"
                                                                                               alt="product-thumbnail"></a>
                                    </c:if>

                                </c:forEach>
                            </div>
                        </div>
                        <!-- Thumbnail image end -->
                    </div>
                    <!-- Main Thumbnail Image End -->
                    <!-- Thumbnail Description Start -->
                    <div class="col-lg-7">
                        <div class="thubnail-desc fix">
                            <h3 class="product-header">${product.productName}</h3>
                            <div class="rating-summary fix mtb-10">
                                <div class="rating">
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star"></i>
                                    <i class="fa fa-star-o"></i>
                                    <i class="fa fa-star-o"></i>
                                    <i class="fa fa-star-o"></i>
                                </div>
                                <%--                                    <div class="rating-feedback">--%>
                                <%--                                        <a href="#">(1 review)</a>--%>
                                <%--                                        <a href="#">add to your review</a>--%>
                                <%--                                    </div>--%>
                            </div>
                            <div class="pro-price mtb-30">
                                <p class="d-flex align-items-center">
                                        <span class="prev-price">
                                            <fmt:formatNumber
                                                    type="currency" currencySymbol="" minFractionDigits="0"
                                                    value="${price}"/>
                                        </span>
                                    <span class="price">
                                            <fmt:formatNumber
                                                    type="currency" currencySymbol="" minFractionDigits="0"
                                                    value="${price-Math.round(price*(product.salePrice/100))}"/> VNĐ
                                        </span>
                                    <span class="saving-price">Tiết kiệm ${product.salePrice}%</span></p>
                            </div>
                            <p class="mb-20 pro-desc-details">
                                <b>Kích thước:</b> ${ProductSizeService.selectByID(product.productID)} <br>
                                <b>Ứng dụng:</b> ${CategoryService.getCateByID(product.productID)}
                            </p>
                            <form id="cart-form" action="/addCart" method="get">
                                <input name="product_id" type="hidden" value="${product.productID}">
                                <div class="product-size mb-20 clearfix">
                                    <label>Kích thước</label>
                                    <select id="sizeSelect" name="size" class="size">
                                        <c:forEach var="size" items="${product.size}">
                                            <c:if test="${size.idSize == size_id}">
                                                <c:set var="selected" value="selected"/>
                                            </c:if>
                                            <c:if test="${size.idSize != size_id}">
                                                <c:set var="selected" value=""/>
                                            </c:if>
                                            <option ${selected} class="sizeOption"
                                                                value="ProductDetail?productID=${product.productID}&color=${color_id}&size=${size.idSize}">${size.width}x${size.length}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="color clearfix mb-20">
                                    <label>Màu</label>
                                    <input name="color_id" type="hidden" value="${color_id}">
                                    <ul class="color-list">
                                        <c:forEach var="color" items="${product.color}" varStatus="loop">
                                            <c:choose>
                                                <c:when test="${color_id == color.id_color }">
                                                    <li>
                                                        <a class="${color.id_color} ${color.id_color == 1 ? 'white' : color.id_color == 2 ? 'red' : color.id_color == 3 ? 'blue' : color.id_color == 4 ? 'purple' : color.id_color == 5 ? 'yellow' : color.id_color == 6 ? 'green' : ''} active"
                                                           href="ProductDetail?productID=${product.productID}&color=${color.id_color}&size=${size_id}"></a>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li>
                                                        <a class="${color.id_color} ${color.id_color == 1 ? 'white' : color.id_color == 2 ? 'red' : color.id_color == 3 ? 'blue' : color.id_color == 4 ? 'purple' : color.id_color == 5 ? 'yellow' : color.id_color == 6 ? 'green' : ''}"
                                                           href="ProductDetail?productID=${product.productID}&color=${color.id_color}&size=${size_id}"></a>
                                                    </li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="box-quantity d-flex hot-product2">
                                    <input name="quantity" class="quantity mr-15" type="number" min="1" value="1"
                                           onchange="updateQuantityValue(this)">
                                    <input name="quantity_value" value="1" type="hidden">
                                    <div class="pro-actions">
                                        <div class="actions-primary">
                                            <%--                                           <c:url value="addCart?productID=${product.productID}&color=${color_id}" var="addCart"/>--%>
                                            <a href="addCart?product_id=${product.productID}&color_id=${color_id}&size=${size_id}"
                                               title="" data-original-title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                        </div>
                                        <div class="actions-secondary">
                                            <%--                                            <a href="compare.html" title="" data-original-title="Compare"><i class="lnr lnr-sync"></i> <span>Add To Compare</span></a>--%>
                                            <a href="wishlist.html" title="" data-original-title="WishList"><i
                                                    class="lnr lnr-heart"></i> <span>Thêm vào danh sách yêu thích</span></a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="pro-ref mt-20">
                                <p><span class="in-stock"><i class="ion-checkmark-round"></i> Còn sẵn ${quantity} sản phẩm</span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- Thumbnail Description End -->
                </div>
                <!-- Row End -->
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Product Thumbnail End -->
    <!-- Product Thumbnail Description Start -->
    <div class="thumnail-desc pb-100 pb-sm-60">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <ul class="main-thumb-desc nav tabs-area" role="tablist">
                        <li><a class="active" data-toggle="tab" href="#review">Mô Tả Sản Phẩm</a></li>
                        <li><a data-toggle="tab" href="#dtail">Hỏi Đáp</a></li>
                    </ul>
                    <!-- Product Thumbnail Tab Content Start -->
                    <div class="tab-content thumb-content border-default p-0" style="border-top: 1px solid #ededed !important;border: none;">
                        <!-- Reviews Start -->
                        <div id="dtail" class="fb-comments tab-pane m-10 ">
                            data-href="product-detail.jsp?productID=${product.productID}"
                            data-width="1100" data-numposts="5">
                        </div>
                        <!-- Reviews End -->
                        <div id="review" class="tab-pane fade fade show active">
                            <!-- Reviews Start -->
                            <div class="justify-content-center flex align-items-center" style="padding: 0 !important;">
                                ${product.description}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Product Thumbnail Tab Content End -->
            <!-- Realted Products Start Here -->
            <div class="hot-deal-products off-white-bg pt-15 pb-90 pt-sm-60 pb-sm-50">
                <div class="container">
                    <!-- Product Title Start -->
                    <div class="post-title pb-30">
                        <h2>Realted Products</h2>
                    </div>
                    <!-- Product Title End -->
                    <!-- Hot Deal Product Activation Start -->
                    <div class="hot-deal-active owl-carousel">
                        <c:forEach items="${listHintForYou}" var="p">
                            <!-- Single Product Start -->
                            <div class="single-product">
                                <!-- Product Image Start -->
                                <div class="pro-img">
                                    <a href="ProductDetail?productID=${p.productID}">
                                        <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                        <img class="secondary-img" src="${p.image[1].image}"
                                             alt="single-product">
                                    </a>
                                    <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                       title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                </div>
                                <!-- Product Image End -->
                                <!-- Product Content Start -->
                                <div class="pro-content">
                                    <div class="pro-info">
                                        <h4>
                                            <a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
                                        </h4>
                                        <p><span class="price"><fmt:formatNumber type="currency"
                                                                                 currencySymbol=""
                                                                                 minFractionDigits="0"
                                                                                 value="${p.price-Math.round(p.price*(p.salePrice/100))}"/> VNĐ </span>
                                            <c:if test="${p.salePrice>0}">
                                                <del class="prev-price"><fmt:formatNumber type="currency"
                                                                                          currencySymbol=""
                                                                                          minFractionDigits="0"
                                                                                          value="${p.price}"/>
                                                    VNĐ
                                                </del>
                                            </c:if>
                                        </p>
                                        <c:if test="${p.salePrice>0}">
                                            <div class="label-product l_sale">${p.salePrice}<span
                                                    class="symbol-percent">%</span></div>
                                        </c:if>

                                    </div>
                                    <div class="pro-actions">
                                        <div class="actions-primary">
                                            <c:url value="/addCart?productID=${product.productID}&color=${color_id}&size=${size_id}"
                                                   var="addCart"/>
                                            <a href="${addCart}" id="add-cart" title="Thêm vào giỏ"> + Thêm vào
                                                giỏ</a>
                                        </div>
                                        <div class="actions-secondary">
                                            <a href="wishlist.html" title="WishList"><i
                                                    class="lnr lnr-heart"></i> <span>Thêm vào danh sách yêu thích</span></a>
                                        </div>
                                    </div>
                                </div>
                                <!-- Product Content End -->
                            </div>
                        </c:forEach>
                        <!-- Single Product End -->
                    </div>
                    <!-- Hot Deal Product Active End -->
                </div>
            </div>
            <!-- Container End -->
        </div>
        <!-- Realated Products End Here -->
        <jsp:include page="support.jsp"/>
        <jsp:include page="footer.jsp"/>
    </div>
    <!-- Main Wrapper End Here -->
    <div id="fb-root"></div>
    <script async defer crossorigin="anonymous"
            src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v15.0&appId=1350248292455948&autoLogAppEvents=1"
            nonce="uY6kzAGR"></script>
    <!-- jquery 3.2.1 -->
    <script src="js\vendor\jquery-3.2.1.min.js"></script>
    <!-- Countdown js -->
    <script src="js\jquery.countdown.min.js"></script>
    <!-- Mobile menu js -->
    <script src="js\jquery.meanmenu.min.js"></script>
    <!-- ScrollUp js -->
    <script src="js\jquery.scrollUp.js"></script>
    <!-- Nivo slider js -->
    <script src="js\jquery.nivo.slider.js"></script>
    <!-- Fancybox js -->
    <script src="js\jquery.fancybox.min.js"></script>
    <!-- Jquery nice select js -->
    <script src="js\jquery.nice-select.min.js"></script>
    <!-- Jquery ui price slider js -->
    <script src="js\jquery-ui.min.js"></script>
    <!-- Owl carousel -->
    <script src="js\owl.carousel.min.js"></script>
    <!-- Bootstrap popper js -->
    <script src="js\popper.min.js"></script>
    <!-- Bootstrap js -->
    <script src="js\bootstrap.min.js"></script>
    <!-- Plugin js -->
    <script src="js\plugins.js"></script>
    <!-- Main activaion js -->
    <script src="js\main.js"></script>
    <script>
        function reviews() {
            var me = $('#comments').val();
            if (me === '') {

            } else {
                alert("Đánh giá thành công");
            }
        }
    </script>
    <script>
        $("select").find("option[value='option3']").prop("selected", true);
        $("#sizeSelect").change(function () {
            const selectedValue = $(this).val();
            if (selectedValue) {
                window.location.href = selectedValue;
            }
        });
    </script>
    <script>
        function updateQuantityValue(input) {
            var quantityValueInput = document.getElementsByName("quantity_value")[0];
            quantityValueInput.value = input.value;
        }
    </script>

</body>
</html>
