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
    <title>Quản lý liên hệ</title>
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
            <h2 class="header-title">Quản Lý Liên Hệ</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý liên hệ</span>
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
                            <th>Email</th>
                            <th>Ngày liên hệ</th>
                            <th>Tiêu đề</th>
                            <th>Trạng thái</th>
                            <th width="10%"></th>
                            <th width="10%"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${listC}" var="contact">
                            <tr id="${contact.contactID}">
                                <td class="align-middle">${contact.email}</td>
                                <td class="align-middle">${contact.createDate}</td>
                                <td class="align-middle">${contact.userSubject}</td>
                                <td class="align-middle">${contact.status==1?'đã xử lí':'chưa xử lí'}</td>
                                <td class="align-middle">
                                    <c:if test="${contact.status ==0}">
                                        <a id="linkupdate"
                                           href="ReplyContact?action=getcontact&id=${contact.contactID}">
                                            <span class="fas fa-reply"></span> Xử lý</a>
                                    </c:if>
                                </td>
                                <td class="align-middle"><a class="text-danger"
                                                            href="ReplyContact?action=delete&id=${contact.contactID}"><span
                                        class="far fa-window-close" aria-hidden="true"></span> Xóa</a></td>
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
