package com.example.hien.webser.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NhanVien implements Parcelable {
    private String ma,ten;
    private double hsl;

    public NhanVien(String ma, String ten, double hsl) {
        this.ma = ma;
        this.ten = ten;
        this.hsl = hsl;
    }

    protected NhanVien(Parcel in) {
        ma = in.readString();
        ten = in.readString();
        hsl = in.readDouble();
    }

    public static final Creator<NhanVien> CREATOR = new Creator<NhanVien>() {
        @Override
        public NhanVien createFromParcel(Parcel in) {
            return new NhanVien(in);
        }

        @Override
        public NhanVien[] newArray(int size) {
            return new NhanVien[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeDouble(hsl);
    }
}
