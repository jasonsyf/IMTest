package com.jason.imtest.MvpView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.imtest.IMTestApplication;
import com.jason.imtest.MvpContract.RegisterLoginIMContract;
import com.jason.imtest.MvpPresenter.RegisterIMPresenter;
import com.jason.imtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterLoginIMContract.View {

    @BindView(R.id.im_register_username)
    EditText mImRegisterUsername;
    @BindView(R.id.im_register_pwd)
    EditText mImRegisterPwd;
    @BindView(R.id.im_register_pwd2)
    EditText mImRegisterPwd2;
    @BindView(R.id.im_register_btn)
    Button mImRegisterBtn;
    private RegisterLoginIMContract.Presenter mPresenter;
    @NonNull
    String pwd, pwd2;


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.Subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new RegisterIMPresenter(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.im_register_btn)
    public void onViewClicked() {
        if (mImRegisterUsername.getText().toString().isEmpty()) {
            Toast.makeText(IMTestApplication.context, "帐号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            progress();
            mPresenter.onSuccess();
            Toast.makeText(IMTestApplication.context, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void setPresenter(RegisterLoginIMContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void progress() {
        Toast.makeText(IMTestApplication.context, "开始注册", Toast.LENGTH_SHORT).show();
    }


    @Override
    public String username() {
        return mImRegisterUsername.getText().toString();
    }

    @Override
    public String pwd() {
        pwd = mImRegisterPwd.getText().toString();
        pwd2 = mImRegisterPwd2.getText().toString();
        if (pwd.equals(pwd2)) {
            return pwd2;
        } else {
            Toast.makeText(this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
