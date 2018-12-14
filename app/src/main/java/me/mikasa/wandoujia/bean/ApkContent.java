package me.mikasa.wandoujia.bean;

import java.util.List;

public class ApkContent {
    private String title;
    private String iconUrl;
    private String downloadTimes;
    private String size;
    private String category;
    private List<String>screenshots;
    private String detail;
    private String apkUrl;

    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setIconUrl(String s){
        this.iconUrl=s;
    }
    public String getIconUrl(){
        return iconUrl;
    }
    public void setDownloadTimes(String s){
        this.downloadTimes=s;
    }
    public String getDownloadTimes(){
        return downloadTimes;
    }
    public void setSize(String s){
        this.size=s;
    }
    public String getSize(){
        return size;
    }
    public void setCategory(String s){
        this.category=s;
    }
    public String getCategory(){
        return category;
    }
    public void setScreenshots(List<String>ss){
        this.screenshots=ss;
    }
    public List<String>getScreenshots(){
        return screenshots;
    }
    public void setDetail(String s){
        this.detail=s;
    }
    public String getDetail(){
        return detail;
    }
    public void setApkUrl(String s){
        this.apkUrl=s;
    }
    public String getApkUrl(){
        return apkUrl;
    }
}
