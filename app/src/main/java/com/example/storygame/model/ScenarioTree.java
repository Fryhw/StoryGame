package com.example.storygame.model;

public class ScenarioTree
{
    private SaveData saveData;

    public ScenarioTree(SaveData saveData) {
        this.saveData = saveData;
    }

    public SaveData getSaveData() {
        return saveData;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }
}
