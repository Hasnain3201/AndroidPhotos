package com.example.androidphotos03;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Parcelable;
import android.widget.Toast;


public class AlbumsList extends AppCompatActivity implements RecyclerAlbumAdapter.AlbumInteractionListener {

    private RecyclerView albumsRecyclerView;
    private RecyclerAlbumAdapter adapter;
    private List<Album> albums;  // List of albums



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home); // Ensure 'home.xml' is your layout file

        DataHolder.getInstance().loadAlbumsFromPreferences(getApplicationContext());
        albums = DataHolder.getInstance().getAlbums();
        Log.d("DebugHome", "Loaded albums count: " + (albums != null ? albums.size() : 0));  // Debug statement

        albumsRecyclerView = findViewById(R.id.albums_recycler_view);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAlbumAdapter(this, albums, this);
        albumsRecyclerView.setAdapter(adapter);

        FloatingActionButton createAlbumButton = findViewById(R.id.createAlbum);
        createAlbumButton.setOnClickListener(v -> {
            createNewAlbum();
            Log.d("DebugHome", "Create album button clicked");  // Debug statement
        });

        FloatingActionButton deleteAlbumButton = findViewById(R.id.deleteAlbum);
        deleteAlbumButton.setOnClickListener(v -> {
            if (!albums.isEmpty()) {
                showDeleteDialog(0); // Assuming to delete the first album for demonstration
                Log.d("DebugHome", "Delete album button clicked");  // Debug statement
            } else {
                Log.d("DebugHome", "No albums to delete");  // Debug statement when no albums exist
            }
        });
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);  // Make sure 'home' is your layout file

        // Setting up the RecyclerView for displaying albums
        albumsRecyclerView = findViewById(R.id.albums_recycler_view);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load albums from SharedPreferences or another persistent storage
        albums = SharedPreferencesUtil.loadAlbumData(this);
        if (albums == null) {
            albums = new ArrayList<>();  // Create a new list if none exists
        }

        adapter = new RecyclerAlbumAdapter(this, albums, this);  // Create an instance of the adapter
        albumsRecyclerView.setAdapter(adapter);

        FloatingActionButton createAlbumButton = findViewById(R.id.createAlbum);
        createAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAlbum();
            }
        });

        FloatingActionButton deleteAlbumButton = findViewById(R.id.deleteAlbum);
        deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);  // Make sure 'home' is your layout file

        // Setting up the RecyclerView for displaying albums
        albumsRecyclerView = findViewById(R.id.albums_recycler_view);
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load albums from SharedPreferences or another persistent storage
        albums = SharedPreferencesUtil.loadAlbumData(this);
        if (albums == null) {
            albums = new ArrayList<>();  // Create a new list if none exists
        }

        adapter = new RecyclerAlbumAdapter(this, albums, this);  // Create an instance of the adapter
        albumsRecyclerView.setAdapter(adapter);

        FloatingActionButton createAlbumButton = findViewById(R.id.createAlbum);
        createAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAlbum();
            }
        });

        // Remove or disable the deleteAlbumButton if it's not tied to a specific album
        FloatingActionButton deleteAlbumButton = findViewById(R.id.deleteAlbum);
        deleteAlbumButton.setVisibility(View.GONE); // or remove this button if not used
    }*/

    private void createNewAlbum() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter New Album Name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String albumName = input.getText().toString().trim();
            if (!albumName.isEmpty()) {
                Album newAlbum = new Album(albumName);
                DataHolder.getInstance().addAlbum(newAlbum);
                DataHolder.getInstance().saveAlbumsToPreferences(getApplicationContext());
                adapter.notifyItemInserted(albums.size() - 1);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    @Override
    public void onAlbumSelected(Album album) {
        Intent intent = new Intent(this, AlbumView.class);
        intent.putExtra("album", album);
        startActivity(intent);
    }


    @Override
    public void onAlbumDelete(Album album, int index) {
        if (index != -1 && index < albums.size()) {
            albums.remove(index);
            adapter.notifyItemRemoved(index);
            adapter.notifyItemRangeChanged(index, albums.size());
            SharedPreferencesUtil.saveAlbumData(this, albums);
        } else {
            Toast.makeText(this, "Invalid album or index", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlbumRename(Album album) {
        showRenameDialog(album);
    }
    private void showRenameDialog(Album album) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rename Album");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(album.getName());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty() && !newName.equals(album.getName())) {
                album.setName(newName);
                adapter.notifyItemChanged(albums.indexOf(album));
                SharedPreferencesUtil.saveAlbumData(this, albums);
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


    public void showDeleteDialog(int albumIndex) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Are you sure you want to delete this album?");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            deleteSelectedAlbum(albumIndex);  // Pass the index directly for deletion
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteSelectedAlbum(int albumIndex) {
        if (albumIndex != -1 && albumIndex < albums.size()) {
            albums.remove(albumIndex);
            adapter.notifyItemRemoved(albumIndex);
            adapter.notifyItemRangeChanged(albumIndex, albums.size());
            SharedPreferencesUtil.saveAlbumData(this, albums);
        } else {
            Toast.makeText(this, "Invalid album index", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAlbumDoubleClick(Album album) {
        Intent intent = new Intent(this, AlbumView.class);
        intent.putExtra("album", album);  // Make sure Album class implements Parcelable
        startActivity(intent);
    }
    private List<Album> loadAlbums() {
        // Load your albums here
        return SharedPreferencesUtil.loadAlbumData(this);
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
