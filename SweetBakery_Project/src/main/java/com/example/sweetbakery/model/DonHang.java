package com.example.sweetbakery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "don_hang")
public class DonHang {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "khach_hang_id") private KhachHang khachHang;
    @Column(nullable = false, precision = 12, scale = 0) private BigDecimal tongTien;
    @Column(nullable = false, length = 2500) private String chiTiet;
    @Column(nullable = false, length = 250) private String diaChiGiaoHang;
    @Column(nullable = false, length = 20) private String trangThai = "Đã tiếp nhận";
    @Column(nullable = false) private LocalDateTime ngayDat = LocalDateTime.now();

    public Long getId() { return id; }
    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getChiTiet() { return chiTiet; }
    public void setChiTiet(String chiTiet) { this.chiTiet = chiTiet; }
    public String getDiaChiGiaoHang() { return diaChiGiaoHang; }
    public void setDiaChiGiaoHang(String diaChiGiaoHang) { this.diaChiGiaoHang = diaChiGiaoHang; }
    public String getTrangThai() { return trangThai; }
    public LocalDateTime getNgayDat() { return ngayDat; }
}
