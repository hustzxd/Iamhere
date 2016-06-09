package com.example.hustzxd.iamhere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import me.drakeet.uiview.UIButton;
import me.drakeet.uiview.UIImageView;

/**
 * main activity
 * Created by buxiaoyao on 2016/6/4.
 */
public class MainActivity extends Activity {
    private UIImageView mUserButton;
    private TextView mUserNameView;
    private TextView mPersonalizedSignatureView;
    private UIButton mLoginButton;
    private Button mLaunchButton;
    private Button mResponseButton;
    private Button mSearchMyLaunch;
    private Button mSearchCheckins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化Bmob
        Bmob.initialize(this, "b656a42357bc206237452e48e2e69929");

        mUserButton = (UIImageView) findViewById(R.id.userpic);
        mUserButton.setOnClickListener(new MyClickListener());
        mPersonalizedSignatureView = (TextView) findViewById(R.id.personalized_signature);
        mUserNameView = (TextView) findViewById(R.id.username);
        mLoginButton = (UIButton) findViewById(R.id.login_button);
        mLaunchButton = (Button) findViewById(R.id.launch);
        mResponseButton = (Button) findViewById(R.id.response);
        mResponseButton = (Button) findViewById(R.id.response);
        mSearchCheckins = (Button) findViewById(R.id.search2);
        mSearchMyLaunch = (Button) findViewById(R.id.search1);


        mResponseButton.setOnClickListener(new MyClickListener());
        mLoginButton.setOnClickListener(new MyClickListener());
        mLaunchButton.setOnClickListener(new MyClickListener());
        mSearchCheckins.setOnClickListener(new MyClickListener());
        mSearchMyLaunch.setOnClickListener(new MyClickListener());


        MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
        if (bmobUser == null) {
            mUserNameView.setVisibility(View.GONE);
            mPersonalizedSignatureView.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
        } else {
            mUserNameView.setText(bmobUser.getUsername());
            Log.i("main", bmobUser.getUsername());
            mUserNameView.setVisibility(View.VISIBLE);
            mPersonalizedSignatureView.setText(bmobUser.getPersonalizedSignature());
            mPersonalizedSignatureView.setVisibility(View.VISIBLE);
        }
    }

    private class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.userpic:
                    intent.setClass(getApplicationContext(), PersonalInfoActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.login_button:
                    intent.setClass(getApplicationContext(), LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.launch:
                    intent.setClass(getApplicationContext(), LaunchActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.response:
                    intent.setClass(getApplicationContext(), SignInActivity.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.search1:
                    intent.setClass(getApplicationContext(), SearchLaunchActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.search2:
                    intent.setClass(getApplicationContext(), SearchCheckinsActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    private void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
