package com.example.nguyenduylong.photomanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;

import com.example.nguyenduylong.photomanager.model.AlbumImage;
import com.example.nguyenduylong.photomanager.model.ImageInfo;

import java.util.ArrayList;


/**
 * Created by nguyen duy long on 10/26/2015.
 */
public class FileType {
    public static ArrayList<ImageInfo> getImageList(Activity context,String albumName) {
        // trả về list image
        ArrayList<ImageInfo> imagesList = new ArrayList<ImageInfo>();
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT
        };
        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?";
        final String[] selectionArgs = { albumName };
        Cursor imageCursor =  context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, "date_added desc");
        while (imageCursor.moveToNext()) {
            Cursor cursor = MediaStore.Images.Thumbnails.queryMiniThumbnail(
                    context.getContentResolver(),imageCursor.getInt(0),
                    MediaStore.Images.Thumbnails.MINI_KIND,
                    null );
            ImageInfo info = new ImageInfo();
            info.setPath(imageCursor.getString(1));
            info.setName(imageCursor.getString(2));
            info.setSize(imageCursor.getInt(3));
            info.setAlbum(imageCursor.getString(4));
            info.setWidth(imageCursor.getInt(6));
            info.setHeight(imageCursor.getInt(7));
            info.setDateAdded(imageCursor.getLong(5));
            imagesList.add(info);
            }
            imageCursor.close();
        return imagesList;
    }
    public static ArrayList<AlbumImage> getAlbumList(Activity context) {
        // trả về list image
        ArrayList<String> albumName = new ArrayList<String>();
        ArrayList<AlbumImage> albumList = new ArrayList<AlbumImage>();
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String selection = MediaStore.Images.Media._ID + "!=0";
        Cursor imageCursor =  context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, null, "date_added desc");
        while (imageCursor.moveToNext()) {
            if (!(albumName.contains(imageCursor.getString(4)))){
                AlbumImage album = new AlbumImage(imageCursor.getString(4),imageCursor.getString(1));
                albumList.add(album);
                albumName.add(imageCursor.getString(4));
            }
        }
        imageCursor.close();
        return albumList;
    }
}
