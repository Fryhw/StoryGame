package com.example.storygame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.storygame.ChatGPTAPI;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView responseTextView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTextView = findViewById(R.id.TestView);
        Button buttonNew = findViewById(R.id.button_New);
        Button buttonLoad = findViewById(R.id.button_load);
        Button buttonAchieve = findViewById(R.id.button_Achieve);

        new ChatGPTTask().execute("Bonjour, comment ça va ?");
        buttonNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1 = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(i1);
                }
            });

    buttonLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i2 = new Intent(MainActivity.this, Save.class);
                    startActivity(i2);
                }
            });

    buttonAchieve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i3 = new Intent(MainActivity.this, Trophee.class);
                    startActivity(i3);
                }
            });
    }

    private class ChatGPTTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String prompt = strings[0];
            try {
                return ChatGPTAPI.getResponse(prompt);
            } catch (IOException e) {
                e.printStackTrace();
                return "Erreur lors de la requête à l'API ChatGPT";
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            responseTextView.setText(response);
        }
    }
}
