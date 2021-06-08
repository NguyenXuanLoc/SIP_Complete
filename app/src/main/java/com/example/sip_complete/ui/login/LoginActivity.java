package com.example.sip_complete.ui.login;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sip_complete.R;
import com.example.sip_complete.base.BaseActivity;
import com.example.sip_complete.model.AccountInfoModel;
import com.example.sip_complete.ui.home.HomeActivity;
import com.example.sip_complete.utils.PreferencesUtils;


public class LoginActivity extends BaseActivity {
    private LoginPresenter mPresenter = new LoginPresenter();
    private EditText edtUserName, edtPass, edtDomain;
    private ProgressBar pbLoading;
    private TextView btnLogin;

    private String acc_id = "";
    private String registrar = "";
    private String proxy = "";
    private String username = "";
    private String password = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        edtDomain = findViewById(R.id.edtDomain);
        edtPass = findViewById(R.id.edtPass);
        edtUserName = findViewById(R.id.edtUserName);
        btnLogin = findViewById(R.id.btnLogin);
        pbLoading = findViewById(R.id.pbLoading);
    }


    @Override
    protected void eventHandle() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pbLoading.setVisibility(View.VISIBLE);
                acc_id = "sip:" + edtUserName.getText().toString() + "@" + edtDomain.getText().toString();
                registrar = "sip:" + edtDomain.getText().toString();
                proxy = "";
                username = edtUserName.getText().toString();
                password = edtPass.getText().toString();
                if (!acc_id.isEmpty() && !registrar.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                    PreferencesUtils.saveAccountInfo(new AccountInfoModel(acc_id, registrar, edtDomain.getText().toString(), username, password), mContext);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(mContext, HomeActivity.class));
                        }
                    }, 1000);
                } else showToast(R.string.login_fails);


            }
        });
    }
}