package com.arpan.itinterview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.arpan.itinterview.listener.InternetConnectionListener;
import com.arpan.itinterview.utility.NetworkUtility;

public class InternetConnectionWatcher extends BroadcastReceiver {

    private final InternetConnectionListener internetConnectionListener;

    public InternetConnectionWatcher(InternetConnectionListener internetConnectionListener){

        this.internetConnectionListener = internetConnectionListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(NetworkUtility.isNetworkConnected(context)){
            internetConnectionListener.isInternetConnected();
        }else{
            internetConnectionListener.isInternetDisconnected();
        }
    }
}
