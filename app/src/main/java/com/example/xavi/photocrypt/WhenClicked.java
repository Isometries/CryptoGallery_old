package com.example.xavi.photocrypt;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.xavi.photocrypt.Activities.AlbumView;
import com.example.xavi.photocrypt.Activities.ImageViewer;

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
            Log.w("img loc", this.location);
            v.getContext().startActivity(image);
        }
    }
}
