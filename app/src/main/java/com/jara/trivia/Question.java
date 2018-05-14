package com.jara.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    /** Question class containing all properties and methods for Question objects **/

    /* Properties */
    private String category;
    private int categoryId;
    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;

    public Question(String category, int categoryId, String question, ArrayList<String> answers, String correctAnswer) {

        this.category = category;
        this.categoryId = categoryId;
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {

        return categoryId;
    }

    /* Setters */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    /* Getters */
    public String getCategory() {

        return category;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
