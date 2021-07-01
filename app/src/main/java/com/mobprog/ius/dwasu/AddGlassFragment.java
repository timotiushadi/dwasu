package com.mobprog.ius.dwasu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AddGlassFragment extends Fragment {

    TextView mangkaTambahGelas;
    int jumlah;

    public AddGlassFragment() {
        // Required empty public constructor
    }

    public static AddGlassFragment newInstance() {

        return new AddGlassFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_glass_dialog,
                container, false);

        ImageButton mbtnTambah = (ImageButton) rootView.findViewById(R.id.btnTambah);
        mbtnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = Integer.parseInt(mangkaTambahGelas.getText().toString());
                jumlah++;
                mangkaTambahGelas.setText(jumlah+"");
            }
        });

        ImageButton mbtnKurang = (ImageButton) rootView.findViewById(R.id.btnKurang);
        mbtnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah = Integer.parseInt(mangkaTambahGelas.getText().toString());
                jumlah--;
                mangkaTambahGelas.setText(jumlah+"");
            }
        });

        Button mbtnConfirmInputGlass = (Button) rootView.findViewById(R.id.btnConfirmInputGlass);
        mbtnConfirmInputGlass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("jumlahGelas", jumlah);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_glass_dialog, container, false);
    }
}