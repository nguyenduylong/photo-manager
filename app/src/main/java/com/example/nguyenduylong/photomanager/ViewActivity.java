package com.example.nguyenduylong.photomanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.FloatMath;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.nguyenduylong.photomanager.model.ImageInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by nguyen duy long on 4/3/2016.
 */
public class ViewActivity extends AppCompatActivity{

    private float[] matrixValues = new float[9];
    ArrayList<ImageInfo> listImage;
    SubsamplingScaleImageView imageView;
    ImageView shareButt, deleteButt;
    ImageView previousButt , nextButt;
    ImageView rotateLeftButt , rotateRightButt;
    ImageView cameraButt , infoButt,exitButt;
    ImageView fullscreenButt;
    int currentPosition =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent i = getIntent();
        String albumName = i.getExtras().getString(AlbumActivity.ALBUM_NAME);
        currentPosition = i.getIntExtra(ImageActivity.IMAGE_POSITION,0);
        listImage = new ArrayList<ImageInfo>();
        listImage = FileType.getImageList(ViewActivity.this,albumName);
        imageView = (SubsamplingScaleImageView)findViewById(R.id.view_image_edit);
        shareButt = (ImageView)findViewById(R.id.share_butt);
        deleteButt  = (ImageView)findViewById(R.id.delete_butt);
        previousButt = (ImageView)findViewById(R.id.previous_butt);
        nextButt = (ImageView)findViewById(R.id.next_butt);
        rotateLeftButt = (ImageView)findViewById(R.id.rotate_left_butt);
        rotateRightButt = (ImageView)findViewById(R.id.rotate_right_butt);
        cameraButt = (ImageView)findViewById(R.id.camera_butt);
        infoButt = (ImageView)findViewById(R.id.info_butt);
        exitButt = (ImageView)findViewById(R.id.exit_butt);
        fullscreenButt = (ImageView)findViewById(R.id.full_screen_butt);

        imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
        shareButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listImage.get(currentPosition).shareImage(ViewActivity.this);
            }
        });
        deleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewActivity.this)
                        .setTitle(getString(R.string.dialog_delete_title))
                        .setMessage(getString(R.string.dialog_delete))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listImage.get(currentPosition).deleteFile(ViewActivity.this);
                                if (currentPosition==0){
                                    currentPosition +=1;
                                }else {
                                    currentPosition-=1;
                                }
                                imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        previousButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == 0){
                    Toast.makeText(ViewActivity.this,"ca'nt previous",Toast.LENGTH_SHORT).show();
                }else {
                    currentPosition-=1;
                    imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
                }
            }
        });
        nextButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == (listImage.size()-1)){
                    Toast.makeText(ViewActivity.this,"can't next",Toast.LENGTH_SHORT).show();
                }else {
                    currentPosition+=1;
                    imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
                }
            }
        });
        rotateRightButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage(listImage.get(currentPosition),90);
                imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
            }
        });
        rotateLeftButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateImage(listImage.get(currentPosition),-90);
                imageView.setImage(ImageSource.uri(listImage.get(currentPosition).getPath()));
            }
        });
        cameraButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(takePictureIntent);
            }
        });
        infoButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ViewActivity.this);
                dialog.setContentView(R.layout.dialog_info);
                dialog.setTitle("Chi tiết");
                // set the custom dialog components - text, image and button
                TextView filePath = (TextView) dialog.findViewById(R.id.text_path_file);
                TextView fileSize = (TextView) dialog.findViewById(R.id.text_size);
                TextView fileDimen = (TextView) dialog.findViewById(R.id.text_dimen);
                TextView fileDate = (TextView) dialog.findViewById(R.id.text_date_added);
                TextView fileType = (TextView) dialog.findViewById(R.id.text_type);
                filePath.setText(listImage.get(currentPosition).getPath());
                fileSize.setText(listImage.get(currentPosition).getSizeDisplay());
                fileDimen.setText(listImage.get(currentPosition).getDimentionDisplay());
                fileDate.setText(listImage.get(currentPosition).getDateAddedDisplay());
                fileType.setText(listImage.get(currentPosition).getType());
                // if button is clicked, close the custom dialog
                TextView dialogButton = (TextView) dialog.findViewById(R.id.ok_butt);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        exitButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fullscreenButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullscreenIntent = new Intent(ViewActivity.this,FullScreenActivity.class);
                fullscreenIntent.putExtra(FullScreenActivity.FILE_PATH,listImage.get(currentPosition).getPath());
                startActivity(fullscreenIntent);
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
    public void rotateImage(ImageInfo tempphoto,int degree){
        String photopath = tempphoto.getPath().toString();
        Bitmap bmp = BitmapFactory.decodeFile(photopath);

        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(tempphoto.getPath());
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
