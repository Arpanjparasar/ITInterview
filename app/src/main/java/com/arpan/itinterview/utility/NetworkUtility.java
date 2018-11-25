package com.arpan.itinterview.utility;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by philips on 10/27/18.
 */

public class NetworkUtility {


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
