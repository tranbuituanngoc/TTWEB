<%@ page import="model.User" %>
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
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Enlink - Admin Dashboard Template</title>

    <!-- Favicon -->
    <link rel="shortcut icon" href="admin/assets/images/logo/favicon.png">

    <!-- page css -->
    <link href="admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">

    <!-- Core css -->
    <link href="admin/assets/css/app.min.css" rel="stylesheet">

</head>
<fmt:setLocale value="vi_VN"/>
<fmt:setBundle basename="javax.servlet.jsp.jstl.fmt.LocalizationContext"/>

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
                <div class="row">
                    <div class="col-lg-5">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <p class="m-b-0 text-muted">Doanh Thu</p>
                                                <h2 class="m-b-0"><fmt:formatNumber type="currency"
                                                                                    value="${revalue}"/></h2>
                                            </div>
                                        </div>
                                        <div class="m-t-40">
                                            <div class="d-flex justify-content-between">
                                                <div class="d-flex align-items-center">
                                                    <span class="badge badge-primary badge-dot m-r-10"></span>
                                                    <span class="text-gray font-weight-semibold font-size-13"> Mục Tiêu Tháng</span>
                                                </div>
                                                <span class="text-dark font-weight-semibold font-size-13"><fmt:formatNumber
                                                        value="${revalue * 100 / 20000000}" pattern="0.000"/>% </span>
                                            </div>
                                            <div class="progress progress-sm w-100 m-b-0 m-t-10">
                                                <div class="progress-bar bg-primary"
                                                     style="width:<fmt:formatNumber value="${revalue * 100 / 20000000}"
                                                                                    pattern="0.000"/>%"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <p class="m-b-0 text-muted">Lợi Nhuận Gộp</p>
                                                <h2 class="m-b-0"><fmt:formatNumber type="currency"
                                                                                    value="${sales}"/></h2>
                                            </div>
                                        </div>
                                        <div class="m-t-40">
                                            <div class="d-flex justify-content-between">
                                                <div class="d-flex align-items-center">
                                                    <span class="badge badge-success badge-dot m-r-10"></span>
                                                    <span class="text-gray font-weight-semibold font-size-13"> Mục Tiêu Tháng</span>
                                                </div>
                                                <span class="text-dark font-weight-semibold font-size-13"><fmt:formatNumber
                                                        value="${sales * 100 / 15000000}" pattern="0.000"/>%</span>
                                            </div>
                                            <div class="progress progress-sm w-100 m-b-0 m-t-10">
                                                <div class="progress-bar bg-success"
                                                     style="width: <fmt:formatNumber value="${sales * 100 / 15000000}"
                                                                                     pattern="0.000"/>%"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <p class="m-b-0 text-muted">Đơn Hàng</p>
                                                <h2 class="m-b-0">${orderInMonth}</h2>
                                            </div>
                                        </div>
                                        <div class="m-t-40">
                                            <div class="d-flex justify-content-between">
                                                <div class="d-flex align-items-center">
                                                    <span class="badge badge-warning badge-dot m-r-10"></span>
                                                    <span class="text-gray font-weight-semibold font-size-13"> Mục Tiêu Tháng</span>
                                                </div>

                                                <span class="text-dark font-weight-semibold font-size-13"><fmt:formatNumber
                                                        value="${orderInMonth * 100 / 2000}" pattern="0.000"/>% </span>
                                            </div>
                                            <div class="progress progress-sm w-100 m-b-0 m-t-10">
                                                <div class="progress-bar bg-warning" style="width: <fmt:formatNumber
                                                        value="${orderInMonth * 100 / 2000}" pattern="0.000"/>%"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="d-flex justify-content-between align-items-center">
                                            <div>
                                                <p class="m-b-0 text-muted">Người Dùng</p>
                                                <h2 class="m-b-0">${regisInMonth}</h2>
                                            </div>
                                        </div>
                                        <div class="m-t-40">
                                            <div class="d-flex justify-content-between">
                                                <div class="d-flex align-items-center">
                                                    <span class="badge badge-secondary badge-dot m-r-10"></span>
                                                    <span class="text-gray font-weight-semibold font-size-13"> Mục Tiêu Tháng</span>
                                                </div>
                                                <span class="text-dark font-weight-semibold font-size-13"><fmt:formatNumber
                                                        value="${regisInMonth * 100 / 150}" pattern="0.000"/>% </span>
                                            </div>
                                            <div class="progress progress-sm w-100 m-b-0 m-t-10">
                                                <div class="progress-bar bg-secondary"
                                                     style="width: <fmt:formatNumber value="${regisInMonth * 100 / 150}"
                                                                                     pattern="0.000"/>%"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-7">
                        <div class="card">
                            <div class="card-body" style="height: 387px;">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5>Thống Kê Lợi Nhuận Theo Tháng</h5>
                                </div>
                                <div>
                                    <div class="d-inline-block m-r-30">
                                    </div>
                                    <div class="d-inline-block">
                                    </div>
                                </div>
                                <div>
                                    <canvas class="chart" style="height: 140px" id="revalueChart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5>Thống Kê Lợi Nhuận Theo Ngày</h5>
                                </div>
                                <div class="m-t-50" style="height: 342px">
                                    <canvas class="chart" id="revenue-chart"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-body" style="min-height: 470px">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5 class="text-capitalize">Sản phẩm bán chạy</h5>
                                    <div>
                                        <a href="/ListProductAd" class="btn btn-sm btn-default">Tất cả sản phẩm</a>
                                    </div>
                                </div>
                                <div class="m-t-30">
                                    <ul class="list-group list-group-flush">
                                        <c:forEach items="${bestProduct}" var="entry">
                                            <li class="list-group-item p-h-0">
                                                <div class="d-flex align-items-center justify-content-between">
                                                    <div class="d-flex">
                                                        <div class="avatar avatar-image m-r-15">
                                                            <img src="../${ProductImageService.getThumbProduct(entry.key.productID)}"
                                                                 alt="">
                                                        </div>
                                                        <div>
                                                            <h6 class="m-b-0">
                                                                <p class="text-dark">${entry.key.productName} </p>
                                                            </h6>
                                                            <span class="text-muted font-size-13">${CategoryService.getCateByID(entry.key.category)}</span>
                                                        </div>
                                                    </div>
                                                    <span class="badge badge-pill badge-cyan font-size-12">
                                                        <span class="font-weight-semibold m-l-5 m-b-20">+ ${entry.value} sản phẩm</span>
                                                    </span>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="text-capitalize">Doanh thu theo danh mục</h5>
                                <div class="m-v-45 text-center" style="height: 220px">
                                    <canvas class="chart" id="customer-chart"></canvas>
                                </div>
                                <div id="legend-container" style="min-height: 160px"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8">
                        <div class="card">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center">
                                    <h5 class="text-capitalize">đơn hàng gần đây</h5>
                                    <div>
                                        <a href="ListOrder" class="btn btn-sm btn-default">Xem Tất Cả</a>
                                    </div>
                                </div>
                                <div class="m-t-30">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tên Khách Hàng</th>
                                                <th>Ngày Đặt</th>
                                                <th>Tổng Đơn Hàng</th>
                                                <th>Tình Trạng Đơn Hàng</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${recentOrder}" var="o">
                                                <tr>
                                                    <td>${o.orderID}</td>
                                                    <td>
                                                        <div class="d-flex align-items-center">
                                                            <div class="d-flex align-items-center">
                                                                <h6 class="m-l-10 m-b-0">${UserService.getNameUser(o.userID)}</h6>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td><fmt:parseDate value="${o.order_date}" var="parsedDate"
                                                                       pattern="yyyy-MM-dd HH:mm:ss.S"/>
                                                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy"
                                                                        var="formattedDate"/>
                                                            ${formattedDate}</td>
                                                    <td>${o.totalPrice}</td>
                                                    <td id="orderstatus">
                                                        <c:if test="${o.status ==1}">
                                                            <div class="d-flex align-items-center">
                                                                <div class="badge badge-success badge-dot m-r-10"></div>
                                                                <div>Đã xác nhận</div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${o.status ==0}">
                                                            <div class="d-flex align-items-center">
                                                                <div class="badge badge-primary badge-dot m-r-10"></div>
                                                                <div>Chưa xác nhận</div>
                                                            </div>
                                                        </c:if>
                                                        <c:if test="${o.status ==2}">
                                                            <div class="d-flex align-items-center">
                                                                <div class="badge badge-danger badge-dot m-r-10"></div>
                                                                <div>Đã hủy</div>
                                                            </div>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>

                                            </tbody>
                                        </table>
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
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="assets/js/pages/chroma.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chroma-js@2.1.1/chroma.min.js"></script>


<script>
    <%--    Thoongs kê doanh thu theo tháng--%>
    var ctx = document.getElementById('revalueChart').getContext('2d');
    var salesData =
    ${jsonSales6M}
    var revalueLabels = Object.keys(salesData);
    var revalueData = Object.values(salesData);
    var revalueChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: revalueLabels,
            datasets: [{
                label: 'Doanh Thu',
                data: revalueData,
                backgroundColor: '#3f87f5',
                borderWidth: 0
            }]
        },
        options: {
            scaleShowVerticalLines: false,
            responsive: true,
            scales: {
                x: {

                    grid: {
                        display: false // Tắt hiển thị grid (border) của trục x
                    },
                    ticks: {
                        fontSize: 13,
                        padding: 20,
                        display: true // Hiển thị phần chữ trên trục x
                    }
                },
                y: {
                    display: true, // Hiển thị trục y
                    gridLines: {
                        drawBorder: false,
                        offsetGridLines: false,
                        drawTicks: false,
                        borderDash: [3, 4],
                        zeroLineWidth: 1,
                        zeroLineBorderDash: [3, 4]
                    },
                    ticks: {
                        beginAtZero: false,
                        fontSize: 13,
                        padding: 10
                    }
                }
            },
            plugins: {
                legend: {
                    display: false // Tắt hiển thị chú thích
                }
            },
            layout: {
                padding: {
                    left: 10, // Khoảng cách cột với mép trái của biểu đồ
                    right: 10 // Khoảng cách cột với mép phải của biểu đồ
                }
            },
            interaction: {
                intersect: false // Tắt tương tác với cột
            }
        }

    });

    //Thống kê doanh thu theo ngày

    const revenueChart = document.getElementById("revenue-chart");
    const revenueChartCtx = revenueChart.getContext('2d');
    revenueChart.height = 120;
    var revenueDataT =
    ${jsonSales10D}
    var revenueLabels = Object.keys(revenueDataT);
    var revenueData = Object.values(revenueDataT);
    const revenueChartConfig = new Chart(revenueChartCtx, {
        type: 'line',
        data: {
            labels: revenueLabels,
            datasets: [{
                backgroundColor: 'transparent',
                borderColor: '#05c9a7',
                pointBackgroundColor: '#05c9a7',
                pointBorderColor: '#FFFFFF',
                pointHoverBackgroundColor: '#00FFFF33',
                pointHoverBorderColor: '#00FFFF33',
                data: revenueData,
                label: 'Series A',
                lineTension: 0.4 // Điều chỉnh độ cong của đường line tại đây
            }]
        },
        options: {
            maintainAspectRatio: false,
            responsive: true,
            hover: {
                mode: 'nearest',
                intersect: true
            },
            legend: {
                display: false
            },
            tooltips: {
                mode: 'index'
            },
            plugins: {
                legend: {
                    display: false // Tắt hiển thị chú thích
                }
            },
            scales: {
                x: {
                    grid: {
                        display: false,
                    },
                    ticks: {
                        display: true,
                        color: '#CCCCCC',
                        font: {
                            size: 13
                        },
                        padding: 10
                    }
                },
                y: {
                    grid: {
                        drawBorder: false,
                        drawTicks: false,
                        lineWidth: 1,
                        borderDashOffset: 3,
                        zeroLineWidth: 1,
                        zeroLineBorderDash: [3, 4]
                    },
                    ticks: {
                        display: true,
                        max: 100,
                        color: '#CCCCCC',
                        font: {
                            size: 13
                        },
                        padding: 10
                    }
                }
            }
        }
    });

    function generateCustomLegend(chart) {
        const legendContainer = document.createElement('div');
        legendContainer.className = 'col-md-8 m-h-auto';

        chart.data.labels.forEach((label, index) => {
            const legendItemContainer = document.createElement('div');
            legendItemContainer.className = 'd-flex justify-content-between align-items-center m-b-20';

            const labelContainer = document.createElement('p');
            labelContainer.className = 'm-b-0 d-flex align-items-center';

            const dot = document.createElement('span');
            dot.className = 'badge badge-dot m-r-10';
            dot.style.backgroundColor = chart.data.datasets[0].backgroundColor[index];

            const labelText = document.createElement('span');
            labelText.textContent = label;

            labelContainer.appendChild(dot);
            labelContainer.appendChild(labelText);

            const value = document.createElement('h5');
            value.className = 'm-b-0';
            value.textContent = chart.data.datasets[0].data[index];

            legendItemContainer.appendChild(labelContainer);
            legendItemContainer.appendChild(value);

            legendContainer.appendChild(legendItemContainer);
        });

        return legendContainer;
    }

    const customerChart = document.getElementById('customer-chart');
    const customerChartCtx = customerChart.getContext('2d');
    customerChart.height = 292;

    const colors = ['#eb2f96', '#eb2f96', '#de4436', '#de4436', '#fa541c', '#fa541c', '#fa8c16', '#fa8c16', '#ffc107', '#ffc107', '#a0d911', '#a0d911', '#52c41a', '#52c41a', '#05c9a7', '#05c9a7', '#3f87f5', '#3f87f5', '#2f54eb', '#2f54eb', '#886cff', '#886cff'];

    const chosenColors = [];
    while (chosenColors.length < 3) {
        const randomColor = colors[Math.floor(Math.random() * colors.length)];
        const isTooClose = chosenColors.some((chosenColor) => {
            const colorDistance = chroma.distance(randomColor, chosenColor);
            return colorDistance < 0.7;
        });
        if (!isTooClose) {
            chosenColors.push(randomColor);
        }
    }
    var cateData =
    ${jsonBestCate}
    var bestCateLabels = Object.keys(cateData);
    var bestCateData = Object.values(cateData);
    const customerChartConfig = new Chart(customerChartCtx, {
        type: 'doughnut',
        data: {
            labels: bestCateLabels,
            datasets: [{
                fill: true,
                backgroundColor: chosenColors,
                pointBackgroundColor: chosenColors,
                data: bestCateData
            }]
        },
        options: {
            plugins: {
                legend: {
                    display: false // Tắt hiển thị chú thích
                }
            },
            cutoutPercentage: 80,
            maintainAspectRatio: false
        }
    });

    const legendContainer = generateCustomLegend(customerChartConfig);
    document.getElementById('legend-container').appendChild(legendContainer);

</script>
<!-- Core Vendors JS -->
<script src="admin/assets/js/vendors.min.js"></script>

<!-- Core JS -->
<script src="admin/assets/js/app.min.js"></script>

</body>

</html>