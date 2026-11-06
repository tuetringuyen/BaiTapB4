# Bài tập Java: Điểm 2D, điểm 3D và danh sách điểm

Project gồm đầy đủ cả **3 phần trong đề**, viết bằng Java, chạy trên console trong IntelliJ IDEA.
Mã nguồn có chú thích tiếng Việt. Không sử dụng cơ sở dữ liệu hoặc thư viện Java bên ngoài.

## 1. Mở và chạy trong IntelliJ IDEA

1. Giải nén `BaiTap_Diem2D_Diem3D_IntelliJ.zip` ra một thư mục.
2. Trong IntelliJ IDEA, chọn **File → Open** hoặc **Open** tại màn hình khởi động.
3. Chọn file **pom.xml** trong thư mục vừa giải nén. Nếu được hỏi, chọn **Open as Project**.
4. Chọn **Trust Project** nếu IntelliJ hỏi về độ tin cậy của project bạn vừa mở.
5. Vào **File → Project Structure → Project → SDK**, chọn **JDK 17 hoặc JDK 21**.
   Nếu chưa có JDK, dùng **Download JDK** trong màn hình chọn SDK.
6. Đợi IDE nhận các thư mục nguồn. Mở:

   `src/main/java/vn/edu/baitap/Main.java`

7. Bấm tam giác màu xanh cạnh `main`, chọn **Run 'Main.main()'**.
8. Nhập **4**, nhấn Enter để xem cả ba bài chạy với dữ liệu mẫu.
   Nhập **1**, **2** hoặc **3** để tự nhập dữ liệu tương ứng. Nhập **0** để thoát.

**Không cần tạo lại project hoặc sao chép từng file Java.**
`pom.xml` đã cấu hình Java 17 và mã hóa UTF-8; JDK 21 cũng biên dịch được project này.
Maven có thể cần mạng trong lần đầu tải các build plugin. Các script `chay.bat` và
`chay.sh` sử dụng trực tiếp JDK, không yêu cầu cài Maven và không tải thư viện.

### Chạy riêng từng bài

| File chạy | Tác dụng |
|---|---|
| `Bai1.java` | Chạy dữ liệu mẫu cho lớp điểm 2D. |
| `Bai2.java` | Chạy dữ liệu mẫu cho lớp điểm 3D kế thừa 2D. |
| `Bai3.java` | Chạy danh sách hỗn hợp 2D/3D, tính tổng và thêm đối xứng. |
| `Main.java` | Menu nhập dữ liệu và lựa chọn chạy mẫu cả ba bài. |

Mỗi file trên đều có `public static void main(String[] args)` để chạy trực tiếp.

## 2. Cấu trúc project

```text
BaiTap_Diem2D_Diem3D_IntelliJ/
├── pom.xml
├── README.md
├── chay.bat
├── chay.sh
├── src/
│   ├── main/java/vn/edu/baitap/
│   │   ├── Diem2D.java        # Bài 1: lớp điểm 2D
│   │   ├── Diem3D.java        # Bài 2: lớp điểm 3D kế thừa
│   │   ├── QuanLyDiem.java    # Bài 3: danh sách và tổng khoảng cách
│   │   ├── Bai1.java          # Chương trình minh họa bài 1
│   │   ├── Bai2.java          # Chương trình minh họa bài 2
│   │   ├── Bai3.java          # Chương trình minh họa bài 3
│   │   ├── NhapLieu.java      # Nhập số và kiểm tra dữ liệu
│   │   └── Main.java          # Menu chính
│   └── test/java/vn/edu/baitap/
│       └── KiemThu.java       # 63 kiểm thử tự chạy, không cần JUnit
└── docs/
    ├── GIAI_THICH_BAI_LAM.md
    ├── KET_QUA_CHAY_MAU.txt
    ├── KET_QUA_KIEM_THU.txt
    └── THONG_TIN_KIEM_CHUNG.txt
```

## 3. Bài 1: lớp Diem2D

- Thuộc tính `private double x`, `private double y`.
- Constructor mặc định tạo điểm `(0, 0)`; constructor có tham số tạo điểm `(x, y)`.
- Getter/setter để đọc và thay đổi tọa độ.
- `khoangCach(Diem2D diemKhac)`: tính `sqrt((x1-x2)^2 + (y1-y2)^2)`.
- `doiXungQuaGoc()`: tạo **điểm mới** `(-x, -y)`, không sửa điểm ban đầu.
- `tinhTien(deltaX, deltaY)`: **thay đổi điểm hiện tại** thành `(x+deltaX, y+deltaY)`.
- `toString()`: hiển thị tọa độ với hai chữ số thập phân. Giá trị tính toán vẫn giữ kiểu `double`.

Ví dụ mẫu: A(1, 2), B(4, 6), khoảng cách AB = **5**.
Đối xứng của A là (-1, -2). Tịnh tiến A theo (2, -1) được (3, 1).

## 4. Bài 2: lớp Diem3D kế thừa Diem2D

```java
public class Diem3D extends Diem2D
```

- Dùng `super(x, y)` để khởi tạo phần tọa độ của lớp cha; bổ sung `private double z`.
- Ghi đè `khoangCach(Diem2D diemKhac)` để tính đủ ba tọa độ:
  `sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)`.
- Ghi đè `doiXungQuaGoc()`, trả về `Diem3D(-x, -y, -z)`.
- Nạp chồng `tinhTien(deltaX, deltaY, deltaZ)` để dịch cả ba tọa độ.
- Phiên bản `tinhTien(deltaX, deltaY)` kế thừa vẫn dùng được và giữ nguyên `z`.
- Ghi đè `soChieu()` và `toString()` để phân loại và hiển thị đúng điểm 3D.

Ví dụ mẫu: A(1, 2, 3), B(3, 5, 9), khoảng cách AB = **7**.
Đối xứng của A là (-1, -2, -3). Tịnh tiến A theo (2, -1, 3) được (3, 1, 6).

## 5. Bài 3: danh sách gồm cả 2D và 3D

Danh sách dùng `List<Diem2D>`, chứa được cả đối tượng `Diem2D` và `Diem3D`.
`QuanLyDiem` thực hiện thêm điểm, tính tổng theo từng loại, thêm các điểm đối xứng và hiển thị.

### Quy ước “tổng khoảng cách”

Đề chưa nói rõ tổng các điểm liên tiếp hay tổng mọi cặp. Bài làm chọn:

**Cộng khoảng cách của mọi cặp điểm cùng loại, mỗi cặp đúng một lần.**

Với ba điểm 2D A, B, C: `tong2D = AB + AC + BC`.
Với ba điểm 3D P, Q, R: `tong3D = PQ + PR + QR`.
Không cộng khoảng cách chéo giữa 2D và 3D, không cộng khoảng cách tới gốc tọa độ.
Vòng lặp thứ hai bắt đầu tại `j = i + 1` để không đếm cả AB lẫn BA.
Nhóm không có điểm hoặc chỉ có một điểm có tổng bằng 0.
Nếu giảng viên yêu cầu chỉ cộng điểm liên tiếp thì cần đổi quy tắc trong
`QuanLyDiem.tongKhoangCachTheoSoChieu`; bản hiện tại không dùng quy tắc đó.

### Dữ liệu mẫu và đáp án

| Nhóm | Điểm | Khoảng cách các cặp | Tổng |
|---|---|---|---|
| 2D | (0,0), (3,4), (6,8) | 5, 10, 5 | **20** |
| 3D | (0,0,0), (1,2,2), (2,4,4) | 3, 6, 3 | **12** |

Các điểm được lưu xen kẽ 2D/3D trong danh sách. Hai tổng được tính **trước khi thêm đối xứng**.
Sau khi thêm đối xứng của 6 điểm ban đầu, danh sách có **12 điểm**.
Điểm tại gốc có đối xứng trùng tọa độ với chính nó nhưng vẫn được thêm như một phần tử mới;
đề không yêu cầu loại trùng. Gọi thêm đối xứng lần nữa sẽ áp dụng cho toàn bộ danh sách hiện có.

## 6. Kế thừa, đa hình và access modifiers

| Nội dung | Cách áp dụng trong bài |
|---|---|
| `private` | Bảo vệ tọa độ x, y, z và danh sách điểm. |
| `public` | Constructor và các thao tác mà chương trình chính cần gọi. |
| `protected` | Hàm `kiemTraSoHuuHan()` được lớp con dùng lại. |
| `extends` | `Diem3D` kế thừa `Diem2D`. |
| `super` | Gọi constructor, tính phần khoảng cách XY, tịnh tiến XY của lớp cha. |
| `@Override` | Định nghĩa lại khoảng cách, đối xứng, số chiều và hiển thị ở lớp 3D. |
| Nạp chồng | `tinhTien(dx, dy, dz)` khác số tham số với `tinhTien(dx, dy)`. |
| Đa hình | Gọi `doiXungQuaGoc()` trên phần tử `List<Diem2D>` vẫn tạo đúng điểm 3D khi cần. |

Phần giải thích kỹ hơn và các lỗi dễ mắc nằm trong `docs/GIAI_THICH_BAI_LAM.md`.

## 7. Nhập dữ liệu

Mỗi lần chương trình hỏi, nhập một giá trị rồi nhấn Enter. Có thể dùng `1.5` hoặc `1,5`
cho số thập phân. Nhập chữ, số ngoài khoảng chọn, `NaN` hoặc vô cực sẽ được yêu cầu nhập lại.
Không dùng dấu phân cách hàng nghìn. Bài 3 cho nhập từ 0 đến 1000 điểm.
Nếu phép tịnh tiến làm tọa độ vượt phạm vi `double`, chương trình báo lỗi thay vì cập nhật dở dang.

## 8. Chạy từ Terminal, không cần Maven

### Windows — PowerShell trong IntelliJ hoặc Windows Terminal

```powershell
.\chay.bat
.\chay.bat demo
.\chay.bat bai1
.\chay.bat bai2
.\chay.bat bai3
.\chay.bat test
```

### Linux/macOS

```bash
bash chay.sh
bash chay.sh demo
bash chay.sh bai1
bash chay.sh bai2
bash chay.sh bai3
bash chay.sh test
```

Yêu cầu JDK 17 hoặc mới hơn; lệnh `java` và `javac` phải có trong PATH hoặc JAVA_HOME.
Các script tự biên dịch mã nguồn vào thư mục `out/`.

### Maven — tùy chọn

```bash
mvn clean package
java -Dfile.encoding=UTF-8 -jar target/diem-2d-3d-1.0.0.jar
java -Dfile.encoding=UTF-8 -jar target/diem-2d-3d-1.0.0.jar --demo
```

`KiemThu` là bộ kiểm thử Java độc lập, **không phải JUnit**. Vì vậy `mvn test` không tự
thực thi 63 kiểm thử này; hãy chạy `KiemThu.main()` hoặc script với tham số `test`.

## 9. Kiểm thử và kết quả

Mã đã được biên dịch bằng `javac --release 17 -encoding UTF-8 -Xlint:all`, chạy trên
OpenJDK 21 trong môi trường Linux. Cả **63/63 kiểm thử** đều thành công.
Bộ kiểm thử gồm công thức 2D/3D, đa hình, đối xứng, tịnh tiến, danh sách rỗng, điểm trùng,
phân loại điểm, nhập sai, số lớn, thêm đối xứng và đối chiếu các dữ liệu ngẫu nhiên có seed cố định.

- Kết quả mẫu: `docs/KET_QUA_CHAY_MAU.txt`.
- Nhật ký kiểm thử: `docs/KET_QUA_KIEM_THU.txt`.
- Phạm vi kiểm chứng: `docs/THONG_TIN_KIEM_CHUNG.txt`.

## 10. Khi IntelliJ chưa chạy được

**Báo “Project SDK is not defined” hoặc “release version 17 not supported”:**
chọn JDK 17/21 trong Project Structure. Kiểm tra cả Module SDK đang kế thừa Project SDK.

**Không thấy nút chạy ở Main:** mở đúng `pom.xml` dưới dạng project, không chỉ mở riêng file Java.
Nếu IDE chưa nhận Maven, dùng **Add as Maven Project** trên `pom.xml`.

**Lỗi hiển thị tiếng Việt:** đặt **Settings → Editor → File Encodings → UTF-8** và kiểm tra
mã hóa console là UTF-8. Các script có sẵn cấu hình mã hóa khi chạy. Không lưu lại mã nguồn ở ANSI.

**Không tải được Maven plugin vì mạng:** mã nguồn không cần thư viện ngoài;
chạy `chay.bat` hoặc `chay.sh` trực tiếp bằng JDK để tiếp tục.
