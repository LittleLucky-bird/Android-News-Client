package com.ihandy.a2013010230;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.PointerIcon;
import android.view.View;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by tangpeng on 16/9/5.
 */
public class ShowActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String keyname;
    private News news;
    private WebView webView;
    private ProgressBar pb;
//    private Button button_share = (Button) findViewById(R.id.button2);
//    private Button button_ilke = (Button) findViewById(R.id.button);

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activityshow);



        Intent intent = getIntent();
        keyname = intent.getStringExtra("keyname");
        final int position = intent.getIntExtra("position", 0);
        news = Data.hashMapNewsItem.get(keyname).newslist.get(position);
        if (news.isliked == 1)
            ((ImageButton)findViewById(R.id.button)).setImageResource(R.drawable.blue_favorite);

        toolbar = (Toolbar) findViewById(R.id.toolbarshow);
        setSupportActionBar(toolbar);
        toolbar.setTitle(news.title);
        pb = (ProgressBar) findViewById(R.id.pb);

        webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadUrl(news.url);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if(newProgress==100){
                    pb.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
//        webView = (WebView) findViewById(R.id.webView);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.setWebChromeClient(new WebChromeClient() {});
//        webView.loadUrl(news.url);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intent.putExtra(Intent.EXTRA_TEXT,news.title+"\n"+news.url);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (news.isliked == 0){
                    ((ImageButton)findViewById(R.id.button)).setImageResource(R.drawable.blue_favorite);
                    news.isliked = 1;
                    ContentValues values = new ContentValues();
                    values.put("islike",1);
                    Data.db.update(keyname,values,"news_id=?",new String[]{""+news.news_id});
                    values = new ContentValues();
                    values.put("news_id",news.news_id);
                    values.put("title",news.title);
                    values.put("image_url",news.image_url);
                    values.put("url",news.url);
                    values.put("origin",news.origin);
                    Data.db.insert("favorite",null,values);
                    NewsItem.favorite.newslist.add(0,news);
                    Toast.makeText(getApplication(), "已经将此新闻添加到Favorite", Toast.LENGTH_LONG).show();
                }
                else{
                    ((ImageButton)findViewById(R.id.button)).setImageResource(R.drawable.grey_favorite);

                    ContentValues values = new ContentValues();
                    values.put("islike",0);
                    Data.db.update(keyname,values,"news_id=?",new String[]{""+news.news_id});
                    for(int i = 0; i<NewsItem.favorite.newslist.size(); i++){
                        if (NewsItem.favorite.newslist.get(i).news_id==news.news_id){
                            NewsItem.favorite.newslist.remove(i);
                            break;
                        }
                    }
                    news.isliked = 0;
                    Data.db.delete("favorite","news_id=?",new String[]{""+news.news_id});
//                    MainActivity.contactsFragment.listView.setAdapter(MainActivity.contactsFragment.adapter);
                    Toast.makeText(ShowActivity.this, "已经将此新闻从Favorite中删除", Toast.LENGTH_LONG).show();
                }

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Show Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ihandy.a2013010230/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Show Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ihandy.a2013010230/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
