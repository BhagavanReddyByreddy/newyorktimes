package com.example.newyorktimes.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Bhagavan.B on 18-05-2016.
 */
public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    private static ConnectivityManager cm;

    public static int getConnectivityStatus(Context context) {
        if(context!=null){
            cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        if(cm!=null){
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return TYPE_WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return TYPE_MOBILE;
                }
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean getConnectivityStatusBoolean(Context context) {
        int conn = 0;
        if(context!=null)
            conn = NetworkUtil.getConnectivityStatus(context);

        boolean networkStatus = false;
        if (conn == NetworkUtil.TYPE_WIFI) {
            networkStatus = true;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            networkStatus = true;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            networkStatus = false;
        }
        return networkStatus;
    }
}

