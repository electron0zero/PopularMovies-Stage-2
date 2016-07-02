package com.wordpress.electron0zero.popularmovies.FetchDataTasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.wordpress.electron0zero.popularmovies.model.Trailer;
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

public class FetchTrailers extends AsyncTask<String, Void, List<Trailer>> {

    private static final String API_KEY =  "put-your-api-key-here";

    private final String LOG_TAG = "FetchTrailers";

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG, "Fetch Trailers started executing");
        super.onPreExecute();
    }

    @Override
    protected List<Trailer> doInBackground(String... params) {

        if (params.length == 0 ){
            Log.d(LOG_TAG,"Died - total params length is 0");
            return null;
        }

        HttpURLConnection httpURLConnection = null;
        BufferedReader reader = null;

        String jsonResponseString = null;

        try {
            //params[0] is movie_id
            final String BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] + "/videos";
            final String API_KEY_PARAM = "api_key";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
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
        }
        finally {
            //do house keeping, i mean clean up
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(LOG_TAG,"Error " + e);
                }
            }
        }

        try {
            return getTrailersFromJson(jsonResponseString);
        } catch (JSONException e) {
            Log.d(LOG_TAG, "Error " + e );
        }

        //this will be returned in case we failed everywhere.
        return null;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        //we got trailers so let's show them
        //get instance of detail Activity fragment
        DetailActivityFragment detailFragment = new DetailActivityFragment();
        //showTrailers card view
        detailFragment.ShowTrailerCardView(trailers);
    }

    private List<Trailer> getTrailersFromJson(String jsonStr) throws JSONException {
        JSONObject trailerJson = new JSONObject(jsonStr);
        JSONArray trailerArray = trailerJson.getJSONArray("results");

        List<Trailer> results = new ArrayList<>();

        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailer = trailerArray.getJSONObject(i);
            // Only show Trailers which are on Youtube
            if (trailer.getString("site").contentEquals("YouTube")) {
                Trailer trailerModel = new Trailer(trailer);
                results.add(trailerModel);
            }
        }
        //returns a list of trailers that are on youtube
        return results;
    }
}
