<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<!Doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Truemart gạch men cao cấp</title>
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
    <!-- Categorie Menu & Slider Area Start Here -->
    <div class="main-page-banner pb-50 off-white-bg home-3">
        <!-- Container End -->
        <!-- Slider Area Start Here -->
        <div class="slider_box">
            <div class="slider-wrapper theme-default">
                <!-- Slider Background  Image Start-->
                <div id="slider" class="nivoSlider">
                    <a href="ProductLists?page=1"><img src="img\slider\5.jpg" data-thumb="img/slider/5.jpg" alt=""
                                                       title="#htmlcaption"></a>
                    <a href="ProductLists?page=1"><img src="img\slider\6.jpg" data-thumb="img/slider/6.jpg" alt=""
                                                       title="#htmlcaption2"></a>
                </div>
                <!-- Slider Background  Image Start-->
            </div>
        </div>
        <!-- Slider Area End Here -->
    </div>
    <!-- Categorie Menu & Slider Area End Here -->
    <!-- Brand Banner Area Start Here -->
    <div class="image-banner pb-30 off-white-bg">
        <div class="container">
            <div class="col-img">
                <a href="#"><img src="img\banner\h1-banner.jpg" alt="image banner"></a>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Brand Banner Area End Here -->
    <!-- Trending Products End Here -->
    <div class="trendig-product pb-10 off-white-bg">
        <div class="container">
            <div class="trending-box">
                <div class="title-box">
                    <h2>Trending Styles</h2>
                </div>
                <div class="product-list-box">
                    <!-- Arrivals Product Activation Start Here -->
                    <div class="trending-pro-active owl-carousel">
                        <c:forEach items="${listNewProduct}" var="p">
                            <!-- Single Product Start -->
                            <div class="single-product">
                                <!-- Product Image Start -->
                                <div class="pro-img">
                                    <a href="ProductDetail?productID=${p.productID}">
                                        <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                        <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                    </a>
                                    <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                       title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                </div>
                                <!-- Product Image End -->
                                <!-- Product Content Start -->
                                <div class="pro-content">
                                    <div class="pro-info">
                                        <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a></h4>
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
                                            <c:url value="/addCart?productID=${p.productID}"
                                                   var="addCart"/>
                                            <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                        </div>
                                        <c:if test="${sessionScope.user !=null}">
                                        <div class="actions-secondary">
                                            <a href="javascript:void(0)" class="product ${p.productID}"
                                               data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                            </a>
                                        </div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Product Content End -->
                            </div>
                        </c:forEach>
                        <!-- Single Product End -->
                    </div>
                    <!-- Arrivals Product Activation End Here -->
                </div>
                <!-- main-product-tab-area-->
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Trending Products End Here -->
    <!-- Trending Products End Here -->
    <div class="trendig-product pb-100 off-white-bg pb-sm-60">
        <div class="container">
            <div class="trending-box">
                <div class="title-box">
                    <h2>Best Selling</h2>
                </div>
                <div class="product-list-box">
                    <!-- Arrivals Product Activation Start Here -->
                    <div class="trending-pro-active owl-carousel">
                        <c:forEach items="${listBestSeller}" var="p">
                            <!-- Single Product Start -->
                            <div class="single-product">
                                <!-- Product Image Start -->
                                <div class="pro-img">
                                    <a href="ProductDetail?productID=${p.productID}">
                                        <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                        <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                    </a>
                                    <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                       title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                </div>
                                <!-- Product Image End -->
                                <!-- Product Content Start -->
                                <div class="pro-content">
                                    <div class="pro-info">
                                        <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a></h4>
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
                                            <c:url value="/addCart?productID=${p.productID}"
                                                   var="addCart"/>
                                            <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                        </div>
                                        <c:if test="${sessionScope.user !=null}">
                                            <div class="actions-secondary">
                                                <a href="javascript:void(0)" class="product ${p.productID}"
                                                   data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                    <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                </a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <!-- Product Content End -->
                            </div>
                        </c:forEach>
                        <!-- Single Product End -->
                    </div>
                    <!-- Arrivals Product Activation End Here -->
                </div>
                <!-- main-product-tab-area-->
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Trending Products End Here -->
    <!-- Hot Deal Products Start Here -->
    <div class="hot-deal-products pt-100 pt-sm-60">
        <div class="container">
            <div class="all-border">
                <!-- Product Title Start -->
                <div class="section-ttitle mb-30">
                    <h2>hot deals </h2>
                </div>
                <!-- Product Title End -->
                <!-- Hot Deal Product Activation Start -->
                <div class="hot-deal-active3 owl-carousel">
                    <div class="row">
                        <!-- Main Thumbnail Image Start -->
                        <div class="col-lg-6 mb-all-40 hot-product2 ">
                            <!-- Thumbnail Large Image start -->
                            <div class="tab-content">
                                <div id="thumb1" class="tab-pane fade show active">
                                    <a data-fancybox="images" href="img\products\35.jpg"><img src="img\products\35.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb2" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\13.jpg"><img src="img\products\13.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb3" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\15.jpg"><img src="img\products\15.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb4" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\4.jpg"><img src="img\products\4.jpg"
                                                                                             alt="product-view"></a>
                                </div>
                            </div>
                            <!-- Thumbnail Large Image End -->
                            <!-- Thumbnail Image End -->
                            <div class="product-thumbnail">
                                <div class="pro-tab-menu nav tabs-area" role="tablist">
                                    <a class="active" data-toggle="tab" href="#thumb1"><img src="img\products\35.jpg"
                                                                                            alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb2"><img src="img\products\13.jpg"
                                                                             alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb3"><img src="img\products\15.jpg"
                                                                             alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb4"><img src="img\products\4.jpg"
                                                                             alt="product-thumbnail"></a>
                                </div>
                            </div>
                            <!-- Thumbnail image end -->
                        </div>
                        <!-- Main Thumbnail Image End -->
                        <!-- Thumbnail Description Start -->
                        <div class="col-lg-6 hot-product2">
                            <div class="thubnail-desc fix">
                                <div class="countdown" data-countdown="2025/03/01"></div>
                                <h3><a href="product.html">Light Inverted Pendant Qu...</a></h3>
                                <div class="pro-price mtb-30">
                                    <p><span class="price">$90.45</span>
                                        <del class="prev-price">$105.50</del>
                                    </p>
                                    <div class="label-product l_sale">6<span class="symbol-percent">%</span></div>
                                </div>
                                <p class="mb-30 pro-desc-details"> Canon's press material for the EOS 5D states that it
                                    'defines (a) new D-SLR category', while we're not typically too concerned with
                                    marketing talk this particular statement is clearly pretty accurate. The EOS 5D is
                                    unlike any previous digital SLR in that it combines a full-frame (35 mm sized) high
                                    resolution sensor (12.8 megapixels) with a relatively compact body (slightly larger
                                    than the EOS.</p>
                                <div class="pro-actions">
                                    <div class="actions-primary">
                                        <a href="cart.html" title="Add to Cart"> + Add To Cart</a>
                                    </div>
                                    <div class="actions-secondary">
                                        <a href="compare.html" title="Compare"><i class="lnr lnr-sync"></i> <span>Add To Compare</span></a>
                                        <a href="wishlist.html" title="WishList"><i class="fa-regular fa-heart"></i>
                                            <span>Add to WishList</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Thumbnail Description End -->
                    </div>
                    <div class="row">
                        <!-- Main Thumbnail Image Start -->
                        <div class="col-lg-6 mb-all-40 hot-product2 ">
                            <!-- Thumbnail Large Image start -->
                            <div class="tab-content">
                                <div id="thumb11" class="tab-pane fade show active">
                                    <a data-fancybox="images" href="img\products\21.jpg"><img src="img\products\21.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb21" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\22.jpg"><img src="img\products\22.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb31" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\23.jpg"><img src="img\products\23.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                                <div id="thumb41" class="tab-pane fade">
                                    <a data-fancybox="images" href="img\products\24.jpg"><img src="img\products\24.jpg"
                                                                                              alt="product-view"></a>
                                </div>
                            </div>
                            <!-- Thumbnail Large Image End -->
                            <!-- Thumbnail Image End -->
                            <div class="product-thumbnail">
                                <div class="pro-tab-menu nav tabs-area" role="tablist">
                                    <a class="active" data-toggle="tab" href="#thumb11"><img src="img\products\21.jpg"
                                                                                             alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb21"><img src="img\products\22.jpg"
                                                                              alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb31"><img src="img\products\23.jpg"
                                                                              alt="product-thumbnail"></a>
                                    <a data-toggle="tab" href="#thumb41"><img src="img\products\24.jpg"
                                                                              alt="product-thumbnail"></a>
                                </div>
                            </div>
                            <!-- Thumbnail image end -->
                        </div>
                        <!-- Main Thumbnail Image End -->
                        <!-- Thumbnail Description Start -->
                        <div class="col-lg-6 hot-product2">
                            <div class="thubnail-desc fix">
                                <div class="countdown" data-countdown="2025/03/01"></div>
                                <h3><a href="product.html">Flos Chasen S2 Suspension </a></h3>
                                <div class="pro-price mtb-30">
                                    <p><span class="price">$84.45</span>
                                        <del class="prev-price">$105.50</del>
                                    </p>
                                    <div class="label-product l_sale">20<span class="symbol-percent">%</span></div>
                                </div>
                                <p class="mb-30 pro-desc-details"> Canon's press material for the EOS 5D states that it
                                    'defines (a) new D-SLR category', while we're not typically too concerned with
                                    marketing talk this particular statement is clearly pretty accurate. The EOS 5D is
                                    unlike any previous digital SLR in that it combines a full-frame (35 mm sized) high
                                    resolution sensor (12.8 megapixels) with a relatively compact body (slightly larger
                                    than the EOS.</p>
                                <div class="pro-actions">
                                    <div class="actions-primary">
                                        <a href="cart.html" title="Add to Cart"> + Add To Cart</a>
                                    </div>
                                    <div class="actions-secondary">
                                        <a href="compare.html" title="Compare"><i class="lnr lnr-sync"></i> <span>Add To Compare</span></a>
                                        <a href="wishlist.html" title="WishList"><i class="fa-regular fa-heart"></i>
                                            <span>Add to WishList</span></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Thumbnail Description End -->
                    </div>
                </div>
                <!-- Hot Deal Product Active End -->
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Hot Deal Products End Here -->
    <!-- Big Banner Start Here -->
    <div class="big-banner mt-100 pb-85 mt-sm-60 pb-sm-45">
        <div class="container banner-2">
            <div class="banner-box">
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-1.jpg" alt="banner 3"></a>
                </div>
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-2.jpg" alt="banner 3"></a>
                </div>
            </div>
            <div class="banner-box">
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-3.jpg" alt="banner 3"></a>
                </div>
            </div>
            <div class="banner-box">
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-4.jpg" alt="banner 3"></a>
                </div>
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-5.jpg" alt="banner 3"></a>
                </div>
            </div>
            <div class="banner-box">
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-6.jpg" alt="banner 3"></a>
                </div>
            </div>
            <div class="banner-box">
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-7.jpg" alt="banner 3"></a>
                </div>
                <div class="col-img">
                    <a href="#"><img src="img\banner\banner3-8.jpg" alt="banner 3"></a>
                </div>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Big Banner End Here -->
    <!-- Arrivals Products Area Start Here -->
    <div class="arrivals-product pb-85 pb-sm-45">
        <div class="container">
            <div class="main-product-tab-area">
                <div class="tab-menu mb-25">
                    <div class="section-ttitle">
                        <h2>New Arrivals</h2>
                    </div>
                    <!-- Nav tabs -->
                    <ul class="nav tabs-area" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#fshion">Gạch giả gỗ</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kids">Gạch lát nền</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#electronics">Gạch ốp tường</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#beauty">Gạch trang trí</a>
                        </li>
                    </ul>
                </div>

                <!-- Tab Contetn Start -->
                <div class="tab-content">
                    <%--                    <c:set var="pCate1" value="${listCategoryProduct1}" />--%>
                    <div id="fshion" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="electronics-pro-active owl-carousel">
                            <c:forEach items="${listCategoryProduct1}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 288px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #fshion End Here -->
                    <div id="kids" class="tab-pane fade show active">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="electronics-pro-active owl-carousel">
                            <c:forEach items="${listCategoryProduct2}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 288px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #fshion End Here -->
                    <div id="beauty" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="electronics-pro-active owl-carousel">
                            <c:forEach items="${listCategoryProduct4}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 288px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>

                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #beauty End Here -->
                    <div id="electronics" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="electronics-pro-active owl-carousel">
                            <c:forEach items="${listCategoryProduct3}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 288px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #electronics End Here -->
                </div>
                <!-- Tab Content End -->
            </div>
            <!-- main-product-tab-area-->
        </div>
        <!-- Container End -->
    </div>
    <!-- Arrivals Products Area End Here -->
    <!-- Arrivals Products Area Start Here -->
    <div class="second-arrivals-product pb-45 pb-sm-5">
        <div class="container">
            <div class="main-product-tab-area">
                <div class="tab-menu mb-25">
                    <div class="section-ttitle">
                        <h2>Best Seller</h2>
                    </div>
                    <!-- Nav tabs -->
                    <ul class="nav tabs-area" role="tablist">
                        <li class="nav-item">
                            <a class="nav-link active" data-toggle="tab" href="#fshion2">Gạch giả gỗ </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#beauty2">Gạch lát nền</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#electronics2">Gạch ốp tường</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" data-toggle="tab" href="#kids2">Gạch trang trí</a>
                        </li>

                    </ul>

                </div>

                <!-- Tab Contetn Start -->
                <div class="tab-content">
                    <div id="fshion2" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="best-seller-pro-active owl-carousel">
                            <c:forEach items="${listSellerProduct1}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                            <!-- Single Product End -->
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #fshion End Here -->
                    <div id="kids2" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="best-seller-pro-active owl-carousel">
                            <c:forEach items="${listSellerProduct4}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                            <!-- Single Product End -->
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #fshion End Here -->
                    <div id="beauty2" class="tab-pane fade">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="best-seller-pro-active owl-carousel">
                            <c:forEach items="${listSellerProduct2}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                            <!-- Single Product End -->
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #beauty End Here -->
                    <div id="electronics2" class="tab-pane fade show active">
                        <!-- Arrivals Product Activation Start Here -->
                        <div class="best-seller-pro-active owl-carousel">
                            <c:forEach items="${listSellerProduct3}" var="p">
                                <!-- Single Product Start -->
                                <div class="single-product">
                                    <!-- Product Image Start -->
                                    <div class="pro-img">
                                        <a href="ProductDetail?productID=${p.productID}">
                                            <img class="primary-img" src="${p.image[0].image}" alt="single-product">
                                           <img class="secondary-img" src="${p.image[1].image}" style="height: 170px" alt="single-product">
                                        </a>
                                        <a href="#" class="quick_view" data-toggle="modal" data-target="#myModal"
                                           title="Quick View"><i class="lnr lnr-magnifier"></i></a>
                                    </div>
                                    <!-- Product Image End -->
                                    <!-- Product Content Start -->
                                    <div class="pro-content">
                                        <div class="pro-info">
                                            <h4><a href="ProductDetail?productID=${p.productID}">${p.productName}</a>
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
                                                <c:url value="/addCart?productID=${p.productID}"
                                                       var="addCart"/>
                                                <a href="${addCart}" title="Thêm vào giỏ"> + Thêm vào giỏ</a>
                                            </div>
                                            <c:if test="${sessionScope.user !=null}">
                                                <div class="actions-secondary">
                                                    <a href="javascript:void(0)" class="product ${p.productID}"
                                                       data-product-id="${p.productID}" onclick="addToWishlist(event)">
                                                        <i class="fa-regular fa-heart"></i> <span>Quan tâm</span>
                                                    </a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <!-- Product Content End -->
                                </div>
                            </c:forEach>
                            <!-- Single Product End -->
                        </div>
                        <!-- Arrivals Product Activation End Here -->
                    </div>
                    <!-- #electronics End Here -->
                </div>
                <!-- Tab Content End -->
            </div>
            <!-- main-product-tab-area-->
        </div>
        <!-- Container End -->
    </div>
    <!-- Arrivals Products Area End Here -->
    <!-- Brand Banner Area Start Here -->
    <div class="main-brand-banner pt-95 pb-100 pb-sm-60 pt-sm-55">
        <div class="container">
            <div class="section-ttitle mb-30">
                <h2>Hot Brands</h2>
            </div>
            <div class="row no-gutters">
                <div class="col-lg-3">
                    <div class="col-img">
                        <img src="img\banner\h1-band1.jpg" alt="">
                    </div>
                </div>
                <div class="col-lg-6">
                    <!-- Brand Banner Start -->
                    <div class="brand-banner owl-carousel">
                        <div class="single-brand">
                            <a href="#"><img class="img" src="img\brand\1.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                        </div>
                        <div class="single-brand">
                            <a href="#"><img class="img" src="img\brand\1.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                        </div>
                        <div class="single-brand">
                            <a href="#"><img src="img\brand\1.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>

                        </div>
                        <div class="single-brand">
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\4.jpg" alt="brand-image"></a>
                        </div>
                        <div class="single-brand">
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\4.jpg" alt="brand-image"></a>
                        </div>
                        <div class="single-brand">
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\4.jpg" alt="brand-image"></a>
                        </div>
                        <div class="single-brand">
                            <a href="#"><img src="img\brand\2.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\3.jpg" alt="brand-image"></a>
                            <a href="#"><img src="img\brand\4.jpg" alt="brand-image"></a>
                        </div>
                    </div>
                    <!-- Brand Banner End -->

                </div>
                <div class="col-lg-3">
                    <div class="col-img">
                        <img src="img\banner\h1-band2.jpg" alt="">
                    </div>
                </div>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <!-- Brand Banner Area End Here -->
    <div class="big-banner pb-100 pb-sm-60">
        <div class="container big-banner-box">
            <div class="col-img">
                <a href="#">
                    <img src="img\banner\5.jpg" alt="">
                </a>
            </div>
            <div class="col-img">
                <a href="#">
                    <img src="img\banner\h1-banner3.jpg" alt="">
                </a>
            </div>
        </div>
        <!-- Container End -->
    </div>
    <jsp:include page="support.jsp"/>
    <jsp:include page="footer.jsp"/>

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