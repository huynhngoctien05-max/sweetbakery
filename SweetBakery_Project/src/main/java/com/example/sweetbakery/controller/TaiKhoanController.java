package com.example.sweetbakery.controller;

import com.example.sweetbakery.model.KhachHang;
import com.example.sweetbakery.repository.KhachHangRepository;
import com.example.sweetbakery.repository.DonHangRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class TaiKhoanController {
    private final KhachHangRepository khachHangRepository;
    private final DonHangRepository donHangRepository;
    private final PasswordEncoder passwordEncoder;
    public TaiKhoanController(KhachHangRepository k, DonHangRepository d, PasswordEncoder p) { this.khachHangRepository=k; this.donHangRepository=d; this.passwordEncoder=p; }

    @GetMapping("/dang-ky") public String trangDangKy() { return "dang-ky"; }
    @PostMapping("/dang-ky")
    public String dangKy(@RequestParam String hoTen, @RequestParam String email, @RequestParam String soDienThoai,
                         @RequestParam String matKhau, @RequestParam String xacNhanMatKhau, Model model) {
        hoTen=hoTen.trim(); email=email.trim().toLowerCase(); soDienThoai=soDienThoai.trim();
        if (hoTen.length()<2 || !email.matches("^[^\s@]+@[^\s@]+\.[^\s@]+$") || !soDienThoai.matches("[0-9+() .-]{9,20}") || matKhau.length()<8) {
            model.addAttribute("loi", "Vui lòng nhập họ tên, email, số điện thoại hợp lệ và mật khẩu ít nhất 8 ký tự."); return "dang-ky";
        }
        if (!matKhau.equals(xacNhanMatKhau)) { model.addAttribute("loi", "Mật khẩu xác nhận chưa khớp."); return "dang-ky"; }
        if (khachHangRepository.existsByEmailIgnoreCase(email)) { model.addAttribute("loi", "Email này đã được đăng ký."); return "dang-ky"; }
        KhachHang k = new KhachHang(); k.setHoTen(hoTen); k.setEmail(email); k.setSoDienThoai(soDienThoai); k.setMatKhau(passwordEncoder.encode(matKhau)); khachHangRepository.save(k);
        model.addAttribute("thanhCong", "Đăng ký thành công. Bạn có thể đăng nhập ngay."); return "dang-nhap";
    }

    @GetMapping("/dang-nhap") public String trangDangNhap() { return "dang-nhap"; }
    @PostMapping("/dang-nhap")
    public String dangNhap(@RequestParam String email, @RequestParam String matKhau, HttpSession session, Model model) {
        var user=khachHangRepository.findByEmailIgnoreCase(email.trim());
        if (user.isEmpty() || !passwordEncoder.matches(matKhau, user.get().getMatKhau())) { model.addAttribute("loi", "Email hoặc mật khẩu chưa đúng."); return "dang-nhap"; }
        session.setAttribute("khachHangId", user.get().getId()); session.setAttribute("khachHangTen", user.get().getHoTen());
        return "redirect:/";
    }

    @GetMapping("/dang-xuat") public String dangXuat(HttpSession session) { session.removeAttribute("khachHangId"); session.removeAttribute("khachHangTen"); return "redirect:/"; }
    @GetMapping("/quen-mat-khau") public String trangQuenMatKhau() { return "quen-mat-khau"; }
    @PostMapping("/quen-mat-khau")
    public String quenMatKhau(@RequestParam String email, @RequestParam String soDienThoai, @RequestParam String matKhauMoi, Model model) {
        var user=khachHangRepository.findByEmailIgnoreCase(email.trim());
        if (user.isEmpty() || !user.get().getSoDienThoai().equals(soDienThoai.trim())) { model.addAttribute("loi", "Không tìm thấy tài khoản khớp với email và số điện thoại."); return "quen-mat-khau"; }
        if (matKhauMoi.length()<8) { model.addAttribute("loi", "Mật khẩu mới phải có ít nhất 8 ký tự."); return "quen-mat-khau"; }
        user.get().setMatKhau(passwordEncoder.encode(matKhauMoi)); khachHangRepository.save(user.get());
        model.addAttribute("thanhCong", "Đổi mật khẩu thành công. Bạn có thể đăng nhập."); return "dang-nhap";
    }

    @GetMapping("/tai-khoan/don-hang")
    public String lichSuDonHang(HttpSession session, Model model) {
        Object id=session.getAttribute("khachHangId"); if (!(id instanceof Long)) return "redirect:/dang-nhap";
        model.addAttribute("donHangs", donHangRepository.findByKhachHang_IdOrderByNgayDatDesc((Long) id)); return "don-hang";
    }
}
