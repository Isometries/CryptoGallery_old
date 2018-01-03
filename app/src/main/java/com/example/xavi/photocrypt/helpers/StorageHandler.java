package com.example.xavi.photocrypt.helpers;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

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

    public String movePhoto(Uri uri, String id, Context context) throws IOException, URISyntaxException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException //may need some work
    {
        String path = getRealPathFromURI(uri, context);
        File photo = new File(dir, id);
        String newPath = photo.getCanonicalPath();

        byte[] plainTxt = Crypto.getFile(path);
        byte[] cipherTxt = Crypto.encrypt(plainTxt);
        Crypto.writeFile(newPath, cipherTxt);

        return newPath;
    }

    public void deletePhoto(String location)
    {
        File photo = new File(location);
        photo.delete();
    }
}

