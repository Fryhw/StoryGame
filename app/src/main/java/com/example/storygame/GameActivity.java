package com.example.storygame;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    private Button b1;
    private String Choix ;
    String filePath = "C:\\Users\\Admin\\Downloads\\fichier.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String Rv = "Raconte moi un histoire intéractive sous forme texte : -exemple-,puis 4 choix ";
        ChatGPTAPI.chatGPT(Rv, new ChatGPTAPI.ChatGPTListener() {
            public void onChatGPTResponse(String response) {

                TextView textView = findViewById(R.id.Text_game);
                try {
                    WriteTXT(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //  int choixIndex = response.indexOf(":");
                //String texte = response.substring(0, choixIndex).trim();
                textView.setText(response);
            }
        });
        b1 = findViewById(R.id.choice1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TextFill = findViewById(R.id.Text_game).toString();
                ChatGPTAPI.chatGPT("J'ai cette histoire : "+TextFill+ "et j'ai fait ce choix : "+Choix+" Alors conitnue L'histoire", new ChatGPTAPI.ChatGPTListener() {
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
    /**
    public void SetChoice(String myStr){
        b1 = findViewById(R.id.choice1);
        b2 = findViewById(R.id.choice2);
        b3 = findViewById(R.id.choice3);
        b4 = findViewById(R.id.choice4);

        int choixIndex = myStr.indexOf("\n\n");
        String texte = myStr.substring(0, choixIndex).trim();
        String choices = myStr.substring(choixIndex + 2);
        System.out.println(texte);
        String[] CA = choices.split("\n");
        for (int i = 0; i < CA.length; i++) {
            CA[i] = CA[i].trim();
        }
        b1.setText(CA[0]);
        b1.setText(CA[1]);
        b1.setText(CA[2]);
        b1.setText(CA[3]);

    }
**/
    public void WriteTXT(String output) throws IOException {
        String fileName = "fichier.txt"; // Nom du fichier
        String response = "Contenu à écrire dans le fichier.";

        // Obtenez le répertoire de données de l'application pour l'écriture du fichier
        File directory = new File("/");
        if (!directory.exists()) {
            directory.mkdirs(); // Créer le répertoire s'il n'existe pas
        }

        // Construire le chemin complet du fichier dans le répertoire
        String filePath = directory.getAbsolutePath() + File.separator + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // Écriture dans le fichier
            writer.write(response);

            // Ajout d'un saut de ligne pour séparer le contenu écrit et lu
            writer.newLine();

            // Lecture du contenu écrit dans le fichier
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Contenu lu depuis le fichier : " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}