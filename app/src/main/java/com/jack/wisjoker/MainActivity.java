package com.jack.wisjoker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_home, btn_tentang, btn_keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_home = findViewById(R.id.btnWisata);
        btn_tentang = findViewById(R.id.btnTentang);
        btn_keluar = findViewById(R.id.btnKeluar);

        btn_home.setOnClickListener(this);
        btn_tentang.setOnClickListener(this);
        btn_keluar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnWisata:
                Intent moveIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.btnTentang:
                Intent moveWithData = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(moveWithData);
                break;
            case R.id.btnKeluar:
                showDialog();
                break;
        }
    }
    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Keluar dari aplikasi?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar!")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        MainActivity.this.finish();
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
