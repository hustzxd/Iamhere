package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hustzxd.iamhere.Bean.LaunchSignInTable;
import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;
import com.example.hustzxd.iamhere.myUtils.MyUtils;

import java.text.SimpleDateFormat;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LaunchActivity extends AppCompatActivity {
    private EditText mPositionView;
    private EditText mCourseNameView;
    private TextView mLaunchTimeView;
    private TextView mLaunchPersonNameView;
    private Button mLaunchSignInButton;
    private Button mAcquireRandomCodeButton;
    private TextView mRandomCodeView;
    private Button mBackToMainButton;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        mLaunchTimeView = (TextView) findViewById(R.id.launch_time);
        mLaunchPersonNameView = (TextView) findViewById(R.id.launch_perosn_name);
        mPositionView = (EditText) findViewById(R.id.position);
        mCourseNameView = (EditText) findViewById(R.id.course_name);
        mLaunchSignInButton = (Button) findViewById(R.id.launch_sign_in);
        mAcquireRandomCodeButton = (Button) findViewById(R.id.acquire_random_code);
        mRandomCodeView = (TextView) findViewById(R.id.random_code);
        mBackToMainButton = (Button) findViewById(R.id.back_to_main);

        mBackToMainButton.setOnClickListener(new MyOnClickListener());
        mLaunchSignInButton.setOnClickListener(new MyOnClickListener());
        mAcquireRandomCodeButton.setOnClickListener(new MyOnClickListener());

        /**
         * 初始化发起人姓名，和发起时间
         */
        MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
        mLaunchPersonNameView.setText(bmobUser.getStuName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
        String date = sdf.format(new java.util.Date());
        mLaunchTimeView.setText(date);

    }

    private class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.acquire_random_code:
                    String position = mPositionView.getText().toString();
                    if (TextUtils.isEmpty(position)) {
                        mPositionView.setError("input position");
                        return;
                    }
                    String courseName = mCourseNameView.getText().toString();
                    if (TextUtils.isEmpty(courseName)) {
                        mCourseNameView.setError("input course name");
                        return;
                    }
                    String randomNumber = MyUtils.getRandomString(4);
                    mRandomCodeView.setText(randomNumber);
                    break;
                case R.id.launch_sign_in:
                    if ("xxxx".equals(mRandomCodeView.getText().toString())) {
                        toast("请先获取随机码");
                        return;
                    }
                    LaunchSignInTable signInTable = new LaunchSignInTable();
                    signInTable.setName(mLaunchPersonNameView.getText().toString());
                    signInTable.setDate(mLaunchTimeView.getText().toString());
                    signInTable.setPositive(mPositionView.getText().toString());
                    signInTable.setCourseName(mCourseNameView.getText().toString());
                    signInTable.setRandomCode(mRandomCodeView.getText().toString());
                    signInTable.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            toast("发起签到成功，请告知待签到同学随机码");
                            mLaunchSignInButton.setVisibility(View.GONE);
                            mBackToMainButton.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            toast("发起签到失败" + s);
                        }
                    });
                    break;
                case R.id.back_to_main:
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    LaunchActivity.this.startActivity(intent);
                    LaunchActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        LaunchActivity.this.startActivity(intent);
        LaunchActivity.this.finish();
    }
}
