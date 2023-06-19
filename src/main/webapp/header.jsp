<%@ page import="javax.mail.Session" %>
<%@ page import="model.User" %>
<%@ page import="controller.UserController" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="service.WishlistService" %>
<%@ page import="model.Product" %>
<%@ page import="java.text.NumberFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="cart" class="model.Cart" scope="session"></jsp:useBean>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Main Header Area Start Here -->
<header>
    <!-- Header Middle Start Here -->
    <div class="header-middle ptb-15">
        <div class="container">
            <div class="row align-items-center no-gutters">
                <div class="col-lg-3 col-md-12">
                    <div class="logo mb-all-30 mr-50">
                        <a href="Home"><img src="img\logo\logo-transparent-png.png" style="width: 100%;"
                                            alt="logo-image"></a>
                    </div>
                </div>
                <!-- Categorie Search Box Start Here -->
                <div class="col-lg-5 col-md-8 ml-auto mr-auto col-10">
                    <div class="categorie-search-box">
                        <form action="search" method="post">
                            <input type="text" name="txt" placeholder="Bạn muốn mua gì...">
                            <button><i class="lnr lnr-magnifier"></i></button>
                        </form>
                    </div>
                </div>
                <!-- Cart Box Start Here -->
                <div class="col-lg-4 col-md-12">
                    <div class="cart-box mt-all-30">
                        <ul class="d-flex justify-content-lg-end justify-content-center align-items-center">
                            <li>
                                <a href="Cart">
                                    <div class="total-product">
                                        <i class="lnr lnr-cart"></i>
                                        <div class="total-pro">
                                            <c:if test="${cartUser.getTotalQuantity() == null}">
                                                0
                                            </c:if>
                                            ${cartUser.getTotalQuantity()}
                                        </div>
                                    </div>
                                </a>
                                <%--                                <ul class="ht-dropdown cart-box-width">--%>
                                <%--                                    <li>--%>
                                <%--                                        <!-- Cart Box Start -->--%>
                                <%--                                        <div class="single-cart-box">--%>
                                <%--                                            <div class="cart-img">--%>
                                <%--                                                <a href="#"><img src="img\products\1.jpg" alt="cart-image"></a>--%>
                                <%--                                                <span class="pro-quantity">1X</span>--%>
                                <%--                                            </div>--%>
                                <%--                                            <div class="cart-content">--%>
                                <%--                                                <h6><a href="product.html">Printed Summer Red </a></h6>--%>
                                <%--                                                <span class="cart-price">27.45</span>--%>
                                <%--                                            </div>--%>
                                <%--                                        </div>--%>
                                <%--                                        <!-- Cart Box End -->--%>
                                <%--                                        <!-- Cart Footer Inner Start -->--%>
                                <%--                                        <div class="cart-footer">--%>
                                <%--                                            <div class="cart-actions text-center">--%>
                                <%--                                                <a class="cart-checkout" href="Cart">Xem giỏ hàng</a>--%>
                                <%--                                            </div>--%>
                                <%--                                        </div>--%>
                                <%--                                        <!-- Cart Footer Inner End -->--%>
                                <%--                                    </li>--%>
                                <%--                                </ul>--%>
                            </li>
                            <%
                                User user = (User) session.getAttribute("user");
                                if (user != null) {
                            %>
                            <li id="wishlist">
                                <a href="danh-sach-quan-tam?action=get">
                                    <div class="total-product-wish">
                                        <div class="total-pro-wish" id="wishlistCount">
                                            <!-- Placeholder for wishlist count -->
                                        </div>
                                        <i class="lnr lnr-heart"></i>
                                    </div>
                                </a>
                                <ul class="ht-dropdown cart-box-width" id="wishlistItems">
                                    <!-- Placeholder for wishlist items -->
                                </ul>
                            </li>
                            <script>
                                // Gọi hàm kiểm tra sau khi trang đã tải xong
                                window.addEventListener('DOMContentLoaded', function () {
                                    headerWishlist();
                                });

                                function headerWishlist() {
                                    // Tạo một đối tượng XMLHttpRequest
                                    var xhr = new XMLHttpRequest();

                                    // Xác định phương thức HTTP và URL của servlet
                                    var method = "POST";
                                    var url = "/danh-sach-quan-tam?action=header";

                                    // Thiết lập hàm xử lý khi có phản hồi từ servlet
                                    xhr.onreadystatechange = function () {
                                        if (xhr.readyState === XMLHttpRequest.DONE) {
                                            if (xhr.status === 200) {
                                                // Xử lý phản hồi từ servlet ở đây
                                                var wishlistData = JSON.parse(xhr.responseText);

                                                // Cập nhật hiển thị số lượng sản phẩm trong wishlist
                                                var wishlistCount = document.querySelector('#wishlistCount');
                                                wishlistCount.textContent = wishlistData.count;

                                                // Cập nhật danh sách sản phẩm trong dropdown
                                                var wishlistItems = document.querySelector('#wishlistItems');
                                                wishlistItems.innerHTML = '';
                                                if (wishlistData.count > 0) {
                                                    for (var productId in wishlistData) {
                                                        if (productId !== 'count') {
                                                            var product = wishlistData[productId];

                                                            // Tạo phần tử HTML cho từng sản phẩm
                                                            var productElement = document.createElement('li');
                                                            productElement.className = 'single-cart-box';

                                                            // Tạo phần tử HTML cho hình ảnh sản phẩm
                                                            var imgElement = document.createElement('div');
                                                            imgElement.className = 'cart-img';
                                                            var imgLink = document.createElement('a');
                                                            imgLink.href = 'ProductDetail?productID=' + product.id;
                                                            var img = document.createElement('img');
                                                            img.src = product.thumb;
                                                            imgLink.appendChild(img);
                                                            imgElement.appendChild(imgLink);
                                                            productElement.appendChild(imgElement);

                                                            // Tạo phần tử HTML cho nội dung sản phẩm
                                                            var contentElement = document.createElement('div');
                                                            contentElement.className = 'cart-content';
                                                            var productName = document.createElement('h6');
                                                            var productNameLink = document.createElement('a');
                                                            productNameLink.href = 'ProductDetail?productID=' + product.id;
                                                            productNameLink.textContent = product.name;
                                                            productName.appendChild(productNameLink);
                                                            contentElement.appendChild(productName);
                                                            var productPrice = document.createElement('span');
                                                            productPrice.className = 'cart-price';
                                                            productPrice.textContent = product.price + ' VNĐ';
                                                            contentElement.appendChild(productPrice);
                                                            productElement.appendChild(contentElement);

                                                            wishlistItems.appendChild(productElement);
                                                        }
                                                    }


                                                    // Tạo phần tử HTML cho cart footer
                                                    var cartFooter = document.createElement('li');
                                                    cartFooter.className = 'cart-footer';
                                                    cartFooter.style = 'padding-top: 0;'
                                                    var cartActions = document.createElement('div');
                                                    cartActions.className = 'cart-actions text-center';
                                                    var cartCheckoutLink = document.createElement('a');
                                                    cartCheckoutLink.className = 'cart-checkout';
                                                    cartCheckoutLink.href = 'danh-sach-quan-tam?action=get';
                                                    cartCheckoutLink.textContent = 'Xem Danh Sách Quan Tâm';
                                                    cartActions.appendChild(cartCheckoutLink);
                                                    cartFooter.appendChild(cartActions);
                                                    wishlistItems.appendChild(cartFooter);
                                                }
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


                            <%
                                }
                                if (user == null) {
                            %>
                            <li>
                                <a href="login.jsp">
                                    <i class="lnr lnr-user"></i>
                                    <span class="my-cart">
                                        <span> <strong>Đăng nhập</strong></span>
                                        <span> đăng kí</span>
                                    </span>
                                </a>
                            </li>
                            <%
                            } else if (user.getRole() == 2) {
                            %>
                            <li class="login-box">
                                <a href="#">
                                    <i class="lnr lnr-user"></i>
                                    <span class="my-cart">
                                            <span class="text-center pt-2 font-weight-bold"><%=user.getUserName()%></span>
                                    </span>
                                </a>
                                <ul class="ht-dropdown log-box mt-2">
                                    <li>
                                        <!-- Cart Box Start -->
                                        <div class="log-text">
                                            <a href="/UserOrder">Đơn hàng</a>
                                        </div>
                                        <!-- Cart Box End -->
                                        <!-- Cart Box Start -->
                                        <div class="log-text">
                                            <a href="changePass.jsp">Đổi mật khẩu</a>
                                        </div>
                                        <div class="log-text">
                                            <a href="nguoi-dung?action=dang-xuat">Đăng xuất</a>
                                        </div>
                                    </li>
                                </ul>
                            </li>
                            <%
                            } else if (user.getRole() != 2) {
                            %>
                            <li><a href="ListProductAd"><i class="lnr lnr-pointer-right"></i><span
                                    class="my-cart"><span><strong><%=user.getUserName()%></strong></span><span>vào admin</span></span></a>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                </div>
                <!-- Cart Box End Here -->
            </div>
            <!-- Row End -->
        </div>
        <!-- Container End -->
    </div>
    <!-- Header Middle End Here -->
    <!-- Header Bottom Start Here -->
    <div class="header-bottom  header-sticky">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-xl-3 col-lg-4 col-md-6 vertical-menu d-none d-lg-block">
                    <span class="categorie-title">Danh mục sản phẩm</span>
                </div>
                <div class="col-xl-9 col-lg-8 col-md-12 ">
                    <nav class="d-none d-lg-block">
                        <ul class="header-bottom-list d-flex">
                            <li><a href="Home">Trang chủ</a></li>
                            <li><a href="ProductLists?page=1">Sản phẩm</a></li>
                            <li><a href="about">Giới thiệu</a></li>
                            <li><a href="contact.jsp">Liên hệ</a></li>
                        </ul>
                    </nav>
                    <div class="mobile-menu d-block d-lg-none">
                        <nav>
                            <ul>
                                <li><a href="Home">Trang chủ</a></li>
                                <li><a href="ProductLists?page=1">Sản phẩm</a></li>
                                <li><a href="about">Giới thiệu</a></li>
                                <li><a href="contact.jsp">Liên hệ</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            <!-- Row End -->
        </div>
        <!-- Container End -->
    </div>
    <!-- Header Bottom End Here -->
    <!-- Mobile Vertical Menu Start Here -->
    <div class="container d-block d-lg-none">
        <div class="vertical-menu mt-30">
            <span class="categorie-title mobile-categorei-menu">Shop by Categories </span>
            <nav>
                <div id="cate-mobile-toggle"
                     class="category-menu sidebar-menu sidbar-style mobile-categorei-menu-list menu-hidden ">
                    <ul>
                        <c:forEach items="${listCategory}" var="p1">
                            <li><a href="ListByType?type=${p1.id}&page=${1}"><span><img src="img\vertical-menu\icon.png"
                                                                                        alt="menu-icon"></span>${p1.description}
                            </a></li>
                        </c:forEach>
                    </ul>
                </div>
                <!-- category-menu-end -->
            </nav>
        </div>
    </div>
    <!-- Mobile Vertical Menu Start End -->
    <div class="container">
        <div class="row">
            <!-- Vertical Menu Start Here -->
            <div class="col-xl-3 col-lg-4 d-none d-lg-block">
                <div class="vertical-menu mb-all-30">
                    <nav>
                        <ul class="vertical-menu-list " style="display: none;">
                            <c:forEach items="${listCategory}" var="p1">
                                <li><a href="ListByType?type=${p1.id}&page=${1}"><span><img
                                        src="img\vertical-menu\icon.png" alt="menu-icon"></span>${p1.description}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
            <!-- Vertical Menu End Here -->
        </div>
        <!-- Row End -->
    </div>
    <!-- Container End -->
</header>
<!-- Main Header Area End Here -->
