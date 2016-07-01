package com.wordpress.electron0zero.popularmovies;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Utility {

    private void showErrorDialog(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setMessage("Something is Wrong !")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public static String buildPosterUrl(String PosterPath) {
        //use recommended w185 width for image
        return "http://image.tmdb.org/t/p/w185"+ PosterPath;
    }

}
