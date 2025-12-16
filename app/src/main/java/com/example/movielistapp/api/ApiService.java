package com.example.movielistapp.api;

import com.example.movielistapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET("search/movie")
    Call<MovieResponse> searchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey
    );
}
