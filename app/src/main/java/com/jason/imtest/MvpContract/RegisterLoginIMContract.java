package com.jason.imtest.MvpContract;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.hyphenate.exceptions.HyphenateException;
import com.jason.imtest.MvpPresenter.BasePresenter;
import com.jason.imtest.MvpView.BaseView;

/**
 * Created by jason_syf on 2017/5/15.
 * Email: jason_sunyf@163.com
 */

public interface RegisterLoginIMContract {
    interface View extends BaseView<Presenter> {

        String username();

        String pwd();
    }

    interface Presenter extends BasePresenter {
        Handler mHandler = new Handler(Looper.getMainLooper());

        void onSuccess();

    }
}
