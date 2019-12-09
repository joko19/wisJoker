package com.jack.wisjoker.admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jack.wisjoker.DataWisata;
import com.jack.wisjoker.R;
import com.jack.wisjoker.UploadActivity;

import java.io.IOException;

import static com.jack.wisjoker.admin.DetailAdmin.EXTRA_WISATA;

public class UpdateAdmin extends AppCompatActivity {

    String storagePath = "images";
    String Database_Path = "All_Image_Uploads_Database";
    private EditText edtName, edtLocation, edtDescription;
    private ImageView cover;
    private Button btnChoose, btnUpload;
    DataWisata dataWisata;
    // Creating URI.
    Uri FilePathUri;
    private String TempName, TempLokasi, TempDeskripsi;
    // Getting image name from EditText and store into string variable.

    // Creating StorageReference and DatabaseReference object.
    StorageReference storageReference;
    DatabaseReference databaseReference;

    // Image request code for onActivityResult() .
    int Image_Request_Code = 7;

    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().setTitle("Update Data");
        edtName = findViewById(R.id.edt_NamaTempat);
        edtLocation = findViewById(R.id.edt_lokasi);
        edtDescription = findViewById(R.id.edt_deskripsi);
        cover = findViewById(R.id.imgCover);
        btnChoose = findViewById(R.id.ButtonChooseImage);
        btnUpload = findViewById(R.id.ButtonUploadImage);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        progressDialog = new ProgressDialog(UpdateAdmin.this);

        //parsing data lama
        dataWisata = getIntent().getParcelableExtra(EXTRA_WISATA);
        edtName.setText(dataWisata.getNamaTempat());
        edtDescription.setText(dataWisata.getDeskripsi());
        edtLocation.setText(dataWisata.getLokasi());

        //simpan data sementara

        Glide.with(UpdateAdmin.this)
                .load(dataWisata.getImgUrl())
                .into(cover);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImageFileToFirebaseStorage();
                Intent pindah = new Intent(UpdateAdmin.this, HomeAdmin.class);
                startActivity(pindah);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                cover.setImageBitmap(bitmap);

                // After selecting image change choose button above text.
                btnChoose.setText("Image Selected");

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.

        if (FilePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Image is Uploading...");

            final String TempName = edtName.getText().toString().trim();
            final String TempLokasi = edtLocation.getText().toString().trim();
            final String TempDeskripsi = edtDescription.getText().toString().trim();
            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            final StorageReference storageReference2nd = storageReference.child(storagePath + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Hiding the progressDialog after done uploading.
                            progressDialog.dismiss();

                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
//
//                            @SuppressWarnings("VisibleForTests")
//                            DataWisata imageUploadInfo = new DataWisata(TempName, TempLokasi, TempDeskripsi, taskSnapshot.getUploadSessionUri().toString());
//
//                            // Getting image upload ID.
//                            final String ImageUploadId = databaseReference.push().getKey();
//
//                            // Adding image upload id s child element into databaseReference.
//                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);


                            //fix
                            storageReference2nd.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    DataWisata upload = new DataWisata(TempName, TempLokasi, TempDeskripsi, url);
//                                    String uploadId = databaseReference.push().getKey();
                                    String uploadId = dataWisata.namaTempat;
                                    databaseReference.child(uploadId).setValue(upload);
                                }
                            });


                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(UpdateAdmin.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setTitle("Image is Uploading...");

                        }
                    });
        }
        else {
            final String TempName = edtName.getText().toString().trim();
            final String TempLokasi = edtLocation.getText().toString().trim();
            final String TempDeskripsi = edtDescription.getText().toString().trim();
                    DataWisata upload = new DataWisata(TempName, TempLokasi, TempDeskripsi, dataWisata.getImgUrl());
//                                    String uploadId = databaseReference.push().getKey();
                    String uploadId = dataWisata.namaTempat;
                    databaseReference.child(uploadId).setValue(upload);
                }
            }
        }

