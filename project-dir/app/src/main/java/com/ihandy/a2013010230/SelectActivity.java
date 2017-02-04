package com.ihandy.a2013010230;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by tangpeng on 16/9/4.
 */
public class SelectActivity extends AppCompatActivity implements AbsListView.OnItemClickListener{
    private ListView listwatched;
    private ListView listunwatched;
    private ArrayList<String> watchedlist;
    private ArrayList<String> unwatchedlist;
    private ArrayAdapter<String> adapterwatched;
    private ArrayAdapter<String> adapterunwatched;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        toolbar = (Toolbar) findViewById(R.id.toolbarselect);
        toolbar.setTitle("Management");
        setSupportActionBar(toolbar);
        listwatched = (ListView) findViewById(R.id.listViewwatched);
        listunwatched = (ListView) findViewById(R.id.listViewunwatched);
        listwatched.setOnItemClickListener(this);
        listunwatched.setOnItemClickListener(this);
        watchedlist = new ArrayList<String>();
        unwatchedlist = new ArrayList<String>();
        init();
    }

    void init(){
        watchedlist.clear();
        unwatchedlist.clear();
        for(String key:Data.selectlist){
            if (Data.hashMapNewsItem.get(key).islike==1)    watchedlist.add(key);
            else unwatchedlist.add(key);
        }
        adapterwatched = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,watchedlist);
        adapterunwatched = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,unwatchedlist);
        listwatched.setAdapter(adapterwatched);
        listunwatched.setAdapter(adapterunwatched);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == listwatched){
            Data.hashMapNewsItem.get(watchedlist.get(position)).islike = 0;
            ContentValues values = new ContentValues();
            values.put("islike",0);
            Data.db.update("islike",values,"keyname=?",new String[]{Data.hashMapNewsItem.get(watchedlist.get(position)).keyname});
        }
        else    {
            Data.hashMapNewsItem.get(unwatchedlist.get(position)).islike = 1;
            ContentValues values = new ContentValues();
            values.put("islike",1);
            Data.db.update("islike",values,"keyname=?",new String[]{Data.hashMapNewsItem.get(unwatchedlist.get(position)).keyname});
        }
        init();
    }
}
