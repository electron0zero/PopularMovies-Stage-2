package com.wordpress.electron0zero.popularmovies.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.electron0zero.popularmovies.R;
import com.wordpress.electron0zero.popularmovies.model.Movie;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mInflater;

    private final Movie mMovie = new Movie();

    private List<Movie> mMovieObjects;

    //constructor
    public GridAdapter(Context context, List<Movie> MovieObjects ){

        mContext = context;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMovieObjects = MovieObjects;
    }

    //Getter method
    public Context getmContext(){
        return mContext;
    }

    public void add(Movie MovieObject){
        synchronized (mMovie){
            mMovieObjects.add(MovieObject);
        }
        notifyDataSetChanged();
    }

    public void remove(){
        synchronized (mMovie){
            mMovieObjects.clear();
        }
        notifyDataSetChanged();
    }

    public void setData(List<Movie> data){
        remove();
        for (Movie movie : data){
            add(movie);
        }
    }

    @Override
    public int getCount() {
        return mMovieObjects.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovieObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MovieViewHolder viewHolder;

        if (view == null){
            view = mInflater.inflate(R.layout.movie_grid_item_layout, parent, false);
            viewHolder = new MovieViewHolder(view);
            view.setTag(viewHolder);
        }

        final Movie movie = getItem(position);

        String poster_url = "http://image.tmdb.org/t/p/w185" + movie.getPoster_path();

        viewHolder = (MovieViewHolder) view.getTag();

        // TODO: 01-07-16 handle imga ewith picasso
        // TODO: 01-07-16  set Movie Name text view for displaying movie name

        return view;
    }

    public static class MovieViewHolder {
        public final ImageView imageView;
        public final TextView titleView;

        public MovieViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_image);
            titleView = (TextView) view.findViewById(R.id.grid_item_title);
            // TODO: 01-07-16 handle this from layout
        }
    }
}
