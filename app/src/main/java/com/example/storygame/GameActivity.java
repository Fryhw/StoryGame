package com.example.storygame;

import static com.example.storygame.ChatGPTAPI.chatGPT;
import static com.example.storygame.ChatGPTAPI.performChatGPTRequest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.RequestConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) { 

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Récupérer une référence vers le bouton à partir du layout
        b1 = findViewById(R.id.choice1);
        b2 = findViewById(R.id.choice2);
        b3 = findViewById(R.id.choice3);
        b4 = findViewById(R.id.choice4);
        String Rv = "Raconte moi un histoire intéractive puis écrit 4 choix qui nous permettrons de continuer histoire sous forme -> Texte - Choix : 1 - 2 - 3 - 4 ";
        String TextFill = findViewById(R.id.Text_game).toString();
        ChatGPTAPI.chatGPT(Rv, new ChatGPTAPI.ChatGPTListener() {
            @Override
            public void onChatGPTResponse(String response) {
                TextView textView = findViewById(R.id.Text_game);
                textView.setText(response);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Rv2 = "Raconte moi un histoire intéractive puis écrit 4 choix qui nous permettrons de continuer histoire sous forme -> Texte - Choix : 1 - 2 - 3 - 4 ";

                ChatGPTAPI.chatGPT(Rv2, new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        int choixIndex = response.indexOf("Choix");
                        if (choixIndex!=-1) {
                            String texte = response.substring(0, choixIndex).trim();
                            textView.setText(texte);
                            System.out.println(response);
                            String choixString = response.substring(choixIndex);
                            String[] choixArray = choixString.split("\n");


                            b1 = findViewById(R.id.choice1);
                            String si  = choixString;
                            b1.setText(si);
                        }
                        else {
                            String s = "NIQUE CETTE APP";
                            System.out.println(s);
                            System.out.println(response);
                        }

                        //String[] choixArray = choixString.split("\n");
                    }
                });
            }
        });
    }

}