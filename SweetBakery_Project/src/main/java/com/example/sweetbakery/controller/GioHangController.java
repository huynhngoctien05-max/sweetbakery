package com.example.sweetbakery.controller;

import com.example.sweetbakery.model.DonHang;
import com.example.sweetbakery.model.KhachHang;
import com.example.sweetbakery.model.SanPham;
import com.example.sweetbakery.repository.DonHangRepository;
import com.example.sweetbakery.repository.KhachHangRepository;
import com.example.sweetbakery.repository.SanPhamRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@Controller
public class GioHangController {
    private final SanPhamRepository sanPhamRepository;
    private final KhachHangRepository khachHangRepository;
    private final DonHangRepository donHangRepository;
    public GioHangController(SanPhamRepository s, KhachHangRepository k, DonHangRepository d) { sanPhamRepository=s; khachHangRepository=k; donHangRepository=d; }

    @SuppressWarnings("unchecked") private Map<Long,Integer> cart(HttpSession session) {
        Object saved=session.getAttribute("gioHang");
        if (saved instanceof Map<?,?>) return (Map<Long,Integer>) saved;
        Map<Long,Integer> map=new LinkedHashMap<>(); session.setAttribute("gioHang",map); return map;
    }
    @PostMapping("/gio-hang/them/{id}") public String them(@PathVariable Long id, @RequestParam(defaultValue="1") int soLuong, HttpSession session) {
        if (sanPhamRepository.existsById(id)) { Map<Long,Integer> c=cart(session); c.put(id, Math.min(99, c.getOrDefault(id,0)+Math.max(1,Math.min(soLuong,20)))); }
        return "redirect:/gio-hang";
    }
    @GetMapping("/gio-hang") public String xemGioHang(HttpSession session, Model model) { populateCart(session,model); return "gio-hang"; }
    @PostMapping("/gio-hang/cap-nhat") public String capNhat(@RequestParam Map<String,String> params, HttpSession session) {
        Map<Long,Integer> c=cart(session);
        for (Long id : new ArrayList<>(c.keySet())) { try { int q=Integer.parseInt(params.getOrDefault("soLuong_"+id,"1")); if(q<=0)c.remove(id); else c.put(id,Math.min(q,99)); } catch(Exception ignored){} }
        return "redirect:/gio-hang";
    }
    @PostMapping("/gio-hang/xoa/{id}") public String xoa(@PathVariable Long id, HttpSession session) { cart(session).remove(id); return "redirect:/gio-hang"; }
    private void populateCart(HttpSession session, Model model) {
        Map<Long,Integer> c=cart(session); List<Map<String,Object>> items=new ArrayList<>(); BigDecimal total=BigDecimal.ZERO;
        for (var e:c.entrySet()) { Optional<SanPham> op=sanPhamRepository.findById(e.getKey()); if(op.isEmpty())continue; SanPham p=op.get(); int q=e.getValue(); BigDecimal subtotal=p.getGia().multiply(BigDecimal.valueOf(q)); total=total.add(subtotal); Map<String,Object> row=new HashMap<>(); row.put("sanPham",p); row.put("soLuong",q); row.put("thanhTien",subtotal); items.add(row); }
        model.addAttribute("items",items); model.addAttribute("tongTien",total);
    }
    @GetMapping("/thanh-toan") public String trangThanhToan(HttpSession session, Model model) {
        if (!(session.getAttribute("khachHangId") instanceof Long)) return "redirect:/dang-nhap";
        populateCart(session,model); if (cart(session).isEmpty()) return "redirect:/gio-hang"; return "thanh-toan";
    }
    @PostMapping("/thanh-toan") public String datHang(@RequestParam String diaChi, HttpSession session, Model model) {
        Object userId=session.getAttribute("khachHangId"); if (!(userId instanceof Long)) return "redirect:/dang-nhap";
        Map<Long,Integer> c=cart(session); if(c.isEmpty()) return "redirect:/gio-hang";
        if(diaChi==null || diaChi.trim().length()<8 || diaChi.trim().length()>250) { populateCart(session,model); model.addAttribute("loi","Vui lòng nhập địa chỉ giao hàng từ 8 đến 250 ký tự."); return "thanh-toan"; }
        KhachHang user=khachHangRepository.findById((Long)userId).orElse(null); if(user==null)return "redirect:/dang-nhap";
        BigDecimal total=BigDecimal.ZERO; StringBuilder details=new StringBuilder();
        for(var e:c.entrySet()) { SanPham p=sanPhamRepository.findById(e.getKey()).orElse(null); if(p==null)continue; BigDecimal sub=p.getGia().multiply(BigDecimal.valueOf(e.getValue())); total=total.add(sub); if(details.length()>0)details.append("; "); details.append(p.getTen()).append(" x ").append(e.getValue()).append(" = ").append(sub.toPlainString()).append(" VND"); }
        if(total.compareTo(BigDecimal.ZERO)<=0)return "redirect:/gio-hang";
        DonHang order=new DonHang(); order.setKhachHang(user); order.setTongTien(total); order.setChiTiet(details.toString()); order.setDiaChiGiaoHang(diaChi.trim()); DonHang saved=donHangRepository.save(order);
        c.clear(); session.setAttribute("donHangVuaDat",saved.getId()); return "redirect:/don-hang-thanh-cong";
    }
    @GetMapping("/don-hang-thanh-cong") public String thanhCong(HttpSession session, Model model) { model.addAttribute("maDonHang",session.getAttribute("donHangVuaDat")); return "don-hang-thanh-cong"; }
}
