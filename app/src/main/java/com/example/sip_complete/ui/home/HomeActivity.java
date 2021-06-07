package com.example.sip_complete.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sip_complete.R;
import com.example.sip_complete.base.BaseActivity;
import com.example.sip_complete.model.AccountInfoModel;
import com.example.sip_complete.model.sip_model.MyAccount;
import com.example.sip_complete.model.sip_model.MyApp;
import com.example.sip_complete.model.sip_model.MyAppObserver;
import com.example.sip_complete.model.sip_model.MyBuddy;
import com.example.sip_complete.model.sip_model.MyCall;
import com.example.sip_complete.ui.call.CallActivity;
import com.example.sip_complete.utils.Constant;
import com.example.sip_complete.utils.PreferencesUtils;

import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.StringVector;

public class HomeActivity extends BaseActivity implements Handler.Callback, MyAppObserver {
    private TextView btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private ImageView btnCall, imgClear, imgAddContact;
    private EditText txtDialNumber;

    public static MyApp app = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;

    public static MyBroadcastReceiver receiver = null;
    public static IntentFilter intentFilter = null;
    private final Handler handler = new Handler(this);
    private AccountInfoModel accountInfoModel;

    @Override
    public boolean handleMessage(@NonNull Message m) {
        return false;
    }

    public class MSG_TYPE {
        public final static int INCOMING_CALL = 1;
        public final static int CALL_STATE = 2;
        public final static int REG_STATE = 3;
        public final static int BUDDY_STATE = 4;
        public final static int CALL_MEDIA_STATE = 5;
        public final static int CHANGE_NETWORK = 6;
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
        Log.e("TAG", "notifyRegState: homeActivity" + msg_str);
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

    public void notifyChangeNetwork() {
        Message m = Message.obtain(handler, MSG_TYPE.CHANGE_NETWORK, null);
        m.sendToTarget();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        private String conn_name = "";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (isNetworkChange(context))
                notifyChangeNetwork();
        }

        private boolean isNetworkChange(Context context) {
            boolean network_changed = false;
            ConnectivityManager connectivity_mgr =
                    ((ConnectivityManager) context.getSystemService(
                            Context.CONNECTIVITY_SERVICE));

            NetworkInfo net_info = connectivity_mgr.getActiveNetworkInfo();
            if (net_info != null && net_info.isConnectedOrConnecting() &&
                    !conn_name.equalsIgnoreCase("")) {
                String new_con = net_info.getExtraInfo();
                if (new_con != null && !new_con.equalsIgnoreCase(conn_name))
                    network_changed = true;

                conn_name = (new_con == null) ? "" : new_con;
            } else {
                if (conn_name.equalsIgnoreCase(""))
                    conn_name = net_info.getExtraInfo();
            }
            return network_changed;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        btnZero = findViewById(R.id.btnZero);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        btnCall = findViewById(R.id.btnCall);
        imgClear = findViewById(R.id.imgClear);
        imgAddContact = findViewById(R.id.imgAddContact);
        txtDialNumber = findViewById(R.id.txtDialNumber);
        accountInfoModel = PreferencesUtils.getAccountInfo(mContext);

/*        Bundle bundle = getIntent().getBundleExtra(Constant.myBundle);
        if (bundle)*/
     /*   if (app == null)
            app = (MyApp) bundle.getSerializable(Constant.myApp);

        if (account == null)
            account = (MyAccount) bundle.getSerializable(Constant.myAccount);

        if (accCfg == null)
            accCfg = (AccountConfig) bundle.getSerializable(Constant.accountConfig);
*/
       /* if (app!=null){
            Log.e("TAG","APP !=null");
        }
        if (account!=null){
            Log.e("TAG","account !=null");
        }
        if (accCfg!=null){
            Log.e("TAG","accCfg !=null");
        }*/
   /*     if (app == null) {
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
        registerAccount();*/
    }

    private void registerAccount() {
        accCfg.setIdUri(accountInfoModel.getAcc_id());
        accCfg.getRegConfig().setRegistrarUri(accountInfoModel.getRegistrar());
        AuthCredInfoVector creds = accCfg.getSipConfig().
                getAuthCreds();
        creds.clear();
        if (accountInfoModel.getUsername().length() != 0) {
            creds.add(new AuthCredInfo("Digest", "*", accountInfoModel.getUsername(), 0,
                    accountInfoModel.getPassword()));
        }
        StringVector proxies = accCfg.getSipConfig().getProxies();
        proxies.clear();
        if (accountInfoModel.getProxy() != null && accountInfoModel.getProxy().length() != 0) {
            proxies.add(accountInfoModel.getProxy());
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

    @Override
    protected void eventHandle() {
        btnZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnZero.getText().toString(), false);
            }
        });
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnOne.getText().toString(), false);
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnTwo.getText().toString(), false);
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnThree.getText().toString(), false);
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnFour.getText().toString(), false);
            }
        });
        btnFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnFive.getText().toString(), false);
            }
        });
        btnSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnSix.getText().toString(), false);
            }
        });
        btnSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnSeven.getText().toString(), false);
            }
        });
        btnEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnEight.getText().toString(), false);
            }
        });
        btnNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber(btnNine.getText().toString(), false);
            }
        });
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDialNumber("", true);
            }
        });
        imgAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void showCallActivity() {
        Intent intent = new Intent(this, CallActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void fillDialNumber(String text, Boolean reset) {
        if (reset) {
            txtDialNumber.setText("");
        } else {
            String str = txtDialNumber.getText().toString();
            txtDialNumber.setText(text + str);
        }

        if (txtDialNumber.getText().length() > 0) {
            txtDialNumber.setVisibility(View.VISIBLE);
            txtDialNumber.setSelection(txtDialNumber.length());
            imgClear.setVisibility(View.VISIBLE);
        } else {
            txtDialNumber.setVisibility(View.GONE);
            imgClear.setVisibility(View.GONE);
            imgAddContact.setVisibility(View.INVISIBLE);
        }
    }
}