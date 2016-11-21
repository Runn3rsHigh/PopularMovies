package com.example.kschmidty.popularmovies;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kschm on 10/30/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Movie> movies;
    private Layout grid_view;

    public MovieAdapter(Context context,int resource, List<Movie> objects) {
        super(context, resource, objects);
        this.movies = objects;
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        Log.v(LOG_TAG, "MovieAdapter Constructor called");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ImageView view;
        if ( null == convertView){
            Log.v(LOG_TAG, "convertView is null");
            view = new ImageView(context);
            view.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT,GridView.LayoutParams.MATCH_PARENT));
            view.setScaleType(ImageView.ScaleType.CENTER);
            view.setPadding(1,1,1,1);
        } else {
            Log.v(LOG_TAG, "reusing previous convertView");
            view = (ImageView) convertView;
        }


        String poster_path = context.getString(R.string.base_image_url) + context.getString(R.string.larger_thumbnail_size) + movies.get(position).getPosterPath();
        Log.v(LOG_TAG,"Post Path: "+poster_path);


        Picasso
                .with(context)
                .load(poster_path)
                .into(view);

        return view;

    }

}
