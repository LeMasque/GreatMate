package edu.washington.mchs.greatmate;

import android.app.Application;
import android.util.Log;

public class GreatMateApp extends Application {
    private Repository instance = new Repository();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("QuizApp", "onCreate fired");
    }

    public Repository getRepository() {
        return instance;
    }
}
