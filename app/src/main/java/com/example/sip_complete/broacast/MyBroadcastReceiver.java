package com.example.sip_complete.broacast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.sip_complete.utils.EventData;

import org.greenrobot.eventbus.EventBus;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private String conn_name = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (isNetworkChange(context)) {
            EventBus.getDefault().post(new EventData(EventData.KEY_NOTIFY_NETWORK));
        }
//                notifyChangeNetwork();
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
