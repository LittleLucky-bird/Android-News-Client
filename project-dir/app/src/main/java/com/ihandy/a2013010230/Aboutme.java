package com.ihandy.a2013010230;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

/**
 * Created by tangpeng on 16/9/9.
 */
public class Aboutme extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activityaboutme, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbaraboutme);
        toolbar.setTitle("About me");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
}
