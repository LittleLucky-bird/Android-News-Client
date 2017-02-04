package com.ihandy.a2013010230;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by tangpeng on 16/9/3.
 */
public class Data {
    static ArrayList<String> selectlist = new ArrayList<String>(){{add("top_stories"); add("technology");add("national");add("sports");add("health");add("world");add("entertainment");add("science");}};
    static ArrayList<String> namelist;
    static SQLiteDatabase db;
    static NewsItem[] newsItemslist = {NewsItem.top_stories,NewsItem.technology,NewsItem.national,NewsItem.sports,NewsItem.health,NewsItem.world,NewsItem.entertainment,NewsItem.science};
    static HashMap<String,NewsItem> hashMapNewsItem = new HashMap<String, NewsItem>();
    static void init(){

        hashMapNewsItem.put("top_stories", NewsItem.top_stories);
        hashMapNewsItem.put("technology", NewsItem.technology);
        hashMapNewsItem.put("national", NewsItem.national);
        hashMapNewsItem.put("sports", NewsItem.sports);
        hashMapNewsItem.put("health", NewsItem.health);
        hashMapNewsItem.put("world", NewsItem.world);
        hashMapNewsItem.put("entertainment", NewsItem.entertainment);
        hashMapNewsItem.put("science", NewsItem.science);

        db.execSQL("create table if not exists islike" +
                "(_id integer primary key autoincrement,keyname TEXT,islike INTEGER)");
        if(db.query("islike",null,null,null,null,null,null).getCount() == 0) {
            for (NewsItem key : newsItemslist) {
                key.islike = 1;
                ContentValues e = new ContentValues();
                e.put("keyname", key.keyname);
                e.put("islike", 1);
                db.insert("islike",null,e);
            }
        }
        else {
            Cursor cursor = db.query("islike",null,null,null,null,null,null);
            while(cursor.moveToNext()){
                String key = cursor.getString(1);
                int i = cursor.getInt(2);
                hashMapNewsItem.get(key).islike = i;
            }
        }

        Iterator<Map.Entry<String, NewsItem>> iter = hashMapNewsItem.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry<String, NewsItem> entry = iter.next();
            String key = entry.getKey();
            NewsItem val = entry.getValue();
            db.execSQL("create table if not exists " + key +
                    "(_id integer primary key autoincrement,title TEXT,news_id INTEGER,url TEXT,image_url TEXT,origin TEXT,islike INTEGE)");
            Cursor cursor = db.query(key,null,null,null,null,null,"news_id desc");


            while (cursor.moveToNext()) {
                News e = new News();
                e.title = cursor.getString(1);
                e.news_id = cursor.getLong(2);
                e.url = cursor.getString(3);
                e.image_url = cursor.getString(4);
                e.origin = cursor.getString(5);
                e.isliked = cursor.getInt(6);
                val.newslist.add(e);

                val.last_news_id = val.newslist.get(0).news_id;
            }
        }
        db.execSQL("create table if not exists favorite"+
                "(_id integer primary key autoincrement,title TEXT,news_id INTEGER,url TEXT,image_url TEXT,origin TEXT,islike INTEGE)");

        Cursor cursor = db.query("favorite",null,null,null,null,null,"_id desc");

        NewsItem.favorite.newslist = new ArrayList<>();
        while (cursor.moveToNext()) {
            News e = new News();
            e.title = cursor.getString(1);
            e.news_id = cursor.getLong(2);
            e.url = cursor.getString(3);
            e.image_url = cursor.getString(4);
            e.origin = cursor.getString(5);
            e.isliked = 1;
            NewsItem.favorite.newslist.add(e);
        }

    }
}
