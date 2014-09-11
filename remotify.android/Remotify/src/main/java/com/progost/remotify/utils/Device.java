package com.progost.remotify.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by Ostap on 9/13/13.
 */
public class Device {

    public static String getUID(Context context){
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return wm.getConnectionInfo().getMacAddress();
    }

    public static String getModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return Text.capitalize(model);
        } else {
            return Text.capitalize(manufacturer) + " " + model;
        }
    }

    public static String getOS(){
        return System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")";
    }

    public static String getAPILevel(){
        return Integer.toString(Build.VERSION.SDK_INT);
    }

    public static String getCodename(){
        return Build.VERSION.CODENAME;
    }

    public static String getType(Context context){
        return isTablet(context)? "tablet": "phone";
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
