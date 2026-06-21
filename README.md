# 🛠️ Buid - Minecraft Plugins Collection

Chào mừng bạn đến với **Buid**! Đây là kho lưu trữ cá nhân chứa các Minecraft plugin chất lượng cao được viết bằng ngôn ngữ Java, hỗ trợ tối ưu hóa hiệu suất server và mang lại các tính năng gameplay độc đáo cho người chơi.

Dự án này sử dụng GitHub Actions để tự động hóa quy trình build và phát hành (CI/CD).

---

## 📂 Các Module & Plugin Hiện Có

Dự án được chia thành các thư mục nhỏ chuyên biệt:

### 1. ⚡ ram-opt-1.20.1 (Tối ưu hóa RAM cho Server)
Plugin giúp tối ưu hóa dung lượng RAM tiêu thụ cho các máy chủ chạy phiên bản **Minecraft 1.20.1**.
* **Tính năng:**
  * Giải phóng bộ nhớ đệm (garbage collection) thông minh.
  * Tối ưu hóa việc load/unload chunks để giảm giật lag.
  * Theo dõi và báo cáo tình trạng RAM theo thời gian thực.

### 2. ⛏️ tool3x3 (Công cụ đào 3x3)
Cung cấp các công cụ đặc biệt giúp khai thác tài nguyên nhanh chóng và tiện lợi.
* **Tính năng:**
  * Phá hủy các block trong phạm vi **3x3x1** khi đào mỏ.
  * Hỗ trợ các loại cúp (pickaxe), xẻng (shovel) nâng cao.
  * Tích hợp kiểm tra bảo vệ vùng đất (bảo vệ khu vực Claim/Towny/GriefPrevention).

### 3. 👹 mahoraga (Tính năng Mahoraga)
Lấy cảm hứng từ Thần tướng Mahoraga trong bộ truyện *Jujutsu Kaisen*.
* **Tính năng:**
  * Tạo ra một thực thể (Boss) hoặc kỹ năng đặc biệt có khả năng **thích ứng (adapt)** với mọi loại sát thương của người chơi.
  * Hiệu ứng kỹ năng bắt mắt và cơ chế chiến đấu đầy thử thách.

---

## 🚀 Hướng dẫn Cài đặt

1. Truy cập vào mục **Releases** trên GitHub này để tải về file `.jar` của plugin bạn cần.
2. Sao chép file `.jar` vào thư mục `plugins` của server Minecraft (Paper/Purpur/Spigot).
3. Khởi động lại (Restart) server.
4. Cấu hình lại các file trong thư mục `plugins/<Tên_Plugin>` nếu cần.

---

## 🛠️ Hướng dẫn Build (Dành cho Lập trình viên)

Dự án được quản lý và xây dựng bằng Java. Để tự build dự án trên máy tính cá nhân:

### Yêu cầu:
* **JDK 17** hoặc cao hơn.
* Công cụ build (Maven hoặc Gradle - *tùy thuộc vào cấu hình bạn đang dùng*).

### Các bước thực hiện:
1. Clone repository về máy:
   ```bash
   git clone https://github.com/Hyakjbw/buid.git
   cd buid
📋
Di chuyển vào thư mục plugin bạn muốn build (ví dụ: tool3x3):
cd tool3x3
📋
Chạy lệnh build (Ví dụ với Maven):
mvn clean package
📋
File .jar sau khi build thành công sẽ nằm trong thư mục /target.
🤖 Tự động hóa CI/CD
Repository này đã được tích hợp GitHub Workflows. Mỗi khi bạn push code mới lên nhánh chính, hệ thống sẽ tự động chạy thử nghiệm và đóng gói sản phẩm giúp bạn tiết kiệm thời gian.
🤝 Đóng góp ý kiến (Contributing)
Nếu bạn phát hiện lỗi (bug) hoặc muốn đề xuất thêm tính năng mới:
Tạo một Issue mới.
Hoặc gửi một bản Pull Request sau khi đã tối ưu lại code.
Tác giả: Huy (Hyakjbw)
