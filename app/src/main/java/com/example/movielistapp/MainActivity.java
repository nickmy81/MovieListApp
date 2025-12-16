package com.example.movielistapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.adapter.MovieAdapter;
import com.example.movielistapp.api.ApiClient;
import com.example.movielistapp.api.ApiService;
import com.example.movielistapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getPopularMovies("ISI_API_KEY_KAMU")
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null) {
                            recyclerView.setAdapter(
                                    new MovieAdapter(MainActivity.this,
                                            response.body().getResults())
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this,
                                "Gagal load data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
