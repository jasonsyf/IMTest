package com.jason.imtest.MvpView;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jason.imtest.IMTestApplication;
import com.jason.imtest.MvpContract.RegisterLoginIMContract;
import com.jason.imtest.MvpPresenter.RegisterIMPresenter;
import com.jason.imtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static io.reactivex.plugins.RxJavaPlugins.onError;

/**
 * A simple {@link Fragment} subclass.
 */
public class IMRegisterFragment extends Fragment implements RegisterLoginIMContract.View{


    @BindView(R.id.im_register_username)
    EditText mImRegisterUsername;
    @BindView(R.id.im_register_pwd)
    EditText mImRegisterPwd;
    @BindView(R.id.im_register_pwd2)
    EditText mImRegisterPwd2;
    @BindView(R.id.im_register_btn)
    Button mImRegisterBtn;
    private RegisterLoginIMContract.Presenter mPresenter;
    private String pwd, pwd2;
    public IMRegisterFragment() {

    }

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new RegisterIMPresenter(this);
        pwd = mImRegisterPwd.getText().toString();
        pwd2 = mImRegisterPwd2.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_imregister, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.im_register_btn)
    public void onClick() {
        if (mImRegisterUsername.getText().toString().isEmpty()) {
            Toast.makeText(IMTestApplication.context, "帐号不能为空", Toast.LENGTH_SHORT).show();
        } else {
            success();
        }
    }

    @Override
    public void setPresenter(RegisterLoginIMContract.Presenter presenter) {
        mPresenter=presenter;
    }

    @Override
    public void progress() {
        Toast.makeText(IMTestApplication.context, "开始注册", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success() {
        progress();
        mPresenter.onSuccess();
        Toast.makeText(IMTestApplication.context, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void error(HyphenateException e) {
        Log.e("register", e.getMessage());
        mPresenter.onError(e);

    }

    @Override
    public String username() {
        return mImRegisterUsername.getText().toString();
    }

    @Override
    public String pwd() {
        if (pwd.isEmpty()&pwd2.isEmpty()&pwd.equals(pwd2)) {
            return pwd2;
        } else {
            Toast.makeText(getContext(), "两次密码不一致！", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
