package com.photocrypt.photocrypt.Threads;

import android.os.AsyncTask;

import com.photocrypt.photocrypt.ExportParams;
import com.photocrypt.photocrypt.Photo;
import com.photocrypt.photocrypt.Album;
import com.photocrypt.photocrypt.helpers.PhotoCrypt;

import java.util.ArrayList;
import java.util.LinkedList;
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

public class ExportAlbums extends AsyncTask<ExportParams, Integer, Void> {

    @Override
    protected Void doInBackground(ExportParams... exportParams)
    {
        PhotoCrypt photoCrypt = exportParams[0].getPhotoCrypt();
        Queue<Album> albums = exportParams[0].getAlbums();

        while (!albums.isEmpty()) {
            Album album = albums.peek();
            albums.remove();
            try {

                ArrayList<Photo> photoList = photoCrypt.getAlbum(album.getTitle());
                Queue<Photo> photos = new LinkedList<>(photoList);
                photoCrypt.exportPhotos(photos);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
}
