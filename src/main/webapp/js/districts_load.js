var districtData;
$.getJSON("js/districts.json", function(data) {
  districtData = data;
  // Lấy đối tượng select tỉnh thành
  var provinceSelect = $("#customer_shipping_proviance");
  // Lấy đối tượng select quận huyện
  var districtSelect = $("#customer_shipping_district");
  // Gán sự kiện onchange cho select tỉnh thành
  provinceSelect.change(function() {
    console.log("Sự kiện change đã được kích hoạt trên select #customer_shipping_proviance");
    // Lấy giá trị của select tỉnh thành được chọn
    var selectedProvince = provinceSelect.val();
    console.log("id province"+selectedProvince)
    // Xóa các option hiện có của select quận huyện
    districtSelect.empty();
    // Lặp qua tất cả các mảng data trong đối tượng JSON
    $.each(districtData.original, function(key, value) {
      // Lặp qua các phần tử của mảng data
      $.each(value.data, function(index, district) {
        // Nếu ProvinceID của quận huyện bằng với ProvinceID của tỉnh thành được chọn
        if (district.ProvinceID == selectedProvince) {
          // Lấy thông tin của quận huyện
          var districtID = district.DistrictID;
          var districtName = district.DistrictName;

          // Thêm option tương ứng vào select quận huyện
          var option = $("<option>").val(districtID).text(districtName);
          districtSelect.append(option);
        }
      });
    });
  });
});