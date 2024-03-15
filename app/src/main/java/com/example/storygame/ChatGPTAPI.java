package com.example.storygame;

import okhttp3.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

public class ChatGPTAPI {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/completions";

    public static String getResponse(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        JsonObject jsonInput = new JsonObject();
        jsonInput.addProperty("model", "text-davinci-002");
        jsonInput.addProperty("prompt", prompt);
        jsonInput.addProperty("max_tokens", 100);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                jsonInput.toString()
        );

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        response.close();

        JsonObject jsonResponse = new Gson().fromJson(responseBody, JsonObject.class);

        // Vérifie si la réponse JSON contient un élément "choices"
        if (jsonResponse.has("choices")) {
            JsonArray choicesArray = jsonResponse.getAsJsonArray("choices");
            if (choicesArray.size() > 0) {
                JsonObject choiceObject = choicesArray.get(0).getAsJsonObject();
                if (choiceObject.has("text")) {
                    return choiceObject.get("text").getAsString();
                }
            }
        }

        // Si la structure JSON est incorrecte ou si les données nécessaires sont manquantes
        return "Réponse non disponible";
    }
}
