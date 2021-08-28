package com.example.impeccable_india;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class infovideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infovideo);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        YouTubePlayerView youTubePlayerView2 = findViewById(R.id.youtube_player_view2);
        getLifecycle().addObserver(youTubePlayerView2);
        YouTubePlayerView youTubePlayerView3 = findViewById(R.id.youtube_player_view3);
        getLifecycle().addObserver(youTubePlayerView3);
        YouTubePlayerView youTubePlayerView4 = findViewById(R.id.youtube_player_view4);
        getLifecycle().addObserver(youTubePlayerView4);
        YouTubePlayerView youTubePlayerView5 = findViewById(R.id.youtube_player_view5);
        getLifecycle().addObserver(youTubePlayerView5);
        YouTubePlayerView youTubePlayerView6 = findViewById(R.id.youtube_player_view6);
        getLifecycle().addObserver(youTubePlayerView6);
        YouTubePlayerView youTubePlayerView7 = findViewById(R.id.youtube_player_view7);
        getLifecycle().addObserver(youTubePlayerView7);
        YouTubePlayerView youTubePlayerView8 = findViewById(R.id.youtube_player_view8);
        getLifecycle().addObserver(youTubePlayerView8);
    }
}