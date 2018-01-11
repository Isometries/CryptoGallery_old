package com.photocrypt.photocrypt;

import com.photocrypt.photocrypt.helpers.StorageHandler;
import com.photocrypt.photocrypt.helpers.PhotoCrypt;

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


public class ExportParams {

    private StorageHandler storageHandler;
    private PhotoCrypt photoCrypt;
    private Queue<Photo> photos;
    private Queue<Album> albums;


    public ExportParams(StorageHandler storageHandler, Queue<Photo> photos)
    {
        this.storageHandler = storageHandler;
        this.photos = photos;
    }

    public ExportParams(PhotoCrypt photocrypt, Queue<Album> albums)
    {
        this.photoCrypt = photocrypt;
        this.albums = albums;
    }

    public StorageHandler getStorageHandler()
    {
        return this.storageHandler;
    }

    public Queue<Photo> getPhotos()
    {
        return this.photos;
    }

    public PhotoCrypt getPhotoCrypt()
    {
        return this.photoCrypt;
    }

    public Queue<Album> getAlbums()
    {
        return this.albums;
    }
}
