package com.example.foodcourt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Upload_image extends AppCompatActivity {

    Button choose_image, upload_image;
    ImageView profile_image;
    @Override

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.upload_image);

        choose_image = findViewById(R.id.choose_image);
        upload_image = findViewById(R.id.upload_image);
        profile_image = findViewById(R.id.profile);





    }


    private void selectImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

    }
}