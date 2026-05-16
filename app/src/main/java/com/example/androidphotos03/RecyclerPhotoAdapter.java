package com.example.androidphotos03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.androidphotos03.AlbumsActivity;
import com.google.gson.Gson;

public class RecyclerPhotoAdapter extends RecyclerView.Adapter<RecyclerPhotoAdapter.PhotoViewHolder> {

    private final Context context;
    private final List<Photo> photoList;
    private RecyclerView albumsRecyclerView;
    private RecyclerAlbumAdapter adapter;

    public RecyclerPhotoAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    public void addPhoto(Photo photo) {
        if (photoList != null) {
            photoList.add(photo);
            notifyItemInserted(photoList.size() - 1);
        }
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_list_item, parent, false);
        return new PhotoViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        Uri imageUri = photo.getImageUri(); // Make sure getImageUri returns a Uri

        // Load the image into the view
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.photoImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("ImageLoadError", "Unable to load image", e);
            //holder.photoImageView.setImageResource(R.drawable.default_image); // Fallback image
        }

        // Update text views or additional elements here, if any
        // e.g., holder.photoName.setText(photo.getName()); // if you have a text view for name

        // Open Photo Button
        holder.openPhotoButton.setOnClickListener(v -> {
            Toast.makeText(context, "Open Photo: " + photo.getName(), Toast.LENGTH_SHORT).show();
            // Here you can add more complex behavior like opening a full-screen view of the photo
        });

        // Remove Photo Button
        holder.removePhotoButton.setOnClickListener(v -> {
            removePhoto(position);
            Toast.makeText(context, "Photo Removed: " + photo.getName(), Toast.LENGTH_SHORT).show();
        });
    }


    private void removePhoto(int position) {
        if (position >= 0 && position < photoList.size()) {
            photoList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, photoList.size());
        }
    }



    private void handleDelete(int position) {
        if (position >= 0 && position < photoList.size()) {
            photoList.remove(position);  // Remove the item from the data list
            notifyItemRemoved(position);  // Notify the adapter that an item has been removed
            notifyItemRangeChanged(position, photoList.size());  // Update the position of remaining items
        }
    }




    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        Button deleteButton;  // Declaration of the delete button
        Button openPhotoButton, removePhotoButton;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.thumbnail_image);
            openPhotoButton = itemView.findViewById(R.id.open_photo);
            removePhotoButton = itemView.findViewById(R.id.remove_photo);
        }


        void bind(Uri imageUri) {
            try {
                InputStream inputStream = itemView.getContext().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                photoImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("ImageLoadError", "Image file not found", e);
                //photoImageView.setImageResource(R.drawable.default_image); // Set a default or error image if needed
            }
        }


        public class AlbumsActivity extends AppCompatActivity implements RecyclerAlbumAdapter.AlbumInteractionListener {

            private RecyclerView albumsRecyclerView;
            private List<Album> albums;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.home); // Assuming 'home.xml' is your layout file

                albumsRecyclerView = findViewById(R.id.albums_recycler_view);
                albumsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Suppose you have a method to load your albums
                albums = loadAlbums();

                // Create an instance of your adapter and pass 'this' as the listener
                RecyclerAlbumAdapter adapter = new RecyclerAlbumAdapter(this, albums, this);
                albumsRecyclerView.setAdapter(adapter);
            }

            // Implementation for renaming an album
            @Override
            public void onAlbumRename(Album album) {
                // Open a dialog to input a new name or start an Activity with a form
                // For example, you could use an AlertDialog here with a custom layout
                // containing an EditText to enter the new album name.
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

            // Implementation for when an album is selected
            @Override
            public void onAlbumSelected(Album album) {
                // You could start a new activity showing the details of the selected album.
                // Pass album details to the detail activity using an Intent.
                Intent intent = new Intent(this, AlbumsActivity.class);
                intent.putExtra("album", album); // Make sure Album class implements Serializable or Parcelable
                startActivity(intent);
            }

            // Implementation for when an album is deleted
            @Override
            public void onAlbumDelete(Album album, int index) {
                if (index >= 0 && index < albums.size()) {
                    albums.remove(index);
                    RecyclerAlbumAdapter adapter = (RecyclerAlbumAdapter) albumsRecyclerView.getAdapter();
                    if (adapter != null) {
                        adapter.notifyItemRemoved(index);
                        adapter.notifyItemRangeChanged(index, albums.size());
                    }
                }
            }

            private List<Album> loadAlbums() {
                // Your code to load albums from a database, shared preferences, or wherever they are stored
                return SharedPreferencesUtil.loadAlbumData(this);
            }

            @Override
            public void onAlbumDoubleClick(Album album) {
                // Intent to start AlbumView activity
                Intent intent = new Intent(this, AlbumView.class);
                intent.putExtra("album", album);  // Passing the album as a Parcelable extra
                startActivity(intent);  // Start the AlbumView activity
            }

            private void displayAlbumContents(Album album) {
                TextView toolbarAlbumText = findViewById(R.id.toolbarAlbumText);
                toolbarAlbumText.setText(album.getName());

                RecyclerView albumPhotosRecyclerView = findViewById(R.id.album_photos_recycler_view);
                albumPhotosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                RecyclerPhotoAdapter photoAdapter = new RecyclerPhotoAdapter(this, album.getPhotos());
                albumPhotosRecyclerView.setAdapter(photoAdapter);
            }

        }
    }}

