package com.example.xavi.photocrypt.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.xavi.photocrypt.Album;
import com.example.xavi.photocrypt.Photo;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class PhotoCrypt {

    //this entire class should probably be turned static
    private StorageHandler storageHandler;
    private DatabaseHandler databaseHandler;

    public PhotoCrypt(Context context)
    {
        this.storageHandler = new StorageHandler(context);
        this.databaseHandler = new DatabaseHandler(context);
    }

    public ArrayList<Photo> getAlbum(String title) throws GeneralSecurityException {
        ArrayList<Photo> photos;
        photos = databaseHandler.getPhotobyAlbum(title);
        return photos;
    }

    public ArrayList<Album> getAlbums() throws GeneralSecurityException {
        ArrayList<Album> albums = databaseHandler.getAlbums();

        return albums;
    }

    public void exportPhotos(Queue<Photo> photoQueue) throws Exception
    {
        storageHandler.exportPhotos(photoQueue);
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

    public static Bitmap getBitmapfromLocation(String location) throws Exception
    {
        byte[] ciphertxt = Crypto.getFile(location);
        byte[] plaintxt = Crypto.decrypt(ciphertxt);
        Bitmap bitmap = BitmapFactory.decodeByteArray(plaintxt,0,plaintxt.length);

        return bitmap;

    }

    public void addPhoto(Uri uri, String title, Context context) throws Exception
    {
        int _id;
        String id, newLocation;
        Random rand = new Random();

        String location = StorageHandler.getRealPathFromURI(uri, context);
        byte[] thumbnail = Photo.getThumbnail(location);

        do {
            _id = rand.nextInt(100000);
            id = Integer.toString(_id);
            newLocation = storageHandler.movePhoto(uri, id, context);
        } while(newLocation == null);

        Photo photo = new Photo(_id, title, newLocation, thumbnail);
        databaseHandler.addPhoto(photo);
    }
}
