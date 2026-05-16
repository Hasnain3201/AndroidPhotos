package com.example.androidphotos03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAlbumAdapter extends RecyclerView.Adapter<RecyclerAlbumAdapter.ViewHolder> {
private List<Album> albumList;
private Context context;
private AlbumInteractionListener interactionListener;
private int selectedAlbumIndex = -1;  // Initialize selected index
private long lastClickTime = 0;


    public interface AlbumInteractionListener {
    void onAlbumRename(Album album);
    void onAlbumDelete(Album album, int index);
    void onAlbumSelected(Album album);
    void onAlbumDoubleClick(Album album);
    void onAlbumOpen(Album album);  // Ensure this method is declared

    }

    public RecyclerAlbumAdapter(Context context, List<Album> albumList, AlbumInteractionListener listener) {
        this.context = context;
        this.albumList = (albumList != null ? albumList : new ArrayList<>());
        this.interactionListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.album_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumName.setText(album.getName());

        holder.openAlbumButton.setOnClickListener(v -> {
            Log.d("OpenAlbum", "Button clicked for album: " + album.getName());
            interactionListener.onAlbumOpen(album);
        });

        holder.deleteButton.setOnClickListener(v -> {
            ((AlbumsList) context).showDeleteDialog(position);  // Correctly initiate deletion dialog
        });

        holder.renameButton.setOnClickListener(v -> {
            interactionListener.onAlbumRename(album);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView albumName;
        Button deleteButton, renameButton, openAlbumButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            albumName = itemView.findViewById(R.id.album_name);
            deleteButton = itemView.findViewById(R.id.deleteAlbum);
            renameButton = itemView.findViewById(R.id.rename_button);
            openAlbumButton = itemView.findViewById(R.id.openAlbum);  // Make sure this ID exists in your layout
        }
    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public int getSelectedIndex() {
        return selectedAlbumIndex;  // Returns the current index of selected album
    }

    public void deleteAlbum(int index) {
        if (index != -1 && index < albumList.size()) {
            albumList.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, albumList.size());
            selectedAlbumIndex = -1;  // Reset selected index after deletion
        }
    }
}