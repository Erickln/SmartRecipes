package com.example.smartrecipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VerReceta extends YouTubeBaseActivity {
    private Button button;
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    Receta receta;

    TextView nombre,ingredientes,procedimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_receta);
        youTubePlayerView=findViewById(R.id.youtubePlayer_view);
        button = findViewById(R.id.button9);

        this.receta = (Receta) getIntent().getSerializableExtra("recetaParaVer");


        String temp="Ingredientes:\n";
        for (int i = 0; i < receta.ingredientes.size(); i++) {
            if (i==receta.ingredientes.size()-1){
                temp+=receta.ingredientes.get(i).nombre;
            }
            temp+=receta.ingredientes.get(i).nombre+"\n";
        }

        nombre = findViewById(R.id.nombreVerReceta);
        ingredientes = findViewById(R.id.ingredientesVerReceta);
        procedimiento = findViewById(R.id.procedimientoVerReceta);

        nombre.setText(receta.nombre);
        ingredientes.setText(temp);
        procedimiento.setText("Procedimiento:\n"+receta.procedimiento);



        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(receta.getUrl());
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize(PlayerConfig.API_KEY,onInitializedListener);
            }
        });
    }


}