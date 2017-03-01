package edu.washington.mchs.greatmate;

/**
 * Created by masq on 3/1/17.
 */

public interface StateKeeper {
    public GreatMateApp getConnectedApp(); // TODO: determine if this is nice/needed
    public Being getUserData(); // returns the User object currently logged in
    public Being setUserData(Being being); // sets the current User associated with the app
    public Structure getCurrentHouse(); // returns the House object currently associated with the user
    public Structure setCurrentHouse(Structure newStructure); // sets the house currently associated with the user to the passed one
    public boolean isLoggedIn(); // returns whether the app is currently has a User logged in
}
