$(document).ready(function () {
  $.getJSON("js/provinces.json", function (data) {
    var provinces = data.original.data;
    var select = $("#customer_shipping_province");
    select.empty();
    select.append(
      '<option data-code="null" value="null">Chọn tỉnh/ thành</option>'
    );
    $.each(provinces, function (i, province) {
      var option = $("<option>");
      option.attr("data-code", province.ProvinceID);
      option.attr("value", province.ProvinceID);
      option.text(province.ProvinceName);
      select.append(option);
    });
  });
});

