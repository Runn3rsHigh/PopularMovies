package com.example.kschmidty.popularmovies;

/**
 * Created by kschm on 10/30/2016.
 */

public class Movie {

    private String title;
    private String movieId;
    private String posterPath;
    private String overview;
    private String releaseDate;
    private Double rating;

    public Movie(String title, String movieId, String posterPath, String overview, String release_date, Double rating){
        this.title = title;
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = release_date;
        this.rating = rating;
    }

    public String getTitle(){
        return title;
    }

    public String getMovieId(){
        return movieId;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public String getOverview(){
        return overview;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public Double getRating(){
        return rating;
    }

    @Override
    public String toString(){
        String movieString = "Movie: " + title + " ID: " + movieId + " poster_path: " + posterPath + " overview: " + overview + " release_date: " + releaseDate + " rating: " + rating.toString();
        return movieString;
    }
}
