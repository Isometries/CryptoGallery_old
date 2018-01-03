package com.example.xavi.photocrypt;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.xavi.photocrypt.helpers.DatabaseHandler;
import com.example.xavi.photocrypt.helpers.StorageHandler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PhotoCrypt {

    //this entire class should probably be turned static
    private StorageHandler storageHandler;
    private DatabaseHandler databaseHandler;

    public PhotoCrypt(Context context)
    {
        this.storageHandler = new StorageHandler(context);
        this.databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Photo> getAlbum(String title) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        ArrayList<Photo> photos;
        photos = databaseHandler.getPhotobyAlbum(title);
        return photos;
    }

    public ArrayList<Album> getAlbums() throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        ArrayList<Album> albums = databaseHandler.getAlbums();

        return albums;
    }

    public static Bitmap byteArrayToBitmap(byte[] thumbnail)
    {
        Bitmap bmp = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);

        return bmp;
    }

    public int getPhotoCount()
    {
        int count = databaseHandler.getPhotosCount();

        return count;
    }

    public void deletePhoto(Photo photo)
    {
        String location = photo.getLocation();
        Log.w("location delete", location);
        storageHandler.deletePhoto(location);
        databaseHandler.deletePhotoByLocation(location);
    }

    public void addPhoto(Uri uri, String title, Context context) throws IOException, URISyntaxException, NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
    {
        long _id = System.currentTimeMillis();
        byte longbytes = (byte) _id;
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(longbytes);
        String id = Base64.encodeToString(messageDigest.digest(), Base64.URL_SAFE);

        String location = StorageHandler.getRealPathFromURI(uri, context);
        byte[] thumbnail = Photo.getThumbnail(location);

        String new_location = storageHandler.movePhoto(uri, id, context);
        Log.w("sent loc", new_location);
        Photo photo = new Photo(_id, title, new_location, thumbnail);
        databaseHandler.addPhoto(photo);
    }
}
