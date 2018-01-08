package com.example.xavi.photocrypt.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.xavi.photocrypt.helpers.Crypto;
import com.example.xavi.photocrypt.R;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

    public void enterPassword(View v) throws IOException, NoSuchAlgorithmException {
        Intent gallery = new Intent(this, GalleryView.class);
        TextView password = findViewById(R.id.passField);
        String pass = password.getText().toString();
        Crypto.setKey(pass, getApplicationContext());
        startActivity(gallery);
    }

}


