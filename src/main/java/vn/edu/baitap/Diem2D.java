package vn.edu.baitap;

import java.util.Locale;
import java.util.Objects;

/**
 * Bài 1: điểm trong mặt phẳng Oxy.
 * Thuộc tính private để đóng gói; thao tác thông qua phương thức public.
 */
public class Diem2D {
    private double x;
    private double y;

    /** Tạo điểm tại gốc tọa độ. */
    public Diem2D() {
        this(0, 0);
    }

    public Diem2D(double x, double y) {
        this.x = kiemTraSoHuuHan(x, "x");
        this.y = kiemTraSoHuuHan(y, "y");
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = kiemTraSoHuuHan(x, "x");
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = kiemTraSoHuuHan(y, "y");
    }

    /** Dùng để phân biệt điểm 2D và 3D trong danh sách chung. */
    public int soChieu() {
        return 2;
    }

    /**
     * d(A, B) = sqrt((xA - xB)^2 + (yA - yB)^2).
     * Không tính khoảng cách giữa hai điểm khác số chiều trong bài này.
     */
    public double khoangCach(Diem2D diemKhac) {
        Objects.requireNonNull(diemKhac, "Điểm cần tính khoảng cách không được null.");
        if (soChieu() != diemKhac.soChieu()) {
            throw new IllegalArgumentException("Chỉ tính khoảng cách giữa hai điểm cùng số chiều.");
        }
        double dx = x - diemKhac.x;
        double dy = y - diemKhac.y;
        return Math.hypot(dx, dy);
    }

    /** Trả về một điểm MỚI (-x, -y), không thay đổi điểm hiện tại. */
    public Diem2D doiXungQuaGoc() {
        return new Diem2D(-x, -y);
    }

    /** Tịnh tiến trực tiếp điểm hiện tại theo (deltaX, deltaY). */
    public void tinhTien(double deltaX, double deltaY) {
        kiemTraSoHuuHan(deltaX, "deltaX");
        kiemTraSoHuuHan(deltaY, "deltaY");
        // Kiểm tra cả hai tọa độ trước khi cập nhật để tránh thay đổi dở dang.
        double xMoi = kiemTraSoHuuHan(x + deltaX, "x sau tịnh tiến");
        double yMoi = kiemTraSoHuuHan(y + deltaY, "y sau tịnh tiến");
        x = xMoi;
        y = yMoi;
    }

    /**
     * protected: lớp con Diem3D sử dụng lại để kiểm tra tọa độ z.
     * Không chấp nhận NaN hoặc vô cực. Chuẩn hóa -0.0 thành 0.0 khi hiển thị.
     */
    protected static double kiemTraSoHuuHan(double giaTri, String ten) {
        if (!Double.isFinite(giaTri)) {
            throw new IllegalArgumentException(ten + " phải là số hữu hạn.");
        }
        return giaTri == 0.0 ? 0.0 : giaTri;
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "Diem2D(x=%.2f, y=%.2f)", x, y);
    }
}
