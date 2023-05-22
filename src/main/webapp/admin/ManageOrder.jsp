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
                            <th>Mã đơn hàng</th>
                            <th>Tên khách hàng</th>
                            <th>Tổng đơn hàng</th>
                            <th>Ngày Đặt</th>
                            <th>Ngày Giao</th>
                            <th>Tình Trạng Đơn Hàng</th>
<%--                            <th>Tình Trạng Vận Chuyển</th>--%>
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

                                    <fmt:parseDate value="${o.order_date}" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss.S" />
                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate" />
                                    ${formattedDate}
                                </td>
                                <td>
                                    <fmt:parseDate value="${o.shipping_time}" var="parsedDate" pattern="yyyy-MM-dd HH:mm:ss.S" />
                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate" />
                                        ${formattedDate}
                                </td>
                                <td id="orderstatus">
                                    <c:if test="${o.status ==1}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-success badge-dot m-r-10"></div>
                                            <div>Đã xử lý</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${o.status ==0}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-primary badge-dot m-r-10"></div>
                                            <div>Chưa xử lý</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${o.status ==2}">
                                        <div class="d-flex align-items-center">
                                            <div class="badge badge-danger badge-dot m-r-10"></div>
                                            <div>Đã hủy</div>
                                        </div>
                                    </c:if>
                                </td>
<%--                                <td id="transportstatus">--%>
<%--                                    <c:if test="${o.transport_status ==1}">Vận chuyển thành công</c:if>--%>
<%--                                    <c:if test="${o.transport_status ==0}">Đang vận chuyển</c:if>--%>
<%--                                    <c:if test="${o.transport_status ==2}">Đã hủy</c:if>--%>
<%--                                </td>--%>
                                <td class="text-right">
                                    <button class="btn btn-icon btn-hover btn-sm btn-rounded pull-right">
                                        <i class="anticon anticon-edit"></i>
                                    </button>
                                    <button class="btn btn-icon btn-hover btn-sm btn-rounded">
                                        <i class="anticon anticon-delete"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div id="id01" class="modal">
                        <!-- Modal Content -->
                        <form class="modal-content animate">
                                <span onclick="document.getElementById('id01').style.display='none'"
                                      class="close" title="Close Modal">&times;</span>
                            <div class="header-modal"><h3>Bạn có chắc là muốn xóa đơn hàng này</h3></div>
                            <%--                                    <input id="delete" name="action" style="display: none" value="delete">--%>
                            <input id="deleteval" name="id" style="display: none">
                            <div class="button-group">
                                <button id="btnDelete" class="btn-yes" type="button"
                                        onclick="document.getElementById('id01').style.display='none'">Có
                                </button>
                                <button class="btn-no" type="button"
                                        onclick="document.getElementById('id01').style.display='none'">Không
                                </button>
                            </div>
                        </form>
                    </div>
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
