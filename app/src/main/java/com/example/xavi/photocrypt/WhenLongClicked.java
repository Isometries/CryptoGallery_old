package com.example.xavi.photocrypt;


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
