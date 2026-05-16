package com.example.androidphotos03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AlbumsActivity extends AppCompatActivity implements RecyclerAlbumAdapter.AlbumInteractionListener {
    private RecyclerView recyclerView;
    private RecyclerAlbumAdapter adapter;
    private List<Album> albums; // List to store albums

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Ensure this is the correct layout file
        DataHolder.getInstance().loadAlbumsFromPreferences(getApplicationContext());
        albums = DataHolder.getInstance().getAlbums();
        FloatingActionButton deleteButton = findViewById(R.id.deleteAlbum);
        recyclerView = findViewById(R.id.albums_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Assuming there is a method to load your albums
        adapter = new RecyclerAlbumAdapter(this, albums, this);
        recyclerView.setAdapter(adapter);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adapter.getSelectedIndex() != -1) {
                    adapter.deleteAlbum(adapter.getSelectedIndex());
                }
            }
        });
    }

    private List<Album> loadAlbums() {
        // Load your albums here
        return SharedPreferencesUtil.loadAlbumData(this);
    }

    // Implement the interface methods
    @Override
    public void onAlbumDelete(Album album, int index) {
        if (index >= 0 && index < albums.size()) {
            albums.remove(index);
            adapter.notifyItemRemoved(index);
            adapter.notifyItemRangeChanged(index, albums.size());
            Toast.makeText(this, "Album deleted: " + album.getName(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onAlbumRename(Album album) {
        // Handle album renaming
    }

    @Override
    public void onAlbumSelected(Album album) {
        // Handle album selection
    }

    @Override
    public void onAlbumDoubleClick(Album album) {
        Intent intent = new Intent(this, AlbumView.class);
        intent.putExtra("album", album);
        startActivity(intent);
    }

    @Override
    public void onAlbumOpen(Album album) {

        // Reload albums to ensure the list is up-to-date
        List<Album> allAlbums = loadAlbums(); // This should call SharedPreferencesUtil.loadAlbumData(this)
        Log.d("AlbumDebug", "All Albums before opening: " + new Gson().toJson(allAlbums));
        logAllAlbums();
        // Find the album again in case the list has been updated
        album = findAlbumById(album != null ? album.getId() : -1, allAlbums);

        logAllAlbums();


        if (album != null) {
            Log.d("AlbumOpen", "Opening album: " + album.getName());
            Intent intent = new Intent(this, AlbumView.class);
            intent.putExtra("album_id", album.getId());
            startActivity(intent);
        } else {
            Log.e("AlbumOpenError", "Album is null or not found");
        }
    }

    private Album findAlbumById(int albumId, List<Album> albums) {
        for (Album alb : albums) {
            if (alb.getId() == albumId) {
                return alb;
            }
        }
        return null;
    }


    private void logAllAlbums() {
        List<Album> allAlbums = DataHolder.getInstance().getAlbums(); // Make sure this method correctly fetches all albums
        Gson gson = new Gson();
        String albumsJson = gson.toJson(allAlbums);
        Log.d("AlbumDebug", "All Albums: " + albumsJson);
    }




}
