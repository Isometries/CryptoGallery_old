package com.example.xavi.photocrypt.Activities;

/*
 *PhotoCrypt - an encrypted gallery for Android
 *Copyright (C) 2018 Thunder Gabriel <tgabriel@protonmail.com>
 *
 *This program is free software; you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation; either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program; if not, see http://www.gnu.org/licenses/.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.xavi.photocrypt.WhenLongClicked;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;
import com.example.xavi.photocrypt.R;
import com.example.xavi.photocrypt.WhenClicked;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class GalleryView extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;
    private static Queue<Album> albumQueue = new LinkedList<>();
    private int albumCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_view);
        try {
            populateView();
        } catch (GeneralSecurityException e) {
            AlertDialog alertDialog = new AlertDialog.Builder(GalleryView.this).create();
            alertDialog.setTitle("Decryption Error");
            alertDialog.setMessage("Invalid key");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            e.printStackTrace();
        }
    }

    public static void addToQueue(Album album)
    {
        albumQueue.add(album);
    }

    public void deleteAlbums(View v) throws GeneralSecurityException
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());

        photocrypt.deleteAlbums(albumQueue);
        albumQueue = new LinkedList<Album>();

        finish();
        startActivity(getIntent());
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        albumQueue = new LinkedList<>();
    }

    public void populateView() throws GeneralSecurityException
    {
        GridLayout grid = findViewById(R.id.grid);

        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());
        ArrayList<Album> albums = photocrypt.getAlbums();
        this.albumCount = albums.size();

        for (int i = 0; i < this.albumCount; i++)
        {
            ImageButton btn = new ImageButton(this);
            btn.setOnClickListener(new WhenClicked(albums.get(i).getTitle(), null, getApplicationContext()));
            btn.setOnLongClickListener(new WhenLongClicked(albums.get(i)));

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
        int n;
        Uri uris[];

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if (resultData.getClipData() != null){
                n = resultData.getClipData().getItemCount();
                uris = new Uri[n];

                for (int i = 0; i < n; i++){
                    uris[i] = resultData.getClipData().getItemAt(i).getUri();
                }
            } else {
                uris = new Uri[] {resultData.getData()};
            }

            try {
                photocrypt.addPhoto(uris, Integer.toString(this.albumCount+1), getApplicationContext());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        finish();
        startActivity(getIntent());
    }


    public void getPhotoFromSystem(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
