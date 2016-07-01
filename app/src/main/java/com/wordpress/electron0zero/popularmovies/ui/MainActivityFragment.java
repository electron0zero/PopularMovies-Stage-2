package com.wordpress.electron0zero.popularmovies.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.wordpress.electron0zero.popularmovies.R;
import com.wordpress.electron0zero.popularmovies.data.MovieContract;
import com.wordpress.electron0zero.popularmovies.model.Movie;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //grid view for images
    private GridView mGridView;
    private GridAdapter mGridAdapter;

    private ArrayList<Movie> mMovies = null;

    private static final String[] FAV_COLUMNS = {
            MovieContract.FavEntry._ID,
            MovieContract.FavEntry.COLUMN_MOVIE_ID,
            MovieContract.FavEntry.COLUMN_MOVIE_TITLE,
            MovieContract.FavEntry.COLUMN_RELEASE_DATE,
            MovieContract.FavEntry.COLUMN_POSTER_PATH,
            MovieContract.FavEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.FavEntry.COLUMN_PLOT
    };
    
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_MOVIE_TITLE = 2;
    public static final int COL_RELEASE_DATE = 3;
    public static final int COL_POSTER_PATH = 4;
    public static final int COL_VOTE_AVERAGE = 5;
    public static final int COL_POLT = 6;
    
    
    public MainActivityFragment() {
    }

    /**
     *call back interface for all activities that contains this Fragment.
     * Activities should implement this callback to get notified for item selections  
     */
    public interface Callback {
        // TODO: 01-07-16 complete this
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to handle options menu
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // TODO: 01-07-16 handle options menu drawing/inflation

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        // TODO: 01-07-16 handle options item selection for sorting of movies
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //create a view and inflate main fragment into it
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        //set gridview into this view
        mGridView = (GridView)view.findViewById(R.id.gridview_movies);

        mGridAdapter = new GridAdapter(getActivity(), new ArrayList<Movie>());
        //// TODO: 01-07-16 this will work after moview model will be created

        //set custom adaptor into view
        mGridView.setAdapter(mGridAdapter);

        //set click listener into view
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 01-07-16 impliment this after movie model creation 
            }
        });
        // TODO: 01-07-16 handle/use saved instance for screen rotation and stuff 

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    // TODO: 01-07-16 line 191 
}
