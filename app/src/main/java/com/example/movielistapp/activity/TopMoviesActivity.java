package com.example.movielistapp.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.R;
import com.example.movielistapp.adapter.MovieAdapter;
import com.example.movielistapp.api.API;
import com.example.movielistapp.api.ApiClient;
import com.example.movielistapp.api.ApiService;
import com.example.movielistapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopMoviesActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_movies);

        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        loadTopRatedMovies();
    }

    private void loadTopRatedMovies() {
        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        apiService.getTopRatedMovies(API.API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null && response.body().getResults() != null) {
                            recyclerView.setAdapter(
                                    new MovieAdapter(TopMoviesActivity.this,
                                            response.body().getResults())
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(TopMoviesActivity.this,
                                "Failed to load top rated movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
