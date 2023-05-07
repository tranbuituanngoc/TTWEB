<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Side Nav START -->
<div class="side-nav">
    <div class="side-nav-inner">
        <ul class="side-nav-menu scrollable">
            <li class="nav-item ">
<%--                hiện đang lỗi do không dẫn vào trang servlet mà gọi thẳng vào địa chỉ gốc nên sai link css image js--%>
<%--                của mấy cái khác !!!--%>
                <a class="dropdown-toggle" href="admin/index.jsp">
                        <span class="icon-holder">
                            <i class="anticon anticon-dashboard"></i>
                        </span>
                    <span class="title">Dashboard</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="Home">
                        <span class="icon-holder">
                            <i class="anticon anticon-home"></i>
                        </span>
                    <span class="title">Về trang chủ</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="ListUserAd">
                        <span class="icon-holder">
                            <i class="anticon anticon-solution"></i>
                        </span>
                    <span class="title"> Quản lý tài khoản</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="ListProductAd">
                        <span class="icon-holder">
                            <i class="anticon anticon-shopping"></i>
                        </span>
                    <span class="title"> Quản lý sản phẩm</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="ListOrder">
                        <span class="icon-holder">
                            <i class="anticon anticon-shopping-cart"></i>
                        </span>
                    <span class="title"> Quản lý đơn hàng</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="ListAbout">
                        <span class="icon-holder">
                            <i class="anticon anticon-team"></i>
                        </span>
                    <span class="title"> Quản lý thành viên</span>
                </a>
            </li>
            <li class="nav-item">
                <a class="dropdown-toggle" href="ListContact">
                        <span class="icon-holder">
                            <i class="anticon anticon-contacts"></i>
                        </span>
                    <span class="title"> Quản lý liên hệ</span>
                </a>
            </li>
        </ul>
    </div>
</div>
<!-- Side Nav END -->
