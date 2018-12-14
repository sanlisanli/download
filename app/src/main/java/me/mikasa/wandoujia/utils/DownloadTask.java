package me.mikasa.wandoujia.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import me.mikasa.wandoujia.listener.DownloadUpdateListener;

public class DownloadTask extends AsyncTask<String,Integer,Long> {
    private DownloadUpdateListener mListener;
    private String mUrl;
    private String downloadFileName;
    private Context mContext;
    public static String downloadPath=Environment.getExternalStorageDirectory()
            +File.separator+"applicationMarket";
    public enum STATE{IDLE,RUNNING}
    public static STATE state=STATE.IDLE;

    public DownloadTask(Context context, String s, String name, DownloadUpdateListener listener){
        this.mUrl=s;
        this.mContext=context;
        this.mListener=listener;
        this.downloadFileName=name;
    }

    @Override
    protected Long doInBackground(String... strings) {
        if (mUrl==null){
            return null;
        }
        HttpURLConnection connection=null;
        InputStream is=null;
        OutputStream os=null;
        RandomAccessFile raf=null;
        try {
            File dir=new File(downloadPath);
            if (!dir.exists()){
                dir.mkdir();
            }
            File file=new File(downloadPath,downloadFileName+".apk");
            if (!file.exists()){
                URL url = new URL(mUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                os=new FileOutputStream(file);
                is=connection.getInputStream();
                file.createNewFile();
                byte buffer [] = new byte[1024];
                int inputSize = -1;
                long total=connection.getContentLength();
                int count=0;
                while ((inputSize = is.read(buffer)) != -1){
                    os.write(buffer,0,inputSize);
                    count+=inputSize;
                    publishProgress((int) ((count / (float) total) * 100));
                    if (isCancelled()){
                        os.flush();
                        return null;
                    }
                }
                os.flush();
            }else {
                URL url = new URL(mUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                long readedSize=file.length();
                connection.setRequestProperty("Range", "bytes=" + readedSize + "-");
                is=connection.getInputStream();
                long total=readedSize+connection.getContentLength();
                raf=new RandomAccessFile(file,"rwd");
                raf.seek(readedSize);
                byte buffer [] = new byte[1024];
                int inputSize = -1;
                int count = (int)readedSize;
                while ((inputSize = is.read(buffer)) != -1){
                    raf.write(buffer,0,inputSize);
                    count+=inputSize;
                    publishProgress((int) ((count / (float) total) * 100));
                    if (isCancelled()){
                        return null;
                    }
                }
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
                if (os!=null){
                    os.close();
                }
                if (raf!=null){
                    raf.close();
                }
                if (connection!=null){
                    connection.disconnect();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(Long aLong) {
        super.onCancelled(aLong);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mListener!=null){
            mListener.onUpdate(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        //context??applicationContext
        Toast.makeText(mContext,downloadFileName+"下载成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(mContext,downloadFileName+"开始下载",Toast.LENGTH_SHORT).show();
        super.onPreExecute();
    }
}
