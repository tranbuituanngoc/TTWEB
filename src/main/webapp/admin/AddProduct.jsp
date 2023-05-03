<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title><c:choose>
        <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
        <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
        <c:when test="${param.action eq 'add'}">Thêm </c:when>
        <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
    </c:choose>
        sản phẩm</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/select2/select2.css" rel="stylesheet">
    <link href="admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">


    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">
    <%-- Custom css--%>
    <link href="admin/assets/css/style.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

    <link href="admin/assets/summernote/summernote.css" rel="stylesheet"/>
    <link href="admin/assets/summernote/summernote-bs4.css" rel="stylesheet"/>
    <link href="admin/assets/summernote/summernote-lite.css" rel="stylesheet"/>
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
                        <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
                        <c:when test="${param.action eq 'getupdate'}">Chỉnh Sửa</c:when>
                        <c:when test="${param.action eq 'add'}">Thêm </c:when>
                        <c:when test="${param.action eq 'update'}">Chỉnh Sửa</c:when>
                    </c:choose> Sản Phẩm</h2>
                    <div class="header-sub-title">
                        <nav class="breadcrumb breadcrumb-dash">
                            <%--                            sửa lại thành địa chỉ dashboard--%>
                            <a href="#" class="breadcrumb-item"><i class="anticon anticon-home m-r-5"></i>Home</a>
                            <span class="breadcrumb-item active"><c:choose>
                                <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
                                <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
                                <c:when test="${param.action eq 'add'}">Thêm </c:when>
                                <c:when test="${param.action eq 'update'}">Chỉnh sửa</c:when>
                            </c:choose> sản phẩm</span>
                        </nav>
                    </div>
                </div>
                <div class="card">
                    <div class="card-body">
                        <h4><c:choose>
                            <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
                            <c:when test="${param.action eq 'getupdate'}">Chỉnh Sửa</c:when>
                            <c:when test="${param.action eq 'add'}">Thêm </c:when>
                            <c:when test="${param.action eq 'update'}">Chỉnh Sửa</c:when>
                        </c:choose> Sản Phẩm</h4>
                        <div class="m-t-25">
                            <h5 class="m-t-10 m-b-10"
                                style="color: red"><%=request.getAttribute("err") == null ? "" : request.getAttribute("err")%>
                            </h5>
                            <form id="form-validation" class="form-horizontal" action="AddOrUpdateProduct"
                                  onsubmit="return checkAddProduct()"
                                  method="post" enctype="multipart/form-data">
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
                                    <ul class="nav nav-tabs m-b-50" id="myTab" role="tablist">
                                        <li class="nav-item">
                                            <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home"
                                               role="tab" aria-controls="home" aria-selected="true">Tổng Quan</a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile"
                                               role="tab" aria-controls="profile" aria-selected="false">Mô Tả</a>
                                        </li>
                                    </ul>
                                    <div class="tab-content m-t-15" id="myTabContent">
                                        <div class="tab-pane fade show active" id="home" role="tabpanel"
                                             aria-labelledby="home-tab">
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="productname">Tên
                                                    gạch *</label>
                                                <div class="col-md-5">
                                                    <input type="text" class="form-control" name="productname"
                                                           id="productname"
                                                           placeholder="Nhập tên gạch"
                                                           value="<%=request.getParameter("productname")==null? "":request.getParameter("productname")%>">
                                                </div>
                                                <div class="m-t-5 m-b-5" id="error_productname"></div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="type">Loại
                                                    gạch *</label>
                                                <!-- Single select boxes -->
                                                <div class="col-md-5">
                                                    <div class="m-b-15">
                                                        <select id="type" class="select2" name="type">
                                                            <%--                                                if list typeSelected is null then only show listCategory otherwise set isSelect if it is in typeSelected--%>
                                                            <c:choose>
                                                                <c:when test="${empty typeSelected}">
                                                                    <c:forEach items="${listCategory}" var="item2">
                                                                        <option value="${item2.description}">${item2.description}</option>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach items="${listCategory}" var="item2">
                                                                        <c:set var="isSelected"
                                                                               value="${typeSelected eq item2.description}"/>
                                                                        <option value="${item2.description}"
                                                                                <c:if test="${isSelected}">selected="selected"</c:if>>${item2.description}</option>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="sizeProduct">Kích
                                                    thước *</label>
                                                <!-- Multiple select boxes -->
                                                <div class="col-md-5">
                                                    <div class="m-b-15">
                                                        <select id="sizeProduct" class="form-select" name="sizeProduct"
                                                                multiple="multiple">
                                                            <%--                                            if list sizeSelected is null then only show listSize otherwise set isSelect if it is in sizeSelected--%>
                                                            <c:choose>
                                                                <c:when test="${empty sizeSelected}">
                                                                    <c:forEach items="${listSize}" var="item2">
                                                                        <option class="select2-result-label"
                                                                                value="${item2.descrip}">${item2.descrip}</option>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach items="${listSize}" var="item2">
                                                                        <c:set var="isSelected" value="false"/>
                                                                        <c:forEach items="${sizeSelected}" var="item1">
                                                                            <c:if test="${item1 eq item2.descrip}">
                                                                                <c:set var="isSelected" value="true"/>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <option class="select2-result-label"
                                                                                value="${item2.descrip}"
                                                                                <c:if test="${isSelected}">selected="selected"</c:if>>${item2.descrip}</option>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="colorProduct">Màu
                                                    sắc *</label>
                                                <!-- Multiple select boxes -->
                                                <div class="col-md-5">
                                                    <div class="m-b-15">
                                                        <select id="colorProduct" class="form-select"
                                                                name="colorProduct" aria-label="size 3 select"
                                                                multiple="multiple">
                                                            <%--                                                if list colorSelected is null then only show listColor otherwise set isSelect if it is in colorSelected   --%>
                                                            <c:choose>
                                                                <c:when test="${empty colorSelected}">
                                                                    <c:forEach items="${listColor}" var="item2">
                                                                        <option class="select2-result-label"
                                                                                value="${item2.descrip}">${item2.descrip}</option>
                                                                    </c:forEach>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:forEach items="${listColor}" var="item2">
                                                                        <c:set var="isSelected" value="false"/>
                                                                        <c:forEach items="${colorSelected}" var="item1">
                                                                            <c:if test="${item1 eq item2.descrip}">
                                                                                <c:set var="isSelected" value="true"/>
                                                                            </c:if>
                                                                        </c:forEach>
                                                                        <option class="select2-result-label"
                                                                                value="${item2.descrip}"
                                                                                <c:if test="${isSelected}">selected="selected"</c:if>>${item2.descrip}</option>
                                                                    </c:forEach>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clearfix"></div>
                                            <div id="form-container"></div>
                                            <div class="clearfix"></div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="sale">Khuyến
                                                    mãi *</label>
                                                <div class="col-md-5">
                                                    <input type="text" class="form-control" name="sale" id="sale"
                                                           placeholder="Nhập vào phần trăm giảm giá..."
                                                           value="<%=request.getParameter("sale")==null? "":request.getParameter("sale")%>">
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label"></label>
                                                <div id="single-image-preview" class="controls m-l-12">
                                                    <c:if test="${product.thumb != null}">
                                                        <img src="${product.thumb}">
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="thumbnail">Chọn
                                                    ảnh Thumbnail *</label>
                                                <div class="col-md-5">
                                                    <div class="custom-file">
                                                        <input type="file" class="custom-file-input" name="thumbnail"
                                                               id="thumbnail" accept="image/*"
                                                               onchange="showFileName('thumbnail', 'thumbnail-name');previewSingleImage()">
                                                        <label class="custom-file-label" for="thumbnail"
                                                               id="thumbnail-name">No file chosen</label>
                                                    </div>
                                                    <div class="controls m-t-5 m-b-5" id="error_thumbnail"></div>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label"></label>
                                                <div id="multiple-image-preview" class="controls m-l-12">
                                                    <c:if test="${not empty product.image}">
                                                        <c:forEach items="${product.image}" var="image">
                                                            <img src="${image.image}">
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="images">Chọn
                                                    ảnh sản phẩm *</label>
                                                <div class="col-md-5">
                                                    <div class="custom-file">
                                                        <input type="file" class="custom-file-input" name="images"
                                                               id="images" accept="image/*" multiple
                                                               onchange="showFileNames('images', 'images-names');previewMultipleImages()">
                                                        <label class="custom-file-label" for="images" id="images-names">No
                                                            file chosen</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="newProduct">Hàng
                                                    mới</label>
                                                <!-- Single select boxes -->
                                                <div class="col-md-5">
                                                    <div class="m-b-15">
                                                        <select id="newProduct" class="select2" name="newProduct">
                                                            <option value="1"
                                                                    <c:if test="${param.newProduct eq '1'}">selected="selected"</c:if>>
                                                                Có
                                                            </option>
                                                            <option value="0"
                                                                    <c:if test="${param.newProduct eq '0'}">selected="selected"</c:if>>
                                                                Không
                                                            </option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="tab-pane fade" id="profile" role="tabpanel"
                                             aria-labelledby="profile-tab">
                                            <div class="form-group row">
                                                <label class="col-sm-2 col-form-label control-label" for="description">Mô
                                                    tả sản phẩm *</label>
                                                <textarea id="my-summernote" name="description" id="description"
                                                          class="description"></textarea>
                                                <script>
                                                    $(document).ready(function () {
                                                        // Định vị Summernote
                                                        var $summernote = $('#my-summernote');

                                                        // Thiết lập giá trị cho Summernote
                                                        $summernote.summernote({
                                                            height: 300,
                                                            tabsize: 2,
                                                            placeholder: 'Nhập mô tả cho sản phẩm...',
                                                            callbacks: {
                                                                onInit: function () {
                                                                    // Đặt giá trị cho Summernote
                                                                    $summernote.summernote('code', '<%=request.getParameter("description") == null ? "" : request.getParameter("description")%>');
                                                                }
                                                            }
                                                        });
                                                    });
                                                </script>
                                                <div class="controls" id="error_description"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group text-right">
                                    <button class="btn btn-primary" type="submit" id="check-button"
                                            onclick=" checkAddProduct()">
                                        <c:choose><c:when
                                                test="${param.action eq 'getadd'}">Thêm </c:when><c:when
                                                test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when><c:when
                                                test="${param.action eq 'add'}">Thêm </c:when><c:when
                                                test="${param.action eq 'update'}">Chỉnh sửa </c:when></c:choose>sản
                                        phẩm
                                    </button>
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


<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


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
<!-- Summnernote -->
<script src="admin/assets/summernote/summernote.js"></script>
<script src="admin/assets/summernote/summernote-bs4.js"></script>
<script src="admin/assets/summernote/summernote-lite.js"></script>
<%--Custom JS--%>
<script src="admin/assets/js/script.js"></script>
<script>

    function showFileName(inputId, labelId) {
        var input = document.getElementById(inputId);
        var label = document.getElementById(labelId);

        label.innerHTML = input.files[0].name;
    }

    function showFileNames(inputId, labelId) {
        var input = document.getElementById(inputId);
        var label = document.getElementById(labelId);

        var fileNames = '';

        for (var i = 0; i < input.files.length; i++) {
            fileNames += input.files[i].name + ', ';
        }

        fileNames = fileNames.substring(0, fileNames.length - 2);
        label.style.whiteSpace = "nowrap";
        label.style.overflow = "hidden";
        label.style.textOverflow = "ellipsis";
        label.innerHTML = fileNames;
    }

    window.onload = generateForm;

    // Tạo lại các ô input với giá trị đã nhập từ danh sách
    function generateForm() {
        var select1 = document.getElementById("sizeProduct");
        var select2 = document.getElementById("colorProduct");
        var values1 = getSelectedValues(select1);
        var values2 = getSelectedValues(select2);
        var container = document.getElementById("form-container");
        container.innerHTML = "";
        var inputListQ = ${not empty requestScope.inputListQ ? requestScope.inputListQ : 'null'};
        var inputListP = ${not empty requestScope.inputListP ? requestScope.inputListP : 'null'};
        var inputListC = ${not empty requestScope.inputListC ? requestScope.inputListC : 'null'};
        var index = 0;
        for (var i = 0; i < values1.length; i++) {
            for (var j = 0; j < values2.length; j++) {
                var div2 = document.createElement("div");
                div2.className = "form-group row "
                container.appendChild(div2);
                var div = document.createElement("div");
                div.className = "col-md-5"
                var label = document.createElement("label");
                label.className = "col-sm-2 col-form-label control-label";
                label.innerHTML = values1[i] + ", " + values2[j] + ":";
                div2.appendChild(label);

                var inputQ = document.createElement("input");
                inputQ.type = "number";
                inputQ.className = "form-control dynamic-input mb-4 float-left col-4 inputQ";
                inputQ.placeholder = "Nhập số lượng!"
                inputQ.name = values1[i] + " " + values2[j] + "q"
                inputQ.style = "padding: 18px !important"
                inputQ.min = "0";
                inputQ.pattern = "[0-9]*";
                inputQ.step = "any";


                var inputC = document.createElement("input");
                inputC.type = "number";
                inputC.className = "form-control dynamic-input mb-4 float-left col-4 inputC";
                inputC.placeholder = "Nhập giá nhập!"
                inputC.name = values1[i] + " " + values2[j] + "c"
                inputC.style = "padding: 18px !important"
                inputC.min = "0";
                inputC.pattern = "[0-9]*";
                inputC.step = "any";

                var inputP = document.createElement("input");
                inputP.type = "number";
                inputP.className = "form-control dynamic-input mb-4 float-left col-4 inputP";
                inputP.placeholder = "Nhập giá bán!"
                inputP.name = values1[i] + " " + values2[j] + "p"
                inputP.style = "padding: 18px !important"
                inputP.min = "0";
                inputP.pattern = "[0-9]*";
                inputP.step = "any";

                if (inputListC && inputListP && inputListQ && index < inputListQ.length && index < inputListP.length && index < inputListC.length) {
                    inputC.value = inputListC[index];
                    inputQ.value = inputListQ[index];
                    inputP.value = inputListP[index];
                    index++;
                } else {
                    inputC.value = "";
                    inputQ.value = "";
                    inputP.value = "";
                }
                var div3 = document.createElement("div");
                var err = document.createElement("p");
                err.id = "error_dynamic_input"
                err.className = "required"

                div3.appendChild(err)

                div.appendChild(inputQ);
                div.appendChild(inputC);
                div.appendChild(inputP);

                div2.appendChild(div)
                div2.appendChild(div3)
            }
        }
    }

    function validateInputs() {
        console.log(1)
        var inputs = document.querySelectorAll('.dynamic-input');
        err = document.getElementById('error_dynamic_input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].value === '') {
                err.innerHTML = "Vui lòng nhập đầy đủ thông tin trong ô input!!!"
                return false;
            }
        }
        return true;
    }

    // Lấy giá trị được chọn từ các ô select
    function getSelectedValues(select) {
        var result = [];
        var options = select && select.options;
        var opt;
        for (var i = 0, iLen = options.length; i < iLen; i++) {
            opt = options[i];
            if (opt.selected) {
                result.push(opt.value || opt.text);
            }
        }
        return result;
    }


    // function checkValues() {
    //     var quantity = document.getElementById("quantity").value;
    //     var dynamicInputs = document.getElementsByClassName("dynamic-input");
    //     var sum = 0;
    //     for (var i = 0; i < dynamicInputs.length; i++) {
    //         sum += parseInt(dynamicInputs[i].value) || 0;
    //     }
    //     var resultElement = document.getElementById("err-quantity");
    //     if (sum != quantity) {
    //         resultElement.innerHTML = "Tổng các giá trị nhập vào khác với số lượng sản phẩm nhập vào!";
    //         resultElement.style.color = "red";
    //         return false;
    //     } else {
    //         resultElement.innerHTML = "";
    //         return true;
    //     }
    // }

    function checkNullValue() {
        console.log(2)
        error = 0;
        productname = document.getElementById('productname').value;
        // price = document.getElementById('price').value;
        // cost = document.getElementById('cost').value;
        // quantity = document.getElementById('quantity').value;
        description = document.getElementById('description').value;
        var images = document.getElementById('images');
        var thumbnail = document.getElementById('thumbnail');

        error_productname = document.getElementById('error_productname');
        // error_price = document.getElementById('error_price');
        // error_cost = document.getElementById('error_cost');
        // error_quantity = document.getElementById('error_quantity');
        error_description = document.getElementById('error_description');
        error_images = document.getElementById('error_images');
        error_thumbnail = document.getElementById('error_thumbnail');

        if (productname.length == 0) {
            error += 1;
            error_productname.innerHTML = '<span class="required">Vui lòng không để trống phần Tên sản phẩm</span>';
        } else {
            error_productname.innerHTML = ''
        }

        // if (price.length == 0) {
        //     error += 1;
        //     error_price.innerHTML = '<span class="required">Vui lòng không để trống phần giá bán sản phẩm</span>';
        // } else {
        //     error_price.innerHTML = ''
        // }
        // if (cost.length == 0) {
        //     error += 1;
        //     error_cost.innerHTML = '<span class="required">Vui lòng không để trống phần giá nhập sản phẩm</span>';
        // } else {
        //     error_cost.innerHTML = ''
        // }

        // if (quantity.length == 0) {
        //     error += 1;
        //     error_quantity.innerHTML = '<span class="required">Vui lòng không để trống phần số lượng sản phẩm</span>';
        // } else {
        //     error_quantity.innerHTML = ''
        // }

        if (description.length == 0) {
            error += 1;
            error_description.innerHTML = '<span class="required">Vui lòng không để trống phần mô tả sản phẩm</span>';
        } else {
            error_description.innerHTML = ''
        }

        if (thumbnail.files.length <= 0) {
            error += 1;
            error_thumbnail.innerHTML = '<span class="required">Vui lòng chọn Thumbnail cho sản phẩm sản phẩm</span>';
        } else {
            error_thumbnail.innerHTML = ''
        }
        if (images.files.length <= 0) {
            error += 1;
            error_images.innerHTML = '<span class="required">Vui lòng chọn hình ảnh cho sản phẩm cho sản phẩm sản phẩm</span>';
        } else {
            error_images.innerHTML = ''
        }
        if (error == 0) {
            return true;
        } else return false;
    }

    function checkAddProduct() {
        if (checkNullValue() == false) {
            return false;
        } else {
            return true;
        }
    }

    document.getElementById("sizeProduct").addEventListener("change", generateForm);
    document.getElementById("colorProduct").addEventListener("change", generateForm);
    // document.getElementById("check-button").addEventListener("click", checkValues);

    //preview image function

    function previewSingleImage() {
        const preview = document.querySelector('#single-image-preview');
        preview.innerHTML = '';
        const file = document.querySelector('#thumbnail').files[0];
        const reader = new FileReader();
        reader.onload = function (event) {
            const img = document.createElement('img');
            img.src = event.target.result;
            preview.appendChild(img);
        }
        reader.readAsDataURL(file);
    }

    function previewMultipleImages() {
        const preview = document.querySelector('#multiple-image-preview');
        preview.innerHTML = '';
        const files = document.querySelector('#images').files;

        for (let i = 0; i < files.length; i++) {
            const file = files[i];
            const reader = new FileReader();
            reader.onload = function (event) {
                const img = document.createElement('img');
                img.src = event.target.result;
                preview.appendChild(img);
            }
            reader.readAsDataURL(file);
        }
    }
</script>
</body>

</html>
