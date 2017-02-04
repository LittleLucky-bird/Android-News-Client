package com.ihandy.a2013010230;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tangpeng on 16/8/31.
 */
public class NewsItem {

    static NewsItem top_stories = new NewsItem("top_stories","Top Stories");
    static NewsItem technology = new NewsItem("technology","Technology");
    static NewsItem national = new NewsItem("national","Nigeria");
    static NewsItem sports = new NewsItem("sports","Sports");
    static NewsItem health = new NewsItem("health","Health");
    static NewsItem world = new NewsItem("world","World");
    static NewsItem entertainment = new NewsItem("entertainment","Entertainment");
    static NewsItem science = new NewsItem("science","Science");
    static NewsItem favorite = new NewsItem("favorite","Favorite");

    int isfreshed = 0;
    int islike = 1;
    long last_news_id = 0;
    String keyname;
    String name;
    ArrayList<News> newslist = new ArrayList<>();

    public NewsItem(String n,String m){ keyname = n;name = m;}

    public int fresh(){
        String message = JsonString.sendHttpRequest("http://assignment.crazz.cn/news/query?locale=en&category="+keyname);
        int num = 0;
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("news");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject ival = jsonArray.getJSONObject(i);
                if (ival.getLong("news_id") == last_news_id)
                    break;
                News ivalnews = new News();
                ivalnews.news_id = ival.getLong("news_id");
                ivalnews.title = ival.getString("title");
                ivalnews.image_url = ival.getJSONArray("imgs").getJSONObject(0).getString("url");
                ivalnews.isliked = 0;
                try {
                    ivalnews.url = ival.getJSONObject("source").getString("url");
                }catch (Exception e){ivalnews.url = null;}
                ivalnews.origin = ival.getString("origin");
                newslist.add(i,ivalnews);
                num++;
            }
            for (int i = num - 1;i>=0;i--){
                ContentValues e = new ContentValues();
                e.put("title",newslist.get(i).title);
                e.put("news_id",newslist.get(i).news_id);
                e.put("url",newslist.get(i).url);
                e.put("image_url",newslist.get(i).image_url);
                e.put("origin",newslist.get(i).origin);
                e.put("islike",newslist.get(i).isliked);
                Data.db.insert(keyname,null,e);
            }
            last_news_id = jsonArray.getJSONObject(0).getLong("news_id");
        }catch (Exception e){}
        return num;
    }
    public int fresh2(){
        if (newslist.size() == 0)   return 0;
        String message = JsonString.sendHttpRequest("http://assignment.crazz.cn/news/query?locale=en&category="+keyname+"&max_news_id="+(newslist.get(newslist.size()-1).news_id-1));
        int num = 0;
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("news");
            for (int i = 0;i<jsonArray.length();i++){
                JSONObject ival = jsonArray.getJSONObject(i);
                News ivalnews = new News();
                ivalnews.news_id = ival.getLong("news_id");
                ivalnews.title = ival.getString("title");
                ivalnews.image_url = ival.getJSONArray("imgs").getJSONObject(0).getString("url");
                ivalnews.isliked = 0;
                try {
                    ivalnews.url = ival.getJSONObject("source").getString("url");
                }catch (Exception e){ivalnews.url = null;}
                ivalnews.origin = ival.getString("origin");
                newslist.add(newslist.size(),ivalnews);
                num++;
            }
            for (int i = newslist.size()-1;i>=newslist.size()-num;i--){
                ContentValues e = new ContentValues();
                e.put("title",newslist.get(i).title);
                e.put("news_id",newslist.get(i).news_id);
                e.put("url",newslist.get(i).url);
                e.put("image_url",newslist.get(i).image_url);
                e.put("origin",newslist.get(i).origin);
                e.put("islike",newslist.get(i).isliked);
                Data.db.insert(keyname,null,e);
            }
        }catch (Exception e){}
        return num;
    }
}
