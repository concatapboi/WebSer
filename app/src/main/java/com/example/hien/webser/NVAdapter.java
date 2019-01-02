package com.example.hien.webser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hien.webser.model.NhanVien;

import java.util.ArrayList;

public class NVAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<NhanVien> arr;
    public NVAdapter(Context context, ArrayList<NhanVien> arr) {
        super(context,0, arr);
        this.context = context;
        this.arr = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_nv,null);
            holder = new ViewHolder();
            holder.textViewMa = convertView.findViewById(R.id.txtID);
            holder.textViewTen = convertView.findViewById(R.id.txtTEN);
            holder.textViewHsLuong = convertView.findViewById(R.id.txtHSLUONG);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String ma = arr.get(position).getMa();
        String ten = arr.get(position).getTen();
        double hsluong = arr.get(position).getHsl();
        holder.textViewMa.setText(ma);
        holder.textViewTen.setText(ten);
        holder.textViewHsLuong.setText(hsluong+"");
        return convertView;
    }

    static class ViewHolder{
        TextView textViewMa;
        TextView textViewTen;
        TextView textViewHsLuong;
    }
}
