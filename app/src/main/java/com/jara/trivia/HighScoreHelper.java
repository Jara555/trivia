package com.jara.trivia;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HighScoreHelper {
    /** HighScoreHelper class containing all properties and methods
     *  to interact with firebase database **/

    /* Properties */
    private HighScore highScore;
    private Callback activity;
    private DatabaseReference db;
    private ArrayList<HighScore> highScores = new ArrayList<>();

    private static String HIGHSCORE = "Highscore";

    /* Interface */
    public interface Callback {
        void gotHighscores(ArrayList<HighScore> highScores);
        void gotError(String message);
    }

    /* Constructor */
    public HighScoreHelper() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    /* Updates high scores in the database */
    public void postNewHighscore(final HighScore highScore) {
        // get user name
        final String name = highScore.getName();

        // read from database if user already exists
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(HIGHSCORE).hasChild(name)) {
                    // if user in database get previous score object
                    HighScore prevScore = (HighScore) dataSnapshot.child(HIGHSCORE).child(name).getValue(HighScore.class);

                    // update scores based on previous and new scores
                    highScore.setCorrect(prevScore.getCorrect() + highScore.getCorrect());
                    highScore.setIncorrect(prevScore.getIncorrect() + highScore.getIncorrect());
                    highScore.setScore();

                    // save updated highscore in database
                    db.child(HIGHSCORE).child(name).setValue(highScore);
                }
                else {
                    // if user was not yet in database save new highscore
                    db.child(HIGHSCORE).child(name).setValue(highScore);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Read", "Failed to read value.", databaseError.toException());
                activity.gotError(databaseError.getMessage());
            }
        });
    }

    /* Retrieves highscores from the database */
    public void getHighscores(Callback inputActivity) {
        activity = inputActivity;

        // read from the database
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // reset old highscores
                if (!highScores.isEmpty()) {
                    highScores = new ArrayList<>();
                }
                // iterate over users in highscore database
                for (DataSnapshot snapshot : dataSnapshot.child(HIGHSCORE).getChildren()) {
                    // get highscore object and add to list
                    HighScore highScore = (HighScore) snapshot.getValue(HighScore.class);
                    highScores.add(highScore);
                    Log.d("Read", "Value is: " + highScore.getName());
                }
                // pass highscores list to got method
                activity.gotHighscores(highScores);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // log error and pass message to got error method
                Log.w("Read", "Failed to read value.", error.toException());
                activity.gotError(error.getMessage());
            }
        });
    }
}
