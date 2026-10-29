package com.example.sweetbakery.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "khach_hang", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class KhachHang {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String hoTen;
    @Column(nullable = false, unique = true, length = 160) private String email;
    @Column(nullable = false, length = 100) private String matKhau;
    @Column(nullable = false, length = 20) private String soDienThoai;
    @Column(nullable = false) private LocalDateTime ngayTao = LocalDateTime.now();

    public Long getId() { return id; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public LocalDateTime getNgayTao() { return ngayTao; }
}
