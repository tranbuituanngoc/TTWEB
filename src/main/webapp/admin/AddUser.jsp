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
    if (username.equalsIgnoreCase("") || role == 2) {
        response.sendRedirect(request.getContextPath() + "/Home");
        return;
    }
%>
<c:set var="username" value="<%=username%>"/>
<!DOCTYPE html>
<html>

<head>
    <title><c:choose>
        <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
        <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
        <c:when test="${param.action eq 'add'}">Thêm </c:when>
        <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
    </c:choose>
        tài khoản</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/select2/select2.css" rel="stylesheet">
    <link href="admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">


    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">

</head>

<body>

<div class="app">
    <div class="layout">
        <!-- Header START -->
        <c:set var="username" value="<%=username%>"/>
        <jsp:include page="headerAd.jsp"></jsp:include>
        <!-- Header END -->

        <!-- Side Nav START -->
        <jsp:include page="menu.jsp"></jsp:include>
        <!-- Side Nav END -->

        <!-- Page Container START -->
        <div class="page-container">
            <!-- Content Wrapper START -->
            <div class="main-content">
                <div class="page-header">
                    <h2 class="header-title"><c:choose>
                        <c:when test="${param.action eq'getadd'}">Thêm </c:when>
                        <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when>
                        <c:when test="${param.action eq 'add'}">Thêm </c:when>
                        <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
                    </c:choose>Tài Khoản</h2>
                    <div class="header-sub-title">
                        <nav class="breadcrumb breadcrumb-dash">
                            <%--                            sửa lại thành địa chỉ dashboard--%>
                            <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                            <span class="breadcrumb-item active"><c:choose>
                                <c:when test="${param.action eq 'getadd'}">Thêm </c:when>
                                <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when>
                                <c:when test="${param.action eq 'add'}">Thêm </c:when>
                                <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
                            </c:choose>tài khoản</span>
                        </nav>
                    </div>
                </div>
                <div class="card">
                    <div class="card-body">
                        <h4><c:choose>
                            <c:when test="${param.action eq'getadd'}">Thêm </c:when>
                            <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when>
                            <c:when test="${param.action eq 'add'}">Thêm </c:when>
                            <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
                        </c:choose>Tài Khoản</h4>
                        <div class="m-t-25">
                            <h5 class="m-t-10 m-b-10"
                                style="color: red"><%=request.getAttribute("err") == null ? "" : request.getAttribute("err")%>
                            </h5>
                            <form id="form-validation" class="form-horizontal" action="AddOfUpdateUser" method="post">
                                <input style="display: none" name="action"
                                       value="<c:choose><c:when test="${param.action eq 'getadd'}">add</c:when><c:when test="${param.action eq 'getupdate'}">update</c:when><c:when test="${param.action eq 'add'}">add</c:when><c:when test="${param.action eq 'update'}">update</c:when></c:choose>">
                                <c:choose>
                                    <c:when test="${param.action eq 'getupdate'}"><input style="display: none"
                                                                                         name="id"
                                                                                         value="<%=request.getParameter("id")%>"></c:when>
                                    <c:when test="${param.action eq 'update'}"><input style="display: none"
                                                                                      name="id"
                                                                                      value="<%=request.getParameter("id")%>"></c:when>
                                </c:choose>
                                <div class="m-t-25">
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="fullname">Họ và Tên
                                            *</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" name="fullname" id="fullname"
                                                   placeholder="Bắt buộc *"
                                                   value="<%=request.getParameter("fullname")==null? "":request.getParameter("fullname")%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="phone">Số điện
                                            thoại *</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" name="phone" id="phone"
                                                   placeholder="Bắt buộc *"
                                                   value="<%=(request.getParameter("phone")==null||request.getParameter("phone").equals("null")) ? "":request.getParameter("phone")%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="username">Tên đăng
                                            nhập *</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" name="username" id="username"
                                                   placeholder="Bắt buộc *"
                                                   value="<%=request.getParameter("username")==null? "":request.getParameter("username")%>"
                                            <c:choose>
                                                   <c:when test="${param.action eq 'getupdate'}">readonly</c:when>
                                                   <c:when test="${param.action eq 'update'}">readonly</c:when>
                                            </c:choose>>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="email">Email</label>
                                        <div class="col-md-5">
                                            <input type="text" class="form-control" name="email" id="email"
                                                   placeholder="Bắt buộc *"
                                                   value="<%=(request.getParameter("email")==null||request.getParameter("email").equals("null")) ? "":request.getParameter("email")%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label"
                                               for="password">Mật khẩu *</label>
                                        <div class="col-md-5">
                                            <input id="password" type="password" class="form-control" name="password"
                                                   placeholder="Bắt buộc *"
                                                   value="<%=request.getParameter("password")==null? "":request.getParameter("password")%>">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label">Nhập lại mật khẩu *</label>
                                        <div class="col-md-5">
                                            <input type="password" class="form-control" name="inputPasswordConfirm"
                                                   placeholder="Nhập lại mật khẩu">
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="status">Trạng
                                            thái *</label>
                                        <!-- Single select boxes -->
                                        <div class="col-md-5">
                                            <div class="m-b-15">
                                                <select class="select2" id="status" name="status">
                                                    <option value="1"
                                                            <c:if test="${param.status eq '1'}">selected="selected"</c:if>>
                                                        Mở hoạt động
                                                    </option>
                                                    <option value="0"
                                                            <c:if test="${param.status eq '0'}">selected="selected"</c:if>>
                                                        Tắt hoạt động
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label class="col-sm-2 col-form-label control-label" for="permission">Quyền
                                            truy cập *</label>
                                        <!-- Single select boxes -->
                                        <div class="col-md-5">
                                            <div class="m-b-15">
                                                <select class="select2" id="permission" name="permission">
                                                    <option value="2"
                                                            <c:if test="${param.permission eq '2'}">selected="selected"</c:if>>
                                                        User
                                                    </option>
                                                    <option value="1"
                                                            <c:if test="${param.permission eq '1'}">selected="selected"</c:if>>
                                                        Admin
                                                    </option>
                                                    <option value="0"
                                                            <c:if test="${param.permission eq '0'}">selected="selected"
                                                            readonly</c:if>>Boss
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group text-right">
                                        <button type="submit" class="btn btn-primary"><c:choose><c:when
                                                test="${param.action eq 'getadd'}">Thêm </c:when><c:when
                                                test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when><c:when
                                                test="${param.action eq 'add'}">Thêm </c:when><c:when
                                                test="${param.action eq 'update'}">Chỉnh sửa </c:when></c:choose>Tài
                                            Khoản
                                        </button>
                                    </div>
                                </div>
                            </form>
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
    </div>
</div>
<!-- Core Vendors JS -->
<script src="admin/assets/js/vendors.min.js"></script>

<!-- page js -->
<script src="admin/assets/vendors/jquery-validation/jquery.validate.min.js"></script>
<script src="admin/assets/js/pages/form-validation.js"></script>
<script src="admin/assets/vendors/select2/select2.min.js"></script>
<script src="admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="admin/assets/vendors/quill/quill.min.js"></script>
<script src="admin/assets/js/pages/form-elements.js"></script>
<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

<script src="admin/assets/js/script.js"></script>
</body>

</html>
