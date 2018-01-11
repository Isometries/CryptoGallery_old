package com.example.xavi.photocrypt.Threads;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.xavi.photocrypt.Photo;
import com.example.xavi.photocrypt.helpers.PhotoCrypt;

import java.security.GeneralSecurityException;

/*
 *PhotoCrypt - an encrypted gallery for Android
 *Copyright (C) 2018 Thunder Gabriel <tgabriel@protonmail.com>

 *This program is free software; you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation; either version 3 of the License, or
 *(at your option) any later version.

 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.

 *You should have received a copy of the GNU General Public License
 *along with this program; if not, see http://www.gnu.org/licenses/.
 */

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
            }
        } while (ok);
    }
}
