# Giải thích bài làm

## 1. Vì sao ba phần dùng chung một project?

Ba phần là các bước nối tiếp: tạo lớp 2D, mở rộng thành 3D, rồi dùng cả hai trong một danh sách.
Vì vậy chỉ có một định nghĩa `Diem2D` và một định nghĩa `Diem3D`, không sao chép lớp ở từng bài.
Các file `Bai1`, `Bai2`, `Bai3` chỉ phụ trách chạy minh họa hoặc nhận dữ liệu.
`Main` là menu gọi các phần tương ứng.

## 2. Công thức và ý nghĩa từng phương thức

### Khoảng cách

Với A(x1,y1), B(x2,y2):

```text
dx = x1 - x2
dy = y1 - y2
d(A,B) = sqrt(dx*dx + dy*dy)
```

Với A(x1,y1,z1), B(x2,y2,z2):

```text
dz = z1 - z2
d(A,B) = sqrt(dx*dx + dy*dy + dz*dz)
```

Mã dùng `Math.hypot(dx, dy)` cho 2D và `Math.hypot(khoangCachXY, dz)` cho 3D.
Đây là cách tính cùng chuẩn khoảng cách Euclid mà không tự bình phương các số lớn.
Dù vậy, `double` có giới hạn: với tọa độ cực lớn, hiệu hai tọa độ hoặc kết quả thật vẫn
có thể vượt phạm vi biểu diễn. Bộ nhập không cho NaN/vô cực, nhưng không dùng kiểu số chính xác tùy ý.

### Đối xứng qua gốc tọa độ

```text
2D: (x,y)   -> (-x,-y)
3D: (x,y,z) -> (-x,-y,-z)
```

Phương thức tạo đối tượng mới, không đổi đối tượng ban đầu. Điều này cần thiết cho bài 3:
phải giữ các điểm ban đầu và thêm điểm đối xứng, chứ không biến điểm ban đầu thành điểm đối xứng.

### Tịnh tiến

```text
2D: (x,y)   -> (x+deltaX, y+deltaY)
3D: (x,y,z) -> (x+deltaX, y+deltaY, z+deltaZ)
```

Phương thức cập nhật trực tiếp đối tượng. Trước khi ghi dữ liệu, mã kiểm tra kết quả mới
để nếu dữ liệu không hợp lệ thì không sửa một phần tọa độ rồi dừng.

## 3. Phân biệt ghi đè và nạp chồng

Trong bài, `Diem3D` ghi đè phương thức sau của `Diem2D`:

```java
@Override
public double khoangCach(Diem2D diemKhac)
```

Giữ cùng kiểu tham số `Diem2D` là chủ ý. Nếu chỉ viết
`khoangCach(Diem3D diemKhac)` thì đó là nạp chồng, không ghi đè phương thức gốc.
Khi một đối tượng 3D được tham chiếu qua biến kiểu `Diem2D`, bản ghi đè trong project
vẫn được gọi và tính đủ tọa độ z:

```java
Diem2D a = new Diem3D(0, 0, 0);
Diem2D b = new Diem3D(0, 0, 5);
System.out.println(a.khoangCach(b)); // 5.0, không phải 0.0
```

`doiXungQuaGoc()` ở lớp con trả về `Diem3D`, là kiểu con của kiểu trả về `Diem2D` ở lớp cha.
Đây là kiểu trả về đồng biến trong Java. Nhờ đó:

```java
Diem3D p = new Diem3D(1, 2, 3);
Diem3D q = p.doiXungQuaGoc(); // không cần ép kiểu
```

Ngược lại, `tinhTien(dx, dy, dz)` là **nạp chồng** của `tinhTien(dx, dy)` vì khác số tham số.
Không đặt `@Override` trước phiên bản ba tham số.

## 4. Vì sao không chỉ dùng instanceof Diem2D để tính tổng 2D?

Do `Diem3D extends Diem2D`, một đối tượng 3D cũng thỏa điều kiện `instanceof Diem2D`.
Chỉ kiểm tra điều kiện này sẽ đưa cả điểm 3D vào nhóm 2D.
Bài làm sử dụng `soChieu()` trả về 2 hoặc 3 để phân biệt rõ hai nhóm.

Ví dụ danh sách:

```text
0: 2D(0,0)
1: 3D(0,0,0)
2: 2D(3,4)
3: 3D(1,2,2)
4: 2D(6,8)
5: 3D(2,4,4)
```

Nhóm 2D có các chỉ số 0,2,4; nhóm 3D có các chỉ số 1,3,5.
Các cặp 2D là (0,2), (0,4), (2,4); các cặp 3D là (1,3), (1,5), (3,5).
Không tính các cặp chéo loại.

## 5. Vì sao vòng lặp bắt đầu tại j = i + 1?

```java
for (int i = 0; i < danhSach.size(); i++) {
    for (int j = i + 1; j < danhSach.size(); j++) {
        // Nếu cùng số chiều thì cộng khoảng cách.
    }
}
```

Cách này bỏ qua khoảng cách từ một điểm tới chính nó và chỉ xét một lần mỗi cặp.
Với n điểm trong cùng nhóm, có n*(n-1)/2 cặp. Chi phí tính tổng tăng theo O(n^2).
Trong bài học với danh sách nhỏ, hai vòng lặp trực tiếp giúp dễ hiểu và kiểm chứng.

## 6. Thêm đối xứng mà không làm hỏng vòng lặp

Không thêm phần tử trực tiếp vào danh sách đang duyệt bằng for-each.
Project tạo một danh sách phụ chứa các điểm đối xứng, sau đó gọi `addAll()` một lần.
Chỉ các điểm có sẵn ở đầu thao tác được lấy đối xứng; không tiếp tục lấy đối xứng của các điểm vừa thêm.
Kiểu điểm được giữ đúng nhờ `doiXungQuaGoc()` đã được ghi đè ở lớp 3D.

## 7. Phân quyền truy cập

- `private`: tọa độ và danh sách chỉ được truy cập trực tiếp trong lớp định nghĩa chúng.
  Lớp con đọc x,y qua getter, không truy cập trực tiếp hai trường private.
- `public`: cho chương trình chính gọi constructor, getter/setter, khoảng cách, đối xứng, tịnh tiến.
- `protected`: hàm kiểm tra số hữu hạn có thể được lớp con sử dụng; đồng thời cũng có phạm vi
  truy cập trong cùng package theo quy tắc Java.

Tham chiếu danh sách được khai báo `final` để không gán lại sang một danh sách khác.
Điều này không làm danh sách bất biến: vẫn có thể thêm các điểm mới bằng `themDiem()`.
`layDanhSach()` trả về một view không cho thêm/xóa từ bên ngoài, nhưng các đối tượng điểm
bên trong vẫn có thể thay đổi tọa độ qua phương thức public của chúng.

## 8. Tài liệu chính thức đối chiếu

1. Oracle Java Tutorials — Overriding and Hiding Methods:
   https://docs.oracle.com/javase/tutorial/java/IandI/override.html
2. JetBrains — Import projects:
   https://www.jetbrains.com/help/idea/import-project-or-module-wizard.html
3. JetBrains — SDKs:
   https://www.jetbrains.com/help/idea/sdk.html
4. JetBrains — Maven support:
   https://www.jetbrains.com/help/idea/maven-support.html
5. Apache Maven Compiler Plugin — Setting the --release:
   https://maven.apache.org/components/plugins/maven-compiler-plugin/examples/set-compiler-release.html
