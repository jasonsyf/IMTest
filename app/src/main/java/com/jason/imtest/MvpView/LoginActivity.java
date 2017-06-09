package com.jason.imtest.MvpView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.imtest.IMTestApplication;
import com.jason.imtest.MvpContract.RegisterLoginIMContract;
import com.jason.imtest.MvpPresenter.LoginIMPresenter;
import com.jason.imtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements RegisterLoginIMContract.View {

    @BindView(R.id.im_login_username)
    EditText mImLoginUsername;
    @BindView(R.id.im_login_pwd)
    EditText mImLoginPwd;
    @BindView(R.id.im_login_btn)
    Button mImLoginBtn;
    private RegisterLoginIMContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        new LoginIMPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.Subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void setPresenter(RegisterLoginIMContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String username() {
        if (mImLoginUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "zhanghaobunengweikong", Toast.LENGTH_SHORT).show();
        }
        return mImLoginUsername.getText().toString();

    }

    @Override
    public String pwd() {
        if (mImLoginPwd.getText().toString().isEmpty()) {
            Toast.makeText(this, "mimabunengweikong", Toast.LENGTH_SHORT).show();
        }
        return mImLoginPwd.getText().toString();
    }

    @OnClick(R.id.im_login_btn)
    public void onViewClicked() {
        if (mImLoginUsername.getText().toString().isEmpty()) {
            Toast.makeText(IMTestApplication.context, "帐号不能为空", Toast.LENGTH_SHORT).show();
        } else if (mImLoginPwd.getText().toString().isEmpty()) {
            Toast.makeText(IMTestApplication.context, "mima不能为空", Toast.LENGTH_SHORT).show();
        } else {
            mPresenter.onSuccess();

        }
    }
}