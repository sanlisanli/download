package me.mikasa.wandoujia.mvp.model;

import android.content.Context;

import java.util.List;

import me.mikasa.wandoujia.bean.ApkItem;
import me.mikasa.wandoujia.listener.HttpCallbackListener;
import me.mikasa.wandoujia.mvp.contract.GetApkItemContract;
import me.mikasa.wandoujia.utils.HttpUrlConnUtil;
import me.mikasa.wandoujia.utils.JsoupUtil;

public class GetApkItemModelImpl implements GetApkItemContract.Model{
    private static final String url="https://sj.qq.com/myapp/category.htm?orgame=1";
    private GetApkItemContract.Presenter mPresenter;
    private static List<ApkItem>itemList=null;
    public GetApkItemModelImpl(GetApkItemContract.Presenter presenter){
        this.mPresenter=presenter;
    }

    @Override
    public void getItem(Context context) {
        HttpUrlConnUtil.doGet(context, url, "utf-8", new HttpCallbackListener() {
            @Override
            public void onResponse(String response) {
                itemList=JsoupUtil.getInstance().getApkItems(response);
                if (itemList!=null){
                    mPresenter.getItemSuccess(itemList);
                }else {
                    mPresenter.getItemError("请求网数据失败");
                }
            }

            @Override
            public void onError(String s) {
            }
        });
    }
}
