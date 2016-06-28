package com.example.hustzxd.iamhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hustzxd.iamhere.Bean.Checkins;
import com.example.hustzxd.iamhere.activity.SearchLaunchActivity;
import com.example.hustzxd.iamhere.myUtils.MyUtils;
import com.quentindommerc.superlistview.SuperListview;
import com.quentindommerc.superlistview.SwipeDismissListViewTouchListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class ShowSigninActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private String randomCode;

    private SuperListview mSuperListview;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_signin);
        Intent intent = getIntent();
        randomCode = intent.getStringExtra("randomCode");
        MyUtils.toast(getApplicationContext(), randomCode);



        // Empty list view demo, just pull to add more items
        ArrayList<String> lst = new ArrayList<String>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, lst);

        // This is what you're looking for
        mSuperListview = (SuperListview) findViewById(R.id.list);

        // Setting the refresh listener will enable the refresh progressbar
        mSuperListview.setRefreshListener(this);

        // Wow so beautiful
//        mSuperListview.setRefreshingColor(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);

        // I want to get loadMore triggered if I see the last item (1)

        mSuperListview.setupSwipeToDismiss(new SwipeDismissListViewTouchListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                return true;
            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
            }
        }, true);

        InfoList();
    }


    private void InfoList() {
        mAdapter.clear();
        String bql = "select * from Checkins where randomCode = '" + randomCode + "'";
        Log.i("sss", bql);
        new BmobQuery<Checkins>().doSQLQuery(getApplicationContext(), bql, new SQLQueryListener<Checkins>() {

            @Override
            public void done(BmobQueryResult<Checkins> result, BmobException e) {
                if (e == null) {
                    List<Checkins> list = (List<Checkins>) result.getResults();
                    if (list != null && list.size() > 0) {
                        Log.i("sss", list.toString());
                        for (Checkins c : list) {
                            mAdapter.add(c.toString());
                        }
                        mSuperListview.setAdapter(mAdapter);
                    } else {
                        Log.i("smile", "查询成功，无数据返回");
                    }
                } else {
                    Log.i("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                    MyUtils.toast(getApplicationContext(), e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.onBackPressed();
            return true; //确认返回了
        }
        return false;  //不允许返回
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShowSigninActivity.this, SearchLaunchActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
        this.finish();
    }

    @Override
    public void onRefresh() {
        InfoList();
    }
}
