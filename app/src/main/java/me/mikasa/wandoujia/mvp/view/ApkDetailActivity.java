package me.mikasa.wandoujia.mvp.view;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import me.mikasa.wandoujia.R;
import me.mikasa.wandoujia.adapter.ApkContentPicAdapter;
import me.mikasa.wandoujia.base.BaseToolbarActivity;
import me.mikasa.wandoujia.bean.ApkContent;
import me.mikasa.wandoujia.listener.DownloadUpdateListener;
import me.mikasa.wandoujia.listener.PermissionListener;
import me.mikasa.wandoujia.mvp.contract.GetApkDetailContract;
import me.mikasa.wandoujia.mvp.presenter.GetApkDetailPresenterImpl;
import me.mikasa.wandoujia.utils.DownloadTask;

public class ApkDetailActivity extends BaseToolbarActivity implements
        View.OnClickListener ,PermissionListener,GetApkDetailContract.View,DownloadUpdateListener {
    private TextView apkTitle,apkDownloadTimes,apkSize,apkCategory,apkDetail,mTitle;
    private ImageView iv_icon;
    private Button apkDownload;
    private Context mContext;
    private ApkContentPicAdapter mAdapter;
    private static String contentLink,apkDownloadUrl,apkName;
    private GetApkDetailPresenterImpl mPresenter;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private static int currentProgress=0;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_apk_detail;
    }

    @Override
    protected void initData() {
        mContext=this;
        Intent intent=getIntent();
        contentLink=intent.getStringExtra("contentlink");
        mAdapter=new ApkContentPicAdapter(mContext);
        mPresenter=new GetApkDetailPresenterImpl(this);
    }

    @Override
    protected void initView() {
       findView();
        RecyclerView recyclerView=findViewById(R.id.rv_pic_box);
        LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getApkDetail(mContext,contentLink);//请求网络数据
    }
    private void findView(){
        mTitle=findViewById(R.id.toolbar_tv);
        apkTitle=findViewById(R.id.tv_apk_title);
        apkDownloadTimes=findViewById(R.id.tv_apk_times);
        apkSize=findViewById(R.id.tv_apk_size);
        apkCategory=findViewById(R.id.tv_apk_category);
        apkDetail=findViewById(R.id.tv_apk_detail);
        iv_icon=findViewById(R.id.iv_apk_icon);
        apkDownload=findViewById(R.id.btn_apk_download);
    }

    @Override
    protected void initListener() {
        apkDownload.setOnClickListener(this);
    }
    @Override
    public void getDetailSuccess(ApkContent apkContent) {
        mTitle.setText(apkContent.getTitle());
        Glide.with(mContext).load(apkContent.getIconUrl()).asBitmap().into(iv_icon);
        apkTitle.setText(apkContent.getTitle());
        apkDownloadTimes.setText(apkContent.getDownloadTimes());
        apkSize.setText(apkContent.getSize());
        apkCategory.setText(apkContent.getCategory());
        apkDownload.setVisibility(View.VISIBLE);
        apkDownloadUrl=apkContent.getApkUrl();
        mAdapter.updateData(apkContent.getScreenshots());
        apkDetail.setText(apkContent.getDetail());
        apkName=apkContent.getTitle();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_apk_download:
                String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestRuntimePermission(permission,this);
                break;
            default:
                break;
        }
    }
    @Override
    public void onGranted() {
        download();
    }
    private void download(){
        if (DownloadTask.state==DownloadTask.STATE.IDLE){
            DownloadTask.state=DownloadTask.STATE.RUNNING;//进入下载状态
            notificationManager=(NotificationManager)mContext.getSystemService(Activity.NOTIFICATION_SERVICE);
            builder=new NotificationCompat.Builder(mContext);
            builder.setSmallIcon(R.mipmap.ic_bili);
            DownloadTask downloadTask=new DownloadTask(mContext,apkDownloadUrl,apkName,this);
            downloadTask.execute();//执行下载任务
        }else {
            showToast("当前已有下载任务，需待任务下载完成");
        }
    }
    @Override
    public void onDenied(List<String> deniedPermission) {
        showToast("你拒绝了下载请求");
    }
    @Override
    public void getDetailError(String s) {
        showToast(s);
    }

    @Override
    public void onUpdate(int progress) {
        Notification notification;
       if(progress-currentProgress>10){
           currentProgress=progress;
           builder.setContentInfo(apkName+"下载中...")
                   .setContentTitle("正在下载")
                   .setProgress(100,progress,false);
           notification=builder.build();
           notificationManager.notify(0,notification);
       }else if (progress==100){
           DownloadTask.state=DownloadTask.STATE.IDLE;
           if (Build.VERSION.SDK_INT<24){
               Intent intent=new Intent(Intent.ACTION_VIEW);
               File file=new File(DownloadTask.downloadPath,apkName+".apk");
               intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
               PendingIntent pendingIntent=PendingIntent.getActivity(mContext,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
               builder.setContentTitle("下载完成")
                       .setContentText("点击安装")
                       .setContentIntent(pendingIntent);
               notification=builder.build();
               notificationManager.notify(0,notification);
           }else {
               builder.setContentTitle("下载完成")
                       .setContentText(apkName+"下载完成")
                       .setAutoCancel(true);
               notification=builder.build();
               notificationManager.notify(0,notification);
           }
       }
    }
}
