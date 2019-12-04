package com.jack.wisjoker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailWisata extends AppCompatActivity {

    DataWisata dataWisata;
    private TextView tv_nama, tv_lokasi, tv_konten;
    private ImageView img_cover;
    public static final String EXTRA_WISATA = "extra_wisata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        dataWisata =getIntent().getParcelableExtra(EXTRA_WISATA);

        inisialisasi();
        setData();

    }

    private void inisialisasi() {
        tv_nama = findViewById(R.id.tv_judul_detail);
        tv_lokasi = findViewById(R.id.tv_lokasi_detail);
        tv_konten = findViewById(R.id.tv_konten_detail);
        img_cover = findViewById(R.id.img_cover_detail);
    }

    private void setData() {
        tv_nama.setText(dataWisata.namaTempat);
        tv_lokasi.setText(dataWisata.getLokasi());
        tv_konten.setText(dataWisata.getDeskripsi());
        Glide.with(DetailWisata.this)
                .load(dataWisata.getImgUrl())
                .into(img_cover);
    }
}
