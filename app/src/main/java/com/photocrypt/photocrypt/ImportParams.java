package com.photocrypt.photocrypt;


import android.content.Context;
import android.net.Uri;

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

public class ImportParams {

    private String title;
    private Uri[] uris;
    private Context context;

    public ImportParams(Uri[] uris, Context context, String title)
    {
        this.title = title;
        this.uris = uris;
        this.context = context;
    }

    public String getTitle()
    {
        return this.title;
    }

    public Uri[] getUris()
    {
        return this.uris;
    }

    public Context getContext()
    {
        return this.context;
    }
}
