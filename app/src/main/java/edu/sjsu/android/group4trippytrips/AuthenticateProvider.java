package edu.sjsu.android.group4trippytrips;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Objects;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class AuthenticateProvider extends ContentProvider {

    AppDB db;

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    @Override
    public boolean onCreate() {
        db = new AppDB(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        byte[] salt = getSalt();
        String saltS = "";
        assert values != null;
        char[] password = values.get("password").toString().toCharArray();
        String hashedPassword = hashPassword(password, salt);
        values.put("password", hashedPassword);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           saltS = Base64.getEncoder().encodeToString(salt);
        }
        values.put("salt", saltS);
        long rowID = db.insertLogin(values);
        // If record is added successfully
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.getAccount(selection);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return db.deleteAccount(selection);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return db.changePassword(values, selection);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static String hashPassword(char[] password, byte[] salt) {
        String result = "";
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSha1");
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            byte[] hash = factory.generateSecret(spec).getEncoded();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                result = Base64.getEncoder().encodeToString(hash);
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
        return result;
    }

    public static byte[] getSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}