package com.example.movielistapp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movielistapp.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView imgPoster;
    TextView tvTitle, tvOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imgPoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);

        String title = getIntent().getStringExtra("title");
        String overview = getIntent().getStringExtra("overview");
        String poster = getIntent().getStringExtra("poster");

        tvTitle.setText(title);
        tvOverview.setText(overview);
        Picasso.get().load(poster).into(imgPoster);
    }
}
