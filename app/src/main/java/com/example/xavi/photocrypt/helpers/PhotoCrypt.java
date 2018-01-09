package com.example.xavi.photocrypt.helpers;

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


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.example.xavi.photocrypt.Album;
import com.example.xavi.photocrypt.Photo;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public ArrayList<Photo> getAlbum(String title) throws GeneralSecurityException
    {
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

    public void deletePhotos(Queue<Photo> photoQueue)
    {
        while(!photoQueue.isEmpty()){
            Photo photo = photoQueue.peek();
            photoQueue.remove();
            deletePhoto(photo);
        }
    }

    public void deleteAlbum(Album album) throws GeneralSecurityException
    {
        String albumTitle = album.getTitle();
        ArrayList<Photo> albumPhotos = getAlbum(albumTitle);

        Queue<Photo> photoQueue = new LinkedList<>(albumPhotos);

        deletePhotos(photoQueue);

    }

    public void deleteAlbums(Queue<Album> albumQueue) throws GeneralSecurityException
    {
        while (!albumQueue.isEmpty()){
            Album album = albumQueue.peek();
            albumQueue.remove();
            deleteAlbum(album);
        }
    }

    public void deletePhoto(Photo photo)
    {
        String location = photo.getLocation();
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

    public static Bitmap getBitmapfromLocation(Uri uri, Context context) throws Exception
    {
        byte[] ciphertxt = Crypto.getFile(uri, context);
        byte[] plaintxt = Crypto.decrypt(ciphertxt);
        Bitmap bitmap = BitmapFactory.decodeByteArray(plaintxt,0,plaintxt.length);

        return bitmap;

    }

    public void addPhoto(Uri[] uri, String title, Context context) throws Exception
    {
        int _id;
        String id, newLocation;
        Random rand = new Random();

        for (int i = 0; i < uri.length; i++){

            byte[] thumbnail = Photo.getThumbnail(uri[i], context);

            do {
                _id = rand.nextInt(100000);
                id = Integer.toString(_id);
                newLocation = storageHandler.movePhoto(uri[i], id, context);
            } while(newLocation == null);

            Photo photo = new Photo(_id, title, newLocation, thumbnail);
            databaseHandler.addPhoto(photo);
        }


    }
}
