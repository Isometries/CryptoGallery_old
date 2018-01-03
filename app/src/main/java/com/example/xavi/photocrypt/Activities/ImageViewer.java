package com.example.xavi.photocrypt.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xavi.photocrypt.helpers.Crypto;
import com.example.xavi.photocrypt.R;
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
        } catch (IOException | NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private void showImage(String location) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException //name needs to change
    {
        ZoomageView img = (ZoomageView) findViewById(R.id.image_view);

        byte[] ciphertxt = Crypto.getFile(location);
        byte[] plaintxt = Crypto.decrypt(ciphertxt);
        Bitmap bitmap = BitmapFactory.decodeByteArray(plaintxt,0,plaintxt.length);
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        img.setImageBitmap(bitmap);
    }
}
