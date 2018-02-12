package com.londonappbrewery.destini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Story {
    private String story;

    private List<Choice> choices;

    public Story() {
    }

    public Story(String story, List<Choice> choices) {

        this.story = story;
        this.choices = choices;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    @Override
    public String toString() {
        return "Story{" +
                "story='" + story + '\'' +
                ", choices=" + choices +
                '}';
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
