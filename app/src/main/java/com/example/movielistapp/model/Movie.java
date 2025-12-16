package com.example.movielistapp.model;

public class Movie {

    private int id;
    private String title;
    private String poster_path;
    private String overview;
    private double vote_average;
    private String release_date;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getPosterUrlSmall() {
        if (poster_path == null) return null;
        return "https://image.tmdb.org/t/p/w185" + poster_path;
    }

    public String getPosterUrl() {
        if (poster_path == null) return null;
        return "https://image.tmdb.org/t/p/w342" + poster_path;
    }

    public String getPosterUrlLarge() {
        if (poster_path == null) return null;
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }

    public double getVoteAverage() {
        return vote_average;
    }

    public String getReleaseDate() {
        return release_date;
    }
}
