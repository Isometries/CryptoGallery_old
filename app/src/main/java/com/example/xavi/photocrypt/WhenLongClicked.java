package com.example.xavi.photocrypt;


import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import com.example.xavi.photocrypt.Activities.AlbumView;

public class WhenLongClicked implements View.OnLongClickListener{

    private Photo photo;

    public WhenLongClicked(Photo photo)
    {
        this.photo = photo;
    }

    @Override
    public boolean onLongClick(View v)
    {
        if (v instanceof ImageButton) {
            ((ImageButton)v).setColorFilter(Color.argb(150, 255, 255, 255));
            AlbumView.addToDelete(photo);
        }
        return true;
    }
}
