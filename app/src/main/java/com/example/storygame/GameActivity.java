package com.example.storygame;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class GameActivity extends AppCompatActivity {

    private Button b1,xT;
    private GridLayout gridLayout,cG;
    private TextView swipeView,cT;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gridLayout = findViewById(R.id.gridLayout);
        swipeView = findViewById(R.id.swipeView);
        cG = findViewById(R.id.counterGrid);
        cT = findViewById(R.id.counterText);
        xT = findViewById(R.id.circleButton1);
        ShakeDetector shakeDetector = new ShakeDetector(this);

        String Rv = "Raconte moi un histoire intéractive sous forme texte : -exemple-,puis 4 choix ";
        ChatGPTAPI.chatGPT(Rv, new ChatGPTAPI.ChatGPTListener() {
            @Override
            public void onChatGPTResponse(String response) {
                TextView textView = findViewById(R.id.Text_game);
                textView.setText(response);
                gridLayout.setVisibility(View.GONE);
                swipeView.setVisibility(View.VISIBLE);
            }
        });

        swipeView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                gridLayout.setVisibility(View.VISIBLE);
                swipeView.setVisibility(View.GONE);
            }
            public void onSwipeRight(){
                swipeView.setVisibility(View.GONE);
                cG.setVisibility(View.VISIBLE);
                cT.setVisibility(View.VISIBLE);
                xT.setVisibility(View.VISIBLE);
                updateCounterText();
            }
            public void onSwipeTop(){

            }
            public void onSwipeBottom(){

            }
        });
        shakeDetector.setOnShakeListener(() -> {
            System.out.println("BOUMMM");
        });


        b1 = findViewById(R.id.choice1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                ChatGPTAPI.chatGPT("J'ai cette histoire : "+TextFill+ "et j'ai fait ce choix :Alors conitnue L'histoire", new ChatGPTAPI.ChatGPTListener() {
                    @Override
                    public void onChatGPTResponse(String response) {
                        TextView textView = findViewById(R.id.Text_game);
                        //int choixIndex = response.indexOf("\n\n");
                        //String texte = response.substring(0, choixIndex).trim();
                        textView.setText(response);

                    }
                });
            }
        });
    }
    public void incrementCounter(View view) {
        counter++;
        updateCounterText();
    }

    private void updateCounterText() {
        cT.setText(String.valueOf(counter));
    }
}