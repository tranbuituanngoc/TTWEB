<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="service.RoleService" %>
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
    if (username.equalsIgnoreCase("") || role == 2) {
        response.sendRedirect(request.getContextPath() + "/Home");
        return;
    }
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>Quản lý tài khoản</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/logo-transparent-png-icon.png">

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
<%
    String result = (String) request.getSession().getAttribute("res");
    String msg = (String) request.getSession().getAttribute("msg");
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
<% request.getSession().removeAttribute("res");
    request.getSession().removeAttribute("msg");
%>
<!-- Page Container START -->
<div class="page-container">
    <!-- Content Wrapper START -->
    <div class="main-content">
        <div class="page-header">
            <h2 class="header-title">Quản Lý Tài Khoản</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý tài khoản</span>
                </nav>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <a href="AddOfUpdateUser?action=getadd">
                    <button class="btn btn-primary btn-tone m-r-5"><i class="fas fa-plus m-r-5"></i>
                        <span>Thêm tài khoản</span></button>
                </a>
                <div class="dropdown float-md-right">
                    <%--                    <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
                    <%--                        <span>Công cụ</span>--%>
                    <%--                    </button>--%>
                    <%--                    <div class="dropdown-menu">--%>
                    <%--                        <a class="dropdown-item" href="#">In</a>--%>
                    <%--                        <a class="dropdown-item" id="exportPDF">Lưu file PDF</a>--%>
                    <%--                        <a class="dropdown-item" onclick="exportTableToExcel('example2','products')" href="#">Xuất ra--%>
                    <%--                            Excel</a>--%>
                    <%--                    </div>--%>
                </div>
                <div class="m-t-25">
                    <table id="data-table" class="table">
                        <thead>
                        <tr>
                            <th>Tên tài khoản</th>
                            <th>Quyền truy cập</th>
                            <th>Trạng thái</th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <jsp:useBean id="listUser" scope="request" type="java.util.List"/>
                        <c:forEach items="${listUser}" var="user">
                            <tr id="${user.id_User}">
                                <td>${user.userName}</td>
                                <td class="text-capitalize">
                                        ${RoleService.getRoleName(user.id_User)}
                                </td>
                                <td id="status">${user.status==true?"đang hoạt động":"đã ngừng hoạt động"}</td>
                                <td id="lock-unlock">
                                    <c:if test="${user.role!=0}">
                                        <c:if test="${user.status==false}"><a class="text-unlock text-primary"
                                                                              href="AddOfUpdateUser?action=unlock&id=${user.id_User}">
                                            <span class="fas fa-lock-open"></span> Mở khóa</a></c:if>
                                        <c:if test="${user.status==true}"><a class="text-lock text-primary"
                                                                             href="AddOfUpdateUser?action=lock&id=${user.id_User}">
                                            <span class="fas fa-lock"></span> Khóa</a></c:if>
                                    </c:if>
                                </td>
                                <td><a class="text-lock text-primary"
                                       href="AddOfUpdateUser?action=getupdate&id=${user.id_User}"><span
                                        class="fas fa-edit"></span> Chỉnh sửa</a></td>
                                <td><c:if test="${user.role!=0}">
                                    <a class="text-danger"
                                       href="AddOfUpdateUser?action=delete&id=${user.id_User}"><span
                                            class="far fa-window-close"></span> Xóa</a>
                                </c:if>
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
<div class="notification-toast top-right" id="notification-toast"></div>
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