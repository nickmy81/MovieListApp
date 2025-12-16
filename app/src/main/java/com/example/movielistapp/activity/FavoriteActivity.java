package com.example.movielistapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.R;
import com.example.movielistapp.adapter.MovieAdapter;
import com.example.movielistapp.model.Movie;
import com.example.movielistapp.util.FavoriteManager;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout emptyState;
    FavoriteManager favoriteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.recyclerView);
        emptyState = findViewById(R.id.emptyState);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        favoriteManager = new FavoriteManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavorites();
    }

    private void loadFavorites() {
        List<Movie> favorites = favoriteManager.getFavorites();

        if (favorites.isEmpty()) {
            emptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new MovieAdapter(this, favorites));
        }
    }
}
