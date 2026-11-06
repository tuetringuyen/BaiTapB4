package vn.edu.baitap;

import java.util.Locale;

/** Chạy riêng bài 2 bằng Bai2.main(), hoặc nhập tay qua menu Main. */
public final class Bai2 {
    private Bai2() {
    }

    public static void main(String[] args) {
        chayDemo();
    }

    public static void chayDemo() {
        System.out.println("\n========== BÀI 2: LỚP ĐIỂM 3D KẾ THỪA 2D ==========");
        hienThiKetQua(new Diem3D(1, 2, 3), new Diem3D(3, 5, 9), 2, -1, 3);
    }

    public static void chayNhapTay(NhapLieu nhap) {
        System.out.println("\n========== BÀI 2: NHẬP ĐIỂM 3D ==========");
        Diem3D a = nhap.nhapDiem3D("A");
        Diem3D b = nhap.nhapDiem3D("B");
        double deltaX = nhap.nhapSoThuc("Nhập deltaX để tịnh tiến A: ");
        double deltaY = nhap.nhapSoThuc("Nhập deltaY để tịnh tiến A: ");
        double deltaZ = nhap.nhapSoThuc("Nhập deltaZ để tịnh tiến A: ");
        hienThiKetQua(a, b, deltaX, deltaY, deltaZ);
    }

    private static void hienThiKetQua(Diem3D a, Diem3D b, double dx, double dy, double dz) {
        System.out.println("A = " + a);
        System.out.println("B = " + b);
        System.out.printf(Locale.ROOT, "Khoảng cách AB = %.4f%n", a.khoangCach(b));
        System.out.println("Điểm đối xứng của A qua gốc O: " + a.doiXungQuaGoc());
        System.out.println("Điểm đối xứng của B qua gốc O: " + b.doiXungQuaGoc());
        a.tinhTien(dx, dy, dz);
        System.out.printf(Locale.ROOT, "A sau tịnh tiến (%.2f, %.2f, %.2f): %s%n", dx, dy, dz, a);
    }
}
