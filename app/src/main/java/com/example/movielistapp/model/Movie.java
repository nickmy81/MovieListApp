package com.example.movielistapp.model;

public class Movie {

    private String title;
    private String poster_path;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterUrl() {
        return "https://image.tmdb.org/t/p/w500" + poster_path;
    }
}

