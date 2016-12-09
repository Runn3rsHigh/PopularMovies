package com.example.kschmidty.popularmovies;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FragmentDiscovery extends Fragment {

    private final String LOG_TAG = FragmentDiscovery.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private List<Movie> mmovies = new ArrayList<Movie>();
    private MovieAdapter mAdapter;
    private final String KEY_LAST_SCROLL_LOCATION = "lastScrollLocation";
    private int lastScrollLocation;


    // Final Strings used for parsing JSON Movie Objects
    private final String TITLE = "original_title";
    private final String ID = "id";
    private final String POSTER_PATH = "poster_path";
    private final String OVERVIEW = "overview";
    private final String RELEASE_DATE = "release_date";
    private final String RATING = "vote_average";

    // Data for grabbing thumbnails from API
    private GridView mGridView;
    private Date mLastUpdated;

    // Other important constants
    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;
    private final String KEY_PREF_SORT_TYPE = "pref_sortType";
    private String currentSortOrder;


    public FragmentDiscovery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG,"onCreate() called");
        setHasOptionsMenu(true);

        setRetainInstance(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){

    }


    private void getPopularMovies() {
        if (!((MainDiscoveryActivity)getActivity()).isOnline()) {
            return;
        }
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + getString(R.string.api_key_string) + "&language=en-US";


        mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Parse response to JSON
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            createMovies(jsonResponse);
                        } catch (JSONException e){
                            Log.e(LOG_TAG,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle error response
                Log.v(LOG_TAG, error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
        mLastUpdated = new Date();

    }

    private void getTopRatedMovies(){
        if (!((MainDiscoveryActivity)getActivity()).isOnline()){
            return;
        }
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + getString(R.string.api_key_string) + "&language=en-US";


        mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // TODO Parse response to JSON
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            createMovies(jsonResponse);
                        } catch (JSONException e){
                            Log.e(LOG_TAG,e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO handle error response
                Log.v(LOG_TAG, error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
        mLastUpdated = new Date();
    }

    private void getMoviesFromInternet(String sortType){
        if (sortType.equalsIgnoreCase("popular")){
            currentSortOrder = "popular";
            getPopularMovies();
        }
        else if (sortType.equalsIgnoreCase("rated")) {
            currentSortOrder = "rated";
            getTopRatedMovies();
        }
        else {
            Log.e(LOG_TAG,"Could not find a default sort order");
        }
    }

    /*
    * Create ArrayList<Movie> mmovies with the data from the Movie db api call
    *
    *
     */
    private void createMovies(JSONObject jsonMovies){
        try {
            JSONArray results = jsonMovies.getJSONArray("results");
            for(int i = 0; i < results.length();i++){
                JSONObject movieObject = results.getJSONObject(i);
                Movie movieItem = new Movie(movieObject.getString(TITLE),movieObject.getString(ID),movieObject.getString(POSTER_PATH),
                        movieObject.getString(OVERVIEW),movieObject.getString(RELEASE_DATE),movieObject.getDouble(RATING));
                mmovies.add(movieItem);
            }
            populateAdapter();

        } catch (JSONException e){
            Log.v(LOG_TAG,e.toString());
        }

    }
    private void populateAdapter(){
       mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(LOG_TAG,"onCreateView() called");

        FrameLayout mGridLayout = (FrameLayout) inflater.inflate(R.layout.fragment_discovery, container,false);
        mGridView = (GridView) mGridLayout.findViewById(R.id.grid_view);
        mAdapter = new MovieAdapter(getActivity().getApplicationContext(),R.layout.fragment_discovery,mmovies);
        mGridView.setAdapter(mAdapter);

        // Date for checking last time data was updated
        Date fiveDaysAgo = new Date(System.currentTimeMillis() - (5 * DAY_IN_MS));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        currentSortOrder = sharedPref.getString(KEY_PREF_SORT_TYPE, getString(R.string.pref_sort_defaultValue));


        if (mLastUpdated != null){
           if (mLastUpdated.before(fiveDaysAgo)){
               mmovies.clear();
               getMoviesFromInternet(currentSortOrder);
           }
        }else{
            getMoviesFromInternet(currentSortOrder);
        }

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                FragmentMovieDetail fmd = new FragmentMovieDetail();
                Bundle bundle = new Bundle();
                android.app.FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();

                Movie clickedMovie = mmovies.get(position);
                bundle.putString("movieTitle",clickedMovie.getTitle());
                bundle.putString("moviePicturePath",clickedMovie.getPosterPath());
                bundle.putString("movieOverview",clickedMovie.getOverview());
                bundle.putDouble("movieRating",clickedMovie.getRating());
                bundle.putString("movieReleaseDate",clickedMovie.getReleaseDate());

                fmd.setArguments(bundle);

                ft.replace(R.id.container,fmd);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return mGridLayout;

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.v(LOG_TAG,"onStop() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.v(LOG_TAG,"onPause() called");

        lastScrollLocation = mGridView.getFirstVisiblePosition();
        Log.v(LOG_TAG,"Setting lastScrollLocation -> " + lastScrollLocation);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
       super.onSaveInstanceState(outState);
        outState.putInt("lastScrollLocation",lastScrollLocation);
        Log.v(LOG_TAG,"onSaveInstancestate() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.v(LOG_TAG,"onResume() called");
        Log.v(LOG_TAG,"lastScrollLocation: " + lastScrollLocation);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String preferenceOrder = sharedPref.getString(KEY_PREF_SORT_TYPE, getString(R.string.pref_sort_defaultValue));

        // If the sort order was updated, then pull the new sorted content from the MovieDB
        if(!preferenceOrder.equalsIgnoreCase(currentSortOrder)){
            mmovies.clear();
            getMoviesFromInternet(preferenceOrder);
        }
        else {
            mGridView.smoothScrollToPosition(lastScrollLocation);
        }

    }




}
