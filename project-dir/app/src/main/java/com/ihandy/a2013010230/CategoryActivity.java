package com.ihandy.a2013010230;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v13.FragmentPagerItems;


import java.util.ArrayList;
import java.util.Iterator;

public class CategoryActivity extends Fragment {

    Iterator<String> is;

    ViewGroup tab;
    ViewPager viewPager;
    SmartTabLayout viewPagerTab;
    FragmentPagerItems pages;
    FragmentPagerItemAdapter adapter;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.http_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        tab = (ViewGroup) view.findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(view.getContext()).inflate(R.layout.tabsmart, tab, false));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        pages = new FragmentPagerItems(view.getContext());
        init();
    }

    void init(){
        int i = 0;
        Data.namelist = new ArrayList<>();
        pages.clear();
        for (NewsItem ival:Data.newsItemslist){
            if (ival.islike==1) {
                pages.add(FragmentPagerItem.of(ival.name, Page.class));
                Data.namelist.add(i,ival.keyname);
                i++;
            }
        }
        adapter = new FragmentPagerItemAdapter(getFragmentManager(),pages);
        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(),SelectActivity.class);
        startActivityForResult(intent,1);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        init();
    }
}


