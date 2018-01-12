package com.photocrypt.CryptoGallery;

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

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.photocrypt.CryptoGallery.Activities.AlbumView;
import com.photocrypt.CryptoGallery.Activities.ImageViewer;

public class WhenClicked implements View.OnClickListener{

    private String title, location;


    public WhenClicked(String title, String location, Context context)
    {
        this.title = title;
        this.location = location;
    }

    @Override
    public void onClick(View v)
    {
        if (location == null) {
            Intent album = new Intent(v.getContext(), AlbumView.class);
            album.putExtra("TITLE", this.title);
            v.getContext().startActivity(album);

        } else {
            Intent image = new Intent(v.getContext(), ImageViewer.class);
            image.putExtra("LOCATION", this.location);
            v.getContext().startActivity(image);
        }
    }
}
