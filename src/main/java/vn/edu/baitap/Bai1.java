package vn.edu.baitap;

import java.util.Locale;

/** Chạy riêng bài 1 bằng Bai1.main(), hoặc nhập tay qua menu Main. */
public final class Bai1 {
    private Bai1() {
    }

    public static void main(String[] args) {
        chayDemo();
    }

    public static void chayDemo() {
        System.out.println("\n========== BÀI 1: LỚP ĐIỂM 2D ==========");
        hienThiKetQua(new Diem2D(1, 2), new Diem2D(4, 6), 2, -1);
    }

    public static void chayNhapTay(NhapLieu nhap) {
        System.out.println("\n========== BÀI 1: NHẬP ĐIỂM 2D ==========");
        Diem2D a = nhap.nhapDiem2D("A");
        Diem2D b = nhap.nhapDiem2D("B");
        double deltaX = nhap.nhapSoThuc("Nhập deltaX để tịnh tiến A: ");
        double deltaY = nhap.nhapSoThuc("Nhập deltaY để tịnh tiến A: ");
        hienThiKetQua(a, b, deltaX, deltaY);
    }

    private static void hienThiKetQua(Diem2D a, Diem2D b, double dx, double dy) {
        System.out.println("A = " + a);
        System.out.println("B = " + b);
        System.out.printf(Locale.ROOT, "Khoảng cách AB = %.4f%n", a.khoangCach(b));
        System.out.println("Điểm đối xứng của A qua gốc O: " + a.doiXungQuaGoc());
        System.out.println("Điểm đối xứng của B qua gốc O: " + b.doiXungQuaGoc());
        a.tinhTien(dx, dy);
        System.out.printf(Locale.ROOT, "A sau tịnh tiến (%.2f, %.2f): %s%n", dx, dy, a);
    }
}
