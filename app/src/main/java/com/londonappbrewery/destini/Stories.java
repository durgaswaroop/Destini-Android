package com.londonappbrewery.destini;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stories {
    private List<Story> stories;

    public Stories(List<Story> stories) {
        this.stories = stories;
    }

    public Stories() {
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> pStories) {
        stories = pStories;
    }

    @Override
    public String toString() {
        return "Stories{" +
                "stories=" + stories +
                '}';
    }
}
