package com.jara.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements QuestionRequest.Callback {

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // get intent of clicked category
        Intent intent = getIntent();

        // call new request for that category
        QuestionRequest questionRequest = new QuestionRequest(this);
        questionRequest.getQuestion(this);
    }

    /* Shows question when request succeeded */
    @Override
    public void gotQuestion(Question getQuestion) {

        question = getQuestion;

        TextView category = (TextView) findViewById(R.id.category);
        category.setText(question.getCategory());

        TextView questionText = (TextView) findViewById(R.id.question);
        questionText.setText(question.getQuestion());

        TextView answer = (TextView) findViewById(R.id.answer);
        answer.setText(question.getCorrectAnswer());

        // generate list of numbers with random order
        ArrayList<Integer> numbers = new ArrayList<Integer>();
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

    public void checkAnswer(View view) {

        Button clickedButton = (Button) view;
        if (clickedButton.getText().equals(question.getCorrectAnswer())) {
            clickedButton.setBackgroundColor(getResources().getColor(R.color.correct));
        }
        else {
            clickedButton.setBackgroundColor(getResources().getColor(R.color.incorrect));
        }

        restartActivity();
    }

    public void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

