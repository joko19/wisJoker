package com.jack.wisjoker.admin;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jack.wisjoker.AdminActivity;
import com.jack.wisjoker.DataWisata;
import com.jack.wisjoker.HomeActivity;
import com.jack.wisjoker.MainActivity;
import com.jack.wisjoker.R;
import com.jack.wisjoker.UploadActivity;
import com.jack.wisjoker.WisataAdapter;

import java.util.ArrayList;

public class HomeAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataWisata> dataWisata;
    TextView cekKoneksi;
    ProgressBar progressBar;
    private Button post;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("All_Image_Uploads_Database");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);
//        cekKoneksi = findViewById(R.id.tv_cek_koneksi);
//        progressBar = findViewById(R.id.progressBar);
        post = findViewById(R.id.posting);
        recyclerView = findViewById(R.id.rv_home);
        getSupportActionBar().setTitle("Wisata Mojokerto");
        getData();
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(HomeAdmin.this, UploadActivity.class);
                startActivity(upload);
            }
        });
    }

    private void getData() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataWisata = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DataWisata wisata = snapshot.getValue(DataWisata.class);

                    dataWisata.add(wisata);
                }
                AdminAdapter adminAdapter = new AdminAdapter(getApplicationContext(), dataWisata);
                if (adminAdapter != null) {
//                progressBar.setVisibility(View.GONE);
//                cekKoneksi.setVisibility(View.GONE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adminAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(valueEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi) {
        showDialog();
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Logout!!");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar dari Akun!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        Intent loginIntent = new Intent(HomeAdmin.this, HomeActivity.class);
                        startActivity(loginIntent);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}
