package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;
import com.example.hustzxd.iamhere.myUtils.MyUtils;

import cn.bmob.v3.BmobUser;
import me.drakeet.uiview.UIButton;
import me.drakeet.uiview.UIImageView;

/**
 * Created by Administrator on 2016/6/4.
 */
public class PersonalInfoActivity extends AppCompatActivity {
    //    private UIImageView mBackButton;
//    private UIButton mLogoutButton;
    private RelativeLayout mHeadPic;
    private RelativeLayout mNickNameLy;
    private RelativeLayout mLogoutLy;

    private TextView mNickNameTv;
    private TextView mStuNameTv;
    private TextView mSexTv;
    private TextView mSignatureTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);

        mNickNameTv = (TextView) findViewById(R.id.nick_name_tv);
        mNickNameTv.setText(bmobUser.getUsername());
        mStuNameTv = (TextView) findViewById(R.id.stu_name_tv);
        mStuNameTv.setText(bmobUser.getStuName());
        mSexTv = (TextView) findViewById(R.id.stu_sex_tv);
        mSexTv.setText(bmobUser.getSex());
        mSignatureTv = (TextView) findViewById(R.id.signature_tv);
        mSignatureTv.setText(bmobUser.getPersonalizedSignature());

        mLogoutLy = (RelativeLayout) findViewById(R.id.logout_ly);
        mLogoutLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut(getApplicationContext());
                onBackPressed();
            }
        });

        mHeadPic = (RelativeLayout) findViewById(R.id.head_portraits);
        mHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.toast(getApplicationContext(), "暂不支持更改头像");
            }
        });
//        mNickNameLy = findViewById(R.id.nick_name)
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
        Intent intent = new Intent(PersonalInfoActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
        this.finish();
    }
}
