package com.example.sweetbakery;

import com.example.sweetbakery.model.SanPham;
import com.example.sweetbakery.repository.SanPhamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class SweetbakeryApplication {
    public static void main(String[] args) {
        SpringApplication.run(SweetbakeryApplication.class, args);
    }

    @Bean
    CommandLineRunner seedProducts(SanPhamRepository repository) {
        return args -> {
            if (repository.count() > 0) return;
            add(repository, "Bánh kem dâu tây", "Bánh kem", "Bánh bông lan mềm mịn, kem tươi và dâu tây mọng nước. Phù hợp sinh nhật và những dịp đặc biệt.", "389000", "https://images.unsplash.com/photo-1578985545062-69928b1d9587?auto=format&fit=crop&w=1000&q=85", true);
            add(repository, "Bánh kem chocolate", "Bánh chocolate", "Chocolate đậm vị kết hợp lớp kem mượt mà, dành cho người yêu vị cacao.", "429000", "https://images.unsplash.com/photo-1606313564200-e75d5e30476c?auto=format&fit=crop&w=1000&q=85", true);
            add(repository, "Bánh kem hoa pastel", "Bánh sinh nhật", "Bánh kem trang trí hoa pastel nhẹ nhàng, thích hợp tặng người thân và bạn bè.", "499000", "https://images.unsplash.com/photo-1557979619-445218f326b9?auto=format&fit=crop&w=1000&q=85", true);
            add(repository, "Cheesecake việt quất", "Cheesecake", "Cheesecake béo nhẹ, chua dịu cùng sốt việt quất thơm ngon.", "359000", "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?auto=format&fit=crop&w=1000&q=85", false);
            add(repository, "Bánh tiramisu", "Bánh lạnh", "Tiramisu mềm mịn với cà phê và cacao, mang phong vị Ý cổ điển.", "329000", "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?auto=format&fit=crop&w=1000&q=85", false);
            add(repository, "Bánh kem matcha", "Bánh kem", "Hương trà xanh thanh nhẹ kết hợp kem tươi, cân bằng vị ngọt.", "419000", "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?auto=format&fit=crop&w=1000&q=85", false);
            add(repository, "Bánh mousse xoài", "Bánh lạnh", "Mousse xoài thơm mát, kết cấu nhẹ và lớp phủ trái cây tươi.", "339000", "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?auto=format&fit=crop&w=1000&q=85", false);
            add(repository, "Bánh cupcake hộp 6", "Cupcake", "Hộp sáu cupcake trang trí xinh xắn, thích hợp cho tiệc nhỏ.", "199000", "https://images.unsplash.com/photo-1486427944299-d1955d23e34d?auto=format&fit=crop&w=1000&q=85", false);
        };
    }

    private void add(SanPhamRepository repo, String ten, String danhMuc, String moTa, String gia, String anh, boolean noiBat) {
        SanPham p = new SanPham();
        p.setTen(ten); p.setDanhMuc(danhMuc); p.setMoTa(moTa); p.setGia(new BigDecimal(gia));
        p.setAnh(anh); p.setNoiBat(noiBat); p.setConHang(true); repo.save(p);
    }
}
