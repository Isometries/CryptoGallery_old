package com.photocrypt.photocrypt;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Photo {

    private static final int THUMBSIZE = 448;
    private int _id;
    private String title;
    private String location;
    private byte[] thumbnail;

    public Photo(){}

    public Photo(int _id, String titles, String location, byte[] thumbnail)
    {
        this._id = _id;
        this.title = titles;
        this.location = location;
        this.thumbnail = thumbnail;
    }

    public Photo(String title, String location, byte[] thumbnail)
    {
        this.title = title;
        this.location = location;
        this.thumbnail = thumbnail;
    }

    public static byte[] getThumbnail(String location)
    {
        byte[] byteArray;
        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(location),
                THUMBSIZE, THUMBSIZE);
        ByteArrayOutputStream out_bytes = new ByteArrayOutputStream();
        ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, out_bytes);
        byteArray = out_bytes.toByteArray();

        return byteArray;
    }

    public static byte[] getThumbnail(Uri uri, Context context) throws IOException
    {
        byte[] byteArray;

        Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri),
                THUMBSIZE, THUMBSIZE);
        ByteArrayOutputStream out_bytes = new ByteArrayOutputStream();
        ThumbImage.compress(Bitmap.CompressFormat.PNG, 100, out_bytes);
        byteArray = out_bytes.toByteArray();

        return byteArray;
    }

    public int getID()
    {
        return this._id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getLocation()
    {
        return this.location;
    }

    public byte[] getThumbnail()
    {
        return this.thumbnail;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setThumbnail(byte[] thumbnail)
    {
        this.thumbnail = thumbnail;
    }

}
