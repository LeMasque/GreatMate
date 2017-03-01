package edu.washington.mchs.greatmate;

import android.app.Application;
import android.util.Log;

public class GreatMateApp extends Application {

    public static final String TAG = "GreatMate";

    private static StateKeeper repo;

    private static GreatMateApp instance;
    public GreatMateApp() {
        this(new Repository()); // default to using the "Repository" implementation
    }

    public GreatMateApp(StateKeeper sk) {
        if (instance == null) {
            // PUT ALL CONSTRUCTOR CODE IN HERE:
            instance = this;
            repo = sk;
        } else {
            Log.e(TAG, "[-] Instance already instantiated");
        }
    }

    public static StateKeeper getRepo() {
        return repo;
    }

    public static GreatMateApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("QuizApp", "onCreate fired");
    }
}
