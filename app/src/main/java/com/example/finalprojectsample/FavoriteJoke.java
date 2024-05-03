package com.example.finalprojectsample;

public class FavoriteJoke {
    private String joke;
    private float rating;

    public FavoriteJoke(String joke, float rating) {
        this.joke = joke;
        this.rating = rating;
    }

    public String getJoke() {
        return joke;
    }

    public void setJoke(String joke) {
        this.joke = joke;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
