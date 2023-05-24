<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Đăng ký || Truemart Gạch men cao cấp</title>
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
    <%--Sweet alert notify--%>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>

<body>
<!--[if lte IE 9]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade your browser</a> to improve your experience and security.</p>
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
                    <li class="active"><a href="register.jsp">Đăng kí</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <%
        String messageResponse= request.getAttribute("messageResponse") +"";
        messageResponse= ((messageResponse).equals("null"))?"":messageResponse;
        if(messageResponse.equals("error")){
    %>
    <script type="text/javascript">
        swal({
            title: "Error!",
            text: "Có gì đó sai sai!!!",
            icon: "error",
            button: "Thử lại!",
        });
    </script>
    <%
        } else if (messageResponse.equals("success")) {
    %>
    <script type="text/javascript">
        swal({
            title: "Congratulation!",
            text: "Tạo tài khoản thành công!",
            icon: "success",
            button: "Tiếp tục!",
        }).then(function() {
            window.location = "Home";
        });
    </script>
    <%
        }
    %>
    <!-- Register Account Start -->
    <div class="register-account ptb-100 ptb-sm-60">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div class="register-title">
                        <h3 class="mb-10">ĐĂNG KÍ TÀI KHOẢN</h3>
                        <p class="mb-10">Nếu bạn chưa có tài khoản, vui lòng đăng kí tại đây.</p>
                    </div>
                </div>
            </div>
            <!-- Row End -->
            <div class="row">
                <div class="col-sm-12">
                    <form class="form-register" method="post" action="/nguoi-dung" onsubmit="return checkSignUp();getAlertSignUp();">
                            <input type="hidden" name="action" value="dang-ki">
                        <fieldset>
                            <legend>Thông tin cá nhân</legend>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="f-name"><span class="require">*</span>Họ và Tên</label>
                                <div class="col-md-10">
                                    <input type="text"  required="required" class="form-control" value="<%=request.getParameter("name")==null ? "":request.getParameter("name")%>" name="name" id="f-name" placeholder="Vui lòng nhập họ và tên..">
                                    <div style="color: #e31414;font-size: 12pt;" id="error_fullname"></div>
                                </div>
                            </div>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="username"><span class="require">*</span>Tên đăng nhập </label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control"  required="required" id="username"value="<%=request.getParameter("username")==null ? "":request.getParameter("username")%>" name="username" placeholder="Vui lòng nhập tên">
                                    <div style="color: #e31414;font-size: 12pt;" id="error_username"><%=(request.getAttribute("errorUName")+"").equals("null") ? "":(request.getAttribute("errorUName")+"")%></div>
                                </div>
                            </div>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="email1"><span class="require">*</span>Email</label>
                                <div class="col-md-10">
                                    <input type="email" required="required" class="form-control" value="<%=request.getParameter("email")==null ? "":request.getParameter("email")%>" name="email" id="email1" placeholder="Nhập địa chỉ email...">
                                    <div id="error_email1"></div>
                                </div>
                            </div>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="tel"><span class="require">*</span>Số điện thoại</label>
                                <div class="col-md-10">
                                    <input type="text" class="form-control" required="required" value="<%=request.getParameter("phone")==null ? "":request.getParameter("phone")%>" name="phone" id="tel"  onkeyup="regexForTel()" placeholder="Số điện thoại">
                                    <div id="error_tel"></div>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Cài đặt mật khẩu</legend>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="pass"><span class="require">*</span>Mật khẩu:</label>
                                <div class="col-md-10">
                                    <input type="password" required="required" class="form-control" name="password" id="pass" onkeyup="regexForPass()" placeholder="Mật khẩu">
                                    <div id="error_pass"></div>
                                </div>
                            </div>
                            <div class="form-group d-md-flex align-items-md-center">
                                <label class="control-label col-md-2" for="comPass"><span class="require">*</span>Xác nhận mật khẩu</label>
                                <div class="col-md-10">
                                    <input type="password" class="form-control" name="confirm-password" id="comPass" onkeyup="checkComPass()" placeholder="Nhập lại mật khẩu...">
                                    <div id="error_compass" style="color: #e31414;font-size: 12pt;"><%=request.getAttribute("errorCPass")==null?"":request.getAttribute("errorCPass")%></div>
                                </div>
                            </div>
                            <p style="color:#62ab00; display:block; margin: auto"><%=request.getAttribute("success") == null ? " " : request.getAttribute("success")%>
                            </p>
                            <p style="color:red; display:block; margin: auto"><%=request.getAttribute("msg") == null ? " " : request.getAttribute("msg")%>
                            </p>

                        </fieldset>

                        <div class="terms">
                            <div class="float-md-right">
                                <span>Tôi đã đọc và đồng ý <a href="#" class="agree"><b>chính sách bảo mật của cửa hàng</b></a></span>
                                <input type="checkbox" name="agree" value="1" id="checkLicense"> &nbsp;
                                <input type="submit" value="Tiếp tục" onclick="signUp();getAlertSignUp();" class="return-customer-btn">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Row End -->
        </div>
        <!-- Container End -->
    </div>
    <!-- Register Account End -->
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
<%--custom script--%>
<script src="js\script.js"></script>

</body>
</html>