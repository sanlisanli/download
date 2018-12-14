package me.mikasa.wandoujia.mvp.model;

import android.content.Context;

import me.mikasa.wandoujia.bean.ApkContent;
import me.mikasa.wandoujia.listener.HttpCallbackListener;
import me.mikasa.wandoujia.mvp.contract.GetApkDetailContract;
import me.mikasa.wandoujia.utils.HttpUrlConnUtil;
import me.mikasa.wandoujia.utils.JsoupUtil;

public class GetApkDetailModelImpl implements GetApkDetailContract.Model {
    private GetApkDetailContract.Presenter mPresenter;
    private static ApkContent apkContent;
    public GetApkDetailModelImpl(GetApkDetailContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getApkDetail(Context context, String link) {
        HttpUrlConnUtil.doGet(context, link, "utf-8", new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                apkContent=JsoupUtil.getInstance().getApkContent(response);//解析html
                if (apkContent!=null){
                    mPresenter.getDetailSuccess(apkContent);
                }else {
                    mPresenter.getDetailError("获取网络数据失败");
                }
            }

            @Override
            public void onError(String s) {
            }
        });
    }
}
