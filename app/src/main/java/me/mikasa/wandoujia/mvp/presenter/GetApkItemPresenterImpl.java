package me.mikasa.wandoujia.mvp.presenter;

import android.content.Context;

import java.util.List;

import me.mikasa.wandoujia.bean.ApkItem;
import me.mikasa.wandoujia.mvp.contract.GetApkItemContract;
import me.mikasa.wandoujia.mvp.model.GetApkItemModelImpl;

public class GetApkItemPresenterImpl implements GetApkItemContract.Presenter {
    private GetApkItemContract.View mView;
    private GetApkItemContract.Model mModel;
    public GetApkItemPresenterImpl(GetApkItemContract.View view){
        this.mView=view;
        mModel=new GetApkItemModelImpl(this);
    }

    @Override
    public void getItem(Context context) {
        mModel.getItem(context);
    }

    @Override
    public void getItemSuccess(List<ApkItem> list) {
        mView.getItemSuccess(list);
    }

    @Override
    public void getItemError(String msg) {
        mView.getItemError(msg);
    }
}
