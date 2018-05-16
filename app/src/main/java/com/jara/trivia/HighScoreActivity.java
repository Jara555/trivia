package com.jara.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreHelper.Callback {
    /** Manages the highscore activity page of the app
     * Allowing users to update and view highscores **/

    /* Implements the layout of the highscore overview */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // create highscore helper object and post new highscore
        HighScoreHelper highScoreHelper = new HighScoreHelper();

        // get highscore object from game activity
        Intent intent = getIntent();
        String fromActivity = (String) intent.getStringExtra("FromActivity");
        if (fromActivity.equals("GAME")) {
            // if redirected from game activity, save highscore
            HighScore highScore = (HighScore) intent.getSerializableExtra("highScore");
            highScoreHelper.postNewHighscore(highScore);
        }

        // load all highscores
        highScoreHelper.getHighscores(this);
    }

    /* Highscore request succeed */
    @Override
    public void gotHighscores(ArrayList<HighScore> highScores) {
        // create new array adapter and set to list view
        HighScoresAdapter adapter = new HighScoresAdapter(this, R.layout.row_highscore, highScores);
        ListView listView = (ListView) findViewById(R.id.listViewMenu);
        listView.setAdapter(adapter);
    }

    /* Highscore request failed */
    @Override
    public void gotError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
