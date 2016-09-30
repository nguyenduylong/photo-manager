package com.example.nguyenduylong.photomanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.nguyenduylong.photomanager.model.ImageInfo;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nguyen duy long on 10/25/2015.
 */
public class ImageAdapter extends BaseAdapter {
    Activity context=null;
    ArrayList<ImageInfo> myArray=null;
    int layoutId;
    ImageHolder holder;
    ArrayList<ImageHolder> holderList;
    public ImageAdapter(Activity context, int layoutId, ArrayList<ImageInfo> arr){
        holderList = new ArrayList<ImageHolder>();
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
        for(int i=0;i<myArray.size();i++)
        {
            holderList.add(new ImageHolder());
        }
    }

    @Override
    public int getCount() {
        return myArray.size();
    }

    @Override
    public ImageInfo getItem(int position) {
        return myArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ImageHolder getHolder(int position)
    {
        return holderList.get(position);
    }

    public void remove(int position) {
        myArray.remove(position);
        holderList.remove(position);
    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {
        View view = convertView;
        if (view ==null){
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutId, null);
            holder = new ImageHolder(view);
            view.setTag(holder);
        }else {
            holder =(ImageHolder) view.getTag();
        }
        holder.setData(position);
        holderList.set(position,holder);
        return view;
    }
    public class ImageHolder {
        private ImageView imageViewItem;
        public ImageHolder()
        {

        }
        public ImageHolder(View convertView){
            imageViewItem = (ImageView) convertView.findViewById(R.id.image);

        }
        public void setData(int position)
        {
            ImageInfo object=myArray.get(position);
            Glide.with(imageViewItem.getContext())
                    .load(new File(object.getPath()))
                            //.placeholder(R.color.image_background)
                    .fitCenter()
                    .into(imageViewItem);
        }
    }
}
