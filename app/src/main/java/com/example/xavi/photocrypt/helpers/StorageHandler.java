package com.example.xavi.photocrypt.helpers;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.xavi.photocrypt.Photo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class StorageHandler {

    private File dir;

    public StorageHandler(Context context)
    {
        this.dir = context.getDir("data", Context.MODE_PRIVATE);
    }

    public static String getRealPathFromURI(Uri contentUri, Context context) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);//dafuq is this
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public String movePhoto(Uri uri, String id, Context context) throws Exception //add exception messesge
    {
        String newPath;
        String path = getRealPathFromURI(uri, context);
        File photo = new File(dir, id);

        if (photo.exists()){ //checking if file name already exists
            newPath = null;
        }else {
            newPath = photo.getCanonicalPath();
            byte[] plainTxt = Crypto.getFile(path);
            byte[] cipherTxt = Crypto.encrypt(plainTxt);
            Crypto.writeFile(newPath, cipherTxt);
        }

        return newPath;
    }

    public void deletePhoto(String location)
    {
        File photo = new File(location);
        photo.delete();
    }

    public void exportPhotos(Queue<Photo> queue) throws Exception //add button and test
    {
        Photo photo;
        String location;
        ZipEntry entry;
        File zip;
        ZipOutputStream out;
        int dupe = 0;
        byte[] Encryptedbytestream;
        byte[] Decryptedbytestream;

        //maybe a sloppy way to avoid overwriting previous exports
        do {
            String n = Integer.toString(dupe);
            zip = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/photos" + n + ".zip");
            dupe++;
        } while(zip.exists());

        out = new ZipOutputStream(new FileOutputStream(zip));

        while (!queue.isEmpty()){
            photo = queue.peek();
            queue.remove();
            location = photo.getLocation();

            String fileName =  location.substring(location.lastIndexOf("/")+1);

            entry = new ZipEntry(fileName);
            out.putNextEntry(entry);
            Encryptedbytestream = Crypto.getFile(location);
            Decryptedbytestream = Crypto.decrypt(Encryptedbytestream);
            out.write(Decryptedbytestream);
            out.closeEntry();
        }
        out.close();
    }

}

