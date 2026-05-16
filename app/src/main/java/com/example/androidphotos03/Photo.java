package com.example.androidphotos03;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Photo implements Parcelable {
    private String name;
    private String path;
    private long lastModified;
    private List<Tag> tags;
    private String caption;
    private String imageUri;

    // Constructor that accepts a Uri and converts it to a String

    // Constructor that accepts a Uri and converts it to a String
    public Photo(Uri imageUri) {
        this.imageUri = imageUri.toString();  // Convert Uri to String
        this.name = "Photo";
        this.tags = new ArrayList<>();
    }

    // Get the Uri as a Uri object
    public Uri getImageUri() {
        return Uri.parse(imageUri);  // Convert String back to Uri
    }

    // Set the Uri using a Uri object
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri.toString();  // Store as String
    }


    // Assuming Tag is also Parcelable.
    public Photo(String name, String path, long lastModified) {
        this.name = name;
        this.path = path;
        this.lastModified = lastModified;
        this.tags = new ArrayList<>();
    }

    protected Photo(Parcel in) {
        name = in.readString();
        path = in.readString();
        lastModified = in.readLong();
        caption = in.readString();
        tags = new ArrayList<>();
        in.readTypedList(tags, Tag.CREATOR);
        imageUri = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public long getLastModified() {
        return lastModified;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public String getCaption() {
        return caption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(path);
        dest.writeLong(lastModified);
        dest.writeString(caption);
        dest.writeTypedList(tags);
        dest.writeString(imageUri);
    }

}
