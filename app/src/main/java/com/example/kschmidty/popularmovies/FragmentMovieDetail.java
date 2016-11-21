package com.example.kschmidty.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * Created by Runn3rsHigh on 11/13/16.
 */

public class FragmentMovieDetail extends Fragment {
    public FragmentMovieDetail(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_movie_detail,container,false);
        ImageView movie_cover = (ImageView) root.findViewById(R.id.movie_detail_pic);

        Bundle created = getArguments();
        String movieTitle = created.getString("movieTitle");
        String movieOverview = created.getString("movieOverview");
        String moviePicturePath = created.getString("moviePicturePath");
        String movieReleaseDate = created.getString("movieReleaseDate");
        Double movieRating = created.getDouble("movieRating",0.0);

        String fullMoviePicturePath = getString(R.string.base_image_url)+getString(R.string.original_image_size)+moviePicturePath;
        Picasso
                .with(getActivity().getApplicationContext())
                .load(fullMoviePicturePath)
                .into(movie_cover);

        // Movie Title
        TextView tempView = (TextView) root.findViewById(R.id.movie_detail_title);
        tempView.setText(movieTitle);

        // Movie Overview
        tempView = (TextView) root.findViewById(R.id.movie_detail_overview);
        tempView.setText(movieOverview);

        // Movie release date
        SpannableString releaseString = new SpannableString("Release Date: "
                + movieReleaseDate);
        releaseString.setSpan(new StyleSpan(Typeface.BOLD),0,12,0);

        tempView = (TextView) root.findViewById(R.id.movie_detail_release);
        tempView.setText(releaseString);

        // Movie Rating
        SpannableString ratingString = new SpannableString("Rating: "
                + movieRating.toString());
        ratingString.setSpan(new StyleSpan(Typeface.BOLD),0,6,0);

        tempView = (TextView) root.findViewById(R.id.movie_detail_rating);
        tempView.setText(ratingString);


        return root;
    }
}
