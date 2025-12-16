package com.example.movielistapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.movielistapp.model.Movie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static final String PREF_NAME = "movie_favorites";
    private static final String KEY_FAVORITES = "favorites_list";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public FavoriteManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void addFavorite(Movie movie) {
        List<Movie> favorites = getFavorites();
        if (!isFavorite(movie.getId())) {
            favorites.add(movie);
            saveFavorites(favorites);
        }
    }

    public void removeFavorite(int movieId) {
        List<Movie> favorites = getFavorites();
        for (int i = 0; i < favorites.size(); i++) {
            if (favorites.get(i).getId() == movieId) {
                favorites.remove(i);
                break;
            }
        }
        saveFavorites(favorites);
    }

    public boolean isFavorite(int movieId) {
        List<Movie> favorites = getFavorites();
        for (Movie movie : favorites) {
            if (movie.getId() == movieId) {
                return true;
            }
        }
        return false;
    }

    public List<Movie> getFavorites() {
        String json = sharedPreferences.getString(KEY_FAVORITES, null);
        if (json == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void saveFavorites(List<Movie> favorites) {
        String json = gson.toJson(favorites);
        sharedPreferences.edit().putString(KEY_FAVORITES, json).apply();
    }
}
