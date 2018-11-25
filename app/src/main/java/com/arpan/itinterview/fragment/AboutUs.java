package com.arpan.itinterview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpan.itinterview.listener.FetchAboutListener;
import com.arpan.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.R;
import com.arpan.itinterview.tables.About;

public class AboutUs extends BaseFragment implements FetchAboutListener {


    private View fragmentView;
    private TextView mTvAbout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.activity_about_us, container, false);
        mTvAbout = fragmentView.findViewById(R.id.tv_about);

        FireBaseUtility.getAbout(this);
        return fragmentView;
    }


    @Override
    public void onSuccess(About about) {
        mTvAbout.setText(about.getAbout());
    }

    @Override
    public void onFailure(Exception exception) {
        Log.w("arpan", "getUser:onCancelled"+ exception.getMessage());
    }

    @Override
    public void isInternetConnected() {

    }
}
