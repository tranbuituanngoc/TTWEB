﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Đăng nhập || Tile Market Gạch men cao cấp</title>
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
    <%--Sweet alert notify--%>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <%--   Google ReCapcha --%>
    <script src="https://www.google.com/recaptcha/api.js?render=6LfaJM4lAAAAAIZJo4uMpLgyFwkQDp2x4hUguTwY"></script>
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
                    <li class="active"><a href="login.html">Đăng nhập</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <!-- LogIn Page Start -->
    <div class="log-in ptb-100 ptb-sm-60">
        <div class="container">
            <div class="row">
                <!-- New Customer Start -->
                <div class="col-md-6">
                    <div class="well mb-sm-30">
                        <div class="new-customer">
                            <h3 class="custom-title">ĐĂNG KÍ</h3>
                            <br>
                            <p>Bằng cách tạo tài khoản, bạn sẽ có thể mua sắm nhanh hơn, cập nhật trạng thái đơn hàng và
                                theo dõi các đơn hàng bạn đã thực hiện trước đó</p>
                            <a class="customer-btn" href="register.jsp">Tiếp tục</a>
                        </div>
                    </div>
                </div>
                <!-- New Customer End -->
                <%
                    String messageResponse = request.getAttribute("messageResponse") + "";
                    messageResponse = ((messageResponse).equals("null")) ? "" : messageResponse;
                    if (messageResponse.equals("error")) {
                %>
                <script type="text/javascript">
                    swal({
                        title: "Error!",
                        text: "<%=request.getAttribute("error")==null ?" ":request.getAttribute("error")%>",
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
                        text: "<%=request.getAttribute("error")==null ?" ":request.getAttribute("error")%>",
                        icon: "success",
                        button: "Tiếp tục.",
                    });
                </script>
                <%
                } else if (messageResponse.equals("info")) {
                %>
                <script type="text/javascript">
                    swal({
                        title: "Xác Thực Không Thành Công!",
                        text: "<%=request.getAttribute("error")==null ?" ":request.getAttribute("error")%>",
                        <% String id_user= (String) request.getAttribute("id_user");
                           String link = "http://localhost:8080/nguoi-dung?action=xac-thuc-lai&id_user=" + id_user ; %>
                        icon: "warning",
                        buttons: {
                            cancel: 'Hủy',
                            confirm: {
                                text: 'Xác thực lại!',
                                value: 'confirm',
                                className: 'swal-confirm-button',
                            },
                        },
                        dangerMode: true,
                    }).then((value) => {
                        if (value === "confirm") {
                            window.location.href = '<%=link%>';
                        }
                    });

                    <%--Swal.fire({--%>
                    <%--    title: 'Xác Thực Không Thành Công!',--%>
                    <%--    text: "<%=request.getAttribute("error")==null ?" ":request.getAttribute("error")%>",--%>
                    <%--    icon: 'warning',--%>
                    <%--    showCancelButton: "Hủy",--%>
                    <%--    confirmButtonColor: '#3085d6',--%>
                    <%--    cancelButtonColor: '#d33',--%>
                    <%--    <%--%>
                    <%--    String id_user= (String) request.getAttribute("id_user");--%>
                    <%--    String link = "http://localhost:8080/nguoi-dung?action=xac-thuc-lai&id_user=" + id_user ;--%>
                    <%--    %>--%>
                    <%--    confirmButtonText: '<a href="<%=link%>" style="text-decoration: none; color: inherit;">Xác thực lại!</a>'--%>
                    <%--})--%>
                </script>
                <%
                    }
                %>
                <!-- Returning Customer Start -->
                <div class="col-md-6">
                    <div class="well">
                        <div class="return-customer">
                            <h3 class="mb-10 custom-title">ĐĂNG NHẬP</h3>
                            <br>
                            <form action="/nguoi-dung" method="post" onsubmit="return signIn(); getAlert()">
                                <input type="hidden" name="action" value="dang-nhap">
                                <%--<p style="color:red; display:block"><%=request.getAttribute("error")==null ?" ":request.getAttribute("error")%></p>--%>
                                <div class="form-group">
                                    <label>Tài khoản</label>
                                    <input type="text"
                                           value="<%=request.getParameter("username")==null ? "":request.getParameter("username")%>"
                                           id="username2" name="username" placeholder="Nhập username..."
                                           class="form-control">
                                    <div id="error_username2"></div>
                                </div>
                                <div class="form-group">
                                    <label>Mật khẩu</label>
                                    <input type="password" name="password" placeholder="Mật khẩu" id="password2"
                                           class="form-control">
                                    <div id="error_password2"></div>
                                </div>
                                <p class="lost-password"><a href="forgot-password.jsp">Quên mật khẩu?</a></p>
                                <br>
                                <div class="justify-content-center">
                                    <div class="g-recaptcha"
                                         data-sitekey="6LfaJM4lAAAAAIZJo4uMpLgyFwkQDp2x4hUguTwY"></div>
                                </div>

                                <input type="submit" value="Đăng nhập" onclick="signIn();getAlert();"
                                       class="return-customer-btn">
                                <br/>
                            </form>
                            <div class="or-divider">
                                <span class="or-text">Hoặc</span>
                            </div>
                            <div class="social-login">
                                <form class="form-fbgg" action="<c:url value='/loginSocial'/>" method="post">
                                    <input type="hidden" name="provider" value="Facebook">
                                    <input type="hidden" name="redirect_uri" value="<c:url value='/loginCallback'/>">
                                    <button class="btn-fbgg" type="submit">
                                        <i class="fa fa-facebook"></i> Đăng nhập bằng Facebook
                                    </button>
                                </form>
                                <form class="form-fbgg" action="<c:url value='/loginSocial'/>" method="post">
                                    <input type="hidden" name="provider" value="Google">
                                    <input type="hidden" name="redirect_uri" value="<c:url value='/loginCallback'/>">
                                    <button class="btn-fbgg-google" type="submit">
                                        <i class="fa fa-google"></i> Đăng nhập bằng Google
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <style>
                    .or-divider {
                        display: flex;
                        align-items: center;
                        text-align: center;
                        margin: 10px 0;
                    }

                    .or-divider:before,
                    .or-divider:after {
                        content: "";
                        flex: 1;
                        border-bottom: 1px solid #d9d9d9;
                    }

                    .or-divider .or-text {
                        padding: 0 10px;
                        color: #8c8c8c;
                        font-weight: bold;
                    }

                    /* Đăng nhập */
                    .custom-title {
                        font-size: 24px;
                        font-weight: bold;
                        margin-bottom: 20px;
                    }

                    .form-group {
                        margin-bottom: 10px;
                    }

                    .form-control {
                        border: 1px solid #d9d9d9;
                        border-radius: 4px;
                        height: 40px;
                        padding: 8px;
                    }

                    .lost-password {
                        text-align: right;
                        margin-top: 10px;
                    }

                    .return-customer-btn {
                        background: #e62e04 none repeat scroll 0 0;
                        border: medium none;
                        color: #fff;
                        border-radius: 4px;
                        height: 40px;
                        width: 100%;
                        font-size: 15px;
                        margin-top: 10px;
                        cursor: pointer;
                        transition: all 300ms ease-in 0s;
                    }

                    .return-customer-btn:hover {
                        background-color: #000;
                    }
                    .g-recaptcha{
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }
                    .social-login {
                        display: flex;
                        justify-content: center;
                        align-items: center;
                    }

                    .social-login form {
                        margin: 0 10px;
                    }
                    /* Button Facebook */

                    .btn-fbgg {
                        background-color: #3b5998;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        height: 40px;
                        padding: 8px 16px;
                        margin: 10px;
                        cursor: pointer;
                        font-size: 16px;
                        display: flex;
                        align-items: center;
                    }

                    .btn-fbgg:hover {
                        background-color: #2d4373;
                    }

                    .btn-fbgg i {
                        margin-right: 10px;
                    }

                    /* Button Google */

                    .btn-fbgg-google {
                        background-color: #dd4b39;
                        color: #fff;
                        border: none;
                        border-radius: 4px;
                        height: 40px;
                        padding: 8px 16px;
                        margin: 10px;
                        cursor: pointer;
                        font-size: 16px;
                        display: flex;
                        align-items: center;
                    }

                    .btn-fbgg-google:hover {
                        background-color: #c23321;
                    }

                    .btn-fbgg-google i {
                        margin-right: 10px;
                    }
                </style>
                <!-- Returning Customer End -->
            </div>
            <!-- Row End -->
        </div>
        <!-- Container End -->
    </div>

    <!-- LogIn Page End -->
    <jsp:include page="footer.jsp"/>
</div>
<!-- Main Wrapper End Here -->
<!-- Jquery min js -->
<script src="js\vendor\jquery-3.2.1.min.js"></script>
<!-- Proper js -->
<script src="js\proper.js"></script>
<!-- Bootstrap js -->
<script src="js\bootstrap.min.js"></script>
<!-- Meanmenu js -->
<script src="js\jquery.meanmenu.min.js"></script>
<!-- Wow js -->
<script src="js\wow.min.js"></script>
<!-- Slick js -->
<script src="js\slick.min.js"></script>
<!-- Owl carousel js -->
<script src="js\owl.carousel.min.js"></script>
<!-- Countdown js -->
<script src="js\jquery.countdown.min.js"></script>
<!-- Jquery ui price slider js -->
<script src="js\jquery-ui.min.js"></script>
<!-- Fancybox js -->
<script src="js\jquery.fancybox.min.js"></script>
<!-- Nivo slider js -->
<script src="js\nivo-slider/jquery.nivo.slider.pack.js"></script>
<!-- Ajax Mail js -->
<script src="js\ajax-mail.js"></script>
<!-- Others js -->
<script src="js\plugins.js"></script>
<!-- Main js -->
<script src="js\main.js"></script>
<%--Custom script--%>
<script src="js\script.js"></script>
</body>

</html>