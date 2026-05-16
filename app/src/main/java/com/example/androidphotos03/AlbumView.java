package com.example.androidphotos03;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;


public class AlbumView extends AppCompatActivity {
    private RecyclerView photosRecyclerView;
    private RecyclerPhotoAdapter photoAdapter;
    private List<Photo> photosList;
    private static final int REQUEST_CODE_PICK_PHOTO = 1;
    private static final int REQUEST_CODE_PERMISSIONS = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_view);
        DataHolder.getInstance().loadAlbumsFromPreferences(getApplicationContext());
        photosList = new ArrayList<>();
        photosRecyclerView = findViewById(R.id.album_photos_recycler_view);
        photosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoAdapter = new RecyclerPhotoAdapter(this, photosList);
        photosRecyclerView.setAdapter(photoAdapter);

        int albumId = getIntent().getIntExtra("album_id", -1);
        Log.d("DebugAlbumView", "Received album ID: " + albumId);  // Debug statement

        if (albumId != -1) {
            Album album = DataHolder.getInstance().findAlbumById(albumId);
            if (album != null) {
                photosList.addAll(album.getPhotos());
                photoAdapter.notifyDataSetChanged();
                Log.d("DebugAlbumView", "Album found: " + album.getName() + " with photos count: " + album.getPhotos().size());  // Debug statement
            } else {
                Toast.makeText(this, "Album not found", Toast.LENGTH_SHORT).show();
                Log.e("DebugAlbumView", "Album not found with ID: " + albumId);  // Error log
                finish();
            }
        }

        FloatingActionButton uploadPhotoButton = findViewById(R.id.upload_photo);
        uploadPhotoButton.setOnClickListener(v -> checkPermissionsAndOpenGallery());
    }


    private void checkPermissionsAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermission(Manifest.permission.READ_MEDIA_IMAGES, REQUEST_CODE_PERMISSIONS);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE_PERMISSIONS);
            }
        }
    }
    private void requestPermission(String permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("This permission is needed to access your photos for uploading.")
                    .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(AlbumView.this,
                            new String[]{permission},
                            requestCode))
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();

            // Create a new Photo object and add it to your list using the updated constructor
            Photo newPhoto = new Photo(selectedImage);
            photosList.add(newPhoto);

            // Notify the adapter of the change
            photoAdapter.notifyItemInserted(photosList.size() - 1);
        }
    }





    private void deletePhoto() {
        Toast.makeText(this, "Delete Photo Clicked", Toast.LENGTH_SHORT).show();
    }
}



