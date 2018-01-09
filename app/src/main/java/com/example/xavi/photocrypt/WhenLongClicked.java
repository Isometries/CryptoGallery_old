package com.example.xavi.photocrypt;

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

import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.example.xavi.photocrypt.Activities.AlbumView;
import com.example.xavi.photocrypt.Activities.GalleryView;

public class WhenLongClicked implements View.OnLongClickListener{

    private Photo photo;
    private Album album;

    public WhenLongClicked(Photo photo)
    {
        this.photo = photo;
    }

    public WhenLongClicked(Album album)
    {
        this.album = album;
    }

    @Override
    public boolean onLongClick(View v)
    {
        if (v instanceof ImageButton) {

            ((ImageButton)v).setColorFilter(Color.argb(150, 255, 255, 255));

            if (this.photo != null){
                AlbumView.addToQueue(this.photo);
            }
            if (this.album != null)
            {
                GalleryView.addToQueue(this.album);
            }
        }
        return true;
    }
}
