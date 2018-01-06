package com.example.xavi.photocrypt.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.xavi.photocrypt.helpers.Crypto;
import com.example.xavi.photocrypt.R;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;
import com.jsibbold.zoomage.ZoomageView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class ImageViewer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_image_viewer);

        Bundle fromAlbumView = getIntent().getExtras();
        String location = null;
        if (fromAlbumView != null) {
            location = fromAlbumView.getString("LOCATION");
        }
        try {
            showImage(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showImage(String location) throws Exception
    {
        ZoomageView img = (ZoomageView) findViewById(R.id.image_view);

        Bitmap bitmap = PhotoCrypt.getBitmapfromLocation(location);
        img.setImageBitmap(bitmap);
    }
}
