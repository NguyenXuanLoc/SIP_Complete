package com.example.sip_complete.ui.splash;

import android.content.Intent;
import android.os.Handler;

import com.example.sip_complete.base.BaseActivity;
import com.example.sip_complete.R;
import com.example.sip_complete.ui.login.LoginActivity;

public class SplashActivity extends BaseActivity implements SplashView {
    SplashPresenter mPresenter = new SplashPresenter();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mPresenter.attachView(this);

    }

    @Override
    protected void eventHandle() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}