package com.example.androidphotos03;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import android.util.Log;

public class SharedPreferencesUtil {

    private static final String PREFS_NAME = "photoAlbumPrefs";
    private static final String ALBUMS_KEY = "albums";

    private static final String NEXT_ID_KEY = "nextId";

    public static void saveNextId(Context context, int nextId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(NEXT_ID_KEY, nextId);
        editor.apply();
        Log.d("SharedPreferencesUtil", "Saved nextId: " + nextId);
    }

    public static int loadNextId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int nextId = prefs.getInt(NEXT_ID_KEY, 1);  // Default to 1 if not found
        Log.d("SharedPreferencesUtil", "Loaded nextId: " + nextId);
        return nextId;
    }

    public static void saveAlbumData(Context context, List<Album> albums) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(albums);
        editor.putString(ALBUMS_KEY, json);
        editor.apply();
        Log.d("SharedPreferencesUtil", "Saved Albums: " + json);
    }

    public static List<Album> loadAlbumData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(ALBUMS_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Album>>() {}.getType();
        List<Album> albums = gson.fromJson(json, type);
        Log.d("SharedPreferencesUtil", "Loaded Albums: " + (json == null ? "null" : json));
        return albums;
    }




}