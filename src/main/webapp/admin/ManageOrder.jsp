<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>
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
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Quản lý đơn hàng</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/datatables/dataTables.bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css">
    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="headerAd.jsp"/>
<jsp:include page="menu.jsp"/>

<style>
    tbody thead th{
        white-space: nowrap;
    }
</style>

<%
    String result = (String) request.getSession().getAttribute("resultOrder");
    String msg = (String) request.getSession().getAttribute("msgOrder");
%>



<% if (result != null && msg != null) {
    if (result.equals("false")) {
%>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: '<%= msg %>',
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });
</script>
<% } %>
<% if (result.equals("true")) { %>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Success',
        text: '<%= msg %>',
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });
</script>
<%
        }
    }
%>
<% request.getSession().removeAttribute("resultOrder");
    request.getSession().removeAttribute("msgOrder");
%>
<!-- Page Container START -->
<div class="page-container">
    <!-- Content Wrapper START -->
    <div class="main-content">
        <div class="page-header">
            <h2 class="header-title">Quản Lý Đơn Hàng</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý đơn hàng</span>
                </nav>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <div class="m-t-25">
                    <table id="data-table" class="table">
                        <thead>
                        <tr>
                            <th>Mã Đơn Hàng</th>
                            <th>Tên Khách Hàng</th>
                            <th>Tổng Đơn Hàng</th>
                            <th>Ngày Đặt</th>
                            <th>Ngày Giao</th>
                            <th>Tình Trạng Đơn Hàng</th>
                            <th>Tình Trạng Vận Chuyển</th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listO}" var="o">
                            <tr id="${o.orderID}">
                                <td>${o.orderID}</td>
                                <td>${o.fullName}</td>
                                <td>
                                    <fmt:formatNumber type="currency" currencySymbol="" minFractionDigits="0"
                                                      value="${o.totalPrice}"/> VNĐ
                                </td>
                                <td>

                                    <fmt:parseDate value="${o.order_date}" var="parsedDate"
                                                   pattern="yyyy-MM-dd HH:mm:ss.S"/>
                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate"/>
                                        ${formattedDate}
                                </td>
                                <td>
                                    <fmt:parseDate value="${o.shipping_time}" var="parsedDate"
                                                   pattern="yyyy-MM-dd HH:mm:ss.S"/>
                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate"/>
                                        ${formattedDate}
                                </td>
                                <td id="orderstatus">
                                    <c:if test="${o.status ==1}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-success badge-dot m-r-10"></div>
                                            <div>Đã xác nhận</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${o.status ==0}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-primary badge-dot m-r-10"></div>
                                            <div>Chưa xác nhận</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${o.status ==2}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-danger badge-dot m-r-10"></div>
                                            <div>Đã hủy</div>
                                        </div>
                                    </c:if>
                                </td>
                                <td id="transportstatus">
                                    <c:if test="${o.transport_status ==0}">Chưa Vận chuyển</c:if>
                                    <c:if test="${o.transport_status ==1}">Đang vận chuyển</c:if>
                                    <c:if test="${o.transport_status ==2}">Vận chuyển thành công</c:if>
                                </td>
                                <td class="text-right">
                                    <a href="EditOrder?orderID=${o.orderID}">
                                        <button class="btn btn-icon btn-hover btn-sm btn-rounded pull-right">
                                            <i class="anticon anticon-edit"></i>
                                        </button>
                                    </a>
                                </td>
                                <td>
                                    <a href="DeleteOrder?orderID=${o.orderID}">
                                        <button class="btn btn-icon btn-hover btn-sm btn-rounded">
                                        <i class="anticon anticon-delete"></i>
                                        </button>
                                    </a>
                                </td>
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
