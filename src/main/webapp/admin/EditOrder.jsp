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
    <link rel="shortcut icon" href="admin/assets/images/logo/logo-transparent-png-icon.png">
    <link href="admin/assets/vendors/select2/select2.css" rel="stylesheet"/>
    <!-- page css -->
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
        <div class="row">
            <div class="col-lg-4">
                <div class="card">
                    <div class="card-body">
                        <h2>Chỉnh sửa đơn hàng</h2>
                        <h5 class="h5 font-weight-light">Mã Đơn Hàng</h5>
                        <strong><h4>${order.orderID}</h4></strong>
                        <br/>
                        <form action="/EditOrder" method="post" id="form_edit">
                            <input type="hidden" name="idOrder" value="${order.orderID}">
                            <strong><h5>Trạng thái đơn hàng</h5></strong>
                            <select id="mySelect1" name="id_status" <c:if test="${order.status eq 2}">disabled</c:if> >
                                <option value="0" <c:if test="${order.status eq 0}">selected</c:if>>Chờ xác nhận</option>
                                <option value="1" <c:if test="${order.status eq 1}">selected </c:if>>Đã xác nhận</option>
                                <option value="2" <c:if test="${order.status eq 2}">selected </c:if>>Đã hủy</option>
                            </select>
                            <br/>
                            <br/>
                            <strong><h5>Trạng thái vận chuyển</h5></strong>
                            <select id="mySelect2" name="id_status_transport" <c:if test="${order.status eq 2}">disabled</c:if>>
                                <option value="0" <c:if test="${order.transport_status eq 0}">selected </c:if>>Chưa vận chuyển</option>
                                <option value="1" <c:if test="${order.transport_status eq 1}">selected </c:if>>Đang vận chuyển</option>
                                <option value="2" <c:if test="${order.transport_status eq 2}">selected </c:if>>Vận chuyển thành công</option>
                            </select>
                        </form>

                    </div>
                </div>
            </div>
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-body">
                        <div
                                class="d-flex justify-content-between align-items-center"
                        >
                            <h5>Chi tiết đơn hàng</h5>
                        </div>
                        <div class="m-t-30">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Sản phẩm</th>
                                        <th>Số lượng</th>
                                        <th>Giá</th>
                                        <th>Tổng</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${cartUser.getCart()}" var="cart" varStatus="status">
                                        <c:set value="${cart.product}" var="product"></c:set>
                                        <c:set value="${cartUser.getValue()[status.index]}" var="quantity"></c:set>
                                        <tr>
                                            <td>${product.productID}</td>
                                            <td>
                                                <div class="d-flex align-items-center">
                                                    <div class="d-flex align-items-center">
                                                        <div
                                                                class="avatar avatar-image"
                                                                style="
                                        height: 30px;
                                        min-width: 30px;
                                        max-width: 30px;
                                      "
                                                        >
                                                            <img class="product-thumbnail-image" alt="${product.productName}"
                                                                 src="${product.image[1].image}"
                                                            />
                                                        </div>
                                                        <h6 class="m-l-10 m-b-0">${product.productName}</h6>
                                                    </div>
                                                </div>
                                            </td>
                                            <td>${quantity}</td>
                                            <td><fmt:formatNumber type="currency" currencySymbol=""
                                                                  minFractionDigits="0"
                                                                  value="${product.priceAfterSale}"/>đ
                                            </td>
                                            <td>
                                                <fmt:formatNumber type="currency" currencySymbol=""
                                                                  minFractionDigits="0"
                                                                  value="${product.priceAfterSale*quantity}"/>đ
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Thông Tin Giao Hàng</h4>
                    </div>
                    <div class="card-body">
                        <form action="/EditOrder" method="post">
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label class="font-weight-semibold" for="fullAddress"
                                    >Địa Chỉ:</label
                                    >
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="fullAddress"
                                            disabled
                                            value="${shippingAdress.address}"
                                            name="address"
                                    />
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="font-weight-semibold" for="email"
                                    >Email:</label
                                    >
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="email"
                                            disabled
                                            name="email"
                                            value="${shippingAdress.email}"
                                    />
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="font-weight-semibold" for="phone"
                                    >Số điện thoại</label
                                    >
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="phone"
                                            name="phone"
                                            disabled
                                            value="${shippingAdress.phone}"

                                    />
                                </div>
                                <div class="form-group col-md-12">
                                    <label class="font-weight-semibold" for="fullAddress"
                                    >Chữ ký đơn hàng:</label
                                    >
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="e_signature"
                                            disabled
                                            value="${order.eSign}"
                                            name="e_signature"
                                    />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="m-v-10 text-md-right">
                    <button type="reset"
                            class="btn btn-default"
                    >
                        <span class="m-l-5">Hủy</span>
                    </button>
                    <button
                            class="btn btn-success"
                            type="submit"
                            onclick="submitForm()"
                    >
                        <span class="m-l-5">Lưu</span>
                    </button>
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
<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>--%>
<script !src="">
    function submitForm() {
        var form = document.getElementById("form_edit");
        form.submit();
    }
</script>
<script src="admin/assets/vendors/jquery/dist/jquery.js"></script>
<script src="admin/assets/vendors/select2/select2.min.js"></script>
<script>
    $("#mySelect1").select2();
    $("#mySelect2").select2();

</script>
<!-- Core Vendors JS -->
<script src="admin/assets/js/vendors.min.js"></script>

<!-- page js -->

<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

<%--Custom JS--%>
<script src="admin/assets/js/script.js"></script>
</body>

</html>
