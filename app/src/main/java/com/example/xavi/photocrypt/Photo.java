package com.example.xavi.photocrypt;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import java.io.ByteArrayOutputStream;

public class Photo {

    private static final int THUMBSIZE = 448;
    private long _id;
    private String title;
    private String location;
    private byte[] thumbnail;

    public Photo(){}

    public Photo(long _id, String titles, String location, byte[] thumbnail)
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

    public long getID()
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
