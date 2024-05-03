package com.example.finalprojectsample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button1, button2,button3, button4,button5, button6, button7;
    FirebaseAuth auth;
    TextView textView5;
    Button button;
    FirebaseUser user;
    private DbHelper dbHelper;
//    private List<String> favoriteJokesList;
//    private ArrayAdapter<String> favoriteJokesAdapter;
//    private ListView favoriteJokesListView;
//    private static final int DISPLAY_JOKE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.button);
        textView5 = findViewById(R.id.textView5);
        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            textView5.setText(user.getEmail());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Programming");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Pun");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Miscellaneous");
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Dark");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Christmas");
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Spooky");
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONRequest("Any");
            }
        });
        dbHelper = new DbHelper(this);
//
//        // Initialize list view and adapter
//        favoriteJokesList = dbHelper.getAllFavoriteJokes();
//        favoriteJokesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, favoriteJokesList);
//        favoriteJokesListView = findViewById(R.id.favoriteJokesListView);
//        favoriteJokesListView.setAdapter(favoriteJokesAdapter);

        // Handle button click to display a joke
/*        Button displayButton = findViewById(R.id.displayButton);
        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayJokeActivity.class);
                startActivityForResult(intent, DISPLAY_JOKE_REQUEST_CODE);
            }
        });*/

        // Handle button click to access favorites
        ImageButton favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start FavoritesActivity
                Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == DISPLAY_JOKE_REQUEST_CODE && resultCode == RESULT_OK) {
//            if (data != null && data.hasExtra("joke")) {
//                String joke = data.getStringExtra("joke");
//                float rating = data.getFloatExtra("rating", 0);
//                dbHelper.addFavoriteJoke(joke, rating);
//                favoriteJokesList.add(joke);
//                favoriteJokesAdapter.notifyDataSetChanged();
//            }
//        }
//    }

    private void sendJSONRequest(final String buttonId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Construct URL for API request
                    String apiUrl = "https://sv443.net/jokeapi/v2/joke/" + buttonId + "?blacklistFlags=nsfw,religious,political,racist,sexist,explicit";

                    // Make HTTP request using a network library like Retrofit or Volley
                    // Here, I'm using HttpURLConnection for simplicity
                    String response = Utils.get(apiUrl);

                    // Handle JSON response
                    JSONObject jsonResponse = new JSONObject(response);

                    // Extract joke setup, delivery, and type from JSON response
                    String jokeType = jsonResponse.getString("type");
                    String setup = jsonResponse.getString("setup");
                    String delivery = jsonResponse.getString("delivery");
                    String joke = "";

                    // Construct the complete joke based on type
                    if (jokeType.equals("twopart")) {
                        // If the joke type is 'twopart', combine setup and delivery
                        joke = setup + "\n\n" + delivery;
                    } else {
                        // If the joke type is not 'twopart', directly use the joke string
                        joke = jsonResponse.getString("joke");
                    }

                    // Start new activity to display the joke
                    Intent intent = new Intent(MainActivity.this, DisplayJokeActivity.class);
                    intent.putExtra("joke", joke);
                    startActivity(intent);

                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
