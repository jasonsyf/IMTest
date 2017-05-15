package com.jason.imtest;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jason.imtest.MvpView.IMRegisterFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        IMRegisterFragment imRegisterFragment=new IMRegisterFragment();
        transaction.add(android.R.id.content, imRegisterFragment, "IM_REGISTER");
        transaction.show(imRegisterFragment);
        transaction.commit();
    }
}
