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
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if ( null == convertView){
            convertView = layoutInflater.inflate(R.layout.image_view_item,parent,false);
            holder = new ViewHolder();

            holder.image = (ImageView) convertView;
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String poster_path = context.getString(R.string.base_image_url) + context.getString(R.string.larger_thumbnail_size) + movies.get(position).getPosterPath();

        Picasso
                .with(context)
                .load(poster_path)
                .into(holder.image);

        return convertView;

    }

    static class ViewHolder {
        ImageView image;
    }

}
