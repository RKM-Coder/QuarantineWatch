package com.goalsr.homequarantineTracker.networkconnectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private WeakReference<NetworkChangeListener> mNetworkChangeListenerWeakReference;

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkChangeListener networkChangeListener = mNetworkChangeListenerWeakReference.get();
        if (networkChangeListener != null) {
            networkChangeListener.onNetworkChange(isNetworkConnected(context));
        }



    }

    void setNetworkChangeListener(NetworkChangeListener networkChangeListener) {
        mNetworkChangeListenerWeakReference = new WeakReference<>(networkChangeListener);
    }

    void removeNetworkChangeListener() {
        if (mNetworkChangeListenerWeakReference != null) {
            mNetworkChangeListenerWeakReference.clear();
        }
    }

    //Interface to send opt to listeners
    public interface NetworkChangeListener {
        void onNetworkChange(boolean isNetworkAvailable);
    }

    boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();

        //should check null because in airplane mode it will be null
        return netInfo != null && netInfo.isAvailable() && netInfo.isConnected();

    }
}
