<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="bean.Log" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý logs</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/logo-transparent-png-icon.png">

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
            <h2 class="header-title">Quản Lý Logs</h2>
            <div class="header-sub-title">
                <nav class="breadcrumb breadcrumb-dash">
                    <%--                    để đường link về trang dashboard--%>
                    <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                    <span class="breadcrumb-item active">Quản lý logs</span>
                </nav>
            </div>
        </div>
        <div class="card">
            <div class="card-body">
                <h1>Quản lý logs</h1>

                <%-- Truy vấn danh sách Logs từ cơ sở dữ liệu --%>
                <% List<Log> logs = Log.getAllLogs(); %>

                <%-- Sắp xếp danh sách Logs theo thời gian tạo (create_at) giảm dần --%>
                <% Collections.sort(logs, Comparator.comparing(Log::getCreate_at).reversed()); %>

                <%-- Hiển thị danh sách Logs --%>
                <table class="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Cấp độ</th>
                        <th>ID Khách hàng hoặc Username</th>
                        <th>Src</th>
                        <th>Nội dung</th>
                        <th>Địa chỉ IP</th>
                        <th>Trình duyệt Web</th>
                        <th>Thời gian tạo</th>
                        <th>Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%-- Duyệt qua danh sách Logs và hiển thị từng dòng --%>
                    <% for (Log log : logs) { %>
                    <tr>
                        <td><%= log.getId_log() %></td>
                        <td><%= log.getLevelWithName() %></td>
                        <td><%= log.getUser_id() %></td>
                        <td><%= log.getSrc() %></td>
                        <td><%= log.getContent() %></td>
                        <td><%= log.getIp_address() %></td>
                        <td><%= log.getWeb_browser() %></td>
                        <td><%= log.getCreate_at() %></td>
                        <td><%= log.getStatus() %></td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
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
