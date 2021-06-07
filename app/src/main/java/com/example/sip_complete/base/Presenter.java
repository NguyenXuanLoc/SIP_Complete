package com.example.sip_complete.base;

public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();
}