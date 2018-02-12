package com.londonappbrewery.destini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sai Sushanth on 11-02-2018.
 */

public class Choice {
    String answer;

    String nextStory;

    public Choice() {
    }

    public Choice(String ansKey, String nextStory) {
        this.answer = ansKey;
        this.nextStory = nextStory;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "answer='" + answer + '\'' +
                ", nextStory='" + nextStory + '\'' +
                '}';
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNextStory() {
        return nextStory;
    }

    public void setNextStory(String nextStory) {
        this.nextStory = nextStory;
    }
}