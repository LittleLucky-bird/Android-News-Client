package com.ihandy.a2013010230;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;



import java.util.ArrayList;

/**
 * Created by tangpeng on 16/8/30.
 */
public class FavoriteActivity extends Fragment implements AbsListView.OnItemClickListener{

    MyAdapter adapter = null;
    ListView listView;
    NewsItem newsItem;
    ArrayList<News> newsList = new ArrayList<>();
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarfavorite);
        toolbar.setTitle("Favorite");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        listView = (ListView) view.findViewById(R.id.listfavorite);
        listView.setOnItemClickListener(this);
        newsItem = NewsItem.favorite;
        newsList = newsItem.newslist;
        News e = new News();
        newsList.add(0,e);
        adapter = new MyAdapter(view.getContext(), newsList);
        listView.setAdapter(adapter);
        newsList.remove(e);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        News n = newsList.get(position);
//        Intent intent = new Intent(getActivity(),ShowActivity.class);
//        intent.putExtra("keyname",newsItem.keyname);
//        intent.putExtra("position",position);
//        startActivity(intent);
    }

//    @Override
//    public void onClick(View v){
//        fresh();
//    }
//
//    public void fresh(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                num = newsItem.fresh();
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listView.setAdapter(adapter);
//                    }
//                });
//            }
//        }).start();
//    }
}