<%@ page import="model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ProductService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html class="no-js" lang="zxx">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Danh sách quan tâm || Tile Market gạch men cao cấp</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicons -->
    <link rel="shortcut icon" href="img\logo-transparent-png-icon.ico">
    <!-- Fontawesome css -->
    <link rel="stylesheet" href="css\font-awesome.min.css">
    <!-- Ionicons css -->
    <link rel="stylesheet" href="css\ionicons.min.css">
    <!-- linearicons css -->
    <link rel="stylesheet" href="css\linearicons.css">
    <link rel="stylesheet" href="css\styles.css">
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
    <script src="https://kit.fontawesome.com/9212eb0180.js" crossorigin="anonymous"></script>
</head>

<body>
<!--[if lte IE 9]>
<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="https://browsehappy.com/">upgrade
    your browser</a> to improve your experience and security.</p>
<![endif]-->
<c:if test="${sessionScope.user !=null}">
    <script>
        window.addEventListener('DOMContentLoaded', function () {
            // Gọi hàm kiểm tra sau khi trang đã tải xong
            checkWishlist();
        });

        function checkWishlist() {
            // Lấy danh sách các sản phẩm trong trang
            var productDivs = document.querySelectorAll('.product');

            // Tạo một đối tượng XMLHttpRequest
            var xhr = new XMLHttpRequest();

            // Xác định phương thức HTTP và URL của servlet
            var method = "POST";
            var url = "/danh-sach-quan-tam?action=check";

            // Thiết lập hàm xử lý khi có phản hồi từ servlet
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Xử lý phản hồi từ servlet ở đây
                        var wishlistData = JSON.parse(xhr.responseText);
                        // Kiểm tra từng sản phẩm và cập nhật hiển thị
                        productDivs.forEach(function (productDiv) {
                            var id_product = productDiv.getAttribute('data-product-id');
                            var wishlistIcon = productDiv.querySelector('i.fa-regular');
                            if (wishlistData.includes(id_product)) {
                                wishlistIcon.classList.add('fa-solid');
                            }
                        });
                    } else {
                        // Xử lý lỗi nếu có
                        console.log('Có lỗi xảy ra.');
                    }
                }
            };

            // Mở kết nối với servlet
            xhr.open(method, url, true);

            // Gửi yêu cầu đến servlet
            xhr.send();
        }
    </script>
</c:if>
<!-- Main Wrapper Start Here -->
<div class="wrapper">
    <jsp:include page="header.jsp"/>
    <!-- Breadcrumb Start -->
    <div class="breadcrumb-area mt-30">
        <div class="container">
            <div class="breadcrumb">
                <ul class="d-flex align-items-center">
                    <li><a href="Home">Trang chủ</a></li>
                    <li class="active"><a href="danh-sach-quan-tam?action=get&page=1">Danh sách quan tâm</a></li>
                </ul>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Breadcrumb End -->
    <!-- Shop Page Start -->
    <div class="main-shop-page pt-100 pb-100 ptb-sm-60">
        <div class="container">
            <!-- Row End -->
            <div class="row">
                <!-- Sidebar Shopping Option Start -->
                <div class="col-lg-3 order-2 order-lg-1">
                    <div class="sidebar">
                        <!-- Price Filter Options End -->

                        <!-- Sidebar Categorie Start -->
                        <div class="sidebar-categorie mb-40">
                            <h3 class="sidebar-title">Loại gạch</h3>
                            <ul class="sidbar-style" style="margin-left: 30px">
                                <li class="form-check">
                                    <a href="ProductLists?page=1">+ Tất cả</a>
                                </li>
                                <li class="form-check" style="color: black">
                                    <a href="ListByType?type=2&page=1">+ Gạch lát nền</a>
                                </li>

                                <li class="form-check">
                                    <a href="ListByType?type=3&page=1">+ Gạch ốp tường</a>
                                </li>
                                <li class="form-check">
                                    <a href="ListByType?type=4&page=1">+ Gạch trang trí</a>
                                </li>
                                <li class="form-check">
                                    <a href="ListByType?type=1&page=1">+ Gạch giả gỗ</a>
                                </li>

                            </ul>
                        </div>


                        <!-- Sidebar Categorie Start -->
                        <!-- Product Size Start -->

                        <!-- Product Size End -->
                        <!-- Product Color Start -->

                        <!-- Product Color End -->
                        <!-- Product Top Start -->

                        <!-- Product Top End -->
                        <!-- Single Banner Start -->
                        <div class="col-img">
                            <a href="ProductLists?page=1"><img src="img/banner/khuyenmaigachnhap-01.webp"
                                                               alt="slider-banner"></a>
                        </div>
                        <!-- Single Banner End -->
                    </div>
                </div>
                <!-- Sidebar Shopping Option End -->
                <!-- Product Categorie List Start -->
                <div class="col-lg-9 order-1 order-lg-2">
                    <!-- Grid & List View Start -->
                    <div class="grid-list-top border-default universal-padding d-md-flex justify-content-md-between align-items-center mb-30">

                        <!-- Toolbar Short Area Start -->
                        <div class="main-toolbar-sorter clearfix">

                            <!-- Toolbar Short Area End -->
                            <!-- Toolbar Short Area Start -->

                            <!-- Toolbar Short Area End -->

                            <!-- Grid & List View End -->
                            <div class="main-categorie mb-all-40">
                                <!-- Grid & List Main Area End -->
                                <div class="tab-content fix">
                                    <div id="grid-view" class="tab-pane fade show active">
                                        <div class="row">
                                            <!-- Single Product Start -->
                                            <%--@elvariable id="listP" type="java.util.List"--%>
                                            <c:forEach items="${listP}" var="p">
                                                <div class="col-lg-4 col-md-4 col-sm-6 col-6">
                                                    <div class="single-product">
                                                        <!-- Product Image Start -->
                                                        <div class="pro-img">
                                                            <a href="ProductDetail?productID=${p.productID}">
                                                                <img class="primary-img"
                                                                     src="${p.image[0].image}"
                                                                     alt="single-product">
                                                                <img class="secondary-img"
                                                                     src="${p.image[1].image}" style="height: 195px"
                                                                     alt="single-product">
                                                            </a>

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
                                                                        <del class="prev-price"><fmt:formatNumber
                                                                                type="currency" currencySymbol=""
                                                                                minFractionDigits="0"
                                                                                value="${p.price}"/> VNĐ
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
                                                                    <c:url value="/addCart?productID=${p.productID}"
                                                                           var="addCart"/>
                                                                    <a href="${addCart}" title="Thêm vào giỏ"> + Thêm
                                                                        vào
                                                                        giỏ</a>
                                                                </div>
                                                                <c:if test="${sessionScope.user !=null}">
                                                                    <div class="actions-secondary">
                                                                        <a href="javascript:void(0)"
                                                                           class="product ${p.productID}"
                                                                           data-product-id="${p.productID}"
                                                                           onclick="addToWishlist(event)">
                                                                            <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                                        </a>
                                                                    </div>
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                        <!-- Product Content End -->
                                                        <!-- <span class="sticker-new">Mới</span> -->
                                                    </div>

                                                </div>
                                            </c:forEach>
                                            <!-- Single Product End -->
                                            <!-- Single Product Start -->


                                            <!-- Single Product End -->
                                        </div>
                                        <!-- Row End -->
                                    </div>
                                </div>
                                <!-- #grid view End -->

                                <div class="pro-pagination">
                                    <ul class="blog-pagination">
                                        <c:if test="${page >4}">
                                            <li><a href="danh-sach-quan-tam?action=get&page=${page - 1}">«</a></li>
                                        </c:if>

                                        <c:forEach begin="1" end="${numberPage}" var="i">
                                            <c:choose>
                                                <c:when test="${page eq i}">
                                                    <li class="active"><a
                                                            href="danh-sach-quan-tam?action=get&page=${i}">${i}</a></li>
                                                </c:when>
                                                <c:when test="${page eq i}">
                                                    <li class="active"><a
                                                            href="danh-sach-quan-tam?action=get&page=${i}">${i}</a></li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="danh-sach-quan-tam?action=get&page=${i}">${i}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${page < numberPage}">
                                            <li><a href="danh-sach-quan-tam?action=get&page=${page + 1}">»</a></li>
                                        </c:if>

                                    </ul>

                                </div>
                                <!-- #list view End -->
                                <!-- Grid & List Main Area End -->
                            </div>
                        </div>
                        <!-- product Categorie List End -->
                    </div>
                    <!-- Row End -->
                </div>
                <!-- Container End -->
            </div>
        </div>
        <!-- Shop Page End -->
        <!-- Support Area Start Here -->
        <jsp:include page="footer.jsp"/>
        <!-- Xem nhanh Content Start -->
        <!-- Xem nhanh Content End -->
    </div>
    <!-- Main Wrapper End Here -->
    <script>
        function submit() {
            document.getElementById("sort").submit();
        }
    </script>
    <script
            src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous">
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
    <script>

        function addToWishlist(event) {
            var wishlistButton = event.target; // Thẻ <a> được click
            var productDiv = wishlistButton.closest('.product'); // Thẻ cha chứa sản phẩm
            var id_product = productDiv.getAttribute('data-product-id'); // Lấy giá trị id của sản phẩm

            // Tạo một đối tượng XMLHttpRequest
            var xhr = new XMLHttpRequest();

            // Xác định phương thức HTTP và URL của servlet
            var method = "POST";
            var url = "/danh-sach-quan-tam?action=update&id_product=" + encodeURIComponent(id_product) + "&url=" + encodeURIComponent(window.location.href);

            // Thiết lập hàm xử lý khi có phản hồi từ servlet
            xhr.onreadystatechange = function () {
                if (xhr.readyState === XMLHttpRequest.DONE) {
                    if (xhr.status === 200) {
                        // Xử lý phản hồi từ servlet ở đây
                        var isExist = JSON.parse(xhr.responseText);
                        var wishlistIcon = document.querySelector(".product." + id_product + " i.fa-regular.fa-heart");
                        if (wishlistIcon) {
                            if (!isExist) {
                                wishlistIcon.classList.add("fa-solid");
                            } else {
                                wishlistIcon.classList.remove("fa-solid");
                            }
                        }
                        headerWishlist();
                    } else {
                        // Xử lý lỗi nếu có
                        console.log('Có lỗi xảy ra.');
                    }
                }
            };

            // Mở kết nối với servlet
            xhr.open(method, url, true);

            // Gửi yêu cầu đến servlet
            xhr.send();
        }

    </script>
</body>

</html>