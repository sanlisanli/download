package me.mikasa.wandoujia.bean;

public class ApkItem {
    private String imgUrl;
    private String title;
    private String size;
    private String downloadTimes;
    private String contentLink;
    public void setImgUrl(String s){
        this.imgUrl=s;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setSize(String s){
        this.size=s;
    }
    public String getSize(){
        return size;
    }
    public void setDownloadTimes(String s){
        this.downloadTimes=s;
    }
    public String getDownloadTimes(){
        return downloadTimes;
    }
    public void setContentLink(String s){
        this.contentLink=s;
    }
    public String getContentLink(){
        return contentLink;
    }
}
