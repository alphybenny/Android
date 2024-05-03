package com.example.finalprojectsample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayJokeActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private String joke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        // Get joke from intent extra
        joke = getIntent().getStringExtra("joke");

        // Display joke in TextView
        TextView jokeTextView = findViewById(R.id.jokeTextView);
        jokeTextView.setText(joke);

        // Get the RatingBar
        ratingBar = findViewById(R.id.ratingBar);

        // Handle button click to save the joke and rating
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                saveJokeWithRating(joke, rating);
                Toast.makeText(DisplayJokeActivity.this, "Joke saved with rating: " + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveJokeWithRating(String joke, float rating) {
        // Save the joke along with its rating to the database
        // You can use your database helper class to perform this operation
        DbHelper dbHelper = new DbHelper(this);
        dbHelper.addFavoriteJoke(joke, rating);
    }
}
