package com.example.magal.tennistracker;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView;
    Button btnPhoto, btnSave;
    private String currentPic;
    //Matches match;
    Joueurs player1, player2;

    final int REQUEST_IMAGE_CAPTURE = 1, REQUEST_IMAGE_GALLERY = 2;
   // private DatabaseHelper mydb;
   // private String path2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //mydb = new DatabaseHelper(getApplicationContext());

        /*Intent intent = getIntent();
        player1 = new Joueurs(intent.getStringExtra("player1"));
        player2 = new Joueurs(intent.getStringExtra("player2"));*/

        //match = new Matches(player1.getId(), player2.getId());


        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnSave = (Button) findViewById(R.id.btnSave);
        imageView = (ImageView) findViewById(R.id.imageView);

        btnPhoto.setOnClickListener(this);

        /*btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });*/
    }

    /*When startActivityForResult() method is called, the Activity is launched.
    Once that Activity finishes, the calling Activity is presented with a result within
    its onActivityResult() handler*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnPhoto:
                Intent iPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(iPhoto.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(iPhoto,REQUEST_IMAGE_CAPTURE);
                    // Create the File where the photo should go
                   /* File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d("InMatchActivity", "Issue creating the file.");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.example.android.fileprovider",
                                photoFile);

                        String stringUri;
                        stringUri = photoURI.toString();
                        Log.d("InMatchActivity URI", stringUri);
                        iPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(iPhoto, REQUEST_IMAGE_CAPTURE);

                        // add picture to database
                        //dbh.createPicture(match.getId(), stringUri);
                        mydb.createPicture(match.getId(), mCurrentPhotoPath);
                        if (mCurrentPhotoPath != null) {
                            //galleryAddPic();
                            mCurrentPhotoPath = null;
                        }

                    }*/
                }
                break;

            case R.id.btnSave:
                //Intent iSave = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //iSave.setType("image/*");
                //startActivityForResult(iSave, REQUEST_IMAGE_GALLERY);
                Intent intent = new Intent(PhotoActivity.this, DisplayPhotos.class);
                startActivity(intent);
                break;

        }
    }

    /*String mCurrentPhotoPath;
    @RequiresApi(api = Build.VERSION_CODES.N)

    private File createImageFile() throws IOException {
        // Create an image file name

        Random rand = new Random();
        int nb = rand.nextInt(1000);

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_MATCH_" + nb + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir2 = getFilesDir();
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );
        File image2 = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir2
        );
        currentPic = imageFileName;

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        path2 = image2.getAbsolutePath();
        Log.d("InMatchActivity", mCurrentPhotoPath);
        Log.d("InMatchActivity", path2);
        return image;
        //return image2;

    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_IMAGE_CAPTURE){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }/*else if(requestCode == REQUEST_IMAGE_GALLERY){
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }*/
        }


    }
}
