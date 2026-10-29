# SweetBakery – Website bán bánh kem

Dự án Spring Boot + Thymeleaf theo cấu trúc MVC, dùng H2 để dễ chạy thử trên máy mà không cần cài MySQL.

## Chức năng
- Trang chủ, tìm kiếm sản phẩm và trang chi tiết bánh kem.
- Đăng ký, đăng nhập, đăng xuất; mật khẩu được băm bằng BCrypt.
- Quên mật khẩu bản demo: xác minh email và số điện thoại rồi đặt mật khẩu mới. Chưa có OTP/email xác minh, vì vậy không dùng nguyên trạng cho website thương mại thật.
- Giỏ hàng theo phiên trình duyệt, cập nhật số lượng và xóa sản phẩm.
- Đặt hàng yêu cầu đăng nhập; đơn hàng được lưu theo tài khoản để xem lịch sử.
- Sản phẩm mẫu tự tạo khi chạy lần đầu; cơ sở dữ liệu H2 lưu cục bộ trong thư mục `data/`.

## Yêu cầu
- JDK 21
- Kết nối Internet lần đầu để Maven tải thư viện

## Chạy trên Windows
1. Giải nén ZIP.
2. Mở thư mục `SweetBakery_Project` trong VS Code hoặc mở CMD ngay tại thư mục có `pom.xml`.
3. Chạy:
   ```bat
   .\mvnw.cmd spring-boot:run
   ```
4. Mở trình duyệt tại http://localhost:8080

Nếu Windows báo thiếu quyền chạy `mvnw.cmd`, dùng lệnh `mvn spring-boot:run` nếu đã cài Maven.

## Đưa lên GitHub
Tải **nội dung bên trong** thư mục `SweetBakery_Project` lên repository để `pom.xml` nằm ngay ở thư mục gốc repository. Không tải thư mục `target` hoặc `data` lên.

## Triển khai lên hosting
GitHub dùng để lưu mã nguồn; ứng dụng Java Spring Boot cần hosting chạy Java (ví dụ Railway/Render hoặc máy chủ riêng). Cần cấu hình biến môi trường cơ sở dữ liệu phù hợp nếu muốn lưu đơn hàng bền vững trên hosting. H2 file phù hợp chạy thử trên máy, không nên dùng làm database production.

## Ghi chú
Ảnh bánh trong bản demo tải từ URL ảnh bên ngoài; cần kết nối Internet để hiển thị. Có thể thay URL bằng ảnh riêng đặt trong `src/main/resources/static/images/`.
