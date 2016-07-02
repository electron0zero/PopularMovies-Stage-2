package com.wordpress.electron0zero.popularmovies.custom_adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wordpress.electron0zero.popularmovies.R;
import com.wordpress.electron0zero.popularmovies.model.Trailer;

import java.util.List;

public class TrailerAdapter extends BaseAdapter {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final Trailer mTrailer = new Trailer();
    private List<Trailer> mTrailerObjects;

    //constructor
    public TrailerAdapter(Context context, List<Trailer> TrailerObjects) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTrailerObjects = TrailerObjects;
    }

    //Getter method
    public Context getmContext(){
        return mContext;
    }

    public void add(Trailer object){
        synchronized (mTrailer){
            mTrailerObjects.add(object);
        }
        notifyDataSetChanged();
    }

    public void remove(){
        synchronized (mTrailer){
            mTrailerObjects.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTrailerObjects.size();
    }

    @Override
    public Trailer getItem(int position) {
        return mTrailerObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        TrailerViewHolder viewHolder;

        if (view == null){
            view = mLayoutInflater.inflate(R.layout.movie_trailer_item_layout, parent, false);
            viewHolder = new TrailerViewHolder(view);
            view.setTag(viewHolder);
        }

        final Trailer trailer = getItem(position);
        viewHolder = (TrailerViewHolder) view.getTag();

        String trailer_thumb_url = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        // TODO: 01-07-16 set that thumb via picasso
        // TODO: 01-07-16 set trailer text

        return view;
    }

    public static class TrailerViewHolder {
        public final ImageView imageView;
        public final TextView nameView;

        public TrailerViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_image);
            nameView = (TextView) view.findViewById(R.id.trailer_name);
        }
    }
}
