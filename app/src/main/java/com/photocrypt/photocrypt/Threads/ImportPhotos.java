package com.photocrypt.photocrypt.Threads;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.photocrypt.photocrypt.ImportParams;
import com.photocrypt.photocrypt.helpers.PhotoCrypt;

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

public class ImportPhotos extends AsyncTask<ImportParams, Integer, Void> {

    @Override
    protected Void doInBackground(ImportParams... importParams)
    {
        String title = importParams[0].getTitle();
        Uri[] uris = importParams[0].getUris();
        Context context = importParams[0].getContext();

        PhotoCrypt photoCrypt = new PhotoCrypt(context);

        try {
            photoCrypt.addPhoto(uris, title, context);

        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }
}
