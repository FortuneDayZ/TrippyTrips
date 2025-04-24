package edu.sjsu.android.group4trippytrips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppDB extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "loginDatabase";
    protected static final int VERSION = 1;

    // Login Table Constants
    protected static final String LOGIN_TABLE_NAME = "login";
    protected static final String ID = "_id";
    protected static final String EMAIL = "email";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    private static final String LOGIN_CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL);", LOGIN_TABLE_NAME, ID, EMAIL, USERNAME, PASSWORD);

    // Locations Table Constants
    protected static final String LOCATION_TABLE_NAME = "locations";
    protected static final String LOCATIONS_ID = "_id";
    protected static final String LOCATION_NAME = "name";
    private static final String LOCATION_CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "FOREIGN KEY(%s) REFERENCES %s(%s));",
                    LOCATION_TABLE_NAME, LOCATIONS_ID, USERNAME, LOCATION_NAME,
                    USERNAME, LOGIN_CREATE_TABLE, USERNAME);

    public AppDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LOGIN_CREATE_TABLE);
        db.execSQL(LOCATION_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LOGIN_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATION_TABLE_NAME);
        onCreate(db);
    }

    public long insertLogin(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(LOGIN_TABLE_NAME, null, contentValues);
    }

    public Cursor getAccount(String username, String password){
        String where = String.format("username=%s and password=%s", username, password);
        SQLiteDatabase db = getWritableDatabase();
        return db.query(LOGIN_TABLE_NAME, null, where, null, null, null, null);
    }

    public int deleteAccount(String username){
        String where = String.format("username=%s", username);
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LOGIN_TABLE_NAME, where, null);
    }

    public long insertLocation(ContentValues contentValues){
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(LOGIN_TABLE_NAME, null, contentValues);
    }

    public Cursor getLocations(String username){
        String where = String.format("username=%s", username);
        SQLiteDatabase db = getWritableDatabase();
        return db.query(LOCATION_TABLE_NAME, null, where, null, null, null, null);
    }

    public int deleteLocation(String username, String name){
        String where = String.format("username=%s and name=%s", username, name);
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LOCATION_TABLE_NAME, where, null);
    }

}
