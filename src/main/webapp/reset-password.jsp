<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Quên mật khẩu || Tile Market Gạch men cao cấp</title>
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
    <jsp:include page="header.jsp"/>
    <!-- Breadcrumb Start -->
    <div class="breadcrumb-area mt-30">
        <div class="container">
            <div class="breadcrumb">
                <ul class="d-flex align-items-center">
                    <li><a href="index.jsp">Trang chủ</a></li>
                    <li class="active"><a href="forgot-password.jsp">Quên mật khẩu</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <!-- LogIn Page Start -->
    <div class="log-in ptb-100 ptb-sm-60">
        <div class="container">
            <div class="row justify-content-center">
                <!-- Returning Customer Start -->
                <div class="col-md-6 center">
                    <div class="well">
                        <div class="return-customer">
                            <h3 class="mb-10 custom-title">Quên mật khẩu</h3>
                            <br>
                            <form action="/nguoi-dung" method="post"
                                  onsubmit="return (regexForPass()&&checkComPass())">
                                <input type="hidden" name="action" value="tao-lai-mat-khau">
                                <input type="hidden" name="token" value="${token}">
                                <div class="form-group">
                                    <label>Mật khẩu mới</label>
                                    <input type="password" name="newPassword" id="pass" class="form-control"
                                           onkeyup="regexForPass()">
                                </div>
                                <div id="error_pass"></div>
                                <div class="form-group">
                                    <label>Nhập lại mật khẩu mới</label>
                                    <input type="password" name="comNewPass" id="comPass" class="form-control"
                                           onkeyup="checkComPass()">
                                </div>
                                <div id="error_compass" style="color: #e31414;font-size: 12pt;"></div>
                                <input type="submit" value="Đổi mật khẩu" class="return-customer-btn">
                                <br/>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- Returning Customer End -->
            </div>
            <!-- Row End -->
        </div>
        <!-- Container End -->
    </div>
    <!-- LogIn Page End -->
    <!-- Support Area Start Here -->
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
<%--Custom script--%>
<script src="js\script.js"></script>
</body>

</html>