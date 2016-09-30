package com.example.nguyenduylong.photomanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;

/**
 * Created by nguyen duy long on 4/18/2016.
 */
public class FullScreenActivity extends AppCompatActivity {
    public static final String FILE_PATH = "file_path";
    String pathFile ;
    SubsamplingScaleImageView imageView;
    TextView closeButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        imageView = (SubsamplingScaleImageView)findViewById(R.id.fullscreen_image);
        closeButt = (TextView)findViewById(R.id.close_butt);
        pathFile = getIntent().getStringExtra(FILE_PATH);
        if (pathFile!=null){
            imageView.setImage(ImageSource.uri(pathFile));
        }
        closeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
