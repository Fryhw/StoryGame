package com.example.storygame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                Intent intent = new Intent(GameActivity.this, Trophee.class);
                startActivity(intent);
            }
        });



    }

}