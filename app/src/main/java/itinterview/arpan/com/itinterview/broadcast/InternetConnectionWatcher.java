package itinterview.arpan.com.itinterview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import itinterview.arpan.com.itinterview.listener.InternetConnectionListener;
import itinterview.arpan.com.itinterview.utility.NetworkUtility;

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
