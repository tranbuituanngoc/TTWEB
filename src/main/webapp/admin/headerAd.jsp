<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    User u = (User) session.getAttribute("user");
    if (u == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String idUser = u.getId_User();
    String username = u.getUserName();
    int role = u.getRole();
    if (username.equalsIgnoreCase("") || role == 2) {
        response.sendRedirect(request.getContextPath() + "/Home");
        return;
    }
%>
<c:set var="username" value="<%=username%>"/>
<c:set var="userId" value="<%=idUser%>"/>
<div class="app">
    <div class="layout">
        <!-- Header START -->
        <div class="header">
            <div class="logo logo-dark">
                <a href="Home">
                    <img src="admin/assets/images/logo/logo.png" alt="Logo">
                    <img class="logo-fold" src="admin/assets/images/logo/logo-fold.png" alt="Logo">
                </a>
            </div>
            <div class="logo logo-white">
                <a href="Home">
                    <img src="admin/assets/images/logo/logo-white.png" alt="Logo">
                    <img class="logo-fold" src="admin/assets/images/logo/logo-fold-white.png" alt="Logo">
                </a>
            </div>

            <c:if test="${user != null}">
                <div class="nav-wrap">
                    <ul class="nav-left">
                    </ul>
                    <ul class="nav-right">
                        <li class="dropdown">
                            <button class="btn btn-default dropdown-toggle border-0"  data-toggle="dropdown">
                                <i class="anticon anticon-user m-r-5"></i><span>${username}</span><i class="caret"></i>
                            </button>
                            <div class="dropdown-menu">
                                <a class="dropdown-item" href="nguoi-dung?action=dang-xuat">Đăng xuất</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </c:if>
        </div>
        <!-- Header END -->
    </div>
</div>

