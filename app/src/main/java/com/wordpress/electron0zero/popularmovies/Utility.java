package com.wordpress.electron0zero.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;

import com.wordpress.electron0zero.popularmovies.data.MovieContract;

public class Utility {

    private void showErrorDialog(Context context, String message) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage(message)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //set action for retry
                    }
                })
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //set action for okay
                    }
                }).show();
    }

    //takes movie_id and tells whether or not that movie is favored
    public static int isFavored(Context context, int id) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.FavEntry.CONTENT_URI,
                null,   // projection
                MovieContract.FavEntry.COLUMN_MOVIE_ID + " = ?", // selection
                new String[] { Integer.toString(id) },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows;
    }

    public static String buildPosterUrl(String PosterPath) {
        //use recommended w185 width for image
        return "http://image.tmdb.org/t/p/w185"+ PosterPath;
    }

}
