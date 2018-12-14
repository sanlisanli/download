package me.mikasa.wandoujia.mvp.contract;

import android.content.Context;
import me.mikasa.wandoujia.bean.ApkContent;

public interface GetApkDetailContract {
    interface View{
        void getDetailSuccess(ApkContent apkContent);
        void getDetailError(String s);
    }
    interface Model{
        void getApkDetail(Context context, String link);//没有getError,getSuccess
    }
    interface Presenter{
        void getApkDetail(Context context,String link);
        void getDetailSuccess(ApkContent apkContent);
        void getDetailError(String s);
    }
}
