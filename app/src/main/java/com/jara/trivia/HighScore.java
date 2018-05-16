package com.jara.trivia;

import java.io.Serializable;

public class HighScore implements Serializable {
    /** HighScore class containing all properties and methods for HighScore objects **/

    /* Properties */
    private String name;
    private int correct;
    private int incorrect;
    private int total;
    private double score;

    /* Firebase constructor */
    public HighScore() {
    }

    public HighScore(String aName, int aCorrect, int aIncorrect) {
        this.name = aName;
        this.correct = aCorrect;
        this.incorrect = aIncorrect;

        // calculate total and score
        this.total = correct + incorrect;
        this.score = (((double) correct - (double) incorrect) / (double) total) * 100;
    }

    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setCorrect(int newcorrect) {
        this.correct = newcorrect;
        this.total = incorrect + correct;
    }

    public void setIncorrect(int newincorrect) {
        this.incorrect = newincorrect;
        this.total = incorrect + correct;
    }

    public void setScore() {
        this.score = (((double) correct - (double) incorrect) / (double) total) * 100;
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public int getCorrect() {
        return correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public int getTotal() {
        return total;
    }

    public double getScore() {
        return score;
    }
}
