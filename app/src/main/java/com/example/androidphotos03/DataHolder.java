package com.example.androidphotos03;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.content.Context;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();
    private List<Album> albums = new ArrayList<>();

    private DataHolder() {
        albums = new ArrayList<>();  // Ensure initial non-null list
    }

    public static DataHolder getInstance() {
        return instance;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = new ArrayList<>(albums);
        Log.d("DataHolder", "Albums set in DataHolder: " + albums.size());
    }

    public Album findAlbumById(int id) {
        for (Album album : albums) {
            if (album.getId() == id) {
                Log.d("DataHolder", "Found Album with ID: " + id + ", Name: " + album.getName());
                return album;
            }
        }
        Log.d("DataHolder", "No Album found with ID: " + id);
        return null;
    }

    public void addAlbum(Album album) {
        this.albums.add(album);
        Log.d("DataHolder", "Album added: " + album.getName() + " with ID: " + album.getId());
    }

    public void loadAlbumsFromPreferences(Context context) {
        List<Album> loadedAlbums = SharedPreferencesUtil.loadAlbumData(context);
        setAlbums(loadedAlbums);
        Log.d("DataHolder", "Albums loaded from SharedPreferences and set to DataHolder");
    }

    public void saveAlbumsToPreferences(Context context) {
        SharedPreferencesUtil.saveAlbumData(context, getAlbums());
        Log.d("DataHolder", "Albums from DataHolder saved to SharedPreferences");
    }
}
