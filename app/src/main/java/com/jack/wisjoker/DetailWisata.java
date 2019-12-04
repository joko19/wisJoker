package com.jack.wisjoker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DetailWisata extends AppCompatActivity {

    public static final String EXTRA_WISATA = "extra_wisata";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);
    }
}
