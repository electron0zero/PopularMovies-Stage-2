package com.wordpress.electron0zero.popularmovies.FetchDataTasks;


import android.os.AsyncTask;
import android.util.Log;

import com.wordpress.electron0zero.popularmovies.model.Movie;
import com.wordpress.electron0zero.popularmovies.ui.MainActivityFragment;

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

public class FetchMovies extends AsyncTask<String, Void, List<Movie>> {

    private static final String API_KEY = "your-api-key-here" ;
    private final String LOG_TAG = "Fetch Movies";

    HttpURLConnection httpURLConnection = null;
    BufferedReader reader = null;
    String jsonResponseString = null;

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG,"Fetch movies started");
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(String... params) {

        if (params.length == 0){
            Log.d(LOG_TAG, "Died - total Params length is 0");
            return null;
        }

        try {
            String choice = params[0];

            URL url = new URL("http://api.themoviedb.org/3/movie/" + choice + "?api_key=" + API_KEY);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            //added new line for pretty printing
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }

            jsonResponseString = buffer.toString();
            Log.d(LOG_TAG,"Result :" + jsonResponseString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.d(LOG_TAG, "Error "+ e);
                }
            }
        }

        try {
            return getMoviesFromJson(jsonResponseString);
        } catch (JSONException e) {
            Log.d(LOG_TAG,"Error " + e);
        }

        //if we failed everywhere this will be returned
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        //we got movies so let's show them
        //get instance of main Activity fragment
        MainActivityFragment mainFragment = new MainActivityFragment();
        mainFragment.ShowMovies(movies);
    }

    private List<Movie> getMoviesFromJson(String jsonStr) throws JSONException {
        JSONObject movieJson = new JSONObject(jsonStr);
        JSONArray movieArray = movieJson.getJSONArray("results");

        List<Movie> results = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject movie = movieArray.getJSONObject(i);
            Movie movieModel = new Movie(movie);
            results.add(movieModel);
        }
        return results;
    }
}
