# TTWEB
Trang web bán gạch men
- [x] Sửa lại thiết kế CSDL 
- [ ] Sửa lại phần front-end [Vũ]
- [ ] Update lại phần code admin : Fe + Be [Ngọc]
- [ ] Thêm chức năng đánh giá sản phẩm theo sao, và đánh giá bằng bình luận [Vũ]
- [ ] Thêm phần thanh toán: ngân hàng or ví điện tử
- [ ] Update phần bảo mật: bảo mật thông tin người dùng, làm phần Logs [Trung], giới hạn số lần đăng nhập[Ngọc], captcha xác thực đăng nhập[Ngọc]
- [ ] Thêm đăng nhập google, facebook,...[Trung]
- [ ] Phần hỗ trợ khách hàng: làm chat hay gì đó, voucher,...
- [ ] Deploy ứng dụng lên server: docker
* Lưu ý khi Upload ảnh: B1: Vào tệp AddOrUpdateProduct 
                        B2: Search từ khóa uploadPath để đi tới thư mục Upload ảnh  
                        B3: Thay đổi đường dẫn tại file này bằng cách chọn vào thư mục UploadFileStore sau đó chọn Copy Path, tiếp đến chọn vào phần Absolute path để lấy ra đường dẫn tuyệt đối của thư mục và thay thế đường dẫn đó với đường dẫn có sẵn (* Việc thay đổi này chỉ giúp ảnh upload chính xác chứ không ảnh hưởng gì tới CSDL. Nếu không đổi sẽ không upload được ảnh)
