package me.mikasa.wandoujia.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import me.mikasa.wandoujia.listener.HttpCallbackListener;

public class ResponseCall<T> {
    Handler mHandler;//用于子线程和主线程的数据交换
    public ResponseCall(Context context, final HttpCallbackListener listener){
        Looper looper=context.getMainLooper();
        mHandler=new Handler(looper){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==0){
                    listener.onResponse((String)msg.obj);
                }else if (msg.what==1){
                    listener.onError((String) msg.obj);
                }
            }
        };
    }
    public void doResponse(T response){
        Message message=Message.obtain();
        message.obj=response;
        message.what=0;
        mHandler.sendMessage(message);
    }
    public void doError(String s){
        Message message=Message.obtain();
        message.obj=s;
        message.what=1;
        mHandler.sendMessage(message);
    }
}
