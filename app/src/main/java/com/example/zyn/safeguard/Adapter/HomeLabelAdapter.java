package com.example.zyn.safeguard.Adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyn.safeguard.R;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by zyn on 2016/7/22.
 */

class viewHolder{
    ImageView ivlabel;
    TextView tvLname;
}

public class HomeLabelAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    LinkedList<HashMap<String,Integer>> mll;
    Context mCt;
    public HomeLabelAdapter(Context ct, LinkedList<HashMap<String,Integer>> ll){
        mCt = ct ;
        mll = ll ;
    }
    @Override
    public int getCount() {

        return mll.size();
    }

    @Override
    public Object getItem(int position) {
        return mll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder vh = new viewHolder();
        if(convertView!=null){
            convertView = mInflater.inflate(R.layout.home_label_item,null);
            vh.ivlabel = (ImageView)convertView.findViewById(R.id.iv_label_pic);
            vh.tvLname = (TextView)convertView.findViewById(R.id.tv_label_name);
            convertView.setTag(vh);
        }else{
            vh = (viewHolder) convertView.getTag();
        }
        return null;
    }
}
