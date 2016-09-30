package com.example.nguyenduylong.photomanager.model;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by nguyen duy long on 10/25/2015.
 */
public class ImageInfo {
    private String name;
    private String path;
    private int size;
    private String album;
    private int width,height;
    private long dateAdded;
    public ImageInfo(){
        name = "";
        path = "";
        album = "";
        width=0;
        height=0;
    }

    public ImageInfo(String mName, String mUri, int mSize, String mAlbum) {
        super();
        this.setName(mName);
        this.album = mAlbum;
    }
    public String getDimentionDisplay(){
        return width+"x"+height;
    }

    public String getDateAddedDisplay(){
        Date date=new Date(dateAdded*1000);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateText = df2.format(date);
        return dateText;
    }

    public String getSizeDisplay(){
        DecimalFormat df = new DecimalFormat("###.##");
        String sizeDisplay = "";

        int mSize = size;

        if(mSize>=1024)
        {
            mSize/=1024;
            if(mSize>=1024)
            {
                mSize/=1024;
                if (mSize>=1024)
                {
                    mSize/=1024;
                    sizeDisplay = df.format(mSize)+"GB";
                }
                else {
                    sizeDisplay =df.format(mSize)+"MB";
                }
            }
            else {
                sizeDisplay = df.format(mSize)+"KB";
            }
        }
        else {
            sizeDisplay = mSize+"B";
        }

        return sizeDisplay;
    }

    public String getType(){
        return "image/"+path.substring(path.lastIndexOf(".")+1);
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void deleteFile(Activity context){
        File selectedFile = new File(path);
        boolean deleted = false;
        if(selectedFile.exists())
        {
            deleted =  selectedFile.delete();
        }
        if (deleted){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(selectedFile);
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
            } else {
                context.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }
        }

    }

    public void shareImage(Activity context){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri imageUri = Uri.parse(path);
        sharingIntent.setType("image/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        context.startActivity(sharingIntent);
    }

}
