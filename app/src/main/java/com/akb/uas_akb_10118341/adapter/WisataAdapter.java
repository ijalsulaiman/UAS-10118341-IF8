package com.akb.uas_akb_10118341.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akb.uas_akb_10118341.R;
import com.akb.uas_akb_10118341.model.Wisata;
import com.bumptech.glide.Glide;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter {

    private List<Wisata> wisataList;
    Context mContext;

    public WisataAdapter(List<Wisata> wisataList, Context context) {
        this.wisataList = wisataList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wisata, parent, false);

        return new PariwisataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Wisata wisata = wisataList.get(position);
        ((PariwisataViewHolder) holder).bindView(wisata);

        Glide.with(mContext).load(wisata.getGambar()).into(((PariwisataViewHolder) holder).iv_image);
    }

    @Override
    public int getItemCount() {
        return wisataList.size();
    }

    private class PariwisataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView iv_image;
        private TextView tv_nama;
        private TextView tv_alamat;


        public PariwisataViewHolder(View itemView) {
            super(itemView);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_nama = (TextView) itemView.findViewById(R.id.tv_nama);
            tv_alamat = (TextView) itemView.findViewById(R.id.tv_alamat);


            itemView.setOnClickListener(this);
        }

        public void bindView(Wisata wisata) {
            tv_nama.setText(wisata.getNama());
            tv_alamat.setText(wisata.getAlamat());

        }

        @Override
        public void onClick(View v) {
            System.out.println(tv_nama.getText());
        }
    }
}
/**
 Nama       : Muhammad Rijal Sulaeman
 Kelas      : IF-8
 NIM        : 10118341
 Tanggal    : 14 Agustus 2021
 **/