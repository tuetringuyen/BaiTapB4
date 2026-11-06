package vn.edu.baitap;

import java.util.NoSuchElementException;
import java.util.Scanner;

/** Các hàm nhập dùng chung; nhập sai thì yêu cầu nhập lại. */
public final class NhapLieu {
    private final Scanner scanner;

    public NhapLieu(Scanner scanner) {
        this.scanner = java.util.Objects.requireNonNull(scanner);
    }

    private String docDong(String thongBao) {
        System.out.print(thongBao);
        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException("Đã kết thúc luồng nhập.");
        }
        return scanner.nextLine().trim();
    }

    public int nhapSoNguyen(String thongBao, int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Giá trị min không được lớn hơn max.");
        }
        while (true) {
            try {
                int giaTri = Integer.parseInt(docDong(thongBao));
                if (giaTri >= min && giaTri <= max) {
                    return giaTri;
                }
            } catch (NumberFormatException e) {
                // Hiển thị thông báo bên dưới rồi nhập lại.
            }
            System.out.printf("Vui lòng nhập số nguyên từ %d đến %d.%n", min, max);
        }
    }

    /** Chấp nhận 1.5 hoặc 1,5; không chấp nhận NaN hoặc Infinity. */
    public double nhapSoThuc(String thongBao) {
        while (true) {
            try {
                String chuoi = docDong(thongBao).replace(',', '.');
                double giaTri = Double.parseDouble(chuoi);
                if (Double.isFinite(giaTri)) {
                    return giaTri;
                }
            } catch (NumberFormatException e) {
                // Hiển thị thông báo bên dưới rồi nhập lại.
            }
            System.out.println("Vui lòng nhập một số hữu hạn, ví dụ: -2; 3.5; 1,25.");
        }
    }

    public Diem2D nhapDiem2D(String ten) {
        System.out.println("Nhập điểm 2D " + ten + ":");
        double x = nhapSoThuc("  x = ");
        double y = nhapSoThuc("  y = ");
        return new Diem2D(x, y);
    }

    public Diem3D nhapDiem3D(String ten) {
        System.out.println("Nhập điểm 3D " + ten + ":");
        double x = nhapSoThuc("  x = ");
        double y = nhapSoThuc("  y = ");
        double z = nhapSoThuc("  z = ");
        return new Diem3D(x, y, z);
    }
}
