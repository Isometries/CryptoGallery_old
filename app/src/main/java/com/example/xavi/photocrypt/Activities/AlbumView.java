package com.example.xavi.photocrypt.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.xavi.photocrypt.Photo;
import com.example.xavi.photocrypt.WhenLongClicked;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;
import com.example.xavi.photocrypt.R;
import com.example.xavi.photocrypt.WhenClicked;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class AlbumView extends AppCompatActivity {

    private String title;
    private static final int READ_REQUEST_CODE = 42;
    private static Queue<Photo> photoQueue = new LinkedList<>(); //maybe not the greatest solution to have this static
    private static int Xpos, Ypos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_view);
        Bundle extra_title = getIntent().getExtras();
        this.title = extra_title.getString("TITLE");

        try {
            populateView();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        photoQueue = new LinkedList<>();
    }

    private void populateView() throws GeneralSecurityException
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());

        GridLayout grid = findViewById(R.id.grid2);

        photocrypt.getAlbum(this.title);
        ArrayList<Photo> photos = photocrypt.getAlbum(this.title);
        int photoCount = photos.size();

        for (int i = 0; i < photoCount; i++)
        {
            ImageButton btn = new ImageButton(this);
            btn.setOnClickListener(new WhenClicked(null, photos.get(i).getLocation(), getApplicationContext()));
            btn.setOnLongClickListener(new WhenLongClicked(photos.get(i)));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(320,448);
            params.leftMargin = 35;
            params.bottomMargin = 25;
            btn.setLayoutParams(params);

            Bitmap bmp = PhotoCrypt.byteArrayToBitmap(photos.get(i).getThumbnail());
            btn.setImageBitmap(bmp);
            grid.addView(btn);
        }
    }

    public static void addToQueue(Photo photo)
    {
        photoQueue.add(photo);
    }

    public void deleteAll(View v)
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());

        photocrypt.deletePhotos(photoQueue);

        finish();
        startActivity(getIntent());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());
        Uri uri;

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            uri = resultData.getData();

            try {
                photocrypt.addPhoto(uri, this.title, getApplicationContext());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            populateView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_album_view);
        try {
            Log.w("onresume", Integer.toString(Ypos));

            final ScrollView scroller = findViewById(R.id.scrollView2);
            populateView();
            scroller.post(new Runnable() {
                @Override
                public void run() {
                    scroller.scrollTo(Xpos, Ypos);
                }
            });

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public void export(View v) throws Exception
    {
        PhotoCrypt photocrypt = new PhotoCrypt(getApplicationContext());
        photocrypt.exportPhotos(photoQueue);
        photoQueue = new LinkedList<>();
        finish();
        startActivity(getIntent());

    }

    @Override
    public void onStop()
    {
        super.onStop();
        ScrollView scroller = findViewById(R.id.scrollView2);
        Xpos = scroller.getScrollX();
        Ypos = scroller.getScrollY();
    }

    public void getPhotoFromSystem(View v) //implement selecting multiple files
    {
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*)");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}
