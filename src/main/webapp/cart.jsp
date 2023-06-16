<%--@elvariable id="product" type="model.Product"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Cart" %>
<%@ page import="model.Product" %>
<%@ page import="java.util.Collection" %>
<%@ page import="javax.swing.text.Document" %>
<%--@elvariable id="cart" type="model.Cart"--%>

<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Giỏ hàng || Tile Market Gạch men cao cấp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicons -->
    <link rel="shortcut icon" href="img\logo-transparent-png-icon.ico">
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
<!--[if lte IE 9]>
<![endif]-->
<!-- Main Wrapper Start Here -->
<div class="wrapper">

    <!-- Banner Popup Start -->
    <jsp:include page="header.jsp"/>
    <!-- Categorie Menu & Slider Area End Here -->
    <!-- Breadcrumb Start -->
    <div class="breadcrumb-area mt-30">
        <div class="container">
            <div class="breadcrumb">
                <ul class="d-flex align-items-center">
                    <li><a href="Home">Trang chủ</a></li>
                    <li class="active"><a href="Show">Giỏ hàng</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <!-- Cart Main Area Start -->
    <div class="cart-main-area ptb-100 ptb-sm-60">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <!-- Form Start -->
                    <form action="UpdateCart" method="get">
                        <!-- Table Content Start -->
                        <div class="table-content table-responsive mb-45">
                            <c:if test="${cartUser.size()==0}">
                                <h5>Bạn chưa có sản phẩm nào trong giỏ hàng</h5>
                            </c:if>
                            <c:if test="${cartUser.size()!=0}">
                            <table>
                                <thead>
                                <tr>
                                    <th class="product-thumbnail">Hình ảnh</th>
                                    <th class="product-name">Sản phẩm</th>
                                    <th class="product-size">Kích thước</th>
                                    <th class="product-color">Màu sắc</th>
                                    <th class="product-price">Đơn giá</th>
                                    <th class="product-quantity">Số lượng</th>
                                    <th class="product-subtotal">Tổng</th>
                                    <th class="product-remove">Xóa</th>
                                </tr>
                                </thead>
                                <tbody>

                                    <c:forEach items="${cartUser.getCart()}" var ="cart" varStatus="status">
                                        <tr>
                                            <c:set value="${cart.product}" var="product"></c:set>
                                            <c:set value="${cartUser.getValue()[status.index]}" var="quantity"></c:set>
                                            <td class="product-thumbnail">
                                                <a href="javascript:void(0)"><img
                                                        src="${product.image[1].image}"
                                                        alt="cart-image"></a>
                                                <input name="product_id" value="${product.productID}" type="hidden">
                                            </td>
                                            <td class="product-name"><a target="_blank"
                                                                        href="ProductDetail?productID=${product.productID}">${product.productName}</a>
                                            </td>
<%--                                            <input type="hidden" value="${cart.color.id_color}" name="id_color">--%>
<%--                                            <input type="hidden" value="${cart.size.idSize}" name="id_size">--%>
                                            <td class="product-size">${cart.size.width}x${cart.size.length}</td>
                                            <td class="product-color">${cart.color.descrip}</td>
                                            <td class="product-price"><span class="amount"><fmt:formatNumber type="currency"
                                                                                                             currencySymbol=""
                                                                                                             minFractionDigits="0"
                                                                                                             value="${product.priceAfterSale}"/> VNĐ</span>
                                            </td>
                                            <td class="product-quantity">
                                                <c:set value="${cart}" var="cartUpdate"></c:set>
                                                <input min="1" id="quantity" onchange="updateQuantity('${product.productID}', this.value, '${cart.color.id_color}', '${cart.size.idSize}', '${cart.idCart}')"
                                                       type="number"
                                                       value="${quantity}">

                                            <%--                                                <input type="hidden" id="quantity_value" name="quantity_value">--%>
                                            </td>
                                            <td class="product-subtotal"><fmt:formatNumber type="currency" currencySymbol=""
                                                                                           minFractionDigits="0"
                                                                                           value="${product.priceAfterSale*quantity}"/>
                                                VNĐ
                                            </td>
                                            <td class="product-remove">
                                                <a href="/DeleteCart?product_id=${product.productID}&id_color=${cart.color.id_color}&id_size=${cart.size.idSize}&cartId=${cart.idCart}"><i class="fa fa-times" aria-hidden="true"></i></a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                </c:if>
                            </table>
                        </div>
                        <!-- Table Content Start -->
                        <div class="row">
                            <!-- Cart Button Start -->

                            <div class="col-md-8 col-sm-12">
                                <div class="buttons-cart">
                                    <a href="ProductLists">Tiếp tục mua sắm</a>
                                </div>
                            </div>

                            <!-- Cart Button Start -->
                            <!-- Cart Totals Start -->
                            <div class="col-md-4 col-sm-12">
                                <div class="cart_totals float-md-right text-md-right">
                                    <h2>SỐ TIỀN THANH TOÁN</h2>
                                    <br>
                                    <table class="float-md-right">
                                        <tbody>
                                        <tr class="cart-subtotal">

                                        </tr>
                                        <tr class="order-total">
                                            <th>Tổng:</th>
                                            <td>
                                                <strong><span class="amount"><fmt:formatNumber type="currency"
                                                                                               currencySymbol=""
                                                                                               minFractionDigits="0"
                                                                                               value="${cartUser.getTotalValue()}"/>VNĐ</span></strong>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="wc-proceed-to-checkout">
                                        <a href="Payment">THANH TOÁN</a>
                                    </div>
                                </div>
                            </div>
                            <!-- Cart Totals End -->
                        </div>
                        <!-- Row End -->
                    </form>
                    <!-- Form End -->
                </div>
            </div>
            <!-- Row End -->
        </div>
    </div>
    <!-- Cart Main Area End -->
    <!-- Support Area Start Here -->
    <%--        footer--%>
    <jsp:include page="footer.jsp"/>
    <!-- Footer Area End Here -->
    <!-- Quick View Content Start -->

    <!-- Quick View Content End -->
</div>
<!-- Main Wrapper End Here -->
<script>

</script>
<script>
    function updateQuantity(itemId, quantity, colorId, sizeId, idcart) {
        console.log(quantity)
        // Make an AJAX call to the servlet to update the quantity of the item
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/UpdateCart?product_id=" + itemId + "&quantity_value=" + quantity + "&id_color=" + colorId + "&id_size=" + sizeId + "&id_cart=" + idcart, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Handle success
                location.reload();
            }
        };
        xhr.send();
    }
</script>
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
</body>

</html>
