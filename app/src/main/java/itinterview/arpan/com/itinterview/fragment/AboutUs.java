package itinterview.arpan.com.itinterview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import itinterview.arpan.com.itinterview.listener.FetchAboutListener;
import itinterview.arpan.com.itinterview.utility.FireBaseUtility;
import itinterview.arpan.com.itinterview.R;
import itinterview.arpan.com.itinterview.tables.About;

public class AboutUs extends Fragment implements FetchAboutListener {


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
}
