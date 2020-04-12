package com.goalsr.homequarantineTracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.goalsr.homequarantineTracker.networkconnectivity.NetworkIndentity;


import java.util.List;

public class YelligoApplication extends Application {

    public static android.app.Application sApplication;
    //private StorageService storageService;

    public static android.app.Application getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    public boolean isBlueColorMode = true;
    public boolean isDarkThemeMode = false;
    private Activity currentActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sApplication = this;

        //storageService = new StorageService(this);
        NetworkIndentity.init(this);


    }

    public Activity getCurrentActivity() {
        return this.currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    private int mAppStatus = 0;

    public int getApplicationStatus() {
        return mAppStatus;
    }

    public void setApplicationStatus(int status) {
        this.mAppStatus = status;
    }

    public int checkAppStauts()
    {
        ActivityManager manager = (ActivityManager) getApplicationContext().getSystemService(Activity.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);


        /*ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();*/
        if (tasks.size() > 0)
        {
            ActivityManager.RunningTaskInfo task = tasks.get(0);
            //ActivityManager.AppTask task = tasks.get(0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.e("PACKAGE",task.topActivity.getPackageName());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.e("PACKAGE",task.topActivity.getPackageName());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (task.topActivity.getPackageName().startsWith("com.goalsr")) {
                        setApplicationStatus(0);//forground
                    } else {
                        setApplicationStatus(1);//background
                    }
                }
            }
        }

        return mAppStatus;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        NetworkIndentity.getInstance().removeAllInternetConnectivityChangeListeners();
    }


}