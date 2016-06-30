package com.example.hustzxd.iamhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hustzxd.iamhere.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mPasswordView;
    private EditText mUsernameView;

    private RelativeLayout mLoginView;
    private RelativeLayout mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化Bmob
        Bmob.initialize(this, "b656a42357bc206237452e48e2e69929");

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("***", "action!");
                    attemptLogin();
                }
                return false;
            }
        });

        mLoginView = (RelativeLayout) findViewById(R.id.ly_sign_in);

        mLoginView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        mUsernameView = (EditText) findViewById(R.id.username);
        String username = getIntent().getStringExtra("username_back");
        if (!TextUtils.isEmpty(username)) {
            mUsernameView.setText(username);
        }

        mRegisterView = (RelativeLayout) findViewById(R.id.ly_register);
        mRegisterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("username", mUsernameView.getText().toString());
                startActivityForResult(intent, 0);
                overridePendingTransition(R.anim.push_left_in,
                        R.anim.push_left_out);
                LoginActivity.this.finish();
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
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.push_right_in,
                R.anim.push_right_out);
        this.finish();
    }

    private void attemptLogin() {
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("Please input username!");
            return;
        }
        if (!isPasswordValid(password)) {
            mPasswordView.setError("Password is too short!");
            return;
        }
        final BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);

        bmobUser.login(getApplicationContext(), new SaveListener() {
            @Override
            public void onSuccess() {
                if(!bmobUser.getEmailVerified()){
                    toast("请先认证邮箱");
                    BmobUser.logOut(getApplicationContext());
                    return;
                }
                toast("Login successfully!");
                onBackPressed();
            }

            @Override
            public void onFailure(int i, String s) {
                toast(s);
            }
        });

    }

    private void toast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 1;
    }

}
