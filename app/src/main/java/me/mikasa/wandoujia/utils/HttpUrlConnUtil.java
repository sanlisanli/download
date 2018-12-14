package me.mikasa.wandoujia.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.mikasa.wandoujia.listener.HttpCallbackListener;

public class HttpUrlConnUtil {
    public static void doGet(final Context context, final String url, final String encode, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String string=null;
                HttpURLConnection connection=null;
                InputStream is=null;
                try {
                    URL u=new URL(url);
                    connection=(HttpURLConnection)u.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(6000);
                    connection.setReadTimeout(6000);
                    if (connection.getResponseCode()==200){
                        is=connection.getInputStream();
                        string=stream2string(is,encode);
                        new ResponseCall<String>(context, listener).doResponse(string);
                    }else {
                        new ResponseCall<String>(context, listener).doError("网络错误");
                    }
                }catch (MalformedURLException e){
                    e.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }finally {
                    try {
                        if (is!=null){
                            is.close();
                        }
                        if (connection!=null){
                            connection.disconnect();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                //
            }
        }).start();//start()
    }
    private static String stream2string(InputStream is,String encode){
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] b=new byte[1024];
        int len=0;
        try {
            if (encode==null||encode.equals("")){
                encode="utf-8";
            }
            while ((len = is.read(b)) > 0){
                out.write(b,0,len);
            }
            return out.toString(encode);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }
}
