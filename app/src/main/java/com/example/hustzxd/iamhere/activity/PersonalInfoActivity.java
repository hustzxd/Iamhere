package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.example.hustzxd.iamhere.R;

import cn.bmob.v3.BmobUser;
import me.drakeet.uiview.UIButton;
import me.drakeet.uiview.UIImageView;

/**
 * Created by Administrator on 2016/6/4.
 */
public class PersonalInfoActivity extends AppCompatActivity {
//    private UIImageView mBackButton;
    private UIButton mLogoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
//        mBackButton = (UIImageView) findViewById(R.id.back_button);
//        mBackButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        mLogoutButton = (UIButton) findViewById(R.id.logout_button);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut(getApplicationContext());
                onBackPressed();
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
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), MainActivity.class);
        PersonalInfoActivity.this.startActivity(intent);
        PersonalInfoActivity.this.finish();
    }
}
