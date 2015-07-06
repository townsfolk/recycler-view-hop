package com.example.hop;

import android.app.Application;
import com.example.hop.model.GameManager;

/**
 * Created by elberry on 7/6/15.
 */
public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GameManager.initialize(getApplicationContext());
    }
}
