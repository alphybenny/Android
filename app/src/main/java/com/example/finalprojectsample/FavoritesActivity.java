package com.example.finalprojectsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private List<String> favoriteJokesList;
    private ArrayAdapter<String> favoriteJokesAdapter;
    private ListView favoriteJokesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Initialize database helper
        dbHelper = new DbHelper(this);

        // Initialize list view and adapter
        favoriteJokesList = dbHelper.getAllFavoriteJokes();
        favoriteJokesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteJokesList);
        favoriteJokesListView = findViewById(R.id.favoriteJokesListView);
        favoriteJokesListView.setAdapter(favoriteJokesAdapter);
    }
}
