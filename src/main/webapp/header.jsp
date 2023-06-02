<%@ page import="javax.mail.Session" %>
<%@ page import="model.User" %>
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
                    <div class="logo mb-all-30">
                        <a href="Home"><img src="img\logo\logo.png" alt="logo-image"></a>
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
                <!-- Categorie Search Box End Here -->
                <!-- Cart Box Start Here -->
                <div class="col-lg-4 col-md-12">
                    <div class="cart-box mt-all-30">
                        <ul class="d-flex justify-content-lg-end justify-content-center align-items-center">
                            <li>
                                <a href="Cart"><i class="lnr lnr-cart"></i>
                                    <span class="my-cart">
                                            <span class="total-pro">${cartUser.getTotalQuantity()}</span>
                                            <span>Giỏ hàng</span>
                                        </span>
                                </a>
                            </li>
                            <li>
                                <a href="/UserOrder"><i class="lnr lnr-shirt"></i>
                                    <span>Đơn hàng</span>
                                </a>
                            </li>
                            <%
                                User user = (User) session.getAttribute("user");
                                if (user == null) {
                            %>
                            <li><a href="login.jsp"><i class="lnr lnr-user"></i><span class="my-cart"><span> <strong>Đăng nhập</strong></span><span> đăng kí</span></span></a>
                            </li>
                            <%
                            } else if (user.getRole() == 2) {
                            %>
                            <i class="lnr lnr-user" style="font-size: 25pt; margin-left: 10px;"></i><span
                                class="dropdown"><a class="dropdown-toggle" style="cursor: pointer;"
                                                    data-toggle="dropdown"><%=user.getUserName()%> <span
                                class="caret"></span></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="changePass.jsp">Đổi mật khẩu</a></li>
                                        <li><a href="nguoi-dung?action=dang-xuat">Đăng xuất</a></li>
                                    </ul>
                            <%
                                } else if (user.getRole() == 1|| user.getRole()==0) {

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
