package com.example.storygame.model;


import android.content.Context;
import android.icu.text.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SaveData implements Serializable
{
    private static final String TAG = "SaveData";
    private ArrayList<String> choices;
    private String name;

    private String scenario;

    private long chronoValue;

    private DateFormat lastPlayed;

    public SaveData(ArrayList<String> choices, String name, DateFormat lastPlayed) {
        this.choices = choices;
        this.name = name;
        this.lastPlayed = lastPlayed;
        chronoValue = 0;
        scenario = "";
    }

    public SaveData() {
        name = "test";
        choices = new ArrayList<>();
        lastPlayed = DateFormat.getDateInstance(java.text.DateFormat.LONG, Locale.FRANCE);
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    @Override
    public String toString() {
        return "SaveData{" +
                "choices=" + choices +
                ", name='" + name + '\'' +
                ", chronoValue=" + chronoValue +
                ", lastPlayed=" + lastPlayed +
                '}';
    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, SaveData saveData){
        File dir = new File(mcoContext.getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File gpxfile = new File(dir, sFileName);
            //Gson gson = new Gson();

            //String json = gson.toJson(saveData);

            ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(gpxfile.toPath()));
            oos.writeObject(saveData);
            Log.d("WTF","Writing File on" + gpxfile.toString());
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append(json);
//            writer.flush();
//            writer.close();
            oos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteSave(Context mcoContext, String sFileName){
        File dir = new File(mcoContext.getFilesDir(), "mydir");
        File gpxfile = new File(dir, sFileName);


        if (LoadData.alreadySaved(mcoContext,gpxfile.toString())){

            boolean deletionStatus = gpxfile.delete();

            if (deletionStatus) {
                Log.d("DELETUS","File deleted successfully.");
            } else {
                Log.d("DELETUS","Failed to delete the file.");
            }
        } else {
            Log.d("DELETUS","File does not exist.");
        }


    }


    public long getChronoValue() {
        return chronoValue;
    }

    public void setChronoValue(long chronoValue) {
        this.chronoValue = chronoValue;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }
}
