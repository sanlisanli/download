package me.mikasa.wandoujia.mvp.presenter;

import android.content.Context;

import me.mikasa.wandoujia.bean.ApkContent;
import me.mikasa.wandoujia.mvp.contract.GetApkDetailContract;
import me.mikasa.wandoujia.mvp.model.GetApkDetailModelImpl;

public class GetApkDetailPresenterImpl implements GetApkDetailContract.Presenter {
    private GetApkDetailContract.View mView;
    private GetApkDetailContract.Model mModel;
    public GetApkDetailPresenterImpl(GetApkDetailContract.View view){
        this.mView=view;
        mModel=new GetApkDetailModelImpl(this);
    }

    @Override
    public void getApkDetail(Context context, String link) {
        mModel.getApkDetail(context, link);
    }

    @Override
    public void getDetailSuccess(ApkContent apkContent) {
        mView.getDetailSuccess(apkContent);
    }

    @Override
    public void getDetailError(String s) {
        mView.getDetailError(s);
    }
}
