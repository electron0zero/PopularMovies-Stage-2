package com.wordpress.electron0zero.popularmovies.FetchDataTasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.wordpress.electron0zero.popularmovies.data.MovieContract;
import com.wordpress.electron0zero.popularmovies.model.Movie;
import com.wordpress.electron0zero.popularmovies.ui.MainActivityFragment;

import java.util.ArrayList;
import java.util.List;

public class FetchFav extends AsyncTask<String, Void, List<Movie>> {

    private Context mContext;

    private static final String[] FAV_COLUMNS = {
            MovieContract.FavEntry._ID,
            MovieContract.FavEntry.COLUMN_MOVIE_ID,
            MovieContract.FavEntry.COLUMN_MOVIE_TITLE,
            MovieContract.FavEntry.COLUMN_RELEASE_DATE,
            MovieContract.FavEntry.COLUMN_POSTER_PATH,
            MovieContract.FavEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.FavEntry.COLUMN_PLOT
    };

    //constructor
    public FetchFav(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.FavEntry.CONTENT_URI,
                FAV_COLUMNS,
                null,
                null,
                null
        );

        return getFavMoviesFromCursor(cursor);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        //we got Fav movies so let's show them
        //get instance of main Activity fragment
        MainActivityFragment mainFragment = new MainActivityFragment();
        mainFragment.ShowFavMovies(movies);
    }

    private List<Movie> getFavMoviesFromCursor(Cursor cursor) {
        List<Movie> results = new ArrayList<>();
        //if we have data in database for Fav. movies.
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor);
                results.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return results;
    }
}
