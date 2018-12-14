package me.mikasa.wandoujia.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import me.mikasa.wandoujia.bean.ApkContent;
import me.mikasa.wandoujia.bean.ApkItem;

public class JsoupUtil {
    private static JsoupUtil sJsoupUtil;
    public static JsoupUtil getInstance(){
        if (sJsoupUtil ==null){
            synchronized (JsoupUtil.class){
                if (sJsoupUtil ==null){
                    sJsoupUtil =new JsoupUtil();
                }
            }
        }
        return sJsoupUtil;
    }
    public List<ApkItem>getApkItems(String s){
        List<ApkItem>beanLists=new ArrayList<>();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.main").first().child(0);
        for (Element ee:e.children()){
            ApkItem bean=new ApkItem();
            bean.setImgUrl(ee.child(0).child(0).child(0).attr("data-original"));
            bean.setTitle(ee.child(0).child(1).child(0).text());
            bean.setSize(ee.child(0).child(1).child(1).text());
            bean.setDownloadTimes(ee.child(0).child(1).child(2).text());
            bean.setContentLink("https://sj.qq.com/myapp/"+ee.child(0).child(0).attr("href"));
            beanLists.add(bean);
        }
        return beanLists;
    }
    public ApkContent getApkContent(String s){
        ApkContent bean=new ApkContent();
        Document doc=Jsoup.parse(s);
        Element e=doc.select("div.det-main-container").first();
        bean.setTitle(e.child(0).child(1).child(0).child(0).text());
        bean.setIconUrl(e.child(0).child(0).child(0).attr("src"));
        bean.setDownloadTimes(e.child(0).child(1).child(2).child(0).text());
        bean.setSize(e.child(0).child(1).child(2).child(2).text());
        bean.setCategory("分类："+e.child(0).child(1).child(2).child(4).child(1).text());//
        bean.setApkUrl(e.child(0).child(3).child(1).attr("data-apkurl"));
        Elements ee=e.select("div.pic-img-box");
        List<String>ss=new ArrayList<>(ee.size());
        for (Element e3:ee){
            ss.add(e3.child(0).attr("data-src"));
        }
        bean.setScreenshots(ss);
        Element e4=e.select("div.det-app-data-info").first();
        bean.setDetail(e4.text().replace("；","；"+"\r\n"+" "+"\r\n"));
        //newString=old.replace("；","；"+"\r\n");
        return bean;
    }
}
