package com.jack.wisjoker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jack.wisjoker.ui.login.LoginActivity;

import java.security.AccessController;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<DataWisata> dataWisata;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("All_Image_Uploads_Database");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.rv_home);
        getSupportActionBar().setTitle("Wisata Mojokerto");
        getData();
    }

    private void getData() { ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            dataWisata = new ArrayList<>();
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                DataWisata berita =snapshot.getValue(DataWisata.class);

                dataWisata.add(berita);
            }
            WisataAdapter wisataAdapter = new WisataAdapter(getApplicationContext(), dataWisata);
            if (wisataAdapter != null){
//                progressBar.setVisibility(View.GONE);
//                cekKoneksi.setVisibility(View.GONE);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(wisataAdapter);
            } else {

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
        getMenuInflater().inflate(R.menu.upload, menu);
        return true;
    }

    public void onComposeAction(MenuItem mi){
        Intent loginIntent = new Intent(HomeActivity.this, AdminActivity.class);
        startActivity(loginIntent);
    }
}
