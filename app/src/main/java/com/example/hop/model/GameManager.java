package com.example.hop.model;

import android.content.Context;
import android.util.Log;

/**
 * Created by elberry on 6/7/15.
 */
public class GameManager {

    private static final String TAG = GameManager.class.getSimpleName();
    private static final String STAGES_KEY = "1125112511251122611226112261122611226112261122611226112261122611226112261122611226112261122611226112261122611226112261122611226112261122611226112261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222611222261122226112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236112222223611222222361122222236";

    private static GameManager instance;

    public Stage currentStage;
    private Context context;
    public Stage[] stages = new Stage[STAGES_KEY.length()];

    private GameManager(Context context) {
        this.context = context.getApplicationContext();
        char[] stagesChars = STAGES_KEY.toCharArray();
        int checkpointCount = 0;
        for (int i = 0; i < stagesChars.length; i++) {
            char stageChar = stagesChars[i];
            Stage stage = new Stage();
            stage.stageIndex = i;
            stage.displayIndex = i;
            stages[i] = stage;
            switch (stageChar) {
                case '1':
                    stage.difficulty = Stage.Difficulty.easy;
                    break;
                case '2':
                    stage.difficulty = Stage.Difficulty.medium;
                    break;
                case '3':
                    stage.difficulty = Stage.Difficulty.hard;
                    break;
                case '4':
                    stage.displayIndex = checkpointCount++;
                    stage.difficulty = Stage.Difficulty.easy;
                    stage.checkpoint = true;
                    break;
                case '5':
                    stage.displayIndex = checkpointCount++;
                    stage.difficulty = Stage.Difficulty.medium;
                    stage.checkpoint = true;
                    break;
                case '6':
                    stage.displayIndex = checkpointCount++;
                    stage.difficulty = Stage.Difficulty.hard;
                    stage.checkpoint = true;
                    break;
            }
        }
        currentStage = stages[0];
        Log.d(TAG, "Loaded stages - total: " + stages.length + ", checkpoints: " + checkpointCount);
    }

    public static synchronized void initialize(Context context) {
        if (instance == null) {
            instance = new GameManager(context);
        }
    }

    public static GameManager getInstance() {
        return instance;
    }

    public Stage doFailCurrentStage() {
        int previousStageIndex = currentStage.stageIndex - 1;
        if (previousStageIndex <= 0) {
            return currentStage;
        }

        Stage previousCheckpoint = null;
        for (int i = previousStageIndex; previousCheckpoint == null && i >= 0; i--) {
            Stage stage = stages[i];
            if (stage.checkpoint) {
                previousCheckpoint = stage;
            }
        }

        Stage nextStage = currentStage;
        if (previousCheckpoint != null) {
            nextStage = stages[previousCheckpoint.stageIndex + 1];
            Log.d(TAG, "Falling back to previous checkpoint - current stage: " + currentStage.stageIndex +
                    ", previous checkpoint: " + previousCheckpoint.stageIndex +
                    ", next stage: " + nextStage.stageIndex);
            currentStage = nextStage;
        }
        return currentStage;
    }

    public Stage doPassCurrentStage() {
        int nextStageIndex = currentStage.stageIndex + 1;
        if (nextStageIndex < stages.length - 1) {
            Log.d(TAG, "Moving to next stage - current: " + currentStage.stageIndex + ", next: " + nextStageIndex);
            currentStage = stages[nextStageIndex];
        } else {
            Log.d(TAG, "All stages completed");
            // do nothing, user is at the end
        }
        return currentStage;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }
}