package com.example.sweetbakery.repository;
import com.example.sweetbakery.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    List<SanPham> findByConHangTrueOrderByNoiBatDescIdAsc();
}
