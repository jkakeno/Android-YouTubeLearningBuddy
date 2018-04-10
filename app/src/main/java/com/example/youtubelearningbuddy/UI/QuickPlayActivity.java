package com.example.youtubelearningbuddy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.youtubelearningbuddy.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/*This class represents the youtube player to play videos.*/

public class QuickPlayActivity extends YouTubeBaseActivity {

    private static final String TAG =QuickPlayActivity.class.getSimpleName();
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        setContentView(R.layout.youtube_player_view);

        /*Get the data sent from main activity.*/
        Intent intent = getIntent();
        result = intent.getStringExtra("videoId");

        /*Inflate the youtube player from xml.*/
        YouTubePlayerView youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);

        /*Initialize the youtube player.*/
        youTubePlayerView.initialize("YOUR API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.loadVideo(result);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}
