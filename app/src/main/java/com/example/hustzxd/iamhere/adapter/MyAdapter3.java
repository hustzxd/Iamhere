package com.example.hustzxd.iamhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hustzxd.iamhere.Bean.Checkins;
import com.example.hustzxd.iamhere.R;

import java.util.List;

/**
 * listView 的布局适配器
 * 我的签到listView
 * Created by buxiaoyao on 2016/6/29.
 */
public class MyAdapter3 extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Checkins> mDatas;
    public MyAdapter3(Context c, List<Checkins> datas) {
        mInflater = LayoutInflater.from(c);
        mDatas = datas;

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if(convertView == null){
                convertView = mInflater.inflate(R.layout.item_listview2,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.mStuName = (TextView) convertView.findViewById(R.id.tv_student_name);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mStuNumber = (TextView) convertView.findViewById(R.id.tv_student_number);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Checkins data = mDatas.get(position);
        viewHolder.mIcon.setImageResource(R.drawable.avatar);
        viewHolder.mStuName.setText(data.getStuName());
        viewHolder.mTime.setText(data.getCreatedAt());
        viewHolder.mStuNumber.setText(data.getStuNumber());
        return convertView;
    }
    private class ViewHolder{
        ImageView mIcon;
        TextView mStuName;
        TextView mTime;
        TextView mStuNumber;
    }
}
