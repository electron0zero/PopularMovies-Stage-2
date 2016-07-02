package com.wordpress.electron0zero.popularmovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.linearlistview.LinearListView;
import com.wordpress.electron0zero.popularmovies.R;
import com.wordpress.electron0zero.popularmovies.Utility;
import com.wordpress.electron0zero.popularmovies.custom_adapters.ReviewAdapter;
import com.wordpress.electron0zero.popularmovies.custom_adapters.TrailerAdapter;
import com.wordpress.electron0zero.popularmovies.model.Movie;
import com.wordpress.electron0zero.popularmovies.model.Review;
import com.wordpress.electron0zero.popularmovies.model.Trailer;
import com.wordpress.electron0zero.popularmovies.FetchDataTasks.FetchTrailers;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public static final String TAG = "DetailActivityFragment";

    static final String DETAIL_MOVIE = "DETAIL_MOVIE";

    private Movie mMovie;

    //All the Views for detail Fragment
    private ImageView mPosterView;

    private TextView mMovieTitileView;
    private TextView mPlotView;
    private TextView mReleaseDateView;
    private TextView mVoteAverageView;

    private LinearListView mTrailersView;
    private LinearListView mReviewsView;

    private CardView mReviewsCardview;
    private CardView mTrailersCardview;

    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    private Toast mToast;

    private ScrollView mDetailLayout;

    private Trailer mTrailer;

    public DetailActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 02-07-16
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // TODO: 02-07-16
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
        // TODO: 02-07-16
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Bundle arguments = getArguments();

        if (arguments != null) {
            mMovie = arguments.getParcelable(DetailActivityFragment.DETAIL_MOVIE);
        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mDetailLayout = (ScrollView) rootView.findViewById(R.id.detail_layout);

        if (mMovie != null) {
            mDetailLayout.setVisibility(View.VISIBLE);
        } else {
            mDetailLayout.setVisibility(View.INVISIBLE);
        }

        mPosterView = (ImageView) rootView.findViewById(R.id.detail_image);
        mMovieTitileView = (TextView) rootView.findViewById(R.id.detail_title);
        mPlotView = (TextView) rootView.findViewById(R.id.detail_overview);
        mReleaseDateView = (TextView) rootView.findViewById(R.id.detail_date);
        mVoteAverageView = (TextView) rootView.findViewById(R.id.detail_vote_average);

        mTrailersView = (LinearListView) rootView.findViewById(R.id.detail_trailers);
        mReviewsView = (LinearListView) rootView.findViewById(R.id.detail_reviews);

        mReviewsCardview = (CardView) rootView.findViewById(R.id.detail_reviews_cardview);
        mTrailersCardview = (CardView) rootView.findViewById(R.id.detail_trailers_cardview);

        //set our custom trailers adaptor into Trailer's View
        mTrailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<Trailer>());
        mTrailersView.setAdapter(mTrailerAdapter);

        //view Trailer in other app that user choose for playing Youtube Videos.
        //using explicit intent for
        mTrailersView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView parent, View view, int position, long id) {

                Trailer trailer = mTrailerAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
                startActivity(intent);
            }
        });

        //set our custom Review adaptor into Custom reviews view
        mReviewAdapter = new ReviewAdapter(getActivity(), new ArrayList<Review>());
        mReviewsView.setAdapter(mReviewAdapter);

        if (mMovie != null) {
            String poster_url = Utility.buildPosterUrl(mMovie.getPoster_path());
            //show that image with picasso
            // TODO: 01-07-16  show image

            mMovieTitileView.setText(mMovie.getMovie_title());
            mPlotView.setText(mMovie.getPlot());
            mReleaseDateView.setText(mMovie.getRelease_date());
            mVoteAverageView.setText(mMovie.getVote_avg());
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mMovie != null){
            //fetch trailers with passing movie_id
            new FetchTrailers().execute(Integer.toString(mMovie.getMovie_id()));
            //fetch reviews with passing movie_id
            new FetchTrailers().execute(Integer.toString(mMovie.getMovie_id()));
        }
    }

    private Intent createShareMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mMovie.getMovie_title() + " " +
                "http://www.youtube.com/watch?v=" + mTrailer.getKey()
                + "  Shared Via Popular Movies App, Data is Sourced form themoviedb.org" );
        return shareIntent;
    }

    public void ShowTrailerCardView(List<Trailer> trailers){
        if (trailers != null) {
            //when we have trailers
            if (trailers.size() > 0) {
                mTrailersCardview.setVisibility(View.VISIBLE);
                if (mTrailerAdapter != null) {
                    mTrailerAdapter.remove();
                    for (Trailer trailer : trailers) {
                        mTrailerAdapter.add(trailer);
                    }
                }

                // TODO: 02-07-16  handle trailer sharing
                //mTrailer = trailers.get(0);
                //if (mShareActionProvider != null) {
                //    mShareActionProvider.setShareIntent(createShareMovieIntent());
                //}
            }
        }
    }

    public void ShowReviewCardView(List<Review> reviews){
        if (reviews != null) {
            //if we have reviews
            if (reviews.size() > 0) {
                mReviewsCardview.setVisibility(View.VISIBLE);
                if (mReviewAdapter != null) {
                    mReviewAdapter.remove();
                    for (Review review : reviews) {
                        mReviewAdapter.add(review);
                    }
                }
            }
        }
    }
}
