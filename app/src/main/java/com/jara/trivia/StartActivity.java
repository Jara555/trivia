package com.jara.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View view) {
        // create intent and pass through to game activity
        Intent intent = new Intent(StartActivity.this, GameActivity.class);
        startActivity(intent);

    }

    public void viewHighscores(View view) {
        // create intent and pass through to game activity
        Intent intent = new Intent(StartActivity.this, HighScoreActivity.class);
        intent.putExtra("FromActivity", "START");
        startActivity(intent);
    }
}
