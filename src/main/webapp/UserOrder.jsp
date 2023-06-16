<%--@elvariable id="product" type="model.Product"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="model.Order" %>
<%@ page import="java.util.Collection" %>
<%@ page import="javax.swing.text.Document" %>
<%--@elvariable id="cart" type="model.Cart"--%>

<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Đơn hàng || Tile Market Gạch men cao cấp</title>
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
                    <li class="active"><a href="Show">Đơn hàng</a></li>
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
                            <c:if test="${order.size()==0}">
                                <h5>Bạn chưa có Đơn Hàng nào</h5>
                            </c:if>
                            <c:if test="${order.size()!=0}">
                            <table>
                                <thead>
                                <tr>
                                    <th class="order-id">Mã đơn hàng</th>
                                    <th class="product-price">Tổng hóa đơn</th>
                                    <th class="shipping-date">Ngày giao hàng</th>
                                    <th class="product-name">Trạng thái đơn hàng</th>
                                    <th class="product-size">Trạng thái vận chuyển</th>
                                    <th class="order-cancel">Hủy đơn hàng</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${order}" var="o">
                                <tr id="${o.orderID}">
                                    <td>${o.orderID}</td>
                                    <td>
                                        <fmt:formatNumber type="currency" currencySymbol="" minFractionDigits="0"
                                                          value="${o.totalPrice}"/> VNĐ
                                    </td>
                                    <td>
                                        <fmt:parseDate value="${o.shipping_time}" var="parsedDate"
                                                       pattern="yyyy-MM-dd HH:mm:ss.S"/>
                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate"/>
                                            ${formattedDate}
                                    </td>
                                    <td id="orderstatus">
                                        <c:if test="${o.status ==1}">
                                            <div class="d-flex align-items-center">
                                                <div>Đã xác nhận</div>
                                            </div>
                                        </c:if>
                                        <c:if test="${o.status ==0}">
                                            <div class="d-flex align-items-center">
                                                <div>Chưa xác nhận</div>
                                            </div>
                                        </c:if>
                                        <c:if test="${o.status ==2}">
                                            <div class="d-flex align-items-center">
                                                <div>Đã hủy</div>
                                            </div>
                                        </c:if>
                                    </td>
                                    <td id="transportstatus">
                                        <c:if test="${o.transport_status ==0}">Chưa Vận chuyển</c:if>
                                        <c:if test="${o.transport_status ==1}">Đang vận chuyển</c:if>
                                        <c:if test="${o.transport_status ==2}">Vận chuyển thành công</c:if>
                                    </td>
                                    <td class="product-remove">
                                        <c:if test="${o.status !=2 || o.transport_status ==0}">
                                            <a href="/CancelOrder?order_id=${o.orderID}"><i class="fa fa-times"
                                                                                            aria-hidden="true"></i></a>
                                        </c:if>
                                    </td>
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
