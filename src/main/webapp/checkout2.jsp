<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" class="flexbox">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="shortcut icon" href="#" type="image/png" />
    <title>Thanh toán || Truemart Gạch men cao cấp</title>
    <link rel="stylesheet" href="./css/checkout.css" />
    <meta name="description" content="Gạch Men TrueMart - Thanh toán đơn hàng" />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <link
      href="./css/check_out.css"
      rel="stylesheet"
      type="text/css"
      media="all"
    />
    <script
      src="js\jquery.min.js"
      type="text/javascript"
    ></script>
    <script
      src="js\api.jquery.js"
      type="text/javascript"
    ></script>
    <script
      src="js\jquery.validate.js"
      type="text/javascript"
    ></script>

    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, maximum-scale=2, user-scalable=no"
    />
    <script>
      var toggleShowOrderSummary = false;
      $(document).ready(function () {
        $("body").on("click", ".order-summary-toggle", function () {
          toggleShowOrderSummary = !toggleShowOrderSummary;
          if (toggleShowOrderSummary) {
            $(".order-summary-toggle")
                    .removeClass("order-summary-toggle-hide")
                    .addClass("order-summary-toggle-show");

            $('.sidebar:not(.sidebar-second) .sidebar-content .order-summary')
                    .removeClass("order-summary-is-collapsed")
                    .addClass("order-summary-is-expanded");

            $(".sidebar.sidebar-second .sidebar-content .order-summary")
                    .removeClass("order-summary-is-expanded")
                    .addClass("order-summary-is-collapsed");
          } else {
            $(".order-summary-toggle")
                    .removeClass("order-summary-toggle-show")
                    .addClass("order-summary-toggle-hide");

            $('.sidebar:not(.sidebar-second) .sidebar-content .order-summary')
                    .removeClass("order-summary-is-expanded")
                    .addClass("order-summary-is-collapsed");

            $(".sidebar.sidebar-second .sidebar-content .order-summary")
                    .removeClass("order-summary-is-collapsed")
                    .addClass("order-summary-is-expanded");
          }
        });
      });
    </script>
  </head>
  <body>
    <noscript
      ><iframe
        src="./style/ns.html"
        height="0"
        width="0"
        eivab926v=""
        style="display: none; visibility: hidden"
      ></iframe
    ></noscript>
    <c:if test="${errorQuantity}">
      <script type="text/javascript">
        swal({
          title: "Error!",
          text: "Số lượng sản phẩm không đủ",
          icon: "error",
          button: "Ok",
        });
      </script>
    </c:if>
    <c:if test="${messageResponseVoucher == 'fail'}">
      <script type="text/javascript">
        swal({
          title: "Error!",
          text: "Đơn hàng chưa thỏa điều kiện để nhập voucher",
          icon: "error",
          button: "Ok",
        });
      </script>
    </c:if>
    <c:if test="${userInvalid}">
      <script type="text/javascript">
        swal({
          title: "Error!",
          text: "Vui lòng đăng nhập để thực hiện chức năng này",
          icon: "error",
          button: "Ok",
        });
      </script>
    </c:if>
    <c:if test="${messageResponseVoucher == 'success'}">
      <script type="text/javascript">
        swal({
          title: "Success!",
          text: "Sử dụng Voucher thành công",
          icon: "success",
          button: "Ok",
        });
      </script>
    </c:if>

    <%
      request.getSession().removeAttribute("errorQuantity");
      request.getSession().removeAttribute("messageResponseVoucher");
      request.getSession().removeAttribute("userInvalid");
    %>
    <input id="reloadValue" type="hidden" name="reloadValue" />
    <input id="is_vietnam" type="hidden" value="true" />
    <input id="is_vietnam_location" type="hidden" value="true" />

    <div class="banner">
      <div class="wrap">
        <a href="Home" class="logo">
          <h1 class="logo-text">Gạch men TrueMart</h1>
        </a>
      </div>
    </div>

    <button class="order-summary-toggle order-summary-toggle-hide">
      <div class="wrap">
        <div class="order-summary-toggle-inner">
          <div class="order-summary-toggle-icon-wrapper">
            <svg
              width="20"
              height="19"
              xmlns="http://www.w3.org/2000/svg"
              class="order-summary-toggle-icon"
            >
              <path
                d="M17.178 13.088H5.453c-.454 0-.91-.364-.91-.818L3.727 1.818H0V0h4.544c.455 0 .91.364.91.818l.09 1.272h13.45c.274 0 .547.09.73.364.18.182.27.454.18.727l-1.817 9.18c-.09.455-.455.728-.91.728zM6.27 11.27h10.09l1.454-7.362H5.634l.637 7.362zm.092 7.715c1.004 0 1.818-.813 1.818-1.817s-.814-1.818-1.818-1.818-1.818.814-1.818 1.818.814 1.817 1.818 1.817zm9.18 0c1.004 0 1.817-.813 1.817-1.817s-.814-1.818-1.818-1.818-1.818.814-1.818 1.818.814 1.817 1.818 1.817z"
              ></path>
            </svg>
          </div>
          <div class="order-summary-toggle-text order-summary-toggle-text-show">
            <span>Hiển thị thông tin đơn hàng</span>
            <svg
              width="11"
              height="6"
              xmlns="http://www.w3.org/2000/svg"
              class="order-summary-toggle-dropdown"
              fill="#000"
            >
              <path
                d="M.504 1.813l4.358 3.845.496.438.496-.438 4.642-4.096L9.504.438 4.862 4.534h.992L1.496.69.504 1.812z"
              ></path>
            </svg>
          </div>
          <div class="order-summary-toggle-text order-summary-toggle-text-hide">
            <span>Ẩn thông tin đơn hàng</span>
            <svg
              width="11"
              height="7"
              xmlns="http://www.w3.org/2000/svg"
              class="order-summary-toggle-dropdown"
              fill="#000"
            >
              <path
                d="M6.138.876L5.642.438l-.496.438L.504 4.972l.992 1.124L6.138 2l-.496.436 3.862 3.408.992-1.122L6.138.876z"
              ></path>
            </svg>
          </div>
          <div
            class="order-summary-toggle-total-recap"
            data-checkout-payment-due-target="${cartUser.getTotalValue()}"
          >
            <span class="total-recap-final-price"><fmt:formatNumber type="currency"
                                              currencySymbol=""
                                              minFractionDigits="0"
                                              value="${cartUser.getTotalValue()}"/>₫</span>
          </div>
        </div>
      </div>
    </button>
    <div class="content content-second">
      <div class="wrap">
        <div class="sidebar sidebar-second">
          <div class="sidebar-content">
            <div class="order-summary">
              <div class="order-summary-sections">
                <div
                  class="order-summary-section order-summary-section-discount"
                  data-order-summary-section="discount"
                >
                  <form accept-charset="UTF-8" method="post" action="form_discount_add">
                    <input name="utf8" type="hidden" value="✓" />
                    <div class="fieldset">
                      <div class="field">
                        <div class="field-input-btn-wrapper">
                          <div class="field-input-wrapper">
                            <label class="field-label" for="discount.code"
                              >Mã giảm giá</label
                            >
                            <input
                              placeholder="Mã giảm giá"
                              class="field-input"
                              data-discount-field="true"
                              autocomplete="false"
                              autocapitalize="off"
                              spellcheck="false"
                              size="30"
                              type="text"
                              name="discount.code"
                              value=""
                            />
                          </div>
                          <button
                            type="submit"
                            class="field-input-btn btn btn-default btn-disabled"
                          >
                            <span class="btn-content">Sử dụng</span>
                            <i class="btn-spinner icon icon-button-spinner"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>

                <div
                  class="order-summary-section order-summary-section-display-discount"
                  data-order-summary-section="discount-display"
                >
                  <div>
                    <div class="hrv-discount-choose-coupons">
                      <div>
                        <svg
                          width="15"
                          height="10"
                          viewBox="0 0 18 14"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            fill-rule="evenodd"
                            clip-rule="evenodd"
                            d="M17.3337 5.3335V2.00016C17.3337 1.07516 16.5837 0.333496 15.667 0.333496H2.33366C1.41699 0.333496 0.675326 1.07516 0.675326 2.00016V5.3335C1.59199 5.3335 2.33366 6.0835 2.33366 7.00016C2.33366 7.91683 1.59199 8.66683 0.666992 8.66683V12.0002C0.666992 12.9168 1.41699 13.6668 2.33366 13.6668H15.667C16.5837 13.6668 17.3337 12.9168 17.3337 12.0002V8.66683C16.417 8.66683 15.667 7.91683 15.667 7.00016C15.667 6.0835 16.417 5.3335 17.3337 5.3335ZM15.667 4.11683C14.6753 4.69183 14.0003 5.77516 14.0003 7.00016C14.0003 8.22516 14.6753 9.3085 15.667 9.8835V12.0002H2.33366V9.8835C3.32533 9.3085 4.00033 8.22516 4.00033 7.00016C4.00033 5.76683 3.33366 4.69183 2.34199 4.11683L2.33366 2.00016H15.667V4.11683ZM9.83366 9.50016H8.16699V11.1668H9.83366V9.50016ZM8.16699 6.16683H9.83366V7.8335H8.16699V6.16683ZM9.83366 2.8335H8.16699V4.50016H9.83366V2.8335Z"
                            fill="#318DBB"
                          ></path>
                        </svg>
                        <span>Xem thêm mã giảm giá</span>
                      </div>
                      <div>
                        <span
                          ><span data-code="Truemart500K">Giảm 500,000₫</span></span
                        >

                        <span
                          ><span data-code="Truemart50K">Giảm 50,000₫</span></span
                        >

                        <span
                          ><span data-code="Truemart300K">Giảm 300,000₫</span></span
                        >

                        <span
                          ><span data-code="Truemart200K">Giảm 200,000₫</span></span
                        >

                        <span
                          ><span data-code="Truemart100K">Giảm 100,000₫</span></span
                        >
                      </div>
                    </div>
                  </div>
                  <div class="hrv-coupons-popup">
                    <div class="hrv-title-coupons-popup">
                      <p>Chọn giảm giá</p>
                      <div class="hrv-coupons-close-popup">
                        <svg
                          width="18"
                          height="18"
                          viewBox="0 0 18 18"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            d="M17.1663 2.4785L15.5213 0.833496L8.99968 7.35516L2.47801 0.833496L0.833008 2.4785L7.35468 9.00016L0.833008 15.5218L2.47801 17.1668L8.99968 10.6452L15.5213 17.1668L17.1663 15.5218L10.6447 9.00016L17.1663 2.4785Z"
                            fill="#424242"
                          ></path>
                        </svg>
                      </div>
                    </div>
                    <div class="hrv-content-coupons-code">
                      <h3 class="coupon_heading">Mã giảm giá của shop</h3>
                      <div class="hrv-discount-code-web"></div>
                      <div class="hrv-discount-code-external"></div>
                    </div>
                  </div>
                  <div class="hrv-coupons-popup-site-overlay"></div>
                  <span class="note_promotion_checkout"
                    >Lưu ý: Không áp dụng đồng thời 2 chương trình khuyến
                    mãi</span
                  >
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="content">
      <div class="wrap">
        <div class="sidebar">
          <div class="sidebar-content">
            <div class="order-summary order-summary-is-collapsed">
              <h2 class="visually-hidden">Thông tin đơn hàng</h2>
              <div class="order-summary-sections">
                <div
                  class="order-summary-section order-summary-section-product-list"
                  data-order-summary-section="line-items"
                >
                  <table class="product-table">
                    <thead>
                      <tr>
                        <th scope="col">
                          <span class="visually-hidden">Hình ảnh</span>
                        </th>
                        <th scope="col">
                          <span class="visually-hidden">Mô tả</span>
                        </th>
                        <th scope="col">
                          <span class="visually-hidden">Số lượng</span>
                        </th>
                        <th scope="col">
                          <span class="visually-hidden">Giá</span>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${cartUser.getCart()}" var="cart" varStatus="status">
                        <tr class="product" data-product-id="1030631225" data-variant-id="1095913802">
                          <c:set value="${cart.product}" var="product"></c:set>
                          <c:set value="${cartUser.getValue()[status.index]}" var="quantity"></c:set>
                          <td class="product-image">
                            <div class="product-thumbnail">
                              <div class="product-thumbnail-wrapper">
                                <img class="product-thumbnail-image" alt="${product.productName}"
                                     src="${product.image[1].image}"
                                />
                              </div>
                              <span class="product-thumbnail-quantity" aria-hidden="true">${quantity}</span>
                            </div>
                          </td>
                          <td class="product-description">
                            <span class="product-description-name order-summary-emphasis">${product.description}</span>
                            <span class="product-description-variant order-summary-small-text">Màu ${cart.color.descrip} / ${cart.size.width}x${cart.size.length}</span>
                          </td>
                          <td class="product-quantity visually-hidden">1</td>
                          <td class="product-price">
                            <span class="order-summary-emphasis">
                              <fmt:formatNumber type="currency" currencySymbol=""
                              minFractionDigits="0" value="${product.priceAfterSale*quantity}"/>
                            </span>
                          </td>
                        </tr>
                      </c:forEach>

                    </tbody>
                  </table>
                </div>

                <div
                  class="order-summary-section order-summary-section-discount"
                  data-order-summary-section="discount"
                >
                  <form id="form_discount_add" accept-charset="UTF-8" method="post" action="form_discount_add">
                    <input name="utf8" type="hidden" value="✓" />
                    <div class="fieldset">
                      <div class="field">
                        <div class="field-input-btn-wrapper">
                          <div class="field-input-wrapper">
                            <label class="field-label" for="discount.code"
                              >Mã giảm giá</label
                            >
                            <input
                              placeholder="Mã giảm giá"
                              class="field-input"
                              data-discount-field="true"
                              autocomplete="false"
                              autocapitalize="off"
                              spellcheck="false"
                              size="30"
                              type="text"
                              id="discount.code"
                              name="discount.code"
                              value=""
                            />
                          </div>
                          <button
                            type="submit"
                            class="field-input-btn btn btn-default btn-disabled"
                          >
                            <span class="btn-content">Sử dụng</span>
                            <i class="btn-spinner icon icon-button-spinner"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </form>
                </div>
                <div
                  class="order-summary-section order-summary-section-display-discount"
                  data-order-summary-section="discount-display"
                >
                  <div>
                    <div class="hrv-discount-choose-coupons">
                      <div>
                        <svg
                          width="15"
                          height="10"
                          viewBox="0 0 18 14"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            fill-rule="evenodd"
                            clip-rule="evenodd"
                            d="M17.3337 5.3335V2.00016C17.3337 1.07516 16.5837 0.333496 15.667 0.333496H2.33366C1.41699 0.333496 0.675326 1.07516 0.675326 2.00016V5.3335C1.59199 5.3335 2.33366 6.0835 2.33366 7.00016C2.33366 7.91683 1.59199 8.66683 0.666992 8.66683V12.0002C0.666992 12.9168 1.41699 13.6668 2.33366 13.6668H15.667C16.5837 13.6668 17.3337 12.9168 17.3337 12.0002V8.66683C16.417 8.66683 15.667 7.91683 15.667 7.00016C15.667 6.0835 16.417 5.3335 17.3337 5.3335ZM15.667 4.11683C14.6753 4.69183 14.0003 5.77516 14.0003 7.00016C14.0003 8.22516 14.6753 9.3085 15.667 9.8835V12.0002H2.33366V9.8835C3.32533 9.3085 4.00033 8.22516 4.00033 7.00016C4.00033 5.76683 3.33366 4.69183 2.34199 4.11683L2.33366 2.00016H15.667V4.11683ZM9.83366 9.50016H8.16699V11.1668H9.83366V9.50016ZM8.16699 6.16683H9.83366V7.8335H8.16699V6.16683ZM9.83366 2.8335H8.16699V4.50016H9.83366V2.8335Z"
                            fill="#318DBB"
                          ></path>
                        </svg>
                        <span>Xem thêm mã giảm giá</span>
                      </div>
                      <div id="list_short_coupon">
                        <span><span data-code="Truemart500K">Giảm 500,000₫</span></span>
                        <span
                          ><span data-code="Truemart50K">Giảm 50,000₫</span></span
                        >
                        <span
                          ><span data-code="Truemart300K">Giảm 300,000₫</span></span
                        >
                        <span
                          ><span data-code="Truemart200K">Giảm 200,000₫</span></span
                        >
                        <span
                          ><span data-code="Truemart100K">Giảm 100,000₫</span></span
                        >
                      </div>
                    </div>
                  </div>
                  <span class="note_promotion_checkout"
                    >Lưu ý: Không áp dụng đồng thời 2 chương trình khuyến
                    mãi</span
                  >
                </div>
                <c:if test="${user == null}">
                  <div
                    class="order-summary-section order-summary-section-redeem redeem-login-section"
                    data-order-summary-section="discount"
                  >
                    <div class="redeem-login">
                      <div class="redeem-login-title">
                        <h2>Khách hàng thân thiết</h2>
                        <i class="btn-redeem-spinner icon-redeem-button-spinner"></i>
                      </div>
                      <div class="redeem-login-btn">
                        <a href="login.jsp">Đăng nhập</a>
                      </div>
                    </div>
                  </div>
                </c:if>
                <div
                  class="order-summary-section order-summary-section-total-lines payment-lines"
                  data-order-summary-section="payment-lines"
                >
                  <table class="total-line-table">
                    <thead>
                      <tr>
                        <th scope="col">
                          <span class="visually-hidden">Mô tả</span>
                        </th>
                        <th scope="col">
                          <span class="visually-hidden">Giá</span>
                        </th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr class="total-line total-line-subtotal">
                        <td class="total-line-name">Tạm tính</td>
                        <td class="total-line-price">
                          <span class="order-summary-emphasis" data-checkout-subtotal-price-target="${cartUser.getTotalValue()}">
                            <fmt:formatNumber type="currency" currencySymbol="" minFractionDigits="0"
                              value="${cartUser.getTotalValue()}"/>₫
                          </span>
                        </td>
                      </tr>
                      <c:if test="${voucher!= null}">

                        <tr class="total-line total-line-reduction">
                          <td class="total-line-name">
                            <span>Mã giảm giá</span>
                            <span class="applied-reduction-code">
                              <svg width="16" height="15" xmlns="http://www.w3.org/2000/svg" class="applied-reduction-code-icon" fill="#CE4549">
                                <path d="M14.476 0H8.76c-.404 0-.792.15-1.078.42L.446 7.207c-.595.558-.595 1.463 0 2.022l5.703 5.35c.296.28.687.42 1.076.42.39 0 .78-.14 1.077-.418l7.25-6.79c.286-.268.447-.632.447-1.01V1.43C16 .64 15.318 0 14.476 0zm-2.62 5.77c-.944 0-1.713-.777-1.713-1.732 0-.954.77-1.73 1.714-1.73.945 0 1.714.776 1.714 1.73 0 .955-.768 1.73-1.713 1.73z"></path>
                              </svg>
                              <span class="applied-reduction-code-information">${voucher.voucherCode}</span>
                            </span>
                          </td>
                          <td class="total-line-price">
                            <span class="order-summary-emphasis"
                                  data-checkout-discount-amount-target="${voucher.discount}">- <fmt:formatNumber type="currency"
                                                                                                                 currencySymbol=""
                                                                                                                 minFractionDigits="0"
                                                                                                                 value="${voucher.discount}"/>₫
                            </span>
                          </td>
                        </tr>
                      </c:if>
                      <tr class="total-line total-line-shipping">
                        <td class="total-line-name">Phí vận chuyển</td>
                        <td class="total-line-price">
                          <span
                            class="order-summary-emphasis"
                            data-checkout-total-shipping-target="0"
                          >
                            —
                          </span>
                        </td>
                      </tr>
                    </tbody>
                    <c:set var="totalAmount" value="${cartUser.getTotalValue()}" />
                    <c:set var="voucherDiscount" value="${voucher != null ? voucher.discount : 0}"/>
                    <c:set var="totalAmount" value="${cartUser.getTotalValue() - voucherDiscount}" />
                    <input type="hidden" name="total-amount" id="total-amount">
                    <tfoot class="total-line-table-footer">
                      <tr class="total-line">
                        <td class="total-line-name payment-due-label">
                          <span class="payment-due-label-total">Tổng cộng</span>
                        </td>
                        <td class="total-line-name payment-due">
                          <span class="payment-due-currency">VND</span>
                          <span class="payment-due-price" data-checkout-payment-due-target="${totalAmount}">
                            <fmt:formatNumber type="currency"
                                              currencySymbol=""
                                              minFractionDigits="0"
                                              value="${totalAmount}"/>₫</span></strong>
                          </span>

                          <span
                            class="checkout_version"
                            display:none=""
                            data_checkout_version="12"
                          >
                          </span>
                        </td>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="main">
          <div class="main-header">
            <a href="Home" class="logo">
                <h1 class="logo-text">Gạch Men TrueMart</h1>
            </a>

            <style>
              a.logo {
                display: block;
              }
              .logo-cus {
                width: 100%;
                padding: 15px 0;
                text-align: left;
              }
              .logo-cus img {
                max-height: 4.2857142857em;
              }

              .logo-text {
                text-align: left;
              }

              @media (max-width: 767px) {
                .banner a {
                  display: block;
                }
              }
            </style>

            <ul class="breadcrumb">
              <li class="breadcrumb-item">
                <a href="Cart">Giỏ hàng</a>
              </li>

              <li class="breadcrumb-item breadcrumb-item-current">
                Thông tin giao hàng
              </li>
            </ul>
          </div>
          <div class="main-content">
            <div
              id="checkout_order_information_changed_error_message"
              class="hidden"
              style="margin-bottom: 15px"
            >
              <p class="field-message field-message-error alert alert-danger">
                <svg
                  x="0px"
                  y="0px"
                  viewBox="0 0 286.054 286.054"
                  style="enable-background: new 0 0 286.054 286.054"
                  xml:space="preserve"
                >
                  <g>
                    <path
                      style="fill: #e2574c"
                      d="M143.027,0C64.04,0,0,64.04,0,143.027c0,78.996,64.04,143.027,143.027,143.027 c78.996,0,143.027-64.022,143.027-143.027C286.054,64.04,222.022,0,143.027,0z M143.027,259.236 c-64.183,0-116.209-52.026-116.209-116.209S78.844,26.818,143.027,26.818s116.209,52.026,116.209,116.209 S207.21,259.236,143.027,259.236z M143.036,62.726c-10.244,0-17.995,5.346-17.995,13.981v79.201c0,8.644,7.75,13.972,17.995,13.972 c9.994,0,17.995-5.551,17.995-13.972V76.707C161.03,68.277,153.03,62.726,143.036,62.726z M143.036,187.723 c-9.842,0-17.852,8.01-17.852,17.86c0,9.833,8.01,17.843,17.852,17.843s17.843-8.01,17.843-17.843 C160.878,195.732,152.878,187.723,143.036,187.723z"
                    ></path>
                  </g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                  <g></g>
                </svg>
                <span> </span>
              </p>
            </div>
            <script>
              $("html, body").animate({ scrollTop: 0 }, "slow");
            </script>
            <form action="CreateOrder" method="GET">
<%--              begin step--%>
            <div class="step">
              <div class="step-sections steps-onepage" step="1">
                <div class="section">
                  <div class="section-header">
                    <h2 class="section-title">Thông tin giao hàng</h2>
                  </div>
                  <div
                    class="section-content section-customer-information no-mb"
                  >
                    <c:if test="${user == null}">
                      <p class="section-content-text">
                        Qúy khách đã có tài khoản?`
                        <a href="#">Đăng nhập</a>
                      </p>
                    </c:if>
                    <div class="fieldset">
                      <div class="field field-required">
                        <div class="field-input-wrapper">
                          <label
                            class="field-label"
                            for="billing_address_full_name"
                            >Họ và tên</label
                          >
                          <input
                            placeholder="Họ và tên"
                            autocapitalize="off"
                            spellcheck="false"
                            class="field-input"
                            size="30"
                            type="text"
                            id="billing_address_full_name"
                            name="billing_address[full_name]"
                            value="${user.fullname}"
                            autocomplete="false"
                          />
                        </div>
                      </div>

                      <div class="field field-required field-two-thirds">
                        <div class="field-input-wrapper">
                          <label class="field-label" for="checkout_user_email"
                            >Email</label
                          >
                          <input
                            autocomplete="false"
                            placeholder="Email"
                            autocapitalize="off"
                            spellcheck="false"
                            class="field-input"
                            size="30"
                            type="email"
                            id="checkout_user_email"
                            name="checkout_user[email]"
                            value="${user.email}"
                          />
                        </div>
                      </div>

                      <div class="field field-required field-third">
                        <div class="field-input-wrapper">
                          <label class="field-label" for="billing_address_phone"
                            >Số điện thoại</label
                          >
                          <input
                            autocomplete="false"
                            placeholder="Số điện thoại"
                            autocapitalize="off"
                            spellcheck="false"
                            class="field-input"
                            size="30"
                            maxlength="15"
                            type="tel"
                            id="billing_address_phone"
                            name="billing_address[phone]"
                            value="${user.phone}"
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="section-content">
                    <div class="fieldset">
                      <form
                        autocomplete="off"
                        id="form_update_shipping_method"
                        class="field default"
                        accept-charset="UTF-8"
                        method="post" action="form_update_shipping_method"
                      >
                        <input name="utf8" type="hidden" value="✓" />
                        <div class="content-box mt0">
                          <div
                            id="form_update_location_customer_shipping"
                            class="order-checkout__loading radio-wrapper content-box-row content-box-row-padding content-box-row-secondary"
                            for="customer_pick_at_location_false"
                          >
                            <div class="order-checkout__loading--box">
                              <div
                                class="order-checkout__loading--circle"
                              ></div>
                            </div>
<%--                            <input type="hidden" name="shippingAddressId" value="${shippingAddress.shippingAddressId}">--%>
                            <div class="field field-required">
                              <div class="field-input-wrapper">
                                <label
                                  class="field-label"
                                  for="billing_address_address1"
                                  >Địa chỉ</label
                                >
                                <input
                                  placeholder="Địa chỉ"
                                  autocapitalize="off"
                                  spellcheck="false"
                                  class="field-input"
                                  size="30"
                                  type="text"
                                  id="billing_address_address1"
                                  name="billing_address[address1]"
                                  value="${shippingAdress.address}"
                                />
                              </div>
                            </div>

                            <input
                              name="selected_customer_shipping_province"
                              type="hidden"
                              id="selected_customer_shipping_province"
                              value="0"
                            />
                            <input
                              name="selected_customer_shipping_district"
                              type="hidden"
                              id="selected_customer_shipping_district"
                              value="0"
                            />
                            <input
                              name="selected_customer_shipping_ward"
                              type="hidden"
                              id="selected_customer_shipping_ward"
                              value="0"
                            />

                            <div
                              class="field field-show-floating-label field-required field-third"
                            >
                              <div
                                class="field-input-wrapper field-input-wrapper-select"
                              >
                                <label
                                  class="field-label"
                                  for="customer_shipping_province"
                                >
                                  Tỉnh/ Thành
                                </label>
                                <select
                                  class="field-input"
                                  id="customer_shipping_province"
                                  name="customer_shipping_province"
                                ></select>
                              </div>
                            </div>

                            <div
                              class="field field-show-floating-label field-required field-third"
                            >
                              <div
                                class="field-input-wrapper field-input-wrapper-select"
                              >
                                <label
                                  class="field-label"
                                  for="customer_shipping_district"
                                  >Quận/ Huyện</label
                                >
                                <select
                                  class="field-input"
                                  id="customer_shipping_district"
                                  name="customer_shipping_district"
                                >
                                  <option data-code="null" value="null">Chọn quận/ huyện</option>
                                </select>
                              </div>
                            </div>

                            <div
                              class="field field-show-floating-label field-required field-third"
                            >
                              <div
                                class="field-input-wrapper field-input-wrapper-select"
                              >
                                <label
                                  class="field-label"
                                  for="customer_shipping_ward"
                                  >Phường/ Xã</label
                                >
                                <select
                                  class="field-input"
                                  id="customer_shipping_ward"
                                  name="customer_shipping_ward"
                                >
                                  <option data-code="null" value="null" selected="">Chọn phường/ xã</option>
                                </select>
                              </div>
                            </div>

                            <div
                              id="div_location_country_not_vietnam"
                              class="section-customer-information"
                              style="display: none"
                            >
                              <div class="field field-two-thirds">
                                <div class="field-input-wrapper">
                                  <label
                                    class="field-label"
                                    for="billing_address_city"
                                    >Thành phố</label
                                  >
                                  <input
                                    placeholder="Thành phố"
                                    autocapitalize="off"
                                    spellcheck="false"
                                    class="field-input"
                                    size="30"
                                    type="text"
                                    id="billing_address_city"
                                    name="billing_address[city]"
                                    value=""
                                  />
                                </div>
                              </div>
                              <div class="field field-third">
                                <div class="field-input-wrapper">
                                  <label
                                    class="field-label"
                                    for="billing_address_zip"
                                    >Mã bưu chính</label
                                  >
                                  <input
                                    placeholder="Mã bưu chính"
                                    autocapitalize="off"
                                    spellcheck="false"
                                    class="field-input"
                                    size="30"
                                    type="text"
                                    id="billing_address_zip"
                                    name="billing_address[zip]"
                                    value=""
                                  />
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>

                  <div id="change_pick_location_or_shipping">
                    <div id="section-shipping-rate">
                      <div class="order-checkout__loading--box">
                        <div class="order-checkout__loading--circle"></div>
                      </div>
                      <div class="section-header">
                        <h2 class="section-title">Phương thức vận chuyển</h2>
                      </div>
                      <div class="section-content province-null">
                        <div class="content-box  blank-slate">
                          <i class="blank-slate-icon icon icon-closed-box "></i>
                          <p>Vui lòng chọn tỉnh / thành để có danh sách phương thức vận chuyển.</p>
                        </div>
                      </div>

                      <div class="section-content hidden province-in">
                        <div class="content-box">
                          <div class="content-box-row">
                            <div class="radio-wrapper">
                              <label
                                class="radio-label"
                                for="shipping_rate_id_1629860"
                              >
                                <div class="radio-input">
                                  <input
                                    id="shipping_rate_id_1629860"
                                    class="input-radio"
                                    type="radio"
                                    name="shipping_rate_id"
                                    value="1629860"
                                    checked=""
                                  />
                                </div>
                                <span class="radio-label-primary"
                                  >Miễn phí giao hàng &amp; lắp đặt tại tất cả
                                  quận huyện thuộc TP.HCM đối với các sản phẩm
                                  nội thất. Các sản phẩm thuộc danh mục Đồ Trang
                                  Trí, phí giao hàng sẽ được TrueMart liên hệ báo
                                  sau.</span>
                                <span class="radio-accessory content-box-emphasis">0₫</span>
                              </label>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div class="section-content hidden province-out">
                      <div class="content-box">
                        <div class="content-box-row">
                          <div class="radio-wrapper">
                            <label class="radio-label" for="shipping_rate_id_1647130">
                              <div class="radio-input">
                                <input id="shipping_rate_id_1647130" class="input-radio" type="radio" name="shipping_rate_id" value="1647130" checked="">
                              </div>
                              <span class="radio-label-primary">Các tỉnh thành không thuộc khu vực miễn phí giao hàng &amp; lắp đặt, phí giao hàng sẽ được TrueMart tính theo khoảng cách vận chuyển.</span>
                              <span class="radio-accessory content-box-emphasis">0₫</span>
                              <input type="hidden" name="service-fee" id="service-fee" value="0">
                              <input type="hidden" name="lead-time" id="lead-time" value="0">
                              <input type="hidden" name="voucher" value="${voucher.voucherCode}">
                            </label>
                          </div>
                        </div>
                      </div>
                    </div>

                    <div id="section-payment-method" class="section">
                      <div class="order-checkout__loading--box">
                        <div class="order-checkout__loading--circle"></div>
                      </div>
                      <div class="section-header">
                        <h2 class="section-title">Phương thức thanh toán</h2>
                      </div>
                      <input type="hidden" name="payment-method" value="1002252882" id="payment-method">
                      <div class="section-content">
                        <div class="content-box">
                          <div class="radio-wrapper content-box-row">
                            <label
                              class="radio-label"
                              for="payment_method_id_1002252882"
                            >
                              <div class="radio-input payment-method-checkbox">
                                <input
                                  type-id="1"
                                  id="payment_method_id_1002252882"
                                  class="input-radio"
                                  name="payment_method_id"
                                  type="radio"
                                  value="1002252882"
                                  checked=""
                                />
                              </div>
                              <div class="radio-content-input">
                                <img class="main-img" src="img/cod.svg" />
                                <div>
                                  <span class="radio-label-primary"
                                    >Thanh toán tiền mặt khi giao hàng
                                    (COD)</span
                                  >
                                  <span class="quick-tagline hidden"></span>
                                  <span class="quick-tagline hidden"
                                    >Buy Now, Pay Later
                                  </span>
                                </div>
                              </div>
                            </label>
                          </div>

                          <div class="radio-wrapper content-box-row">
                            <label
                              class="radio-label"
                              for="payment_method_id_1002252884"
                            >
                              <div class="radio-input payment-method-checkbox">
                                <input
                                  type-id="2"
                                  id="payment_method_id_1002252884"
                                  class="input-radio"
                                  name="payment_method_id"
                                  type="radio"
                                  value="1002252884"
                                />
                              </div>
                              <div class="radio-content-input">
                                <img class="main-img" src="img/other.svg" />
                                <div>
                                  <span class="radio-label-primary"
                                    >Thanh toán chuyển khoản qua ngân hàng</span
                                  >
                                  <span class="quick-tagline hidden"></span>
                                  <span class="quick-tagline hidden"
                                    >Buy Now, Pay Later
                                  </span>
                                </div>
                              </div>
                            </label>
                          </div>

                          <div
                            class="radio-wrapper content-box-row content-box-row-secondary bank-payment hidden"
                            for="payment_method_id_1002252884"
                          >
                            <div class="blank-slate">
                              Tên tài khoản: Ngân hàng Thương mại cổ phần Đầu tư và Phát triển Việt Nam Số tài khoản: 31410004065840
                              Ngân hàng: BIDV – CN HCM Nội dung: Tên +
                              SĐT đặt hàng
                            </div>
                          </div>

                          <div class="radio-wrapper content-box-row">
                            <label
                              class="radio-label"
                              for="payment_method_id_1002965974"
                            >
                              <div class="radio-input payment-method-checkbox">
                                <input
                                  type-id="21"
                                  id="payment_method_id_1002965974"
                                  class="input-radio"
                                  name="payment_method_id"
                                  type="radio"
                                  value="1002965974"
                                />
                              </div>
                              <div class="radio-content-input">
                                <img class="main-img" src="img/momo.svg" />
                                <div>
                                  <span class="radio-label-primary"
                                    >Ví MoMo</span
                                  >
                                  <span class="quick-tagline hidden"></span>
                                  <span class="quick-tagline hidden"
                                    >Buy Now, Pay Later
                                  </span>
                                </div>
                              </div>
                            </label>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="step-footer" id="step-footer-checkout">
                  <input name="utf8" type="hidden" value="✓" />
                  <button type="submit" class="step-footer-continue-btn btn">
                    <span class="btn-content">Hoàn tất đơn hàng</span>
                    <i class="btn-spinner icon icon-button-spinner"></i>
                  </button>
                <a class="step-footer-previous-link" href="Cart">Giỏ hàng</a>
              </div>
            </div>
            </form>
<%--              end step--%>
          </div>
          <div class="hrv-coupons-popup">
            <div class="hrv-title-coupons-popup">
              <p>Chọn giảm giá <span class="count-coupons"></span></p>
              <div class="hrv-coupons-close-popup">
                <svg
                  width="18"
                  height="18"
                  viewBox="0 0 18 18"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <path
                    d="M17.1663 2.4785L15.5213 0.833496L8.99968 7.35516L2.47801 0.833496L0.833008 2.4785L7.35468 9.00016L0.833008 15.5218L2.47801 17.1668L8.99968 10.6452L15.5213 17.1668L17.1663 15.5218L10.6447 9.00016L17.1663 2.4785Z"
                    fill="#424242"
                  ></path>
                </svg>
              </div>
            </div>
            <div class="hrv-content-coupons-code">
              <h3 class="coupon_heading">Mã giảm giá của shop</h3>
              <div class="hrv-discount-code-web"></div>
              <div class="hrv-discount-code-external"></div>
            </div>
          </div>
          <div class="hrv-coupons-popup-site-overlay"></div>
        </div>
      </div>
    </div>
    <script !src="">
      $(document).ready(function() {

        $('#customer_shipping_ward').change(function() {
          const wardOption = $('#customer_shipping_ward').children('option:selected')[0].outerHTML;
          const wardId = $('#customer_shipping_ward').val()
          localStorage.setItem('wardOption', wardOption);
          localStorage.setItem('wardId', wardId);

          // console.log(wardOption)

          const provinceOption = $('#customer_shipping_province').children('option:selected')[0].outerHTML;
          const provinceId = $('#customer_shipping_province').val()
          localStorage.setItem('provinceOption', provinceOption);
          localStorage.setItem('provinceId', provinceId)
          // console.log(provinceOption)

          const districtOption = $('#customer_shipping_district').children('option:selected')[0].outerHTML;
          const districtId = $('#customer_shipping_district').val()
          localStorage.setItem('districtOption', districtOption);
          localStorage.setItem('districtId', districtId);

          // console.log(districtOption)
        });

      });
    </script>
    <script !src="">
      $(document).ready(function() {
        const province = localStorage.getItem('provinceOption');
        const district = localStorage.getItem('districtOption');
        const ward = localStorage.getItem('wardOption');

        const provinceId = localStorage.getItem('provinceId');
        const districtId = localStorage.getItem('districtId');
        const wardId = localStorage.getItem('wardId');

        console.log(provinceId)
        console.log(districtId)
        console.log(wardId)

        $('#customer_shipping_ward').append(ward)
        $('#customer_shipping_district').append(district)
        // $('#customer_shipping_province').append(province)

        // Đặt thuộc tính "selected" cho tùy chọn đã lưu
        $('#customer_shipping_ward option[value="' + wardId + '"]').prop('selected', true);
        $('#customer_shipping_district option[value="' + districtId + '"]').prop('selected', true);
        $('#customer_shipping_province option[value="' + provinceId + '"]').prop('selected', true);

      });

    </script>
    <script>
      const url = 'http://140.238.54.136/api/auth/login';
      const email = '20130471@st.hcmuaf.edu.vn'; // Replace with your actual email
      const password = '12345678'; // Replace with your actual password
      // load district
      $('#customer_shipping_province').on('change', function() {
        // Lấy giá trị được chọn trong select
        $('.province-null').removeClass('hidden');
        $('.province-in').addClass('hidden');
        $('.province-out').addClass('hidden');
        var selectedValue = $(this).val();
        $.ajax({
          url: url,
          type: 'POST',
          data: {
            email: email,
            password: password
          },
          success: function(response) {
            const token = response.access_token;
            const districtUrl = 'http://140.238.54.136/api/district?provinceID='+selectedValue;

            var xhr = new XMLHttpRequest();
            xhr.open('GET', districtUrl, true);

            xhr.setRequestHeader('Authorization', 'Bearer '+token);
            xhr.onload = function() {
              if (xhr.status === 200) {
                // xử lý dữ liệu trả về từ API ở đây
                var select =  $("#customer_shipping_district");
                select.empty();
                select.append('<option data-code="null" value="null">Chọn quận/ huyện</option>');
                var select2 =  $("#customer_shipping_ward");
                select2.empty();
                select2.append('<option data-code="null" value="null" selected="">Chọn phường/ xã</option>')
                const data = JSON.parse(xhr.responseText).original.data;
                $.each(data, function (i, district) {
                  var option = $("<option>");
                  option.attr("data-code", district.DistrictID);
                  option.attr("value", district.DistrictID);
                  option.text(district.DistrictName);
                  select.append(option);
                });
              } else {
                // xử lý lỗi nếu có
                console.log('Error:', xhr.status);
              }
            };
            xhr.send();
          },
          error: function(xhr, status, error) {
            console.log(xhr.status); // Handle the error
          }
        });
      });
      //load ward
      $('#customer_shipping_district').on('change', function() {
        $('.province-null').removeClass('hidden');
        $('.province-in').addClass('hidden');
        $('.province-out').addClass('hidden');
        // Lấy giá trị được chọn trong select
        var selectedValue = $(this).val();
        $.ajax({
          url: url,
          type: 'POST',
          data: {
            email: email,
            password: password
          },
          success: function(response) {
            const token = response.access_token;
            const wardUrl = 'http://140.238.54.136/api/ward?districtID='+selectedValue;
            var xhr = new XMLHttpRequest();
            xhr.open('GET', wardUrl, true);
            xhr.setRequestHeader('Authorization', 'Bearer '+token);
            xhr.onload = function() {
              if (xhr.status === 200) {
                // xử lý dữ liệu trả về từ API ở đây
                var select =  $("#customer_shipping_ward");
                select.empty();
                select.append('<option data-code="null" value="null" selected="">Chọn phường/ xã</option>')
                const data = JSON.parse(xhr.responseText).original.data;
                $.each(data, function (i, ward) {
                  var option = $("<option>");
                  option.attr("data-code", ward.WardCode);
                  option.attr("value", ward.WardCode);
                  option.text(ward.WardName);
                  select.append(option);
                });
              } else {
                // xử lý lỗi nếu có
                console.log('Error:', xhr.status);
              }
            };
            xhr.send();
          },
          error: function(xhr, status, error) {
            console.log(xhr.status); // Handle the error
          }
        });
      });
       //get lead time and calculate fee
      $("#customer_shipping_ward").on('change', function() {
        // Lấy giá trị được chọn trong select
        var wardId = parseInt($(this).val());
        var districtId = parseInt($('#customer_shipping_district').val());
        var provinceId = parseInt($('#customer_shipping_province').val());
        $('#selected_customer_shipping_ward').val($(this).val());
        $('#selected_customer_shipping_district').val($('#customer_shipping_district').val());
        $('#selected_customer_shipping_province').val($('#customer_shipping_province').val());
        if (provinceId === 202){
          $('.province-in').removeClass('hidden');
          $('.province-null').addClass('hidden');
          $('.province-out').addClass('hidden');

          $('#lead-time').val(0);
          $('#service-fee').val(0);
        }
        else if (provinceId !== 202){
          $('.province-out').removeClass('hidden');
          $('.province-null').addClass('hidden');
          $('.province-in').addClass('hidden');
          $.ajax({
            url: 'form_update_shipping_method',
            type: 'POST',
            data: {
              wardId: wardId,
              districtId: districtId
            },
            success: function(response) {
              // Xử lý kết quả trả về từ servlet
              // console.log(response.status);
              // console.log(response.message);
              // console.log(response.fee)
              // console.log(response.leadTime)
              // console.log(response)
              var dataFee = JSON.parse(response.fee);
              var dataLeadTime = JSON.parse(response.leadTime)
              var serviceFee = Number(dataFee[0].service_fee);
              var leadTime = dataLeadTime[0].timestamp;
              $('.province-out .radio-accessory.content-box-emphasis').text(serviceFee.toLocaleString() + "₫");
              $('.total-line-shipping .total-line-price').text(serviceFee !== 0 ? serviceFee.toLocaleString() + "₫" : "miễn phí");
              $('#lead-time').val(leadTime);
              $('#service-fee').val(serviceFee);
              var totalAmount = Number($(".payment-due-price").data("checkout-payment-due-target"));
              // Nếu serviceFee khác 0, cộng thêm vào totalAmount
              if (serviceFee !== 0) {
                totalAmount += serviceFee;
              }
              // Hiển thị giá tiền cộng thêm serviceFee
              // $(".payment-due-price").data("checkout-payment-due-target", totalAmount);
              $(".payment-due-price").text(new Intl.NumberFormat("vi-VN", { style: "currency", currency: "VND", minimumFractionDigits: 0 }).format(totalAmount));

            },
            error: function(xhr, status, error) {
              // Xử lý lỗi khi gửi dữ liệu đến servlet
              console.log(error);
            }
          });
        }

      });
    </script>
    <script !src="">
      $('input[name="payment_method_id"]').change(function() {
        if ($(this).val() === '1002252884') {
          $('.bank-payment').removeClass('hidden');
        } else {
          $('.bank-payment').addClass('hidden');
        }
        $('#payment-method').val($(this).val());
      });
    </script>
    <script src="js/provinces_load.js"></script>
    <script src="js/counpon.js"></script>
  </body>
</html>
