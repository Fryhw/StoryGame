package com.example.storygame;

import static com.example.storygame.ChatGPTAPI.chatGPT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.RequestConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


public class GameActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ChatGPTAPI.chatGPT("hello, how are you?", new ChatGPTAPI.ChatGPTListener() {
            @Override
            public void onChatGPTResponse(String response) {
                //Log.d("ChatGPT Response", response);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Récupérer une référence vers le bouton à partir du layout
        button = findViewById(R.id.choice1);

        // Ajouter un écouteur de clic sur le bouton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué

                // Démarrer une nouvelle activité
                Intent intent = new Intent(GameActivity.this, OtherActivity.class);
                startActivity(intent);
            }
        });
    }

}