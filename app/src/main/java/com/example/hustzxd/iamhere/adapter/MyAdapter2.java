package com.example.hustzxd.iamhere.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hustzxd.iamhere.Bean.Checkins;
import com.example.hustzxd.iamhere.Bean.LaunchSignInTable;
import com.example.hustzxd.iamhere.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * listView 的布局适配器
 * 我发起的签到listView
 * Created by buxiaoyao on 2016/6/29.
 */
public class MyAdapter2 extends BaseAdapter {



    private LayoutInflater mInflater;
    private List<LaunchSignInTable> mDatas;
    public MyAdapter2(Context c, List<LaunchSignInTable> datas) {
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
                convertView = mInflater.inflate(R.layout.item_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.mCourseName = (TextView) convertView.findViewById(R.id.tv_course_name);
            viewHolder.mTime = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.mRandomCode = (TextView) convertView.findViewById(R.id.tv_random_code);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LaunchSignInTable data = mDatas.get(position);
        viewHolder.mIcon.setImageResource(R.drawable.avatar2);
        viewHolder.mCourseName.setText(data.getCourseName());
        viewHolder.mTime.setText(data.getCreatedAt());
        viewHolder.mRandomCode.setText(data.getRandomCode());
        return convertView;
    }
    private class ViewHolder{
        ImageView mIcon;
        TextView mCourseName;
        TextView mTime;
        TextView mRandomCode;
    }
}
