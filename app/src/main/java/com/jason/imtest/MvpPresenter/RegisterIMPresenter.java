package com.jason.imtest.MvpPresenter;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.data.DataBufferObserver;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.jason.imtest.IMTestApplication;
import com.jason.imtest.MainActivity;
import com.jason.imtest.MvpContract.RegisterLoginIMContract;
import com.jason.imtest.MvpModel.RegisterModel;
import com.jason.imtest.MvpView.IMRegisterFragment;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by jason_syf on 2017/5/15.
 * Email: jason_sunyf@163.com
 */

public class RegisterIMPresenter implements RegisterLoginIMContract.Presenter{

    @NonNull
    private final RegisterLoginIMContract.View mView;
    @NonNull
    private CompositeDisposable mDisposable;

    public RegisterIMPresenter(@NonNull RegisterLoginIMContract.View view) {
        mView = view;
        mDisposable=new CompositeDisposable();
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
    public void onProgress() {
    }

    @Override
    public void onSuccess() {
        final String username = mView.username();
        final String pwd = mView.pwd();
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                try {
                    EMClient.getInstance().createAccount(username, pwd);//同步方法
                } catch (HyphenateException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mDisposable.add(observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }



    @Override
    public void onError(HyphenateException e) {
        mView.error(e);
    }
}
