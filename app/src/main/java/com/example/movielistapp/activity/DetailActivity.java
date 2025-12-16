package com.example.movielistapp.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movielistapp.R;
import com.example.movielistapp.model.Movie;
import com.example.movielistapp.util.FavoriteManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPoster;
    TextView tvTitle, tvOverview, tvRating, tvReleaseDate;
    Button btnFavorite;
    FavoriteManager favoriteManager;

    int movieId;
    String title, overview, poster, posterPath, releaseDate;
    double voteAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imgPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        tvRating = findViewById(R.id.tvRating);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);
        btnFavorite = findViewById(R.id.btnFavorite);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        favoriteManager = new FavoriteManager(this);

        movieId = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        overview = getIntent().getStringExtra("overview");
        poster = getIntent().getStringExtra("poster");
        posterPath = getIntent().getStringExtra("poster_path");
        voteAverage = getIntent().getDoubleExtra("vote_average", 0);
        releaseDate = getIntent().getStringExtra("release_date");

        tvTitle.setText(title);
        tvOverview.setText(overview);
        tvRating.setText(String.format("%.1f/10", voteAverage));
        tvReleaseDate.setText(releaseDate != null ? releaseDate : "N/A");

        ColorDrawable placeholder = new ColorDrawable(Color.parseColor("#1F1F3D"));
        if (poster != null) {
            Picasso.get()
                    .load(poster)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fit()
                    .centerCrop()
                    .into(imgPoster);
        } else {
            imgPoster.setImageDrawable(placeholder);
        }

        updateFavoriteButton();

        btnFavorite.setOnClickListener(v -> {
            if (favoriteManager.isFavorite(movieId)) {
                favoriteManager.removeFavorite(movieId);
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            } else {
                String movieJson = "{\"id\":" + movieId +
                        ",\"title\":\"" + escapeJson(title) + "\"" +
                        ",\"overview\":\"" + escapeJson(overview) + "\"" +
                        ",\"poster_path\":\"" + escapeJson(posterPath) + "\"" +
                        ",\"vote_average\":" + voteAverage +
                        ",\"release_date\":\"" + escapeJson(releaseDate) + "\"}";
                Movie movie = new Gson().fromJson(movieJson, Movie.class);
                favoriteManager.addFavorite(movie);
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            }
            updateFavoriteButton();
        });
    }

    private void updateFavoriteButton() {
        if (favoriteManager.isFavorite(movieId)) {
            btnFavorite.setText("Remove from Favorites");
        } else {
            btnFavorite.setText("Add to Favorites");
        }
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r");
    }
}
