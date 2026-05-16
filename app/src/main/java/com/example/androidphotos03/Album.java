package com.example.androidphotos03;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import android.util.Log;

public class Album implements Parcelable {
    private static AtomicInteger nextId;

    static {
        nextId = new AtomicInteger(SharedPreferencesUtil.loadNextId(MyApplication.getContext()));  // Assume MyApplication holds context
    }
    private int id;
    private String name;
    private List<Photo> photos;

    public Album(String name) {
        this.id = nextId.getAndIncrement();
        SharedPreferencesUtil.saveNextId(MyApplication.getContext(), nextId.get());
        this.name = name;
        this.photos = new ArrayList<>();
        Log.d("AlbumDebug", "Created Album: ID=" + id + ", Name=" + name);

    }

    protected Album(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photos = new ArrayList<>();
        in.readTypedList(photos, Photo.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(photos);
    }
}
