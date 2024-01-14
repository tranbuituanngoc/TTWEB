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
    <%--Sweet alert notify--%>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <%--Sweet alert notify--%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
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
        <form action="/EditOrder" method="post" id="form_edit">
            <div class="row">
                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-body">
                            <h2>Chỉnh sửa đơn hàng</h2>
                            <h5 class="h5 font-weight-light">Mã Đơn Hàng</h5>
                            <strong><h4>${order.orderID}</h4></strong>
                            <br/>
                            <input type="hidden" name="idOrder" value="${order.orderID}">
                            <strong><h5>Trạng thái đơn hàng</h5></strong>
                            <select id="mySelect1" name="id_status"
                                    <c:if test="${order.status eq 2 || order.status eq 3}">disabled</c:if> >
                                <option value="0" <c:if test="${order.status eq 0}">selected</c:if>>Chờ xác nhận
                                </option>
                                <option value="1" <c:if test="${order.status eq 1}">selected </c:if>>Đã xác nhận
                                </option>
                                <option value="2" <c:if test="${order.status eq 2}">selected </c:if>>Đã hủy</option>
                                <option value="3" <c:if test="${order.status eq 3}">selected </c:if>>Hoàn tiền</option>
                            </select>
                            <br/>
                            <br/>
                            <strong><h5>Trạng thái vận chuyển</h5></strong>
                            <select id="mySelect2" name="id_status_transport"
                                    <c:if test="${order.status eq 2 || order.status eq 3}">disabled</c:if>>
                                <option value="0" <c:if test="${order.transport_status eq 0}">selected </c:if>>Chưa vận
                                    chuyển
                                </option>
                                <option value="1" <c:if test="${order.transport_status eq 1}">selected </c:if>>Đang vận
                                    chuyển
                                </option>
                                <option value="2" <c:if test="${order.transport_status eq 2}">selected </c:if>>Vận
                                    chuyển thành công
                                </option>
                            </select>
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
                                                <td class="productID"
                                                    id="${product.productID}">${product.productID}</td>
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
                                                                <img class="product-thumbnail-image"
                                                                     alt="${product.productName}"
                                                                     src="${product.image[1].image}"
                                                                />
                                                            </div>
                                                            <h6 class="m-l-10 m-b-0">${product.productName}</h6>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <input min="1" class="quantity" id="quantity_${product.productID}"
                                                           name="quantity_${product.productID}"
                                                           type="number"
                                                           value="${quantity}" style="width:70px">
                                                </td>
                                                <td class="product-price" data-price="${product.priceAfterSale}">
                                                    <fmt:formatNumber type="currency" currencySymbol=""
                                                                      minFractionDigits="1"
                                                                      value="${product.priceAfterSale}"/>đ
                                                </td>
                                                <td id="totalPrice_${product.productID}" class="total-price">
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
                            <div class="form-row">
                                <div class="form-group col-md-12">
                                    <label class="font-weight-semibold" for="fullAddress"
                                    >Địa Chỉ:</label
                                    >
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="fullAddress"
                                            value="${shippingAdress.address}"
                                            name="address"
                                    />
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="font-weight-semibold" for="email">Email:</label>
                                    <input
                                            type="text"
                                            class="form-control"
                                            id="email"
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
                                onclick="confirmSave(event)"
                        >
                            <span class="m-l-5">Lưu</span>
                        </button>
                    </div>
                </div>
            </div>
        </form>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function confirmSave(event) {
        event.preventDefault(); // Ngăn chặn sự kiện submit mặc định của form

        var style = document.createElement('style');
        style.type = 'text/css';

        style.innerHTML = `
#swal2-title {
    font-size: 24px !important; /* Thay đổi cỡ chữ */
    font-family: Arial, sans-serif !important; /* Thay đổi font chữ */
}

#swal2-html-container {
    font-size: 16px !important; /* Thay đổi cỡ chữ */
    font-family: Arial, sans-serif !important; /* Thay đổi font chữ */
}
`;
        document.getElementsByTagName('head')[0].appendChild(style);

// Mã SweetAlert2 của bạn
        Swal.fire({
            title: "Bạn có chắc là muốn chỉnh sửa hóa đơn này không?",
            html: `
        Việc thay đổi hóa đơn có thể làm cho <b>chữ ký điện tử của đơn hàng thay đổi</b> và người dùng có thể yêu cầu <b> hoàn tiền </b> nếu việc đó xảy ra <br>Bạn vẫn muốn tiếp tục chứ?
    `,
            icon: "warning",
            showDenyButton: true,
            denyButtonText: `Hủy`,
            confirmButtonText: "Tiếp tục",
            confirmButtonColor: '#00e3bc',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                submitForm();
            } else if (result.isDenied) {
                window.location.href = "/ListOrder";
            }
        });

    }

</script>

<script>
    $(document).ready(function () {
        $(".quantity").change(function () {
            var productID = $(this).closest('tr').find('.productID').text(); // Lấy productID từ nội dung của td
            var quantity = $(this).val();
            var price = $(this).closest('tr').find('.product-price').data('price');
            var totalPrice = quantity * price;

            // Định dạng giá tiền
            var formatter = new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND',
            });

            var formattedTotalPrice = formatter.format(totalPrice);
            formattedTotalPrice = formattedTotalPrice.replaceAll('.', ',');

            $("#totalPrice_" + productID).text(formattedTotalPrice); // Sử dụng productID để xác định ô tổng giá
        });
    });
</script>

</body>

</html>
