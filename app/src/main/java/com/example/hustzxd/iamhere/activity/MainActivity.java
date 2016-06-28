package com.example.hustzxd.iamhere.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
 * 程序主界面，Bmob组件的初始化
 * 主界面显示所有的功能入口
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


        mLoginButton.setOnClickListener(new MyClickListener());
        mResponseButton.setOnClickListener(new MyClickListener());
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
            if(v.getId() == R.id.login_button){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                return;
            }
            MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
            if (bmobUser == null) {
                toast("请先登录");
                return;
            }
            Intent intent;
            switch (v.getId()) {
                case R.id.userpic:
                    intent = new Intent(MainActivity.this, PersonalInfoActivity.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    MainActivity.this.finish();
                    break;
                case R.id.launch:
                    intent = new Intent(MainActivity.this, LaunchActivity.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    MainActivity.this.finish();
                    break;
                case R.id.response:
                    intent = new Intent(MainActivity.this, SignInActivity.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    MainActivity.this.finish();
                    break;
                case R.id.search1:
                    intent = new Intent(MainActivity.this, SearchLaunchActivity.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    MainActivity.this.finish();
                    break;
                case R.id.search2:
                    intent = new Intent(MainActivity.this, SearchCheckinsActivity.class);
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.anim.push_left_in,
                            R.anim.push_left_out);
                    MainActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
