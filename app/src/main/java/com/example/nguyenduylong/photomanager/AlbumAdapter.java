package com.example.nguyenduylong.photomanager;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.nguyenduylong.photomanager.model.AlbumImage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by nguyen duy long on 11/16/2015.
 */
public class AlbumAdapter extends BaseAdapter {
    Activity context=null;
    ArrayList<AlbumImage> myArray=null;
    int layoutId;
    ArrayList<AlbumHolder> holderList;
    public AlbumAdapter(Activity context, int layoutId, ArrayList<AlbumImage> arr){
        this.context=context;
        this.layoutId=layoutId;
        this.myArray=arr;
        holderList = new ArrayList<AlbumHolder>();
        for (int i=0;i<myArray.size();i++)
        {
            holderList.add(new AlbumHolder());
        }
    }
    @Override
    public int getCount() {
        return myArray.size();
    }

    @Override
    public AlbumImage getItem(int position) {
        return myArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        AlbumHolder holder = null;
        if (null == view) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(layoutId, null);
            holder = new  AlbumHolder (view);
            view.setTag(holder);
        }else {
            holder = ( AlbumHolder )view.getTag();
        }
        holder.setData(position);
        holderList.set(position,holder);
        return view;
    }
    class AlbumHolder{
        TextView nameViewItem;
        ImageView imageViewItem;
        public AlbumHolder(){}
        public AlbumHolder(View convertView)
        {
            imageViewItem = (ImageView)convertView.findViewById(R.id.avatar_view);
            nameViewItem = (TextView)convertView.findViewById(R.id.albumname_view);
        }
        public void setData(int position)
        {
            AlbumImage object=myArray.get(position);
            nameViewItem.setText(object.getName());
            Glide.with(imageViewItem.getContext())
                    .load(new File(object.getAvatarUri()))
                            //.placeholder(R.color.image_background)
                    .fitCenter()
                    .into(imageViewItem);
        }
    }
}
