package com.example.hop.model;

/**
 * Created by elberry on 6/4/15.
 */

public class Stage {

    public static enum Difficulty {
        easy("Easy", '1', '4'), medium("Med", '2', '5'), hard("Hard", '3', '6');
        final String string;
        final char level;
        final char checkpoint;


        Difficulty(String string, char level, char checkpoint) {
            this.string = string;
            this.level = level;
            this.checkpoint = checkpoint;
        }
    }

    public boolean checkpoint;
    public Difficulty difficulty;
    public int displayIndex;
    public int stageIndex;
}