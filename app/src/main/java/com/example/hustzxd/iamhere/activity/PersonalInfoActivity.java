package com.example.hustzxd.iamhere.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;
import com.example.hustzxd.iamhere.myUtils.MyUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 显示用户信息界面
 * 并增加用户信息修改逻辑
 * 尽量加上头像的修改
 * Created by Administrator on 2016/6/4.
 */
public class PersonalInfoActivity extends AppCompatActivity implements OnClickListener {

    private RelativeLayout mHeadPic;
    private RelativeLayout mNickNameLy;
    private RelativeLayout mStuNameLy;
    private RelativeLayout mStuSexLy;
    private RelativeLayout mLogoutLy;
    private RelativeLayout mStuNumLy;
    private RelativeLayout mPersonalizedSignatureLy;

    private TextView mNickNameTv;
    private TextView mStuNameTv;
    private TextView mSexTv;
    private TextView mStuNumberTv;
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
        mStuNumberTv = (TextView) findViewById(R.id.stu_number_tv);
        mStuNumberTv.setText(bmobUser.getStuNo());
        mSignatureTv = (TextView) findViewById(R.id.signature_tv);
        mSignatureTv.setText(bmobUser.getSignature());

        mNickNameLy = (RelativeLayout) findViewById(R.id.nick_name_ly);
        mNickNameLy.setOnClickListener(this);

        mStuNameLy = (RelativeLayout) findViewById(R.id.stu_name);
        mStuNameLy.setOnClickListener(this);

        mStuSexLy = (RelativeLayout) findViewById(R.id.stu_sex_ly);
        mStuSexLy.setOnClickListener(this);

        mPersonalizedSignatureLy = (RelativeLayout) findViewById(R.id.personalized_signature);
        mStuNumLy = (RelativeLayout) findViewById(R.id.stu_number_ly);

        mPersonalizedSignatureLy.setOnClickListener(this);
        mStuNumLy.setOnClickListener(this);

        mLogoutLy = (RelativeLayout) findViewById(R.id.logout_ly);
        mLogoutLy.setOnClickListener(this);

        mHeadPic = (RelativeLayout) findViewById(R.id.head_portraits);
        mHeadPic.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nick_name_ly:
                final EditText username = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入新的用户名(昵称)")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(username)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyUser newUser = new MyUser();
                                newUser.setUsername(username.getText().toString());
                                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                                newUser.update(getApplicationContext(), bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        MyUtils.toast(getApplicationContext(), "更新用户名成功");
                                        mNickNameTv.setText(username.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        MyUtils.toast(getApplicationContext(), s);
                                        Log.e("sss", i + s);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.stu_name:
                final EditText stuNameEt = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入真实姓名")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(stuNameEt)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyUser newUser = new MyUser();
                                newUser.setStuName(stuNameEt.getText().toString());
                                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                                newUser.update(getApplicationContext(), bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        MyUtils.toast(getApplicationContext(), "更新姓名成功");
                                        mStuNameTv.setText(stuNameEt.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        MyUtils.toast(getApplicationContext(), s);
                                        Log.e("sss", i + s);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.stu_sex_ly:
                new AlertDialog.Builder(this).setTitle("单选框").setIcon(
                        android.R.drawable.ic_dialog_info).setSingleChoiceItems(
                        new String[]{"男", "女"}, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final String sex;
                                if (which == 0) {
                                    sex = "男";
                                } else {
                                    sex = "女";
                                }
                                MyUser newUser = new MyUser();
                                newUser.setSex(sex);
                                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                                newUser.update(getApplicationContext(), bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        MyUtils.toast(getApplicationContext(), "更新姓名成功");
                                        mSexTv.setText(sex);
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        MyUtils.toast(getApplicationContext(), s);
                                        Log.e("sss", i + s);
                                    }
                                });
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
                break;
            case R.id.stu_number_ly:
                final EditText stuNumberEt = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入规范学号")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(stuNumberEt)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyUser newUser = new MyUser();
                                newUser.setStuNo(stuNumberEt.getText().toString());
                                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                                newUser.update(getApplicationContext(), bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        MyUtils.toast(getApplicationContext(), "更新学号成功");
                                        mStuNumberTv.setText(stuNumberEt.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        MyUtils.toast(getApplicationContext(), s);
                                        Log.e("sss", i + s);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.personalized_signature:
                final EditText stuPersonEt = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入个性签名")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(stuPersonEt)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyUser newUser = new MyUser();
                                newUser.setSignature(stuPersonEt.getText().toString());
                                MyUser bmobUser = BmobUser.getCurrentUser(getApplicationContext(), MyUser.class);
                                newUser.update(getApplicationContext(), bmobUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        MyUtils.toast(getApplicationContext(), "更新签名成功");
                                        mSignatureTv.setText(stuPersonEt.getText().toString());
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        MyUtils.toast(getApplicationContext(), s);
                                        Log.e("sss", i + s);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.logout_ly:
                new AlertDialog.Builder(this)
                        .setTitle("确认退出吗")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BmobUser.logOut(getApplicationContext());
                                onBackPressed();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.head_portraits:
                MyUtils.toast(getApplicationContext(),"暂不支持更新头像");
                break;
            default:
                break;
        }
    }
}
