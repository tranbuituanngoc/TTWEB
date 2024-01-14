<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Hoàn Tiền || Tile Market Gạch men cao cấp</title>
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
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade
    your browser</a> to improve your experience and security.</p>
<![endif]-->

    <!-- Main Wrapper Start Here -->
    <div class="wrapper">
        <!-- Banner Popup Start -->
<%--        menu--%>
        <jsp:include page="header.jsp"/>
        <!-- Categorie Menu & Slider Area End Here -->
        <!-- Breadcrumb Start -->

        <!-- Breadcrumb End -->
        <!-- About Us Start Here -->
        <!-- Error 404 Area Start -->
        <div class="error404-area ptb-100 ptb-sm-60">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="error-wrapper text-center">
                            <%
                                boolean res= (boolean) request.getAttribute("msgRefund");
                                String idOrder= (String) request.getAttribute("idOrder");
                                if(res==true){
                            %>
                                <div class="error-text">
                                    <h2>Thành Công!</h2>
                                    <h4>Đơn hàng <b><%=idOrder%></b> của bạn đã được hoàn tiền thành công.</h4>
                                    <p>Việc hoàn tiền đang được tiến hành và có thể sẽ mất từ 1 đến 2 ngày. Nếu có thắc mắc xin hãy liên hệ đến bộ phận chăm sóc khách hàng của chúng tôi.</p>
                                    <p>Xin cảm ơn!</p>
                                </div>
                            <%
                                }else{
                            %>
                                <div class="error-text">
                                    <h2>Thất Bại!</h2>
                                    <h4>Đơn hàng <b><%=idOrder%></b> của bạn hoàn tiền không thành công.</h4>
                                    <p>Nếu có vấn đề gì hay thắc mắc vui lòng liên hệ đến bộ phận chăm sóc khách hàng của chúng tôi.</p>
                                    <p>Xin cảm ơn!</p>
                                </div>
                            <%
                                }
                            %>
                            <div class="error-button">
                                <a href="Home">Quay về trang chủ</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- About Us Skills End Here -->
        <!-- Support Area Start Here -->

        <!-- Footer Area End Here -->
        <jsp:include page="footer.jsp"/>
        <!-- Quick View Content Start -->

        <!-- Quick View Content End -->
    </div>
    <!-- Main Wrapper End Here -->

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