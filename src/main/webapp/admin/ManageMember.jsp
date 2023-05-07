<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String username = u.getUserName();
    int role = u.getRole();
    if (username.equalsIgnoreCase("") || role == 2){
        response.sendRedirect(request.getContextPath() +"/Home");
        return;
    }
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Quản lý thành viên</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/datatables/dataTables.bootstrap.min.css" rel="stylesheet">

    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="headerAd.jsp"/>
<jsp:include page="menu.jsp"/>

<!-- Page Container START -->
<div class="page-container">
    <!-- Content Wrapper START -->
    <div class="main-content">
        <div class="page-header">
            <h2 class="header-title">Quản lý Thành Viên</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý thành viên</span>
                </nav>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <a href="UpdateAbout?action=getadd">
                    <button class="btn btn-primary btn-tone m-r-5"><i class="fas fa-plus m-r-5"></i>
                        <span>Thêm thành viên</span></button>
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
                            <th class="align-middle">Họ tên</th>
                            <th>Ngày sinh</th>
                            <th>Vị trí</th>
                            <th>Số điện thoại</th>
                            <th width="12%">Email</th>
                            <th width="12%">Hình ảnh</th>
                            <th width="12%"></th>
                            <th width="8%"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listA}" var="a">
                            <tr id="${a.id}">
                                <td class="align-middle">${a.fullname}</td>
                                <td>${a.birth}</td>
                                <td>${a.position}</td>
                                <td>${a.phone}</td>
                                <td>${a.email}</td>
                                <td><img src="${a.image}"></td>
                                <td><a class="text-lock text-primary"
                                       href="UpdateAbout?action=getupdate&id=${a.id}"><span
                                        class="fas fa-edit"></span> Chỉnh sửa</a></td>
                                <td><a class="text-danger" href="UpdateAbout?action=delete&id=${a.id}"><span
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
