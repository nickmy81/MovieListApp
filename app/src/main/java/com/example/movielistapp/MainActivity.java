package com.example.movielistapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.activity.FavoriteActivity;
import com.example.movielistapp.activity.NewMoviesActivity;
import com.example.movielistapp.activity.SearchActivity;
import com.example.movielistapp.activity.TopMoviesActivity;
import com.example.movielistapp.adapter.MovieAdapter;
import com.example.movielistapp.api.API;
import com.example.movielistapp.api.ApiClient;
import com.example.movielistapp.api.ApiService;
import com.example.movielistapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout navPopular, navTop, navNew, navSearch, navFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        navPopular = findViewById(R.id.navPopular);
        navTop = findViewById(R.id.navTop);
        navNew = findViewById(R.id.navNew);
        navSearch = findViewById(R.id.navSearch);
        navFavorites = findViewById(R.id.navFavorites);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        navTop.setOnClickListener(v -> {
            startActivity(new Intent(this, TopMoviesActivity.class));
        });

        navNew.setOnClickListener(v -> {
            startActivity(new Intent(this, NewMoviesActivity.class));
        });

        navSearch.setOnClickListener(v -> {
            startActivity(new Intent(this, SearchActivity.class));
        });

        navFavorites.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });

        loadPopularMovies();
    }

    private void loadPopularMovies() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        apiService.getPopularMovies(API.API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null && response.body().getResults() != null) {
                            recyclerView.setAdapter(
                                    new MovieAdapter(MainActivity.this,
                                            response.body().getResults())
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Failed to load movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
