package itinterview.arpan.com.itinterview.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by philips on 10/27/18.
 */

public class NetworkUtility {


    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
