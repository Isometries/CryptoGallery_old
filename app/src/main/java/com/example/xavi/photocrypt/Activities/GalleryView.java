package com.example.xavi.photocrypt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.xavi.photocrypt.Album;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;
import com.example.xavi.photocrypt.R;
import com.example.xavi.photocrypt.WhenClicked;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class GalleryView extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private int albumCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        try {
            populateView();
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_gallery_view);
        try {
            populateView();
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }



    public void populateView() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException //next step here
    {
        GridLayout grid = findViewById(R.id.grid);

        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());
        photocrypt.getAlbums();
        ArrayList<Album> albums = photocrypt.getAlbums();
        this.albumCount = albums.size();

        for (int i = 0; i < this.albumCount; i++)
        {
            ImageButton btn = new ImageButton(this);
            btn.setOnClickListener(new WhenClicked(albums.get(i).getTitle(), null, getApplicationContext()));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(320,448);
            params.leftMargin = 35;
            params.bottomMargin = 25;
            btn.setLayoutParams(params);

            Bitmap bmp = PhotoCrypt.byteArrayToBitmap(albums.get(i).getThumbnail());
            btn.setImageBitmap(bmp);
            grid.addView(btn);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());
        Uri uri;

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            uri = resultData.getData();

            try {
                photocrypt.addPhoto(uri, Integer.toString(this.albumCount+1), getApplicationContext());

            } catch (IOException | URISyntaxException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | NoSuchPaddingException | IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            populateView();
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public void getPhotoFromSystem(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*)");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
