package vn.edu.baitap;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Điểm chạy chính: Run Main.main() trong IntelliJ IDEA để mở menu.
 * Tham số --demo: chạy dữ liệu mẫu của cả ba bài và kết thúc.
 */
public final class Main {
    private Main() {
    }

    public static void main(String[] args) {
        if (args.length == 1 && "--demo".equalsIgnoreCase(args[0])) {
            chayTatCaDemo();
            return;
        }
        if (args.length != 0) {
            System.err.println("Cách dùng: chạy Main không có tham số, hoặc thêm --demo.");
            return;
        }
        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            NhapLieu nhap = new NhapLieu(scanner);
            while (true) {
                hienThiMenu();
                int luaChon = nhap.nhapSoNguyen("Chọn chức năng: ", 0, 4);
                try {
                    switch (luaChon) {
                        case 1:
                            Bai1.chayNhapTay(nhap);
                            break;
                        case 2:
                            Bai2.chayNhapTay(nhap);
                            break;
                        case 3:
                            Bai3.chayNhapTay(nhap);
                            break;
                        case 4:
                            chayTatCaDemo();
                            break;
                        case 0:
                            System.out.println("Đã kết thúc chương trình.");
                            return;
                        default:
                            throw new IllegalStateException("Lựa chọn không hợp lệ.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Không thể xử lý dữ liệu: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("\nĐã kết thúc luồng nhập. Chương trình dừng.");
        }
    }

    private static void hienThiMenu() {
        System.out.println("\n================ MENU BÀI TẬP ================");
        System.out.println("1. Bài 1 - Nhập hai điểm 2D, tính khoảng cách, đối xứng, tịnh tiến");
        System.out.println("2. Bài 2 - Nhập hai điểm 3D, tính khoảng cách, đối xứng, tịnh tiến");
        System.out.println("3. Bài 3 - Nhập danh sách 2D/3D, tính tổng, thêm điểm đối xứng");
        System.out.println("4. Chạy dữ liệu mẫu của cả 3 bài");
        System.out.println("0. Thoát");
    }

    public static void chayTatCaDemo() {
        Bai1.chayDemo();
        Bai2.chayDemo();
        Bai3.chayDemo();
    }
}
