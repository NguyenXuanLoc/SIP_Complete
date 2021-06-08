package com.example.sip_complete.ui.home;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import com.example.sip_complete.broacast.MyBroadcastReceiver;
import com.example.sip_complete.model.AccountInfoModel;
import com.example.sip_complete.model.sip_model.MyAccount;
import com.example.sip_complete.model.sip_model.MyApp;
import com.example.sip_complete.model.sip_model.MyAppObserver;
import com.example.sip_complete.model.sip_model.MyBuddy;
import com.example.sip_complete.model.sip_model.MyCall;
import com.example.sip_complete.ui.call.CallActivity;
import com.example.sip_complete.utils.Constant;
import com.example.sip_complete.utils.EventData;
import com.example.sip_complete.utils.PreferencesUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.AuthCredInfoVector;
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;
import org.pjsip.pjsua2.StringVector;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsip_status_code;

import java.util.HashMap;

public class HomeActivity extends BaseActivity implements Handler.Callback, MyAppObserver {
    private TextView btnZero, btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private ImageView btnCall, imgClear, imgAddContact;
    private EditText txtDialNumber;

    public static MyApp app = null;
    public static MyAccount account = null;
    public static AccountConfig accCfg = null;
    public static MyCall currentCall = null;

    public static MyBroadcastReceiver receiver = null;
    public static IntentFilter intentFilter = null;

    private final Handler handler = new Handler(this);
    private AccountInfoModel accountInfoModel;

    public class MSG_TYPE {
        public final static int INCOMING_CALL = 1;
        public final static int CALL_STATE = 2;
        public final static int REG_STATE = 3;
        public final static int BUDDY_STATE = 4;
        public final static int CALL_MEDIA_STATE = 5;
        public final static int CHANGE_NETWORK = 6;
    }

    @Override
    public boolean handleMessage(@NonNull Message m) {
        switch (m.what) {
            case 0: {
                Log.e("TAG", "0 finíh");
//                finish();
                Runtime.getRuntime().gc();
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            }
            case MSG_TYPE.CALL_STATE: {
                CallInfo ci = (CallInfo) m.obj;

                if (currentCall == null || ci == null || ci.getId() != currentCall.getId()) {
                    Log.e("TAG", "Call state event received, but call info is invalid");
                    return true;
                }
                if (CallActivity.handler_ != null) {
                    Message m2 = Message.obtain(CallActivity.handler_, MSG_TYPE.CALL_STATE, ci);
                    m2.sendToTarget();
                }

                if (ci.getState() == pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED) {
                    currentCall.delete();
                    currentCall = null;
                }
                break;
            }
            case MSG_TYPE.INCOMING_CALL: {
                Log.e("TAG", "INCOMING_CALL");
                final MyCall call = (MyCall) m.obj;
                CallOpParam prm = new CallOpParam();
                if (currentCall != null) {
                    call.delete();
                    return true;
                }
                prm.setStatusCode(pjsip_status_code.PJSIP_SC_RINGING);
                try {
                    call.answer(prm);
                } catch (Exception e) {
                }
                currentCall = call;
                showCallActivity();
                Log.e("TAG", "incoming callllllll");
                break;
            }
            case MSG_TYPE.CALL_MEDIA_STATE: {
                if (CallActivity.handler_ != null) {
                    Message m2 = Message.obtain(CallActivity.handler_,
                            MSG_TYPE.CALL_MEDIA_STATE,
                            null);
                    m2.sendToTarget();
                }
                break;
            }
            case MSG_TYPE.BUDDY_STATE: {
                MyBuddy buddy = (MyBuddy) m.obj;
                int idx = account.buddyList.indexOf(buddy);
                notifyCallState(currentCall);
            }
        }
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
        Log.e("TAG", "notifyRegState: homeActivity" + msg_str);
    }

    @Override
    public void notifyIncomingCall(MyCall call) {
        Log.e("TAG", "notifyIncomingCall");
        Message m = Message.obtain(handler, MSG_TYPE.INCOMING_CALL, call);
        m.sendToTarget();
    }

    @Override
    public void notifyCallState(MyCall call) {
        Log.e("TAG", "notifyCallState");
        if (currentCall == null || call.getId() != currentCall.getId())
            return;
        CallInfo ci = null;
        try {
            ci = call.getInfo();
        } catch (Exception e) {
        }
        if (ci == null)
            return;
        Message m = Message.obtain(handler, MSG_TYPE.CALL_STATE, ci);
        m.sendToTarget();
    }

    @Override
    public void notifyCallMediaState(MyCall call) {
        Message m = Message.obtain(handler, MSG_TYPE.CALL_MEDIA_STATE, null);
        m.sendToTarget();
    }

    @Override
    public void notifyBuddyState(MyBuddy buddy) {
        Message m = Message.obtain(handler, MSG_TYPE.BUDDY_STATE, buddy);
        m.sendToTarget();
    }

    @Override
    public void notifyChangeNetwork() {
        Message m = Message.obtain(handler, MSG_TYPE.CHANGE_NETWORK, null);
        m.sendToTarget();
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
        registerAccount();
        if (receiver == null) {
            receiver = new MyBroadcastReceiver();
            intentFilter = new IntentFilter(
                    ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(receiver, intentFilter);
        }
    }

    private void registerAccount() {
        accCfg.setIdUri(accountInfoModel.getAcc_id());
        Log.e("TAG", "account id uri: " + accountInfoModel.getAcc_id());
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
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void OnCustomEvent(EventData event) {
        if (event.getKey() == EventData.KEY_NOTIFY_NETWORK) {
            Log.e("TAG", "notifyChangeNetwork");
            notifyChangeNetwork();
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
                makeCall();
            }
        });

    }

    public void makeCall() {
        if (currentCall != null) {
            Log.e("TAG", "currentCall !=null => return");
            return;
        }
        String buddy_uri = "sip:" + txtDialNumber.getText().toString() + "@" + accountInfoModel.getDomain();
        Log.e("TAG", "makeCall: " + buddy_uri);
        MyCall call = new MyCall(account, -1);
        CallOpParam prm = new CallOpParam(true);
        try {
            call.makeCall(buddy_uri, prm);
        } catch (Exception e) {
            Log.e("TAG", "exe: " + e.getMessage());
            call.delete();
            return;
        }

        currentCall = call;
        showCallActivity();
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
            txtDialNumber.setText(str + text);
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