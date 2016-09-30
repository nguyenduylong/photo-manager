package com.example.nguyenduylong.photomanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;


import com.example.nguyenduylong.photomanager.model.ImageInfo;

import java.util.ArrayList;

/**
 * Created by Phu-Tran on 29/01/2016.
 */
public class ImageActivity extends AppCompatActivity {

    private GridView gridView;
    private ImageAdapter imageAdapter;
    private  String albumName;
    ArrayList<ImageInfo> listImage;
    Toolbar toolBar;
    public static final String IMAGE_POSITION ="position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent i = getIntent();
        albumName = i.getExtras().getString(AlbumActivity.ALBUM_NAME);
        toolBar = (Toolbar) findViewById(R.id.charge_tool_bar);
        ImageActivity.this.setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(albumName);
        gridView = (GridView) findViewById(R.id.list_image);
        listImage = new ArrayList<ImageInfo>();
        listImage = FileType.getImageList(ImageActivity.this, albumName);
        imageAdapter = new ImageAdapter(ImageActivity.this, R.layout.image_item, listImage);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ImageActivity.this, ViewActivity.class);
                intent.putExtra(AlbumActivity.ALBUM_NAME, albumName);
                intent.putExtra(IMAGE_POSITION, position);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listImage = FileType.getImageList(ImageActivity.this, albumName);
        imageAdapter = new ImageAdapter(ImageActivity.this, R.layout.image_item, listImage);
        gridView.setAdapter(imageAdapter);
    }
}



