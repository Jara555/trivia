package com.jara.trivia;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuestionRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    /** Specialized request class, which downloads data from the server
     * and transforms this to a Question object **/

    /* Properties */
    private Context context;
    private Callback activity;
    private ArrayList<String> answers = new ArrayList<>();
    private int count;
    private int MAX_ANSWERS = 4;

    /* Interface */
    public interface Callback {
        void gotQuestion(Question question);
        void gotError(String message);
    }

    /* Constructor */
    public QuestionRequest(Context context) {
        this.context = context;
    }

    /* Retrieves categories from API */
    public void getQuestion(Callback inputActivity) {
        // initialize activity
        activity = inputActivity;

        // create new request queue
        RequestQueue queue = Volley.newRequestQueue(context);

        //int randomInt = new Random().nextInt(100);

        // request data from url and add to queue
        String url = "http://jservice.io/api/random";

        count = 0;

        for (int i = 0; i < MAX_ANSWERS; i++) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                    null, this, this);
            queue.add(jsonArrayRequest);
        }
    }

    /* Extracts categories when request succeeded */
    @Override
    public void onResponse(JSONArray response) {
        try {
            // Get current json object
            JSONObject questionObject = response.getJSONObject(0);

            // get answer and add to answers list
            String answer = questionObject.getString("answer");
            answers.add(answer);

            count++;

            // only for the last request create Question object
            if (count == MAX_ANSWERS) {
                // get question
                String questionText = questionObject.getString("question");

                // get category info
                JSONObject categoryObject = questionObject.getJSONObject("category");
                String categoryText = categoryObject.getString("title");
                int categoryId = categoryObject.getInt("id");

                // create question object
                Question question = new Question(categoryText, categoryId, questionText, answers, answer);
                activity.gotQuestion(question);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* Gets error message if request not succeeded */
    @Override
    public void onErrorResponse(VolleyError error) {
        // pass message through to got error method
        activity.gotError(error.getMessage());
    }
}

