<%@ page import="bean.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.UserSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    UserSession u = UserSession.getUS(session);
    Collection<User> user = u.getUser();
    String username = u.getUserName();
    if(username.equalsIgnoreCase("")||!user.iterator().next().accept("admin.index")) response.sendRedirect("http://localhost:8080/GachMen_Store_war/Home");
%>
<!DOCTYPE html>
<html>

<head>
    <title>Quản lý tài khoản</title>
    <!-- Bootstrap -->
    <meta charset="utf-8">
    <link href="admin/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="admin/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet" media="screen">
    <link href="admin/assets/styles.css" rel="stylesheet" media="screen">
    <link href="admin/assets/DT_bootstrap.css" rel="stylesheet" media="screen">
    <!--[if lte IE 8]>
    <script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script src="admin/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.22/pdfmake.min.js"></script>
    <script type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.min.js"></script>
    <style>

    </style>
</head>

<body>
<jsp:include page="headerAd.jsp"/>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="menu.jsp"/>
        <!--/span-->
        <div class="span9" id="content">
            <div class="row-fluid">
                <!-- block -->
                <div class="block">
                    <div class="navbar navbar-inner block-header">
                        <div class="muted pull-left">Quản lý tài khoản</div>
                    </div>
                    <div class="block-content collapse in">
                        <div class="span12">
                            <div class="table-toolbar">
                                <div class="btn-group">
                                    <a href="AddOfUpdateUser?action=getadd">
                                        <button class="btn btn-success">Thêm tài khoản <i
                                                class="icon-plus icon-white"></i></button>
                                    </a>
                                </div>
                                <div class="btn-group pull-right">
                                    <button data-toggle="dropdown" class="btn dropdown-toggle">Công cụ <span
                                            class="caret"></span></button>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">In</a></li>
                                        <li><a id="exportPDF">Lưu file PDF</a></li>
                                        <li><a onclick="exportTableToExcel('example2','users')">Xuất ra Excel</a></li>
                                    </ul>
                                </div>
                            </div>
                            <table cellpadding="0" cellspacing="0" border="0" class="table table-bordered"
                                   id="example2">
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
                                    <tr id="${user.idUser}">
                                        <td>${user.userName}</td>
                                        <td><c:if test="${user.isAdmin==1}">Admin</c:if>
                                            <c:if test="${user.isAdmin==3}">Boss</c:if>
                                            <c:if test="${user.isAdmin==0}">User</c:if>
                                        </td>
                                        <td id="status">${user.status==1?"đang hoạt động":"đã ngừng hoạt động"}</td>
                                        <td id="lock-unlock">
                                            <c:if test="${user.isAdmin!=3}">
                                                <c:if test="${user.status==0}"><a class="text-unlock text-primary"
                                                                                  href="AddOfUpdateUser?action=unlock&id=${user.idUser}">
                                                    <span class="fas fa-lock-open"></span> Mở khóa</a></c:if>
                                                <c:if test="${user.status==1}"><a class="text-lock text-primary"
                                                                                  href="AddOfUpdateUser?action=lock&id=${user.idUser}">
                                                    <span class="fas fa-lock"></span> Khóa</a></c:if>
                                            </c:if>

                                        </td>
                                        <td><a class="text-lock text-primary"
                                               href="AddOfUpdateUser?action=getupdate&id=${user.idUser}"><span
                                                class="fas fa-edit"></span> Chỉnh sửa</a></td>
                                        <td><c:if test="${user.isAdmin!=3}">
                                            <a class="text-danger"
                                               href="AddOfUpdateUser?action=delete&id=${user.idUser}"><span
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
                <!-- /block -->
            </div>
        </div>
    </div>
    <hr>
    <jsp:include page="footerAd.jsp"/>
</div>
<!--/.fluid-container-->

<script src="admin/vendors/jquery-1.9.1.js"></script>
<script src="admin/bootstrap/js/bootstrap.min.js"></script>
<script src="admin/vendors/datatables/js/jquery.dataTables.min.js"></script>


<script src="admin/assets/scripts.js"></script>
<script src="admin/assets/DT_bootstrap.js"></script>

<script type="text/javascript">
    $("body").on("click", "#exportPDF", function () {
        html2canvas($('#example2')[0], {
            onrendered: function (canvas) {
                var data = canvas.toDataURL();
                var docDefinition = {
                    content: [{
                        image: data,
                        width: 500
                    }]
                };
                pdfMake.createPdf(docDefinition).download("users.pdf");
            }
        });
    });
</script>
<script>
    function exportTableToExcel(tableID, filename = '') {
        var downloadLink;
        var dataType = 'application/vnd.ms-excel';
        var tableSelect = document.getElementById(tableID);
        var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');

        // Specify file name
        filename = filename ? filename + '.xls' : 'excel_data.xls';

        // Create download link element
        downloadLink = document.createElement("a");

        document.body.appendChild(downloadLink);

        if (navigator.msSaveOrOpenBlob) {
            var blob = new Blob(['\ufeff', tableHTML], {
                type: dataType
            });
            navigator.msSaveOrOpenBlob(blob, filename);
        } else {
            // Create a link to the file
            downloadLink.href = 'data:' + dataType + ', ' + tableHTML;

            // Setting the file name
            downloadLink.download = filename;

            //triggering the function
            downloadLink.click();
        }
    }
</script>
</body>
