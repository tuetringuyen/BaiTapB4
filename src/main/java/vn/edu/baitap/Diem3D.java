package vn.edu.baitap;

import java.util.Locale;
import java.util.Objects;

/** Bài 2: kế thừa tọa độ x, y và các thao tác từ Diem2D, bổ sung z. */
public class Diem3D extends Diem2D {
    private double z;

    public Diem3D() {
        this(0, 0, 0);
    }

    public Diem3D(double x, double y, double z) {
        super(x, y);
        this.z = kiemTraSoHuuHan(z, "z");
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = kiemTraSoHuuHan(z, "z");
    }

    @Override
    public int soChieu() {
        return 3;
    }

    /**
     * Giữ tham số Diem2D để GHI ĐÈ đúng phương thức của lớp cha.
     * Nhờ đó vẫn tính đủ x, y, z khi điểm 3D nằm trong List<Diem2D>.
     * d(A, B) = sqrt((xA-xB)^2 + (yA-yB)^2 + (zA-zB)^2).
     */
    @Override
    public double khoangCach(Diem2D diemKhac) {
        Objects.requireNonNull(diemKhac, "Điểm cần tính khoảng cách không được null.");
        if (!(diemKhac instanceof Diem3D)) {
            throw new IllegalArgumentException("Điểm 3D chỉ được tính khoảng cách với điểm 3D.");
        }
        Diem3D diem3DKhac = (Diem3D) diemKhac;
        double khoangCachXY = super.khoangCach(diemKhac);
        return Math.hypot(khoangCachXY, z - diem3DKhac.z);
    }

    /** Ghi đè: trả về một điểm 3D MỚI (-x, -y, -z). */
    @Override
    public Diem3D doiXungQuaGoc() {
        return new Diem3D(-getX(), -getY(), -z);
    }

    /**
     * Nạp chồng tinhTien: phiên bản ba tham số cho điểm 3D.
     * Phiên bản hai tham số kế thừa từ Diem2D vẫn dùng được và giữ nguyên z.
     */
    public void tinhTien(double deltaX, double deltaY, double deltaZ) {
        kiemTraSoHuuHan(deltaZ, "deltaZ");
        double zMoi = kiemTraSoHuuHan(z + deltaZ, "z sau tịnh tiến");
        super.tinhTien(deltaX, deltaY);
        z = zMoi;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Diem3D(x=%.2f, y=%.2f, z=%.2f)",
                getX(), getY(), z);
    }
}
