package com.example.sweetbakery.repository;
import com.example.sweetbakery.model.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {
    Optional<KhachHang> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
