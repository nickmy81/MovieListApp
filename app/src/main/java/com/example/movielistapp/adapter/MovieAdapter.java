package com.example.movielistapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movielistapp.R;
import com.example.movielistapp.activity.DetailActivity;
import com.example.movielistapp.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private final Context context;
    private final List<Movie> movieList;
    private final ColorDrawable placeholder;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
        this.placeholder = new ColorDrawable(Color.parseColor("#1F1F3D"));
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return movieList.get(position).getId();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());
        holder.rating.setText(String.format("%.1f", movie.getVoteAverage()));

        String releaseDate = movie.getReleaseDate();
        if (releaseDate != null && releaseDate.length() >= 4) {
            holder.releaseDate.setText(releaseDate.substring(0, 4));
        } else {
            holder.releaseDate.setText("N/A");
        }

        String overview = movie.getOverview();
        if (overview != null && !overview.isEmpty()) {
            holder.overview.setText(overview);
        } else {
            holder.overview.setText("No description available");
        }

        String posterUrl = movie.getPosterUrlSmall();
        if (posterUrl != null) {
            Picasso.get()
                    .load(posterUrl)
                    .placeholder(placeholder)
                    .error(placeholder)
                    .fit()
                    .centerCrop()
                    .into(holder.poster);
        } else {
            holder.poster.setImageDrawable(placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", movie.getId());
            intent.putExtra("title", movie.getTitle());
            intent.putExtra("overview", movie.getOverview());
            intent.putExtra("poster", movie.getPosterUrl());
            intent.putExtra("poster_path", movie.getPosterPath());
            intent.putExtra("vote_average", movie.getVoteAverage());
            intent.putExtra("release_date", movie.getReleaseDate());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        Picasso.get().cancelRequest(holder.poster);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title, rating, releaseDate, overview;
        final ImageView poster;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            poster = itemView.findViewById(R.id.ivPoster);
            rating = itemView.findViewById(R.id.tvRating);
            releaseDate = itemView.findViewById(R.id.tvReleaseDate);
            overview = itemView.findViewById(R.id.tvOverview);
        }
    }
}
