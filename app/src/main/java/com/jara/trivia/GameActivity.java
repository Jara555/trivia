package com.jara.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements QuestionRequest.Callback {
    /** Implements the game activity page of the app,
     * requesting for a set of random questions **/

    /* Properties */
    private static int MAX_QUESTIONS = 10;
    private Question question;
    private String userName;
    private int correct = 0;
    private int incorrect = 0;
    private int questionCount = 0;

    /* Implements the layout of the page and calls questions */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get user name of current user
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userName = EncodeString(user.getEmail());
        }
        else {
            // force user to login
            Intent intent = new Intent(GameActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }

        // call new request for question
        callQuestion();
    }

    /* Shows question when request succeeded */
    @Override
    public void gotQuestion(Question getQuestion) {
        question = getQuestion;

        // set text views
        TextView category = (TextView) findViewById(R.id.category);
        category.setText(question.getCategory());

        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question.getQuestion());

        TextView answer = (TextView) findViewById(R.id.answer);
        answer.setText(question.getCorrectAnswer());

        // generate list of numbers with random order
        ArrayList<Integer> numbers = new ArrayList<>();
        Random randomGenerator = new Random();
        while (numbers.size() < 4) {
            int random = randomGenerator.nextInt(4);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }

        // place answers in random order on buttons
        int[] buttonIds = new int[]{R.id.button1, R.id.button2, R.id.button3, R.id.button4};
        for (int index = 0; index < buttonIds.length; index++) {
            Button button = (Button) findViewById(buttonIds[index]);
            button.setText(question.getAnswers().get(numbers.get(index)));
        }
    }

    /* Creates toast error message when request failed */
    @Override
    public void gotError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /* Check correctness of answer */
    public void checkAnswer(View view) {
        final Button clickedButton = (Button) view;
        if (clickedButton.getText().equals(question.getCorrectAnswer())) {
            // if correct update score and show green color
            correct++;
            clickedButton.setBackgroundColor(getResources().getColor(R.color.correct));
        }
        else {
            // if incorrect update score and show red color
            incorrect++;
            clickedButton.setBackgroundColor(getResources().getColor(R.color.incorrect));
        }

        // wait 500ms for next question
        clickedButton.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                clickedButton.setBackgroundResource(android.R.drawable.btn_default);
                callQuestion();
            }
        }, 500);
    }

    /* Manages the amount of question requests */
    public void callQuestion() {
        if (questionCount < MAX_QUESTIONS) {
            // call new request for random question till max reached
            QuestionRequest questionRequest = new QuestionRequest(this);
            questionRequest.getQuestion(this);
            questionCount++;
        }
        else {
            // put scores in highScore object
            HighScore highScore = new HighScore(userName, correct, incorrect);

            // redirect to highscore activity
            Intent intent = new Intent(GameActivity.this, HighScoreActivity.class);
            intent.putExtra("highScore", highScore);
            intent.putExtra("FromActivity", "GAME");
            startActivity(intent);

            // prevent redirecting back to game activity
            finish();
        }
    }

    /* Encodes strings to allowed chars for firebase */
    public static String EncodeString(String string) {
        return string.replace(".", ",");
    }
}

