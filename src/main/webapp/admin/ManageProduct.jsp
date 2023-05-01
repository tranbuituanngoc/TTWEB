<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="service.ProductImageService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Quản lý sản phẩm</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/datatables/dataTables.bootstrap.min.css" rel="stylesheet">

    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">
</head>

<body>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String username = u.getUserName();
    int role = u.getRole();
    if (username.equalsIgnoreCase("") || role == 2) {
        response.sendRedirect(request.getContextPath() + "/Home");
        return;
    }
%>
<jsp:include page="headerAd.jsp"/>
<jsp:include page="menu.jsp"/>

<!-- Page Container START -->
<div class="page-container">
    <!-- Content Wrapper START -->
    <div class="main-content">
        <div class="page-header">
            <h2 class="header-title">Quản Lý Sản Phẩm</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý sản phẩm</span>
                </nav>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <a href="AddOrUpdateProduct?action=getadd">
                    <button class="btn btn-primary btn-tone m-r-5"><i class="fas fa-plus m-r-5"></i>
                        <span>Thêm sản phẩm</span></button>
                </a>
                <div class="dropdown float-md-right">
                    <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <span>Công cụ</span>
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="#">In</a>
                        <a class="dropdown-item" id="exportPDF">Lưu file PDF</a>
                        <a class="dropdown-item" onclick="exportTableToExcel('example2','products')" href="#">Xuất ra
                            Excel</a>
                    </div>
                </div>
                <div class="m-t-25">
                    <table id="data-table" class="table">
                        <thead>
                        <tr>
                            <th class="align-middle">Tên Gạch</th>
                            <th>Giá tiền</th>
                            <th>Giảm giá %</th>
                            <th width="10%">Số lượng</th>
                            <th width="12%">Hình ảnh</th>
                            <th width="10%">Trạng thái</th>
                            <th width="12%"></th>
                            <th width="12%"></th>
                            <th width="8%"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listP}" var="p">
                            <tr id="${p.productID}">
                                <td class="align-middle">${p.productName}</td>
                                <td><fmt:formatNumber type="currency" currencySymbol="" minFractionDigits="0"
                                                      value="${p.price}"/> VNĐ
                                </td>
                                <td><fmt:formatNumber type="currency" currencySymbol="" minFractionDigits="0"
                                                      value="${p.salePrice}"/> VNĐ
                                </td>
                                <td>${p.quantity}</td>
                                <td><img src="${ProductImageService.getThumbProduct(p.productID)}" width="100%"> </td>

                                <td id="status">${p.status==1?"Đang bán":"Ngừng bán"}</td>
                                <td id="hide-nothide">
                                    <c:if test="${p.status ==1}">
                                        <a class="text-primary" href="AddOrUpdateProduct?action=hide&id=${p.productID}">
                                            <i class="fas fa-eye-slash fa-lg"></i>
                                            <span>Ẩn</span>
                                        </a>
                                    </c:if>
                                    <c:if test="${p.status ==0}">
                                        <a class="text-primary" href="AddOrUpdateProduct?action=show&id=${p.productID}">
                                            <span class="fas fa-eye fa-lg"></span> Hiển thị
                                        </a>
                                    </c:if>
                                </td>
                                <td><a class="text-lock text-primary"
                                       href="AddOrUpdateProduct?action=getupdate&id=${p.productID}"><span
                                        class="fas fa-edit"></span> Chỉnh sửa</a></td>
                                <td><a class="text-danger"
                                       href="AddOrUpdateProduct?action=delete&id=${p.productID}"><span
                                        class="far fa-window-close"></span> Xóa</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- Content Wrapper END -->
    <!-- Footer START -->
    <jsp:include page="footerAd.jsp"/>
    <!-- Footer END -->
</div>
<!-- Page Container END -->

<!-- Core Vendors JS -->
<script src="admin/assets/js/vendors.min.js"></script>

<!-- page js -->
<script src="admin/assets/vendors/datatables/jquery.dataTables.min.js"></script>
<script src="admin/assets/vendors/datatables/dataTables.bootstrap.min.js"></script>
<script src="admin/assets/js/pages/datatables.js"></script>

<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

<%--Custom JS--%>
<script src="admin/assets/js/script.js"></script>
</body>

</html>
