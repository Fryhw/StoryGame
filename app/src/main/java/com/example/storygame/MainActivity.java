package com.example.storygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storygame.GameActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String s = "Il était une fois une jeune princesse nommée Lily, qui vivait dans un royaume lointain. Un jour, un dragon maléfique attaqua le château de la princesse et captura son père, le roi. \n\nChoix 1 : La princesse décide de partir à la recherche";
        String st = s.replaceAll("\\n\\n","*");

        System.out.println(st);

        // Récupérer une référence vers le bouton à partir du layout
        button = findViewById(R.id.button_New);

        // Ajouter un écouteur de clic sur le bouton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code à exécuter lorsque le bouton est cliqué

                // Démarrer une nouvelle activité
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }
}