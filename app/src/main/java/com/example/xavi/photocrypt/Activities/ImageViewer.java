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

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.xavi.photocrypt.R;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;
import com.example.xavi.photocrypt.zoomage.ZoomageView;


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
