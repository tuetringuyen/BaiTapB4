package vn.edu.baitap;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

/**
 * Kiểm thử tự chạy, chỉ dùng Java chuẩn, không cần JUnit hay tải thư viện.
 * IntelliJ: mở file này, chọn Run KiemThu.main().
 * Terminal: chay.bat test (Windows), hoặc bash chay.sh test (Linux/macOS).
 * Chú ý: mvn test không tự chạy lớp kiểm thử độc lập này.
 */
public final class KiemThu {
    private static int tongSo;
    private static int soLoi;

    private KiemThu() {
    }

    public static void main(String[] args) {
        kiemThu2D();
        kiemThu3D();
        kiemThuDanhSach();
        kiemThuNhapLieu();
        kiemThuNgauNhien();
        System.out.printf("%nKẾT QUẢ: %d/%d kiểm thử thành công.%n", tongSo - soLoi, tongSo);
        if (soLoi > 0) {
            throw new AssertionError("Có " + soLoi + " kiểm thử thất bại.");
        }
    }

    private static void kiemThu2D() {
        chay("2D - constructor mặc định", () -> diem(new Diem2D(), 0, 0));
        chay("2D - constructor có tham số", () -> diem(new Diem2D(1, -2), 1, -2));
        chay("2D - getter/setter", () -> {
            Diem2D p = new Diem2D();
            p.setX(3.5);
            p.setY(-4.5);
            diem(p, 3.5, -4.5);
        });
        chay("2D - số chiều", () -> dung(new Diem2D().soChieu() == 2));
        chay("2D - khoảng cách 3-4-5", () ->
                bang(5, new Diem2D(1, 2).khoangCach(new Diem2D(4, 6))));
        chay("2D - hai điểm trùng nhau", () ->
                bang(0, new Diem2D(2, 3).khoangCach(new Diem2D(2, 3))));
        chay("2D - tọa độ âm", () ->
                bang(5, new Diem2D(-1, -2).khoangCach(new Diem2D(2, 2))));
        chay("2D - tọa độ thập phân", () ->
                bang(0.5, new Diem2D(0.1, 0.2).khoangCach(new Diem2D(0.4, 0.6))));
        chay("2D - khoảng cách AB bằng BA", () -> {
            Diem2D a = new Diem2D(-3, 7);
            Diem2D b = new Diem2D(4, -2);
            bang(a.khoangCach(b), b.khoangCach(a));
        });
        chay("2D - đối xứng tạo đối tượng mới, không sửa điểm gốc", () -> {
            Diem2D p = new Diem2D(2, -5);
            Diem2D q = p.doiXungQuaGoc();
            diem(q, -2, 5);
            diem(p, 2, -5);
            dung(p != q);
        });
        chay("2D - đối xứng hai lần", () ->
                diem(new Diem2D(3, -7).doiXungQuaGoc().doiXungQuaGoc(), 3, -7));
        chay("2D - tịnh tiến", () -> {
            Diem2D p = new Diem2D(1, 2);
            p.tinhTien(2, -1);
            diem(p, 3, 1);
        });
        chay("2D - tịnh tiến bằng không", () -> {
            Diem2D p = new Diem2D(1, 2);
            p.tinhTien(0, 0);
            diem(p, 1, 2);
        });
        chay("2D - từ chối NaN", () ->
                nemLoi(IllegalArgumentException.class, () -> new Diem2D(Double.NaN, 0)));
        chay("2D - từ chối vô cực", () ->
                nemLoi(IllegalArgumentException.class, () -> new Diem2D(0, Double.POSITIVE_INFINITY)));
        chay("2D - setter sai không đổi dữ liệu", () -> {
            Diem2D p = new Diem2D(1, 2);
            nemLoi(IllegalArgumentException.class, () -> p.setX(Double.NaN));
            diem(p, 1, 2);
        });
        chay("2D - delta sai không tịnh tiến dở dang", () -> {
            Diem2D p = new Diem2D(1, 2);
            nemLoi(IllegalArgumentException.class, () -> p.tinhTien(3, Double.NaN));
            diem(p, 1, 2);
        });
        chay("2D - phát hiện tràn tọa độ khi tịnh tiến", () -> {
            Diem2D p = new Diem2D(1, Double.MAX_VALUE);
            nemLoi(IllegalArgumentException.class, () -> p.tinhTien(1, Double.MAX_VALUE));
            diem(p, 1, Double.MAX_VALUE);
        });
        chay("2D - hypot xử lý số lớn không bình phương trực tiếp", () ->
                bang(Math.sqrt(2) * 1e200, new Diem2D(1e200, 1e200).khoangCach(new Diem2D())));
        chay("2D - không tính lẫn điểm 3D", () ->
                nemLoi(IllegalArgumentException.class, () -> new Diem2D().khoangCach(new Diem3D())));
        chay("2D - không tính với null", () ->
                nemLoi(NullPointerException.class, () -> new Diem2D().khoangCach(null)));
        chay("2D - chuẩn hóa âm không khi đối xứng", () -> {
            Diem2D p = new Diem2D().doiXungQuaGoc();
            dung(Double.doubleToLongBits(p.getX()) == Double.doubleToLongBits(0.0));
            dung(!p.toString().contains("-0.00"));
        });
    }

    private static void kiemThu3D() {
        chay("3D - constructor mặc định", () -> diem(new Diem3D(), 0, 0, 0));
        chay("3D - constructor có tham số", () -> diem(new Diem3D(1, 2, 3), 1, 2, 3));
        chay("3D - kế thừa getter/setter x y, bổ sung z", () -> {
            Diem3D p = new Diem3D();
            p.setX(-1);
            p.setY(2);
            p.setZ(3);
            diem(p, -1, 2, 3);
        });
        chay("3D - số chiều", () -> dung(new Diem3D().soChieu() == 3));
        chay("3D - khoảng cách dùng đủ ba tọa độ", () ->
                bang(7, new Diem3D(1, 2, 3).khoangCach(new Diem3D(3, 5, 9))));
        chay("3D - chỉ khác z", () ->
                bang(7, new Diem3D(1, 2, 3).khoangCach(new Diem3D(1, 2, 10))));
        chay("3D - khoảng cách qua tham chiếu lớp cha", () -> {
            Diem2D a = new Diem3D(0, 0, 0);
            Diem2D b = new Diem3D(0, 0, 5);
            bang(5, a.khoangCach(b));
        });
        chay("3D - đối xứng ghi đè, giữ đúng kiểu 3D", () -> {
            Diem2D p = new Diem3D(1, -2, 3);
            Diem2D q = p.doiXungQuaGoc();
            dung(q instanceof Diem3D);
            diem((Diem3D) q, -1, 2, -3);
            diem((Diem3D) p, 1, -2, 3);
            dung(p != q);
        });
        chay("3D - đối xứng hai lần", () ->
                diem(new Diem3D(1, -2, 3).doiXungQuaGoc().doiXungQuaGoc(), 1, -2, 3));
        chay("3D - tịnh tiến ba tham số", () -> {
            Diem3D p = new Diem3D(1, 2, 3);
            p.tinhTien(2, -1, 3);
            diem(p, 3, 1, 6);
        });
        chay("3D - tịnh tiến hai tham số kế thừa giữ z", () -> {
            Diem3D p = new Diem3D(1, 2, 3);
            p.tinhTien(2, -1);
            diem(p, 3, 1, 3);
        });
        chay("3D - từ chối tọa độ z vô cực", () ->
                nemLoi(IllegalArgumentException.class, () -> new Diem3D(0, 0, Double.NEGATIVE_INFINITY)));
        chay("3D - deltaZ sai không đổi x y", () -> {
            Diem3D p = new Diem3D(1, 2, 3);
            nemLoi(IllegalArgumentException.class, () -> p.tinhTien(4, 5, Double.NaN));
            diem(p, 1, 2, 3);
        });
        chay("3D - deltaY sai không đổi z", () -> {
            Diem3D p = new Diem3D(1, 2, 3);
            nemLoi(IllegalArgumentException.class, () -> p.tinhTien(4, Double.NaN, 5));
            diem(p, 1, 2, 3);
        });
        chay("3D - tràn z không thay đổi x y", () -> {
            Diem3D p = new Diem3D(1, 2, Double.MAX_VALUE);
            nemLoi(IllegalArgumentException.class, () -> p.tinhTien(4, 5, Double.MAX_VALUE));
            diem(p, 1, 2, Double.MAX_VALUE);
        });
        chay("3D - không tính lẫn điểm 2D", () ->
                nemLoi(IllegalArgumentException.class, () -> new Diem3D().khoangCach(new Diem2D())));
        chay("3D - không tính với null", () ->
                nemLoi(NullPointerException.class, () -> new Diem3D().khoangCach(null)));
        chay("3D - toString có tọa độ z", () -> dung(new Diem3D(1, 2, 3).toString().contains("z=3.00")));
    }

    private static void kiemThuDanhSach() {
        chay("Danh sách - rỗng", () -> {
            QuanLyDiem q = new QuanLyDiem();
            bang(0, q.tongKhoangCach2D());
            bang(0, q.tongKhoangCach3D());
            dung(q.themCacDiemDoiXung() == 0);
            dung(q.soLuongDiem() == 0);
        });
        chay("Danh sách - mỗi loại chỉ một điểm", () -> {
            QuanLyDiem q = tao(new Diem2D(2, 3), new Diem3D(4, 5, 6));
            bang(0, q.tongKhoangCach2D());
            bang(0, q.tongKhoangCach3D());
        });
        chay("Danh sách - ba điểm 2D cộng đủ mọi cặp", () -> {
            QuanLyDiem q = tao(new Diem2D(), new Diem2D(3, 4), new Diem2D(6, 8));
            bang(20, q.tongKhoangCach2D());
            bang(0, q.tongKhoangCach3D());
        });
        chay("Danh sách - ba điểm 3D cộng đủ mọi cặp", () -> {
            QuanLyDiem q = tao(new Diem3D(), new Diem3D(1, 2, 2), new Diem3D(2, 4, 4));
            bang(12, q.tongKhoangCach3D());
            bang(0, q.tongKhoangCach2D());
        });
        chay("Danh sách - xen kẽ 2D/3D, tổng 20 và 12", () -> {
            QuanLyDiem q = Bai3.taoDanhSachMau();
            bang(20, q.tongKhoangCach2D());
            bang(12, q.tongKhoangCach3D());
        });
        chay("Danh sách - 3D không bị tính vào nhóm 2D", () -> {
            QuanLyDiem q = tao(new Diem3D(0, 0, 1), new Diem3D(3, 4, 1), new Diem2D(100, 100));
            bang(0, q.tongKhoangCach2D());
            bang(5, q.tongKhoangCach3D());
        });
        chay("Danh sách - không phụ thuộc thứ tự", () -> {
            QuanLyDiem a = Bai3.taoDanhSachMau();
            List<Diem2D> daoNguoc = new ArrayList<>(a.layDanhSach());
            Collections.reverse(daoNguoc);
            QuanLyDiem b = new QuanLyDiem();
            for (Diem2D p : daoNguoc) {
                b.themDiem(p);
            }
            bang(a.tongKhoangCach2D(), b.tongKhoangCach2D());
            bang(a.tongKhoangCach3D(), b.tongKhoangCach3D());
        });
        chay("Danh sách - các điểm trùng vẫn là phần tử riêng", () -> {
            QuanLyDiem q = tao(new Diem2D(), new Diem2D(), new Diem2D(3, 4));
            bang(10, q.tongKhoangCach2D());
        });
        chay("Danh sách - thêm đối xứng từ 6 thành 12 điểm", () -> {
            QuanLyDiem q = Bai3.taoDanhSachMau();
            dung(q.themCacDiemDoiXung() == 6);
            dung(q.soLuongDiem() == 12);
        });
        chay("Danh sách - đối xứng đúng kiểu, đúng tọa độ, giữ nguyên gốc", () -> {
            Diem2D a = new Diem2D(1, 2);
            Diem3D b = new Diem3D(3, 4, 5);
            QuanLyDiem q = tao(a, b);
            q.themCacDiemDoiXung();
            List<Diem2D> ds = q.layDanhSach();
            dung(ds.get(0) == a && ds.get(1) == b);
            diem(a, 1, 2);
            diem(b, 3, 4, 5);
            dung(ds.get(2).getClass() == Diem2D.class);
            dung(ds.get(3) instanceof Diem3D);
            diem(ds.get(2), -1, -2);
            diem((Diem3D) ds.get(3), -3, -4, -5);
        });
        chay("Danh sách - điểm đối xứng không dùng chung đối tượng với gốc", () -> {
            Diem3D a = new Diem3D(1, 2, 3);
            QuanLyDiem q = tao(a);
            q.themCacDiemDoiXung();
            ((Diem3D) q.layDanhSach().get(1)).tinhTien(1, 1, 1);
            diem(a, 1, 2, 3);
        });
        chay("Danh sách - gốc tọa độ vẫn được thêm đối xứng mới", () -> {
            Diem2D p = new Diem2D();
            QuanLyDiem q = tao(p);
            q.themCacDiemDoiXung();
            dung(q.soLuongDiem() == 2);
            dung(q.layDanhSach().get(1) != p);
            diem(q.layDanhSach().get(1), 0, 0);
        });
        chay("Danh sách - gọi thêm đối xứng hai lần", () -> {
            QuanLyDiem q = tao(new Diem2D(1, 2));
            dung(q.themCacDiemDoiXung() == 1);
            dung(q.themCacDiemDoiXung() == 2);
            dung(q.soLuongDiem() == 4);
        });
        chay("Danh sách - tính lại tổng sau đối xứng", () -> {
            QuanLyDiem q = Bai3.taoDanhSachMau();
            q.themCacDiemDoiXung();
            bang(130, q.tongKhoangCach2D());
            bang(78, q.tongKhoangCach3D());
        });
        chay("Danh sách - không cho thêm null", () ->
                nemLoi(NullPointerException.class, () -> new QuanLyDiem().themDiem(null)));
        chay("Danh sách - bảo vệ cấu trúc danh sách trả về", () -> {
            QuanLyDiem q = tao(new Diem2D());
            nemLoi(UnsupportedOperationException.class, () -> q.layDanhSach().clear());
            dung(q.soLuongDiem() == 1);
        });
    }

    private static void kiemThuNhapLieu() {
        chay("Nhập liệu - số nguyên sai thì nhập lại", () -> anDauRa(() -> {
            NhapLieu n = new NhapLieu(new Scanner("abc\n9\n-1\n2\n"));
            dung(n.nhapSoNguyen("", 0, 4) == 2);
        }));
        chay("Nhập liệu - dấu phẩy thập phân", () -> anDauRa(() -> {
            NhapLieu n = new NhapLieu(new Scanner("1,25\n"));
            bang(1.25, n.nhapSoThuc(""));
        }));
        chay("Nhập liệu - NaN, Infinity, chữ được yêu cầu nhập lại", () -> anDauRa(() -> {
            NhapLieu n = new NhapLieu(new Scanner("NaN\nInfinity\nabc\n-3.5\n"));
            bang(-3.5, n.nhapSoThuc(""));
        }));
        chay("Nhập liệu - khoảng trắng", () -> anDauRa(() -> {
            NhapLieu n = new NhapLieu(new Scanner("   2.5   \n"));
            bang(2.5, n.nhapSoThuc(""));
        }));
        chay("Nhập liệu - hết luồng nhập không lặp vô hạn", () -> anDauRa(() -> {
            NhapLieu n = new NhapLieu(new Scanner(""));
            nemLoi(NoSuchElementException.class, () -> n.nhapSoThuc(""));
        }));
    }

    private static void kiemThuNgauNhien() {
        chay("Đối chiếu 200 cặp 2D và 200 cặp 3D với công thức độc lập", () -> {
            Random r = new Random(20261106L);
            for (int i = 0; i < 200; i++) {
                double x1 = r.nextInt(201) - 100;
                double y1 = r.nextInt(201) - 100;
                double z1 = r.nextInt(201) - 100;
                double x2 = r.nextInt(201) - 100;
                double y2 = r.nextInt(201) - 100;
                double z2 = r.nextInt(201) - 100;
                double dx = x1 - x2, dy = y1 - y2, dz = z1 - z2;
                bang(Math.sqrt(dx * dx + dy * dy),
                        new Diem2D(x1, y1).khoangCach(new Diem2D(x2, y2)));
                bang(Math.sqrt(dx * dx + dy * dy + dz * dz),
                        new Diem3D(x1, y1, z1).khoangCach(new Diem3D(x2, y2, z2)));
            }
        });
        chay("Tổng mọi cặp đối chiếu độc lập trên 40 điểm hỗn hợp", () -> {
            Random r = new Random(42);
            QuanLyDiem q = new QuanLyDiem();
            double[][] duLieu = new double[40][3];
            for (int i = 0; i < duLieu.length; i++) {
                for (int j = 0; j < 3; j++) {
                    duLieu[i][j] = r.nextInt(21) - 10;
                }
                q.themDiem(i % 2 == 0
                        ? new Diem2D(duLieu[i][0], duLieu[i][1])
                        : new Diem3D(duLieu[i][0], duLieu[i][1], duLieu[i][2]));
            }
            double[] tong = new double[2];
            for (int i = 0; i < duLieu.length; i++) {
                for (int j = i + 1; j < duLieu.length; j++) {
                    if (i % 2 == j % 2) {
                        int loai = i % 2;
                        double binhPhuong = 0;
                        for (int k = 0; k < (loai == 0 ? 2 : 3); k++) {
                            double delta = duLieu[i][k] - duLieu[j][k];
                            binhPhuong += delta * delta;
                        }
                        tong[loai] += Math.sqrt(binhPhuong);
                    }
                }
            }
            bang(tong[0], q.tongKhoangCach2D());
            bang(tong[1], q.tongKhoangCach3D());
        });
    }

    private static QuanLyDiem tao(Diem2D... cacDiem) {
        QuanLyDiem q = new QuanLyDiem();
        for (Diem2D p : Arrays.asList(cacDiem)) {
            q.themDiem(p);
        }
        return q;
    }

    private static void chay(String ten, Runnable kiemThu) {
        tongSo++;
        try {
            kiemThu.run();
            System.out.println("[PASS] " + ten);
        } catch (AssertionError | RuntimeException e) {
            soLoi++;
            System.out.println("[FAIL] " + ten + " -> " + e);
        }
    }

    private static void dung(boolean dieuKien) {
        if (!dieuKien) {
            throw new AssertionError("Điều kiện không đúng.");
        }
    }

    private static void bang(double mongDoi, double thucTe) {
        double saiSo = 1e-9 * Math.max(1, Math.abs(mongDoi));
        if (!Double.isFinite(thucTe) || Math.abs(mongDoi - thucTe) > saiSo) {
            throw new AssertionError("Mong đợi " + mongDoi + ", thực tế " + thucTe);
        }
    }

    private static void diem(Diem2D p, double x, double y) {
        bang(x, p.getX());
        bang(y, p.getY());
    }

    private static void diem(Diem3D p, double x, double y, double z) {
        diem(p, x, y);
        bang(z, p.getZ());
    }

    private static void nemLoi(Class<? extends Throwable> loai, Runnable hanhDong) {
        try {
            hanhDong.run();
        } catch (Throwable e) {
            if (loai.isInstance(e)) {
                return;
            }
            throw new AssertionError("Sai loại lỗi: " + e, e);
        }
        throw new AssertionError("Đáng lẽ phải phát sinh " + loai.getSimpleName());
    }

    private static void anDauRa(Runnable hanhDong) {
        PrintStream cu = System.out;
        try (PrintStream tam = new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8)) {
            System.setOut(tam);
            hanhDong.run();
        } finally {
            System.setOut(cu);
        }
    }
}
