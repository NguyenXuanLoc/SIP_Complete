package com.example.sip_complete.ui.login;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sip_complete.base.BaseActivity;
import com.example.sip_complete.R;

import com.example.sip_complete.model.AccountInfoModel;
import com.example.sip_complete.model.sip_model.MyAccount;
import com.example.sip_complete.model.sip_model.MyApp;
import com.example.sip_complete.model.sip_model.MyAppObserver;
import com.example.sip_complete.model.sip_model.MyBuddy;
import com.example.sip_complete.model.sip_model.MyCall;
import com.example.sip_complete.ui.home.HomeActivity;
import com.example.sip_complete.utils.Constant;
import com.example.sip_complete.utils.PreferencesUtils;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.StringVector;

public class LoginActivity extends BaseActivity implements Handler.Callback, MyAppObserver {
    private LoginPresenter mPresenter = new LoginPresenter();
    private EditText edtUserName, edtPass, edtDomain;
    private ProgressBar pbLoading;
    private TextView btnLogin;

    public static MyApp app = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;
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

        if (app == null) {
            app = new MyApp();
            // Wait for GDB to init, for native debugging only
            if (false &&
                    (getApplicationInfo().flags &
                            ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }

            app.init(this, getFilesDir().getAbsolutePath());
        }
        if (app.accList.size() == 0) {
            accCfg = new AccountConfig();
            accCfg.setIdUri("sip:localhost");
            accCfg.getNatConfig().setIceEnabled(true);
            accCfg.getVideoConfig().setAutoTransmitOutgoing(true);
            accCfg.getVideoConfig().setAutoShowIncoming(true);
            account = app.addAcc(accCfg);
        } else {
            account = app.accList.get(0);
            accCfg = account.cfg;
        }

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
                accCfg.setIdUri(acc_id);
                accCfg.getRegConfig().setRegistrarUri(registrar);
                AuthCredInfoVector creds = accCfg.getSipConfig().
                        getAuthCreds();
                creds.clear();
                if (username.length() != 0) {
                    creds.add(new AuthCredInfo("Digest", "*", username, 0,
                            password));
                }
                StringVector proxies = accCfg.getSipConfig().getProxies();
                proxies.clear();
                if (proxy.length() != 0) {
                    proxies.add(proxy);
                }

                /* Enable ICE */
                accCfg.getNatConfig().setIceEnabled(true);
                /* Finally */
                try {
                    account.modify(accCfg);
                } catch (Exception e) {
                    Log.e("TAG", "exe: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    @Override
    public void notifyRegState(int code, String reason, long expiration) {
        String msg_str = "";
        if (expiration == 0)
            msg_str += Constant.unRegistration;
        else
            msg_str += Constant.registration;

        if (code / 100 == 2)
            msg_str += Constant.successful;
        else
            msg_str += Constant.register_false + reason;
        Log.e("TAG", "notifyRegState: " + msg_str);
        if (code / 100 == 2) {
            account.delete();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showToast(R.string.login_success);
                            PreferencesUtils.saveAccountInfo(new AccountInfoModel(acc_id, registrar, username, password), mContext);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constant.myApp, app);
                            bundle.putSerializable(Constant.accountConfig, accCfg);
                            bundle.putSerializable(Constant.myAccount, account);
                            Intent intent = new Intent(mContext, HomeActivity.class);
//                            intent.putExtra(Constant.myBundle, bundle);
                            startActivity(intent);
                            pbLoading.setVisibility(View.GONE);
                            finish();
                        }
                    }, 1000);
                }
            });
        } else {
            showToast(R.string.login_fails);
        }
    }

    @Override
    public void notifyIncomingCall(MyCall call) {

    }

    @Override
    public void notifyCallState(MyCall call) {

    }

    @Override
    public void notifyCallMediaState(MyCall call) {

    }

    @Override
    public void notifyBuddyState(MyBuddy buddy) {

    }

    @Override
    public void notifyChangeNetwork() {

    }
}