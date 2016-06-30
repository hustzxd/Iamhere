package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hustzxd.iamhere.Bean.LaunchSignInTable;
import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;
import com.example.hustzxd.iamhere.myUtils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mPositionView;
    private EditText mCourseNameView;

    private TextView mRandomCodeTv;

    private RelativeLayout mGetRandomCodeLy;
    private RelativeLayout mLaunchLy;
    private RelativeLayout mBackToMainLy;


    private WifiManager mWifiManager;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        mPositionView = (EditText) findViewById(R.id.position);
        mCourseNameView = (EditText) findViewById(R.id.course_name);
        mRandomCodeTv = (TextView) findViewById(R.id.tv_random_code);

        mGetRandomCodeLy = (RelativeLayout) findViewById(R.id.ly_acquire_random_code);
        mLaunchLy = (RelativeLayout) findViewById(R.id.ly_launch_sign_in);
        mBackToMainLy = (RelativeLayout) findViewById(R.id.ly_back_to_main);

        mGetRandomCodeLy.setOnClickListener(this);
        mLaunchLy.setOnClickListener(this);
        mBackToMainLy.setOnClickListener(this);


        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_acquire_random_code:
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
                mRandomCodeTv.setText(randomNumber);
                if (!mWifiManager.isWifiEnabled()) {
                    mWifiManager.setWifiEnabled(true);
                    return;
                }
                mWifiManager.startScan();
                mGetRandomCodeLy.setVisibility(View.GONE);
                mLaunchLy.setVisibility(View.VISIBLE);
                break;
            case R.id.ly_launch_sign_in:
                //获取当前位置的SSID信息，便于位置的确认
                List<String> BSSIDs = new ArrayList<>();
                List<ScanResult> scanResults = mWifiManager.getScanResults();
                for (ScanResult scanResult : scanResults) {
                    BSSIDs.add(scanResult.BSSID);
                }
                /**
                 * 获取当前用户信息
                 */
                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                LaunchSignInTable signInTable = new LaunchSignInTable();
                signInTable.setWifiBSSIDs(BSSIDs);
                signInTable.setName(bmobUser.getStuName());
                signInTable.setRandomCode(mRandomCodeTv.getText().toString());
                signInTable.setCourseName(mCourseNameView.getText().toString());
                signInTable.setPositive(mPositionView.getText().toString());
                signInTable.setUserObjectId(bmobUser.getObjectId());
                signInTable.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("发起签到成功，请告知同学们随机码" + mRandomCodeTv.getText().toString());
                        mLaunchLy.setVisibility(View.GONE);
                        mBackToMainLy.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast("发起签到失败" + s);
                    }
                });
                break;
            case R.id.ly_back_to_main:
                onBackPressed();
            default:
                break;

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
        Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
        this.finish();
    }
}
