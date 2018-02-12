package com.londonappbrewery.destini;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    TextView mStoryTextView;
    Button mTopButton;
    Button mBottomButton;
    Map<String, List<Choice>> storyChoicesMapBank;
    List<String> userStoriesPath;
    private String mCurrentStory;
    private String TAG = "Destini";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (storyChoicesMapBank == null) storyChoicesMapBank = setRoutes();

        Log.d(TAG, "onCreate: StoryChoicesMapba: " + storyChoicesMapBank);

        mStoryTextView = (TextView) findViewById(R.id.storyTextView);
        mTopButton = (Button) findViewById(R.id.buttonTop);
        mBottomButton = (Button) findViewById(R.id.buttonBottom);

        mBottomButton.setBackgroundColor(Color.BLUE);

        if (savedInstanceState == null) {
            initializeView();
        } else {
            mCurrentStory = savedInstanceState.getString("storyKey");
            Log.d("Destini", "onCreate: Currentstory" + mCurrentStory);
            userStoriesPath = savedInstanceState.getStringArrayList("userStoryPath");
        }

        updateView(mCurrentStory);
        setListenersForButtons(mCurrentStory);
    }

    private void initializeView() {
        Log.d(TAG, "initializeView: calllee");
        mCurrentStory = "T1_Story";
        userStoriesPath = new ArrayList<>();
        mTopButton.setVisibility(View.VISIBLE);
    }

    private void updateView(String currentStory) {
        Log.d(TAG, "updateView: from " + mCurrentStory + " to " + currentStory);
        mCurrentStory = currentStory;
        userStoriesPath.add(currentStory);

        int storyIdentifier = getResources().getIdentifier(currentStory, "string", getPackageName());
        mStoryTextView.setText(getString(storyIdentifier));

        List<Choice> choices = storyChoicesMapBank.get(currentStory);
        Log.d(TAG, "updateView: choices" + choices);

        // When we are on the last story
        if (choices == null) {
            Log.d(TAG, "updateView: choices null");
            mTopButton.setVisibility(View.INVISIBLE);
            mBottomButton.setText(R.string.play_again);
            mBottomButton.setBackgroundColor(Color.GREEN);
        } else { // next story is not empty
            mTopButton.setText(getString(getResources().getIdentifier(choices.get(0).getAnswer(), "string", getPackageName())));
            mBottomButton.setText(getString(getResources().getIdentifier(choices.get(1).getAnswer(), "string", getPackageName())));
        }
    }

    private void setListenersForButtons(String currentStory) {
        Log.d(TAG, "setListenersForButtons: Currentstory" + currentStory);

        List<Choice> choices = storyChoicesMapBank.get(currentStory);
        Log.d(TAG, "setListenersForButtons: Choices: " + choices);

        if (choices == null) { // Only for bottom button
            mBottomButton.setOnClickListener(view -> {
                initializeView();
                updateView(mCurrentStory);
                setListenersForButtons(mCurrentStory);
            });
            return;
        }

        mTopButton.setOnClickListener(view -> {
            String nextStory = choices.get(0).getNextStory();
            updateView(nextStory);
            setListenersForButtons(nextStory);
        });

        mBottomButton.setOnClickListener(view -> {
            String nextStory = choices.get(1).getNextStory();
            updateView(choices.get(1).getNextStory());
            setListenersForButtons(nextStory);
        });
    }

    private Map<String, List<Choice>> setRoutes() {
        Map<String, List<Choice>> storyChoiceMap = new HashMap<>();

        try {
            InputStream routesStream = getResources().openRawResource(R.raw.story_routes);
            String storyJson = IOUtils.toString(routesStream);
            Log.d(TAG, "Story Json: " + storyJson);
            routesStream.close();

            Gson gson = new Gson();
//            Stories stories = gson.fromJson(storyJson, Stories.class);

            Stories stories = new ObjectMapper().readValue(storyJson, Stories.class);

            for (Story story : stories.getStories()) {
                storyChoiceMap.put(story.getStory(), story.getChoices());
            }

        } catch (IOException e) {
            Log.e(TAG, "setRoutes: Error while parsing json", e);
        }

        return storyChoiceMap;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("storyKey", mCurrentStory);
        Log.d(TAG, "onSaveInstanceState: Current" + mCurrentStory);
        outState.putStringArrayList("userStoryPath", (ArrayList<String>) userStoriesPath);
    }
}
