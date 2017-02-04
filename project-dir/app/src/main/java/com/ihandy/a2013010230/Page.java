package com.ihandy.a2013010230;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;



import java.util.ArrayList;

/**
 * Created by tangpeng on 16/8/30.
 */
public class Page extends Fragment implements AbsListView.OnItemClickListener,OnRefreshListener{
    private int num = 0;
    private int p;
    private SwipeRefreshLayout refresh_layout;
    private TextView textView2;
    private String text;
    private Button buttonFooter;

    MyAdapter adapter = null;
    ListView listView;
    NewsItem newsItem;
    ArrayList<News> newsList = new ArrayList<>();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    refresh_layout.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        p = FragmentPagerItem.getPosition(getArguments());
        buttonFooter = new Button(view.getContext());
        buttonFooter.setText("点击加载更多");
//        buttonFooter.setTextSize(12);
        buttonFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fresh();
            }
        });

        textView2 = (TextView) view.findViewById(R.id.textView2);
        refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        refresh_layout.setColorSchemeColors(Color.GREEN, Color.GRAY, Color.BLUE, Color.WHITE);
        refresh_layout.setSize(SwipeRefreshLayout.LARGE);
        refresh_layout.setOnRefreshListener(this);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        newsItem = Data.hashMapNewsItem.get(Data.namelist.get(p));
        newsList = newsItem.newslist;
        adapter = new MyAdapter(view.getContext().getApplicationContext(), newsList);
        listView.addFooterView(buttonFooter,null,false);
        listView.setAdapter(adapter);

        if (newsItem.isfreshed == 0) {
            refresh_layout.setRefreshing(true);
            newsItem.isfreshed = 1;
            onRefresh();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            News n = newsList.get(position);
            Intent intent = new Intent(getActivity(),ShowActivity.class);
            intent.putExtra("keyname",newsItem.keyname);
            intent.putExtra("position",position);
            startActivity(intent);
    }


    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                num = newsItem.fresh();
                mHandler.sendEmptyMessage(1);
                if (newsItem.isfreshed == 0)    refresh_layout.setRefreshing(true);
                if (num == 0) text = "没有新内容";
                else text = "为您刷新"+num+"条新闻";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(String.valueOf(text));
                            }
                        });
                        try {
                            Thread.sleep(700);
                        }catch (Exception e){}
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(String.valueOf(""));
                            }
                        });
                    }
                }).start();

            }
        }).start();
    }

    public void fresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                num = newsItem.fresh2();
                mHandler.sendEmptyMessage(1);
                if (newsItem.isfreshed == 0)    refresh_layout.setRefreshing(true);
                if (num == 0) text = "没有新内容";
                else text = "为您刷新"+num+"条新闻";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(String.valueOf(text));
                            }
                        });
                        try {
                            Thread.sleep(700);
                        }catch (Exception e){}
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(String.valueOf(""));
                            }
                        });
                    }
                }).start();

            }
        }).start();
    }
}


class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<News> data;
    ViewHolder viewHolder;
    Context context;

    class   ViewHolder{
        TextView title;
        ImageView thumb_image;
        TextView news_origin;

    }

    public MyAdapter(Context context, ArrayList<News> d){
        mInflater = LayoutInflater.from(context);
        data = d;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.news_title); // 标题
            viewHolder.news_origin = (TextView) convertView.findViewById(R.id.news_origin);
            viewHolder.thumb_image = (ImageView) convertView.findViewById(R.id.list_image); // 缩略图
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title.setText(data.get(position).title);
        viewHolder.news_origin.setText(data.get(position).origin);
//
        Glide.with(context) //
                .load(data.get(position).image_url) //
                .fitCenter()
                .into(viewHolder.thumb_image);


        return convertView;
    }
}
