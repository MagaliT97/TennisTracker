package com.example.magal.tennistracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayPhotos extends AppCompatActivity {

    private DatabaseHelper mydb;
    LinearLayout picContainer;
    final int THUMBSIZE = 400;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_photos);

        mydb = new DatabaseHelper(getApplicationContext());
        //picContainer = (LinearLayout) findViewById(R.id.picContainer);


    }

    public void displayPics(){
        List<String> pics = new ArrayList<String>();
        pics = mydb.getPics();

        // if there aren't any pictures
        /*if(pics.isEmpty()){
            TextView nopic = new TextView(getApplicationContext());
            nopic.setText(getString(R.string.no_pic_msg));
            picContainer.addView(nopic);
        }else {*/
            // for each picture
            for (String path : pics) {
                ImageView picture = new ImageView(getApplicationContext());
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(path),
                        THUMBSIZE, THUMBSIZE);
                Bitmap fullpic = BitmapFactory.decodeFile(path);
                //fullpic = Bitmap.createScaledBitmap()
                //picture.setImageBitmap(ThumbImage);
                //picture.setMaxWidth(THUMBSIZE);
                picture.setImageBitmap(fullpic);
                picture.setPadding(0,10,0,0);
                picture.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                picture.setVisibility(View.VISIBLE);

                picContainer.addView(picture);
            //}
        }
    }
}
