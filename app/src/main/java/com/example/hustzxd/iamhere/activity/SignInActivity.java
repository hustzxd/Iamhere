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
import android.widget.EditText;
import android.widget.RelativeLayout;
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

    private RelativeLayout mObtainCourseInfoView;
    private RelativeLayout mConfirmSignInView;
    private RelativeLayout mSuccessView;
    private EditText mRandomCodeView;
    private TextView mCourseNameView;
    private TextView mTeacherNameView;
    private TextView mPositionView;
    private TextView mTimeView;

    private WifiManager mWifiManager;
    List<LaunchSignInTable> mResults;
    Boolean isError = false;//获取课程信息出错

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mObtainCourseInfoView = (RelativeLayout) findViewById(R.id.obtain_course_info);
        mConfirmSignInView = (RelativeLayout) findViewById(R.id.confirm_sign_in);
        mSuccessView = (RelativeLayout) findViewById(R.id.sign_in_success);

        mCourseNameView = (TextView) findViewById(R.id.tv_course_name);
        mTeacherNameView = (TextView) findViewById(R.id.tv_teacher_name);
        mPositionView = (TextView) findViewById(R.id.tv_position);
        mTimeView = (TextView) findViewById(R.id.tv_time);

        mRandomCodeView = (EditText) findViewById(R.id.random_code);

        mObtainCourseInfoView.setOnClickListener(new MyOnClickListener());
        mConfirmSignInView.setOnClickListener(new MyOnClickListener());
        mSuccessView.setOnClickListener(new MyOnClickListener());

        mWifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String randomCode = mRandomCodeView.getText().toString();
            switch (v.getId()) {
                case R.id.obtain_course_info:
                    if (!mWifiManager.isWifiEnabled()) {
                        mWifiManager.setWifiEnabled(true);
                        return;
                    }
                    mWifiManager.startScan();
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
                                        Log.i("result", mResults.toString());
                                        if (mResults.size() == 0) {
                                            //未找到随机码
                                            MyUtils.toast(getApplicationContext(), "请输入正确的随机码！");
                                            isError = true;
                                        } else {
                                            mTeacherNameView.setText(mResults.get(0).getName());
                                            mCourseNameView.setText(mResults.get(0).getCourseName());
                                            mPositionView.setText(mResults.get(0).getPositive());
                                            mTimeView.setText(mResults.get(0).getUpdatedAt());

                                            mWifiManager.startScan();
                                            mObtainCourseInfoView.setVisibility(View.GONE);
                                            mConfirmSignInView.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        Log.e("sss", String.valueOf(e.getErrorCode()));
                                        MyUtils.toast(getApplicationContext(), e.getMessage());
                                        isError = true;
                                    }
                                }
                            });
                    break;
                case R.id.confirm_sign_in:
                    Boolean isRightPosition = false;
                    List<String> BSSIDs = new ArrayList<>();

                    Log.i("BSSID", mResults.get(0).getWifiBSSIDs().toString());
                    List<ScanResult> scanResults = mWifiManager.getScanResults();
                    for (ScanResult scanResult : scanResults) {
                        BSSIDs.add(scanResult.BSSID);
                        if (mResults.get(0).getWifiBSSIDs().contains(scanResult.BSSID)) {
                            isRightPosition = true;
                        }
                    }
                    Log.i("BSSID2", BSSIDs.toString());
                    if (!isRightPosition) {
                        MyUtils.toast(getApplicationContext(), "定位地点与发起签到地点不符合");
                        return;
                    }
                    Checkins checkin = new Checkins();
                    MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);

                    checkin.setStuObjectId(bmobUser.getObjectId());
                    checkin.setStuName(bmobUser.getStuName());
                    checkin.setStuNumber(bmobUser.getStuNo());
                    checkin.setRandomCode(randomCode);
                    checkin.setCourseName(mCourseNameView.getText().toString());

                    checkin.save(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            MyUtils.toast(getApplicationContext(), "签到成功");
                            mConfirmSignInView.setVisibility(View.GONE);
                            mSuccessView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            MyUtils.toast(getApplicationContext(), s);
                        }
                    });
                    break;
                case R.id.sign_in_success:
                    onBackPressed();
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
