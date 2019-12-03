package com.jack.wisjoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Admin Page");
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = usernameEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                if (user.equals("admin") && pass.equals("admin")){
                    Intent pageUpload = new Intent(AdminActivity.this, UploadActivity.class);
                    startActivity(pageUpload);
                }
                else {

                    Toast.makeText(getApplicationContext(),"Username dan Password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
