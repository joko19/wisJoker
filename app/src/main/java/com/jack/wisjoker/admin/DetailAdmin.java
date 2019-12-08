package com.jack.wisjoker.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jack.wisjoker.AdminActivity;
import com.jack.wisjoker.DataWisata;
import com.jack.wisjoker.DetailWisata;
import com.jack.wisjoker.HomeActivity;
import com.jack.wisjoker.R;
import com.jack.wisjoker.UploadActivity;

public class DetailAdmin extends AppCompatActivity {

    DataWisata dataWisata;
    private TextView tv_nama, tv_lokasi, tv_konten;
    private ImageView img_cover;
    public static final String EXTRA_WISATA = "extra_wisata";

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("All_Image_Uploads_Database");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_wisata);

        dataWisata = getIntent().getParcelableExtra(EXTRA_WISATA);
        getSupportActionBar().setTitle(dataWisata.getNamaTempat());
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
        Glide.with(DetailAdmin.this)
                .load(dataWisata.getImgUrl())
                .into(img_cover);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setMode(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    public void setMode(int selectedMode) {
        switch (selectedMode) {
            case R.id.delete:
                deletedData(dataWisata.namaTempat);
                break;
            case R.id.update:
                Intent updatedata = new Intent(DetailAdmin.this, UpdateAdmin.class);
                updatedata.putExtra(DetailAdmin.EXTRA_WISATA, dataWisata);
                startActivity(updatedata);
                break;
        }
    }

    private void deletedData(String userId) {
        reference.child(userId).removeValue();

        Toast.makeText(DetailAdmin.this, "Data berhasil dihapus", Toast.LENGTH_LONG).show();
        Intent home = new Intent(DetailAdmin.this, HomeAdmin.class);
        startActivity(home);
    }
}

