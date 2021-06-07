package com.example.sip_complete.ui.call;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.sip_complete.R;

public class CallActivity extends AppCompatActivity {
    public static Handler handler_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
    }
}