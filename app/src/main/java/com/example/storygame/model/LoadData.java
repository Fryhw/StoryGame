package com.example.storygame.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.List;


public class LoadData {
    private SaveData saveData;
    private static final Type SAVE_TYPE = new TypeToken<List<SaveData>>() {
    }.getType();

    public SaveData getSaveData() {
        return saveData;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }


    public static SaveData loadSave(Context mcoContext, String filepath) {
        File dir = new File(mcoContext.getFilesDir(), "mydir");


        File gpxfile = new File(dir, filepath);


        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(gpxfile.toPath()));
            SaveData saveData1 = (SaveData) ois.readObject();
            Log.d("XYZ", String.valueOf(saveData1.getChronoValue()));
            Log.d("XYZ",saveData1.getScenario());
            return saveData1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean alreadySaved(Context context, String filepath){
        File dir = new File(context.getFilesDir(), "mydir");


        File file = new File(dir, filepath);
        if(file.exists() && !file.isDirectory()) {
            return true;
        }
        return false;
    }


}
