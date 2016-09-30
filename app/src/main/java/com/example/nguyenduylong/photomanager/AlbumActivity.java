package com.example.nguyenduylong.photomanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import com.example.nguyenduylong.photomanager.model.AlbumImage;

import java.util.ArrayList;

/**
 * Created by nguyen duy long on 3/10/2016.
 */
public class AlbumActivity extends AppCompatActivity {
    public static final String ALBUM_NAME = "albumName";
    ArrayList<AlbumImage> listAlbum = new ArrayList<AlbumImage>();
    Toolbar toolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        listAlbum = FileType.getAlbumList(AlbumActivity.this);
        toolBar = (Toolbar) findViewById(R.id.charge_tool_bar);
        toolBar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        AlbumActivity.this.setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        GridView gridView = (GridView) findViewById(R.id.list_image);
        AlbumAdapter adapter = new AlbumAdapter(AlbumActivity.this, R.layout.album_item, listAlbum);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AlbumActivity.this, ImageActivity.class);
                i.putExtra(ALBUM_NAME,listAlbum.get(position).getName());
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }




}
