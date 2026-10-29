package com.example.sweetbakery.controller;

import com.example.sweetbakery.repository.SanPhamRepository;
import com.example.sweetbakery.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final SanPhamRepository sanPhamRepository;
    public HomeController(SanPhamRepository sanPhamRepository) { this.sanPhamRepository = sanPhamRepository; }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String tuKhoa, Model model) {
        var products = sanPhamRepository.findByConHangTrueOrderByNoiBatDescIdAsc();
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            String q = tuKhoa.trim().toLowerCase();
            products = products.stream().filter(p -> p.getTen().toLowerCase().contains(q) || p.getDanhMuc().toLowerCase().contains(q)).toList();
        }
        model.addAttribute("sanPhams", products);
        model.addAttribute("tuKhoa", tuKhoa == null ? "" : tuKhoa);
        return "home";
    }

    @GetMapping("/san-pham/{id}")
    public String chiTiet(@PathVariable Long id, Model model) {
        SanPham p = sanPhamRepository.findById(id).orElse(null);
        if (p == null) return "redirect:/?khongTimThay=1";
        model.addAttribute("sanPham", p);
        return "product-detail";
    }
}
