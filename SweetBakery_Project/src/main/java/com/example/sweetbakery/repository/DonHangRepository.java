package com.example.sweetbakery.repository;
import com.example.sweetbakery.model.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface DonHangRepository extends JpaRepository<DonHang, Long> {
    List<DonHang> findByKhachHang_IdOrderByNgayDatDesc(Long khachHangId);
}
