package com.example.storygame.model;


import android.content.Context;
import android.icu.text.DateFormat;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SaveData
{
    private static final String TAG = "SaveData";
    private ArrayList<String> choices;
    private String name;

    private DateFormat lastPlayed;

    public SaveData(ArrayList<String> choices, String name, DateFormat lastPlayed) {
        this.choices = choices;
        this.name = name;
        this.lastPlayed = lastPlayed;
    }

    public SaveData() {
        choices = new ArrayList<>();
        lastPlayed = DateFormat.getDateInstance(java.text.DateFormat.LONG, Locale.FRANCE);
    }


    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateFormat getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(DateFormat lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public void addChoices(String choice){
        getChoices().add(choice);
        Log.d(TAG,choice);
    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File dir = new File(mcoContext.getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, sFileName);
            Log.d("Writing","Writing File");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
