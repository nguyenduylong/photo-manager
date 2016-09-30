package com.example.nguyenduylong.photomanager.model;

/**
 * Created by nguyen duy long on 11/16/2015.
 */
public class AlbumImage {
    private String name;
    private String avatarUri;

    public AlbumImage(String name, String avatarUri) {
        this.name = name;
        this.avatarUri = avatarUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    public void setAvatarUri(String avatarUri) {
        this.avatarUri = avatarUri;
    }
}
