package com.example.xavi.photocrypt.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.xavi.photocrypt.Album;
import com.example.xavi.photocrypt.Photo;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PhotoManager";
    private static final String TABLE_PHOTOS = "photos";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_THUMBNAIL = "thumbnail";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_PHOTOS_TABLE = "CREATE TABLE " + TABLE_PHOTOS + "("
                + KEY_ID + "INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_LOCATION + " TEXT," + KEY_THUMBNAIL + ")";
        db.execSQL(CREATE_PHOTOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
        onCreate(db);
    }

    public void addPhoto(Photo photo) throws NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, photo.getTitle());
        values.put(KEY_LOCATION, photo.getLocation());
        values.put(KEY_THUMBNAIL, Crypto.encrypt(photo.getThumbnail())); //new
        db.insert(TABLE_PHOTOS, null, values);
        db.close();
    }

    //album is title here
    public ArrayList<Photo> getPhotobyAlbum(String album) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        ArrayList<Photo> photos = new ArrayList<>();
        String title, location;
        byte[] thumbnail;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PHOTOS, null, KEY_TITLE +"=?",
                new String[] {album},null,null,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            title = cursor.getString(1);
            location = cursor.getString(2);
            Log.w("DB LOC ", location);
            thumbnail = Crypto.decrypt(cursor.getBlob(3)); //new
            Photo photo = new Photo(title, location, thumbnail);
            photos.add(photo);
            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return photos;
    }

    public ArrayList<Album> getAlbums() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        ArrayList<Album> albums = new ArrayList<>();
        ArrayList<String> album_titles = new ArrayList<>();
        String title;
        byte[] thumbnail;
        Album album;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PHOTOS, null,null,  null, null,null,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            title = cursor.getString(1);
            thumbnail = Crypto.decrypt(cursor.getBlob(3));//new
            album = new Album(title, thumbnail);

            if (!album_titles.contains(title)){
                album_titles.add(title);
                albums.add(album);
            }

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return albums;
    }


    public ArrayList<Photo> getAllPhotos() throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        ArrayList<Photo> photoList = new ArrayList<Photo>();

        String selectQuery = "SELECT * FROM " + TABLE_PHOTOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            String title, location;
            byte[] thumbnail;

            do {
                title = cursor.getString(1);
                location = cursor.getString(2);
                thumbnail = Crypto.decrypt(cursor.getBlob(3));//new
                Photo photo = new Photo(title, location, thumbnail);
                photoList.add(photo);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return photoList;
    }

    public void deletePhotoByLocation(String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PHOTOS, KEY_LOCATION + " =?",
                new String[] {location});
        db.close();
    }

    public int getPhotosCount()
    {
        int count;

        String countQuery = "SELECT * FROM " + TABLE_PHOTOS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);

        count = cursor.getCount();
        cursor.close();
        db.close();

        return count;
    }


}
