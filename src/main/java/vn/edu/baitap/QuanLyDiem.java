package vn.edu.baitap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Bài 3: quản lý một danh sách có cả điểm 2D và điểm 3D. */
public class QuanLyDiem {
    private final List<Diem2D> danhSach = new ArrayList<>();

    public void themDiem(Diem2D diem) {
        danhSach.add(Objects.requireNonNull(diem, "Không được thêm điểm null."));
    }

    public int soLuongDiem() {
        return danhSach.size();
    }

    /** Không cho bên ngoài tự ý thêm/xóa phần tử qua danh sách trả về. */
    public List<Diem2D> layDanhSach() {
        return Collections.unmodifiableList(danhSach);
    }

    public double tongKhoangCach2D() {
        return tongKhoangCachTheoSoChieu(2);
    }

    public double tongKhoangCach3D() {
        return tongKhoangCachTheoSoChieu(3);
    }

    /**
     * Quy ước: cộng khoảng cách của MỌI CẶP điểm cùng số chiều, mỗi cặp một lần.
     * Ví dụ A, B, C: tổng = AB + AC + BC, không chỉ AB + BC.
     * Danh sách rỗng hoặc nhóm có một điểm có tổng bằng 0.
     */
    private double tongKhoangCachTheoSoChieu(int soChieu) {
        double tong = 0;
        for (int i = 0; i < danhSach.size(); i++) {
            Diem2D a = danhSach.get(i);
            if (a.soChieu() != soChieu) {
                continue;
            }
            for (int j = i + 1; j < danhSach.size(); j++) {
                Diem2D b = danhSach.get(j);
                if (b.soChieu() == soChieu) {
                    // Đa hình: nếu a là điểm 3D, gọi khoangCach của Diem3D.
                    tong += a.khoangCach(b);
                }
            }
        }
        return tong;
    }

    /**
     * Thêm điểm đối xứng của từng điểm đang có vào cuối danh sách.
     * Tạo danh sách phụ để không vừa duyệt vừa thêm vào cùng một danh sách.
     * Không loại trùng: điểm tại gốc vẫn có một điểm đối xứng được thêm vào.
     * Nếu gọi lại, thao tác áp dụng cho toàn bộ danh sách tại lần gọi mới.
     *
     * @return số điểm mới được thêm
     */
    public int themCacDiemDoiXung() {
        List<Diem2D> cacDiemDoiXung = new ArrayList<>();
        for (Diem2D diem : danhSach) {
            cacDiemDoiXung.add(diem.doiXungQuaGoc());
        }
        danhSach.addAll(cacDiemDoiXung);
        return cacDiemDoiXung.size();
    }

    public void hienThiDanhSach() {
        if (danhSach.isEmpty()) {
            System.out.println("Danh sách đang rỗng.");
            return;
        }
        for (int i = 0; i < danhSach.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, danhSach.get(i));
        }
    }
}
