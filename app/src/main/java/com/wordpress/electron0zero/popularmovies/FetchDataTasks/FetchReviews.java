package com.wordpress.electron0zero.popularmovies.FetchDataTasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wordpress.electron0zero.popularmovies.model.Review;
import com.wordpress.electron0zero.popularmovies.ui.DetailActivityFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchReviews extends AsyncTask<String, Void, List<Review>> {

    private static final String API_KEY = "your-api-key-here" ;
    private final String LOG_TAG = "FetchReviews";

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG, "Fetch reviews Started");
        super.onPreExecute();
    }

    @Override
    protected List<Review> doInBackground(String... params) {

        if (params.length == 0){
            Log.d(LOG_TAG, "Died - total Params length is 0");
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;

        String jsonResponseString = null;

        try {
            //params[0] is movie_id
            final String BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] + "/reviews";
            final String API_KEY_PARAM = "api_key";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY )
                    .build();

            URL url = new URL(builtUri.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            jsonResponseString = buffer.toString();

        } catch (IOException e){
            Log.d(LOG_TAG,"Error " + e);
            return null;

        } finally {
            //do housekeeping stuff, i mean clean up
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    Log.d(LOG_TAG, "Error " + e );
                }
            }
        }

        try {
            return getReviewsFromJson(jsonResponseString);
        } catch (JSONException e){
            Log.d(LOG_TAG,"Error " + e);
        }
        //this will be returned in case we failed everywhere.
        return null;
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        //we got reviews so let's show them
        //get instance of detail Activity fragment
        DetailActivityFragment detailFragment = new DetailActivityFragment();
        //showReviews card view
        detailFragment.ShowReviewCardView(reviews);
    }

    private List<Review> getReviewsFromJson(String jsonStr) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonStr);
        JSONArray reviewArray = reviewJson.getJSONArray("results");

        List<Review> results = new ArrayList<>();

        for(int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            results.add(new Review(review));
        }

        return results;
    }
}
