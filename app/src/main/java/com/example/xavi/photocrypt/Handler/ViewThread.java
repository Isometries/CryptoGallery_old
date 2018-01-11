package com.example.xavi.photocrypt.Handler;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.xavi.photocrypt.Photo;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;

import java.security.GeneralSecurityException;

public class ViewThread implements Runnable {

    private Handler mHandler;
    private PhotoCrypt photocrypt;
    private String title;
    private Bundle b;
    private Cursor cursor;

    public ViewThread(PhotoCrypt photocrypt, String title, Handler mHandler)
    {
        this.mHandler = mHandler;
        this.photocrypt = photocrypt;
        this.title = title;
        this.cursor = photocrypt.getCursor(this.title);
    }

    @Override
    public void run() {

        boolean ok = true;
        Photo photo;

        String title, location;
        byte[] thumbnail;

        do {
            try {
                photo = photocrypt.getNextPhoto(cursor);
                if (photo != null) {
                    title = photo.getTitle();
                    location = photo.getLocation();
                    Log.w("location", location);
                    thumbnail = photo.getThumbnail();

                    Message msg = new Message();
                    b = new Bundle();
                    b.putString("TITLE", title);
                    b.putByteArray("THUMBNAIL", thumbnail);
                    b.putString("LOCATION", location);
                    msg.setData(b);
                    mHandler.sendMessage(msg);
                } else {
                    ok = false;
                }
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (ok);
    }
}
