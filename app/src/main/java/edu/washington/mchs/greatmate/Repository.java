package edu.washington.mchs.greatmate;


import android.util.Log;

public class Repository implements StateKeeper {

    private GreatMateApp app = null; // override in constructor

    public Repository() {} // empty constructor; TODO: consider need for other constructors
    public Repository(GreatMateApp app) {
        this.app = app;
    }

    @Override
    public boolean isLoggedIn() {
        return false;
    }

    @Override
    public House getCurrentHouse() {
        return null;
    }

    @Override
    public User getUserData() {
        return null;
    }

    @Override
    public GreatMateApp getConnectedApp() {
        return app;
    }

    @Override
    public House setCurrentHouse(Structure newStructure) {
        // on failure, return null; on success return the original object
        return null;
    }

    @Override
    public User setUserData(Being being) {
        // on failure, return null; on success return the original object
        return null;
    }
}
