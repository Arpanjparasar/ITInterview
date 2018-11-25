package com.arpan.itinterview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import itinterview.arpan.com.itinterview.R;

public class Terms extends BaseFragment {

    ImageView iv_back;
    private View fragmentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.activity_terms, container, false);
        return fragmentView;
    }


    @Override
    public void isInternetConnected() {

    }
}
