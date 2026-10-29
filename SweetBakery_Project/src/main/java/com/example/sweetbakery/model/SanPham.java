package com.example.sweetbakery.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "san_pham")
public class SanPham {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 120) private String ten;
    @Column(nullable = false, length = 80) private String danhMuc;
    @Column(nullable = false, length = 1500) private String moTa;
    @Column(nullable = false, precision = 12, scale = 0) private BigDecimal gia;
    @Column(length = 1000) private String anh;
    private boolean noiBat;
    private boolean conHang = true;

    public Long getId() { return id; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDanhMuc() { return danhMuc; }
    public void setDanhMuc(String danhMuc) { this.danhMuc = danhMuc; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }
    public String getAnh() { return anh; }
    public void setAnh(String anh) { this.anh = anh; }
    public boolean isNoiBat() { return noiBat; }
    public void setNoiBat(boolean noiBat) { this.noiBat = noiBat; }
    public boolean isConHang() { return conHang; }
    public void setConHang(boolean conHang) { this.conHang = conHang; }
}
