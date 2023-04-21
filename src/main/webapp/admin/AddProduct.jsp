<%--suppress ALL --%>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ProductColorService" %>
<%@ page import="service.ProductSizeService" %>
<%@ page import="service.ProductCategoryService" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <title><c:choose>
        <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
        <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
        <c:when test="${param.action eq 'add'}">Thêm </c:when>
        <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
    </c:choose>
        sản phẩm</title>
    <!-- Bootstrap -->
    <meta charset="utf-8">
    <link href="${pageContext.request.contextPath}/admin/bootstrap/css/bootstrap.min.css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath}/admin/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath}/admin/assets/styles.css" rel="stylesheet" media="screen">
    <!--[if lte IE 8]>
    <script language="javascript" type="text/javascript" src="vendors/flot/excanvas.min.js"></script><![endif]-->
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script src="admin/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
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
<c:set var="username" value="<%=username%>"/>
<jsp:include page="headerAd.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row-fluid">
        <!--/span-->
        <jsp:include page="menu.jsp"></jsp:include>
        <div class="span9" id="content">
            <!-- block -->
            <div class="block">
                <div class="navbar navbar-inner block-header">
                    <div class="muted pull-left">
                        <c:choose>
                            <c:when test="${param.action eq 'getadd'}">Thêm</c:when>
                            <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
                            <c:when test="${param.action eq 'add'}">Thêm </c:when>
                            <c:when test="${param.action eq 'update'}">Chỉnh sửa</c:when>
                        </c:choose> sản phẩm
                    </div>
                </div>
                <div class="block-content collapse in">
                    <div class="span12">
                        <h4 style="color: red"><%=request.getAttribute("err") == null ? "" : request.getAttribute("err")%>
                        </h4>
                        <form class="form-horizontal" action="AddOrUpdateProduct" onsubmit="return checkAddProduct()"
                              method="post" enctype="multipart/form-data">
                            <fieldset>
                                <legend><c:choose>
                                    <c:when test="${param.action eq'getadd'}">Thêm</c:when>
                                    <c:when test="${param.action eq 'getupdate'}">Chỉnh sửa</c:when>
                                    <c:when test="${param.action eq 'add'}">Thêm </c:when>
                                    <c:when test="${param.action eq 'update'}">Chỉnh sửa </c:when>
                                </c:choose> sản phẩm
                                </legend>
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
                                <div class="control-group">
                                    <label class="control-label" for="productname">Tên gạch (*) </label>
                                    <div class="controls">
                                        <input type="text" name="productname" class="span6" id="productname"
                                               placeholder="Nhập tên gạch"
                                               value="<%=request.getParameter("productname")==null? "":request.getParameter("productname")%>">
                                    </div>
                                    <div class="controls" id="error_productname"></div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="type">Loại gạch</label>
                                    <div class="controls">
                                        <select id="type" class="chzn-select" name="type" style="width:49%;">
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
                                <div class="control-group">
                                    <label class="control-label" for="sizeProduct">Kích thước </label>
                                    <div class="controls">
                                        <select id="sizeProduct" class="chzn-select" name="sizeProduct"
                                                style="width:49% ;" multiple>
                                            <%--                                            if list sizeSelected is null then only show listSize otherwise set isSelect if it is in sizeSelected--%>
                                            <c:choose>
                                                <c:when test="${empty sizeSelected}">
                                                    <c:forEach items="${listSize}" var="item2">
                                                        <option value="${item2.description}">${item2.description}</option>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${listSize}" var="item2">
                                                        <c:set var="isSelected" value="false"/>
                                                        <c:forEach items="${sizeSelected}" var="item1">
                                                            <c:if test="${item1 eq item2.description}">
                                                                <c:set var="isSelected" value="true"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <option value="${item2.description}"
                                                                <c:if test="${isSelected}">selected="selected"</c:if>>${item2.description}</option>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="colorProduct">Màu sắc </label>
                                    <div class="controls">
                                        <select id="colorProduct" class="chzn-select" name="colorProduct"
                                                style="width:49% ;" multiple>
                                            <%--                                                if list colorSelected is null then only show listColor otherwise set isSelect if it is in colorSelected   --%>
                                            <c:choose>
                                                <c:when test="${empty colorSelected}">
                                                    <c:forEach items="${listColor}" var="item2">
                                                        <option value="${item2.description}">${item2.description}</option>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach items="${listColor}" var="item2">
                                                        <c:set var="isSelected" value="false"/>
                                                        <c:forEach items="${colorSelected}" var="item1">
                                                            <c:if test="${item1 eq item2.description}">
                                                                <c:set var="isSelected" value="true"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <option value="${item2.description}"
                                                                <c:if test="${isSelected}">selected="selected"</c:if>>${item2.description}</option>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </select>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div id="form-container"></div>
                                <div class="clearfix"></div>
                                <div class="control-group">
                                    <label class="control-label" for="sale">Khuyến mãi </label>
                                    <div class="controls">
                                        <input type="number" name="sale" class="span6" id="sale"
                                               placeholder="Nhập phần trăm giảm giá"
                                               value="<%=request.getParameter("sale")==null? "":request.getParameter("sale")%>">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="thumbnail">Chọn ảnh Thumbnail (*) </label>
                                    <div id="single-image-preview" class="controls">
                                    <c:if test="${product.thumb != null}">
                                        <img src="${product.thumb}">
                                    </c:if>
                                    </div>
                                    <div class="controls">
                                        <input type="file" name="thumbnail" class="span6" id="thumbnail"
                                               accept="image/*" onchange="previewSingleImage()">
                                    </div>
                                    <div class="controls" id="error_thumbnail"></div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="images">Chọn ảnh cho sản phẩm(*) </label>
                                    <div id="multiple-image-preview" class="controls">
                                        <c:if test="${not empty product.image}">
                                            <c:forEach items="${product.image}" var="image">
                                                <img src="${image.image}">
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <div class="controls">
                                        <input type="file" name="images" class="span6" id="images" accept="image/*"
                                               multiple onchange="previewMultipleImages()">
                                    </div>
                                    <div class="controls" id="error_images"></div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="newProduct">Hàng mới</label>
                                    <div class="controls">
                                        <select id="newProduct" class="chzn-select" name="newProduct">
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

                                <div class="control-group">
                                    <label class="control-label" for="description">Mô tả (*) </label>
                                    <div class="controls">
                                            <textarea rows="100" cols="200" name="description" id="description"
                                                      class="description" placeholder="Nhập mô tả"
                                                      style="width: 810px; height: 200px"
                                            ><%=request.getParameter("description") == null ? "" : request.getParameter("description")%></textarea>
                                    </div>
                                    <div class="controls" id="error_description"></div>
                                </div>

                                <div class="form-actions" style="background: white">
                                    <button type="submit" id="check-button" onclick=" checkAddProduct()"
                                            class="btn btn-primary"><c:choose><c:when
                                            test="${param.action eq 'getadd'}">Thêm </c:when><c:when
                                            test="${param.action eq 'getupdate'}">Chỉnh sửa </c:when><c:when
                                            test="${param.action eq 'add'}">Thêm </c:when><c:when
                                            test="${param.action eq 'update'}">Chỉnh sửa </c:when></c:choose>sản
                                        phẩm
                                    </button>
                                </div>
                            </fieldset>
                        </form>

                    </div>
                </div>
            </div>
            <!-- /block -->
        </div>
    </div>
</div>
<hr>
</div>
<!--/.fluid-container-->
<link href="${pageContext.request.contextPath}/admin/vendors/datepicker.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/admin/vendors/uniform.default.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/admin/vendors/chosen.min.css" rel="stylesheet" media="screen">

<link href="${pageContext.request.contextPath}/admin/vendors/wysiwyg/bootstrap-wysihtml5.css" rel="stylesheet"
      media="screen">
<script src="${pageContext.request.contextPath}/admin/vendors/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath}/admin/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendors/jquery.uniform.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendors/chosen.jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendors/bootstrap-datepicker.js"></script>

<script src="${pageContext.request.contextPath}/admin/vendors/wysiwyg/wysihtml5-0.3.0.js"></script>
<script src="${pageContext.request.contextPath}/admin/vendors/wysiwyg/bootstrap-wysihtml5.js"></script>

<script src="${pageContext.request.contextPath}/admin/vendors/wizard/jquery.bootstrap.wizard.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/admin/vendors/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/admin/assets/form-validation.js"></script>

<script src="${pageContext.request.contextPath}/admin/assets/scripts.js"></script>
<%--<script type="text/javascript">--%>
<%--    $(document).ready(function () {--%>
<%--        $('.multiple-checkboxes').multiselect();--%>
<%--    });--%>
<%--</script>--%>
<script>
    jQuery(document).ready(function () {
        FormValidation.init();
    });


    $(function () {
        $(".datepicker").datepicker();
        $(".uniform_on").uniform();
        $(".chzn-select").chosen();
        $('.textarea').wysihtml5();

        $('#rootwizard').bootstrapWizard({
            onTabShow: function (tab, navigation, index) {
                var $total = navigation.find('li').length;
                var $current = index + 1;
                var $percent = ($current / $total) * 100;
                $('#rootwizard').find('.bar').css({width: $percent + '%'});
                // If it's the last tab then hide the last button and show the finish instead
                if ($current >= $total) {
                    $('#rootwizard').find('.pager .next').hide();
                    $('#rootwizard').find('.pager .finish').show();
                    $('#rootwizard').find('.pager .finish').removeClass('disabled');
                } else {
                    $('#rootwizard').find('.pager .next').show();
                    $('#rootwizard').find('.pager .finish').hide();
                }
            }
        });
        $('#rootwizard .finish').click(function () {
            alert('Finished!, Starting over!');
            $('#rootwizard').find("a[href*='tab1']").trigger('click');
        });
    });


    window.onload = generateForm;

    // Tạo lại các ô input với giá trị đã nhập từ danh sách
    function generateForm() {
        var select1 = document.getElementById("sizeProduct");
        var select2 = document.getElementById("colorProduct");
        var values1 = getSelectedValues(select1);
        var values2 = getSelectedValues(select2);
        var container = document.getElementById("form-container");
        container.innerHTML = "";
        var div2 = document.createElement("div");
        div2.className = "control-group"
        container.appendChild(div2);
        var inputListQ = ${not empty requestScope.inputListQ ? requestScope.inputListQ : 'null'};
        var inputListP = ${not empty requestScope.inputListP ? requestScope.inputListP : 'null'};
        var inputListC = ${not empty requestScope.inputListC ? requestScope.inputListC : 'null'};
        var index = 0;
        for (var i = 0; i < values1.length; i++) {
            for (var j = 0; j < values2.length; j++) {
                var div = document.createElement("div");
                var label = document.createElement("label");
                label.className = "ml-5 h5 bold float-left  col-3 input-group-text controls ";
                label.innerHTML = values1[i] + ", " + values2[j] + ":";
                div.appendChild(label);

                var inputQ = document.createElement("input");
                inputQ.type = "number";
                inputQ.className = "form-control dynamic-input mb-4 float-left col-2 inputQ";
                inputQ.placeholder = "Nhập số lượng!"
                inputQ.name = values1[i] + " " + values2[j] + "q"
                inputQ.style = "padding: 18px !important"

                var inputC = document.createElement("input");
                inputC.type = "number";
                inputC.className = "form-control dynamic-input mb-4 float-left col-2 inputC";
                inputC.placeholder = "Nhập giá nhập!"
                inputC.name = values1[i] + " " + values2[j] + "c"
                inputC.style = "padding: 18px !important"

                var inputP = document.createElement("input");
                inputP.type = "number";
                inputP.className = "form-control dynamic-input mb-4 float-left col-2 inputP";
                inputP.placeholder = "Nhập giá bán!"
                inputP.name = values1[i] + " " + values2[j] + "p"
                inputP.style = "padding: 18px !important"

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
    document.getElementById("check-button").addEventListener("click", checkValues);

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
