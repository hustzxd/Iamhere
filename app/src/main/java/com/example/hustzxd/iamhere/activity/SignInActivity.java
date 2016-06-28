package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hustzxd.iamhere.Bean.Checkins;
import com.example.hustzxd.iamhere.Bean.LaunchSignInTable;
import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;
import com.example.hustzxd.iamhere.myUtils.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class SignInActivity extends AppCompatActivity {

    private Button mObtainCourseInfoButton;
    private Button mConfirmSignInButton;
    private TextView mCourseInfoView;
    private EditText mRandomCodeView;

    private WifiManager mWifiManager;
    List<LaunchSignInTable> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mObtainCourseInfoButton = (Button) findViewById(R.id.obtain_course_info);
        mConfirmSignInButton = (Button) findViewById(R.id.confirm_sign_in);
        mCourseInfoView = (TextView) findViewById(R.id.course_info);
        mRandomCodeView = (EditText) findViewById(R.id.random_code);

        mObtainCourseInfoButton.setOnClickListener(new MyOnClickListener());
        mConfirmSignInButton.setOnClickListener(new MyOnClickListener());

        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String randomCode = mRandomCodeView.getText().toString();
            switch (v.getId()) {
                case R.id.obtain_course_info:
                    if (TextUtils.isEmpty(randomCode)) {
                        mRandomCodeView.setError("input random code");
                        return;
                    }
                    if (randomCode.length() != 4) {
                        mRandomCodeView.setError("error length");
                        return;
                    }
                    String bql = "select * from LaunchSignInTable where randomCode = '" + randomCode + "'";
                    Log.i("bql", bql);
                    new BmobQuery<LaunchSignInTable>().doSQLQuery(getApplicationContext(), bql,
                            new SQLQueryListener<LaunchSignInTable>() {
                                @Override
                                public void done(BmobQueryResult<LaunchSignInTable> bmobQueryResult,
                                                 BmobException e) {
                                    if (e == null) {
                                        mResults = bmobQueryResult.getResults();
                                        mCourseInfoView.setText(mResults.toString());
                                        Log.i("result", mResults.toString());
                                    } else {
                                        MyUtils.toast(getApplicationContext(), e.getMessage());
                                    }
                                }
                            });
                    break;
                case R.id.confirm_sign_in:
                    Boolean isRightPosition = false;
                    List<String> BSSIDs = new ArrayList<>();
                    mWifiManager.startScan();
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<ScanResult> scanResults = mWifiManager.getScanResults();
                    for (ScanResult scanResult : scanResults) {
                        BSSIDs.add(scanResult.BSSID);
                        mResults.get(0).getWifiBSSIDs().contains(scanResult.BSSID);
                        isRightPosition = true;
                    }

                    if(!isRightPosition){
                        MyUtils.toast(getApplicationContext(),"定位地点与发起签到地点不符合");
                        return;
                    }
                    Checkins checkin = new Checkins();
                    checkin.setRandomCode(randomCode);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
                    String date = sdf.format(new java.util.Date());
                    checkin.setDate(date);
                    MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                    checkin.setStuName(bmobUser.getStuName());
                    checkin.setStuNumber(bmobUser.getStuNo());
                    checkin.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            MyUtils.toast(getApplicationContext(), "签到成功");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            MyUtils.toast(getApplicationContext(), s);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
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
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
        this.finish();
    }
}
