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
    <link href="admin/assets/vendors/select2/select2.css" rel="stylesheet"/>

    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">
</head>

<body>
<jsp:include page="headerAd.jsp"/>
<jsp:include page="menu.jsp"/>

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
                <div class="row">
                    <div class="col-lg-11 mx-auto">
                        <!-- Card View -->
                        <div class="row d-none" id="card-view">
                            <div class="col-md-3">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="m-t-20 text-center">
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">
                                            </div>
                                            <h4 class="m-t-30">Erin Gonzales</h4>
                                            <p>erin.gon@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">


                                            </div>
                                            <h4 class="m-t-30">Darryl Day</h4>
                                            <p>darryl.d@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">

                                            </div>
                                            <h4 class="m-t-30">Marshall Nichols</h4>
                                            <p>marshalln@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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

                                            <h4 class="m-t-30">Virgil Gonzales</h4>
                                            <p>virgil14@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">

                                            </div>
                                            <h4 class="m-t-30">Nicole Wyne</h4>
                                            <p>nicolew@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">

                                            </div>
                                            <h4 class="m-t-30">Riley Newman</h4>
                                            <p>rileyn93@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
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
                                            <div class="avatar avatar-image" style="height: 100px; width: 100px">

                                            </div>
                                            <h4 class="m-t-30">Pamela Wanda</h4>
                                            <p>pamelaw@gmail.com</p>
                                        </div>
                                        <div class="text-center m-t-15">
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-facebook"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-twitter"></i>
                                            </button>
                                            <button class="m-r-5 btn btn-icon btn-hover btn-rounded">
                                                <i class="anticon anticon-instagram"></i>
                                            </button>
                                        </div>
                                        <div class="text-center m-t-30">
                                            <a href="profile.html" class="btn btn-primary btn-tone">
                                                <i class="anticon anticon-mail"></i>
                                                <span class="m-l-5">Contact</span>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <h3>Sửa Role cho người dùng</h3>
                                <h4>${userName}</h4>
                                <div class="m-t-25" style="max-width: 700px">
                                    <form action="/EditRoleUser" method="post">
                                        <div class="form-group">
                                            <input type="hidden" name="userId" value="${userId}">
                                            <label class="font-weight-bold font-size-18">Role *</label>
                                            <div class="m-b-15">
                                                <select class="select2" name="role_id">
                                                    <c:forEach items="${roles}" var="role">
                                                        <option value="${role.role_id}" <c:if test="${role.role_id eq r}">selected</c:if>>${role.role_name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group row">
                                            <div class="col-sm-10">
                                                <button type="submit" class="btn btn-primary">
                                                    Lưu
                                                </button>
                                            </div>
                                        </div>
                                    </form>
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
<script src="admin/assets/vendors/jquery/dist/jquery.js"></script>
<script src="admin/assets/vendors/select2/select2.min.js"></script>
<script>
    $(".select2").select2();
</script>
<%--<script !src="">--%>
<%--    $(document).ready(function() {--%>
<%--        var defaultValue = ${r};--%>
<%--        $(".select2").val(defaultValue).trigger("change");--%>
<%--    });--%>
<%--</script>--%>
<script src="admin/assets/js/vendors.min.js"></script>
<!-- page js -->
<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

<%--Custom JS--%>
<script src="admin/assets/js/script.js"></script>




</body>

</html>
