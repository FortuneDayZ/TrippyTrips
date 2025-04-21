package edu.sjsu.android.group4trippytrips;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LoginDB extends SQLiteOpenHelper {

    protected static final String DATABASE_NAME = "loginDatabase";
    protected static final int VERSION = 1;
    protected static final String TABLE_NAME = "login";
    protected static final String ID = "_id";
    protected static final String EMAIL = "email";
    protected static final String USERNAME = "username";
    protected static final String PASSWORD = "password";
    private static final String CREATE_TABLE =
            String.format("CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL, " +
                    "%s STRING NOT NULL);", TABLE_NAME, ID, EMAIL, USERNAME, PASSWORD);

    public LoginDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insert(ContentValues contentValues) {
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAccount(String username, String password){
        String where = String.format("username=%s and password=%s", username, password);
        SQLiteDatabase db = getWritableDatabase();
        return db.query(TABLE_NAME, null, where, null, null, null, null);
    }

    public int deleteAccount(String username){
        String where = String.format("username=%s", username);
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, where, null);
    }
}
