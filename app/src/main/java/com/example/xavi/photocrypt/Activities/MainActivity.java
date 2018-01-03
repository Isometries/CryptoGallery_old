package com.example.xavi.photocrypt.Activities;

import android.content.Intent;
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
    }

    public void enterPassword(View v) throws IOException, NoSuchAlgorithmException {
        Intent gallery = new Intent(this, GalleryView.class);
        TextView password = findViewById(R.id.passField);
        String pass = password.getText().toString();
        Crypto.setKey(pass, getApplicationContext());
        startActivity(gallery);
    }

}


