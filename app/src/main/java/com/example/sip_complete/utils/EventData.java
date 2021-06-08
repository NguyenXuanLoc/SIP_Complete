package com.example.sip_complete.utils;

public class EventData {
    public static int KEY_NOTIFY_NETWORK;
    private int key;
    private Object data;

    public EventData(int key) {
        this.key = key;
    }

    public EventData(int key, Object data) {
        this.key = key;
        this.data = data;
    }



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
