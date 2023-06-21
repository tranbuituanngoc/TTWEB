<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Side Nav START -->
<div class="side-nav">
    <div class="side-nav-inner">
        <ul class="side-nav-menu scrollable">
            <li class="nav-item ">
                <a class="dropdown-toggle" href="thong-ke">
                        <span class="icon-holder">
                            <i class="anticon anticon-dashboard"></i>
                        </span>
                    <span class="title">Thống Kê</span>
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
                <a class="dropdown-toggle" href="ListLog">
                        <span class="icon-holder">
                            <i class="anticon anticon-file-text"></i>
                        </span>
                    <span class="title"> Quản lý logs</span>
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="dropdown-toggle" href="javascript:void(0);">
                        <span class="icon-holder">
                            <i class="anticon anticon-team"></i>
                        </span>
                    <span class="title">Phân quyền</span>
                    <span class="arrow">
                                    <i class="arrow-icon"></i>
                                </span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/ManageRole">Role</a>
                    </li>
                    <li>
                        <a href="/ManageRoleUser">Phân quyền người dùng</a>
                    </li>
                </ul>
            </li>
            <%--            <li class="nav-item">--%>
            <%--                <a class="dropdown-toggle" href="ListContact">--%>
            <%--                        <span class="icon-holder">--%>
            <%--                            <i class="anticon anticon-contacts"></i>--%>
            <%--                        </span>--%>
            <%--                    <span class="title"> Quản lý liên hệ</span>--%>
            <%--                </a>--%>
            <%--            </li>--%>
        </ul>
    </div>
</div>
<!-- Side Nav END -->
