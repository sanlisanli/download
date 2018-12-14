package me.mikasa.wandoujia.listener;

public interface HttpCallbackListener {
    void onResponse(String response);
    void onError(String s);
}
