package com.example.storygame;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPI {

    public interface ChatGPTListener {
        void onChatGPTResponse(String response);
    }

    public static void chatGPT(String prompt, ChatGPTListener listener) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... prompts) {
                return performChatGPTRequest(prompts[0]);
            }

            @Override
            protected void onPostExecute(String response) {
                listener.onChatGPTResponse(response);
            }
        }.execute(prompt);
    }

    private static String performChatGPTRequest(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // Extract the message from the JSON response
            return extractMessageFromJSONResponse(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        return response.substring(start, end);
    }
}
