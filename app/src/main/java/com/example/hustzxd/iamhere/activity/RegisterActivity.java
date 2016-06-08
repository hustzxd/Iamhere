package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hustzxd.iamhere.Bean.MyUser;
import com.example.hustzxd.iamhere.R;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/6/4.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordAgainView;
    private EditText mEmailView;
    private EditText mStuNoView;
    private EditText mStuNameView;
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordAgainView = (EditText) findViewById(R.id.password_again);
        mEmailView = (EditText) findViewById(R.id.email);
        mStuNoView = (EditText) findViewById(R.id.stu_number);
        mStuNameView = (EditText) findViewById(R.id.stu_name);
        mRegisterButton = (Button) findViewById(R.id.register_button);


        String username = getIntent().getStringExtra("username");
        mUsernameView.setText(username);


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();
                String passwordAgain = mPasswordAgainView.getText().toString();
                String email = mEmailView.getText().toString();
                String stuNo = mStuNoView.getText().toString();
                String stuName = mStuNameView.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    mUsernameView.setError("Input your username");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPasswordView.setError("Input your password");
                    return;
                }
                if (TextUtils.isEmpty(passwordAgain) || !passwordAgain.equals(password)) {
                    mPasswordAgainView.setError("Entered passwords differ");
                    return;
                }
                if (!email.contains("@")) {
                    mEmailView.setError("Email address format is not correct");
                    return;
                }
                if (TextUtils.isEmpty(stuNo)) {
                    mStuNoView.setError("Input your student number");
                    return;
                }
                if (TextUtils.isEmpty(stuName)) {
                    mStuNameView.setError("Input your real name");
                    return;
                }

                MyUser myUser = new MyUser();
                myUser.setUsername(username);
                myUser.setPassword(password);
                myUser.setEmail(email);
                myUser.setStuNo(stuNo);
                myUser.setStuName(stuName);
                myUser.signUp(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        toast("register successfully");
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        toast(s);
                    }
                });
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
        RegisterActivity.this.startActivity(intent);
        RegisterActivity.this.finish();
    }

    private void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
