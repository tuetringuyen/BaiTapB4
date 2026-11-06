package vn.edu.baitap;

import java.util.Locale;

/** Chạy riêng bài 3 bằng Bai3.main(), hoặc nhập danh sách qua menu Main. */
public final class Bai3 {
    private Bai3() {
    }

    public static void main(String[] args) {
        chayDemo();
    }

    /** Danh sách mẫu xen kẽ điểm 2D và 3D để kiểm tra việc phân loại. */
    public static QuanLyDiem taoDanhSachMau() {
        QuanLyDiem quanLy = new QuanLyDiem();
        quanLy.themDiem(new Diem2D(0, 0));
        quanLy.themDiem(new Diem3D(0, 0, 0));
        quanLy.themDiem(new Diem2D(3, 4));
        quanLy.themDiem(new Diem3D(1, 2, 2));
        quanLy.themDiem(new Diem2D(6, 8));
        quanLy.themDiem(new Diem3D(2, 4, 4));
        return quanLy;
    }

    public static void chayDemo() {
        System.out.println("\n========== BÀI 3: DANH SÁCH ĐIỂM 2D VÀ 3D ==========");
        xuLyDanhSach(taoDanhSachMau());
    }

    public static void chayNhapTay(NhapLieu nhap) {
        System.out.println("\n========== BÀI 3: NHẬP DANH SÁCH ĐIỂM ==========");
        int n = nhap.nhapSoNguyen("Nhập số điểm (0 - 1000): ", 0, 1000);
        QuanLyDiem quanLy = new QuanLyDiem();
        for (int i = 1; i <= n; i++) {
            int loai = nhap.nhapSoNguyen("Điểm thứ " + i + " thuộc loại nào (2 = 2D, 3 = 3D)? ", 2, 3);
            Diem2D diem = loai == 2
                    ? nhap.nhapDiem2D("P" + i)
                    : nhap.nhapDiem3D("P" + i);
            quanLy.themDiem(diem);
        }
        xuLyDanhSach(quanLy);
    }

    public static void xuLyDanhSach(QuanLyDiem quanLy) {
        System.out.println("Danh sách ban đầu:");
        quanLy.hienThiDanhSach();
        System.out.println("\nCộng khoảng cách mọi cặp cùng loại, mỗi cặp đúng một lần.");
        System.out.printf(Locale.ROOT, "Tổng khoảng cách các điểm 2D = %.4f%n", quanLy.tongKhoangCach2D());
        System.out.printf(Locale.ROOT, "Tổng khoảng cách các điểm 3D = %.4f%n", quanLy.tongKhoangCach3D());
        System.out.println("(Hai tổng trên được tính TRƯỚC khi thêm các điểm đối xứng.)");
        int daThem = quanLy.themCacDiemDoiXung();
        System.out.printf("%nĐã thêm %d điểm đối xứng. Danh sách sau khi thêm có %d điểm:%n",
                daThem, quanLy.soLuongDiem());
        quanLy.hienThiDanhSach();
    }
}
