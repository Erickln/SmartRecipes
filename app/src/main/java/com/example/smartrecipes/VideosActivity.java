package com.example.smartrecipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

/////////////////CLASE DONDE SE IMPLEMENTA LA OPCION DE CORRER UN VIDEO DE YOUTUBE/////////////////////////////
public class VideosActivity extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    public String videoId;
    Receta receta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                videoId= receta.getUrl().toString();
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }
}