package com.example.xavi.photocrypt.Threads;

import android.os.AsyncTask;

import com.example.xavi.photocrypt.ExportParams;
import com.example.xavi.photocrypt.Photo;
import com.example.xavi.photocrypt.helpers.StorageHandler;

import java.util.Queue;

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

public class ExportPhotos extends AsyncTask<ExportParams, Integer, Void> {

    //should spwan progress window in future
    @Override
    protected Void doInBackground(ExportParams... exportParams)
    {
        StorageHandler storageHandler = exportParams[0].getStorageHandler();
        Queue<Photo> photoQueue = exportParams[0].getPhotos();

        try {
            storageHandler.exportPhotos(photoQueue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
