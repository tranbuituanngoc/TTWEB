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

<%
    String updateRoleResult = (String) request.getAttribute("updateRoleResult");
%>
<% if (updateRoleResult != null && updateRoleResult.equals("1")) { %>
<script>
    Swal.fire({
        icon: 'success',
        title: 'Success',
        text: 'Cập nhật thông tin Role thành công',
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });
</script>
<%
    }
%>

<% if (updateRoleResult != null && updateRoleResult.equals("0")) { %>
<script>
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Cập nhật thông tin Role thất bại',
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000
    });
</script>
<%
    }
%>

<% request.removeAttribute("updateRoleResult");%>
<!-- Page Container START -->
<div class="page-container">
    <!-- Content Wrapper START -->
    <div class="main-content">
        <div class="page-header">
            <h2 class="header-title">Quản lý Role</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý Role</span>
                </nav>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-11 mx-auto">
                <!-- Card View -->
                <div class="row d-none" id="card-view">
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-1.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Erin Gonzales</h4>
                                    <p>erin.gon@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-2.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Darryl Day</h4>
                                    <p>darryl.d@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-3.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Marshall Nichols</h4>
                                    <p>marshalln@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-4.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Virgil Gonzales</h4>
                                    <p>virgil14@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-5.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Nicole Wyne</h4>
                                    <p>nicolew@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-6.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Riley Newman</h4>
                                    <p>rileyn93@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="card">
                            <div class="card-body">
                                <div class="m-t-20 text-center">
                                    <div
                                            class="avatar avatar-image"
                                            style="height: 100px; width: 100px"
                                    >
                                        <img
                                                src="assets/images/avatars/thumb-7.jpg"
                                                alt=""
                                        />
                                    </div>
                                    <h4 class="m-t-30">Pamela Wanda</h4>
                                    <p>pamelaw@gmail.com</p>
                                </div>
                                <div class="text-center m-t-15">
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-facebook"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-twitter"></i>
                                    </button>
                                    <button
                                            class="m-r-5 btn btn-icon btn-hover btn-rounded"
                                    >
                                        <i class="anticon anticon-instagram"></i>
                                    </button>
                                </div>
                                <div class="text-center m-t-30">
                                    <a
                                            href="profile.html"
                                            class="btn btn-primary btn-tone"
                                    >
                                        <i class="anticon anticon-mail"></i>
                                        <span class="m-l-5">Contact</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="list-view">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>Tên</th>
                                            <th>Quản Lý Sản Phẩm</th>
                                            <th>Quản Lý Hóa Đơn</th>
                                            <th>Quản Lý Role</th>
                                            <th>Quản Lý Địa Chỉ Giao Hàng</th>
                                            <th>Quản Lý Người Dùng</th>
                                            <th>Quản Lý Giỏ Hàng</th>
                                            <th></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- begin item -->
                                        <c:forEach var="r" items="${r}">
                                            <tr>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <h6 class="mb-0">${r.role_name}</h6>
                                                        </div>
                                                    </div>
                                                </td>

                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.product_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.product_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>

                                                </td>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.order_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.order_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.role_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.role_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.shipping_address_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.shipping_address_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.user_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.user_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <div class="media align-items-center">
                                                        <div class="media-body m-l-15">
                                                            <c:if test="${r.cart_permission == 1}">
                                                                <div class="badge badge-pill badge-blue">
                                                                    <i class="anticon anticon-check"></i>
                                                                </div>
                                                            </c:if>
                                                            <c:if test="${r.cart_permission == 0}">
                                                                <div class="badge badge-pill badge-red">
                                                                    <i class="anticon anticon-close"></i>
                                                                </div>
                                                            </c:if>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td class="text-right">
                                                    <a href="EditRole?role_id=${r.role_id}"
                                                       class="btn btn-primary btn-tone">
                                                        <i class="anticon anticon-edit"></i>
                                                        <span class="m-l-5">Chỉnh sửa</span>
                                                    </a>
                                                    <a
                                                            href="RemoveRole?role_id=${r.role_id}"
                                                            class="btn btn-danger btn-outline-danger"
                                                    >
                                                        <i class="anticon anticon-delete"></i>
                                                        <span class="m-l-5">Xóa</span>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                        </tbody>
                                    </table>
                                    <div class="text-right">
                                        <a class="btn btn-primary" href="AddRole">
                                            <i class="anticon anticon-plus-circle m-r-5"></i>
                                            <span>Thêm Role</span>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
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
<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

<%--Custom JS--%>
<script src="admin/assets/js/script.js"></script>
</body>

</html>
