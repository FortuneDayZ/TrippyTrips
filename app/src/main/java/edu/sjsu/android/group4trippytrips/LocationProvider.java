package edu.sjsu.android.group4trippytrips;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LocationProvider extends ContentProvider {
    AppDB db;

    @Override
    public boolean onCreate() {
        db = new AppDB(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        long rowID = db.insertLocation(values);
        if (rowID > 0){
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.getLocations(selection);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
//        assert selectionArgs != null;
//        return db.deleteLocation(selection, selectionArgs[0]);
        if (selectionArgs == null){
            return db.deleteAllLocation(selection);
        }
        return db.deleteLocation(selection, selectionArgs[0]);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}