package com.akb.uas_akb_10118341;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akb.uas_akb_10118341.adapter.WisataAdapter;
import com.akb.uas_akb_10118341.model.Wisata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private List<Wisata> wisataList;
    private WisataAdapter wisataAdapter;
    private RecyclerView rv_wisata;
    DatabaseReference database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv_wisata = view.findViewById(R.id.rv_wisata);

        read();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_wisata.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void read() {
        wisataList = new ArrayList<>();

        database = FirebaseDatabase.getInstance("https://wisata-ce306-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("wisata");

        System.out.println("test: " + database);

        wisataAdapter = new WisataAdapter(wisataList, this.getContext());
        rv_wisata.setAdapter(wisataAdapter);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    try {
                        Wisata wisata = dataSnapshot.getValue(Wisata.class);
                        wisataList.add(wisata);
                    } catch(Exception error) {
                        Toast.makeText(getActivity(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                }
                wisataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
/**
 Nama       : Muhammad Rijal Sulaeman
 Kelas      : IF-8
 NIM        : 10118341
 Tanggal    : 13 Agustus 2021
 **/