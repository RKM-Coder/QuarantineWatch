package com.goalsr.homequarantineTracker.networkconnectivity;

import android.content.Context;
import android.content.IntentFilter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//https://github.com/JobGetabu/DroidNet
public class NetworkIndentity implements NetworkChangeReceiver.NetworkChangeListener{

    private static final Object LOCK = new Object();
    private static volatile NetworkIndentity sInstance;

    private WeakReference<Context> mContextWeakReference;
    private List<WeakReference<NetworkIdentityListener>> mInternetConnectivityListenersWeakReferences;
    private NetworkChangeReceiver mNetworkChangeReceiver;
    private boolean mIsNetworkChangeRegistered = false;
    private boolean mIsInternetConnected = false;

    private TaskFinished<Boolean> mCheckConnectivityCallback;

    private static final String CONNECTIVITY_CHANGE_INTENT_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    private NetworkIndentity(Context context) {
        Context appContext = context.getApplicationContext();
        mContextWeakReference = new WeakReference<>(appContext);
        mInternetConnectivityListenersWeakReferences = new ArrayList<>();
    }

    /**
     * Call this function in application class to do initial setup. it returns singleton instance.
     *
     * @param context need to register for Connectivity broadcast
     * @return instance of InternetConnectivityHelper
     */
    public static NetworkIndentity init(Context context) {
        if (context == null) {
            throw new NullPointerException("context can not be null");
        }

        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new NetworkIndentity(context);
                }
            }
        }
        return sInstance;
    }

    public static NetworkIndentity getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("call init(Context) in your application class before calling getInstance()");
        }
        return sInstance;
    }

    /**
     * Add DroidListener only if it's not added. It keeps a weak reference to the listener.
     * So user should have a strong reference to that listener otherwise that will be garbage collected
     */
    public void addInternetConnectivityListener(NetworkIdentityListener droidListener) {
        if (droidListener == null) {
            return;
        }
        mInternetConnectivityListenersWeakReferences.add(new WeakReference<>(droidListener));
        if (mInternetConnectivityListenersWeakReferences.size() == 1) {
            registerNetworkChangeReceiver();
            return;
        }
        publishInternetAvailabilityStatus(mIsInternetConnected);
    }

    /**
     * remove the weak reference to the listener
     */
    public void removeInternetConnectivityChangeListener(NetworkIdentityListener droidListener) {
        if (droidListener == null) {
            return;
        }

        if (mInternetConnectivityListenersWeakReferences == null) {
            return;
        }

        Iterator<WeakReference<NetworkIdentityListener>> iterator = mInternetConnectivityListenersWeakReferences.iterator();
        while (iterator.hasNext()) {

            //if weak reference is null then remove it from iterator
            WeakReference<NetworkIdentityListener> reference = iterator.next();
            if (reference == null) {
                iterator.remove();
                continue;
            }

            //if listener referenced by this weak reference is garbage collected then remove it from iterator
            NetworkIdentityListener listener = reference.get();
            if (listener == null) {
                reference.clear();
                iterator.remove();
                continue;
            }

            //if listener to be removed is found then remove it
            if (listener == droidListener) {
                reference.clear();
                iterator.remove();
                break;
            }
        }

        //if all listeners are removed then unregister NetworkChangeReceiver
        if (mInternetConnectivityListenersWeakReferences.size() == 0) {
            unregisterNetworkChangeReceiver();
        }
    }

    public void removeAllInternetConnectivityChangeListeners() {
        if (mInternetConnectivityListenersWeakReferences == null) {
            return;
        }

        Iterator<WeakReference<NetworkIdentityListener>> iterator = mInternetConnectivityListenersWeakReferences.iterator();
        while (iterator.hasNext()) {
            WeakReference<NetworkIdentityListener> reference = iterator.next();
            if (reference != null) {
                reference.clear();
            }
            iterator.remove();
        }
        unregisterNetworkChangeReceiver();
    }

    /**
     * registers a NetworkChangeReceiver if not registered already
     */
    private void registerNetworkChangeReceiver() {
        Context context = mContextWeakReference.get();
        if (context != null && !mIsNetworkChangeRegistered) {
            mNetworkChangeReceiver = new NetworkChangeReceiver();
            mNetworkChangeReceiver.setNetworkChangeListener(this);
            context.registerReceiver(mNetworkChangeReceiver, new IntentFilter(CONNECTIVITY_CHANGE_INTENT_ACTION));
            mIsNetworkChangeRegistered = true;
        }
    }

    /**
     * unregisters the already registered NetworkChangeReceiver
     */
    private void unregisterNetworkChangeReceiver() {
        Context context = mContextWeakReference.get();
        if (context != null && mNetworkChangeReceiver != null && mIsNetworkChangeRegistered) {
            try {
                context.unregisterReceiver(mNetworkChangeReceiver);
            } catch (IllegalArgumentException exception) {
                //consume this exception
            }
            mNetworkChangeReceiver.removeNetworkChangeListener();
        }
        mNetworkChangeReceiver = null;
        mIsNetworkChangeRegistered = false;
        mCheckConnectivityCallback = null;
    }

    @Override
    public void onNetworkChange(boolean isNetworkAvailable) {
        if (isNetworkAvailable) {
            mCheckConnectivityCallback = new TaskFinished<Boolean>() {
                @Override
                public void onTaskFinished(Boolean isInternetAvailable) {
                    mCheckConnectivityCallback = null;
                    publishInternetAvailabilityStatus(isInternetAvailable);
                }
            };
            new CheckInternetTask(mCheckConnectivityCallback).execute();
        } else {
            publishInternetAvailabilityStatus(false);
        }
    }

    public boolean getInternetAvalable(){
        return mIsInternetConnected;
    }

    private void publishInternetAvailabilityStatus(boolean isInternetAvailable) {
        mIsInternetConnected = isInternetAvailable;
        if (mInternetConnectivityListenersWeakReferences == null) {
            return;
        }

        Iterator<WeakReference<NetworkIdentityListener>> iterator = mInternetConnectivityListenersWeakReferences.iterator();
        while (iterator.hasNext()) {
            WeakReference<NetworkIdentityListener> reference = iterator.next();

            if (reference == null) {
                iterator.remove();
                continue;
            }

            NetworkIdentityListener listener = reference.get();
            if (listener == null) {
                iterator.remove();
                continue;
            }

            listener.onInternetConnectivityChanged(isInternetAvailable);
        }

        if (mInternetConnectivityListenersWeakReferences.size() == 0) {
            unregisterNetworkChangeReceiver();
        }
    }

}
