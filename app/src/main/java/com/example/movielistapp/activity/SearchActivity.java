package com.example.movielistapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class SearchActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btnSearch;
    private RecyclerView recyclerView;
    private TextView tvNoResults;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoResults = findViewById(R.id.tvNoResults);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        apiService = ApiClient.getClient(this).create(ApiService.class);

        btnSearch.setOnClickListener(v -> searchMovies());
    }

    private void searchMovies() {
        String query = etSearch.getText().toString().trim();

        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter a search term", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.searchMovies(API.API_KEY, query)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null && response.body().getResults() != null) {
                            if (response.body().getResults().isEmpty()) {
                                tvNoResults.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            } else {
                                tvNoResults.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                recyclerView.setAdapter(
                                        new MovieAdapter(SearchActivity.this,
                                                response.body().getResults())
                                );
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Toast.makeText(SearchActivity.this,
                                "Failed to search movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
