package com.jason.imtest.MvpPresenter;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.jason.imtest.IMTestApplication;
import com.jason.imtest.MvpContract.RegisterLoginIMContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/8 0008.
 */

public class LoginIMPresenter implements RegisterLoginIMContract.Presenter {
    @NonNull
    private final RegisterLoginIMContract.View mView;
    @NonNull
    private CompositeDisposable mDisposable;

    public LoginIMPresenter(@NonNull RegisterLoginIMContract.View view) {
        mView = view;
        mDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void Subscribe() {
        onSuccess();
    }

    @Override
    public void unSubscribe() {
        mDisposable.clear();
    }

    @Override
    public void onSuccess() {
        final String username = mView.username();
        final String pwd = mView.pwd();
            Observable<String> observable = Observable.create(e ->
                    EMClient.getInstance().login(username.isEmpty()?"1":username, pwd.isEmpty()?"1":pwd, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            mHandler.post(() -> {
                                Toast.makeText(IMTestApplication.context(), "登陆成功", Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onError(int code, String error) {
                            mHandler.post(() -> {
                                Toast.makeText(IMTestApplication.context(), code + error, Toast.LENGTH_SHORT).show();
                            });
                        }

                        @Override
                        public void onProgress(int progress, String status) {
                            mHandler.post(() -> {
                                Toast.makeText(IMTestApplication.context(), progress + status, Toast.LENGTH_SHORT).show();
                            });
                        }
                    }));
            mDisposable.add(observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }

}
