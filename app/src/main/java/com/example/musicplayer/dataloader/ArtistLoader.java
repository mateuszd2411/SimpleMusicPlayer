package com.example.musicplayer.dataloader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.musicplayer.models.Artis;

import java.util.ArrayList;
import java.util.List;

public class ArtistLoader {

    public List<Artis> getArtiss(Context context, Cursor cursor){

        List<Artis> list = new ArrayList<>();
        if (cursor!= null&&cursor.moveToFirst()){
            do {
                list.add(new Artis(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));

            }while (cursor.moveToNext());
            if (cursor!=null){
                cursor.close();
            }
        }

        return list;
    }

    public Artis getArtis(Context context, long id){

        return artis(makeCursor(context,"_id=?",new String[]{String.valueOf(id)}));
    }

    private Artis artis(Cursor cursor) {
        Artis artis = new Artis();
        if (cursor.moveToFirst()&&cursor!=null){

            artis = new Artis(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3));

        }
        if (cursor!=null){
            cursor.close();
        }

        return artis;
    }

    public List<Artis> aritsList (Context context){
        return getArtiss(context,makeCursor(context,null,null));
    }

    public static Cursor makeCursor(Context context, String selection, String[] selectionArg){

        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                "_id",//0
                "artist",//1
                "number_of_albums",//2
                "number_of_tracks",//3

        };
        String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, projection,selection,selectionArg,sortOrder);
        return cursor;
    }

}
