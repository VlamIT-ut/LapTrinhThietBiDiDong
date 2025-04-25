Ứng dụng Giao Đồ Ăn - Food Delivery App

I. Giới thiệu
  Đây là một ứng dụng giao đồ ăn được phát triển trên nền tảng Android sử dụng Jetpack Compose, thư viện giao diện hiện đại của Google. Ứng dụng cho phép người dùng tìm kiếm,đặt hàng và theo dõi đơn hàng đồ ăn từ các nhà hàng một cách dễ dàng và nhanh chóng.
  
  Dự án được xây dựng nhằm mục đích học tập và áp dụng các công nghệ mới nhất trong phát triển ứng dụng Android, bao gồm Jetpack Compose, MVVM,firebase và API integration.

II. Tính năng chính
  1. Tìm kiếm món ăn: Người dùng có thể tìm kiếm theo tên món ăn.
  2. Đặt hàng: Thêm món ăn vào giỏ hàng và tiến hành đặt hàng.
  3. Theo dõi đơn hàng: Xem trạng thái đơn hàng theo thời gian thực.
  4. Quản lý tài khoản: Đăng nhập, đăng ký, đăng xuất và cập nhật thông tin cá nhân.
  5. Thanh toán: Hỗ trợ thanh toán qua nhiều phương thức (giả lập).
  6. Thêm món ăn yêu thích: Người dùng có thể thêm món mình yêu thích vào trang yêu thích.
     
III. Công nghệ sử dụng
  1. Ngôn ngữ lập trình: Kotlin
  2. UI Framework: Jetpack Compose
  3. Kiến trúc: MVVM (Model-View-ViewModel)
  4. Quản lý dữ liệu: Realtime Database(firebase)
  5. Mạng: Retrofit để gọi API
  6. Quản lý trạng thái: LiveData/ViewModel
  7. Công cụ xây dựng: Android Studio
     
IV. Yêu cầu cài đặt
  - Android Studio (phiên bản mới nhất khuyến nghị)
  - Android SDK 21 trở lên
  - Kotlin 1.8 hoặc cao hơn
  - Gradle 7.0 hoặc cao hơn
    
V. Hướng dẫn cài đặt và chạy dự án
Để cài đặt và chạy dự án "Giao Đồ Ăn - Food Delivery App", bạn làm theo các bước sau:
  1. Clone dự án từ GitHub
  - Mở Terminal hoặc Command Prompt và nhập lệnh:
  - git clone https://github.com/VlamIT-ut/LapTrinhThietBiDiDong.git
  2. Mở dự án trong Android Studio
  - Mở Android Studio.
  - Chọn "Open an existing project".
  - Điều hướng đến thư mục dự án vừa clone (LapTrinhThietBiDiDong) và mở nó.

  3. Đồng bộ và xây dựng dự án
  - Chờ Android Studio tải và đồng bộ tất cả các dependency (Gradle, plugin, v.v...).
  - Nếu được yêu cầu, hãy cập nhật Gradle Plugin hoặc SDK phù hợp.
  -Kiểm tra tab Build > Make Project (hoặc nhấn Ctrl + F9) để chắc chắn dự án build thành công.

  4. Cài đặt Firebase (nếu cần)
  - Nếu Firebase chưa được cấu hình sẵn, hãy:
  - Truy cập Firebase Console.
  - Tạo một Project mới và thêm ứng dụng Android.
  - Tải tệp google-services.json và đặt vào thư mục app/ trong dự án.
  -Đảm bảo các dòng cấu hình Firebase đã có trong build.gradle (Project và App level).

  5. Chạy ứng dụng
  - Cắm thiết bị Android hoặc sử dụng Android Emulator.
  - Chọn thiết bị mục tiêu.
  - Nhấn nút Run (Shift + F10) hoặc chọn Run > Run 'app'.


VI. Tổng hợp: Đề tài, ý tưởng, nghiên cứu và phân tích: 
    https://docs.google.com/document/d/1xaKC8XDljPosAC4i7h9qhgb8YQ4b236CYbCkGqpGMso/edit?tab=t.0#heading=h.1t3h5sf

VII. Thiết Kế UI/UX
    link figma thiết kế:https://www.figma.com/design/9rcu6ft2Cdb2pzri2E1cTb/App-Food?node-id=0-1&t=br8WYSHTFvawshz8-1

