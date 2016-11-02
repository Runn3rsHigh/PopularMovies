package com.example.kschmidty.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by kschm on 10/30/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private LayoutInflater layoutInflater;
    private List<Movie> movies;

    public MovieAdapter(Context context, int resource, int textViewResourceId, List<Movie> objects) {
        super(context, resource, textViewResourceId, objects);
        this.movies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

    }
}
