package edu.sjsu.android.group4trippytrips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AppDB extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "appDatabase";
    protected static final int VERSION = 1;

    // Login Table Constants
    protected static final String LOGIN_TABLE_NAME = "login";
    protected static final String ID = "_id";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    protected static final String SALT = "salt";
    private static final String LOGIN_CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL);", LOGIN_TABLE_NAME, ID, USERNAME, PASSWORD, SALT);

    // Locations Table Constants
    protected static final String LOCATION_TABLE_NAME = "locations";
    protected static final String LOCATIONS_ID = "_id";
    protected static final String LOCATION_NAME = "name";
    protected static final String LOCATION_ADDRESS = "address";
    protected static final String LOCATION_RATING = "rating";
    private static final String LOCATION_CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "FOREIGN KEY(%s) REFERENCES %s(%s));",
                    LOCATION_TABLE_NAME, LOCATIONS_ID, USERNAME, LOCATION_NAME, LOCATION_ADDRESS,LOCATION_RATING,
                    USERNAME, LOGIN_TABLE_NAME, USERNAME);

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

    public Cursor getAccount(String username){
        SQLiteDatabase db = getWritableDatabase();
        String where = "username=?";
        String[] args = new String[] {username};
        return db.query(LOGIN_TABLE_NAME, null, where, args, null, null, null);
    }

    public int deleteAccount(String username){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LOGIN_TABLE_NAME, "username=?", new String[]{username});
    }

    public int changePassword(ContentValues values, String username){
        SQLiteDatabase db = getWritableDatabase();
        return db.update(LOGIN_TABLE_NAME, values, "username=?", new String[]{username});
    }

    public long insertLocation(ContentValues contentValues){
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(LOCATION_TABLE_NAME, null, contentValues);
    }

    public Cursor getLocations(String username){
        String where = "username=?";
        String[] args = new String[] {username};
        SQLiteDatabase db = getWritableDatabase();
        return db.query(LOCATION_TABLE_NAME, null, where, args, null, null, null);
    }

    public int deleteLocation(String username, String name){
        String where = "username=? AND name=?";
        String[] args = new String[] {username, name};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LOCATION_TABLE_NAME, where, args);
    }

    public int deleteAllLocation(String username){
        String where = "username=?";
        String[] args = new String[] {username};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(LOCATION_TABLE_NAME, where, args);
    }

}
