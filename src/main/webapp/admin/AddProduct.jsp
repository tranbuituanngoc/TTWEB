<%--suppress ALL --%>
<%@ page import="model.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ProductColor" %>
<%@ page import="java.util.List" %>
<%@ page import="service.ProductColorService" %>
<%@ page import="model.ProductSize" %>
<%@ page import="service.ProductSizeService" %>
<%@ page import="model.ProductCategory" %>
<%@ page import="service.ProductCategoryService" %>
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
        thành viên</title>
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
</head>

<body>
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
                                <%
                                    List<ProductColor> colors = ProductColorService.getAll();
                                    List<ProductSize> sizes = ProductSizeService.getAll();
                                    List<ProductCategory> categories = ProductCategoryService.getAll();
                                %>
                                <div class="control-group">
                                    <label class="control-label" for="type">Loại gạch</label>
                                    <div class="controls">
                                        <select id="type" class="chzn-select" name="type" style="width:49%;">
                                            <%
                                                for (ProductCategory c : categories) {
                                            %>
                                            <option value="<%=c.getDescription()%>">
                                                <%=c.getDescription()%>
                                                <%
                                                    //                                                    if()
                                                %>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="sizeProduct">Kích thước </label>
                                    <div class="controls">
                                        <select id="sizeProduct" class="chzn-select" name="sizeProduct"
                                                style="width:49% ;" multiple>
                                            <%--                                            <c:forEach items="${listSize}" var="item">--%>
                                            <%--                                                <option value="${item.description}">--%>
                                            <%--                                                        ${item.description}--%>
                                            <%--                                                    <c:if test="${param.type eq {item.description}}">selected="selected"</c:if>--%>
                                            <%--                                                </option>--%>
                                            <%--                                            </c:forEach>--%>
                                            <%
                                                for (ProductSize s : sizes) {
                                            %>
                                            <option value="<%=s.getDescription()%>">
                                                <%=s.getDescription()%>
                                                <%
                                                    //                                                    if()
                                                %>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="colorProduct">Màu sắc </label>
                                    <div class="controls">
                                        <select id="colorProduct" class="chzn-select" name="colorProduct"
                                                style="width:49% ;" multiple>
                                            <%--                                            <c:forEach items="${listColor}" var="item">--%>
                                            <%--                                                <option value="${item.description}">--%>
                                            <%--                                                        ${item.description}--%>
                                            <%--                                                    <c:if test="${param.type eq {item.description}}">selected="selected"</c:if>--%>
                                            <%--                                                </option>--%>
                                            <%--                                            </c:forEach>--%>
                                            <%
                                                for (ProductColor co : colors) {
                                            %>
                                            <option value="<%=co.getDescription()%>">
                                                <%=co.getDescription()%>
                                                <%

                                                    //                                                    if ()
                                                %>
                                            </option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <%--                                <div class="control-group">--%>
                                <%--                                    <label class="control-label" for="quantity">Số lượng (*) </label>--%>
                                <%--                                    <div class="controls">--%>
                                <%--                                        <input type="number" name="quantity" class="span6" id="quantity"--%>
                                <%--                                               placeholder="Nhập số lượng"--%>
                                <%--                                               value="<%=request.getParameter("quantity")==null? "":request.getParameter("quantity")%>">--%>
                                <%--                                    </div>--%>
                                <%--                                    <div class="controls"><p id="err-quantity"></p></div>--%>
                                <%--                                    <div class="controls" id="error_quantity"></div>--%>
                                <%--                                </div>--%>
                                <div class="clearfix"></div>
                                <div id="form-container"></div>
                                <div class="clearfix"></div>
                                <div class="control-group">
                                    <label class="control-label" for="cost">Giá nhập (*) </label>
                                    <div class="controls">
                                        <input type="number" name="cost" class="span6" id="cost"
                                               placeholder="Nhập giá nhập"
                                               value="<%=request.getParameter("cost")==null? "":request.getParameter("cost")%>">
                                    </div>
                                    <div class="controls" id="error_cost"></div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="price">Giá bán (*) </label>
                                    <div class="controls">
                                        <input type="number" name="price" class="span6" id="price"
                                               placeholder="Nhập giá tiền"
                                               value="<%=request.getParameter("price")==null? "":request.getParameter("price")%>">
                                    </div>
                                    <div class="controls" id="error_price"></div>
                                </div>

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
                                    <div class="controls">
                                        <input type="file" name="thumbnail" class="span6" id="thumbnail"
                                               accept="image/*"
                                               placeholder="Nhập link ảnh gạch"
                                               value="<%=request.getParameter("thumbnail")==null? "":request.getParameter("thumbnail")%>">
                                    </div>
                                    <div class="controls" id="error_thumbnail"></div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="images">Chọn ảnh cho sản phẩm(*) </label>
                                    <div class="controls">
                                        <input type="file" name="images" class="span6" id="images" accept="image/*"
                                               value="<%=request.getParameter("images")==null? "":request.getParameter("images")%>"
                                               multiple>
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
                                    <button type="submit" id="check-button" onclick="checkAddProduct()"
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

    function generateForm() {
        var select1 = document.getElementById("sizeProduct");
        var select2 = document.getElementById("colorProduct");
        var values1 = getSelectedValues(select1);
        var values2 = getSelectedValues(select2);
        var container = document.getElementById("form-container");
        container.innerHTML = "";
        // var div = document.createElement("div");
        // div.className = "w-100"
        // container.appendChild(div);
        for (var i = 0; i < values1.length; i++) {
            for (var j = 0; j < values2.length; j++) {
                var div = document.createElement("div");
                var label = document.createElement("label");
                label.className = "ml-5 h5 bold float-left  col-2 input-group-text";
                label.innerHTML = values1[i] + ", " + values2[j] + ":";
                div.appendChild(label);

                var input = document.createElement("input");
                input.type = "number";
                input.className = "form-control dynamic-input mb-4 float-left col-3 ";
                input.name = values1[i] + " " + values2[j]
                input.style = "padding: 18px !important"
                // input.onkeyup = checkValues;
                div.appendChild(input);
                container.appendChild(div)
            }
        }
    }

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

    function checkValues() {
        var quantity = document.getElementById("quantity").value;
        var dynamicInputs = document.getElementsByClassName("dynamic-input");
        var sum = 0;
        for (var i = 0; i < dynamicInputs.length; i++) {
            sum += parseInt(dynamicInputs[i].value) || 0;
        }
        var resultElement = document.getElementById("err-quantity");
        if (sum != quantity) {
            resultElement.innerHTML = "Tổng các giá trị nhập vào khác với số lượng sản phẩm nhập vào!";
            resultElement.style.color = "red";
            return false;
        } else {
            resultElement.innerHTML = "";
            return true;
        }
    }

    function checkNullValue() {
        error = 0;
        productname = document.getElementById('productname').value;
        price = document.getElementById('price').value;
        cost = document.getElementById('cost').value;
        quantity = document.getElementById('quantity').value;
        description = document.getElementById('description').value;
        var images = document.getElementById('images');
        var thumbnail = document.getElementById('thumbnail');

        error_productname = document.getElementById('error_productname');
        error_price = document.getElementById('error_price');
        error_cost = document.getElementById('error_cost');
        error_quantity = document.getElementById('error_quantity');
        error_description = document.getElementById('error_description');
        error_images = document.getElementById('error_images');
        error_thumbnail = document.getElementById('error_thumbnail');

        if (productname.length == 0) {
            error += 1;
            error_productname.innerHTML = '<span class="required">Vui lòng không để trống phần Tên sản phẩm</span>';
        } else {
            error_productname.innerHTML = ''
        }

        if (price.length == 0) {
            error += 1;
            error_price.innerHTML = '<span class="required">Vui lòng không để trống phần giá bán sản phẩm</span>';
        } else {
            error_price.innerHTML = ''
        }
        if (cost.length == 0) {
            error += 1;
            error_cost.innerHTML = '<span class="required">Vui lòng không để trống phần giá nhập sản phẩm</span>';
        } else {
            error_cost.innerHTML = ''
        }

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

        if (thumbnail.files.length === 0) {
            error += 1;
            error_thumbnail.innerHTML = '<span class="required">Vui lòng chọn Thumbnail cho sản phẩm sản phẩm</span>';
        } else {
            error_thumbnail.innerHTML = ''
        }
        if (images.files.length === 0) {
            error += 1;
            error_images.innerHTML = '<span class="required">Vui lòng chọn hình ảnh cho sản phẩm cho sản phẩm sản phẩm</span>';
        } else {
            error_images.innerHTML = ''
        }
        if (error == 0) {
            // signupform = document.getElementById('signupform');
            // signupform.submit;
            return true;
        } else return false;
    }

    function checkAddProduct() {
        if (checkNullValue() == false || checkValues() == false) {
            return false;
        } else {
            return true;
        }
    }

    document.getElementById("sizeProduct").addEventListener("change", generateForm);
    document.getElementById("colorProduct").addEventListener("change", generateForm);
    document.getElementById("check-button").addEventListener("click", checkValues);

</script>
</body>

</html>
