package com.jara.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class HighScoresAdapter extends ArrayAdapter<HighScore> {
    /** Highscores adapter class loading views with data of users and highscores **/

    /* Properties */
    private ArrayList<HighScore> highScores;

    /* Constructor */
    public HighScoresAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HighScore> objects) {
        super(context, resource, objects);

        // set menu items list to objects input
        highScores = objects;
    }

    /* Gets view for MenuItem objects */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get menu row layout if not already exists
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_highscore, parent, false);
        }

        // get single menu item of the array list
        HighScore highScore = highScores.get(position);

        // set name
        TextView name = convertView.findViewById(R.id.name);
        name.setText(DecodeString((highScore.getName())));

        // set score
        TextView score = convertView.findViewById(R.id.score);
        score.setText(String.format("%.2s", highScore.getScore()));

        return convertView;
    }

    /* Decodes strings back to original chars */
    public static String DecodeString(String string) {
        return string.replace(",", ".");
    }
}
