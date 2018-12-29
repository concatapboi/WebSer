package com.example.hien.webser.model;

public class NhanVien {
    private String ma,ten;
    private double hsl;

    public NhanVien(String ma, String ten, double hsl) {
        this.ma = ma;
        this.ten = ten;
        this.hsl = hsl;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public double getHsl() {
        return hsl;
    }

    public void setHsl(double hsl) {
        this.hsl = hsl;
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "ma='" + ma + '\'' +
                ", ten='" + ten + '\'' +
                ", hsl=" + hsl +
                '}';
    }
}
